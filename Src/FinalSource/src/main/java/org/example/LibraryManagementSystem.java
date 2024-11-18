package org.example;
// این اینترفیس رفتارهای مختلفی را برای یک سیستم مدیریت کتابخانه مشخص می‌کند
import java.util.List;
import java.util.Optional;

public interface LibraryManagementSystem {
    void addBook(Book book); // کتاب جدیدی را به سیستم اضافه می‌کند
    void registerMember(Member member); // یک عضو جدید را در سیستم ثبت می‌کند
    List<BorrowTransaction> getAllBorrowedBooks(); // تمام کتاب‌های امانت‌گرفته شده را برمی‌گرداند
    List<BorrowTransaction> getAllCancelledTransactions(); // تمام تراکنش‌های لغو شده را برمی‌گرداند
    Optional<Book> searchBook(String title); // کتابی را با عنوان مشخص جست‌وجو می‌کند و در صورت وجود برمی‌گرداند
    void borrowBook(Member member, Book book) throws ReservationException; // یک عضو می‌تواند کتابی را امانت بگیرد، در صورت اشتباه در رزرو استثنا می‌اندازد
    void returnBook(Member member, Book book) throws ReservationException; // یک عضو کتابی را که امانت گرفته برمی‌گرداند، در صورت اشتباه در رزرو استثنا می‌اندازد
    double calculateFine(Member member, Book book); // جریمه یک عضو برای دیرکرد بازگشت کتاب را محاسبه می‌کند
    List<Book> getBooksByGenre(String genre); // کتاب‌ها را بر اساس ژانر مشخص شده برمی‌گرداند
    boolean isMembershipExpired(Member member); // بررسی می‌کند که آیا عضویت یک عضو منقضی شده است یا خیر
    void addReview(Book book, Review review); // نقدی را برای یک کتاب اضافه می‌کند
    List<Review> getReviewsForBook(Book book); // تمام نقدها برای یک کتاب مشخص شده را برمی‌گرداند
}
