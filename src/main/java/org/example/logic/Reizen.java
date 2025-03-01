/*
package org.example.logic;


public class Reizen {
    private int aktuellerReizwert;
    private int hoechsterReizwert;
    private int gewinner;

    public int getHoechsterReizwert() {
        return hoechsterReizwert;
    }

    public void setHoechsterReizwert(int hoechsterReizwert) {
        this.hoechsterReizwert = hoechsterReizwert;
    }


    public Reizen() {   //Konstruktor
        this.aktuellerReizwert = 18;
        this.hoechsterReizwert = 0;
        this.gewinner = -1;
        sv.aktuellerSpieler = 0;
    }

    public boolean reizen (int spielerIndex, int caseIndex){
        int reizwert = getReizwert(caseIndex);
        if (reizwert > aktuellerReizwert) {
            aktuellerReizwert = reizwert;
            hoechsterReizwert = reizwert;
            gewinner = spielerIndex;
            sv.naechsterSpieler();
            return true;
        }
        return false;
    }

    public void passe (int spielerIndex){
        sv.naechsterSpieler();
        if (spielerIndex == gewinner) {
        }
    }

    private int getReizwert(int caseIndex){
        switch (caseIndex) {
            case 0:
                return 18;
            case 1:
                return 20;
            case 2:
                return 22;
            case 3:
                return 23;
            case 4:
                return 24;
            case 5:
                return 27;
            case 6:
                return 30;
            case 7:
                return 33;
            case 8:
                return 35;
            case 9:
                return 36;
            case 10:
                return 40;
            case 11:
                return 44;
            case 12:
                return 45;
            case 13:
                return 46;
            case 14:
                return 48;
            case 15:
                return 50;
            case 16:
                return 55;
            case 17:
                return 59;
            case 18:
                return 60;
            case 19:
                return 72;
            case 20:
                return 96;
            case 21:
                return 120;
            default:
                return 0;
        }
    }
}

 */