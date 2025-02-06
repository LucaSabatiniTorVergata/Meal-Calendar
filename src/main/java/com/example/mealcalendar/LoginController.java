package com.example.mealcalendar;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private UserDaoFS userDAO = new UserDaoFS();

    public boolean login(LoginBean userLoginBean) throws IOException {

        if (userDAO.checkPassword(userLoginBean.getUsername(), userLoginBean.getPassword())) {
            return true; // Login riuscito
        } else {
            return false; // Login fallito
        }
    }
}
