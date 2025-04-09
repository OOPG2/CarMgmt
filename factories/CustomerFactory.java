package org.example.CarMgmt.factories;

import org.example.CarMgmt.objects.Customer;
import org.example.CarMgmt.objects.User;

public class CustomerFactory implements UserFactory {
    @Override
    public User createUser(String[] userData) {
        return new Customer(
                userData[0], // userId
                userData[1], // password
                userData[2], // role
                userData[3], // name
                userData[4], // email
                userData[5], // phone
                Integer.parseInt(userData[6]), // lifetimePoints
                Integer.parseInt(userData[7]), // loyaltyPoints
                Boolean.parseBoolean(userData[8]) // isBanned
        );
    }
}
