package org.example.util;

import java.time.Instant;

import org.example.BorrowTransaction;
import org.example.Member;
//آیا یک تراکنش امانت کتاب (BorrowTransaction) منقضی شده است یا خیر
public class TimeManagement {
    public boolean isOverdue(BorrowTransaction transaction) {
        // تاریخ سررسید را با اضافه کردن 86400 ثانیه (یک روز) به تاریخ امانت محاسبه می‌کند
        Instant dueDate = transaction.getBorrowDate().plusSeconds(86400);
        return Instant.now().isAfter(dueDate);
    }
    // این متد بررسی می‌کند که آیا عضویت یک عضو (Member) معتبر است یا خیر
    public static boolean isMembershipValid(Member member) {
        // عضویت یک سال پس از تاریخ پیوستن منقضی می‌شود (با کم کردن یک ماه از آن)
        return Instant.now().isBefore(member.getJoinDate().plusSeconds(31536000 - 2592000));
    }
}
