package org.example;

import java.time.Instant;

public class Member {
    private int memberId;
    private String name;
    private String role;
    private Instant joinDate;

    public Member(int memberId, String name, String role) {
        this.memberId = memberId;
        this.name = name;
        this.role = role;
        this.joinDate = Instant.now();
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Instant getJoinDate() {
        return joinDate;
    }
}
