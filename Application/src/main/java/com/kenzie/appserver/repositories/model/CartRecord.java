package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.google.common.base.Objects;
import com.kenzie.appserver.service.model.Item;

import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName = "Cart")
public class CartRecord {
    private String id;
    private String user;
    private Map<Item,Integer> items;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "User")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @DynamoDBAttribute(attributeName = "Items")
    public Map<Item,Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item,Integer> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartRecord that = (CartRecord) o;
        return Objects.equal(id, that.id) && Objects.equal(user, that.user) && Objects.equal(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, user, items);
    }
}
