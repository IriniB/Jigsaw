package com.example.hw5.Controllers;

import com.example.hw5.Model.LiderboardRow;

import java.sql.*;
import java.util.ArrayList;

public class DbController {

    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://localhost/postgres";
            String user = "postgres";
            String password = "1326";
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public ArrayList<LiderboardRow> getLeaderboard(){
        Connection conn = connect();
        ArrayList<LiderboardRow> result = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM results ORDER BY moves DESC, seconds LIMIT 5");
            while (rs.next()) {
                String name = rs.getString("name");
                long moves = rs.getLong("moves");
                long seconds = rs.getLong("seconds");
                result.add(new LiderboardRow(name, moves, seconds));
            }
            conn.close();
        } catch (SQLException exception) {
            System.out.println(exception.toString());
        }
        return result;
    }

    public void insertResult(String name, long moves, long seconds) {
        System.out.println("111111111111111111");
        String SQL = "INSERT INTO results(name,moves, seconds) "
                + "VALUES(?,?,?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setLong(2, moves);
            pstmt.setLong(3, seconds);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
