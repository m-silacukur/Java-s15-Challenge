package com.library.core;

public interface Lendable {
    boolean canBorrowMore();
    boolean increaseBookCount(String bookId);
    boolean decreaseBookCount(String bookId);
}
