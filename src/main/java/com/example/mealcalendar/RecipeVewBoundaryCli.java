package com.example.mealcalendar;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RecipeVewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private final boolean vengoDaCalendar;
    private RecipeReturnBean ricettaSelezionata;

    // Constructor for calendar navigation
    public RecipeVewBoundaryCli(boolean vengoDaCalendar) {
        this.vengoDaCalendar = vengoDaCalendar;
    }

    // Default constructor for other uses
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
            System.out.println("4. Rimuovi Ricetta");
            System.out.println("5. Torna Indietro");
            System.out.println("6. Esci");
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
                    editRecipe();
                    break;
                case "4":
                    removeRecipe();
                    break;
                case "5":
                    if (vengoDaCalendar) {
                        cliController.navigateTo("calendariopasti");
                    } else {
                        cliController.navigateTo("mainmenu");
                    }
                    exit = true;
                    break;
                case "6":
                    exit = true;
                    break;
                default:
                    System.out.println("❌ Opzione non valida.");
            }
        }
    }

    // Method for searching recipes (option 1)
    private void searchRecipies() throws Exception {
        System.out.println("\n===== Ricerca Ricette =====");

        // Diet type selection
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

        // Meal type selection
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

        // Display the found recipes
        System.out.println("\n===== Lista Ricette =====");
        for (int i = 0; i < listaRicette.size(); i++) {
            RecipeReturnBean ricetta = listaRicette.get(i);
            String riga = (i + 1) + ". " + ricetta.getRecipeName() + " - "
                    + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal() + " - "
                    + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                    + ricetta.getDescription() + " - " + ricetta.getAuthor();
            System.out.println(riga);
        }

        // Ask the user if they want to view a recipe's details
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

            // If navigating from the calendar, go back to calendar with the selected recipe
            if (vengoDaCalendar) {
                cliController.navigateToCalendarWithRecipe(ricettaSelezionata);
            }
        }
    }

    // Method to edit a selected recipe (option 3)
    private void editRecipe() throws Exception {
        if (ricettaSelezionata == null) {
            System.out.println("❌ Nessuna ricetta selezionata da modificare.");
            return;
        }

        // Use the existing navigate method
        cliController.navigateToRecipeEditWithRecipe(ricettaSelezionata);
    }

    private void showRecipeDetails(RecipeReturnBean ricetta) {
        System.out.println("\n===== Dettagli Ricetta Selezionata =====");
        System.out.println("Nome: " + ricetta.getRecipeName());
        System.out.println("Tipo Dieta: " + ricetta.getTypeofDiet());
        System.out.println("Tipo Pasto: " + ricetta.getTypeofMeal());
        System.out.println("Numero Ingredienti: " + ricetta.getNumIngredients());
        System.out.println("Ingredienti: " + ricetta.getIngredients());
        System.out.println("Descrizione: " + ricetta.getDescription());
        System.out.println("Autore: " + ricetta.getAuthor());
    }

    // Method for removing a recipe (option 4)
    private void removeRecipe() throws IOException {
        System.out.println("\n===== Rimozione Ricetta =====");
        if (ricettaSelezionata == null) {
            System.out.println("❌ Nessuna ricetta selezionata da rimuovere.");
            return;
        }

        // Recupera lo username dell'utente loggato
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Verifica se l'utente è l'autore della ricetta
        if (ricettaSelezionata.getAuthor().equals(username)) {
            // Crea la stringa completa della ricetta, con tutti i dettagli separati da trattini
            String ricettaCompleta = ricettaSelezionata.getRecipeName() + " - "
                    + ricettaSelezionata.getTypeofDiet() + " - "
                    + ricettaSelezionata.getTypeofMeal() + " - "
                    + ricettaSelezionata.getNumIngredients() + " - "
                    + ricettaSelezionata.getIngredients() + " - "
                    + ricettaSelezionata.getDescription() + " - "
                    + ricettaSelezionata.getAuthor();

            // Chiedi conferma per la rimozione
            System.out.print("Sei sicuro di voler rimuovere la ricetta '" + ricettaSelezionata.getRecipeName() + "'? (S/N): ");
            String confirmation = scanner.nextLine().trim();
            if ("S".equalsIgnoreCase(confirmation)) {
                // Passa l'intera stringa al controller per la rimozione
                RecipeEditController controller = new RecipeEditController();
                controller.rimuovi(ricettaCompleta); // Passa tutta la stringa
                System.out.println("✅ Ricetta '" + ricettaSelezionata.getRecipeName() + "' rimossa con successo.");
                ricettaSelezionata = null; // Clear the selected recipe
            } else {
                System.out.println("❌ Rimozione annullata.");
            }
        } else {
            // Se l'utente non è l'autore, mostra un messaggio di errore
            System.out.println("❌ Non puoi rimuovere una ricetta che non hai creato.");
        }
    }



}
