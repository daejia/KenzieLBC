package com.kenzie.appserver.repositories.model;

import com.google.common.base.Objects;

public class StoreRecord {
    private String id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String zip;
    private boolean isInRadius;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public boolean isInRadius() {
        return isInRadius;
    }

    public void setIsInRadius(boolean isInRadius) {
        this.isInRadius = isInRadius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreRecord that = (StoreRecord) o;
        return isInRadius == that.isInRadius && Objects.equal(id, that.id) && Objects.equal(name, that.name)
                && Objects.equal(address, that.address) && Objects.equal(city, that.city)
                && Objects.equal(state, that.state) && Objects.equal(zip, that.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, address, city, state, zip, isInRadius);
    }
}
