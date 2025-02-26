package org.example.logic;

public class Kartenwert {
    public static int getPunkte(Kartenart kartenart){
        switch(kartenart){
            case SIEBEN:
                return 0;
            case ACHT:
                return 0;
            case NEUN:
                return 0;
            case ZEHN:
                return 10;
            case BUBE:
                return 2;
            case DAME:
                return 3;
            case KOENIG:
                return 4;
            case ASS:
                return 11;
            default:
                throw new IllegalArgumentException("Unbekannter Wert" + kartenart);
        }
    }
}
