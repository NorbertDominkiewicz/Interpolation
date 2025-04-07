public class Interpolation {
    double[]x;
    double[]y;
    double[]px;
    double[]py;
    Interpolation(double[] x, double[] y) {
        if (x.length != y.length){
            System.exit(-1);
        }
        this.x = x;
        this.y = y;
    }

    Interpolation(double[] x, double[] y, double[] px, double[] py) {
        if ((x.length != y.length) && (px.length != py.length)){
            System.exit(-1);
        }
        this.x = x;
        this.y = y;
        this.px = px;
        this.py = py;
    }

    private double countFirstDegree(int i, double w){
        double licznik = 1;
        double mianownik = 1;
        for(int j = 1; j < x.length; j++){
            if (i == j) continue;
            licznik *= (w - x[j]);
            mianownik *= (x[i] - x[j]);
        }
        return y[i]*(licznik/mianownik);
    }

    private double countSecondDegree(int i, double w){
        double licznik = 1;
        double mianownik = 1;
        for(int j = 0; j < x.length; j++){
            if (i == j) continue;
            licznik *= (w - x[j]);
            mianownik *= (x[i] - x[j]);
        }
        return y[i]*(licznik/mianownik);
    }

    private double countNDegree(int i,double w){
        double licznik = 1;
        double mianownik = 1;
        for(int j = 0; j < x.length; j++){
            if (i == j) continue;
            licznik *= (w - x[j]);
            mianownik *= (x[i] - x[j]);
        }
        return y[i]*(licznik/mianownik);
    }
    public void methodLagrange(double w){
        double wynik = 0;
        for (int i = 0; i < x.length; i++){
            if(i == 0) {
                wynik += countFirstDegree(i, w);
            } else if(i == 1){
                wynik += countSecondDegree(i, w);
            } else {
                wynik += countNDegree(i, w);
            }
        }
        System.out.println("Lagrange: " + wynik);
    }

    public void methodDifferentialNewton(double w){
        double [][] values = new double[x.length][];
        double [] finals = new double[x.length];
        int indexArray = x.length - 1;
        for(int i = 0; i < values.length; i++){
            if(i == 0){
                values[i] = new double[1];
            }
            else{
                values[i] = new double[indexArray];
                indexArray--;
            }
        }
        for(int i = 0; i < x.length; i++){
            if(i == 0){
                finals[0] = y[0];
            }
            if(i == 1){
                for(int j = 0; j < x.length - 1; j++){
                    values[i][j] = (y[j+1] - y[j]) / (x[j+1] - x[j]);
                    if(j ==0){
                        finals[i] = (y[j+1] - y[j]) / (x[j+1] - x[j]);
                    }
                }
            }
            if(i > 1){
                for(int j = 0; j < x.length - i; j++){
                    values[i][j] = (values[i-1][j+1] - values[i-1][j]) / (x[i+j] - x[j]);
                    if(j ==0){
                        finals[i] = (values[i-1][j+1] - values[i-1][j]) / (x[i+j] - x[j]);
                    }
                }
            }
        }
        double wynik = 0;
        for (int i = 0; i < finals.length; i++){
            wynik+= multiply(i, w, finals[i]);
        }
        System.out.println("Differential Newton: " + wynik);
    }

    public void methodProgressiveNewton(double w){
        double h = x[2] - x[1];
        double [][] values = new double[x.length][];
        double [] finals = new double[x.length];
        int indexArray = x.length - 1;

        for(int i = 0; i < values.length; i++){
            if(i == 0){
                values[i] = new double[1];
            }
            else{
                values[i] = new double[indexArray];
                indexArray--;
            }
        }
        for(int i = 0; i < x.length; i++){
            if(i == 0){
                finals[0] = y[0];
            }
            if(i == 1){
                for(int j = 0; j < x.length - 1; j++){
                    values[i][j] = (y[j+1] - y[j]);
                    if(j ==0){
                        finals[i] = (y[j+1] - y[j]);
                    }
                }
            }
            if(i > 1){
                for(int j = 0; j < x.length - i; j++){
                    values[i][j] = (values[i-1][j+1] - values[i-1][j]);
                    if(j ==0){
                        finals[i] = (values[i-1][j+1] - values[i-1][j]);
                    }
                }
            }
        }
        double wynik = 0;
        for (int i = 0; i < finals.length; i++){
            if(i == 0){
                wynik += multiply(i, w, finals[i]);
            } else{
                wynik+= divide(multiply(i, w, finals[i]),i,h);
            }
        }
        System.out.println("Progressive Newton: " + wynik);
    }

    private double divide(double wynik, int k, double h){
        return wynik / (silnia(k) * Math.pow(h,k));
    }

    private double silnia(int k) {
        if (k < 2) {
            return 1;
        }
        return k * silnia(k - 1);
    }

    private double multiply(int k, double w, double mnoznik){
        double wynik = 1;
        for(int i = 0; i < k; i++){
            wynik *= (w - x[i]);
        }
        return mnoznik * wynik;
    }

    private double[] wielomian(double x){
        double[] values = new double[4];
        for(int i = 0; i < 4; i++){
            values[i] = Math.pow(x,i);
        }
        return values;
    }

    private double[] wielomianPoch(double x){
        double[] values = new double[4];
        values[0] = 0;
        for(int i = 1; i < 4; i++){
            values[i] = i * Math.pow(x,i-1);
        }
        return values;
    }

    private void fillMatrixWithWielomians(Matrix matrix){
        int lastRow = 0;
        for(int i = 0; i < x.length; i++){
            double [] values = wielomian(x[i]);
            for(int j = 0; j < values.length; j++){
                matrix.tab[i][j] = values[j];
            }
            lastRow++;
        }
        for(int i = 0; i < px.length; i++){
            double [] values = wielomianPoch(px[i]);
            for(int j = 0; j < values.length; j++){
                matrix.tab[lastRow + i][j] = values[j];
            }
        }
    }

    private void fillColumnsBetween(Matrix matrix){
        for(int i = 2; i < matrix.wiersze; i++){
            if(i < x.length) {
                for (int j = 0; j < i - 1; j++) {
//                System.out.println("i = " + i + " j = " + j);
//                System.out.println("(" + x[i] + " - " + x[2-1+j] + ")^3");
                    matrix.tab[i][j + 4] = Math.pow(x[i] - x[j + 1], 3);
                }
            } else {
                i++;
                for (int j = 0; j < i - 2; j++) {
                    System.out.println("i = " + i + " j = " + j);
                    matrix.tab[i][j + 4] = 3 * Math.pow(x[3] - x[j + 1], 2);
                }
            }
        }
    }

    private void fillYColumnsMatrix(Matrix matrix, double [] y, double [] py){
        int lastRow = 0;
        for(int i = 0; i < matrix.wiersze; i++){
            if(i < y.length){
                matrix.tab[i][matrix.kolumny-1] = y[i];
                lastRow++;
            }
        }
        for(int i = 0; i < py.length; i++){
            matrix.tab[lastRow + i][matrix.kolumny-1] = py[i];
        }
    }

    public void stickMethod(){
        Matrix matrix = new Matrix(x.length + px.length);
        fillMatrixWithWielomians(matrix);
        fillColumnsBetween(matrix);
        fillYColumnsMatrix(matrix, y, py);
        //matrix.wypisz();
        matrix.oblicz();
    }

    public static void main(String[] args) {
//        Interpolation ob3 = new Interpolation(new double[] { -4, -2, 0, 2, 4 }, new double[] { -96, 6, -4, -30, -360 });
//        ob3.methodLagrange(3);
//        Interpolation ob4 = new Interpolation(new double[] { -4, -2, 0, 2, 4}, new double[] { -96, 6, -4, -30, -360 });
//        ob4.methodDifferentialNewton(3);
//        Interpolation ob5 = new Interpolation(new double[] { -4, -2, 0, 2, 4}, new double[] { -96, 6, -4, -30, -360 });
//        ob5.methodProgressiveNewton(3);
        Interpolation ob6 = new Interpolation(new double[] { 1, 3, 5, 7 }, new double[] { 1, 8, 9, 17 }, new double[] { 1, 7 }, new double[] { 1, 1 });
        ob6.stickMethod();
    }
}
