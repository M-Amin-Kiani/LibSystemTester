package org.example;

public class EmailNotificationService {
    public void sendReminder(Member member, Book book) {
        System.out.println("Reminder: Return " + book.getTitle() + " soon, " + member.getName() + "!");
    }
}
