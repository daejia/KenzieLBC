package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.google.common.base.Objects;
import com.kenzie.appserver.service.model.BrandType;
import com.kenzie.appserver.service.model.Category;
import com.kenzie.appserver.service.model.Store;

public class ItemRecord {
    private String id;
    private Store store;
    private String name;
    private BrandType brandType;
    private Category category;
    private double price;
    private boolean isInStock;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBRangeKey(attributeName = "Store")
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "BrandType")
    public BrandType getBrandType() {
        return brandType;
    }

    public void setBrandType(BrandType brandType) {
        this.brandType = brandType;
    }

    @DynamoDBAttribute(attributeName = "Category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @DynamoDBAttribute(attributeName = "Price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @DynamoDBAttribute(attributeName = "IsInStock")
    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRecord that = (ItemRecord) o;
        return Double.compare(that.price, price) == 0 && isInStock == that.isInStock && Objects.equal(id, that.id) && Objects.equal(store, that.store) && Objects.equal(name, that.name) && brandType == that.brandType && Objects.equal(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, store, name, brandType, category, price, isInStock);
    }
}
