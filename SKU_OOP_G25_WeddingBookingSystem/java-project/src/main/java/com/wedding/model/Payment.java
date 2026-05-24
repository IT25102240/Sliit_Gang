package com.wedding.model;

import com.wedding.util.FileHandler;

// OOP: ENCAPSULATION - payment data is private and controlled
// FIX: toFileString() sanitises all user-supplied string fields
public class Payment {

    private String paymentId;
    private String bookingId;
    private double amount;
    private String method;         // cash, card, bank_transfer
    private String status;         // pending, completed, failed, refunded
    private String transactionRef;
    private String paymentDate;

    public Payment() {}

    public Payment(String paymentId, String bookingId, double amount,
                   String method, String transactionRef, String paymentDate) {
        this.paymentId      = paymentId;
        this.bookingId      = bookingId;
        this.amount         = amount;
        this.method         = method;
        this.status         = "completed";
        this.transactionRef = transactionRef;
        this.paymentDate    = paymentDate;
    }

    // FIX: sanitise transactionRef and method fields
    public String toFileString() {
        return paymentId + "|"
                + bookingId + "|"
                + amount + "|"
                + FileHandler.sanitise(method) + "|"
                + status + "|"
                + FileHandler.sanitise(transactionRef == null ? "N/A" : transactionRef) + "|"
                + paymentDate;
    }

    public static Payment fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 7) return null;
        Payment pay = new Payment(p[0], p[1], Double.parseDouble(p[2]), p[3], p[5], p[6]);
        pay.setStatus(p[4]);
        return pay;
    }

    // Getters and Setters - OOP: ENCAPSULATION
    public String getPaymentId()      { return paymentId; }
    public void   setPaymentId(String paymentId)   { this.paymentId = paymentId; }

    public String getBookingId()      { return bookingId; }
    public void   setBookingId(String bookingId)   { this.bookingId = bookingId; }

    public double getAmount()         { return amount; }
    public void   setAmount(double amount)         { this.amount = amount; }

    public String getMethod()         { return method; }
    public void   setMethod(String method)         { this.method = method; }

    public String getStatus()         { return status; }
    public void   setStatus(String status)         { this.status = status; }

    public String getTransactionRef() { return transactionRef; }
    public void   setTransactionRef(String transactionRef) { this.transactionRef = transactionRef; }

    public String getPaymentDate()    { return paymentDate; }
    public void   setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }
}
