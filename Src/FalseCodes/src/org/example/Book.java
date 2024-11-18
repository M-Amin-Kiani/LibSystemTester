package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Book {
    private String title;
    private String author;
    private String genre;
    private boolean isBorrowed;
    private List<Review> reviews;

    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isBorrowed = false;
        this.reviews = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public OptionalDouble getAverageRating() {
        return reviews.stream().mapToInt(Review::getRating).average();
    }

    public List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }

    @Override
    public String toString() {
        String rating = getAverageRating()
                .map(avg -> String.format("Average Rating: %.2f/5", avg))
                .orElse("No ratings yet");
        return title + " by " + author + " | Genre: " + genre + " | " + rating;
    }
}
