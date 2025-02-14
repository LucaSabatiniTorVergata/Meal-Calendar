package com.example.mealcalendar;


import java.util.ArrayList;
import java.util.List;

public class RecipeEditController {

    private RecipeEditBean bean;

    public RecipeEditController(RecipeEditBean bean) {
        this.bean = bean;
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


}