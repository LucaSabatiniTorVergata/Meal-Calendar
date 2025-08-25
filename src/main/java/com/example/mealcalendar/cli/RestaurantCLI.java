package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;

import java.util.List;
import java.util.Scanner;

public class RestaurantCLI {

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        String nomeRistorante = SessionManagerSLT.getInstance().getLoggedInUsername();
        System.out.println("Benvenuto Ristorante: " + nomeRistorante);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- MENU RISTORANTE ---");
            System.out.println("1. Visualizza prenotazioni");
            System.out.println("2. Elimina prenotazione");
            System.out.println("3. Esci");
            System.out.print("Scegli un'opzione: ");

            String scelta = scanner.nextLine();

            switch (scelta) {
                case "1" -> mostraPrenotazioniRistorante(nomeRistorante);
                case "2" -> eliminaPrenotazioneRistorante(nomeRistorante);
                case "3" -> exit = true;
                default -> System.out.println("Opzione non valida, riprova.");
            }
        }

        System.out.println("Arrivederci!");
    }

    private void mostraPrenotazioniRistorante(String nomeRistorante) {
        List<PrenotazioneBean> tutte = prenotazioneController.getPrenotazioni();

        List<PrenotazioneBean> filtrate = tutte.stream()
                .filter(p -> p.getNomeRistorante().equals(nomeRistorante))
                .toList();

        if (filtrate.isEmpty()) {
            System.out.println("Non ci sono prenotazioni per il tuo ristorante.");
        } else {
            System.out.println("\nPrenotazioni per " + nomeRistorante + ":");
            for (PrenotazioneBean p : filtrate) {
                String stato = p.isScaduta() ? "SCADUTA" : "ATTIVA";
                System.out.printf("ID: %d | Utente: %s | Data: %s | Ora: %s | Posti: %d | Stato: %s%n",
                        p.getId(), p.getUsernameUtente(), p.getDataPrenotazione(),
                        p.getOraPrenotazione(), p.getPostiASedere(), stato);
            }
        }
    }

    private void eliminaPrenotazioneRistorante(String nomeRistorante) {
        List<PrenotazioneBean> tutte = prenotazioneController.getPrenotazioni();

        List<PrenotazioneBean> filtrate = tutte.stream()
                .filter(p -> p.getNomeRistorante().equals(nomeRistorante))
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
        RestaurantCLI cli = new RestaurantCLI();
        cli.start();
    }
}
