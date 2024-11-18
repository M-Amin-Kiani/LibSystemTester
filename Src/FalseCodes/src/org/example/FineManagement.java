package org.example;

import java.time.Instant;

public class FineManagement {
    public double calculateFine(BorrowTransaction transaction) {
        long overdueSeconds = Instant.now().getEpochSecond() - transaction.getBorrowDate().getEpochSecond();
        return (overdueSeconds / 86400) * 0.5;
    }
}
