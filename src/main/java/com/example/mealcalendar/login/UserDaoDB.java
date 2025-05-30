package com.example.mealcalendar.login;

import com.example.mealcalendar.Loader;

import java.sql.*;
import java.util.*;

public class UserDaoDB implements UserDaoInterface {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mealcalendar";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = System.getenv("DBPASS");

    @Override
    public boolean registerUser(UserEntity user) {
        if (getUserByUsername(user.getUsername()) != null) return false;

        String sql = Loader.getQuery("registerUser");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserEntity> getAllUsers() {
        List<UserEntity> users = new ArrayList<>();
        String sql = Loader.getQuery("getUserByUsername");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UserEntity user = new UserEntity(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        String sql = Loader.getQuery("getAllUsers");

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserEntity(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}