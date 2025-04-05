package org.example.CarMgmt.manager;

import org.example.CarMgmt.objects.Admin;
import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.Staff;
import org.example.CarMgmt.objects.User;

import java.util.Map;

public class AuthenticationManager {
    private static User loggedUser = null;
    public final static String defaultPassword = "password";

    public static boolean loginUser(String userId, String password) {
        try {
            Map<String, User> users = UserManager.getUsers();
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                    setLoggedUser(switch (user.getRole()) {
                        case "Customer" -> (Customer) user;
                        case "Staff" -> (Staff) user;
                        case "Admin" -> (Admin) user;
                        default -> throw new IllegalStateException("Unexpected value (loadUsers): " + user.getRole());
                    });
                    return true;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error verifying user (loginUser): " + e.getMessage());
        }
        return false;
    }

    public static void logoutUser() {
        setLoggedUser(null);
    }

    public static boolean isUserBanned(String userId) {
        User user = UserManager.getUserByID(userId);
        return user instanceof Customer && ((Customer) user).getIsBanned();
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User loggedUser) {
        AuthenticationManager.loggedUser = loggedUser;
    }
}
