package org.example.CarMgmt.objects;

public class Staff extends User {
    public Staff(String userId, String username, String password, String role, int membership, String name, String email, String phone, boolean isBanned) {
        super(userId, username, password, role, membership, name, email, phone, isBanned);
    }
}
