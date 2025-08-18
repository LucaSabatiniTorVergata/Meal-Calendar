package com.example.mealcalendar.dao;

import com.example.mealcalendar.register_login.UserLoginBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class UserDao {

    private String getFilenameByRole(String role) {
        switch (role) {
            case "nutritionist":
                return "nutritionists.txt";
            case "restaurant":
                return "restaurants.txt";
            default:
                return "users.txt";
        }
    }

    public void saveUser(com.example.mealcalendar.register_login.UserBeanA bean) throws IOException {
        String filename = getFilenameByRole(bean.getRuolo());

        File file = new File(filename);

        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (!created) {
                throw new IOException("Impossibile creare il file: " + file.getAbsolutePath());
            }
        }

        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(file, true))) {
            writer.write(bean.getUsername() + "-" + bean.getEmail() + "-" + bean.getPassword() + "-" + bean.getRuolo());
            writer.newLine();
        }
    }

    public String[] validateUser(UserLoginBean userBean) throws IOException {
        String filename = getFilenameByRole(userBean.getRuolo());

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("-");
                if (fields.length >= 4) {
                    String savedUsername = fields[0];
                    String savedMail = fields[1];
                    String savedPassword = fields[2];
                    String savedRole = fields[3];

                    if (savedUsername.equals(userBean.getUsername()) &&
                            savedPassword.equals(userBean.getPassword()) &&
                            savedRole.equals(userBean.getRuolo())) {
                        return fields; // ritorno i dati trovati
                    }
                }
            }
        }
        return null; // nessun match
    }
}
