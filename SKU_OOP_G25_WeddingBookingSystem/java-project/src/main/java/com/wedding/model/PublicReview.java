package com.wedding.model;

// OOP: INHERITANCE - PublicReview extends Review
// OOP: POLYMORPHISM - overrides abstract methods
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
    @Override
    public String toFileString() {
        return "PUBLIC|" + getReviewId() + "|" + getUserId() + "|" + getUserName()
                + "|" + getPackageId() + "|" + getPackageName()
                + "|" + getRating() + "|" + getTitle()
                + "|" + getComment().replace("|", ",") + "|" + getCreatedDate();
    }

    public static PublicReview fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 10) return null;
        return new PublicReview(p[1], p[2], p[3], p[4], p[5],
                Integer.parseInt(p[6]), p[7], p[8], p[9]);
    }
}
