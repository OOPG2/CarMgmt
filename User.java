package org.example.crms;

import com.opencsv.bean.CsvBindByName;

/**
 * Model class for a user in the reservation system.
 * Each user has an ID, username, password, and role (e.g., Customer or Staff).
 * Uses OpenCSV annotations for CSV binding.
 */
public class User {
    @CsvBindByName(column = "id")
    private int id;
    @CsvBindByName(column = "username")
    private String username;
    @CsvBindByName(column = "password")
    private String password;
    @CsvBindByName(column = "role")
    private String role;

    public User() {
        // No-arg constructor for OpenCSV
    }

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

