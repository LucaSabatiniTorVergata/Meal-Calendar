package com.example.mealcalendar.findrecipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class TestFindRecipe2 {

    private RecipeEditController controller;
    private RecipeEditBean beanMock;
    private RecipeManagerController managerMock;

    @BeforeEach
    void setUp() {
        beanMock = mock(RecipeEditBean.class);
        managerMock = mock(RecipeManagerController.class);
        controller = new RecipeEditController(beanMock, managerMock);
    }

    @Test
    void testRimuovi_EsegueRemoveRecipe() throws RecipeDaoException {
        // Arrange
        // Simuliamo utente loggato

        String input = "Pizza - Veg - Dinner - 4 - Farina, Pomodoro - Classica";

        // Act
        controller.rimuovi(input);

        // Non possiamo verificare l’esatto oggetto se non abbiamo accesso diretto
        // all’oggetto RecipeEntity, ma possiamo almeno verificare che il metodo sia stato chiamato una volta:
        verify(managerMock, times(1)).removeRecipe(any(RecipeEntity.class));
    }
}
