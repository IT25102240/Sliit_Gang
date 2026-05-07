package com.wedding.model;

// OOP: ABSTRACTION - abstract base for all wedding packages
// OOP: ENCAPSULATION - fields are private with getters/setters
public abstract class WeddingPackage {

    private String id;
    private String name;
    private String description;
    private double price;
    private int maxGuests;
    private String vendorName;
    private boolean available;

    public WeddingPackage() {}

    public WeddingPackage(String id, String name, String description,
                          double price, int maxGuests, String vendorName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxGuests = maxGuests;
        this.vendorName = vendorName;
        this.available = true;
    }

    // OOP: ABSTRACTION - subclasses must define their type
    public abstract String getPackageType();

    // OOP: ABSTRACTION - subclasses must define file format
    public abstract String toFileString();

    // OOP: ABSTRACTION - different packages calculate discounted price differently
    public abstract double getDiscountedPrice();

    // Getters and Setters - OOP: ENCAPSULATION
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getMaxGuests() { return maxGuests; }
    public void setMaxGuests(int maxGuests) { this.maxGuests = maxGuests; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return "[" + getPackageType() + "] " + name + " | $" + price + " | " + maxGuests + " guests | " + vendorName;
    }
}
