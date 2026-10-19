package com.scc.dao;

import com.scc.model.FoodItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FoodItemDao {

    public FoodItemDao() {
        DatabaseConnection.initializeDatabase();
    }

    // Inserts a new food item. Returns true when the row was saved.
    public boolean addFoodItem(String itemName, String category, double price, int stock) {
        String sql = "INSERT INTO food_items(item_name, category, price, stock) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, itemName);
            pstmt.setString(2, category.toUpperCase());
            pstmt.setDouble(3, price);
            pstmt.setInt(4, stock);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to add item: " + e.getMessage());
            return false;
        }
    }

    // Returns every food item ordered by ID.
    public List<FoodItem> getAllFoodItems() {
        String sql = "SELECT item_id, item_name, category, price, stock FROM food_items ORDER BY item_id";
        List<FoodItem> items = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new FoodItem(
                        rs.getInt("item_id"),
                        rs.getString("item_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Failed to load inventory: " + e.getMessage());
        }
        return items;
    }
}