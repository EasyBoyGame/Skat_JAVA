
package org.example.logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reizen {
    private int aktuellerReizwert;
    private List<Integer> reizWerte = new ArrayList<>();
    private int[][] reizTabelle = new int[5][7];

    public Reizen() {   //Konstruktor
        this.aktuellerReizwert = 0;
        reizWerte.addAll(Arrays.asList(18, 20, 22, 23, 24, 27, 30, 33, 35, 36, 40, 44, 45, 46, 48, 50, 59, 60, 72, 96, 120));

    }

    public int appendReizwert(){
        aktuellerReizwert++;
        return reizWerte.get(aktuellerReizwert-1);
    }

    public int getReizwert() {
        return reizWerte.get(aktuellerReizwert-1);
    }
}