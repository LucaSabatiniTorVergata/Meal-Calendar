package com.example.mealcalendar.testSmith;

import com.example.mealcalendar.bean.DayTakenBean;
import com.example.mealcalendar.bean.DietTakenBean;
import com.example.mealcalendar.bean.MealTakenBean;
import com.example.mealcalendar.model.*;
import com.example.mealcalendar.controller_applicativo.FollowDietController;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


class FollowDietControllerTest {

    @Test
    void testConverterTaken() {
        // Preparazione del bean di input
        MealTakenBean mealBean = new MealTakenBean();
        mealBean.setNome("Pasta");
        mealBean.setDescrizione("Pasta al pomodoro");
        mealBean.setKcal(500);

        DayTakenBean dayBean = new DayTakenBean();
        dayBean.setGiorno(1);
        dayBean.addMeal(mealBean);

        DietTakenBean dietTakenBean = new DietTakenBean();
        dietTakenBean.setUser("utenteTest");
        dietTakenBean.addDay(dayBean);

        // Creazione del controller
        FollowDietController controller = new FollowDietController();

        // Esecuzione metodo
        TakenDietEntity entity = controller.converterTaken(dietTakenBean);

        // Verifiche
        assertEquals("utenteTest", entity.getUser());
        assertEquals(1, entity.getDays().size());
        assertEquals(1, entity.getDays().get(0).getGiorno());
        assertEquals(1, entity.getDays().get(0).getPasti().size());
        assertEquals("Pasta", entity.getDays().get(0).getPasti().get(0).getNome());
        assertEquals(500, entity.getDays().get(0).getPasti().get(0).getKcal());
    }
}
