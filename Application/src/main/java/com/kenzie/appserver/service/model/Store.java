package com.kenzie.appserver.service.model;

public class Store {
    private final String id;
    private final String name;
    private final String address;
    private final String city;
    private final String state;
    private final String zip;
//    private final boolean isInRadius;

    public Store(String id, String name, String address, String city, String state, String zip) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
//        this.isInRadius = isInRadius;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

//    public boolean isInRadius() {
//        return isInRadius;
//    }
}
