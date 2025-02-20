package com.example.mealcalendar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeEditController {

    private RecipeEditBean bean;

    public RecipeEditController(RecipeEditBean bean) {
        this.bean = bean;
    }

    public RecipeEditController() {
    }

    public List<RecipeReturnBean> mostraricette() throws RecipeDaoException {
        // Otteniamo il DAO in modo generico, senza il cast esplicito
        RecipeDao dao = RecipeDaoFactory.createRecipeDao();

        // Recuperiamo tutte le ricette dal file
        List<RecipeEntity> ricette = dao.getAllRecipes();
        List<RecipeReturnBean> lista = new ArrayList<>();

        // Filtriamo le ricette per autore
        for (RecipeEntity r : ricette) {
            if (r.getAuthor().equals(bean.getUser())) {
                // Creiamo il RecipeReturnBean usando i dati della ricetta
                lista.add(new RecipeReturnBean(
                        r.getRecipeName(),
                        r.getTypeofDiet(),
                        r.getTypeofMeal(),
                        r.getNumIngredients(),
                        r.getIngredients(),
                        r.getDescription(),
                        r.getAuthor()
                ));
            }
        }
        return lista;
    }

    public void rimuovi(String ricetta) throws RecipeDaoException {
        // Split della ricetta per ottenere i dettagli
        String[] parts = ricetta.split(" - ");
        if (parts.length >= 6) {
            // Creazione della ricetta da rimuovere usando la fabbrica
            RecipeEntity ricettarim = RecipeEntityFactory.createRecipe(
                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], SessionManagerSLT.getInstance().getLoggedInUsername()
            );

            // Otteniamo il DAO in modo generico, senza il cast esplicito
            RecipeDao dao = RecipeDaoFactory.createRecipeDao();
            dao.removeRecipe(ricettarim);
        }
    }
}
