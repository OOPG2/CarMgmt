package org.example.CarMgmt.objects;

import org.example.CarMgmt.interfaces.CSVSerializable;

public abstract class User implements CSVSerializable {
    private String userId;
    private String password;
    private String role;
    private String name;
    private String email;
    private String phone;

    public User(String userId, String password, String role, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
