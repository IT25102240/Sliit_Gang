package com.wedding.model;

import com.wedding.util.FileHandler;

// OOP: INHERITANCE - PublicReview extends Review
// OOP: POLYMORPHISM - overrides abstract methods
// FIX: toFileString() sanitises all user-supplied fields (title, comment, userName etc.)
public class PublicReview extends Review {

    public PublicReview() {}

    public PublicReview(String reviewId, String userId, String userName,
                        String packageId, String packageName,
                        int rating, String title, String comment, String createdDate) {
        super(reviewId, userId, userName, packageId, packageName,
                rating, title, comment, createdDate);
    }

    // OOP: POLYMORPHISM
    @Override
    public String getReviewType() {
        return "PUBLIC";
    }

    // OOP: POLYMORPHISM
    // FIX: all string fields sanitised, not just comment
    @Override
    public String toFileString() {
        return "PUBLIC|"
                + getReviewId() + "|"
                + getUserId() + "|"
                + FileHandler.sanitise(getUserName()) + "|"
                + getPackageId() + "|"
                + FileHandler.sanitise(getPackageName()) + "|"
                + getRating() + "|"
                + FileHandler.sanitise(getTitle()) + "|"
                + FileHandler.sanitise(getComment()) + "|"
                + getCreatedDate();
    }

    public static PublicReview fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 10) return null;
        return new PublicReview(p[1], p[2], p[3], p[4], p[5],
                Integer.parseInt(p[6]), p[7], p[8], p[9]);
    }
}
