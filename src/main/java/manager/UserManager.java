/**
 * The UserManager class handles CRUD operations for User objects.
 * It interacts with UserStorage and UserFactory to create, read, update, and delete user data.
 */
package manager;

import constants.Constants;
import factories.UserFactory;
import interfaces.UserStorage;
import objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserManager facilitates managing user data and supports operations such as creation,
 * retrieval, updating, and deletion of User objects.
 */
public class UserManager {
    /**
     * The UserStorage instance used to manage user data storage.
     */
    private final UserStorage storage;

    /**
     * A map of user roles to corresponding UserFactory implementations.
     */
    private final Map<String, UserFactory> factories;

    /**
     * The Constants instance used for default values.
     */
    private final Constants constants;

    /**
     * Constructs a UserManager instance with the specified storage, factories, and constants.
     *
     * @param storage   the UserStorage instance used for managing user data
     * @param factories a map of user roles to corresponding UserFactory implementations
     * @param constants the Constants instance used for default values
     */
    public UserManager(UserStorage storage, Map<String, UserFactory> factories, Constants constants) {
        this.storage = storage;
        this.factories = factories;
        this.constants = constants;
    }

    /**
     * Creates a new user and appends it to the user storage.
     *
     * @param userId the unique identifier for the user
     * @param name   the name of the user
     * @param email  the email address of the user
     * @param phone  the phone number of the user
     * @param role   the role of the user (e.g., Admin, Customer, Staff)
     */
    public void createUser(String userId, String name, String email, String phone, String role) {
        if (name != null && email != null && phone != null && role != null) {
            String[] userInfo = new String[]{
                    userId,
                    constants.getDefaultPassword(),
                    role,
                    name,
                    email,
                    phone,
                    Integer.toString(0),
                    Integer.toString(0),
                    Boolean.toString(false)
            };

            storage.appendFile(userInfo);
        }
    }

    /**
     * Retrieves all users from the storage and maps them by user ID.
     *
     * @return a map of user IDs to corresponding User objects
     */
    public Map<String, User> getUsers() {
        Map<String, User> users = new HashMap<>();
        List<String[]> userInfos = storage.readFile();
        for (String[] userInfo : userInfos) {
            if (userInfo.length > 0) {
                String role = userInfo[2];
                UserFactory factory = factories.get(role);

                if (factory == null) {
                    throw new IllegalArgumentException("Unknown user role (getUsers): " + role);
                }

                User user = factory.createUser(userInfo);
                users.put(user.getUserId(), user);
            }
        }
        return users;
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId the ID of the user to retrieve
     * @return the User object corresponding to the provided ID, or null if not found
     */
    public User getUserByID(String userId) {
        return getUsers().get(userId);
    }

    /**
     * Updates a user in the storage with new information.
     *
     * @param user the User object containing updated information
     */
    public void updateUser(User user) {
        Map<String, User> users = getUsers();
        List<String[]> userInfos = new ArrayList<>();
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            for (User u : users.values()) {
                userInfos.add(u.toCsvRow());
            }
            storage.writeFile(userInfos);
        } else {
            System.err.println("Error (updateUser): User to update not found.");
        }
    }

    /**
     * Deletes a user by their unique identifier from the storage.
     *
     * @param userId the ID of the user to delete
     */
    public void deleteUser(String userId) {
        List<String[]> userInfos = storage.readFile();
        userInfos.removeIf(userInfo -> userInfo.length > 0 && userInfo[0].equals(userId));
        storage.writeFile(userInfos);
    }
}
