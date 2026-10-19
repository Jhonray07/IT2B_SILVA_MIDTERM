package com.scc;

import com.scc.dao.FoodItemDao;
import com.scc.ui.CanteenMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FoodItemDao foodItemDao = new FoodItemDao();
        Scanner scanner = new Scanner(System.in);

        CanteenMenu canteenMenu = new CanteenMenu(foodItemDao, scanner);
        canteenMenu.display();

        scanner.close();
    }
}