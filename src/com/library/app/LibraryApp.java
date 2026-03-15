package com.library.app;

import com.library.book.*;
import com.library.member.Faculty;
import com.library.member.MemberRecord;
import com.library.member.Student;
import com.library.person.Author;
import com.library.person.Librarian;
import com.library.system.Library;

import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static Library library;
    private static Librarian librarian;

    public static void main(String[] args) {
        initializeDemoData();
        printWelcome();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1  -> bookMenu();
                case 2  -> memberMenu();
                case 3  -> lendingMenu();
                case 4  -> searchMenu();
                case 5  -> { running = false; printBye(); }
                default -> System.out.println("  ✗ Geçersiz seçim.");
            }
        }
        scanner.close();
    }


    private static void initializeDemoData() {
        library   = new Library("Merkez Kütüphanesi");
        librarian = new Librarian("Arwen Kütüphaneci", "12345", library);

        Book b1 = new StudyBook.Builder()
                .bookId("KTP-001").name("IoT ile Akıllı Sistemler").author("Ahmet xxy")
                .price(120.0).edition(3).dateOfPurchase("2023-01-10").build();

        Book b2 = new StudyBook.Builder()
                .bookId("KTP-002").name("Gömülü Sistemler ve IoT")
                .author("Fatma xyx").price(95.0).edition(2)
                .dateOfPurchase("2022-06-15").build();

        Book b3 = new Journal.Builder()
                .bookId("KTP-003").name(" Teknolojik Dergi")
                .author("Ali xzyz").price(40.0).edition(1)
                .dateOfPurchase("2024-03-01").build();

        Book b4 = new Magazine.Builder()
                .bookId("KTP-004").name("Teknoloji Dünyası")
                .author("Editör xkyz").price(25.0).edition(55)
                .dateOfPurchase("2024-11-01").build();

        Book b5 = new StudyBook.Builder()
                .bookId("KTP-005").name("Big Data Big ve 5V ")
                .author("Alina xyz").price(150.0).edition(1)
                .dateOfPurchase("2015-09-20").build();

        library.getBooks().put(b1.getBookId(), b1);
        library.getBooks().put(b2.getBookId(), b2);
        library.getBooks().put(b3.getBookId(), b3);
        library.getBooks().put(b4.getBookId(), b4);
        library.getBooks().put(b5.getBookId(), b5);

        Student s1 = new Student("UYE-001", "Ayse Ayse",
                "İstanbul", "xxxxxx", "2026-01-01",
                "2024100001", "Bilgisayar Mühendisliği");

        Student s2 = new Student("UYE-002", "Emre Emre",
                "Ankara", "xxxxxx", "2026-01-05",
                "2024100002", "Elektrik Mühendisliği");

        Faculty f1 = new Faculty("UYE-003", "Prof. Dr. Ali Ali",
                "Eskişehir", "xxxxxxx", "2025-08-16",
                "FAK-0099", "Bilgisayar Mühendisliği");

        library.getMembers().put(s1.getMemberId(), s1);
        library.getMembers().put(s2.getMemberId(), s2);
        library.getMembers().put(f1.getMemberId(), f1);

        Author author = new Author("Ahmet xxx");
        author.addBook(b1);
        library.addAuthor(author);
    }


    private static void printMainMenu() {
        System.out.println();
        System.out.println("    🎀🌸 " + library.getLibraryName());
        System.out.println();
        System.out.println("    1. 🎀Kitap İşlemleri");
        System.out.println("    2. 🎀Üye İşlemleri");
        System.out.println("    3. 🎀Ödünç Verme / İade İşlemleri");
        System.out.println("    4. 🎀Arama & Listeleme");
        System.out.println("    5. 🎀Çıkış");
        System.out.println();
        System.out.print("  🎀Seçiminiz: ");
    }


    private static void bookMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("  🎀 Kitap İşlemleri 🎀");
            System.out.println("  1. Yeni kitap ekle");
            System.out.println("  2. Kitap bilgilerini güncelle");
            System.out.println("  3. Kitap sil");
            System.out.println("  4. Tüm kitapları listele");
            System.out.println("  5. Ödünç verilen kitap kayıtlarını göster");
            System.out.println("  0. Geri");
            System.out.print("  Seçiminiz: ");

            switch (readInt()) {
                case 1 -> addBook();
                case 2 -> updateBook();
                case 3 -> deleteBook();
                case 4 -> library.displayAllBooks();
                case 5 -> library.displayLendingRecord();
                case 0 -> back = true;
                default -> System.out.println("  ✗ Geçersiz seçim.");
            }
        }
    }

    private static void addBook() {
        System.out.println();
        System.out.println("  🎀 Kategori seçin: ");
        System.out.println("  1. Ders Kitabı   2. Dergi/Journal   3. Magazin");
        System.out.print("  Seçiminiz: ");
        int cat = readInt();

        System.out.print("  Kitap ID   : "); String id      = scanner.nextLine().trim();
        System.out.print("  İsim       : "); String name    = scanner.nextLine().trim();
        System.out.print("  Yazar      : "); String author  = scanner.nextLine().trim();
        System.out.print("  Fiyat (₺)  : "); double price   = readDouble();
        System.out.print("  Baskı No   : "); int    edition = readInt();
        System.out.print("  Alım tarihi: "); String date    = scanner.nextLine().trim();

        Book book = switch (cat) {
            case 2 -> new Journal.Builder()
                    .bookId(id).name(name).author(author)
                    .price(price).edition(edition).dateOfPurchase(date).build();
            case 3 -> new Magazine.Builder()
                    .bookId(id).name(name).author(author)
                    .price(price).edition(edition).dateOfPurchase(date).build();
            default -> new StudyBook.Builder()
                    .bookId(id).name(name).author(author)
                    .price(price).edition(edition).dateOfPurchase(date).build();
        };
        library.addBook(book);
    }

    private static void updateBook() {
        System.out.print("  Güncellenecek Kitap ID: ");
        String bookId = scanner.nextLine().trim();
        Book book = library.getBook(bookId);
        if (book == null) { System.out.println("  ✗ Kitap bulunamadı."); return; }

        book.display();
        System.out.println("  (Değiştirmek istemediğiniz alan için Enter'a basın)");
        System.out.print("  Yeni İsim       : "); String newName   = scanner.nextLine().trim();
        System.out.print("  Yeni Yazar      : "); String newAuthor = scanner.nextLine().trim();
        System.out.print("  Yeni Fiyat (₺)  : "); String priceStr  = scanner.nextLine().trim();
        System.out.print("  Yeni Baskı No   : "); String edStr     = scanner.nextLine().trim();

        double newPrice   = priceStr.isBlank() ? 0 : Double.parseDouble(priceStr);
        int    newEdition = edStr.isBlank()    ? 0 : Integer.parseInt(edStr);
        library.updateBook(bookId, newName, newAuthor, newPrice, newEdition);
    }

    private static void deleteBook() {
        System.out.print("  Silinecek Kitap ID: ");
        String bookId = scanner.nextLine().trim();
        library.removeBook(bookId);
    }


    private static void memberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("  🎀 Üye İşlemleri 🎀");
            System.out.println("  1. Yeni üye ekle");
            System.out.println("  2. Tüm üyeleri listele");
            System.out.println("  3. Üye bilgilerini göster");
            System.out.println("  0. Geri");
            System.out.print("  Seçiminiz: ");

            switch (readInt()) {
                case 1 -> addMember();
                case 2 -> library.displayAllMembers();
                case 3 -> showMember();
                case 0 -> back = true;
                default -> System.out.println("  ✗ Geçersiz seçim.");
            }
        }
    }

    private static void addMember() {
        System.out.println();
        System.out.println("  Üye türü: 1. Öğrenci   2. Öğretim Üyesi");
        System.out.print("  Seçiminiz: ");
        int type = readInt();

        System.out.print("  Üye ID   : "); String mId    = scanner.nextLine().trim();
        System.out.print("  Ad Soyad : "); String mName  = scanner.nextLine().trim();
        System.out.print("  Adres    : "); String mAddr  = scanner.nextLine().trim();
        System.out.print("  Telefon  : "); String mPhone = scanner.nextLine().trim();
        String today = java.time.LocalDate.now().toString();

        if (type == 2) {
            System.out.print("  Personel No : "); String facId = scanner.nextLine().trim();
            System.out.print("  Bölüm       : "); String dept  = scanner.nextLine().trim();
            library.addMember(new Faculty(mId, mName, mAddr, mPhone, today, facId, dept));
        } else {
            System.out.print("  Öğrenci No  : "); String stuId = scanner.nextLine().trim();
            System.out.print("  Bölüm       : "); String dept  = scanner.nextLine().trim();
            library.addMember(new Student(mId, mName, mAddr, mPhone, today, stuId, dept));
        }
    }

    private static void showMember() {
        System.out.print("  Üye ID: ");
        String memberId = scanner.nextLine().trim();
        MemberRecord m = library.getMember(memberId);
        if (m == null) System.out.println("  ✗ Üye bulunamadı.");
        else           m.display();
    }


    private static void lendingMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("  🎀 Ödünç Verme / İade İşlemleri 🎀");
            System.out.println("  1. Kitap ödünç verme işlemi");
            System.out.println("  2. Kitap iade alımı");
            System.out.println("  3. Ödünç verilen kitap kayıtlarını göster");
            System.out.println("  0. Geri");
            System.out.print("  Seçiminiz: ");

            switch (readInt()) {
                case 1 -> lendBook();
                case 2 -> returnBook();
                case 3 -> library.displayLendingRecord();
                case 0 -> back = true;
                default -> System.out.println("  ✗ Geçersiz seçim.");
            }
        }
    }

    private static void lendBook() {
        System.out.print("  Üye ID   : "); String memberId = scanner.nextLine().trim();
        System.out.print("  Kitap ID : "); String bookId   = scanner.nextLine().trim();
        librarian.issueBook(memberId, bookId);
    }

    private static void returnBook() {
        System.out.print("  Üye ID   : "); String memberId = scanner.nextLine().trim();
        System.out.print("  Kitap ID : "); String bookId   = scanner.nextLine().trim();
        librarian.returnBook(memberId, bookId);
    }


    private static void searchMenu() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("  🎀 Arama & Listeleme 🎀");
            System.out.println("  1. ID ile kitap ara");
            System.out.println("  2. İsim ile kitap ara");
            System.out.println("  3. Yazar ile kitap ara");
            System.out.println("  4. Kategoriye göre listele");
            System.out.println("  5. Yazarın kitaplarını göster");
            System.out.println("  0. Geri");
            System.out.print("  Seçiminiz: ");

            switch (readInt()) {
                case 1 -> searchById();
                case 2 -> searchByName();
                case 3 -> searchByAuthor();
                case 4 -> searchByCategory();
                case 5 -> showAuthorBooks();
                case 0 -> back = true;
                default -> System.out.println("  ✗ Geçersiz seçim.");
            }
        }
    }

    private static void searchById() {
        System.out.print("  Kitap ID: ");
        String id = scanner.nextLine().trim();
        List<Book> results = librarian.searchBook(b -> b.getBookId().equalsIgnoreCase(id));
        printSearchResults(results);
    }

    private static void searchByName() {
        System.out.print("  Kitap ismi: ");
        String name = scanner.nextLine().trim().toLowerCase();
        List<Book> results = librarian.searchBook(b -> b.getName().toLowerCase().contains(name));
        printSearchResults(results);
    }

    private static void searchByAuthor() {
        System.out.print("  Yazar adı: ");
        String author = scanner.nextLine().trim();
        List<Book> results = library.getBooksByAuthor(author);
        printSearchResults(results);
    }

    private static void searchByCategory() {
        System.out.println("  1. Ders Kitabı   2. Dergi/Journal   3. Magazin");
        System.out.print("  Seçiminiz: ");
        BookCategory cat = switch (readInt()) {
            case 2 -> BookCategory.JOURNAL;
            case 3 -> BookCategory.MAGAZINE;
            default -> BookCategory.STUDY_BOOK;
        };
        List<Book> results = library.getBooksByCategory(cat);
        System.out.println("  Kategori: " + cat);
        printSearchResults(results);
    }

    private static void showAuthorBooks() {
        System.out.print("  Yazar adı: ");
        String name = scanner.nextLine().trim();
        Author author = library.getAuthorByName(name);
        if (author == null) {
            System.out.println("  ✗ Yazar bulunamadı: " + name);
        } else {
            author.showBooks();
        }
    }

    private static void printSearchResults(List<Book> results) {
        System.out.println("  " + results.size() + " sonuç bulundu:");
        if (results.isEmpty()) {
            System.out.println("  Kayıt bulunamadı.");
        } else {
            results.forEach(Book::display);
        }
    }


    private static int readInt() {
        try {
            String line = scanner.nextLine().trim();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static double readDouble() {
        try {
            String line = scanner.nextLine().trim();
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static void printWelcome() {
        System.out.println();
        System.out.println("  🎀 KÜTÜPHANE OTOMASYONU SİSTEMİ v1.0 🎀");
        System.out.println("  🎀 Kütüphane Sorumlusu: Arwen 🎀");
        System.out.println();
        System.out.println("  🎀 Kütüphanemize hoş geldiniz! 🎀");
    }

    private static void printBye() {
        System.out.println();
        System.out.println("  🎀 Güle güle! Kütüphane Otomasyonu Kapatılıyor... 🎀");
    }
}