package com.wedding.model;

// OOP: ENCAPSULATION - all fields private, accessed via getters/setters
public class Booking {

    private String bookingId;
    private String userId;
    private String packageId;
    private String packageName;
    private String eventDate;     // format: YYYY-MM-DD
    private int guestCount;
    private String venueName;
    private String status;        // pending, confirmed, cancelled, completed
    private double totalAmount;
    private String createdDate;
    private String specialRequests;

    public Booking() {}

    public Booking(String bookingId, String userId, String packageId, String packageName,
                   String eventDate, int guestCount, String venueName,
                   double totalAmount, String createdDate) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.packageId = packageId;
        this.packageName = packageName;
        this.eventDate = eventDate;
        this.guestCount = guestCount;
        this.venueName = venueName;
        this.status = "pending";
        this.totalAmount = totalAmount;
        this.createdDate = createdDate;
        this.specialRequests = "";
    }

    public String toFileString() {
        return bookingId + "|" + userId + "|" + packageId + "|" + packageName
                + "|" + eventDate + "|" + guestCount + "|" + venueName
                + "|" + status + "|" + totalAmount + "|" + createdDate
                + "|" + (specialRequests == null ? "" : specialRequests.replace("|", ","));
    }

    public static Booking fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 10) return null;
        Booking b = new Booking(p[0], p[1], p[2], p[3], p[4],
                Integer.parseInt(p[5]), p[6], Double.parseDouble(p[8]), p[9]);
        b.setStatus(p[7]);
        if (p.length > 10) b.setSpecialRequests(p[10]);
        return b;
    }

    // Getters and Setters - OOP: ENCAPSULATION
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public int getGuestCount() { return guestCount; }
    public void setGuestCount(int guestCount) { this.guestCount = guestCount; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
}
