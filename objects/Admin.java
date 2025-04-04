package org.example.CarMgmt.objects;

public class Admin extends User {
    public Admin(String userId, String username, String password, String role, int membership, String name, String email, String phone, boolean isBanned) {
        super(userId, username, password, role, membership, name, email, phone, isBanned);
    }
}
