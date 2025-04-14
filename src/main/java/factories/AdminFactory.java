/**
 * The AdminFactory class implements the UserFactory interface for creating Admin objects.
 * It provides a method to construct an Admin instance using user data.
 */
package factories;

import objects.Admin;
import objects.User;

/**
 * AdminFactory is responsible for creating instances of the Admin class.
 */
public class AdminFactory implements UserFactory {
    /**
     * Creates an Admin object using the provided user data.
     *
     * @param userData an array of strings containing user data
     *                 (userId, password, role, name, email, phone)
     * @return an Admin object constructed using the provided data
     */
    @Override
    public User createUser(String[] userData) {
        return new Admin(
                userData[0], // userId
                userData[1], // password
                userData[2], // role
                userData[3], // name
                userData[4], // email
                userData[5]  // phone
        );
    }
}
