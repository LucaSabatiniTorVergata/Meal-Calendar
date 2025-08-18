package com.example.mealcalendar.register_login;

import com.example.mealcalendar.dao.UserDao;
import java.io.IOException;

public class RegistrationController {

    private final UserDao userDao = new UserDao();

    public void register(UserBeanA bean) throws IOException {
        userDao.saveUser(bean);
    }
}
