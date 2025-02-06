package com.example.mealcalendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.mealcalendar.RecipeEntity;
import com.example.mealcalendar.RecipeEntityFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

public class RecipeAddController {

    public boolean salvaricetta(AddRecipeBean bean)throws IOException{
         String nome= bean.getRecipeName();
         String dieta= bean.getTypeofDiet();
         String pasto= bean.getTypeofMeal();
         double numingredienti=bean.getNumIngredients();
         String ingredienti= bean.getIngredients();
         String descrizione= bean.getDescription();
         String autore= bean.getAuthor();



    }
}