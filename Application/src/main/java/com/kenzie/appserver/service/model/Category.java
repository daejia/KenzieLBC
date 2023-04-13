package com.kenzie.appserver.service.model;

public enum Category {
    HEALTH_AND_BEAUTY ("Health and Beauty"),
    HOME_AND_KITCHEN ("Home and Kitchen"),
    PET_SUPPLIES ("Pet Supplies"),
    CLEANING_SUPPLIES ("Cleaning Supplies"),
    BABY ("Baby"),
    ELECTRONIC ("Electronic"),
    FROZEN_FOOD ("Frozen Food"),
    DAIRY ("Dairy"),
    BAKERY ("Bakery"),
    MEAT ("Meat"),
    SEAFOOD ("Seafood"),
    CANNED_GOODS ("Canned Goods"),
    POULTRY ("Poultry"),
    PASTA ("Pasta"),
    PRODUCE ("Produce"),
    BEVERAGES ("Beverages"),
    OTHER ("Other");

    final String label;

    private Category(String label) {
        this.label = label;
    }
}
