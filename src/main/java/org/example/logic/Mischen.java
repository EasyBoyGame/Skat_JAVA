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
        }
        return null;
    }

    public List<Karte> kartenErstellen() {
        List<Karte> deck = new ArrayList<>();
        for (Farbe farbe : Farbe.values()) {
            for (Kartenart kartenart : Kartenart.values()) {
                deck.add(new Karte(farbe, kartenart));
            }
        }
        Collections.shuffle(deck);

        kartenSp1 = kartenSortieren(deck.subList(0 ,10));
        kartenSp2 = kartenSortieren(deck.subList(10 ,20));
        kartenSp3 = kartenSortieren(deck.subList(20 ,30));
        skat = kartenSortieren(deck.subList(30, 32));
        System.out.println(skat);
        return deck;
    }

    private static List<Karte> kartenSortieren(List<Karte> deck) {

        List<String> sortierReihenfolge = Arrays.asList(
                "KREUZ BUBE", "PIK BUBE", "HERZ BUBE", "KARO BUBE",
                "KREUZ ASS", "KREUZ ZEHN", "KREUZ KOENIG", "KREUZ DAME", "KREUZ NEUN", "KREUZ ACHT", "KREUZ SIEBEN",
                "PIK ASS", "PIK ZEHN", "PIK KOENIG", "PIK DAME", "PIK NEUN", "PIK ACHT", "PIK SIEBEN",
                "HERZ ASS", "HERZ ZEHN", "HERZ KOENIG", "HERZ DAME", "HERZ NEUN", "HERZ ACHT", "HERZ SIEBEN",
                "KARO ASS", "KARO ZEHN", "KARO KOENIG", "KARO DAME", "KARO NEUN", "KARO ACHT", "KARO SIEBEN"
        ); // Reihenfolge der Kartenwerte und Farben


        Map<String, Integer> kartenReihenfolge = new HashMap<>();
        for (int i = 0; i < sortierReihenfolge.size(); i++) {
            kartenReihenfolge.put(sortierReihenfolge.get(i), i);
        } //Map, um den Kartenwert als String eine Reihenfolge zuzuweisen


        List<Karte> decksortiert = new ArrayList<>(deck);
        Collections.sort(decksortiert, (k1, k2) -> {
            // Vergleiche die Karten basierend auf der Reihenfolge in kartenReihenfolge
            String k1String = k1.getFarbe().toString() + " " + k1.getWert().toString();
            String k2String = k2.getFarbe().toString() + " " + k2.getWert().toString();
            return Integer.compare(kartenReihenfolge.get(k1String), kartenReihenfolge.get(k2String));
        }); // Sortierung der Karten anhand der Reihenfolge in der Map

        return decksortiert;
    }
}
