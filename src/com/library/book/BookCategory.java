package com.library.book;

public enum BookCategory {
    STUDY_BOOK("Ders Kitabı"),
    JOURNAL("Dergi / Journal"),
    MAGAZINE("Magazin");

    private final String displayName;

    BookCategory(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
