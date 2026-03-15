package com.library.billing;

public class Bill {

    public enum BillType {
        BORROW("Ödünç Alma"),
        RETURN_REFUND("İade / Geri Ödeme");

        private final String label;
        BillType(String label) { this.label = label; }
        @Override public String toString() { return label; }
    }

    private final String   billId;
    private final String   memberId;
    private final String   memberName;
    private final String   bookId;
    private final String   bookName;
    private final double   amount;
    private final String   date;
    private final BillType type;

    private Bill(Builder builder) {
        this.billId     = builder.billId;
        this.memberId   = builder.memberId;
        this.memberName = builder.memberName;
        this.bookId     = builder.bookId;
        this.bookName   = builder.bookName;
        this.amount     = builder.amount;
        this.date       = builder.date;
        this.type       = builder.type;
    }


    public void display() {

        System.out.printf( "  -  Fatura No  : %-23s║%n", billId);
        System.out.printf( "  -  Üye ID     : %-23s║%n", memberId);
        System.out.printf( "  -  Üye Adı    : %-23s║%n", memberName);
        System.out.printf( "  -  Kitap ID   : %-23s║%n", bookId);
        System.out.printf( "  -  Kitap Adı  : %-23s║%n", bookName);
        System.out.printf( "  -  İşlem      : %-23s║%n", type);
        System.out.printf( "  -  Tutar      : %-19.2f TL ║%n", amount);
        System.out.printf( "  -  Tarih      : %-23s║%n", date);
    }

    public String   getBillId()     { return billId; }
    public String   getMemberId()   { return memberId; }
    public String   getMemberName() { return memberName; }
    public String   getBookId()     { return bookId; }
    public String   getBookName()   { return bookName; }
    public double   getAmount()     { return amount; }
    public String   getDate()       { return date; }
    public BillType getType()       { return type; }

    public static class Builder {
        private String   billId;
        private String   memberId;
        private String   memberName;
        private String   bookId;
        private String   bookName;
        private double   amount;
        private String   date;
        private BillType type;

        public Builder billId(String billId)         { this.billId = billId;         return this; }
        public Builder memberId(String memberId)     { this.memberId = memberId;     return this; }
        public Builder memberName(String memberName) { this.memberName = memberName; return this; }
        public Builder bookId(String bookId)         { this.bookId = bookId;         return this; }
        public Builder bookName(String bookName)     { this.bookName = bookName;     return this; }
        public Builder amount(double amount)         { this.amount = amount;         return this; }
        public Builder date(String date)             { this.date = date;             return this; }
        public Builder type(BillType type)           { this.type = type;             return this; }

        public Bill build() { return new Bill(this); }
    }
}
