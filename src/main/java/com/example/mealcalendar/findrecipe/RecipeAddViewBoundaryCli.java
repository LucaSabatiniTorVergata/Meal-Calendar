package com.example.mealcalendar.findrecipe;

import com.example.mealcalendar.AntiCodeSmellPrinter;
import com.example.mealcalendar.CliController;
import com.example.mealcalendar.SessionManagerSLT;

import java.util.Scanner;

public class RecipeAddViewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final RecipeAddController controller = new RecipeAddController();
    private final CliController cliController = new CliController();
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("RecipeAddViewBoundaryCli");

    public void start() throws Exception {
        printer.print("===== Aggiungi Ricetta (CLI) =====");

        printer.print("Inserisci il nome della ricetta: ");
        String name = scanner.nextLine().trim();

        printer.print("Seleziona la dieta:");
        printer.print("1. Omnivorous");
        printer.print("2. Vegetarian");
        printer.print("3. Vegan");
        printer.print("Scegli (1-3): ");
        String dietChoice = scanner.nextLine().trim();
        String diet = switch (dietChoice) {
            case "1" -> "Omnivorous";
            case "2" -> "Vegetarian";
            case "3" -> "Vegan";
            default -> "";
        };

        printer.print("Seleziona il pasto:");
        printer.print("1. Breakfast");
        printer.print("2. Lunch");
        printer.print("3. Dinner");
        printer.print("Scegli (1-3): ");
        String mealChoice = scanner.nextLine().trim();
        String meal = switch (mealChoice) {
            case "1" -> "Breakfast";
            case "2" -> "Lunch";
            case "3" -> "Dinner";
            default -> "";
        };

        printer.print("Inserisci il numero degli ingredienti: ");
        String numIngredients = scanner.nextLine().trim();

        printer.print("Inserisci gli ingredienti (separati da virgola): ");
        String ingredients = scanner.nextLine().trim();

        printer.print("Inserisci la descrizione della ricetta: ");
        String description = scanner.nextLine().trim();

        String author = SessionManagerSLT.getInstance().getLoggedInUsername();

        AddRecipeBean bean = new AddRecipeBean(name, diet, meal, numIngredients, ingredients, description, author);
        boolean result = controller.salvaRicetta(bean);

        if (result) {
            printer.print("✅ Ricetta salvata con successo!");
        } else {
            printer.print("❌ Errore nel salvataggio della ricetta.");
        }

        printer.print("\nScegli dove andare:");
        printer.print("1. Torna alla Home");
        printer.print("2. Torna alla Lista Ricette");
        printer.print("3. Esci");
        printer.print("Opzione: ");
        String navChoice = scanner.nextLine().trim();

        switch (navChoice) {
            case "1" -> cliController.navigateTo("usermenu");
            case "2" -> cliController.navigateTo("recipe-view");
            default -> printer.print("Uscita dal menu.");
        }
    }
}
