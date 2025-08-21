package com.example.mealcalendar.cli;

import com.example.mealcalendar.dao.RistoranteDao;
import com.example.mealcalendar.bean.RistoranteBean;
import com.example.mealcalendar.model.TipologiaRistorante;

import java.util.Scanner;

public class RegisterCLI {

    private final Scanner scanner;
    private final RistoranteDao ristoranteDao = new RistoranteDao();

    public RegisterCLI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("=== Registrazione Nuovo Ristorante ===");

        System.out.println("Inserisci nome del ristorante:");
        String nome = scanner.nextLine().trim();

        System.out.println("Inserisci indirizzo:");
        String indirizzo = scanner.nextLine().trim();

        System.out.println("Inserisci numero posti disponibili:");
        int posti;
        try {
            posti = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Numero posti non valido.");
            return;
        }

        System.out.println("Seleziona tipologia (VEGANO, VEGETARIANO, ONNIVORO):");
        String tipologiaInput = scanner.nextLine().trim().toUpperCase();
        TipologiaRistorante tipologia;
        try {
            tipologia = TipologiaRistorante.valueOf(tipologiaInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Tipologia non valida, default ONNIVORO.");
            tipologia = TipologiaRistorante.ONNIVORO;
        }

        // Creazione del bean senza menu
        RistoranteBean ristorante = new RistoranteBean(nome, indirizzo, posti, tipologia);

        try {
            ristoranteDao.aggiungiRistorante(ristorante);
            System.out.println("Ristorante registrato con successo!");
        } catch (Exception e) {
            System.out.println("Errore nella registrazione: " + e.getMessage());
        }
    }
}
