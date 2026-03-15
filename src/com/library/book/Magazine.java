package com.library.book;

public class Magazine extends Book {

    private Magazine(Builder builder) {
        super(builder);
    }

    @Override
    public BookCategory getCategory() { return BookCategory.MAGAZINE; }

    @Override
    public String getCategoryName() { return "Magazin"; }

    public static class Builder extends Book.Builder<Builder> {
        @Override
        public Magazine build() { return new Magazine(this); }
    }
}

