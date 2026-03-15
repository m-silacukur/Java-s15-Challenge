package com.library.system;

import com.library.billing.Bill;
import com.library.book.Book;
import com.library.book.BookCategory;
import com.library.book.BookStatus;
import com.library.core.Searchable;
import com.library.member.MemberRecord;
import com.library.person.Author;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library implements Searchable<Book> {

    private final String libraryName;

    private final Map<String, Book>         books;
    private final Map<String, MemberRecord> members;
    private final Map<String, String>       lendingRecord;
    private final Map<String, Author>       authors;

    private int billCounter = 1;

    public Library(String libraryName) {
        this.libraryName   = libraryName;
        this.books         = new LinkedHashMap<>();
        this.members       = new LinkedHashMap<>();
        this.lendingRecord = new HashMap<>();
        this.authors       = new LinkedHashMap<>();
    }


    public boolean addBook(Book book) {
        if (books.containsKey(book.getBookId())) {
            System.out.println("  ✗ Bu ID'ye sahip kitap zaten mevcut: " + book.getBookId());
            return false;
        }
        books.put(book.getBookId(), book);
        System.out.println("  ✓ Kitap eklendi: " + book.getName());
        return true;
    }

    public Book getBook(String bookId) {
        return books.get(bookId);
    }

    public boolean removeBook(String bookId) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("  ✗ Kitap bulunamadı: " + bookId);
            return false;
        }
        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("  ✗ Ödünçteki kitap silinemez: " + book.getName());
            return false;
        }
        books.remove(bookId);
        System.out.println("  ✓ Kitap silindi: " + book.getName());
        return true;
    }

    public boolean updateBook(String bookId, String newName, String newAuthor,
                              double newPrice, int newEdition) {
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("  ✗ Kitap bulunamadı: " + bookId);
            return false;
        }
        if (newName   != null && !newName.isBlank())   book.setName(newName);
        if (newAuthor != null && !newAuthor.isBlank()) book.setAuthor(newAuthor);
        if (newPrice  > 0)                             book.setPrice(newPrice);
        if (newEdition > 0)                            book.setEdition(newEdition);
        System.out.println("  ✓ Kitap güncellendi: " + book.getName());
        return true;
    }

    public List<Book> getBooksByCategory(BookCategory category) {
        return books.values().stream()
                .filter(b -> b.getCategory() == category)
                .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(String authorName) {
        return books.values().stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("  Kütüphanede kayıtlı kitap bulunmamaktadır.");
            return;
        }
        System.out.println("  Toplam " + books.size() + " kitap:");
        books.values().forEach(Book::display);
    }


    @Override
    public List<Book> search(Predicate<Book> predicate) {
        return books.values().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }



    public boolean addMember(MemberRecord member) {
        if (members.containsKey(member.getMemberId())) {
            System.out.println("  ✗ Bu ID'ye sahip üye zaten mevcut: " + member.getMemberId());
            return false;
        }
        members.put(member.getMemberId(), member);
        System.out.println("  ✓ Üye eklendi: " + member.getName());
        return true;
    }

    public MemberRecord getMember(String memberId) {
        return members.get(memberId);
    }

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("  Kayıtlı üye bulunmamaktadır.");
            return;
        }
        System.out.println("  Toplam " + members.size() + " üye:");
        members.values().forEach(MemberRecord::display);
    }


    public void addAuthor(Author author) {
        authors.put(author.getName(), author);
    }

    public Author getAuthorByName(String name) {
        return authors.get(name);
    }


    public Bill lendBook(String memberId, String bookId) {
        MemberRecord member = members.get(memberId);
        Book         book   = books.get(bookId);

        if (member == null) {
            System.out.println("  ✗ Üye bulunamadı: " + memberId);
            return null;
        }
        if (book == null) {
            System.out.println("  ✗ Kitap bulunamadı: " + bookId);
            return null;
        }
        if (book.getStatus() == BookStatus.BORROWED) {
            System.out.println("  ✗  Bu kitap şu an başka bir üyede: " + book.getName());
            String borrowerId = lendingRecord.get(bookId);
            MemberRecord borrower = members.get(borrowerId);
            if (borrower != null)
                System.out.println("     → Kullanan: " + borrower.getName());
            return null;
        }
        if (!member.canBorrowMore()) {
            System.out.printf("  ✗ %s, %d kitap limitine ulaştı!%n",
                    member.getName(), member.getMaxBookLimit());
            return null;
        }

        book.updateStatus(BookStatus.BORROWED);
        member.increaseBookCount(bookId);
        lendingRecord.put(bookId, memberId);

        Bill bill = new Bill.Builder()
                .billId("FATURA-" + String.format("%04d", billCounter++))
                .memberId(memberId)
                .memberName(member.getName())
                .bookId(bookId)
                .bookName(book.getName())
                .amount(book.getPrice())
                .date(LocalDate.now().toString())
                .type(Bill.BillType.BORROW)
                .build();

        System.out.println("  ✓ Ödünç verildi: " + book.getName()
                + "  →  " + member.getName());
        bill.display();
        return bill;
    }

    public Bill takeBackBook(String memberId, String bookId) {
        MemberRecord member = members.get(memberId);
        Book         book   = books.get(bookId);

        if (member == null || book == null) {
            System.out.println("  ✗ Üye veya kitap bulunamadı.");
            return null;
        }
        if (!member.hasBorrowed(bookId)) {
            System.out.println("  ✗ Bu kitap bu üyeye kayıtlı değil.");
            return null;
        }

        book.updateStatus(BookStatus.AVAILABLE);
        member.decreaseBookCount(bookId);
        lendingRecord.remove(bookId);

        Bill bill = new Bill.Builder()
                .billId("FATURA-" + String.format("%04d", billCounter++))
                .memberId(memberId)
                .memberName(member.getName())
                .bookId(bookId)
                .bookName(book.getName())
                .amount(book.getPrice())
                .date(LocalDate.now().toString())
                .type(Bill.BillType.RETURN_REFUND)
                .build();

        System.out.println("  ✓ İade alındı: " + book.getName()
                + "  ←  " + member.getName());
        bill.display();
        return bill;
    }

    public void displayLendingRecord() {
        if (lendingRecord.isEmpty()) {
            System.out.println("  Şu anda ödünç verilen kitap bulunmamaktadır.");
            return;
        }
        System.out.println(" Ödünç Verilen Kitap Kayıtları:");
        lendingRecord.forEach((bId, mId) -> {
            Book         b = books.get(bId);
            MemberRecord m = members.get(mId);
            System.out.printf("   %-30s  →   %s%n",
                    (b != null ? b.getName() : bId),
                    (m != null ? m.getName() : mId));
        });
    }


    public String                    getLibraryName()   { return libraryName; }
    public Map<String, Book>         getBooks()         { return books; }
    public Map<String, MemberRecord> getMembers()       { return members; }
    public Map<String, String>       getLendingRecord() { return lendingRecord; }
    public Map<String, Author>       getAuthors()       { return authors; }
}