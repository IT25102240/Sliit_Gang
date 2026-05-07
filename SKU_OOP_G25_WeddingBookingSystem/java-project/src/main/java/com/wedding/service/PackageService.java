package com.wedding.service;

import com.wedding.model.BasicPackage;
import com.wedding.model.PremiumPackage;
import com.wedding.model.WeddingPackage;
import com.wedding.util.FileHandler;
import com.wedding.util.IdGenerator;
import com.wedding.util.VendorLinkedList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// LAHIRU - Wedding Package Management Service
// Uses: VendorLinkedList (Linked List) + Bubble Sort
// FILE HANDLING: reads/writes to packages.txt
@Service
public class PackageService {

    private static final String FILE = "packages.txt";
    private final FileHandler fileHandler;

    public PackageService(@Value("${data.dir}") String dataDir) {
        this.fileHandler = new FileHandler(dataDir);
        seedDefaultPackages();
    }

    private void seedDefaultPackages() {
        if (!fileHandler.fileExists(FILE) || fileHandler.readAll(FILE).isEmpty()) {
            BasicPackage p1 = new BasicPackage(IdGenerator.generatePackageId(),
                    "Garden Ceremony", "An intimate outdoor garden ceremony",
                    2500.0, 80, "Green Garden Venue", "Flowers,Chairs,Officiant");
            PremiumPackage p2 = new PremiumPackage(IdGenerator.generatePackageId(),
                    "Grand Ballroom Reception", "Luxurious ballroom with full catering",
                    8500.0, 300, "Royal Grand Hotel",
                    "Catering,DJ,Flowers,Photography", "Honeymoon Suite,Limo");
            BasicPackage p3 = new BasicPackage(IdGenerator.generatePackageId(),
                    "Beach Wedding", "Romantic beachfront ceremony",
                    3800.0, 100, "Sunset Beach Venue", "Arch,Chairs,Sound System");
            fileHandler.appendLine(FILE, p1.toFileString());
            fileHandler.appendLine(FILE, p2.toFileString());
            fileHandler.appendLine(FILE, p3.toFileString());
        }
    }

    // Load all packages into a LinkedList (using our custom data structure)
    private VendorLinkedList loadIntoLinkedList() {
        VendorLinkedList list = new VendorLinkedList();
        for (String line : fileHandler.readAll(FILE)) {
            WeddingPackage pkg = parseLine(line);
            if (pkg != null) list.add(pkg);
        }
        return list;
    }

    private WeddingPackage parseLine(String line) {
        if (line.startsWith("BASIC")) return BasicPackage.fromFileString(line);
        if (line.startsWith("PREMIUM")) return PremiumPackage.fromFileString(line);
        return null;
    }

    // CREATE - Add new package
    public void addPackage(String type, String name, String description,
                           double price, int maxGuests, String vendorName,
                           String includes, String addOns) {
        String id = IdGenerator.generatePackageId();
        WeddingPackage pkg;
        if ("PREMIUM".equalsIgnoreCase(type)) {
            pkg = new PremiumPackage(id, name, description, price, maxGuests, vendorName, includes, addOns);
        } else {
            pkg = new BasicPackage(id, name, description, price, maxGuests, vendorName, includes);
        }
        fileHandler.appendLine(FILE, pkg.toFileString());
    }

    // READ - Get all packages (sorted by price using Bubble Sort in LinkedList)
    public List<WeddingPackage> getAllSortedByPrice() {
        VendorLinkedList list = loadIntoLinkedList();
        list.sortByPrice(); // BUBBLE SORT
        WeddingPackage[] arr = list.toArray();
        List<WeddingPackage> result = new ArrayList<>();
        for (WeddingPackage p : arr) result.add(p);
        return result;
    }

    // READ - Get all packages sorted by name
    public List<WeddingPackage> getAllSortedByName() {
        VendorLinkedList list = loadIntoLinkedList();
        list.sortByName(); // BUBBLE SORT
        WeddingPackage[] arr = list.toArray();
        List<WeddingPackage> result = new ArrayList<>();
        for (WeddingPackage p : arr) result.add(p);
        return result;
    }

    // READ - Get all (unsorted)
    public List<WeddingPackage> getAll() {
        List<WeddingPackage> result = new ArrayList<>();
        for (String line : fileHandler.readAll(FILE)) {
            WeddingPackage p = parseLine(line);
            if (p != null) result.add(p);
        }
        return result;
    }

    // READ - Find by ID
    public WeddingPackage findById(String id) {
        VendorLinkedList list = loadIntoLinkedList();
        return list.findById(id);
    }

    // READ - Search by name
    public List<WeddingPackage> searchByName(String keyword) {
        List<WeddingPackage> results = new ArrayList<>();
        for (WeddingPackage p : getAll()) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    // UPDATE - Edit package
    public boolean updatePackage(String id, String name, String description,
                                  double price, int maxGuests, String vendorName) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            WeddingPackage pkg = parseLine(line);
            if (pkg != null && pkg.getId().equals(id)) {
                pkg.setName(name);
                pkg.setDescription(description);
                pkg.setPrice(price);
                pkg.setMaxGuests(maxGuests);
                pkg.setVendorName(vendorName);
                updated.add(pkg.toFileString());
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // DELETE - Remove package by ID
    public boolean deletePackage(String id) {
        VendorLinkedList list = loadIntoLinkedList();
        if (!list.remove(id)) return false;
        // Rewrite file from updated linked list
        List<String> updated = new ArrayList<>();
        for (WeddingPackage p : list.toArray()) updated.add(p.toFileString());
        fileHandler.writeAll(FILE, updated);
        return true;
    }

    public long countPackages() {
        return getAll().size();
    }
}
