package org.example.logic;

import java.util.*;

public class Mischen {
    public static List<Karte> shuffle() {
        List<Karte> deck = kartenErstellen();

        Collections.shuffle(deck);

        List<Karte> spieler1 = new ArrayList<>(kartenSortieren(deck.subList(0, 10)));
        List<Karte> spieler2 = new ArrayList<>(kartenSortieren(deck.subList(10, 20)));
        List<Karte> spieler3 = new ArrayList<>(kartenSortieren(deck.subList(20, 30)));
        List<Karte> skat = new ArrayList<>(kartenSortieren(deck.subList(30, 32)));

        System.out.println("Spieler1:" + spieler1);
        System.out.println("Spieler2:" + spieler2);
        System.out.println("Spieler3:" + spieler3);
        System.out.println("Skat:" + skat);

        List<Karte> decksortiert = new ArrayList<>();
        decksortiert.addAll(spieler1);
        decksortiert.addAll(spieler2);
        decksortiert.addAll(spieler3);
        decksortiert.addAll(skat);

        return decksortiert;
    }

    private static List<Karte> kartenErstellen() {
        List<Karte> deck = new ArrayList<>();
        for (Farbe farbe : Farbe.values()) {
            for (Kartenart kartenart : Kartenart.values()) {
                deck.add(new Karte(farbe, kartenart));
            }
        }
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
