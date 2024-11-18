package org.example;

import java.time.Instant;

public class BorrowTransaction {
    private Member member;
    private Book book;
    private Instant borrowDate;

    public BorrowTransaction(Member member, Book book) {
        this.member = member;
        this.book = book;
        this.borrowDate = Instant.now();
    }

    public Member getMember() {
        return member;
    }

    public Book getBook() {
        return book;
    }

    public Instant getBorrowDate() {
        return borrowDate;
    }
}
