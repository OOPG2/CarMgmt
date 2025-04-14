/**
 * The StaffFactory class implements the UserFactory interface for creating Staff objects.
 * It provides a method to construct a Staff instance using user data.
 */
package factories;

import objects.Staff;
import objects.User;

/**
 * StaffFactory is responsible for creating instances of the Staff class.
 */
public class StaffFactory implements UserFactory {
    /**
     * Creates a Staff object using the provided user data.
     *
     * @param userData an array of strings containing user data
     *                 (userId, password, role, name, email, phone)
     * @return a Staff object constructed using the provided data
     */
    @Override
    public User createUser(String[] userData) {
        return new Staff(
                userData[0], // userId
                userData[1], // password
                userData[2], // role
                userData[3], // name
                userData[4], // email
                userData[5]  // phone
        );
    }
}
