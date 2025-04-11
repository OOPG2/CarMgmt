package objects;

public class Customer extends User {
    private int lifetimePoints;
    private int loyaltyPoints;
    private boolean isBanned;

    public Customer(String userId, String password, String role, String name, String email, String phone, int lifetimePoints, int loyaltyPoints, boolean isBanned) {
        super(userId, password, role, name, email, phone);
        this.lifetimePoints = lifetimePoints;
        this.loyaltyPoints = loyaltyPoints;
        this.isBanned = isBanned;
    }

    @Override
    public String[] toCsvRow() {
        return new String[]{
                getUserId(), getPassword(), getRole(), getName(), getEmail(), getPhone(), String.valueOf(lifetimePoints), String.valueOf(loyaltyPoints), String.valueOf(isBanned)
        };
    }

    public Integer getLifetimePoints() {
        return lifetimePoints;
    }

    public void setLifetimePoints(int lifetimePoints) {
        this.lifetimePoints = lifetimePoints;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
