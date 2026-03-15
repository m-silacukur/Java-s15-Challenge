package com.library.book;

public class Journal extends Book {

    private Journal(Builder builder) {
        super(builder);
    }

    @Override
    public BookCategory getCategory() { return BookCategory.JOURNAL; }

    @Override
    public String getCategoryName() { return "Dergi / Journal"; }

    public static class Builder extends Book.Builder<Builder> {
        @Override
        public Journal build() { return new Journal(this); }
    }
}
