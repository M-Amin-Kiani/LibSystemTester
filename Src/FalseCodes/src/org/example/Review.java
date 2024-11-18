package org.example;

public class Review {
    private Member member;
    private int rating;
    private String reviewText;

    public Review(Member member, int rating, String reviewText) {
        this.member = member;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public Member getMember() {
        return member;
    }

    public int getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    @Override
    public String toString() {
        return "Review by " + member.getName() + ": " + rating + "/5 - " + reviewText;
    }
}
