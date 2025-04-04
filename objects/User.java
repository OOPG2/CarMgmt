package org.example.CarMgmt.objects;

import com.opencsv.bean.CsvBindByName;

public abstract class User {
    private String userId;
    private String username;
    private String password;
    private String role;
    private int membership;
    private String name;
    private String email;
    private String phone;
    private boolean isBanned;

    public User(String userId, String username, String password, String role, int membership, String name, String email, String phone, boolean isBanned) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.membership = membership;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isBanned = isBanned;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
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

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
