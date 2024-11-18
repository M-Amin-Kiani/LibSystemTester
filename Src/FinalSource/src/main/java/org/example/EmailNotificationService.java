package org.example;

// کلاس EmailNotificationService برای ارسال اعلان‌های ایمیل
public class EmailNotificationService {
    // متد sendReminder، یادآوری برای عضو و کتاب خاصی ارسال می‌کند
    public void sendReminder(Member member, Book book) {
        // چاپ یادآوری برای کاربر
        System.out.println("Reminder: The book " + book.getTitle() + " is available for you, " + member.getName() + "!");
    }
}
