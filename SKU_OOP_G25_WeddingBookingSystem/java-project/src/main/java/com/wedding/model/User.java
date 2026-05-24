package com.wedding.model;

import com.wedding.util.FileHandler;
import com.wedding.util.PasswordUtil;

// OOP: INHERITANCE - User extends Person
// OOP: ENCAPSULATION - password is private and HASHED — never stored plain-text
// FIX: password is now stored as a SHA-256 hash via PasswordUtil.hash()
//      checkPassword() verifies by hashing the input and comparing hashes.
public class User extends Person {

    private String password; // stored as SHA-256 hex hash
    private String role;     // "customer" or "admin"

    public User() {}

    // Constructor — accepts PLAIN-TEXT password; hashes it internally
    public User(String id, String name, String email, String phone, String password, String role) {
        super(id, name, email, phone);
        // Hash on the way in so plain text never lives in memory beyond this call
        this.password = PasswordUtil.hash(password);
        this.role = role;
    }

    // OOP: POLYMORPHISM - overrides abstract method from Person
    @Override
    public String getRole() {
        return role;
    }

    // OOP: POLYMORPHISM - overrides abstract toFileString
    // FIX: all string fields passed through FileHandler.sanitise()
    @Override
    public String toFileString() {
        return FileHandler.sanitise(getId()) + "|"
                + FileHandler.sanitise(getName()) + "|"
                + FileHandler.sanitise(getEmail()) + "|"
                + FileHandler.sanitise(getPhone()) + "|"
                + password + "|"   // already a hex hash — no pipes possible
                + FileHandler.sanitise(role);
    }

    // Parse a line from users.txt back into a User or AdminUser object
    // FIX: now correctly returns AdminUser when role is "admin" and
    //      extra fields (department, accessLevel) are present in the line.
    public static User fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;

        String role = parts[5];

        // FIX: deserialise AdminUser correctly (was always creating plain User before)
        if ("admin".equals(role) && parts.length >= 8) {
            AdminUser admin = new AdminUser();
            admin.setId(parts[0]);
            admin.setName(parts[1]);
            admin.setEmail(parts[2]);
            admin.setPhone(parts[3]);
            admin.setHashedPassword(parts[4]);
            admin.setRole("admin");
            admin.setDepartment(parts[6]);
            try { admin.setAccessLevel(Integer.parseInt(parts[7])); }
            catch (NumberFormatException e) { admin.setAccessLevel(1); }
            return admin;
        }

        User user = new User();
        user.setId(parts[0]);
        user.setName(parts[1]);
        user.setEmail(parts[2]);
        user.setPhone(parts[3]);
        user.setHashedPassword(parts[4]);  // restores the stored hash directly
        user.setRole(parts[5]);
        return user;
    }

    // Verify a plain-text password attempt against the stored hash
    public boolean checkPassword(String input) {
        return PasswordUtil.verify(input, this.password);
    }

    public String getPassword() { return password; }

    // For internal use only — sets the hash directly (used by fromFileString)
    public void setHashedPassword(String hash) { this.password = hash; }

    // Sets a NEW password — hashes the plain-text input before storing
    public void setPassword(String plainText) { this.password = PasswordUtil.hash(plainText); }

    public void setRole(String role) { this.role = role; }
}
