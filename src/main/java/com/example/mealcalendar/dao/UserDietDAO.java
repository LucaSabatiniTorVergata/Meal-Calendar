package com.example.mealcalendar.dao;


import com.example.mealcalendar.model.UserEntity;

import java.util.List;


public interface UserDietDAO {

    void saveUser(UserEntity user);
    UserEntity getUserByEmail(String email);
    List<UserEntity> getAllUsers();
}
