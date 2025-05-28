package com.example.mealcalendar.findrecipe;

import com.example.mealcalendar.SessionManagerSLT;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeEditController {

    private RecipeEditBean bean;
    private final RecipeManagerController managerController;

    public RecipeEditController(RecipeEditBean bean) {
        this.bean = bean;
        RecipeDao dao = RecipeDaoFactory.createRecipeDao();
        this.managerController = new RecipeManagerController(dao);
    }

    public RecipeEditController() {
        RecipeDao dao = RecipeDaoFactory.createRecipeDao();
        this.managerController = new RecipeManagerController(dao);
    }


    //usato per il test

    public RecipeEditController(RecipeEditBean bean, RecipeManagerController managerController) {
        this.bean = bean;
        this.managerController = managerController;
    }

    public List<RecipeReturnBean> mostraricette()  {
        List<RecipeReturnBean> lista = new ArrayList<>();
        List<RecipeEntity> ricette = managerController.getAllRecipes();

        for (RecipeEntity r : ricette) {
            if (Objects.equals(r.getAuthor(), bean.getUser())) {
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
        String[] parts = ricetta.split(" - ");
        if (parts.length >= 6) {
            RecipeEntity ricettaDaRimuovere = RecipeEntityFactory.createRecipe(
                    parts[0],
                    parts[1],
                    parts[2],
                    parts[3],
                    parts[4],
                    parts[5],
                    SessionManagerSLT.getInstance().getLoggedInUsername()
            );
            managerController.removeRecipe(ricettaDaRimuovere);
        }
    }
}
