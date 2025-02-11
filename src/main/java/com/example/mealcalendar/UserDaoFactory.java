package com.example.mealcalendar;

public class UserDaoFactory {

    private static boolean useDatabase = true; // Impostazione predefinita su Database

    // Metodo per cambiare modalità tra File System e Database
    public static void setUseDatabase(boolean value) {
        useDatabase = value;
    }

    public static UserDaoInterface createUserDao() {
        return new UserDao(useDatabase);
    }
}
