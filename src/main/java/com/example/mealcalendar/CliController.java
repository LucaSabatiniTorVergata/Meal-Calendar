package com.example.mealcalendar;


public class CliController {

    public void navigateTo(String view) throws Exception {
        switch (view) {
            case "fridge":
                System.out.println("🔹 Navigazione alla schermata frigo (CLI).");
                FridgeViewBoundaryCli frigorifero = new FridgeViewBoundaryCli();
                frigorifero.start();
                break;
            case "registration":
                System.out.println("🔹 Navigazione alla schermata hello (CLI).");
                HelloViewBoundaryCli helloview = new HelloViewBoundaryCli();
                helloview.start();
                break;
            case "addrecipe":
                System.out.println("🔹 Navigazione alla schermata aggiunta ricetta (CLI).");
                RecipeAddViewBoundaryCli addrecipeboundary = new RecipeAddViewBoundaryCli();
                addrecipeboundary.start();
                break;
            case "login":
                System.out.println("🔹 Navigazione alla schermata di login (CLI).");
                LoginViewBoundaryCli loginView = new LoginViewBoundaryCli();
                loginView.start();
                break;
            case "guest":
                System.out.println("🔹 Navigazione alla schermata principale guest (CLI).");
                MainMenuViewBoundaryGuestCli mainMenuViewGuest = new MainMenuViewBoundaryGuestCli();
                mainMenuViewGuest.start();
                break;
            case "mainmenu":
                System.out.println("🔹 Navigazione alla schermata principale (CLI).");
                MainMenuViewBoundaryCli mainMenuView = new MainMenuViewBoundaryCli();
                mainMenuView.start();
                break;
            case "ristoranteUser":
                System.out.println("🔹 Navigazione alla schermata di ristorante user (CLI).");
                FindRestaurantViewBoundaryCli findRestaurantView = new FindRestaurantViewBoundaryCli();
                findRestaurantView.start();
                break;
            case "ristoranteguest":
                System.out.println("🔹 Navigazione alla schermata di ristorante guest (CLI).");
                FindRestaurantViewBoundaryCli findRestaurantViewGuest = new FindRestaurantViewBoundaryCli();
                findRestaurantViewGuest.start();
                break;
            case "trovaricette":
                System.out.println("🔹 Navigazione alla schermata di trova ricette (CLI).");
                RecipeVewBoundaryCli recipeView = new RecipeVewBoundaryCli();
                recipeView.start();
                break;
            case "calendariopasti":
                System.out.println("🔹 Navigazione alla schermata di calendario pasti (CLI).");
                MealCalenderViewBoundaryCli mealcalendarView = new MealCalenderViewBoundaryCli();
                mealcalendarView.start();
                break;
            default:
                System.out.println("❌ Schermata non trovata.");
        }


    }
    public void navigateToCalendarWithRecipe(RecipeReturnBean ricetta) throws Exception {
        System.out.println("🔹 Navigazione al calendario con la ricetta selezionata.");
        MealCalenderViewBoundaryCli calendario = new MealCalenderViewBoundaryCli(ricetta);
        calendario.start();
    }
}
