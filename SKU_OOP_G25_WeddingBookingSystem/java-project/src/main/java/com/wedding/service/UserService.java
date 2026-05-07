package com.wedding.service;

import com.wedding.model.User;
import com.wedding.util.FileHandler;
import com.wedding.util.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// VIDURA - User Management Service
// FILE HANDLING: reads/writes to users.txt
// OOP: Service layer separates business logic from controller (Abstraction)
@Service
public class UserService {

    private static final String FILE = "users.txt";
    private final FileHandler fileHandler;

    public UserService(@Value("${data.dir}") String dataDir) {
        this.fileHandler = new FileHandler(dataDir);
        seedDefaultUsers();
    }

    // Seed demo users if file is empty
    private void seedDefaultUsers() {
        if (!fileHandler.fileExists(FILE) || fileHandler.readAll(FILE).isEmpty()) {
            User admin = new User(IdGenerator.generateUserId(), "Admin User",
                    "admin@wedding.com", "0771234567", "admin123", "admin");
            User customer = new User(IdGenerator.generateUserId(), "Emma Johnson",
                    "emma@example.com", "0779876543", "customer123", "customer");
            fileHandler.appendLine(FILE, admin.toFileString());
            fileHandler.appendLine(FILE, customer.toFileString());
        }
    }

    // CREATE - Register new user
    public boolean registerUser(String name, String email, String phone,
                                 String password, String role) {
        if (findByEmail(email) != null) return false; // email already exists
        String id = IdGenerator.generateUserId();
        User user = new User(id, name, email, phone, password, role);
        fileHandler.appendLine(FILE, user.toFileString());
        return true;
    }

    // READ - Get all users
    public List<User> getAllUsers() {
        List<String> lines = fileHandler.readAll(FILE);
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            User u = User.fromFileString(line);
            if (u != null) users.add(u);
        }
        return users;
    }

    // READ - Find user by email
    public User findByEmail(String email) {
        for (User u : getAllUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    // READ - Find user by ID
    public User findById(String id) {
        for (User u : getAllUsers()) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    // READ - Search users by name
    public List<User> searchByName(String keyword) {
        List<User> results = new ArrayList<>();
        for (User u : getAllUsers()) {
            if (u.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(u);
            }
        }
        return results;
    }

    // UPDATE - Modify user details
    public boolean updateUser(String id, String name, String phone, String role) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            User u = User.fromFileString(line);
            if (u != null && u.getId().equals(id)) {
                u.setName(name);
                u.setPhone(phone);
                u.setRole(role);
                updated.add(u.toFileString());
                found = true;
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // DELETE - Remove user by ID
    public boolean deleteUser(String id) {
        List<String> lines = fileHandler.readAll(FILE);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            User u = User.fromFileString(line);
            if (u != null && u.getId().equals(id)) {
                found = true; // skip this line (delete)
            } else {
                updated.add(line);
            }
        }
        if (found) fileHandler.writeAll(FILE, updated);
        return found;
    }

    // LOGIN - Authenticate user
    public User login(String email, String password) {
        User user = findByEmail(email);
        if (user != null && user.checkPassword(password)) return user;
        return null;
    }

    // Count users by role
    public long countByRole(String role) {
        return getAllUsers().stream().filter(u -> u.getRole().equals(role)).count();
    }
}
