package com.example.mealcalendar.register_login;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.dao.UserDao;

import java.io.IOException;

public class LoginController {

    private final UserDao userDao = new UserDao();

    public boolean vallogin(UserLoginBean userBean) throws IOException {
        String[] fields = userDao.validateUser(userBean);

        if (fields != null) {
            String savedUsername = fields[0];
            String savedMail = fields[1];
            String savedPassword = fields[2];
            String savedRuolo = fields[3];

            SessionManagerSLT.getInstance().setLoggedInUsername(savedUsername);
            SessionManagerSLT.getInstance().setLoggedRole(savedRuolo);
            SessionManagerSLT.getInstance().setLoggedmail(savedMail);
            SessionManagerSLT.getInstance().setLoggedpassword(savedPassword);

            return true;
        }
        return false;
    }
}
