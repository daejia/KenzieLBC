package com.kenzie.appserver.service.model;

import java.util.Map;

public class Cart {
    private final String id;
    private final String user;
    private final Map<String,Item> items;
    private final Boolean isInStock;

    public Cart(String id, String user, Map<String, Item> items, Boolean isInStock) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.isInStock = isInStock;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Map<String,Item> getItems() {
        return items;
    }

    public Boolean getIsInStock(boolean b) {
        return isInStock;
    }
}
