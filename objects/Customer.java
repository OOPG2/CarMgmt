package org.example.CarMgmt.objects;

public class Customer extends User {

    private int membership;
    private boolean isBanned;

    public Customer(String userId, String password, String role, int membership, String name, String email, String phone, boolean isBanned) {
        super(userId, password, role, name, email, phone);
        this.membership = membership;
        this.isBanned = isBanned;
    }

    public int getMembership() {
        return membership;
    }

    public void setMembership(int membership) {
        this.membership = membership;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
