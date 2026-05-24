package com.wedding.model;

import com.wedding.util.FileHandler;

// OOP: INHERITANCE - PremiumPackage extends WeddingPackage
// OOP: POLYMORPHISM - overrides abstract methods differently from BasicPackage
// FIX: toFileString() sanitises all user-supplied string fields
public class PremiumPackage extends WeddingPackage {

    private String includes;
    private String addOns; // additional luxury services

    public PremiumPackage() {}

    public PremiumPackage(String id, String name, String description,
                          double price, int maxGuests, String vendorName,
                          String includes, String addOns) {
        super(id, name, description, price, maxGuests, vendorName);
        this.includes = includes;
        this.addOns   = addOns;
    }

    // OOP: POLYMORPHISM
    @Override
    public String getPackageType() {
        return "Premium";
    }

    // OOP: POLYMORPHISM - premium packages get 10% discount
    @Override
    public double getDiscountedPrice() {
        return getPrice() * 0.90;
    }

    // OOP: POLYMORPHISM
    // FIX: all string fields sanitised
    @Override
    public String toFileString() {
        return "PREMIUM|"
                + getId() + "|"
                + FileHandler.sanitise(getName()) + "|"
                + FileHandler.sanitise(getDescription()) + "|"
                + getPrice() + "|"
                + getMaxGuests() + "|"
                + FileHandler.sanitise(getVendorName()) + "|"
                + isAvailable() + "|"
                + FileHandler.sanitise(includes) + "|"
                + FileHandler.sanitise(addOns);
    }

    public static PremiumPackage fromFileString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 10) return null;
        PremiumPackage pkg = new PremiumPackage(p[1], p[2], p[3],
                Double.parseDouble(p[4]), Integer.parseInt(p[5]), p[6], p[8], p[9]);
        pkg.setAvailable(Boolean.parseBoolean(p[7]));
        return pkg;
    }

    public String getIncludes() { return includes; }
    public void   setIncludes(String includes) { this.includes = includes; }

    public String getAddOns()   { return addOns; }
    public void   setAddOns(String addOns) { this.addOns = addOns; }
}
