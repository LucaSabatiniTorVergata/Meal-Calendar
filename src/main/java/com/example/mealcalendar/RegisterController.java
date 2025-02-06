package com.example.mealcalendar;

import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;

public class RegisterController {

    private UserDaoFS userDAO = new UserDaoFS();

    public boolean register(UserBean userRegisterBean) throws IOException {
        String hashedPassword = userRegisterBean.getPassword();
        UserEntity newUser = new UserEntity(userRegisterBean.getUsername(), userRegisterBean.getEmail(), hashedPassword);
        if(userDAO.registerUser(newUser)){
            return true;
        }else{
            return false;
        }

    }
}
