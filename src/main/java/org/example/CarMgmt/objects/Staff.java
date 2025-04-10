package org.example.CarMgmt.objects;

public class Staff extends User {
    public Staff(String userId, String password, String role, String name, String email, String phone) {
        super(userId, password, role, name, email, phone);
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{
                getUserId(), getPassword(), getRole(), getName(), getEmail(), getPhone(), "0", "0", "false"
        };
    }
}
