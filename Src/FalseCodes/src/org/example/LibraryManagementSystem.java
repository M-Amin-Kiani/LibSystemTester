package org.example;

import java.util.List;
import java.util.Optional;

public interface LibraryManagementSystem {
    void addBook(Book book);
    void registerMember(Member member);
    List<BorrowTransaction> getAllBorrowedBooks();
    List<BorrowTransaction> getAllCancelledTransactions();
    Optional<Book> searchBook(String title);
    void borrowBook(Member member, Book book) throws ReservationException;
    void returnBook(Member member, Book book) throws ReservationException;
    double calculateFine(Member member, Book book);
    List<Book> getBooksByGenre(String genre);
    boolean isMembershipExpired(Member member);
    void addReview(Book book, Review review);
    List<Review> getReviewsForBook(Book book);
}
