package com.example.mealcalendar.findrecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeList {

    private static RecipeList instance = null;
    private List<RecipeEntity> listaRicette;

    private RecipeList() {
        listaRicette = new ArrayList<>();
    }

    public static RecipeList getInstance () {
        if (instance == null) {
            instance = new RecipeList();
        }
        return instance;
    }
    public void svuotaLista() {
        listaRicette.clear();
    }

    public void aggiungiRicette(RecipeEntity ricetta) {
        listaRicette.add(ricetta);
    }

    public List<RecipeReturnBean> getrcicettereturn() {
        List<RecipeReturnBean> beansList = new ArrayList<>();
        for (RecipeEntity ricette : listaRicette) {
            beansList.add(new RecipeReturnBean(
                    ricette.getRecipeName(),
                    ricette.getTypeofDiet(),
                    ricette.getTypeofMeal(),
                    ricette.getNumIngredients(),
                    ricette.getIngredients(),
                    ricette.getDescription(),
                    ricette.getAuthor()
            ));
        }
        return beansList;
    }
}
