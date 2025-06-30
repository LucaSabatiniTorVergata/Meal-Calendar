package com.example.mealcalendar.login;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserSessionCacheSLT {
    private static UserSessionCacheSLT instance=null;
    private List <UserEntity> userCache = new ArrayList<>();

    private UserSessionCacheSLT() {}

    public static synchronized UserSessionCacheSLT getInstance() {
        if (instance == null) instance = new UserSessionCacheSLT();
        return instance;
    }
    public UserEntity getUser(String username) throws IOException {
        for (UserEntity user : getUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public void invalidateAll() {
        userCache.clear();
    }

    public List<UserEntity> getUsers() throws IOException {
        if (userCache.isEmpty()) {
            UserDaoInterface dao = UserDaoFactory.createUserDao();
            try {
                userCache = dao.getAllUsers();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userCache;
    }

}