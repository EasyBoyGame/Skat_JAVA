package org.example.database;

import java.sql.*;

/**
 * Dies ist ein DBHelper
 * @param
 */
public class DBHelper {
    private final String dbURL = "jdbc:sqlite:src/main/java/org/example/database/results.db";

    // useless method (checks if a connection to the db is possible)
    public void connect(){
        try(Connection con = DriverManager.getConnection(dbURL)){
            System.out.println("Connection established!");
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    // gets all tables (all games played) from db
    public void getTables(){
        String sql = "SELECT name FROM sqlite_master WHERE type='table'";
        try(Connection con = DriverManager.getConnection(dbURL);
        PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    // creates the table when hosted game gets started, to count points
    public void createTable(String tablename, String username1, String username2, String username3){
        String sql = "CREATE TABLE \"" + tablename + "\" (" +
                "ID INT PRIMARY KEY NOT NULL, " +
                "\"" + username1 + "\" INT NOT NULL DEFAULT 0, " + // "\" wird genutzt, um den Einschub des Strings als Teil des sql-String zu machen
                "\"" + username2 + "\" INT NOT NULL DEFAULT 0, " +
                "\"" + username3 + "\" INT NOT NULL DEFAULT 0)";


        try (Connection con = DriverManager.getConnection(dbURL);
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.execute();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    // inserts/adds the results of every round
    public void setRoundResults(String tablename, int result1, int result2, int result3){
        // Null als Wert, da so AutoIncrement in SQLite implementiert werden kann
        String sql = "INSERT INTO \"" + tablename + "\" VALUES (NULL, ?,?,?)";

        try (Connection con = DriverManager.getConnection(dbURL);
        PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, result1);
            ps.setInt(2, result2);
            ps.setInt(3, result3);

            ps.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }


    // reading every line of database and counting points
    public int[] getGameResult(String tablename){
        String sql = "SELECT * FROM \"" + tablename + "\"";
        int resultU1 = 0;
        int resultU2 = 0;
        int resultU3 = 0;

        try (Connection con = DriverManager.getConnection(dbURL);
        PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                resultU1 += rs.getInt(2);
                resultU2 += rs.getInt(3);
                resultU3 += rs.getInt(4);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return new int[]{resultU1, resultU2, resultU3};
    }
}


