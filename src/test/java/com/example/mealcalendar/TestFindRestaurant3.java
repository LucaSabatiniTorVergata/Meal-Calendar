package com.example.mealcalendar;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestFindRestaurant3 {

    @Test
    void testListaVuota() throws IOException {
        FiltersRestaurantBean filtro = new FiltersRestaurantBean();
        FindRestaurantApiBoundary apiStub = new FindRestaurantApiBoundary() {
            @Override
            public List<JSONObject> cercaRistoranti(FiltersRestaurantBean filtro) {
                return new ArrayList<>();
            }
        };

        ChooseRestaurantController controller = new ChooseRestaurantController(filtro, apiStub);
        List<ReturnRestaurantsBean> result = controller.trovaRistorante();

        assertTrue(result.isEmpty());
    }
}
