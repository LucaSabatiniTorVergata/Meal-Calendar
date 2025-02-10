package com.example.mealcalendar;

public class UserDaoFactory {

    private static final boolean USE_DATABASE = true; // Cambia a true per usare il database

    public static UserDaoInterface createUserDao() {
        return new UserDao(USE_DATABASE);
    }
}
