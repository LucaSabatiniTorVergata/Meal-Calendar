package com.example.mealcalendar;

import java.io.IOException;

public class RecipeEdit2Controller {

    private RecipeEdit2Bean bean;

    public RecipeEdit2Controller(RecipeEdit2Bean bean) {
        this.bean = bean;
    }

    public void cambiaRicetta() throws IOException {
        // Splittiamo la ricetta presa per ottenere le informazioni della ricetta vecchia
        String[] parts = bean.getRicettapresa().split(" - ");
        if (parts.length >= 6) {
            // Creiamo l'oggetto RecipeEntity per la ricetta vecchia usando la fabbrica con il Builder
            RecipeEntity ricettavecchia = RecipeEntityFactory.createRecipe(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], SessionManagerSLT.getInstance().getLoggedInUsername());

            // Creiamo l'oggetto RecipeEntity per la nuova ricetta usando i dati nel bean
            RecipeEntity ricetta = RecipeEntityFactory.createRecipe(
                    bean.getricetta(),
                    bean.getTdieta(),
                    bean.getTpasto(),
                    bean.getNumingred(),
                    bean.getIngred(),
                    bean.getDesrcip(),
                    bean.getAutor()
            );

            // Otteniamo il DAO e aggiorniamo la ricetta
            RecipeDaoFS dao = (RecipeDaoFS) RecipeDaoFactory.createRecipeDao();
            dao.updateRecipe(ricettavecchia, ricetta);
        }
    }
}

