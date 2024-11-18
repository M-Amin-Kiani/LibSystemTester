package org.example; // تعریف بسته (پکیج) برای این کلاس

import java.util.HashMap; // وارد کردن کلاس HashMap از کتابخانه
import java.util.LinkedList; // وارد کردن کلاس LinkedList از کتابخانه
import java.util.Map; // وارد کردن رابط Map از کتابخانه
import java.util.Queue; // وارد کردن رابط Queue از کتابخانه

public class WaitlistManagement { // تعریف کلاس WaitlistManagement
    private Map<String, Queue<Member>> waitlistMap = new HashMap<>(); // ایجاد نقشه‌ای برای نگهداری لیست‌های انتظار کتاب‌ها

    public void addMemberToWaitlist(String bookTitle, Member member) { // متدی برای اضافه کردن عضو به لیست انتظار
        waitlistMap.computeIfAbsent(bookTitle, k -> new LinkedList<>()).add(member); // اگر کتاب در نقشه موجود نیست، یک لیست جدید ایجاد کرده و عضو را اضافه می‌کند
    }

    public Member getNextInWaitlist(String bookTitle) { // متدی برای گرفتن عضو بعدی از لیست انتظار
        Queue<Member> waitlist = waitlistMap.get(bookTitle); // دسترسی به لیست انتظار کتاب مشخص شده
        return (waitlist != null && !waitlist.isEmpty()) ? waitlist.poll() : null; // اگر لیست انتظار وجود داشته باشد و خالی نباشد، عضو بعدی را برمی‌گرداند، در غیر این صورت null برمی‌گرداند
    }
}
