package com.wedding.model;

// OOP: INHERITANCE - VerifiedReview extends Review
// OOP: POLYMORPHISM - overrides abstract methods, adds booking verification
public class VerifiedReview extends Review {

    private String bookingId; // proof of purchase

    public VerifiedReview() {}

    public VerifiedReview(String reviewId, String userId, String userName,
                          String packageId, String packageName,
                          int rating, String title, String comment,
                          String createdDate, String bookingId) {
        super(reviewId, userId, userName, packageId, packageName,
              rating, title, comment, createdDate);
        this.bookingId = bookingId;
    }

    // OOP: POLYMORPHISM - different label from PublicReview
    @Override
    public String getReviewType() {
        return "VERIFIED";
    }

    // OOP: POLYMORPHISM - includes bookingId in file format
    @Override
    public String toFileString() {
        return "VERIFIED|" + getReviewId() + "|" + getUserId() + "|" + getUserName()
                + "|" + getPackageId() + "|" + getPackageName()
                + "|" + getRating() + "|" + getTitle()
                + "|" + getComment().replace("|", ",") + "|" + getCreatedDate()
                + "|" + bookingId;
    }

    public static VerifiedReview fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 11) return null;
        return new VerifiedReview(p[1], p[2], p[3], p[4], p[5],
                Integer.parseInt(p[6]), p[7], p[8], p[9], p[10]);
    }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
}
