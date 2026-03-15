package com.library.person;

import com.library.book.Book;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    private final List<Book> books;

    public Author(String name) {
        super(name);
        this.books = new ArrayList<>();
    }


    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() { return books; }

    public void showBooks() {
        System.out.println("  - " + getName() + " — Kitapları:");
        if (books.isEmpty()) {
            System.out.println("  Henüz kayıtlı kitap yok.");
        } else {
            books.forEach(Book::display);
        }
    }

    @Override
    public String whoYouAre() { return "Yazar"; }
}
