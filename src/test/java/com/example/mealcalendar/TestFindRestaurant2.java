package com.example.mealcalendar;

import com.example.mealcalendar.findrest.ChooseRestaurantController;
import com.example.mealcalendar.findrest.FiltersRestaurantBean;
import com.example.mealcalendar.findrest.FindRestaurantApiBoundary;
import com.example.mealcalendar.findrest.ReturnRestaurantsBean;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TestFindRestaurant2 {


    @Test
    public void testRistoranteConDatiMancanti() throws IOException {
        FiltersRestaurantBean filtro = new FiltersRestaurantBean();
        FindRestaurantApiBoundary apiStub = new FindRestaurantApiBoundary() {
            @Override
            public List<JSONObject> cercaRistoranti(FiltersRestaurantBean filtro) {
                List<JSONObject> list = new ArrayList<>();
                JSONObject r = new JSONObject();
                r.put("name", "Pizzeria Fantasma");
                // niente "vicinity" e "geometry"
                list.add(r);
                return list;
            }
        };

        ChooseRestaurantController controller = new ChooseRestaurantController(filtro, apiStub);
        List<ReturnRestaurantsBean> result = controller.trovaRistorante();

        assertEquals(1, result.size());
        assertEquals("Indirizzo non disponibile", result.get(0).getIndirizzo());
    }
}
