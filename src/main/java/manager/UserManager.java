package manager;

import constants.*;
import factories.*;
import interfaces.*;
import objects.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


// CRUD (User)
public class UserManager {
    private final UserStorage storage;
    private final Map<String, UserFactory> factories;
    private final Constants constants;

    public UserManager(UserStorage storage, Map<String, UserFactory> factories, Constants constants) {
        this.storage = storage;
        this.factories = factories;
        this.constants = constants;
    }

    // Create
    public void createUser(String userId, String name, String email, String phone, String role) {
        if (name != null && email != null  && phone != null && role != null) {
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


    // Read
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

    public User getUserByID(String userId) {
        return getUsers().get(userId);
    }

    // Update
    public void updateUser(User user) {
        Map<String, User> users = getUsers();
        List<String[]> userInfos = new ArrayList<>();
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            for (User u : users.values()) {
                userInfos.add(u.toCsvRow());
            }
            storage.writeFile(userInfos);
        }
        else {
            System.err.println("Error (updateUser): User to update not found.");
        }
    }


    // Delete
    public void deleteUser(String userId) {
        List<String[]> userInfos = storage.readFile();
        userInfos.removeIf(userInfo -> userInfo.length > 0 && userInfo[0].equals(userId));
        storage.writeFile(userInfos);
    }
}