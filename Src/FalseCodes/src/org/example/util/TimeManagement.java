package org.example.util;

import java.time.Instant;

public class TimeManagement {
    public boolean isOverdue(BorrowTransaction transaction) {
        Instant dueDate = transaction.getBorrowDate().plusSeconds(86400);
        return Instant.now().isAfter(dueDate);
    }

    public boolean isMembershipValid(Member member) {
        return Instant.now().isBefore(member.getJoinDate().plusSeconds(31536000 - 2592000));
    }
}
