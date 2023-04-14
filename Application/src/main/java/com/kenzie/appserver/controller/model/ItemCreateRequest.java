package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.BrandType;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.Store;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class ItemCreateRequest {
    @NotEmpty
    @JsonProperty("name")
    private String name;
    private Store store;
    private BrandType brandType;
    private Category category;
    @Min(0)
    private double price;
    private boolean isInStock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public BrandType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandType brandType) {
        this.brandType = brandType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsInStock() {
        return isInStock;
    }

    public void setIsInStock(boolean isInStock) {
        this.isInStock = isInStock;
    }
}
