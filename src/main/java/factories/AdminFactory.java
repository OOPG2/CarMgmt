package factories;

import objects.Admin;
import objects.User;

public class AdminFactory implements UserFactory {
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