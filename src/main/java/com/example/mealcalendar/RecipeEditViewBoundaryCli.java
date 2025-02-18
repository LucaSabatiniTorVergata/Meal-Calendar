package com.example.mealcalendar;

import java.util.List;
import java.util.Scanner;

public class RecipeEditViewBoundaryCli {

    private RecipeReturnBean ricetta;
    private Scanner scanner = new Scanner(System.in);

    // Costruttore senza parametri (eventuale modifica “a vuoto”)
    public RecipeEditViewBoundaryCli() {}

    // Costruttore che riceve la ricetta selezionata
    public RecipeEditViewBoundaryCli(RecipeReturnBean ricetta) {
        this.ricetta = ricetta;
    }

    public void start() throws Exception {
        if (ricetta == null) {
            System.out.println("Nessuna ricetta selezionata per la modifica.");
            return;
        }

        System.out.println("\n===== Modifica Ricetta (CLI) =====");
        System.out.println("Ricetta attuale:");
        System.out.println("Nome: " + ricetta.getRecipeName());
        System.out.println("Tipo Dieta: " + ricetta.getTypeofDiet());
        System.out.println("Tipo Pasto: " + ricetta.getTypeofMeal());
        System.out.println("Numero Ingredienti: " + ricetta.getNumIngredients());
        System.out.println("Ingredienti: " + ricetta.getIngredients());
        System.out.println("Descrizione: " + ricetta.getDescription());
        System.out.println("Autore: " + ricetta.getAuthor());
        System.out.println("\nInserisci i nuovi valori (lascia vuoto per mantenere il valore attuale):");

        System.out.print("Nuovo nome: ");
        String nuovoNome = scanner.nextLine();
        if (nuovoNome.isEmpty()) {
            nuovoNome = ricetta.getRecipeName();
        }

        System.out.print("Nuovo Tipo Dieta (es. Vegano, Vegetariano, Omnivoro): ");
        String nuovoTipoDieta = scanner.nextLine();
        if (nuovoTipoDieta.isEmpty()) {
            nuovoTipoDieta = ricetta.getTypeofDiet();
        }

        System.out.print("Nuovo Tipo Pasto (es. Colazione, Pranzo, Cena): ");
        String nuovoTipoPasto = scanner.nextLine();
        if (nuovoTipoPasto.isEmpty()) {
            nuovoTipoPasto = ricetta.getTypeofMeal();
        }

        System.out.print("Nuovo Numero Ingredienti: ");
        String nuovoNumIngredienti = scanner.nextLine();
        if (nuovoNumIngredienti.isEmpty()) {
            nuovoNumIngredienti = ricetta.getNumIngredients();
        }

        System.out.print("Nuovi Ingredienti: ");
        String nuoviIngredienti = scanner.nextLine();
        if (nuoviIngredienti.isEmpty()) {
            nuoviIngredienti = ricetta.getIngredients();
        }

        System.out.print("Nuova Descrizione: ");
        String nuovaDescrizione = scanner.nextLine();
        if (nuovaDescrizione.isEmpty()) {
            nuovaDescrizione = ricetta.getDescription();
        }

        // Recupera lo username dalla sessione (ipotizzando che SessionManagerSLT sia implementato anche per CLI)
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Crea il bean con i dati:
        RecipeEdit2Bean bean = new RecipeEdit2Bean(
                ricetta.getRecipeName() + " - " + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal()
                        + " - " + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                        + ricetta.getDescription() + " - " + ricetta.getAuthor(),
                nuovoNome,
                nuovoTipoDieta,
                nuovoTipoPasto,
                nuovoNumIngredienti,
                nuoviIngredienti,
                nuovaDescrizione,
                username
        );
        RecipeEdit2Controller controller = new RecipeEdit2Controller(bean);
        controller.cambiaRicetta(); // Applica le modifiche

        System.out.println("Ricetta modificata con successo!");

        // Menu per la navigazione successiva
        System.out.println("\nCosa vuoi fare ora?");
        System.out.println("1. Torna alla Lista delle Ricette");
        System.out.println("2. Esci");
        System.out.print("Opzione: ");
        String scelta = scanner.nextLine().trim();

        switch (scelta) {
            case "1":
                // Torna alla lista ricette
                // In un'applicazione CLI, puoi invocare un altro metodo per visualizzare la lista delle ricette
                break;
            case "2":
                System.out.println("Uscita dal menu.");
                break;
            default:
                System.out.println("Opzione non valida.");
                break;
        }
    }
}
