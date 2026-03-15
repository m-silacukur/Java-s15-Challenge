package com.library.book;

public enum BookStatus {
    AVAILABLE("Mevcut"),
    BORROWED("Ödünç Verildi"),
    RESERVED("Rezerve");

    private final String displayName;

    BookStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
