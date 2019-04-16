package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public Inventory() {}

    private static ObservableList<Product> products = FXCollections.observableArrayList();
    public static ObservableList<Product> getProducts() { return products; }
    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    public static ObservableList<Part> getParts() { return parts; }
    public static void addPart(Part part) { parts.add(part); }
    public static void addProduct(Product product) { products.add(product); }
    public static void removePart(Part part) { parts.remove(part); }
    public static void removeProduct(Product product) { products.remove(product); }
    public static void updatePart(int pin, Part part) {
        parts.set(pin, part);
    }
    public static void updateProduct(int pin, Product product) { products.set(pin, product); }

    private static boolean integer(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean partDelete(Part part) {
        boolean find = false;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductParts().contains(part)) {
                find = true;
            }
        }
        return find;
    }

    public static int lookupPart(String search) {
        boolean find = false;
        int pin = 0;
        if (integer(search)) {
            for (int i = 0; i < parts.size(); i++) {
                if (Integer.parseInt(search) == parts.get(i).getPartID()) {
                    pin = i;
                    find = true;
                }
            }
        } else {
            for (int i = 0; i < parts.size(); i++) {
                if (search.equals(parts.get(i).getPartName())) {
                    pin = i;
                    find = true;
                }
            }
        }
        if (find) {
            return pin;
        } else {
            return -1;
        }
    }

    public static int lookupProduct(String search) {
        boolean find = false;
        int pin = 0;
        if (integer(search)) {
            for (int i = 0; i < products.size(); i++) {
                if (Integer.parseInt(search) == products.get(i).getProductID()) {
                    pin = i;
                    find = true;
                }
            }
        }
        else {
            for (int i = 0; i < products.size(); i++) {
                if (search.equals(products.get(i).getProductName())) {
                    pin = i;
                    find = true; }
            }
        }
        if (find) {
            return pin;
        } else {
            return -1;
        }
    }
}