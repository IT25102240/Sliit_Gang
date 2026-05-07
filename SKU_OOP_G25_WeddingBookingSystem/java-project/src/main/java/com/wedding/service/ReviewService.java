package com.wedding.service;

import com.wedding.model.PublicReview;
import com.wedding.model.Review;
import com.wedding.model.VerifiedReview;
import com.wedding.util.FileHandler;
import com.wedding.util.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// CHANUKA - Review & Feedback Management Service
// FILE HANDLING: reads/writes to reviews.txt
// OOP: Polymorphism — PublicReview and VerifiedReview handled uniformly as Review
@Service
public class ReviewService {

    private static final String FILE = "reviews.txt";
    private final FileHandler fileHandler;

    public ReviewService(@Value("${data.dir}") String dataDir) {
        this.fileHandler = new FileHandler(dataDir);
        seedDefaultReviews();
    }

    private void seedDefaultReviews() {
        if (!fileHandler.fileExists(FILE) || fileHandler.readAll(FILE).isEmpty()) {
            PublicReview r1 = new PublicReview(IdGenerator.generateReviewId(),
                    "USR001", "Emma Johnson", "PKG001", "Garden Ceremony",
                    5, "Absolutely perfect!", "Every detail was handled beautifully.", IdGenerator.today());
            fileHandler.appendLine(FILE, r1.toFileString());
        }
    }

    private Review parseLine(String line) {
        if (line.startsWith("PUBLIC")) return PublicReview.fromFileString(line);
        if (line.startsWith("VERIFIED")) return VerifiedReview.fromFileString(line);
        return null;
    }

    // CREATE - Submit a public review
    public Review submitPublicReview(String userId, String userName,
                                      String packageId, String packageName,
                                      int rating, String title, String comment) {
        String id = IdGenerator.generateReviewId();
        PublicReview review = new PublicReview(id, userId, userName,
                packageId, packageName, rating, title, comment, IdGenerator.today());
        fileHandler.appendLine(FILE, review.toFileString());
        return review;
    }

    // CREATE - Submit a verified review (with booking ID as proof)
    public Review submitVerifiedReview(String userId, String userName,
                                        String packageId, String packageName,
                                        int rating, String title, String comment,
                                        String bookingId) {
        String id = IdGenerator.generateReviewId();
        VerifiedReview review = new VerifiedReview(id, userId, userName,
                packageId, packageName, rating, title, comment, IdGenerator.today(), bookingId);
        fileHandler.appendLine(FILE, review.toFileString());
        return review;
    }

    // READ - Get all reviews
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        for (String line : fileHandler.readAll(FILE)) {
            Review r = parseLine(line);
            if (r != null) reviews.add(r);
        }
        return reviews;
    }

    // READ - Get reviews for a specific package
    public List<Review> getByPackage(String packageId) {
        List<Review> result = new ArrayList<>();
        for (Review r : getAllReviews()) {
            if (r.getPackageId().equals(packageId)) result.add(r);
        }
        return result;
    }

    // READ - Find by review ID
    public Review findById(String reviewId) {
        for (Review r : getAllReviews()) {
            if (r.getReviewId().equals(reviewId)) return r;
        }
        return null;
    }

    // UPDATE - Edit a review (title, comment, rating)
    public boolean updateReview(String reviewId, String title, String comment, int rating) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Review r = parseLine(line);
            if (r != null && r.getReviewId().equals(reviewId)) {
                r.setTitle(title);
                r.setComment(comment);
                r.setRating(rating);
                updated.add(r.toFileString()); // OOP: Polymorphism - correct toFileString() called
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // DELETE - Remove a review
    public boolean deleteReview(String reviewId) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Review r = parseLine(line);
            if (r != null && r.getReviewId().equals(reviewId)) {
                found = true; // skip = delete
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // Average rating for a package
    public double averageRating(String packageId) {
        List<Review> reviews = getByPackage(packageId);
        if (reviews.isEmpty()) return 0;
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0);
    }
}
