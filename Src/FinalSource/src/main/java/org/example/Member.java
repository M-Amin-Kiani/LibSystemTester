package org.example; // این خط مشخص می‌کند که کلاس در بسته‌ای به نام org.example قرار دارد.

import java.time.Instant; // استفاده از کلاس Instant برای کار با تاریخ و زمان.

public class Member { // تعریف کلاس Member.
    private int memberId; // شناسه عضو (عدد صحیح).
    private String name; // نام عضو (رشته).
    private String role; // نقش عضو (رشته).
    private Instant joinDate; // تاریخ پیوستن عضو (کلاس Instant).

    // سازنده کلاس Member که سه پارامتر ورودی می‌گیرد و تاریخ پیوستن را به صورت خودکار با زمان فعلی تنظیم می‌کند.
    public Member(int memberId, String name, String role) {
        this.memberId = memberId; // مقداردهی شناسه عضو.
        this.name = name; // مقداردهی نام عضو.
        this.role = role; // مقداردهی نقش عضو.
        this.joinDate = Instant.now(); // تنظیم تاریخ پیوستن به زمان فعلی.
    }

    // تابع برای دریافت شناسه عضو.
    public int getMemberId() {
        return memberId; // بازگرداندن شناسه عضو.
    }

    // تابع برای دریافت نام عضو.
    public String getName() {
        return name; // بازگرداندن نام عضو.
    }

    // تابع برای دریافت نقش عضو.
    public String getRole() {
        return role; // بازگرداندن نقش عضو.
    }

    // تابع برای دریافت تاریخ پیوستن عضو.
    public Instant getJoinDate() {
        return joinDate; // بازگرداندن تاریخ پیوستن.
    }

    // تابع برای تنظیم تاریخ انقضای عضویت (فعلاً خالی است).
    public void setMembershipExpirationDate(Instant instant) {
        // این تابع می‌تواند برای تنظیم تاریخ انقضای عضویت استفاده شود.
    }
}
