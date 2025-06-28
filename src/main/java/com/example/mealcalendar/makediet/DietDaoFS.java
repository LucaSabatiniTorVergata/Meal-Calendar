package com.example.mealcalendar.makediet;

import com.example.mealcalendar.login.UserDaoFS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DietDaoFS implements DietDao {

    private static final String FILE_PATH = "diets.json";
    private static final Logger logger = Logger.getLogger(DietDaoFS.class.getName());
    private ObjectMapper mapper = new ObjectMapper();




    @Override
    public void save(DietaEntity dieta) {

        List<DietaEntity> diete = findAll();
        diete.add(dieta);
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            mapper.writeValue(writer, diete);
        } catch (IOException e) {
            exceptionHandler();
        }

    }

    @Override
    public List<DietaEntity>findAll(){

        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    logger.info("File created successfully.");
                } else {
                    logger.info("File already exists.");
                }
            }
        } catch (IOException e) {
            exceptionHandler();
        }
        try (FileReader reader = new FileReader(file)) {
            return mapper.readValue(reader, new TypeReference<List<DietaEntity>>() {});
        } catch (IOException e) {
            exceptionHandler();
            return new ArrayList<>();
        }
    }

    @Override
    public List<DietaEntity> Filbyauthor(String username) {
        return findAll().stream()
                .filter(d -> d.getAutore() != null && username.equals(d.getAutore()))
                .collect(Collectors.toList());
    }

    private void exceptionHandler(){
        logger.log(Level.SEVERE, "Errore nella creazione del file tento di crearne uno nuovo");
        File file = new File(FILE_PATH);
        try {
            if (!file.exists()) {
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    logger.info("File created successfully.");
                } else {
                    logger.info("File already exists.");
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE,"impossibile creare il file");
        }

    }

}
