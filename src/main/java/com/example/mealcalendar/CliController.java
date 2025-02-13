package com.example.mealcalendar;

public class CliController {

    public void navigateTo(String view) {
        switch (view) {
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
            default:
                System.out.println("❌ Schermata non trovata.");
        }
    }
}
