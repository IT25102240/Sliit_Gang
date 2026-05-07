package com.wedding.model;

// OOP: INHERITANCE - User extends Person
// OOP: ENCAPSULATION - password is private, never exposed directly
public class User extends Person {

    private String password;
    private String role; // "customer" or "admin"

    public User() {}

    public User(String id, String name, String email, String phone, String password, String role) {
        super(id, name, email, phone);
        this.password = password;
        this.role = role;
    }

    // OOP: POLYMORPHISM - overrides abstract method from Person
    @Override
    public String getRole() {
        return role;
    }

    // OOP: POLYMORPHISM - overrides abstract toFileString
    @Override
    public String toFileString() {
        return getId() + "|" + getName() + "|" + getEmail() + "|" + getPhone() + "|" + password + "|" + role;
    }

    // Parse a line from users.txt back into a User object
    public static User fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;
        return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }

    public boolean checkPassword(String input) {
        return this.password.equals(input);
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
