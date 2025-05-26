package com.example.mealcalendar.findrecipe;

import com.example.mealcalendar.SessionManagerSLT;

public class RecipeEdit2Controller {

    private RecipeEdit2Bean bean;
    private final RecipeManagerController managerController;

    public RecipeEdit2Controller(RecipeEdit2Bean bean) {
        this.bean = bean;
        RecipeDao dao = RecipeDaoFactory.createRecipeDao();
        this.managerController = new RecipeManagerController(dao);
    }

    public void cambiaRicetta() throws RecipeDaoException {
        // Splittiamo la ricetta presa per ottenere le informazioni della ricetta vecchia
        String[] parts = bean.getRicettapresa().split(" - ");
        if (parts.length >= 6) {
            // Ricetta originale da aggiornare
            RecipeEntity ricettavecchia = RecipeEntityFactory.createRecipe(
                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                    SessionManagerSLT.getInstance().getLoggedInUsername()
            );

            // Nuova versione della ricetta
            RecipeEntity ricettaAggiornata = RecipeEntityFactory.createRecipe(
                    bean.getricetta(),
                    bean.getTdieta(),
                    bean.getTpasto(),
                    bean.getNumingred(),
                    bean.getIngred(),
                    bean.getDesrcip(),
                    bean.getAutor()
            );

            // Usiamo il manager per fare l’update
            managerController.updateRecipe(ricettavecchia, ricettaAggiornata);
        }
    }
}
