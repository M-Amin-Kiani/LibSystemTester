package org.example;

// Import کردن کلاسهای مورد نیاز
import java.time.Instant; // برای کار با زمان
import java.util.Objects; // برای مقایسه اشیاء

public class BorrowTransaction {
    private Member member; // عضو (Member) که کتاب را قرض می‌گیرد
    private Book book;     // کتابی که قرض داده می‌شود
    private Instant borrowDate; // تاریخ قرض دادن کتاب
    private boolean isReturned;  // وضعیت اینکه آیا کتاب برگردانده شده یا خیر

    // سازنده کلاس که عضو، کتاب و تاریخ قرض دادن را می‌گیرد
    public BorrowTransaction(Member member, Book book, Instant borrowDate) {
        this.member = member; // مقداردهی به عضو
        this.book = book;     // مقداردهی به کتاب
        this.borrowDate = borrowDate; // مقداردهی به تاریخ قرض دادن
        this.isReturned = false; // پیش‌فرض وضعیت برگرداندن کتاب را نادرست قرار می‌دهد
    }

    // متد برای دریافت عضو
    public Member getMember() {
        return member;
    }

    // متد برای دریافت تاریخ قرض دادن
    public Instant getBorrowDate() {
        return borrowDate;
    }

    // متد برای دریافت کتاب
    public Book getBook() {
        return book;
    }

    // متد برای بررسی اینکه آیا کتاب برگشت داده شده است
    public boolean isReturned() {
        return isReturned;
    }

    // متد برای علامت‌گذاری کتاب به عنوان برگشت داده شده
    public void markReturned() {
        this.isReturned = true; // وضعیت را به 'برگشت داده شده' تغییر می‌دهد
    }

    // overridden متد equals برای مقایسه دو BorrowTransaction
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // اگر دو شیء برابرند
        if (o == null || getClass() != o.getClass()) return false; // بررسی نوع
        BorrowTransaction that = (BorrowTransaction) o; // تبدیل به نوع صحیح
        return Objects.equals(member, that.member) && Objects.equals(book, that.book); // مقایسه عضو و کتاب
    }

    // overridden متد hashCode برای تولید کد هش
    @Override
    public int hashCode() {
        return Objects.hash(member, book); // تولید کد هش بر اساس عضو و کتاب
    }
}
