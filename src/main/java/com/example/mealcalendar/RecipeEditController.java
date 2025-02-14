package com.example.mealcalendar;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeEditController {

    private RecipeEditBean bean;

    public RecipeEditController(RecipeEditBean bean) {
        this.bean = bean;
    }

    public RecipeEditController () {
    }

    public List<RecipeReturnBean> mostraricette(){
        RecipeDaoFS dao = RecipeDaoFactory.createRecipeDao();
        List<RecipeEntity> ricette= dao.getAllRecipes();
        List<RecipeReturnBean> lista= new ArrayList<RecipeReturnBean>();
        for(RecipeEntity r:ricette){
            if(r.getAuthor().equals(bean.getUser()) ){
                lista.add(new RecipeReturnBean(r.getRecipeName(),r.getTypeofDiet(),r.getTypeofMeal(),r.getNumIngredients(),r.getIngredients(),r.getDescription(),r.getAuthor()));
            }
        }
        return lista;
    }

    public void rimuovi(String ricetta) throws IOException {

        String[] parts = ricetta.split(" - ");
        if (parts.length >= 6) {

            RecipeEntity ricettarim=new RecipeEntity(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],SessionManagerSLT.getInstance().getLoggedInUsername());
            RecipeDaoFS dao = RecipeDaoFactory.createRecipeDao();
            dao.removeRecipe(ricettarim);


        }
    }

}