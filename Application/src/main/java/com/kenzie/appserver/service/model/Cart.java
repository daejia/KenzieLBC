package com.kenzie.appserver.service.model;

import java.util.Map;

public class Cart {
    private final String id;
    private final String user;
    private final Map<Item,Integer> items;

    public Cart(String id, String user, Map<Item,Integer> items) {
        this.id = id;
        this.user = user;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Map<Item,Integer> getItems() {
        return items;
    }
}
