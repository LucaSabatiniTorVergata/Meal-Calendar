package com.example.mealcalendar;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import static com.example.mealcalendar.FridgeViewBoundary.usePersistence;


public class FridgeViewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private final Logger logger = Logger.getLogger(getClass().getName());
    private FrigoriferoController frigoriferoController;
    private final IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();
    private final CliController cliController = new CliController();
    public void start() throws Exception {
        // Inizializzazione del controller
        try {
            frigoriferoController = new FrigoriferoController(usePersistence);
        } catch (IOException e) {
            logger.severe("Errore nell'inizializzazione del controller: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n===== GESTIONE FRIGORIFERO =====");
            System.out.println("1. Aggiungi ingrediente");
            System.out.println("2. Rimuovi ingrediente");
            System.out.println("3. Mostra inventario");
            System.out.println("4. Abilita persistenza");
            System.out.println("5. Disabilita persistenza");
            System.out.println("6. Torna al menu principale");
            System.out.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1":
                    aggiungiIngrediente();
                    break;
                case "2":
                    rimuoviIngrediente();
                    break;
                case "3":
                    mostraInventario();
                    break;
                case "4":
                    abilitaPersistenza();
                    break;
                case "5":
                    disabilitaPersistenza();
                    break;
                case "6":
                    cliController.navigateTo("mainmenu");
                    return;
                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }
    }

    private void aggiungiIngrediente() {
        System.out.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        System.out.print("Quantità: ");
        String quantitaText = scanner.nextLine().trim();

        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

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

    private void rimuoviIngrediente() {
        System.out.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        System.out.print("Quantità da rimuovere: ");
        String quantitaText = scanner.nextLine().trim();

        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            System.out.println("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

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

    private void mostraInventario() {
        System.out.println("\n===== INVENTARIO =====");
        Map<String, Integer> inventario = frigoriferoController.getInventario();
        if (inventario.isEmpty()) {
            System.out.println("L'inventario è vuoto.");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                System.out.println(entry.getKey() + " - Quantità: " + entry.getValue());
            }
        }
    }

    private void abilitaPersistenza() {
        usePersistence = true;
        aggiornaController();
        System.out.println("✅ Persistenza abilitata.");
    }

    private void disabilitaPersistenza() {
        usePersistence = false;
        aggiornaController();
        System.out.println("✅ Persistenza disabilitata.");
    }

    private void aggiornaController() {
        try {
            frigoriferoController = new FrigoriferoController(usePersistence);
            logger.info("Persistenza impostata su: " + usePersistence);
        } catch (IOException e) {
            logger.severe("Errore nell'inizializzazione del controller: " + e.getMessage());
        }
    }
}
