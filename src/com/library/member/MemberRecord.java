package com.library.member;

import com.library.core.Displayable;
import com.library.core.Lendable;

import java.util.HashSet;
import java.util.Set;

public abstract class MemberRecord implements Displayable, Lendable {

    private final String memberId;
    private final String type;
    private final String dateOfMembership;
    private final int    maxBookLimit = 5;
    private int          noBooksIssued;
    private String       name;
    private String       address;
    private String       phoneNo;


    private final Set<String> borrowedBookIds;

    protected MemberRecord(String memberId, String type, String name,
                           String address, String phoneNo, String dateOfMembership) {
        this.memberId          = memberId;
        this.type              = type;
        this.name              = name;
        this.address           = address;
        this.phoneNo           = phoneNo;
        this.dateOfMembership  = dateOfMembership;
        this.noBooksIssued     = 0;
        this.borrowedBookIds   = new HashSet<>();
    }

    @Override
    public boolean canBorrowMore() {
        return noBooksIssued < maxBookLimit;
    }

    @Override
    public boolean increaseBookCount(String bookId) {
        if (!canBorrowMore()) return false;
        borrowedBookIds.add(bookId);
        noBooksIssued++;
        return true;
    }

    @Override
    public boolean decreaseBookCount(String bookId) {
        if (borrowedBookIds.remove(bookId)) {
            noBooksIssued--;
            return true;
        }
        return false;
    }

    public boolean hasBorrowed(String bookId) {
        return borrowedBookIds.contains(bookId);  // O(1)
    }

    public void payBill(double amount) {
        System.out.printf("  ✓ %s → %.2f TL ödendi.%n", name, amount);
    }

    public abstract String getMemberType();

    public String getMemberId()           { return memberId; }
    public String getType()               { return type; }
    public String getName()               { return name; }
    public String getAddress()            { return address; }
    public String getPhoneNo()            { return phoneNo; }
    public String getDateOfMembership()   { return dateOfMembership; }
    public int getNoBooksIssued()         { return noBooksIssued; }
    public int getMaxBookLimit()          { return maxBookLimit; }
    public Set<String> getBorrowedBookIds(){ return borrowedBookIds; }

    public void setName(String name)       { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }


    @Override
    public void display() {

        System.out.printf( "  - Üye ID   : %-26s│%n", memberId);
        System.out.printf( "  - İsim     : %-26s│%n", name);
        System.out.printf( "  - Tür      : %-26s│%n", getMemberType());
        System.out.printf( "  - Telefon  : %-26s│%n", phoneNo);
        System.out.printf( "  - Kitap    : %d/%-24d│%n", noBooksIssued, maxBookLimit);
        System.out.printf( "  - Üyelik   : %-26s│%n", dateOfMembership);

    }
}