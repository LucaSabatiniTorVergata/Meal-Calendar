package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;

import java.util.List;
import java.util.Scanner;

public class GestisciPrenotazioneUserCLI {

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        String username = SessionManagerSLT.getInstance().getLoggedInUsername();
        System.out.println("Benvenuto " + username + "!");
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENU PRENOTAZIONI ---");
            System.out.println("1. Visualizza prenotazioni");
            System.out.println("2. Elimina prenotazione");
            System.out.println("3. Esci");
            System.out.print("Scegli un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> mostraPrenotazioniUtente(username);
                case "2" -> eliminaPrenotazioneUtente(username);
                case "3" -> exit = true;
                default -> System.out.println("Opzione non valida, riprova.");
            }
        }

        System.out.println("Arrivederci!");
    }

    private void mostraPrenotazioniUtente(String username) {
        List<PrenotazioneBean> tutte = prenotazioneController.getPrenotazioni();

        List<PrenotazioneBean> filtrate = tutte.stream()
                .filter(p -> p.getUsernameUtente().equals(username))
                .toList();

        if (filtrate.isEmpty()) {
            System.out.println("Non hai prenotazioni attive.");
        } else {
            System.out.println("\nLe tue prenotazioni:");
            for (PrenotazioneBean p : filtrate) {
                String stato = p.isScaduta() ? "SCADUTA" : "ATTIVA";
                System.out.printf("ID: %d | Ristorante: %s | Data: %s | Ora: %s | Posti: %d | Stato: %s%n",
                        p.getId(), p.getNomeRistorante(), p.getDataPrenotazione(),
                        p.getOraPrenotazione(), p.getPostiASedere(), stato);
            }
        }
    }

    private void eliminaPrenotazioneUtente(String username) {
        List<PrenotazioneBean> tutte = prenotazioneController.getPrenotazioni();
        List<PrenotazioneBean> filtrate = tutte.stream()
                .filter(p -> p.getUsernameUtente().equals(username))
                .toList();

        if (filtrate.isEmpty()) {
            System.out.println("Non ci sono prenotazioni da eliminare.");
            return;
        }

        System.out.println("Inserisci l'ID della prenotazione da eliminare:");
        int idDaEliminare;
        try {
            idDaEliminare = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID non valido.");
            return;
        }

        PrenotazioneBean daEliminare = filtrate.stream()
                .filter(p -> p.getId() == idDaEliminare)
                .findFirst()
                .orElse(null);

        if (daEliminare == null) {
            System.out.println("Prenotazione non trovata.");
            return;
        }

        System.out.printf("Sei sicuro di voler eliminare la prenotazione ID %d? (s/n): ", idDaEliminare);
        String conferma = scanner.nextLine();
        if (!conferma.equalsIgnoreCase("s")) {
            System.out.println("Operazione annullata.");
            return;
        }

        boolean eliminata = prenotazioneController.eliminaPrenotazione(daEliminare);
        if (eliminata) {
            System.out.println("Prenotazione eliminata correttamente!");
        } else {
            System.out.println("Errore: impossibile eliminare la prenotazione.");
        }
    }

    public static void main(String[] args) {
        GestisciPrenotazioneUserCLI cli = new GestisciPrenotazioneUserCLI();
        cli.start();
    }
}
