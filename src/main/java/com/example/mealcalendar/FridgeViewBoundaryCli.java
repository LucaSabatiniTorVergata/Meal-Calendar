package com.example.mealcalendar;

import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class FridgeViewBoundaryCli {

    private final Scanner scanner = new Scanner(System.in);
    private FrigoriferoController frigoriferoController;
    private final IngredienteValidoSet ingredienteValidoSet = IngredienteValidoSet.getInstance();
    private final CliController cliController = new CliController();
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("FridgeViewCLI");

    public void start() throws Exception {
        frigoriferoController = new FrigoriferoController(SessionManagerSLT.getInstance().getDemo());

        while (true) {
            printer.print("\n===== GESTIONE FRIGORIFERO =====");
            printer.print("1. Aggiungi ingrediente");
            printer.print("2. Rimuovi ingrediente");
            printer.print("3. Mostra inventario");
            printer.print("4. Torna al menu principale");
            printer.print("Seleziona un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> aggiungiIngrediente();
                case "2" -> rimuoviIngrediente();
                case "3" -> mostraInventario();
                case "4" -> {
                    cliController.navigateTo("mainmenu");
                    return;
                }
                default -> printer.print("❌ Opzione non valida. Riprova.");
            }
        }
    }

    private void aggiungiIngrediente() {
        printer.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        printer.print("Quantità: ");
        String quantitaText = scanner.nextLine().trim();

        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            printer.print("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            printer.print("❌ Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.aggiungiIngrediente(nomeIngrediente, quantita);
            printer.print("✅ Ingrediente aggiunto con successo!");
        } catch (NumberFormatException e) {
            printer.print("❌ Errore: Inserisci un numero valido per la quantità!");
        }
    }

    private void rimuoviIngrediente() {
        printer.print("\nNome ingrediente: ");
        String nomeIngrediente = scanner.nextLine().trim();
        printer.print("Quantità da rimuovere: ");
        String quantitaText = scanner.nextLine().trim();

        if (nomeIngrediente.isEmpty() || quantitaText.isEmpty()) {
            printer.print("❌ Inserisci nome e quantità dell'ingrediente!");
            return;
        }

        if (!ingredienteValidoSet.isIngredienteValido(nomeIngrediente)) {
            printer.print("❌ Errore: Inserisci un ingrediente valido!");
            return;
        }

        try {
            int quantita = Integer.parseInt(quantitaText);
            frigoriferoController.rimuoviIngrediente(nomeIngrediente, quantita);
            printer.print("✅ Ingrediente rimosso con successo!");
        } catch (NumberFormatException e) {
            printer.print("❌ Errore: Inserisci un numero valido per la quantità!");
        }
    }

    private void mostraInventario() {
        printer.print("\n===== INVENTARIO =====");
        Map<String, Integer> inventario = frigoriferoController.getInventario();
        if (inventario.isEmpty()) {
            printer.print("🗒️ L'inventario è vuoto.");
        } else {
            for (Map.Entry<String, Integer> entry : inventario.entrySet()) {
                printer.print(entry.getKey() + " - Quantità: " + entry.getValue());
            }
        }
    }
}
