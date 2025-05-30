package com.example.mealcalendar.findrecipe;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestFindRecipe3 {

    @Test
    void testMostraricette_ConBeanNonNull_RitornaLista() {
        // Creo un mock di RecipeEditBean
        RecipeEditBean beanMock = mock(RecipeEditBean.class);
        // Quando getUser() viene chiamato, ritorna "utenteDiTest"
        when(beanMock.getUser()).thenReturn("utenteDiTest");

        // Creo il controller con il bean mockato
        RecipeEditController controller = new RecipeEditController(beanMock);

        // Chiamo il metodo da testare
        List<RecipeReturnBean> ricette = controller.mostraricette();

        // Assert di base: lista non nulla (potrebbe essere vuota se non ci sono ricette)
        assertNotNull(ricette);
    }
}
