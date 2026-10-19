package com.scc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String DB_URL = "jdbc:sqlite:SchoolCanteen.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
        }
        return conn;
    }

    // Creates the food_items table if it does not exist yet.
    // AUTOINCREMENT makes the ID start at 1 and go up automatically.
    public static void initializeDatabase() {
        String foodItems = """
                CREATE TABLE IF NOT EXISTS food_items (
                    item_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    item_name TEXT NOT NULL,
                    category TEXT NOT NULL CHECK (category IN ('MEAL','SNACK','BEVERAGE')),
                    price REAL NOT NULL CHECK (price >= 0),
                    stock INTEGER NOT NULL CHECK (stock >= 0)
                )""";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(foodItems);
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}