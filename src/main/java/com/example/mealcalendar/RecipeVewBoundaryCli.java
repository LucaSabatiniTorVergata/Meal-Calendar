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

    // Costruttore per gestire la navigazione da calendario
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

    // Metodo per cercare le ricette (case 1)
    private void searchRecipies() throws Exception {
        System.out.println("\n===== Ricerca Ricette =====");

        // Selezione del tipo di dieta
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

        // Selezione del tipo di pasto
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

            // Se vengo dal calendario, naviga al calendario con la ricetta selezionata
            if (vengoDaCalendar) {
                cliController.navigateToCalendarWithRecipe(ricettaSelezionata);
            }
        }
    }

    // Metodo per cercare e selezionare una ricetta da modificare (case 3)
    // Visualizza solo le ricette create dall'utente in sessione
    private void editRecipe() throws Exception {
        System.out.println("\n===== Ricerca Ricetta da Modificare =====");

        // Selezione del tipo di dieta
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

        // Selezione del tipo di pasto
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

        // Filtra le ricette in base all'utente in sessione
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        List<RecipeReturnBean> listaRicetteFiltrate = listaRicette.stream()
                .filter(ricetta -> ricetta.getAuthor().equals(username))
                .collect(Collectors.toList());

        if (listaRicetteFiltrate.isEmpty()) {
            System.out.println("Non hai ricette da modificare con i filtri specificati.");
            return;
        }

        // Mostra le ricette filtrate
        System.out.println("\n===== Lista Ricette Creata da Te =====");
        for (int i = 0; i < listaRicetteFiltrate.size(); i++) {
            RecipeReturnBean ricetta = listaRicetteFiltrate.get(i);
            String riga = (i + 1) + ". " + ricetta.getRecipeName() + " - "
                    + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal() + " - "
                    + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                    + ricetta.getDescription() + " - " + ricetta.getAuthor();
            System.out.println(riga);
        }

        System.out.println("\nInserisci il numero della ricetta da modificare, oppure 0 per tornare:");
        String input = scanner.nextLine().trim();
        int scelta;
        try {
            scelta = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
            return;
        }

        if (scelta > 0 && scelta <= listaRicetteFiltrate.size()) {
            ricettaSelezionata = listaRicetteFiltrate.get(scelta - 1);
            showRecipeDetails(ricettaSelezionata);
            // Naviga alla schermata di modifica passando la ricetta selezionata
            cliController.navigateToRecipeEditWithRecipe(ricettaSelezionata);
        }
    }

    // Metodo per cercare e selezionare una ricetta da rimuovere (opzione 4)
    // Visualizza solo le ricette create dall'utente in sessione
    private void removeRecipe() throws Exception {
        System.out.println("\n===== Ricetta da Rimuovere =====");

        // Selezione del tipo di dieta
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

        // Selezione del tipo di pasto
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

        // Controlla se la lista è null
        if (listaRicette == null) {
            System.out.println("Nessuna ricetta trovata con i filtri specificati.");
            return;
        }

        // Filtra le ricette in base all'utente in sessione
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        List<RecipeReturnBean> listaRicetteFiltrate = listaRicette.stream()
                .filter(ricetta -> ricetta.getAuthor().equals(username))
                .collect(Collectors.toList());

        if (listaRicetteFiltrate.isEmpty()) {
            System.out.println("Non hai ricette da rimuovere con i filtri specificati.");
            return;
        }

        // Mostra le ricette filtrate
        System.out.println("\n===== Lista Ricette Creata da Te =====");
        for (int i = 0; i < listaRicetteFiltrate.size(); i++) {
            RecipeReturnBean ricetta = listaRicetteFiltrate.get(i);
            String riga = (i + 1) + ". " + ricetta.getRecipeName() + " - "
                    + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal() + " - "
                    + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                    + ricetta.getDescription() + " - " + ricetta.getAuthor();
            System.out.println(riga);
        }

        System.out.println("\nInserisci il numero della ricetta da rimuovere, oppure 0 per tornare:");
        String input = scanner.nextLine().trim();
        int scelta;
        try {
            scelta = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
            return;
        }

        if (scelta > 0 && scelta <= listaRicetteFiltrate.size()) {
            ricettaSelezionata = listaRicetteFiltrate.get(scelta - 1);
            showRecipeDetails(ricettaSelezionata);
            // Chiedi conferma per la cancellazione
            System.out.print("Sei sicuro di voler rimuovere questa ricetta? (s/n): ");
            String conferma = scanner.nextLine().trim();
            if (conferma.equalsIgnoreCase("s")) {
                RecipeEditController controllerDelete = new RecipeEditController();
                // Costruisco l'identificatore della ricetta nello stesso formato usato per la modifica
                String recipeIdentifier = ricettaSelezionata.getRecipeName() + " - "
                        + ricettaSelezionata.getTypeofDiet() + " - "
                        + ricettaSelezionata.getTypeofMeal() + " - "
                        + ricettaSelezionata.getNumIngredients() + " - "
                        + ricettaSelezionata.getIngredients() + " - "
                        + ricettaSelezionata.getDescription() + " - "
                        + ricettaSelezionata.getAuthor();
                controllerDelete.rimuovi(recipeIdentifier);
                System.out.println("Ricetta rimossa con successo!");
            } else {
                System.out.println("Operazione annullata.");
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
        System.out.println("\nPremi invio per continuare.");
        scanner.nextLine();
    }
}
