package com.library.book;

public class StudyBook extends Book {

    private StudyBook(Builder builder) {
        super(builder);
    }

    @Override
    public BookCategory getCategory() { return BookCategory.STUDY_BOOK; }

    @Override
    public String getCategoryName() { return "Ders Kitabı"; }

    public static class Builder extends Book.Builder<Builder> {
        @Override
        public StudyBook build() { return new StudyBook(this); }
    }
}
