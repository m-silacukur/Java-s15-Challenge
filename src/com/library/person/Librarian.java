package com.library.person;

import com.library.billing.Bill;
import com.library.book.Book;
import com.library.member.MemberRecord;
import com.library.system.Library;

import java.util.List;
import java.util.function.Predicate;

public class Librarian extends Person {

    private final String password;
    private final Library library;

    public Librarian(String name, String password, Library library) {
        super(name);
        this.password = password;
        this.library  = library;
    }


    public List<Book> searchBook(Predicate<Book> predicate) {
        return library.search(predicate);
    }

    public boolean verifyMember(String memberId) {
        return library.getMember(memberId) != null;
    }

    public Bill issueBook(String memberId, String bookId) {
        return library.lendBook(memberId, bookId);
    }

    public boolean returnBook(String memberId, String bookId) {
        return library.takeBackBook(memberId, bookId) != null;
    }

    public double calculateFine(String memberId) {
        MemberRecord member = library.getMember(memberId);
        if (member == null) return 0.0;

        return 0.0;
    }

    public Library getLibrary() { return library; }

    @Override
    public String whoYouAre() { return "Kütüphane Sorumlusu"; }
}
