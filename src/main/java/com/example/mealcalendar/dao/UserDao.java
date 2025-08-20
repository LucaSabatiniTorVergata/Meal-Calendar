package com.example.mealcalendar.dao;

import com.example.mealcalendar.register_login.UserBeanA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private String getFilenameByRole(String role) {
        switch (role) {
            case "nutritionist":
                return "nutritionists.txt";
            default:
                return "users.txt";
        }
    }

    public void saveUser(UserBeanA bean) throws IOException {
        String filename = getFilenameByRole(bean.getRuolo());

        File file = new File(filename);

        if (!file.exists()) {
            boolean created = file.createNewFile();
            if (!created) {
                throw new IOException("Impossibile creare il file: " + file.getAbsolutePath());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(bean.getUsername() + "-" +
                    bean.getEmail() + "-" +
                    bean.getPassword() + "-" +
                    bean.getRuolo());
            writer.newLine();
        }
    }

    // Lettura utenti come Bean
    public List<UserBeanA> leggiUtenti() {
        List<UserBeanA> utenti = new ArrayList<>();

        String[] files = {"users.txt", "nutritionists.txt"};

        for (String filename : files) {
            File file = new File(filename);
            if (!file.exists()) continue;

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] fields = line.split("-");
                    if (fields.length == 4) {
                        String username = fields[0];
                        String email = fields[1];
                        String password = fields[2];
                        String ruolo = fields[3];

                        utenti.add(new UserBeanA(username, email, password, ruolo));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return utenti;
    }
}
