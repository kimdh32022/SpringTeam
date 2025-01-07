package com.busanit501.bootproject.domain;

public enum Category {
    FreeBoard,
    EmergencyHospital,
    UsedItems,
    WalkRequest;
    public static Category fromString(String category) {
        for (Category c : Category.values()) {
            if (c.name().equalsIgnoreCase(category)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Unknown category: " + category);
    }
}