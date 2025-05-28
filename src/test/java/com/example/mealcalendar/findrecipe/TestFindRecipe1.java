package com.example.mealcalendar.findrecipe;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestFindRecipe1 {

    @Test
    public void testMostraricette_FiltraSoloRicetteUtenteLoggato() {
        // Arrange
        RecipeEditBean beanMock = mock(RecipeEditBean.class);
        when(beanMock.getUser()).thenReturn("utente123");

        RecipeManagerController managerMock = mock(RecipeManagerController.class);

        RecipeEntity ricettaUtente = new RecipeEntity.RecipeEntityBuilder()
                .recipeName("Pizza")
                .typeofDiet("Veg")
                .typeofMeal("Dinner")
                .numIngredients("4")
                .ingredients("Farina, Pomodoro")
                .description("Classica")
                .author("utente123")
                .build();

        RecipeEntity ricettaAltra = new RecipeEntity.RecipeEntityBuilder()
                .recipeName("Insalata")
                .typeofDiet("Vegan")
                .typeofMeal("Lunch")
                .numIngredients("3")
                .ingredients("Lattuga, Carote")
                .description("Fresca")
                .author("altroUtente")
                .build();

        when(managerMock.getAllRecipes()).thenReturn(Arrays.asList(ricettaUtente, ricettaAltra));

        RecipeEditController controller = new RecipeEditController(beanMock, managerMock);

        // Act
        List<RecipeReturnBean> result = controller.mostraricette();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Pizza", result.get(0).getRecipeName());
        assertEquals("utente123", result.get(0).getAuthor());
    }
}
