package com.example.mealcalendar.login;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    public LoginController() {//costruttore
    }


    public boolean login(LoginBean userLoginBean) throws IOException {
        return check(userLoginBean.getUsername(), userLoginBean.getPassword(),userLoginBean.getRuolo());
    }

    private boolean check(String username, String password,String ruolo) throws IOException {
        UserEntity user = UserSessionCacheSLT.getInstance().getUser(username);
        if (user != null && user.getRole().equals(ruolo)) {

            LOGGER.log(Level.INFO, "User: {0}", user.getUsername());
            LOGGER.log(Level.INFO, "Password inserita: {0}", password);
            LOGGER.log(Level.INFO, "Password memorizzata: {0}", user.getPassword());
            LOGGER.log(Level.INFO, "Password corretta: {0}", BCrypt.checkpw(password, user.getPassword()));
            LOGGER.log(Level.INFO, "Ruolo:{0}", ruolo);
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }
}

