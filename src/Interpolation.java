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
    public static void main(String[] args) {
        Interpolation ob1 = new Interpolation(new double[] { 1, 2, 3 }, new double[] { 7, 9, 18 });
        ob1.methodLagrange(1.5);
        Interpolation ob2 = new Interpolation(new double[] { -5, -4, -1, 3, 5 }, new double[] { 975, 433, 7, -1, 235 });
        ob2.methodLagrange(2);
    }
}
