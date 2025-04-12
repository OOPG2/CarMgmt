package manager;

import objects.Customer;
import objects.User;

import java.util.Map;

public class AuthenticationManager {
    private final UserManager userManager;
    private static User loggedUser = null;

    public AuthenticationManager(UserManager userManager) {
        this.userManager = userManager;
    }

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
        }
        catch (Exception e) {
            System.err.println("Error verifying user (loginUser): " + e.getMessage());
        }
        return false;
    }

    public void logoutUser() {
        setLoggedUser(null);
    }

    public boolean isUserBanned(String userId) {
        User user = userManager.getUserByID(userId);
        return user instanceof Customer && ((Customer) user).getIsBanned();
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        AuthenticationManager.loggedUser = loggedUser;
    }
}
