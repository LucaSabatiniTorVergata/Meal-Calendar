package com.example.mealcalendar.login;
import com.example.mealcalendar.SessionManagerSLT;


public class UserDaoFactory {

    private UserDaoFactory() {}

    public static UserDaoInterface createUserDao() {
        SessionManagerSLT session = SessionManagerSLT.getInstance();

        if (session.getDemo()) {
            return new UserDaoRam(); // in-memory
        } else if (session.getFSDataBase()) {
            return new UserDaoDB(); // database
        } else {
            return new UserDaoFS(); // filesystem
        }
    }
}
