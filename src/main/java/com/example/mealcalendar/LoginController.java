package com.example.mealcalendar;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private UserDaoInterface userDAO;

    public LoginController() {
        this(UserDaoFactory.createUserDao());
    }

    public LoginController(UserDaoInterface userDao) {
        this.userDAO = userDao;
    }

    public boolean login(LoginBean userLoginBean) throws IOException {
        return checkPassword(userLoginBean.getUsername(), userLoginBean.getPassword());
    }

    private boolean checkPassword(String username, String password) throws IOException {
        UserEntity user = userDAO.getUserByUsername(username);
        if (user != null) {
            LOGGER.log(Level.INFO, "User: {0}", user.getUsername());
            LOGGER.log(Level.INFO, "Password inserita: {0}", password);
            LOGGER.log(Level.INFO, "Password memorizzata: {0}", user.getPassword());
            LOGGER.log(Level.INFO, "Password corretta: {0}", BCrypt.checkpw(password, user.getPassword()));
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
}
