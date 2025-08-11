package com.example.mealcalendar.register_login;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.model.DietEntity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {

    public boolean vallogin(UserLoginBean userBean) throws IOException {

        String filename;
        if (userBean.getRuolo().equals("nutritionist")) {
            filename = "nutritionists.txt";
        } else if (userBean.getRuolo().equals("restaurant")) {
            filename = "restaurants.txt";
        } else {
            filename = "users.txt";
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("-");
                if (fields.length >= 4) {
                    String savedUsername = fields[0];
                    String savedmail=fields[1];
                    String savedPassword = fields[2];
                    String savedRuolo = fields[3];


                    if (savedUsername.equals(userBean.getUsername()) &&
                            savedPassword.equals(userBean.getPassword()) &&
                            savedRuolo.equals(userBean.getRuolo())) {

                        SessionManagerSLT.getInstance().setLoggedInUsername(savedUsername);
                        SessionManagerSLT.getInstance().setLoggedRole(savedRuolo);
                        SessionManagerSLT.getInstance().setLoggedmail(savedmail);
                        SessionManagerSLT.getInstance().setLoggedpassword(savedPassword);

                        return true;
                    }
                }
            }
        }

        return false;
    }


}


