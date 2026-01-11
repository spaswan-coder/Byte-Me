package org.example;

public class Review {
    private String reviewerName;
    private int rating;
    private String comment;

    public Review(String reviewerName, int rating, String comment) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Reviewer: " + reviewerName + ", Rating: " + rating + ", Comment: " + comment;
    }
}