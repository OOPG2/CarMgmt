/**
 * The CustomerFactory class implements the UserFactory interface for creating Customer objects.
 * It provides a method to construct a Customer instance using user data.
 */
package factories;

import objects.Customer;
import objects.User;

/**
 * CustomerFactory is responsible for creating instances of the Customer class.
 */
public class CustomerFactory implements UserFactory {
    /**
     * Creates a Customer object using the provided user data.
     *
     * @param userData an array of strings containing user data
     *                 (userId, password, role, name, email, phone, lifetimePoints, loyaltyPoints, isBanned)
     * @return a Customer object constructed using the provided data
     */
    @Override
    public User createUser(String[] userData) {
        return new Customer(
                userData[0], // userId
                userData[1], // password
                userData[2], // role
                userData[3], // name
                userData[4], // email
                userData[5], // phone
                Integer.parseInt(userData[6]), // lifetimePoints
                Integer.parseInt(userData[7]), // loyaltyPoints
                Boolean.parseBoolean(userData[8]) // isBanned
        );
    }
}
