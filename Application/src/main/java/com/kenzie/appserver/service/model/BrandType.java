package com.kenzie.appserver.service.model;

public enum BrandType {
    GENERIC("Generic"),
    NAME_BRAND("Name Brand");

    final String label;

    private BrandType(String label) {
        this.label = label;
    }
}
