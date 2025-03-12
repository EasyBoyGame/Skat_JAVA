package org.example.logic;

import java.util.HashMap;

public class Kartenwert {
    public HashMap<String, Integer> kartenWertigkeit = new HashMap<>();

    // Konstruktor
    public Kartenwert(Farbe trumpf){
        if(trumpf != Farbe.NULL){
            kartenWertigkeit.put("SIEBEN", 1);
            kartenWertigkeit.put("ACHT", 2);
            kartenWertigkeit.put("NEUN", 3);
            kartenWertigkeit.put("DAME", 4);
            kartenWertigkeit.put("KOENIG", 5);
            kartenWertigkeit.put("ZEHN", 6);
            kartenWertigkeit.put("ASS", 7);
            kartenWertigkeit.put("BUBE", 8);
        }
        else {
            kartenWertigkeit.put("SIEBEN", 1);
            kartenWertigkeit.put("ACHT", 2);
            kartenWertigkeit.put("NEUN", 3);
            kartenWertigkeit.put("ZEHN", 4);
            kartenWertigkeit.put("BUBE", 5);
            kartenWertigkeit.put("DAME", 6);
            kartenWertigkeit.put("KOENIG", 7);
            kartenWertigkeit.put("ASS", 8);
        }
    }

    public int getFarbwert(String karte){
        int result = 0;
        switch (karte.split(" ")[0]){
            case "KREUZ" -> result = 4;
            case "PIK" -> result = 3;
            case "HERZ" -> result = 2;
            case "KARO" -> result = 1;
        }
        return result;
    }

    public int getPunkte(Kartenart kartenart){
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
