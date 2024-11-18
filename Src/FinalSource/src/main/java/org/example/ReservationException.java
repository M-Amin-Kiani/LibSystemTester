package org.example;

// این کلاس برای مدیریت استثناء‌های مربوط به رزرو استفاده می‌شود
public class ReservationException extends Exception {
    // سازنده کلاس که پیام خطا را به کلاس والد (Exception) منتقل می‌کند
    public ReservationException(String message) {
        super(message);
    }
}
