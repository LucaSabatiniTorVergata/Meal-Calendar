package com.example.mealcalendar.cli;

import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.bean.PrenotazioneBean;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.controller_applicativo.PrenotazioneController;
import com.example.mealcalendar.exception.PrenotazioneException;
import com.example.mealcalendar.model.TipologiaRistorante;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class PrenotazioneRistoranteCLI {

    private final PrenotazioneController prenotazioneController = new PrenotazioneController();
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("Benvenuto nel sistema di prenotazione ristoranti!");
        boolean exit = false;
        TipologiaRistorante filtro = null;

        while (!exit) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Mostra ristoranti");
            System.out.println("2. Filtra per tipologia");
            System.out.println("3. Prenota un ristorante");
            System.out.println("4. Esci");
            System.out.print("Scegli un'opzione: ");

            String scelta = scanner.nextLine();
            try {
                switch (scelta) {
                    case "1" -> mostraRistoranti(filtro);
                    case "2" -> filtro = scegliTipologia();
                    case "3" -> prenotaRistorante(filtro);
                    case "4" -> exit = true;
                    default -> System.out.println("Opzione non valida, riprova.");
                }
            } catch (PrenotazioneException e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }

        System.out.println("Arrivederci!");
    }

    private void mostraRistoranti(TipologiaRistorante filtro) throws PrenotazioneException {
        List<RistoranteBean> ristoranti = prenotazioneController.getRistoranti();

        if (filtro != null) {
            ristoranti = ristoranti.stream()
                    .filter(r -> r.getTipologiaRistorante() == filtro)
                    .toList();
        }

        if (ristoranti.isEmpty()) {
            System.out.println("Nessun ristorante disponibile.");
        } else {
            System.out.println("\nRistoranti disponibili:");
            for (int i = 0; i < ristoranti.size(); i++) {
                RistoranteBean r = ristoranti.get(i);
                System.out.printf("%d. %s - %s (%s) | Posti disponibili: %d%n",
                        i + 1, r.getNome(), r.getIndirizzo(), r.getTipologiaRistorante(), r.getPostiDisponibili());
            }
        }
    }

    private TipologiaRistorante scegliTipologia() {
        System.out.println("\nSeleziona tipologia ristorante:");
        TipologiaRistorante[] tipi = TipologiaRistorante.values();
        for (int i = 0; i < tipi.length; i++) {
            System.out.printf("%d. %s%n", i + 1, tipi[i].name());
        }
        System.out.print("Scelta: ");

        try {
            int scelta = Integer.parseInt(scanner.nextLine());
            if (scelta >= 1 && scelta <= tipi.length) {
                System.out.println("Filtro impostato su: " + tipi[scelta - 1]);
                return tipi[scelta - 1];
            }
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida. Nessun filtro applicato.");
        }

        return null;
    }

    private void prenotaRistorante(TipologiaRistorante filtro) throws PrenotazioneException {
        List<RistoranteBean> ristoranti = prenotazioneController.getRistoranti();

        if (filtro != null) {
            ristoranti = ristoranti.stream()
                    .filter(r -> r.getTipologiaRistorante() == filtro)
                    .toList();
        }

        if (ristoranti.isEmpty()) {
            System.out.println("Nessun ristorante disponibile per prenotare.");
            return;
        }

        System.out.println("\nSeleziona il ristorante da prenotare:");
        for (int i = 0; i < ristoranti.size(); i++) {
            RistoranteBean r = ristoranti.get(i);
            System.out.printf("%d. %s - %s | Posti disponibili: %d%n", i + 1, r.getNome(), r.getIndirizzo(), r.getPostiDisponibili());
        }

        System.out.print("Scelta: ");
        int scelta;
        try {
            scelta = Integer.parseInt(scanner.nextLine());
            if (scelta < 1 || scelta > ristoranti.size()) {
                System.out.println("Scelta non valida.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
            return;
        }

        RistoranteBean ristoranteScelto = ristoranti.get(scelta - 1);

        System.out.print("Inserisci data prenotazione (YYYY-MM-DD): ");
        LocalDate dataPrenotazione;
        try {
            dataPrenotazione = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Data non valida.");
            return;
        }

        System.out.print("Inserisci ora prenotazione (HH:MM): ");
        String ora = scanner.nextLine();

        System.out.printf("Posti disponibili: %d. Quanti posti vuoi prenotare? ", ristoranteScelto.getPostiDisponibili());
        int posti;
        try {
            posti = Integer.parseInt(scanner.nextLine());
            if (posti > ristoranteScelto.getPostiDisponibili()) {
                System.out.println("Errore: non ci sono abbastanza posti disponibili.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Numero posti non valido. Imposto 1 posto.");
            posti = 1;
        }

        String usernameUtente = SessionManagerSLT.getInstance().getLoggedInUsername();
        PrenotazioneBean prenotazione = new PrenotazioneBean(
                null,
                dataPrenotazione,
                dataPrenotazione.plusDays(1),
                ora,
                usernameUtente,
                ristoranteScelto.getNome(),
                posti
        );

        // Prova a salvare la prenotazione e scala i posti disponibili nel controller
        prenotazioneController.salvaPrenotazione(prenotazione);

        System.out.printf("Prenotazione confermata per %s da %s alle ore %s per %d persone.%n",
                ristoranteScelto.getNome(), usernameUtente, ora, posti);
    }

    public static void main(String[] args) {
        PrenotazioneRistoranteCLI cli = new PrenotazioneRistoranteCLI();
        cli.start();
    }
}
