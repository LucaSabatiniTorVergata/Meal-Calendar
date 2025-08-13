package com.example.mealcalendar.register_login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class RegistrationController {

    public void register(UserBeanA bean) throws IOException {

        String filename;
        if (bean.getRuolo().equals("nutritionist")) {
            filename = "nutritionists.txt";
        } else if (bean.getRuolo().equals("restaurant")) {
            filename = "restaurants.txt";
        } else {
            filename = "users.txt";
        }
        File file = new File(filename);

        // Se il file non esiste, lo creo
        if (!file.exists()) {

            boolean ignored = file.createNewFile();


        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(bean.getUsername() + "-" + bean.getEmail() + "-" + bean.getPassword() + "-" + bean.getRuolo());
            writer.newLine();
        }
    }


}
