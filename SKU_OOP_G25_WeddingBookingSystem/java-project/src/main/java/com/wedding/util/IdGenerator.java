package com.wedding.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// Utility to generate unique IDs for each entity
// FIX: replaced System.currentTimeMillis() % 100000 with UUID to prevent collisions
public class IdGenerator {

    // Generates a short 8-char uppercase UUID prefix  e.g. "USR-A3F7B2C1"
    private static String shortUUID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    public static String generateUserId() {
        return "USR-" + shortUUID();
    }

    public static String generatePackageId() {
        return "PKG-" + shortUUID();
    }

    public static String generateBookingId() {
        return "BKG-" + shortUUID();
    }

    public static String generatePaymentId() {
        return "PAY-" + shortUUID();
    }

    public static String generateReviewId() {
        return "REV-" + shortUUID();
    }

    public static String today() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
