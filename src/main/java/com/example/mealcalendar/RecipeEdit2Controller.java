package com.example.mealcalendar;

import java.io.IOException;

public class RecipeEdit2Controller {

    private RecipeEdit2Bean bean;

    public RecipeEdit2Controller(RecipeEdit2Bean bean) {
        this.bean = bean;
    }

    public void cambiaRicetta() throws IOException {


        String[] parts = bean.getRicettapresa().split(" - ");
        if (parts.length >= 6) {

            RecipeEntity ricettavecchia=new RecipeEntity(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],SessionManagerSLT.getInstance().getLoggedInUsername());
            RecipeDaoFS dao = RecipeDaoFactory.createRecipeDao();
            RecipeEntity ricetta = new RecipeEntity(bean.getricetta(), bean.getTdieta(), bean.getTpasto(), bean.getNumingred(), bean.getIngred(), bean.getDesrcip(), bean.getAutor());
            dao.updateRecipe(ricettavecchia, ricetta);

        }

    }

}
