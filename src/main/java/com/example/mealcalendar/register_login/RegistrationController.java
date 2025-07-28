package com.example.mealcalendar.register_login;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class RegistrationController {

    public void register(UserBean bean) throws IOException {

        String filename = bean.getRuolo().equals("Nutritionist") ? "nutritionists.txt" : "users.txt";
        File file = new File(filename);

        // Se il file non esiste, lo creo
        if (!file.exists()) {
            file.createNewFile(); // crea file vuoto
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(bean.getUsername() + "-" + bean.getEmail() + "-" + bean.getPassword() + "-" + bean.getRuolo());
            writer.newLine();
        }
    }


}
