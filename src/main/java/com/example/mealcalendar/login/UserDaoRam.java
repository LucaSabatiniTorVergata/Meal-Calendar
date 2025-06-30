package com.example.mealcalendar.login;

import java.io.IOException;
import java.util.*;

public class UserDaoRam implements UserDaoInterface {

    private static List<UserEntity> demoUsers = new ArrayList<>();

    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        demoUsers.add(user);
        return true;
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
            return new ArrayList<>(demoUsers);  // Return a copy of the demo users list
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        for (UserEntity user : demoUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void updateUser(UserEntity updatedUser) {
        demoUsers.removeIf(u -> u.getUsername().equals(updatedUser.getUsername()));
        demoUsers.add(updatedUser);
    }

}