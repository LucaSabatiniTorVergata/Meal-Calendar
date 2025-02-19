package com.example.mealcalendar;

import java.io.IOException;
import java.util.Scanner;

public class RecipeEditViewBoundaryCli {

    private RecipeReturnBean ricetta;
    private Scanner scanner = new Scanner(System.in);
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("RecipeEditViewBoundaryCli");

    // Costruttore senza parametri (eventuale modifica “a vuoto”)
    public RecipeEditViewBoundaryCli() {}

    // Costruttore che riceve la ricetta selezionata
    public RecipeEditViewBoundaryCli(RecipeReturnBean ricetta) {
        this.ricetta = ricetta;
    }

    public void start() throws RecipeNotSelectedException, IOException {
        if (ricetta == null) {
            throw new RecipeNotSelectedException();
        }

        printer.print("\n===== Modifica Ricetta (CLI) =====");
        printer.print("Ricetta attuale:");
        printer.print("Nome: " + ricetta.getRecipeName());
        printer.print("Tipo Dieta: " + ricetta.getTypeofDiet());
        printer.print("Tipo Pasto: " + ricetta.getTypeofMeal());
        printer.print("Numero Ingredienti: " + ricetta.getNumIngredients());
        printer.print("Ingredienti: " + ricetta.getIngredients());
        printer.print("Descrizione: " + ricetta.getDescription());
        printer.print("Autore: " + ricetta.getAuthor());
        printer.print("\nInserisci i nuovi valori (lascia vuoto per mantenere il valore attuale):");

        printer.print("Nuovo nome: ");
        String nuovoNome = scanner.nextLine();
        if (nuovoNome.isEmpty()) {
            nuovoNome = ricetta.getRecipeName();
        }

        printer.print("Nuovo Tipo Dieta (es. Vegano, Vegetariano, Omnivoro): ");
        String nuovoTipoDieta = scanner.nextLine();
        if (nuovoTipoDieta.isEmpty()) {
            nuovoTipoDieta = ricetta.getTypeofDiet();
        }

        printer.print("Nuovo Tipo Pasto (es. Colazione, Pranzo, Cena): ");
        String nuovoTipoPasto = scanner.nextLine();
        if (nuovoTipoPasto.isEmpty()) {
            nuovoTipoPasto = ricetta.getTypeofMeal();
        }

        printer.print("Nuovo Numero Ingredienti: ");
        String nuovoNumIngredienti = scanner.nextLine();
        if (nuovoNumIngredienti.isEmpty()) {
            nuovoNumIngredienti = ricetta.getNumIngredients();
        }

        printer.print("Nuovi Ingredienti: ");
        String nuoviIngredienti = scanner.nextLine();
        if (nuoviIngredienti.isEmpty()) {
            nuoviIngredienti = ricetta.getIngredients();
        }

        printer.print("Nuova Descrizione: ");
        String nuovaDescrizione = scanner.nextLine();
        if (nuovaDescrizione.isEmpty()) {
            nuovaDescrizione = ricetta.getDescription();
        }

        // Recupera lo username dalla sessione
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();

        // Utilizzo del Builder Pattern per creare il bean con i dati
        RecipeEdit2Bean bean = new RecipeEdit2Bean.Builder()
                .setRicettapresa(ricetta.getRecipeName() + " - " + ricetta.getTypeofDiet() + " - " + ricetta.getTypeofMeal()
                        + " - " + ricetta.getNumIngredients() + " - " + ricetta.getIngredients() + " - "
                        + ricetta.getDescription() + " - " + ricetta.getAuthor())
                .setRicetta(nuovoNome)
                .setTdieta(nuovoTipoDieta)
                .setTpasto(nuovoTipoPasto)
                .setNumingred(nuovoNumIngredienti)
                .setIngred(nuoviIngredienti)
                .setDesrcip(nuovaDescrizione)
                .setAutor(username)
                .build();

        // Passiamo il bean al controller
        RecipeEdit2Controller controller = new RecipeEdit2Controller(bean);
        controller.cambiaRicetta(); // Applica le modifiche

        printer.print("Ricetta modificata con successo!");

        // Menu per la navigazione successiva
        printer.print("\nCosa vuoi fare ora?");
        printer.print("1. Torna alla Lista delle Ricette");
        printer.print("2. Esci");
        printer.print("Opzione: ");
        String scelta = scanner.nextLine().trim();

        switch (scelta) {
            case "1":
                // Torna alla lista ricette
                break;
            case "2":
                printer.print("Uscita dal menu.");
                break;
            default:
                printer.print("Opzione non valida.");
                break;
        }
    }
}
