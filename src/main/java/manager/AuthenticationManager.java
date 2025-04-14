/**
 * The AuthenticationManager class handles user authentication and session management.
 * It provides methods for logging in, logging out, checking user ban status,
 * and managing the currently logged-in user.
 */
package manager;

import objects.Customer;
import objects.User;

import java.util.Map;

/**
 * AuthenticationManager facilitates user authentication by interacting with UserManager
 * and tracks the currently logged-in user.
 */
public class AuthenticationManager {
    /**
     * An instance of UserManager used to manage and retrieve user data.
     */
    private final UserManager userManager;

    /**
     * The currently logged-in user, tracked as a static field.
     */
    private static User loggedUser = null;

    /**
     * Constructs an AuthenticationManager instance with the specified UserManager.
     *
     * @param userManager an instance of UserManager used to manage user data
     */
    public AuthenticationManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Attempts to log in a user with the provided user ID and password.
     *
     * @param userId   the ID of the user attempting to log in
     * @param password the password of the user attempting to log in
     * @return true if the login is successful; false otherwise
     */
    public boolean loginUser(String userId, String password) {
        try {
            Map<String, User> users = userManager.getUsers();
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                    setLoggedUser(user);
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error verifying user (loginUser): " + e.getMessage());
        }
        return false;
    }

    /**
     * Logs out the currently logged-in user by clearing the session.
     */
    public void logoutUser() {
        setLoggedUser(null);
    }

    /**
     * Checks whether a user with the specified ID is banned.
     *
     * @param userId the ID of the user to check
     * @return true if the user is banned; false otherwise
     */
    public boolean isUserBanned(String userId) {
        User user = userManager.getUserByID(userId);
        return user instanceof Customer && ((Customer) user).getIsBanned();
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return the User object representing the currently logged-in user, or null if no user is logged in
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param loggedUser the User object to set as the currently logged-in user
     */
    public void setLoggedUser(User loggedUser) {
        AuthenticationManager.loggedUser = loggedUser;
    }
}
