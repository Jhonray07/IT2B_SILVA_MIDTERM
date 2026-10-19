package com.scc.ui;

import com.scc.dao.FoodItemDao;
import com.scc.model.FoodItem;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CanteenMenu {
    private static final String LINE = "=====================================================";
    private static final String WIDE_LINE =
            "=====================================================================";
    private static final String TABLE_BORDER =
            "+----+--------------------+------------+--------------+----------------+";

    private final FoodItemDao foodItemDao;
    private final Scanner scanner;

    public CanteenMenu(FoodItemDao foodItemDao, Scanner scanner) {
        this.foodItemDao = foodItemDao;
        this.scanner = scanner;
    }

    public void display() {
        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println(LINE);
            System.out.println("          CANTEEN INVENTORY MANAGEMENT");
            System.out.println(LINE);
            System.out.println("[1] Add New Food Item");
            System.out.println("[2] View Canteen Inventory");
            System.out.println("[3] Exit System");
            System.out.println(LINE);
            System.out.print("Enter choice: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> addFoodItem();
                case "2" -> viewInventory();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nInvalid choice. Please enter 1, 2 or 3.");
            }
        }
    }

    // ---------- [1] Add New Food Item ----------
    private void addFoodItem() {
        System.out.println("\n--- ADD NEW FOOD ITEM ---");

        String name = readItemName();
        String category = readCategory();
        double price = readPrice();
        int stock = readStock();

        if (foodItemDao.addFoodItem(name, category, price, stock)) {
            System.out.println("\nSUCCESS: Food item added to inventory!");
        } else {
            System.out.println("\nERROR: Could not save the food item.");
        }
    }

    private String readItemName() {
        while (true) {
            System.out.print("Enter Item Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("ERROR: Item name cannot be empty.");
            } else if (name.length() > 18) {
                System.out.println("ERROR: Item name must be 18 characters or less.");
            } else {
                return name;
            }
        }
    }

    private String readCategory() {
        while (true) {
            System.out.print("Enter Category (MEAL / SNACK / BEVERAGE): ");
            String category = scanner.nextLine().trim().toUpperCase();
            if (category.equals("MEAL") || category.equals("SNACK") || category.equals("BEVERAGE")) {
                return category;
            }
            System.out.println("ERROR: Category must be MEAL, SNACK or BEVERAGE.");
        }
    }

    private double readPrice() {
        while (true) {
            System.out.print("Enter Price: ");
            String text = scanner.nextLine().trim();
            try {
                double price = Double.parseDouble(text);
                if (price < 0) {
                    System.out.println("ERROR: Price cannot be negative.");
                } else {
                    return price;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a valid number (e.g., 65.00).");
            }
        }
    }

    private int readStock() {
        while (true) {
            System.out.print("Enter Stock Quantity: ");
            String text = scanner.nextLine().trim();
            try {
                int stock = Integer.parseInt(text);
                if (stock < 0) {
                    System.out.println("ERROR: Stock cannot be negative.");
                } else {
                    return stock;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Please enter a whole number (e.g., 50).");
            }
        }
    }

    // ---------- [2] View Canteen Inventory ----------
    private void viewInventory() {
        List<FoodItem> items = foodItemDao.getAllFoodItems();

        System.out.println();
        System.out.println(WIDE_LINE);
        System.out.println("                         CANTEEN FOOD INVENTORY");
        System.out.println(WIDE_LINE);

        if (items.isEmpty()) {
            System.out.println("No food items in inventory yet.");
            System.out.println("Total Records Displayed: 0");
            return;
        }

        System.out.println(TABLE_BORDER);
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                "ID", "ITEM NAME", "CATEGORY", "PRICE", "STOCK");
        System.out.println(TABLE_BORDER);

        for (FoodItem item : items) {
            System.out.printf(Locale.US, "| %-2d | %-18s | %-10s | %-12s | %-14d |%n",
                    item.id(),
                    item.itemName(),
                    item.category(),
                    String.format(Locale.US, "%.2f", item.price()),
                    item.stock());
        }

        System.out.println(TABLE_BORDER);
        System.out.println("Total Records Displayed: " + items.size());
    }
}