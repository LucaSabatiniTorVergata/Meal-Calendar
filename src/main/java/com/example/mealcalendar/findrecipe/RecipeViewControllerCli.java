package com.example.mealcalendar.findrecipe;


import com.example.mealcalendar.AntiCodeSmellPrinter;
import com.example.mealcalendar.CliController;
import com.example.mealcalendar.SessionManagerSLT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RecipeViewControllerCli {

    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private final boolean vengoDaCalendar;
    private RecipeReturnBean ricettaSelezionata;

    // Istanza di AntiCodeSmellPrinter per sostituire System.out
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("RecipeVewBoundaryCli");

    // Costruttore per navigazione dal calendario
    public RecipeViewControllerCli(boolean vengoDaCalendar) {
        this.vengoDaCalendar = vengoDaCalendar;
    }

    // Costruttore di default per altri usi
    public RecipeViewControllerCli() {
        this.vengoDaCalendar = false;
    }

    public void start() throws Exception {
        boolean exit = false;
        while (!exit) {
            printer.print("\n===== Menu Ricette (CLI) =====");
            printer.print("1. Cerca Ricette");
            printer.print("2. Aggiungi Ricetta");
            printer.print("3. Modifica Ricetta");
            printer.print("4. Rimuovi Ricetta");
            printer.print("5. Torna Indietro");
            printer.print("6. Esci");
            printer.print("Scegli un'opzione: ");

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
                    printer.print(" Opzione non valida.");
            }
        }
    }

    // Metodo per cercare ricette (opzione 1)
    private void searchRecipies() throws Exception {
        printer.print("\n===== Ricerca Ricette =====");

        // Selezione del tipo di dieta
        printer.print("Seleziona Tipo Dieta:");
        printer.print("1. Vegan");
        printer.print("2. Vegetarian");
        printer.print("3. Omnivorous");
        printer.print("Scegli (1-3): ");
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
        printer.print("Seleziona Tipo Pasto:");
        printer.print("1. Breakfast");
        printer.print("2. Lunch");
        printer.print("3. Dinner");
        printer.print("Scegli (1-3): ");
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

        // Ottieni una lista di ricette come Optional
        Optional<List<RecipeReturnBean>> optionalListaRicette = controller.trovaricette();

        // Controlla se l'Optional contiene un valore
        if (optionalListaRicette.isEmpty() || optionalListaRicette.get().isEmpty()) {
            printer.print("Nessuna ricetta trovata con i filtri specificati.");
            return;
        }

        List<RecipeReturnBean> listaRicette = optionalListaRicette.get();

        // Visualizza le ricette trovate
        printer.print("\n===== Lista Ricette =====");
        for (int i = 0; i < listaRicette.size(); i++) {
            RecipeReturnBean ricetta = listaRicette.get(i);
            String riga = (i + 1) + ". " + ricetta.getRecipeName() + " - "
                    + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal() + " - "
                    + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                    + ricetta.getDescription() + " - " + ricetta.getAuthor();
            printer.print(riga);
        }

        printer.print("\nInserisci il numero della ricetta per visualizzarne i dettagli, oppure 0 per tornare:");
        String input = scanner.nextLine().trim();
        int scelta;
        try {
            scelta = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            printer.print("Input non valido.");
            return;
        }

        if (scelta > 0 && scelta <= listaRicette.size()) {
            ricettaSelezionata = listaRicette.get(scelta - 1);
            showRecipeDetails(ricettaSelezionata);

            if (vengoDaCalendar) {
                cliController.navigateToCalendarWithRecipe(ricettaSelezionata);
            }
        }
    }

    // Metodo per modificare una ricetta selezionata (opzione 3)
    private void editRecipe() throws RecipeEditException {
        if (ricettaSelezionata == null) {
            printer.print(" Nessuna ricetta selezionata da modificare.");
            return;
        }
        try {
            cliController.navigateToRecipeEditWithRecipe(ricettaSelezionata);
        } catch (Exception e) { // Sostituisci con le eccezioni specifiche che può generare
            throw new RecipeEditException("Errore durante la modifica della ricetta.", e);
        }
    }


    // Metodo per mostrare i dettagli di una ricetta
    private void showRecipeDetails(RecipeReturnBean ricetta) {
        printer.print("\n===== Dettagli Ricetta Selezionata =====");
        printer.print("Nome: " + ricetta.getRecipeName());
        printer.print("Tipo Dieta: " + ricetta.getTypeofDiet());
        printer.print("Tipo Pasto: " + ricetta.getTypeofMeal());
        printer.print("Numero Ingredienti: " + ricetta.getNumIngredients());
        printer.print("Ingredienti: " + ricetta.getIngredients());
        printer.print("Descrizione: " + ricetta.getDescription());
        printer.print("Autore: " + ricetta.getAuthor());
    }

    // Metodo per rimuovere una ricetta (opzione 4)
    private void removeRecipe() throws RecipeDaoException {
        printer.print("\n===== Rimozione Ricetta =====");
        if (ricettaSelezionata == null) {
            printer.print(" Nessuna ricetta selezionata da rimuovere.");
            return;
        }

        // Recupera lo username dell'utente loggato
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Verifica se l'utente è l'autore della ricetta
        if (ricettaSelezionata.getAuthor().equals(username)) {
            String ricettaCompleta = ricettaSelezionata.getRecipeName() + " - "
                    + ricettaSelezionata.getTypeofDiet() + " - "
                    + ricettaSelezionata.getTypeofMeal() + " - "
                    + ricettaSelezionata.getNumIngredients() + " - "
                    + ricettaSelezionata.getIngredients() + " - "
                    + ricettaSelezionata.getDescription() + " - "
                    + ricettaSelezionata.getAuthor();

            printer.print("Sei sicuro di voler rimuovere la ricetta '" + ricettaSelezionata.getRecipeName() + "'? (S/N): ");
            String confirmation = scanner.nextLine().trim();
            if ("S".equalsIgnoreCase(confirmation)) {
                RecipeEditController controller = new RecipeEditController();
                controller.rimuovi(ricettaCompleta);
                printer.print("✅ Ricetta '" + ricettaSelezionata.getRecipeName() + "' rimossa con successo.");
                ricettaSelezionata = null;
            } else {
                printer.print(" Rimozione annullata.");
            }
        } else {
            printer.print(" Non puoi rimuovere una ricetta che non hai creato.");
        }
    }
}
