package org.example;

import org.example.database.DBHelper;
import org.example.game_selection.GameSelection;
import org.example.logic.Farbe;
import org.example.logic.Karte;
import org.example.logic.Kartenart;
import org.example.logic.Mischen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameSelection gameSelection = new GameSelection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);

        DBHelper helper = new DBHelper();
        helper.connect();
        //helper.createTable(timestamp, "Tahir", "Tobias", "Arne");
    }
}