package org.example;

import java.time.Instant;

public class FineManagement {
    // متد محاسبه جریمه برای یک تراکنش قرضی
    public double calculateFine(BorrowTransaction transaction) {
        // محاسبه تعداد ثانیه‌های تأخیری
        long overdueSeconds = Instant.now().getEpochSecond() - transaction.getBorrowDate().getEpochSecond();
        // بازگشت مقدار جریمه بر اساس روزهای تأخیر (محاسبه شده با تقسیم بر 86400 ثانیه، که معادل یک روز است)
        return (overdueSeconds / 86400) * 0.5;
    }
}
