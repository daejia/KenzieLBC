package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.appserver.service.model.Item;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

public class CartCreateRequest {
    @NotEmpty
    @JsonProperty("user")
    private String user;
    private Map<String, Item> items;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void setItems(Map<String, Item> items) {
        this.items = items;
    }
}
