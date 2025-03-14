package org.example.database;

import java.sql.*;

/**
 * Dies ist ein DBHelper
 * @param
 */
public class DBHelper {
    private final String dbURL = "jdbc:sqlite:src/main/java/org/example/database/results.db";
    private String username1;
    private String username2;
    private String username3;

    // check if database is available
    public void connect(){
        try(Connection con = DriverManager.getConnection(dbURL)){
            System.out.println("Connection to database established!");
        }
        catch (SQLException ex){
            ex.printStackTrace();
            System.out.println("Connection to database could not be established!");
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
        this.username1 = username1;
        this.username2 = username3;
        this.username3 = username3;
        String sql = "CREATE TABLE \"" + tablename + "\" (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "\"" + username1 + "\" INTEGER NOT NULL DEFAULT 0, " + // "\" wird genutzt, um den Einschub des Strings als Teil des sql-String zu machen
                "\"" + username2 + "\" INTEGER NOT NULL DEFAULT 0, " +
                "\"" + username3 + "\" INTEGER NOT NULL DEFAULT 0)";

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
        String sql = "INSERT INTO \"" + tablename + "\" ("+ username1 + ", " + username2 + ", " + username3 + ") VALUES (?,?,?)";

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