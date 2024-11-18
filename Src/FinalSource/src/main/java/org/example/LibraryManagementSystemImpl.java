package org.example;

// وارد کردن کلاس‌ها و ابزارهای مورد نیاز از بسته‌های دیگر
import org.example.util.TimeManagement;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

// تعریف کلاس اصلی برای سیستم مدیریت کتابخانه
public class LibraryManagementSystemImpl implements LibraryManagementSystem {

    // تعریف لیست‌هایی برای ذخیره اطلاعات کتاب‌ها، اعضا، تراکنش‌ها و دیگر داده‌ها
    private List<Book> books; // لیست تمام کتاب‌های کتابخانه
    private List<Member> members; // لیست اعضای ثبت‌شده در کتابخانه
    private List<BorrowTransaction> transactions; // لیست تراکنش‌های امانت کتاب
    private List<BorrowTransaction> cancelledTransactions; // لیست تراکنش‌های لغوشده
    private WaitlistManagement waitlistManager; // مدیریت لیست انتظار
    private FineManagement fineManager; // مدیریت جریمه‌ها
    private EmailNotificationService emailService; // سرویس ارسال ایمیل

    // سازنده کلاس که لیست‌ها و مدیریت‌های مورد نیاز را مقداردهی اولیه می‌کند
    public LibraryManagementSystemImpl() {
        this.books = new ArrayList<>(); // مقداردهی لیست کتاب‌ها
        this.members = new ArrayList<>(); // مقداردهی لیست اعضا
        this.transactions = new ArrayList<>(); // مقداردهی لیست تراکنش‌ها
        this.cancelledTransactions = new ArrayList<>(); // مقداردهی لیست تراکنش‌های لغوشده
        this.waitlistManager = new WaitlistManagement(); // ایجاد مدیر لیست انتظار
        this.fineManager = new FineManagement(); // ایجاد مدیر جریمه
        this.emailService = new EmailNotificationService(); // ایجاد سرویس ارسال ایمیل
    }

    // اضافه کردن یک کتاب به کتابخانه
    @Override
    public void addBook(Book book) {
        books.add(book); // کتاب جدید به لیست کتاب‌ها اضافه می‌شود
    }

    // ثبت یک عضو جدید در سیستم کتابخانه
    @Override
    public void registerMember(Member member) {
        members.add(member); // عضو جدید به لیست اعضا اضافه می‌شود
    }

    // گرفتن لیست کتاب‌های امانت داده‌شده که هنوز بازگردانده نشده‌اند
    @Override
    public List<BorrowTransaction> getAllBorrowedBooks() {
        return transactions.stream()
                .filter(t -> !t.isReturned()) // فیلتر تراکنش‌هایی که هنوز کتاب بازگردانده نشده
                .collect(Collectors.toList()); // تبدیل نتیجه به لیست
    }

    // گرفتن لیست تراکنش‌های لغوشده
    @Override
    public List<BorrowTransaction> getAllCancelledTransactions() {
        return Collections.unmodifiableList(cancelledTransactions); // بازگرداندن یک لیست غیرقابل تغییر از تراکنش‌های لغوشده
    }

    // جستجوی یک کتاب بر اساس عنوان
    @Override
    public Optional<Book> searchBook(String title) {
        return books.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title)) // پیدا کردن کتاب با عنوان داده‌شده (بدون حساسیت به حروف کوچک و بزرگ)
                .findFirst(); // بازگرداندن اولین نتیجه
    }

    // امانت‌دادن یک کتاب به یک عضو
    @Override
    public void borrowBook(Member member, Book book) throws ReservationException {
        if (book.isBorrowed()) { // بررسی اینکه آیا کتاب قبلاً امانت داده شده است
            waitlistManager.addMemberToWaitlist(book.getTitle(), member); // افزودن عضو به لیست انتظار
            throw new ReservationException("Book is currently unavailable. You have been added to the waitlist."); // پرتاب استثنا در صورت عدم دسترسی کتاب
        }

        // ایجاد تراکنش جدید برای امانت کتاب
        BorrowTransaction transaction = new BorrowTransaction(member, book, Instant.now());
        transactions.add(transaction); // اضافه‌کردن تراکنش به لیست تراکنش‌ها
        book.setBorrowed(true); // علامت‌گذاری کتاب به عنوان امانت‌داده‌شده
        emailService.sendReminder(member, book); // ارسال ایمیل یادآوری به عضو
    }

    // بازگرداندن یک کتاب توسط عضو
    @Override
    public void returnBook(Member member, Book book) throws ReservationException {
        // پیدا کردن تراکنش مربوط به این عضو و این کتاب
        Optional<BorrowTransaction> transaction = transactions.stream()
                .filter(t -> t.getBook().equals(book) && t.getMember().equals(member) && !t.isReturned())
                .findFirst();

        if (transaction.isPresent()) {
            transaction.get().markReturned(); // علامت‌گذاری تراکنش به عنوان بازگردانده‌شده
            book.setBorrowed(false); // کتاب دیگر امانت‌داده‌شده نیست

            // ارسال یادآوری به عضو بعدی در لیست انتظار
            Member nextInLine = waitlistManager.getNextInWaitlist(book.getTitle());
            if (nextInLine != null) {
                emailService.sendReminder(nextInLine, book); // ارسال ایمیل یادآوری
            }
        } else {
            throw new ReservationException("No active borrow record found for this book."); // استثنا در صورت عدم پیدا شدن تراکنش فعال
        }
    }

    // محاسبه جریمه برای یک عضو خاص و یک کتاب خاص
    @Override
    public double calculateFine(Member member, Book book) {
        Optional<BorrowTransaction> transaction = transactions.stream()
                .filter(t -> t.getBook().equals(book) && t.getMember().equals(member) && !t.isReturned())
                .findFirst();

        if (transaction.isPresent()) {
            return fineManager.calculateFine(transaction.get()); // محاسبه جریمه برای تراکنش مربوطه
        }
        return 0; // در صورت عدم وجود تراکنش جریمه صفر است
    }

    // دریافت لیست کتاب‌ها بر اساس ژانر
    @Override
    public List<Book> getBooksByGenre(String genre) {
        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre)) // فیلتر کتاب‌ها بر اساس ژانر
                .collect(Collectors.toList()); // تبدیل نتیجه به لیست
    }

    // بررسی انقضای عضویت
    @Override
    public boolean isMembershipExpired(Member member) {
        return !TimeManagement.isMembershipValid(member); // بررسی معتبر بودن عضویت عضو
    }

    // افزودن یک نظر یا امتیاز به کتاب
    @Override
    public void addReview(Book book, Review review) {
        if (!members.contains(review.getMember())) { // بررسی اینکه آیا عضو ثبت‌شده است یا خیر
            throw new IllegalArgumentException("Member not registered."); // استثنا در صورت عدم ثبت عضو
        }
        book.addReview(review); // افزودن نظر یا امتیاز به کتاب
    }

    // دریافت لیست نظرات یا امتیازات برای یک کتاب
    @Override
    public List<Review> getReviewsForBook(Book book) {
        return book.getReviews(); // بازگرداندن نظرات کتاب
    }
}
