/**
 * The UserFactory interface defines a method for creating User objects.
 * Classes implementing this interface should provide their own logic for constructing User instances.
 */
package factories;

import objects.User;

/**
 * UserFactory provides an abstraction for creating User objects from input data.
 */
public interface UserFactory {
    /**
     * Creates a User object using the provided user data.
     *
     * @param userData an array of strings containing user data
     * @return a User object constructed using the provided data
     */
    User createUser(String[] userData);
}
