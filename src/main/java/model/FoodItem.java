package com.scc.model;

// One row of the food_items table.
public record FoodItem(int id, String itemName, String category, double price, int stock) {
}