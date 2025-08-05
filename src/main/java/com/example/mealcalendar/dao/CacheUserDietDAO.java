package com.example.mealcalendar.dao;

import com.example.mealcalendar.model.UserEntity;

import java.util.List;

public class CacheUserDietDAO implements UserDietDAO {

    private final UserDietDAO backend;
    private final RamUserDietDAO cache = RamUserDietDAO.getInstance();

    public CacheUserDietDAO(UserDietDAO backend) {
        this.backend = backend;
    }

    @Override
    public void saveUser(UserEntity user) {

        cache.saveUser(user);
        backend.saveUser(user);

    }

    @Override
    public UserEntity getUserByEmail(String email) {

        UserEntity user = cache.getUserByEmail(email);
        if (user != null) return user;

        user = backend.getUserByEmail(email);
        if (user != null) cache.saveUser(user);

        return user;

    }

    @Override
    public List<UserEntity> getAllUsers() {

        List<UserEntity> users = cache.getAllUsers();
        if (!users.isEmpty()) return users;

        List<UserEntity> all = backend.getAllUsers();
        for (UserEntity user : all) {
            cache.saveUser(user);
        }
        return all;

    }

    public void clearAllCache() {
        cache.clearCache();
    }
}
