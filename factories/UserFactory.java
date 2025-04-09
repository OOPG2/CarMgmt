package org.example.CarMgmt.factories;

import org.example.CarMgmt.objects.User;

public interface UserFactory {
    User createUser(String[] userData);
}
