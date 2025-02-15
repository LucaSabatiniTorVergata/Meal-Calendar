package com.example.mealcalendar;

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
        // Il primo parametro (ricettascelta) è qui costruito a partire dai dati originali (puoi modificarlo in base alla logica della tua applicazione)
        RecipeEdit2Bean bean = new RecipeEdit2Bean(
                ricetta.getRecipeName() + " - " + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal()
                        + " - " + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - " + ricetta.getDescription() + " - " + ricetta.getAuthor(),
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
        // Puoi aggiungere qui la navigazione al menu principale o ad altra schermata CLI
    }
}
