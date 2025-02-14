package com.example.mealcalendar;

import java.util.Scanner;

public class RecipeAddViewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final RecipeAddController controller = new RecipeAddController();
    private final CliController cliController = new CliController();

    public void start() throws Exception {
        System.out.println("===== Aggiungi Ricetta (CLI) =====");

        // Richiesta del nome della ricetta
        System.out.print("Inserisci il nome della ricetta: ");
        String name = scanner.nextLine().trim();

        // Selezione della dieta
        System.out.println("Seleziona la dieta:");
        System.out.println("1. Omnivorous");
        System.out.println("2. Vegetarian");
        System.out.println("3. Vegan");
        System.out.print("Scegli (1-3): ");
        String dietChoice = scanner.nextLine().trim();
        String diet;
        switch (dietChoice) {
            case "1":
                diet = "Omnivorous";
                break;
            case "2":
                diet = "Vegetarian";
                break;
            case "3":
                diet = "Vegan";
                break;
            default:
                diet = "";
                break;
        }

        // Selezione del pasto
        System.out.println("Seleziona il pasto:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Scegli (1-3): ");
        String mealChoice = scanner.nextLine().trim();
        String meal;
        switch (mealChoice) {
            case "1":
                meal = "Breakfast";
                break;
            case "2":
                meal = "Lunch";
                break;
            case "3":
                meal = "Dinner";
                break;
            default:
                meal = "";
                break;
        }

        // Numero degli ingredienti
        System.out.print("Inserisci il numero degli ingredienti: ");
        String numIngredients = scanner.nextLine().trim();

        // Ingredienti
        System.out.print("Inserisci gli ingredienti (separati da virgola): ");
        String ingredients = scanner.nextLine().trim();

        // Descrizione della ricetta
        System.out.print("Inserisci la descrizione della ricetta: ");
        String description = scanner.nextLine().trim();

        // Recupera l'autore dalla sessione
        String author = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Creazione del bean e salvataggio della ricetta
        AddRecipeBean bean = new AddRecipeBean(name, diet, meal, numIngredients, ingredients, description, author);
        boolean result = controller.salvaRicetta(bean);

        if (result) {
            System.out.println("✅ Ricetta salvata con successo!");
        } else {
            System.out.println("❌ Errore nel salvataggio della ricetta.");
        }

        // Opzioni di navigazione post-salvataggio
        System.out.println("\nScegli dove andare:");
        System.out.println("1. Torna alla Home");
        System.out.println("2. Torna alla Lista Ricette");
        System.out.println("3. Esci");
        System.out.print("Opzione: ");
        String navChoice = scanner.nextLine().trim();

        switch (navChoice) {
            case "1":
                cliController.navigateTo("usermenu");
                break;
            case "2":
                cliController.navigateTo("recipe-view");
                break;
            default:
                System.out.println("Uscita dal menu.");
                break;
        }
    }
}
