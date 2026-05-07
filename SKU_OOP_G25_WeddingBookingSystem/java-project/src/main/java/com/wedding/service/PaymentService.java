package com.wedding.service;

import com.wedding.model.Payment;
import com.wedding.util.FileHandler;
import com.wedding.util.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// DAHAM - Payment Management Service
// FILE HANDLING: reads/writes to payments.txt
@Service
public class PaymentService {

    private static final String FILE = "payments.txt";
    private final FileHandler fileHandler;

    public PaymentService(@Value("${data.dir}") String dataDir) {
        this.fileHandler = new FileHandler(dataDir);
    }

    // CREATE - Record new payment
    public Payment recordPayment(String bookingId, double amount,
                                  String method, String transactionRef) {
        String id = IdGenerator.generatePaymentId();
        Payment payment = new Payment(id, bookingId, amount, method, transactionRef, IdGenerator.today());
        fileHandler.appendLine(FILE, payment.toFileString());
        return payment;
    }

    // READ - All payments
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        for (String line : fileHandler.readAll(FILE)) {
            Payment p = Payment.fromFileString(line);
            if (p != null) payments.add(p);
        }
        return payments;
    }

    // READ - Payments by booking ID
    public List<Payment> getByBookingId(String bookingId) {
        List<Payment> result = new ArrayList<>();
        for (Payment p : getAllPayments()) {
            if (p.getBookingId().equals(bookingId)) result.add(p);
        }
        return result;
    }

    // READ - Find by payment ID
    public Payment findById(String paymentId) {
        for (Payment p : getAllPayments()) {
            if (p.getPaymentId().equals(paymentId)) return p;
        }
        return null;
    }

    // UPDATE - Update payment status (e.g. mark as refunded)
    public boolean updateStatus(String paymentId, String newStatus) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Payment p = Payment.fromFileString(line);
            if (p != null && p.getPaymentId().equals(paymentId)) {
                p.setStatus(newStatus);
                updated.add(p.toFileString());
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // DELETE - Remove a payment record
    public boolean deletePayment(String paymentId) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            Payment p = Payment.fromFileString(line);
            if (p != null && p.getPaymentId().equals(paymentId)) {
                found = true; // skip = delete
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // Total collected amount for a booking
    public double getTotalPaid(String bookingId) {
        return getByBookingId(bookingId).stream()
                .filter(p -> p.getStatus().equals("completed"))
                .mapToDouble(Payment::getAmount)
                .sum();
    }
}
