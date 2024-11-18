package org.example.impl;

import org.example.*;
import org.example.util.*;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryManagementSystemImpl implements LibraryManagementSystem {

    private List<Book> books;
    private List<Member> members;
    private List<BorrowTransaction> transactions;
    private List<BorrowTransaction> cancelledTransactions;
    private WaitlistManagement waitlistManager;
    private FineManagement fineManager;
    private EmailNotificationService emailService;

    public LibraryManagementSystemImpl() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.transactions = new ArrayList<>();
        this.cancelledTransactions = new ArrayList<>();
        this.waitlistManager = new WaitlistManagement();
        this.fineManager = new FineManagement();
        this.emailService = new EmailNotificationService();
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void registerMember(Member member) {
        members.add(member);
    }

    @Override
    public List<BorrowTransaction> getAllBorrowedBooks() {
        return transactions.stream()
                .filter(t -> !t.isReturned())
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowTransaction> getAllCancelledTransactions() {
        return Collections.unmodifiableList(cancelledTransactions);
    }

    @Override
    public Optional<Book> searchBook(String title) {
        return books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    @Override
    public void borrowBook(Member member, Book book) throws ReservationException {
        if (book.isBorrowed()) {
            waitlistManager.addMemberToWaitlist(book.getTitle(), member);
            throw new ReservationException("Book is currently unavailable. You have been added to the waitlist.");
        }

        BorrowTransaction transaction = new BorrowTransaction(member, book, Instant.now());
        transactions.add(transaction);
        book.setBorrowed(true);
        emailService.sendReminder(member, book);
    }

    @Override
    public void returnBook(Member member, Book book) throws ReservationException {
        Optional<BorrowTransaction> transaction = transactions.stream()
                .filter(t -> t.getBook().equals(book) && t.getMember().equals(member) && !t.isReturned())
                .findFirst();

        if (transaction.isPresent()) {
            transaction.get().markReturned();
            book.setBorrowed(false);
            Member nextInLine = waitlistManager.getNextInWaitlist(book.getTitle());
            if (nextInLine != null) {
                emailService.sendReminder(nextInLine, book);
            }
        } else {
            throw new ReservationException("No active borrow record found for this book.");
        }
    }

    @Override
    public double calculateFine(Member member, Book book) {
        Optional<BorrowTransaction> transaction = transactions.stream()
                .filter(t -> t.getBook().equals(book) && t.getMember().equals(member) && !t.isReturned())
                .findFirst();

        if (transaction.isPresent()) {
            return fineManager.calculateFine(transaction.get());
        }
        return 0;
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isMembershipExpired(Member member) {
        return !TimeManagement.isMembershipValid(member);
    }

    @Override
    public void addReview(Book book, Review review) {
        if (!members.contains(review.getMember())) {
            throw new IllegalArgumentException("Member not registered.");
        }
        book.addReview(review);
    }

    @Override
    public List<Review> getReviewsForBook(Book book) {
        return book.getReviews();
    }
}
