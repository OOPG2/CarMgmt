/**
 * The User class serves as an abstract base class implementing the CSVSerializable interface.
 * It encapsulates common attributes for users including userId, password, role, name, email, and phone.
 */
package objects;

import interfaces.CSVSerializable;

/**
 * Abstract base class for User objects that implements CSVSerializable.
 */
public abstract class User implements CSVSerializable {
    /**
     * Unique identifier for the user.
     */
    private String userId;

    /**
     * Password for the user account.
     */
    private String password;

    /**
     * Role of the user (e.g., admin, guest).
     */
    private String role;

    /**
     * Name of the user.
     */
    private String name;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * Phone number of the user.
     */
    private String phone;

    /**
     * Constructs a new User instance with the specified details.
     *
     * @param userId    the unique identifier for the user
     * @param password  the password for the user account
     * @param role      the role of the user
     * @param name      the name of the user
     * @param email     the email address of the user
     * @param phone     the phone number of the user
     */
    public User(String userId, String password, String role, String name, String email, String phone) {
        this.userId = userId;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Returns the unique identifier for the user.
     *
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param userId the unique identifier to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the password for the user account.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for the user account.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role of the user.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the name of the user.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phone the phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
