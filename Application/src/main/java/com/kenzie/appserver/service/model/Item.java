package com.kenzie.appserver.service.model;

public class Item {
    private final String id;
    private final Store store;
    private final BrandType brandType;
    private final String name;
    private final Category category;
    private final double price;
    private final boolean isInStock;

    public Item(String id, Store store, BrandType brandType, String name, Category category, double price, boolean isInStock) {
        this.id = id;
        this.store = store;
        this.brandType = brandType;
        this.name = name;
        this.category = category;
        this.price = price;
        this.isInStock = isInStock;
    }

    public String getId() {
        return id;
    }

    public Store getStore() {
        return store;
    }

    public BrandType getBrandType() {
        return brandType;
    }

    public String getName() {
        return name;
    }
    public Category getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }

    public boolean getIsInStock() {return isInStock;
    }
}
