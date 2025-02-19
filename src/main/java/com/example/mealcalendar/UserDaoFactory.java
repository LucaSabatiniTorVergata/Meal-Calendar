package com.example.mealcalendar;

public class UserDaoFactory {

    private UserDaoFactory() {}

    private static boolean useDatabase = false; // Impostazione predefinita su fs
    private static boolean demoMode = false;

    // Metodo per cambiare modalità tra File System e Database
    public static void setUseDatabase(boolean value) {
        useDatabase = value;
        demoMode = false;
    }
    public static void useDemo(boolean value) {
        demoMode = value;
    }

    public static UserDaoInterface createUserDao() {
        return new UserDao(useDatabase, demoMode);
    }
}
