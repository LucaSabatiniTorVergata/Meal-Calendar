package com.example.mealcalendar;

public class RecipeAddController {

    // Istanza del DAO per interagire con il file
    private RecipeDao dao = RecipeDaoFactory.createRecipeDao();

    // Metodo per salvare una ricetta
    public boolean salvaRicetta(AddRecipeBean bean) throws RecipeDaoException {
        String nome = bean.getRecipeName();
        String dieta = bean.getTypeofDiet();
        String pasto = bean.getTypeofMeal();
        String numingredienti = bean.getNumIngredients();
        String ingredienti = bean.getIngredients();
        String descrizione = bean.getDescription();
        String autore = bean.getAuthor();

        // Creiamo la ricetta utilizzando il Builder Pattern tramite la fabbrica
        RecipeEntity newRecipe = RecipeEntityFactory.createRecipe(nome, dieta, pasto, numingredienti, ingredienti, descrizione, autore);

        // Aggiungiamo la ricetta al DAO
        return dao.addRecipe(newRecipe);
    }
}

