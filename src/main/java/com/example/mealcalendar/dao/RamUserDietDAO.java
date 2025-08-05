package com.example.mealcalendar.dao;


import com.example.mealcalendar.model.UserEntity;

import java.util.*;

public class RamUserDietDAO implements UserDietDAO{

    private final Map<String, UserEntity> cache = new HashMap<>();

    private static RamUserDietDAO instance=null;

    private RamUserDietDAO() {}

    public static RamUserDietDAO getInstance() {
        if (instance == null) {
            instance = new RamUserDietDAO();
        }
        return instance;
    }

    @Override
    public void saveUser(UserEntity user) {
        cache.put(user.getEmail(), user);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return cache.get(email);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return new ArrayList<>(cache.values());
    }

    public void clearCache() {
        cache.clear();
    }
}

