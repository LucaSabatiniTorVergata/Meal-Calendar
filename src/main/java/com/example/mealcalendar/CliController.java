package com.example.mealcalendar;

import com.example.mealcalendar.fillfridge.FridgeViewBoundaryCli;
import com.example.mealcalendar.findrecipe.*;
import com.example.mealcalendar.login.HelloViewControllerCli;
import com.example.mealcalendar.login.LoginViewControllerCli;
import com.example.mealcalendar.seteatingtime.MealCalenderViewControllerCli;

public class CliController {

    // Istanza di AntiCodeSmellPrinter per sostituire System.out.println
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("CliController");

    public void navigateTo(String view) throws Exception {
        switch (view) {
            case "recipeedit":
                printer.print("🔹 Navigazione alla schermata di modifica ricette (CLI).");
                RecipeEditViewControllerCli recipeEditViewControllerCli = new RecipeEditViewControllerCli();
                recipeEditViewControllerCli.start();
                break;
            case "fridge":
                printer.print("🔹 Navigazione alla schermata frigo (CLI).");
                FridgeViewBoundaryCli frigorifero = new FridgeViewBoundaryCli();
                frigorifero.start();
                break;
            case "registration":
                printer.print("🔹 Navigazione alla schermata hello (CLI).");
                HelloViewControllerCli helloview = new HelloViewControllerCli();
                helloview.start();
                break;
            case "addrecipe":
                printer.print("🔹 Navigazione alla schermata aggiunta ricetta (CLI).");
                RecipeAddViewControllerCli addrecipeboundary = new RecipeAddViewControllerCli();
                addrecipeboundary.start();
                break;
            case "login":
                printer.print("🔹 Navigazione alla schermata di login (CLI).");
                LoginViewControllerCli loginView = new LoginViewControllerCli();
                loginView.start();
                break;
            case "guest":
                printer.print("🔹 Navigazione alla schermata principale guest (CLI).");
                MainMenuViewControllerGuestCli mainMenuViewGuest = new MainMenuViewControllerGuestCli();
                mainMenuViewGuest.start();
                break;
            case "mainmenu":
                printer.print("🔹 Navigazione alla schermata principale (CLI).");
                MainMenuViewControllerCli mainMenuView = new MainMenuViewControllerCli();
                mainMenuView.start();
                break;
            case "trovaricette":
                printer.print("🔹 Navigazione alla schermata di trova ricette (CLI).");
                RecipeViewControllerCli recipeView = new RecipeViewControllerCli();
                recipeView.start();
                break;
            case "calendariopasti":
                printer.print("🔹 Navigazione alla schermata di calendario pasti (CLI).");
                MealCalenderViewControllerCli mealcalendarView = new MealCalenderViewControllerCli();
                mealcalendarView.start();
                break;
            default:
                printer.print("❌ Schermata non trovata.");
        }
    }

    public void navigateToRecipeEditWithRecipe(RecipeReturnBean ricetta) throws RecipeNavigationException {
        try {
            printer.print("🔹 Navigazione alla schermata di modifica ricetta (CLI) con ricetta selezionata.");
            RecipeEditViewControllerCli recipeEditViewControllerCli = new RecipeEditViewControllerCli(ricetta);
            recipeEditViewControllerCli.start();
        } catch (Exception e) {  // Sostituire Exception con eccezioni più specifiche se possibile
            throw new RecipeNavigationException("Errore durante la navigazione alla modifica ricetta.", e);
        }
    }


    // Metodo per navigare al calendario con una ricetta specifica
    public void navigateToCalendarWithRecipe(RecipeReturnBean ricetta) throws Exception {
        printer.print("🔹 Navigazione al calendario con la ricetta selezionata.");
        MealCalenderViewControllerCli calendario = new MealCalenderViewControllerCli(ricetta);
        calendario.printSelectionAndSendMail();
        calendario.start();
    }
    public void navigateToCalendarWithRestaurant() throws Exception {
        printer.print("🔹 Navigazione al calendario con la ricetta selezionata.");
        MealCalenderViewControllerCli calendario = new MealCalenderViewControllerCli();
        calendario.printSelectionAndSendMail();
        calendario.start();
    }
}
