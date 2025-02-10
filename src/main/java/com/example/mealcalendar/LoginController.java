package com.example.mealcalendar;

import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private UserDaoFS userDAO;

    public LoginController(){
     this(new UserDaoFS());
    }

    public LoginController(UserDaoFS userDaoFS) {
        this.userDAO = userDaoFS;
    }

    public boolean login(LoginBean userLoginBean) throws IOException {

        if (checkPassword(userLoginBean.getUsername(), userLoginBean.getPassword())) {
            return true; // Login riuscito
        } else {
            return false; // Login fallito
        }
    }
    public boolean login(LoginBean userLoginBean, UserDaoFS userDAO) throws IOException {

        if (checkPassword(userLoginBean.getUsername(), userLoginBean.getPassword())) {
            return true; // Login riuscito
        } else {
            return false; // Login fallito
        }
    }

    public boolean checkPassword (String username, String password) throws IOException {

        UserEntity user = userDAO.getUserByUsername(username);


        if (user != null) {
            // Confronta la password criptata con quella inserita
            System.out.println("Password inserita: " + password);
            System.out.println("Password memorizzata: " + user.getPassword());
            System.out.println("Password corretta: " + BCrypt.checkpw(password, user.getPassword()));
            return BCrypt.checkpw(password, user.getPassword());

        }

        return false;
    }
}
