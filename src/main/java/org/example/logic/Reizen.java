package org.example.logic;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Reizen {
    private int aktuellerReizwert;
    private int hoechsterReizwert;
    private int gewinner;
    private List<Integer> reizTabelle = new ArrayList<>();



    public Reizen() {   //Konstruktor
        this.aktuellerReizwert = 18;
        this.hoechsterReizwert = 0;
        this.gewinner = -1;
        reizTabelle.addAll(Arrays.asList(18, 20, 22, 23, 24, 27, 30, 33, 35, 36, 40, 44, 45, 46, 48, 50, 55, 59, 60, 72, 96, 120));
        //sv.aktuellerSpieler = 0;
    }


    public int getHoechsterReizwert() {
        return hoechsterReizwert;
    }


    public void setHoechsterReizwert(int hoechsterReizwert) {
        this.hoechsterReizwert = hoechsterReizwert;
    }


    public boolean reizen (int spielerIndex, int caseIndex){
        int reizwert = getReizwert(caseIndex);
        if (reizwert > aktuellerReizwert) {
            aktuellerReizwert = reizwert;
            hoechsterReizwert = reizwert;
            gewinner = spielerIndex;
            //sv.naechsterSpieler();
            return true;
        }
        return false;
    }

    public void passe (int spielerIndex){
        //sv.naechsterSpieler();
        if (spielerIndex == gewinner) {
        }
    }

    private int getReizwert(int caseIndex){
        return reizTabelle.get(caseIndex);
    }
}