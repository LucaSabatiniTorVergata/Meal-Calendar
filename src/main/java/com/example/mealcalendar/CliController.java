package com.example.mealcalendar;

public class CliController {

    // Istanza di AntiCodeSmellPrinter per sostituire System.out.println
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("CliController");

    public void navigateTo(String view) throws Exception {
        switch (view) {
            case "recipeedit":
                printer.print("🔹 Navigazione alla schermata di modifica ricette (CLI).");
                RecipeEditViewBoundaryCli recipeEditViewBoundaryCli = new RecipeEditViewBoundaryCli();
                recipeEditViewBoundaryCli.start();
                break;
            case "fridge":
                printer.print("🔹 Navigazione alla schermata frigo (CLI).");
                FridgeViewBoundaryCli frigorifero = new FridgeViewBoundaryCli();
                frigorifero.start();
                break;
            case "registration":
                printer.print("🔹 Navigazione alla schermata hello (CLI).");
                HelloViewBoundaryCli helloview = new HelloViewBoundaryCli();
                helloview.start();
                break;
            case "addrecipe":
                printer.print("🔹 Navigazione alla schermata aggiunta ricetta (CLI).");
                RecipeAddViewBoundaryCli addrecipeboundary = new RecipeAddViewBoundaryCli();
                addrecipeboundary.start();
                break;
            case "login":
                printer.print("🔹 Navigazione alla schermata di login (CLI).");
                LoginViewBoundaryCli loginView = new LoginViewBoundaryCli();
                loginView.start();
                break;
            case "guest":
                printer.print("🔹 Navigazione alla schermata principale guest (CLI).");
                MainMenuViewBoundaryGuestCli mainMenuViewGuest = new MainMenuViewBoundaryGuestCli();
                mainMenuViewGuest.start();
                break;
            case "mainmenu":
                printer.print("🔹 Navigazione alla schermata principale (CLI).");
                MainMenuViewBoundaryCli mainMenuView = new MainMenuViewBoundaryCli();
                mainMenuView.start();
                break;
            case "ristoranteUser":
                printer.print("🔹 Navigazione alla schermata di ristorante user (CLI).");
                FindRestaurantViewBoundaryCli findRestaurantView = new FindRestaurantViewBoundaryCli();
                findRestaurantView.start();
                break;
            case "ristoranteguest":
                printer.print("🔹 Navigazione alla schermata di ristorante guest (CLI).");
                FindRestaurantViewBoundaryCli findRestaurantViewGuest = new FindRestaurantViewBoundaryCli();
                findRestaurantViewGuest.start();
                break;
            case "trovaricette":
                printer.print("🔹 Navigazione alla schermata di trova ricette (CLI).");
                RecipeVewBoundaryCli recipeView = new RecipeVewBoundaryCli();
                recipeView.start();
                break;
            case "calendariopasti":
                printer.print("🔹 Navigazione alla schermata di calendario pasti (CLI).");
                MealCalenderViewBoundaryCli mealcalendarView = new MealCalenderViewBoundaryCli();
                mealcalendarView.start();
                break;
            default:
                printer.print("❌ Schermata non trovata.");
        }
    }

    // Metodo per navigare alla modifica di una ricetta specifica
    public void navigateToRecipeEditWithRecipe(RecipeReturnBean ricetta) throws Exception {
        printer.print("🔹 Navigazione alla schermata di modifica ricetta (CLI) con ricetta selezionata.");
        RecipeEditViewBoundaryCli recipeEditViewBoundaryCli = new RecipeEditViewBoundaryCli(ricetta);
        recipeEditViewBoundaryCli.start();
    }

    // Metodo per navigare al calendario con una ricetta specifica
    public void navigateToCalendarWithRecipe(RecipeReturnBean ricetta) throws Exception {
        printer.print("🔹 Navigazione al calendario con la ricetta selezionata.");
        MealCalenderViewBoundaryCli calendario = new MealCalenderViewBoundaryCli(ricetta);
        calendario.start();
    }
}
