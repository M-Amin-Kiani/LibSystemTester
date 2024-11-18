package org.example; // تعریف بسته‌ی (Package) کد

public class Review { // تعریف کلاس Review
    private Member member; // متغیر خصوصی برای نگهداری عضو (Member) که نظر را ثبت کرده است
    private int rating; // متغیر خصوصی برای نگهداری امتیاز (Rating) که بین 1 تا 5 است
    private String reviewText; // متغیر خصوصی برای نگهداری متن نظر (Review Text)

    public Review(Member member, int rating, String reviewText) { // سازنده‌ی کلاس که یک نظر جدید ایجاد می‌کند
        this.member = member; // مقداردهی به متغیر member
        this.rating = rating; // مقداردهی به متغیر rating
        this.reviewText = reviewText; // مقداردهی به متغیر reviewText
    }

    public Member getMember() { // متد برای دریافت عضو (Member) که نظر را نوشته
        return member; // بازگشت مقدار member
    }

    public int getRating() { // متد برای دریافت امتیاز (Rating)
        return rating; // بازگشت مقدار rating
    }

    public String getReviewText() { // متد برای دریافت متن نظر (Review Text)
        return reviewText; // بازگشت مقدار reviewText
    }

    @Override
    public String toString() { // متد برای تبدیل شیء به رشته (String)
        return "Review by " + member.getName() + ": " + rating + "/5 - " + reviewText; // فرمت نمایش نظر
    }
}
