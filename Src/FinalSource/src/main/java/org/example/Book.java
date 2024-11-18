package org.example; // تعریف بسته (پکیج) کتابخانه

import java.util.ArrayList; // وارد کردن کلاس ArrayList از کتابخانه جاوا
import java.util.List; // وارد کردن رابط List از کتابخانه جاوا
import java.util.Objects; // وارد کردن کلاس Objects برای استفاده از متدهای مقایسه شیء
import java.util.OptionalLong;

public class Book { // تعریف کلاس Book
    private String title; // عنوان کتاب
    private String genre; // ژانر کتاب
    private boolean isBorrowed; // وضعیت امانت‌داری کتاب (بدون یا با امانت)
    private List<Review> reviews; // لیست نظرات مربوط به کتاب

    public Book(String title, String genre) { // سازنده کلاس Book
        this.title = title; // مقداردهی عنوان
        this.genre = genre; // مقداردهی ژانر
        this.isBorrowed = false; // مقداردهی وضعیت امانت‌داری به false
        this.reviews = new ArrayList<>(); // ایجاد لیستی جدید برای نظرات
    }

    public String getTitle() { // متد برای دریافت عنوان کتاب
        return title;
    }

    public String getGenre() { // متد برای دریافت ژانر کتاب
        return genre;
    }

    public boolean isBorrowed() { // متد برای بررسی وضعیت امانت‌داری کتاب
        return isBorrowed;
    }

    public List<Review> getReviews() { // متد برای دریافت نظرات کتاب
        return new ArrayList<>(reviews); // بازگشت یک کپی از لیست نظرات
    }

    public void addReview(Review review) { // متد برای افزودن یک نظر به کتاب
        reviews.add(review); // اضافه کردن نظر به لیست
    }

    public void setBorrowed(boolean isBorrowed) { // متد برای تعیین وضعیت امانت‌داری
        this.isBorrowed = isBorrowed; // به‌روزرسانی وضعیت امانت‌داری
    }

    @Override
    public boolean equals(Object o) { // متد برای مقایسه دو شیء Book
        if (this == o) return true; // اگر دو شیء یکسان باشند
        if (o == null || getClass() != o.getClass()) return false; // بررسی نوع شیء
        Book book = (Book) o; // تبدیل شیء ورودی به نوع Book
        return Objects.equals(title, book.title); // مقایسه عنوان دو کتاب
    }

    @Override
    public int hashCode() { // متد برای بازگشت کد هش شیء
        return Objects.hash(title); // تولید کد هش بر اساس عنوان کتاب
    }

    @Override
    public String toString() {
        String rating = reviews.isEmpty()
                ? "No ratings yet"
                : String.format("Average Rating: %.2f/5", getAverageRating());
        return String.format("%s | Genre: %s | %s", title, genre, rating);
    }

    private OptionalLong getAverageRating() {
        return null;
    }
}
