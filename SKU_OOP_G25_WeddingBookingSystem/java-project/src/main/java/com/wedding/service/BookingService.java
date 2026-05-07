package com.wedding.service;

import com.wedding.model.Booking;
import com.wedding.util.FileHandler;
import com.wedding.util.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// DAHAM - Booking Management Service
// FILE HANDLING: reads/writes to bookings.txt
// OOP: Encapsulation — all booking logic contained here
@Service
public class BookingService {

    private static final String FILE = "bookings.txt";
    private final FileHandler fileHandler;

    public BookingService(@Value("${data.dir}") String dataDir) {
        this.fileHandler = new FileHandler(dataDir);
    }

    // CREATE - New booking
    public Booking createBooking(String userId, String packageId, String packageName,
                                  String eventDate, int guestCount,
                                  String venueName, double totalAmount,
                                  String specialRequests) {
        String id = IdGenerator.generateBookingId();
        Booking booking = new Booking(id, userId, packageId, packageName,
                eventDate, guestCount, venueName, totalAmount, IdGenerator.today());
        booking.setSpecialRequests(specialRequests);
        fileHandler.appendLine(FILE, booking.toFileString());
        return booking;
    }

    // READ - Get all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (String line : fileHandler.readAll(FILE)) {
            Booking b = Booking.fromFileString(line);
            if (b != null) bookings.add(b);
        }
        return bookings;
    }

    // READ - Get bookings for a specific user
    public List<Booking> getBookingsByUser(String userId) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : getAllBookings()) {
            if (b.getUserId().equals(userId)) result.add(b);
        }
        return result;
    }

    // READ - Find booking by ID
    public Booking findById(String bookingId) {
        for (Booking b : getAllBookings()) {
            if (b.getBookingId().equals(bookingId)) return b;
        }
        return null;
    }

    // READ - Get bookings by status
    public List<Booking> getByStatus(String status) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : getAllBookings()) {
            if (b.getStatus().equalsIgnoreCase(status)) result.add(b);
        }
        return result;
    }

    // UPDATE - Change booking status (confirm / cancel / complete)
    public boolean updateStatus(String bookingId, String newStatus) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Booking b = Booking.fromFileString(line);
            if (b != null && b.getBookingId().equals(bookingId)) {
                b.setStatus(newStatus);
                updated.add(b.toFileString());
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // UPDATE - Edit booking details
    public boolean updateBooking(String bookingId, String eventDate,
                                  int guestCount, String venueName, String specialRequests) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Booking b = Booking.fromFileString(line);
            if (b != null && b.getBookingId().equals(bookingId)) {
                b.setEventDate(eventDate);
                b.setGuestCount(guestCount);
                b.setVenueName(venueName);
                b.setSpecialRequests(specialRequests);
                updated.add(b.toFileString());
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // DELETE - Remove a booking
    public boolean deleteBooking(String bookingId) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Booking b = Booking.fromFileString(line);
            if (b != null && b.getBookingId().equals(bookingId)) {
                found = true; // skip = delete
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // ANALYTICS helpers
    public long countByStatus(String status) {
        return getByStatus(status).size();
    }

    public double totalRevenue() {
        return getAllBookings().stream()
                .filter(b -> !b.getStatus().equals("cancelled"))
                .mapToDouble(Booking::getTotalAmount)
                .sum();
    }
}
