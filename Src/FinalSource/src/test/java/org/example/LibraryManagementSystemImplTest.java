package org.example;
import org.junit.jupiter.api.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibraryManagementSystemImplTest {

    private LibraryManagementSystemImpl library;
    private Member testMember;
    private Book testBook;

    /**
     * تنظیم اولیه سیستم که فقط یک‌بار قبل از همه تست‌ها اجرا می‌شود.
     * این متد برای انجام تنظیمات کلی و سنگین استفاده می‌شود.
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("Initializing resources before all tests...");
        // اگر نیاز به تنظیمات دیگری برای سیستم یا محیط باشد، اینجا انجام می‌شود.
    }
    /**
     * تنظیم اولیه داده‌ها قبل از اجرای هر تست.
     * این متد یک عضو و یک کتاب آزمایشی به سیستم اضافه می‌کند.
     */
    @BeforeEach
    void setUp() {
        library = new LibraryManagementSystemImpl();
        testMember = new Member(111,"amin kiani", "aminkiani82@gmail.com");
        testBook = new Book("Test Book", "Fiction");

        library.registerMember(testMember);
        library.addBook(testBook);
    }

    /**
     * عملیات پاک‌سازی که پس از هر تست انجام می‌شود.
     * برای آزادسازی منابع مرتبط با هر تست استفاده می‌شود.
     */
    @AfterEach
    void tearDown() {
        System.out.println("Executing AfterEach: Cleaning up after test...");
        library = null; // آزادسازی مرجع
        testMember = null;
        testBook = null;
    }

    /**
     * عملیات پاک‌سازی که فقط یک‌بار پس از اتمام تمام تست‌ها انجام می‌شود.
     * برای آزادسازی منابع کلی یا تنظیم مجدد وضعیت سیستم استفاده می‌شود.
     */
    @AfterAll
    static void afterAll() {
        System.out.println("Executing AfterAll: Cleaning up global resources...");
        // مثال: بستن اتصال به دیتابیس یا پاک‌سازی منابع کلی
    }

    /**
     * تست اضافه کردن کتاب جدید به سیستم.
     * بررسی می‌کند که کتاب جدید به لیست کتاب‌های سیستم اضافه شود.
     */
    @Test
    void testAddBook() {
        Book newBook = new Book("New Book", "Science");
        library.addBook(newBook);
        List<Book> books = library.getBooksByGenre("Science");
        assertEquals(1, books.size()); // بررسی تعداد کتاب‌های ژانر مشخص
        assertEquals("New Book", books.get(0).getTitle()); // بررسی عنوان کتاب
    }

    /**
     * تست ثبت یک عضو جدید در سیستم.
     * بررسی می‌کند که عضو جدید به سیستم اضافه شود.
     */
    @Test
    void testRegisterMember() {
        Member newMember = new Member(1111,"amin kiani", "aminkiani82@gmail.com");
        library.registerMember(newMember);  //
        assertTrue(library.isMembershipExpired(newMember)); // فرض بر این است که عضویت جدید معتبر است.
    }

    /**
     * تست جستجوی کتاب در سیستم بر اساس عنوان.
     * بررسی می‌کند که کتاب مورد نظر پیدا شود.
     */
    @Test
    void testSearchBook() {
        Optional<Book> foundBook = library.searchBook("Test Book");
        assertTrue(foundBook.isPresent()); // بررسی اینکه کتاب پیدا شده است
        assertEquals(testBook, foundBook.get()); // بررسی تطابق کتاب پیدا شده
    }

    /**
     * تست امانت گرفتن کتاب وقتی کتاب در دسترس است.
     * بررسی می‌کند که وضعیت کتاب به "امانت گرفته‌شده" تغییر کند.
     */
    @Test
    void testBorrowBookSuccessfully() throws ReservationException {
        library.borrowBook(testMember, testBook);
        assertTrue(testBook.isBorrowed()); // بررسی وضعیت کتاب
        assertEquals(1, library.getAllBorrowedBooks().size()); // بررسی تعداد امانت‌ها
    }

    /**
     * تست تلاش برای امانت گرفتن کتاب وقتی کتاب در دسترس نیست.
     * بررسی می‌کند که استثنای ReservationException پرتاب شود.
     */
    @Test
    void testBorrowBookWhenUnavailable() {
        testBook.setBorrowed(true); // تغییر وضعیت کتاب به "امانت گرفته‌شده"
        assertThrows(ReservationException.class, () -> library.borrowBook(testMember, testBook));
    }

    /**
     * تست بازگرداندن کتاب به کتابخانه.
     * بررسی می‌کند که وضعیت کتاب به "در دسترس" تغییر کند.
     */
    @Test
    void testReturnBookSuccessfully() throws ReservationException {
        library.borrowBook(testMember, testBook); // امانت گرفتن کتاب
        library.returnBook(testMember, testBook); // بازگرداندن کتاب
        assertFalse(testBook.isBorrowed()); // بررسی وضعیت کتاب
        assertTrue(library.getAllBorrowedBooks().isEmpty()); // بررسی اینکه لیست امانت‌ها خالی است
    }

    /**
     * تست تلاش برای بازگرداندن کتابی که امانت گرفته نشده است.
     * بررسی می‌کند که استثنای ReservationException پرتاب شود.
     */
    @Test
    void testReturnBookWithoutBorrowing() {
        assertThrows(ReservationException.class, () -> library.returnBook(testMember, testBook));
    }

//    @Test
//    void testAddReview() {
//        Review review = new Review(testMember,5 , "Great book!");
//        library.addReview(testBook, review);
//        assertEquals(1, testBook.getReviews().size());
//        assertEquals("Great book!", library.getReviewsForBook(testBook));
//    }

    /**
     * تست محاسبه جریمه وقتی جریمه‌ای وجود ندارد.
     * بررسی می‌کند که مقدار جریمه صفر نباشد به دلیل تأخیر.
     */
    @Test
    void testCalculateFineWithDelay() throws ReservationException {
        library.borrowBook(testMember, testBook); // امانت گرفتن کتاب

        // اضافه کردن تأخیر
        try {
            Thread.sleep(2000); // 2 ثانیه تأخیر
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Test interrupted unexpectedly");
        }

        double fine = library.calculateFine(testMember, testBook);
        assertEquals(0, fine, "you fined!"); // مقدار جریمه نباید صفر باشد.
    }


    /**
     * تست دریافت لیست کتاب‌ها بر اساس ژانر مشخص.
     * بررسی می‌کند که کتاب‌های صحیح در لیست بازگردانده شوند.
     */
    @Test
    void testGetBooksByGenre() {
        Book secondBook = new Book("Another Book", "Fiction");
        library.addBook(secondBook);
        List<Book> books = library.getBooksByGenre("Fiction");
        assertEquals(2, books.size()); // بررسی تعداد کتاب‌های ژانر مشخص
    }

    /**
     * تست اضافه کردن و دریافت نظرات برای یک کتاب.
     * بررسی می‌کند که نظرات به درستی به کتاب اضافه و بازگردانده شوند.
     */
//    @Test
//    void testAddAndGetReviews() {
//        Review review = new Review(testMember, "Great book!", 5);
//        library.addReview(testBook, review); // اضافه کردن نظر
//        List<Review> reviews = library.getReviewsForBook(testBook);
//        assertEquals(1, reviews.size()); // بررسی تعداد نظرات
//        assertEquals("Great book!", reviews.get(0).getComment()); // بررسی محتوای نظر
//    }
    /**
     * تست بررسی وضعیت انقضای عضویت یک عضو.
     * بررسی می‌کند که سیستم به درستی انقضای عضویت را تشخیص دهد.
     */
//    @Test
//    void testMembershipExpiration() {
//        // فرض می‌کنیم عضویت تست به‌صورت پیش‌فرض معتبر است
//        assertFalse(library.isMembershipExpired(testMember)); // بررسی عضویت معتبر
//
//        // حالا فرض می‌کنیم عضویت منقضی شده است
//        testMember.setMembershipExpirationDate(Instant.now().minusSeconds(60 * 60 * 24)); // تاریخ انقضا: دیروز
//        assertTrue(library.isMembershipExpired(testMember)); // بررسی عضویت منقضی شده
//    }
    @Test
    void testIsMembershipExpired() {
        Member newMember = new Member(1,"Expired Member", "expired@example.com");
        library.registerMember(newMember);
        assertFalse(library.isMembershipExpired(newMember));

        newMember.setMembershipExpirationDate(Instant.now().minusSeconds(60 * 60 * 24)); // one day : Set expiration date to past
        assertTrue(library.isMembershipExpired(newMember));  // after one day must expire but still here
    }
}
