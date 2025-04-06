public class Interpolation {
    double[]x;
    double[]y;
    Interpolation(double[] x, double[] y) {
        if (x.length != y.length){
            System.exit(-1);
        }
        this.x = x;
        this.y = y;
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
        System.out.println(wynik);
    }

    public void methodNewton(double w){
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

        for(int i = 0; i < values.length; i++){
            System.out.println("Wielkosc tablic: " + values[i].length);
        }

        for(int i = 0; i < x.length; i++){
            if(i == 0){
                finals[0] = y[0];
            }
            if(i == 1){
                System.out.println("Pracuję na tablicy o wielkości: " + values[i].length);
                for(int j = 0; j < x.length - 1; j++){
                    System.out.println("Liczę: (" + y[j+1] + " - " + y[j] + ") / (" + x[j+1] + " - " + x[j] + ")");
                    values[i][j] = (y[j+1] - y[j]) / (x[j+1] - x[j]);
                    System.out.println("Tab[j] = " + values[i][j]);
                    if(j ==0){
                        finals[i] = (y[j+1] - y[j]) / (x[j+1] - x[j]);
                    }
                }
            }
            if(i > 1){
                System.out.println("Pracuję na tablicy o wielkości: " + values[i].length);
                for(int j = 0; j < x.length - i; j++){
                    System.out.println("Liczę: (" + values[i-1][j+1] + " - " + values[i-1][j] + ") / (" + x[i+j] + " - " + x[j] + ")");
                    values[i][j] = (values[i-1][j+1] - values[i-1][j]) / (x[i+j] - x[j]);
                    System.out.println("Tab[j] = " + values[i][j]);
                    if(j ==0){
                        finals[i] = (values[i-1][j+1] - values[i-1][j]) / (x[i+j] - x[j]);
                    }
                }
            }
        }

        System.out.println("wypisz finalne");
        for (int i = 0; i < finals.length; i++){
            System.out.println(finals[i]);
        }
        System.out.println();
        System.out.println("licze wielomian: ");
        double wynik = 0;
        for (int i = 0; i < finals.length; i++){
            wynik+= laczNawiasy(i, w, finals[i]);
        }
        System.out.println("wynik: " + wynik);
    }

    private double laczNawiasy(int k, double w, double mnoznik){
        double wynik = 1;
        for(int i = 0; i < k; i++){
            wynik *= (w - x[i]);
        }
        return mnoznik * wynik;
    }

    public static void main(String[] args) {
        Interpolation ob1 = new Interpolation(new double[] { 1, 2, 3 }, new double[] { 7, 9, 18 });
        ob1.methodLagrange(1.5);
        Interpolation ob2 = new Interpolation(new double[] { -5, -4, -1, 3, 5 }, new double[] { 975, 433, 7, -1, 235 });
        ob2.methodLagrange(2);
        Interpolation ob3 = new Interpolation(new double[] { -4, -2, 0, 2, 4 }, new double[] { -96, 6, -4, -30, -360 });
        ob3.methodLagrange(3);
        System.out.println("Newton");
        Interpolation ob4 = new Interpolation(new double[] { -4, -2, 0, 2, 4}, new double[] { -96, 6, -4, -30, -360 });
        ob4.methodNewton(3);
    }
}
