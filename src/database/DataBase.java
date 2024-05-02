package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

    private Connection con;

    public DataBase(String username, String password) {
        try {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Hospital";
            con = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTED");
        } catch (SQLException ex) {
            System.out.println("Error CONNECTION: " + ex.getMessage());
        }
    }

    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR closing connection: " + ex.getMessage());
        }
    }

    // Method to fetch all records from a table
    public ResultSet searchRecords(String table) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table);
            return ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
            return null;
        }
    }

    // Method to insert a record into a table
    public boolean insertRecord(String table, String values) {
        String sql = "INSERT INTO " + table + " VALUES (" + values + ")";
        return executeNonQuery(sql);
    }

    // Method to update a record in a table
    public boolean updateRecord(String table, String set, String condition) {
        String sql = "UPDATE " + table + " SET " + set + " WHERE " + condition;
        return executeNonQuery(sql);
    }

    // Method to delete a record from a table
    public boolean deleteRecord(String table, String condition) {
        String sql = "DELETE FROM " + table + " WHERE " + condition;
        return executeNonQuery(sql);
    }

    // General method to execute non-query SQL commands
    public boolean executeNonQuery(String sql) {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Execution error: " + e.getMessage());
            return false;
        }
    }
}




