package com.example.mealcalendar;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class FindRestaurantViewBoundaryCli {

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Richiesta del tipo di dieta
        System.out.println("Inserisci il tipo di dieta (Vegano, Vegetariano, Onnivoro): ");
        String tipoDieta = scanner.nextLine().trim();
        // Se il tipo di dieta è "Onnivoro", non viene applicato alcun filtro
        if (tipoDieta.equalsIgnoreCase("Onnivoro")) {
            tipoDieta = "";
        }

        // Richiesta del pasto
        System.out.println("Inserisci il pasto (Colazione, Pranzo, Cena): ");
        String pasto = scanner.nextLine().trim();

        // Richiesta della distanza in km
        System.out.println("Inserisci la distanza massima (in km): ");
        double distanza = 0;
        try {
            distanza = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valore di distanza non valido. Inserisci un numero.");
            return;
        }

        // Creazione del bean dei filtri
        FiltersRestaurantBean filtro = new FiltersRestaurantBean(tipoDieta, pasto, distanza);

        // Creazione del controller e chiamata al metodo per trovare i ristoranti
        ChooseRestaurantController controller = new ChooseRestaurantController(filtro);
        List<ReturnRestaurantsBean> listaRistoranti;
        try {
            listaRistoranti = controller.trovaRistorante();
        } catch (IOException e) {
            System.err.println("Errore durante il recupero dei ristoranti: " + e.getMessage());
            return;
        }

        // Verifica se sono stati trovati ristoranti
        if (listaRistoranti == null || listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato con i filtri specificati.");
            return;
        }

        // Visualizzazione dei ristoranti trovati
        System.out.println("\nRistoranti trovati:");
        for (int i = 0; i < listaRistoranti.size(); i++) {
            ReturnRestaurantsBean ristorante = listaRistoranti.get(i);
            System.out.println((i + 1) + ". " + ristorante.getNome() + " - " + ristorante.getIndirizzo());
        }

        // Scelta opzionale per aprire il ristorante selezionato su Google Maps
        System.out.println("\nInserisci il numero del ristorante per aprirlo su Google Maps, oppure 0 per uscire:");
        int scelta = 0;
        try {
            scelta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida.");
            return;
        }

        if (scelta > 0 && scelta <= listaRistoranti.size()) {
            ReturnRestaurantsBean ristorante = listaRistoranti.get(scelta - 1);
            String url = "https://www.google.com/maps/search/?api=1&query="
                    + ristorante.getLatitudine() + "," + ristorante.getLongitudine();
            System.out.println("Apro Google Maps per: " + ristorante.getNome());
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (IOException | URISyntaxException e) {
                    System.err.println("Errore nell'apertura di Google Maps: " + e.getMessage());
                }
            } else {
                System.out.println("Desktop non supportato. Copia e incolla il seguente URL nel tuo browser:");
                System.out.println(url);
            }
        } else {
            System.out.println("Programma terminato.");

        }

        scanner.close();
    }
}
