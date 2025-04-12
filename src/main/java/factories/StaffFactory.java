package factories;

import objects.Staff;
import objects.User;

public class StaffFactory implements UserFactory {
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