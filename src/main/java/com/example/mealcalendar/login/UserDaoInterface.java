package com.example.mealcalendar.login;

import java.io.IOException;
import java.util.List;

public interface UserDaoInterface {
    boolean registerUser(UserEntity user) throws IOException;
    List<UserEntity> getAllUsers() throws IOException;
    UserEntity getUserByUsername(String username) throws IOException;
    void updateUser(UserEntity updatedUser) throws IOException;
}
