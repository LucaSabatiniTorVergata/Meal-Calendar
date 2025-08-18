package com.example.mealcalendar.controller_applicativo;

import com.example.mealcalendar.dao.RistoranteDao;
import java.util.List;

public class PrenotazioneController {

    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public List<String> getRistoranti() {
        return ristoranteDao.leggiRistoranti();
    }
}
