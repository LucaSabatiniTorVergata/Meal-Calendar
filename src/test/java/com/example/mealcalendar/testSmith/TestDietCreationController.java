package com.example.mealcalendar.testSmith;

import com.example.mealcalendar.bean.DietBean;

import com.example.mealcalendar.controller_applicativo.DietCreationController;

import com.example.mealcalendar.model.DietEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TestDietCreationController {


    @Test
    void testConvertBeanToEntity() {
        // Preparazione Bean
        DietBean bean = new DietBean();
        bean.setNome("Test Diet");
        bean.setDescrizione("Descrizione di test");

        // Creazione controller (senza dipendenze)
        DietCreationController controller = new DietCreationController();

        // Esecuzione
        DietEntity entity = controller.converter(bean);

        // Verifica
        assertEquals("Test Diet", entity.getNome());
        assertEquals("Descrizione di test", entity.getDescrizione());
    }
}


