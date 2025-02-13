package com.example.mealcalendar;

public class SessionManagerSLT {

    private static SessionManagerSLT instance=null;
    private String loggedInUsername;
    private String loggedEmail;

    private SessionManagerSLT() {}

    public static SessionManagerSLT getInstance() {
        if (instance == null) {
            instance = new SessionManagerSLT();
        }
        return instance;
    }

    public void setLoggedInUsername(String username) {

        this.loggedInUsername = username;
    }


    public String getLoggedInUsername() {
        return loggedInUsername;
    }



    public void logout() {
        loggedInUsername = null;
    }
}
