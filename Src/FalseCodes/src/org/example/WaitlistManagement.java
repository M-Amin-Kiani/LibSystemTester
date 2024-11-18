package org.example;

import java.util.*;

public class WaitlistManagement {
    private final Map<String, Queue<Member>> waitlists = new HashMap<>();

    public void addMemberToWaitlist(String bookTitle, Member member) {
        waitlists.computeIfAbsent(bookTitle, k -> new LinkedList<>()).add(member);
    }

    public Member getNextInWaitlist(String bookTitle) {
        return waitlists.getOrDefault(bookTitle, new LinkedList<>()).poll();
    }
}
