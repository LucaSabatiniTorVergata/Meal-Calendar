package com.example.mealcalendar;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private UserDaoInterface userDAO;

    public LoginController() {
        this(UserDaoFactory.createUserDao()); // Usa la factory per ottenere il DAO corretto
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
            System.out.println("User :"+ user.getUsername());
            System.out.println("Password inserita: " + password);
            System.out.println("Password memorizzata: " + user.getPassword());
            System.out.println("Password corretta: " + BCrypt.checkpw(password, user.getPassword()));
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
}
