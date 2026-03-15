package com.library.book;

import com.library.core.Displayable;

public abstract class Book implements Displayable {

    private final String bookId;
    private String author;
    private String name;
    private double price;
    private BookStatus status;
    private int edition;
    private String dateOfPurchase;


    protected Book(Builder<?> builder) {
        this.bookId        = builder.bookId;
        this.author        = builder.author;
        this.name          = builder.name;
        this.price         = builder.price;
        this.status        = builder.status;
        this.edition       = builder.edition;
        this.dateOfPurchase = builder.dateOfPurchase;
    }


    public abstract BookCategory getCategory();
    public abstract String getCategoryName();


    public String getBookId()        { return bookId; }
    public String getAuthor()        { return author; }
    public String getName()          { return name; }
    public double getPrice()         { return price; }
    public BookStatus getStatus()    { return status; }
    public int getEdition()          { return edition; }
    public String getDateOfPurchase(){ return dateOfPurchase; }
    public String getTitle()         { return name; }


    public void setAuthor(String author)   { this.author = author; }
    public void setName(String name)       { this.name = name; }
    public void setPrice(double price)     { this.price = price; }
    public void setEdition(int edition)    { this.edition = edition; }

    public void updateStatus(BookStatus status) { this.status = status; }


    @Override
    public void display() {

        System.out.printf( "  - ID       : %-26s│%n", bookId);
        System.out.printf( "  - İsim     : %-26s│%n", name);
        System.out.printf( "  - Yazar    : %-26s│%n", author);
        System.out.printf( "  - Kategori : %-26s│%n", getCategoryName());
        System.out.printf( "  - Fiyat    : %-23.2f TL│%n", price);
        System.out.printf( "  - Durum    : %-26s│%n", status);
        System.out.printf( "  - Baskı    : %-26d│%n", edition);

    }

    public abstract static class Builder<T extends Builder<T>> {
        private String bookId        = "UNKNOWN";
        private String author        = "Bilinmiyor";
        private String name          = "Bilinmiyor";
        private double price         = 0.0;
        private BookStatus status    = BookStatus.AVAILABLE;
        private int edition          = 1;
        private String dateOfPurchase = "Belirtilmemiş";

        @SuppressWarnings("unchecked")
        public T bookId(String bookId)              { this.bookId = bookId;               return (T) this; }
        @SuppressWarnings("unchecked")
        public T author(String author)              { this.author = author;               return (T) this; }
        @SuppressWarnings("unchecked")
        public T name(String name)                  { this.name = name;                   return (T) this; }
        @SuppressWarnings("unchecked")
        public T price(double price)                { this.price = price;                 return (T) this; }
        @SuppressWarnings("unchecked")
        public T status(BookStatus status)          { this.status = status;               return (T) this; }
        @SuppressWarnings("unchecked")
        public T edition(int edition)               { this.edition = edition;             return (T) this; }
        @SuppressWarnings("unchecked")
        public T dateOfPurchase(String date)        { this.dateOfPurchase = date;         return (T) this; }

        public abstract Book build();
    }
}
