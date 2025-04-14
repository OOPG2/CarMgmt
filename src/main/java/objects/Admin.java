/**
 * The Admin class extends the abstract User class.
 * It represents a specific type of User with administrative privileges in the system.
 */
package objects;

/**
 * Admin is a concrete implementation of the User class.
 */
public class Admin extends User {
    /**
     * Constructs a new Admin instance with the specified details.
     *
     * @param userId    the unique identifier for the admin
     * @param password  the password for the admin account
     * @param role      the role of the admin
     * @param name      the name of the admin
     * @param email     the email address of the admin
     * @param phone     the phone number of the admin
     */
    public Admin(String userId, String password, String role, String name, String email, String phone) {
        super(userId, password, role, name, email, phone);
    }

    /**
     * Converts the admin object into a CSV row representation.
     *
     * @return an array of Strings containing admin attributes formatted for CSV output
     */
    @Override
    public String[] toCsvRow() {
        return new String[]{
                getUserId(), getPassword(), getRole(), getName(), getEmail(), getPhone(), "0", "0", "false"
        };
    }
}
