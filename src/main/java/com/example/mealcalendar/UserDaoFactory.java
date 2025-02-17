package com.example.mealcalendar;

public class UserDaoFactory {

    private static boolean useDatabase = false; // Impostazione predefinita su File System
    private static boolean useDemo = false; // Impostazione predefinita su non demo

    // Metodo per cambiare modalità tra File System e Database
    public static void setUseDatabase(boolean value) {
        useDatabase = value;
        useDemo = false; // Se si usa il DB o FS, disabilita la demo
    }

    // Metodo per attivare la modalità demo
    public static void setUseDemo(boolean value) {
        useDemo = value;
        useDatabase = false; // Se si usa la demo, disabilita DB e FS
    }

    public static UserDaoInterface createUserDao() {
        return new UserDao(useDatabase, useDemo);
    }
}


