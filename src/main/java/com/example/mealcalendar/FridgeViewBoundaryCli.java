package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class FridgeViewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final Logger logger = Logger.getLogger(getClass().getName());
    private FrigoriferoController frigoriferoController;
    private final IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();
    private final CliController cliController = new CliController();

    public void start() throws Exception {
        // Inizializzazione del controller
        try {
            frigoriferoController = new FrigoriferoController(SessionManagerSLT.getInstance().getDemo());
        } catch (IOException e) {
            logger.severe("Errore nell'inizializzazione del controller: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n===== GESTIONE FRIGORIFERO =====");
            System.out.println("1. Aggiungi ingrediente");
            System.out.println("2. Rimuovi ingrediente");
            System.out.println("3. Mostra inventario");
            System.out.println("4. Torna al menu principale");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> aggiungiIngrediente();
                case "2" -> rimuoviIngrediente();
                case "3" -> mostraInventario();
                case "4" -> {
                    cliController.navigateTo("mainmenu");
                    return;
                }
                default -> System.out.println("❌ Opzione non valida. Riprova.");
            }
        }
    }

    // Aggiunge un ingrediente all'inventario
    private void aggiungiIngrediente() {
        System.out.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        System.out.print("Quantità: ");
        String quantitaText = scanner.nextLine().trim();

        // Controlla se i campi sono vuoti
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        // Verifica se l'ingrediente è valido
        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            System.out.println("❌ Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);
            System.out.println("✅ Ingrediente aggiunto con successo!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Errore: Inserisci un numero valido per la quantità!");
        }
    }

    // Rimuove un ingrediente dall'inventario
    private void rimuoviIngrediente() {
        System.out.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        System.out.print("Quantità da rimuovere: ");
        String quantitaText = scanner.nextLine().trim();

        // Controlla se i campi sono vuoti
        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        // Verifica se l'ingrediente è valido
        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            System.out.println("❌ Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.rimuoviIngrediente(nomeIngrediente, quantita);
            System.out.println("✅ Ingrediente rimosso con successo!");
        } catch (NumberFormatException e) {
            System.out.println("❌ Errore: Inserisci un numero valido per la quantità!");
        }
    }

    // Mostra l'inventario degli ingredienti
    private void mostraInventario() {
        System.out.println("\n===== INVENTARIO =====");
        Map<String, Integer> inventario = frigoriferoController.getInventario();
        if (inventario.isEmpty()) {
            System.out.println("🗒️ L'inventario è vuoto.");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                System.out.println(entry.getKey() + " - Quantità: " + entry.getValue());
            }
        }
    }
}
