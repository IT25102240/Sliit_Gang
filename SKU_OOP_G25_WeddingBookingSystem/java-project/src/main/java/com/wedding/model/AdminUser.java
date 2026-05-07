package com.wedding.model;

// OOP: INHERITANCE - AdminUser extends User (multi-level inheritance)
// OOP: POLYMORPHISM - overrides getRole() and toFileString()
public class AdminUser extends User {

    private String department;
    private int accessLevel; // 1 = basic admin, 2 = super admin

    public AdminUser() {}

    public AdminUser(String id, String name, String email, String phone,
                     String password, String department, int accessLevel) {
        super(id, name, email, phone, password, "admin");
        this.department = department;
        this.accessLevel = accessLevel;
    }

    // OOP: POLYMORPHISM - overrides Person.getRole()
    @Override
    public String getRole() {
        return "admin (Level " + accessLevel + ")";
    }

    // OOP: POLYMORPHISM - overrides Person.toFileString()
    @Override
    public String toFileString() {
        return getId() + "|" + getName() + "|" + getEmail() + "|" + getPhone()
                + "|" + getPassword() + "|admin|" + department + "|" + accessLevel;
    }

    // Check if admin has super access
    public boolean isSuperAdmin() {
        return accessLevel >= 2;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }
}
