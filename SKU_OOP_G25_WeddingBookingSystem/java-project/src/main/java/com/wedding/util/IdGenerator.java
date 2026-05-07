package com.wedding.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Utility to generate unique IDs for each entity
public class IdGenerator {

    public static String generateUserId() {
        return "USR" + System.currentTimeMillis() % 100000;
    }

    public static String generatePackageId() {
        return "PKG" + System.currentTimeMillis() % 100000;
    }

    public static String generateBookingId() {
        return "BKG" + System.currentTimeMillis() % 100000;
    }

    public static String generatePaymentId() {
        return "PAY" + System.currentTimeMillis() % 100000;
    }

    public static String generateReviewId() {
        return "REV" + System.currentTimeMillis() % 100000;
    }

    public static String today() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
