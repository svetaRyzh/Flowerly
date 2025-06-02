package com.example.flowerly;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private List<Bouquet> items = new ArrayList<>();

    private Cart() {}

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void addItem(Bouquet bouquet) {
        items.add(bouquet);
    }

    public void removeItem(Bouquet bouquet) {
        items.remove(bouquet);
    }

    public List<Bouquet> getItems() {
        return new ArrayList<>(items);
    }

    public int getTotalPrice() {
        int total = 0;
        for (Bouquet item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }
}
