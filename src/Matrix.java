public class Macierz {
    double[][]tab;
    int wiersze;
    int kolumny;
    Macierz(double[][] tab){
        this.tab = tab;
        wiersze = tab.length;
        kolumny = tab[0].length;
    }
    public void wypisz() {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(" " + tab[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void setWartosc(Macierz m, int i, int j, double value) {
        m.tab[i][j] = value;
    }

    public void oblicz(){
        Macierz m = zerowanie();
        m.rownanie();
    }

    public Macierz zerowanie(){
        double[][] tab2 = new double[tab.length][tab[0].length];
        for (int i = 0; i < wiersze; i++) {
            for (int j = 0; j < kolumny; j++) {
                tab2[i][j] = tab[i][j];
            }
        }
        Macierz wynikowa = new Macierz(tab2);
        int k=0;
        while(k<wiersze-1){
            double shrWartosc;
            for (int i = k+1; i < wiersze; i++) {
                shrWartosc = wynikowa.tab[i][k];
                for (int j = k; j < kolumny; j++) {
                    if(i==1){
                        setWartosc(wynikowa,0, j, wynikowa.tab[0][j]);
                    }
                    double wynik = wynikowa.tab[i][j]-(wynikowa.tab[k][j])*shrWartosc/wynikowa.tab[k][k];
                    //System.out.println("Wynik dla i=" + i + " j=" + j + " k=" + k + " wynik: " + wynikowa.tab[i][j] + "-(" +(wynikowa.tab[k][j])+")* "+shrWartosc+"/"+wynikowa.tab[k][k]);
                    //if(k==1) wynikowa.wypisz();
                    setWartosc(wynikowa,i, j, wynik);
                }
            }
            this.tab = wynikowa.tab;
            k++;
        }
        return wynikowa;
    }

    private void rownanie() {
        double[] wyniki = new double[wiersze];
        for (int i = wiersze - 1; i >= 0; i--) {
            double suma = tab[i][kolumny - 1];
            for (int j = i + 1; j < kolumny - 1; j++) {
                suma -= tab[i][j] * wyniki[j];
            }
            try{
                if (tab[i][i] == 0) throw new ArithmeticException("Dzielenie przez zero!");
                wyniki[i] = suma / tab[i][i];
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
        for (int i = 0; i < wyniki.length; i++) {
            System.out.println("x"+i+" = "+wyniki[i]);
        }
    }

}