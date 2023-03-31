package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.Item;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class CartCreateRequest {
    @NotEmpty
    @JsonProperty("user")
    private String user;
    private Map<Item, Integer> items;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Item, Integer> items) {
        this.items = items;
    }
}
