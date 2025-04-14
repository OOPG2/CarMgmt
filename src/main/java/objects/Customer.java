/**
 * The Customer class extends the abstract User class.
 * It represents a specific type of User with additional attributes and CSV serialization capabilities.
 */
package objects;

/**
 * Customer is a concrete implementation of the User class, with fields for tracking
 * customer-specific details such as points and account status.
 */
public class Customer extends User {
    /**
     * The lifetime points accumulated by the customer.
     */
    private int lifetimePoints;

    /**
     * The loyalty points currently available for the customer.
     */
    private int loyaltyPoints;

    /**
     * A flag indicating whether the customer is banned.
     */
    private boolean isBanned;

    /**
     * Constructs a new Customer instance with the specified details.
     *
     * @param userId         the unique identifier for the customer
     * @param password       the password for the customer account
     * @param role           the role of the customer
     * @param name           the name of the customer
     * @param email          the email address of the customer
     * @param phone          the phone number of the customer
     * @param lifetimePoints the lifetime points of the customer
     * @param loyaltyPoints  the loyalty points of the customer
     * @param isBanned       the banned status of the customer
     */
    public Customer(String userId, String password, String role, String name, String email, String phone,
                    int lifetimePoints, int loyaltyPoints, boolean isBanned) {
        super(userId, password, role, name, email, phone);
        this.lifetimePoints = lifetimePoints;
        this.loyaltyPoints = loyaltyPoints;
        this.isBanned = isBanned;
    }

    /**
     * Converts the customer object into a CSV row representation.
     *
     * @return an array of Strings containing customer attributes formatted for CSV output
     */
    @Override
    public String[] toCsvRow() {
        return new String[]{
                getUserId(), getPassword(), getRole(), getName(), getEmail(), getPhone(),
                String.valueOf(lifetimePoints), String.valueOf(loyaltyPoints), String.valueOf(isBanned)
        };
    }

    /**
     * Returns the lifetime points of the customer.
     *
     * @return the lifetime points
     */
    public int getLifetimePoints() {
        return lifetimePoints;
    }

    /**
     * Sets the lifetime points of the customer.
     *
     * @param lifetimePoints the lifetime points to set
     */
    public void setLifetimePoints(int lifetimePoints) {
        this.lifetimePoints = lifetimePoints;
    }

    /**
     * Returns the loyalty points of the customer.
     *
     * @return the loyalty points
     */
    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    /**
     * Sets the loyalty points of the customer.
     *
     * @param loyaltyPoints the loyalty points to set
     */
    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    /**
     * Returns whether the customer is banned.
     *
     * @return true if the customer is banned, otherwise false
     */
    public boolean getIsBanned() {
        return isBanned;
    }

    /**
     * Sets the banned status of the customer.
     *
     * @param isBanned the banned status to set
     */
    public void setIsBanned(boolean isBanned) {
        this.isBanned = isBanned;
    }
}
