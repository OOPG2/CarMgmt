/**
 * The Staff class extends the abstract User class.
 * It represents a specific type of User in the system with additional CSV serialization capabilities.
 */
package objects;

/**
 * Staff is a concrete implementation of the User class.
 */
public class Staff extends User {
    /**
     * Constructs a new Staff instance with the specified details.
     *
     * @param userId    the unique identifier for the staff
     * @param password  the password for the staff account
     * @param role      the role of the staff
     * @param name      the name of the staff
     * @param email     the email address of the staff
     * @param phone     the phone number of the staff
     */
    public Staff(String userId, String password, String role, String name, String email, String phone) {
        super(userId, password, role, name, email, phone);
    }

    /**
     * Converts the staff object into a CSV row representation.
     *
     * @return an array of Strings containing staff attributes formatted for CSV output
     */
    @Override
    public String[] toCsvRow() {
        return new String[]{
                getUserId(), getPassword(), getRole(), getName(), getEmail(), getPhone(), "0", "0", "false"
        };
    }
}
