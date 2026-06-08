package com.bittercode.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class SessionCart implements Serializable {

    private final Map<String, Integer> items = new LinkedHashMap<>();

    public Map<String, Integer> getItems() {
        return items;
    }

    public void addItem(String bookId) {
        items.merge(bookId, 1, Integer::sum);
    }

    public void removeItem(String bookId) {
        Integer quantity = items.get(bookId);
        if (quantity == null) {
            return;
        }
        if (quantity > 1) {
            items.put(bookId, quantity - 1);
        } else {
            items.remove(bookId);
        }
    }

    public int getQuantity(String bookId) {
        return items.getOrDefault(bookId, 0);
    }

    public String toBookIdsCsv() {
        return String.join(",", items.keySet());
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
