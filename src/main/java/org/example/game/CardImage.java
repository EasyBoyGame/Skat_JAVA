package org.example.game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardImage {
    Map<String, String> bilder = new HashMap<>();
    public CardImage(){


        bilder.put("KARO SIEBEN", "src/main/resources/deck/7D.png");
        bilder.put("KARO ACHT", "src/main/resources/deck/8D.png");
        bilder.put("KARO NEUN", "src/main/resources/deck/9D.png");
        bilder.put("KARO ZEHN", "src/main/resources/deck/0D.png");
        bilder.put("KARO BUBE", "src/main/resources/deck/JD.png");
        bilder.put("KARO DAME", "src/main/resources/deck/QD.png");
        bilder.put("KARO KOENIG", "src/main/resources/deck/KD.png");
        bilder.put("KARO ASS", "src/main/resources/deck/AD.png");

        bilder.put("HERZ SIEBEN", "src/main/resources/deck/7H.png");
        bilder.put("HERZ ACHT", "src/main/resources/deck/8H.png");
        bilder.put("HERZ NEUN", "src/main/resources/deck/9H.png");
        bilder.put("HERZ ZEHN", "src/main/resources/deck/0H.png");
        bilder.put("HERZ BUBE", "src/main/resources/deck/JH.png");
        bilder.put("HERZ DAME", "src/main/resources/deck/QH.png");
        bilder.put("HERZ KOENIG", "src/main/resources/deck/KH.png");
        bilder.put("HERZ ASS", "src/main/resources/deck/AH.png");

        bilder.put("PIK SIEBEN", "src/main/resources/deck/7S.png");
        bilder.put("PIK ACHT", "src/main/resources/deck/8S.png");
        bilder.put("PIK NEUN", "src/main/resources/deck/9S.png");
        bilder.put("PIK ZEHN", "src/main/resources/deck/0S.png");
        bilder.put("PIK BUBE", "src/main/resources/deck/JS.png");
        bilder.put("PIK DAME", "src/main/resources/deck/QS.png");
        bilder.put("PIK KOENIG", "src/main/resources/deck/KS.png");
        bilder.put("PIK ASS", "src/main/resources/deck/AS.png");

        bilder.put("KREUZ SIEBEN", "src/main/resources/deck/7C.png");
        bilder.put("KREUZ ACHT", "src/main/resources/deck/8C.png");
        bilder.put("KREUZ NEUN", "src/main/resources/deck/9C.png");
        bilder.put("KREUZ ZEHN", "src/main/resources/deck/0C.png");
        bilder.put("KREUZ BUBE", "src/main/resources/deck/JC.png");
        bilder.put("KREUZ DAME", "src/main/resources/deck/QC.png");
        bilder.put("KREUZ KOENIG", "src/main/resources/deck/KC.png");
        bilder.put("KREUZ ASS", "src/main/resources/deck/AC.png");

    }


    public ImageIcon loadImageFromFile(String filePath, int width, int height) {
        try {
            ImageIcon icon = new ImageIcon(filePath); // Lade Bild von lokalem Pfad
            Image image = icon.getImage().getScaledInstance(93, 141, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Falls das Bild nicht geladen werden kann
        }
    }
}
