package org.example;

import org.example.database.DBHelper;
import org.example.game_selection.GameSelection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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