package factories;

import objects.User;

public interface UserFactory {
    User createUser(String[] userData);
}
