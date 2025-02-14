package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RecipeVewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private final boolean vengoDaCalendar;
    private RecipeReturnBean ricettaSelezionata;

    // Costruttore per gestire la navigazione
    public RecipeVewBoundaryCli(boolean vengoDaCalendar) {
        this.vengoDaCalendar = vengoDaCalendar;
    }

    // Costruttore di default per compatibilità con altre chiamate
    public RecipeVewBoundaryCli() {
        this.vengoDaCalendar = false;
    }

    public void start() throws Exception {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Menu Ricette (CLI) =====");
            System.out.println("1. Cerca Ricette");
            System.out.println("2. Aggiungi Ricetta");
            System.out.println("3. Modifica Ricetta");
            System.out.println("4. Torna Indietro");
            System.out.println("5. Esci");
            System.out.print("Scegli un'opzione: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchRecipies();
                    break;
                case "2":
                    cliController.navigateTo("addrecipe");
                    break;
                case "3":
                   // editRecipe();
                    break;
                case "4":
                    // Torna indietro in base alla schermata di provenienza
                    if (vengoDaCalendar) {
                        cliController.navigateTo("calendariopasti");
                    } else {
                        cliController.navigateTo("mainmenu");
                    }
                    exit = true;
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.out.println("❌ Opzione non valida.");
            }
        }
    }

    private void searchRecipies() throws Exception {
        System.out.println("\n===== Ricerca Ricette =====");

        // Selezione tipo di dieta
        System.out.println("Seleziona Tipo Dieta:");
        System.out.println("1. Vegan");
        System.out.println("2. Vegetarian");
        System.out.println("3. Omnivorous");
        System.out.print("Scegli (1-3): ");
        String dietChoice = scanner.nextLine().trim();
        String tipoDieta;
        switch (dietChoice) {
            case "1":
                tipoDieta = "Vegan";
                break;
            case "2":
                tipoDieta = "Vegetarian";
                break;
            case "3":
                tipoDieta = "Omnivorous";
                break;
            default:
                tipoDieta = "";
                break;
        }

        // Selezione tipo di pasto
        System.out.println("Seleziona Tipo Pasto:");
        System.out.println("1. Breakfast");
        System.out.println("2. Lunch");
        System.out.println("3. Dinner");
        System.out.print("Scegli (1-3): ");
        String mealChoice = scanner.nextLine().trim();
        String tipoPasto;
        switch (mealChoice) {
            case "1":
                tipoPasto = "Breakfast";
                break;
            case "2":
                tipoPasto = "Lunch";
                break;
            case "3":
                tipoPasto = "Dinner";
                break;
            default:
                tipoPasto = "";
                break;
        }

        RecipeSearchFiltersBean filters = new RecipeSearchFiltersBean(tipoDieta, tipoPasto);
        RecipeSearchController controller = new RecipeSearchController(filters);

        List<RecipeReturnBean> listaRicette;
        try {
            listaRicette = controller.trovaricette();
        } catch (IOException e) {
            System.out.println("Errore durante la ricerca delle ricette: " + e.getMessage());
            return;
        }

        if (listaRicette == null || listaRicette.isEmpty()) {
            System.out.println("Nessuna ricetta trovata con i filtri specificati.");
            return;
        }

        // Mostra le ricette trovate
        System.out.println("\n===== Lista Ricette =====");
        for (int i = 0; i < listaRicette.size(); i++) {
            RecipeReturnBean ricetta = listaRicette.get(i);
            String riga = (i + 1) + ". " + ricetta.getRecipeName() + " - "
                    + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal() + " - "
                    + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                    + ricetta.getDescription() + " - " + ricetta.getAuthor();
            System.out.println(riga);
        }

        // Chiedi all'utente se vuole vedere i dettagli di una ricetta
        System.out.println("\nInserisci il numero della ricetta per visualizzarne i dettagli, oppure 0 per tornare:");
        String input = scanner.nextLine().trim();
        int scelta;
        try {
            scelta = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
            return;
        }

        if (scelta > 0 && scelta <= listaRicette.size()) {
            ricettaSelezionata = listaRicette.get(scelta - 1);
            showRecipeDetails(ricettaSelezionata);

            // Se vengo dal calendario, salvo la ricetta e navigo lì
            if (vengoDaCalendar) {
                cliController.navigateToCalendarWithRecipe(ricettaSelezionata);
            }
        }
    }

    private void showRecipeDetails(RecipeReturnBean ricetta) {
        System.out.println("\n===== Dettagli Ricetta =====");
        System.out.println("Nome: " + ricetta.getRecipeName());
        System.out.println("Tipo Dieta: " + ricetta.getTypeofDiet());
        System.out.println("Tipo Pasto: " + ricetta.getTypeofMeal());
        System.out.println("Numero Ingredienti: " + ricetta.getNumIngredients());
        System.out.println("Ingredienti: " + ricetta.getIngredients());
        System.out.println("Descrizione: " + ricetta.getDescription());
        System.out.println("Autore: " + ricetta.getAuthor());
        System.out.println("\nPremi invio per tornare al menu.");
        scanner.nextLine();
    }
}
