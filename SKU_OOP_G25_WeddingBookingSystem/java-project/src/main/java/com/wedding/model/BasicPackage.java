package com.wedding.model;

import com.wedding.util.FileHandler;
// LAHIRU  IT25102208
// OOP: INHERITANCE - BasicPackage extends WeddingPackage
// OOP: POLYMORPHISM - overrides abstract methods
// FIX: toFileString() sanitises all user-supplied string fields
public class BasicPackage extends WeddingPackage {

    private String includes; // comma-separated list of what's included

    public BasicPackage() {}

    public BasicPackage(String id, String name, String description,
                        double price, int maxGuests, String vendorName, String includes) {
        super(id, name, description, price, maxGuests, vendorName);
        this.includes = includes;
    }

    // OOP: POLYMORPHISM
    @Override
    public String getPackageType() {
        return "Basic";
    }

    // OOP: POLYMORPHISM - basic packages get 5% discount
    @Override
    public double getDiscountedPrice() {
        return getPrice() * 0.95;
    }

    // OOP: POLYMORPHISM
    // FIX: all string fields sanitised
    @Override
    public String toFileString() {
        return "BASIC|"
                + getId() + "|"
                + FileHandler.sanitise(getName()) + "|"
                + FileHandler.sanitise(getDescription()) + "|"
                + getPrice() + "|"
                + getMaxGuests() + "|"
                + FileHandler.sanitise(getVendorName()) + "|"
                + isAvailable() + "|"
                + FileHandler.sanitise(includes);
    }

    public static BasicPackage fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 9) return null;
        BasicPackage pkg = new BasicPackage(p[1], p[2], p[3],
                Double.parseDouble(p[4]), Integer.parseInt(p[5]), p[6], p[8]);
        pkg.setAvailable(Boolean.parseBoolean(p[7]));
        return pkg;
    }

    public String getIncludes() { return includes; }
    public void   setIncludes(String includes) { this.includes = includes; }
}
