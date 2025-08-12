package com.example.mealcalendar.testSmith;

import com.example.mealcalendar.bean.DayTakenBean;
import com.example.mealcalendar.bean.DietTakenBean;
import com.example.mealcalendar.bean.MealTakenBean;
import com.example.mealcalendar.controller_applicativo.RequestNutritionsReportController;
import com.example.mealcalendar.model.TakenDietEntity;
import com.example.mealcalendar.model.TakenDayEntity;
import com.example.mealcalendar.model.TakenMealEntity;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class RequestNutritionsReportControllerTest {

    @Test
    void testConverterTaken() {

        TakenMealEntity colazione = new TakenMealEntity("Colazione", "Caffè e biscotti", 200);
        TakenMealEntity pranzo = new TakenMealEntity("Pranzo", "Pasta al pomodoro", 500);

        TakenDayEntity lunedi = new TakenDayEntity(1);
        lunedi.addMeal(colazione);
        lunedi.addMeal(pranzo);

        TakenDietEntity dietaEffettiva = new TakenDietEntity("user");
        dietaEffettiva.addDay(lunedi);

        // ---- Eseguo il metodo ----
        RequestNutritionsReportController controller = new RequestNutritionsReportController();
        DietTakenBean bean = controller.converterTaken(dietaEffettiva);

        // ---- Verifico ----
        assertNotNull(bean);
        assertEquals(1, bean.getDietTaken().size());

        DayTakenBean dayBean = bean.getDietTaken().get(0);
        assertEquals(2, dayBean.getMealsTaken().size());

        MealTakenBean meal1 = dayBean.getMealsTaken().get(0);
        assertEquals("Colazione", meal1.getNome());
        assertEquals("Caffè e biscotti", meal1.getDescrizione());
        assertEquals(200, meal1.getKcal());

        MealTakenBean meal2 = dayBean.getMealsTaken().get(1);
        assertEquals("Pranzo", meal2.getNome());
        assertEquals("Pasta al pomodoro", meal2.getDescrizione());
        assertEquals(500, meal2.getKcal());
    }
}
