package org.example.logic;

import java.util.*;

public class Mischen {

    List<Karte> deck;
    public List<Karte> kartenSp1;
    public List<Karte> kartenSp2;
    public List<Karte> kartenSp3;
    public List<Karte> skat;



    public Mischen() {
        deck = kartenErstellen();
    }


    public List<Karte> getKarten(int spieler){
        switch (spieler){
            case 1:
                return kartenSp1;
            case 2:
                return kartenSp2;
            case 3:
                return kartenSp3;
            case 4:
                return skat;
        }
        return null;
    }


    public List<Karte> kartenErstellen() {
        List<Karte> deck = new ArrayList<>();
        for (Farbe farbe : Farbe.values()) {
            if (farbe == Farbe.GRAND || farbe == Farbe.NULL) continue;
            for (Kartenart kartenart : Kartenart.values()) {
                deck.add(new Karte(farbe, kartenart));
            }
        }
        Collections.shuffle(deck);

        kartenSp1 = kartenSortieren(deck.subList(0 ,10), Farbe.KREUZ);
        kartenSp2 = kartenSortieren(deck.subList(10 ,20), Farbe.KREUZ);
        kartenSp3 = kartenSortieren(deck.subList(20 ,30), Farbe.KREUZ);
        skat = kartenSortieren(deck.subList(30, 32), Farbe.KREUZ);
        return deck;
    }


    /** Reihenfolge der Kartenwerte und Farben
     * @param deck Deck welches sortiert werden soll
     * @param farbe Bestimmt Sortierreihenfolge
     * @return Gibt sortiertes Array aus
     */
    public List<Karte> kartenSortieren(List<Karte> deck, Farbe farbe) {
        List<String> sortierReihenfolge = new ArrayList<>(Arrays.asList("KREUZ BUBE", "PIK BUBE", "HERZ BUBE", "KARO BUBE"));
        switch (farbe){
            case NULL:
                sortierReihenfolge = Arrays.asList(
                        "KREUZ ASS", "KREUZ KOENIG", "KREUZ DAME", "KREUZ BUBE", "KREUZ ZEHN", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                        "PIK ASS", "PIK KOENIG", "PIK DAME", "PIK BUBE", "PIK ZEHN", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                        "HERZ ASS", "HERZ KOENIG", "HERZ DAME", "HERZ BUBE", "HERZ ZEHN", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN",
                        "KARO ASS", "KARO KOENIG", "KARO DAME", "KARO BUBE", "KARO ZEHN", "KARO NEUN", "KARO ACHT", "KARO SIEBEN");
                break;
            case GRAND:
            case KREUZ:
                sortierReihenfolge.addAll(Arrays.asList(
                        "KREUZ ASS", "KREUZ ZEHN", "KREUZ KOENIG", "KREUZ DAME", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                        "PIK ASS", "PIK ZEHN", "PIK KOENIG", "PIK DAME", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                        "HERZ ASS", "HERZ ZEHN", "HERZ KOENIG", "HERZ DAME", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN",
                        "KARO ASS", "KARO ZEHN", "KARO KOENIG", "KARO DAME", "KARO NEUN", "KARO ACHT", "KARO SIEBEN"));
                break;
            case PIK:
                sortierReihenfolge.addAll(Arrays.asList(
                        "PIK ASS", "PIK ZEHN", "PIK KOENIG", "PIK DAME", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                        "KREUZ ASS", "KREUZ ZEHN", "KREUZ KOENIG", "KREUZ DAME", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                        "HERZ ASS", "HERZ ZEHN", "HERZ KOENIG", "HERZ DAME", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN",
                        "KARO ASS", "KARO ZEHN", "KARO KOENIG", "KARO DAME", "KARO NEUN", "KARO ACHT", "KARO SIEBEN"));
                break;
            case HERZ:
                sortierReihenfolge.addAll(Arrays.asList(
                        "HERZ ASS", "HERZ ZEHN", "HERZ KOENIG", "HERZ DAME", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN",
                        "KREUZ ASS", "KREUZ ZEHN", "KREUZ KOENIG", "KREUZ DAME", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                        "PIK ASS", "PIK ZEHN", "PIK KOENIG", "PIK DAME", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                        "KARO ASS", "KARO ZEHN", "KARO KOENIG", "KARO DAME", "KARO NEUN", "KARO ACHT", "KARO SIEBEN"));
                break;
            case KARO:
                sortierReihenfolge.addAll(Arrays.asList(
                        "KARO ASS", "KARO ZEHN", "KARO KOENIG", "KARO DAME", "KARO NEUN", "KARO ACHT", "KARO SIEBEN",
                        "KREUZ ASS", "KREUZ ZEHN", "KREUZ KOENIG", "KREUZ DAME", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                        "PIK ASS", "PIK ZEHN", "PIK KOENIG", "PIK DAME", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                        "HERZ ASS", "HERZ ZEHN", "HERZ KOENIG", "HERZ DAME", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN"));
                break;
        }

        // Map, um den Kartenwert als String eine Reihenfolge zuzuweisen
        Map<String, Integer> kartenReihenfolge = new HashMap<>();
        for (int i = 0; i < sortierReihenfolge.size(); i++) {
            kartenReihenfolge.put(sortierReihenfolge.get(i), i);
        }

        // Sortierung der Karten anhand der Reihenfolge in der Map
        List<Karte> decksortiert = new ArrayList<>(deck);
        decksortiert.sort((k1, k2) -> {
            // Vergleiche die Karten basierend auf der Reihenfolge in kartenReihenfolge
            String k1String = k1.getFarbe().toString() + " " + k1.getWert().toString();
            String k2String = k2.getFarbe().toString() + " " + k2.getWert().toString();
            return Integer.compare(kartenReihenfolge.get(k1String), kartenReihenfolge.get(k2String));
        });

        return decksortiert;
    }
}