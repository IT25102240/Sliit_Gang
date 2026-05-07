package com.wedding.model;

// OOP: ABSTRACTION - abstract base for all review types
// OOP: ENCAPSULATION - all fields private
public abstract class Review {

    private String reviewId;
    private String userId;
    private String userName;
    private String packageId;
    private String packageName;
    private int rating;          // 1 to 5
    private String title;
    private String comment;
    private String createdDate;

    public Review() {}

    public Review(String reviewId, String userId, String userName,
                  String packageId, String packageName,
                  int rating, String title, String comment, String createdDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.packageId = packageId;
        this.packageName = packageName;
        this.rating = Math.max(1, Math.min(5, rating)); // clamp 1-5
        this.title = title;
        this.comment = comment;
        this.createdDate = createdDate;
    }

    // OOP: ABSTRACTION - subclasses define their review type label
    public abstract String getReviewType();

    // OOP: ABSTRACTION - subclasses define file format
    public abstract String toFileString();

    // Getters and Setters - OOP: ENCAPSULATION
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = Math.max(1, Math.min(5, rating)); }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
}
