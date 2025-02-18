package com.example.mealcalendar;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class FindRestaurantViewBoundaryCli {

    private final boolean vengoDaCalendar;

    // Costruttore con parametro per impostare vengoDaCalendar
    public FindRestaurantViewBoundaryCli(boolean vengoDaCalendar) {
        this.vengoDaCalendar = vengoDaCalendar;
    }

    // Costruttore di default (assume che non venga dal Calendario)
    public FindRestaurantViewBoundaryCli() {
        this.vengoDaCalendar = false;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Chiediamo il tipo di dieta
        System.out.println("Inserisci il tipo di dieta (Vegano, Vegetariano, Onnivoro): ");
        String tipoDieta = scanner.nextLine().trim();
        if (tipoDieta.equalsIgnoreCase("Onnivoro")) {
            tipoDieta = "";
        }

        // Chiediamo il tipo di pasto
        System.out.println("Inserisci il tipo di pasto (Colazione, Pranzo, Cena): ");
        String pasto = scanner.nextLine().trim();

        // Chiediamo la distanza massima
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

        // Creazione del controller e recupero della lista di ristoranti
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

        // Chiediamo all'utente di scegliere un ristorante
        System.out.println("\nInserisci il numero del ristorante per selezionarlo, oppure 0 per uscire:");
        int scelta = 0;
        try {
            scelta = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida.");
            return;
        }

        if (scelta > 0 && scelta <= listaRistoranti.size()) {
            ReturnRestaurantsBean ristoranteSelezionato = listaRistoranti.get(scelta - 1);

            // Se provieni dal calendario, salva il ristorante e torna al calendario
            if (vengoDaCalendar) {
                System.out.println("✅ Hai selezionato: " + ristoranteSelezionato.getNome());

                // Salva il ristorante selezionato nella variabile statica
                MealCalenderViewBoundaryCli.ristorantescelto = ristoranteSelezionato.getNome();

                System.out.println("\n🔄 Sto tornando al calendario con il ristorante selezionato...");

                // Usa il controller per navigare al calendario
                CliController cliController = new CliController();
                cliController.navigateTo("mealcalendar");
                return;
            }

            // Se NON provieni dal calendario, offriamo l'opzione di aprirlo su Google Maps
            String url = "https://www.google.com/maps/search/?api=1&query="
                    + ristoranteSelezionato.getLatitudine() + "," + ristoranteSelezionato.getLongitudine();
            System.out.println("Apro Google Maps per: " + ristoranteSelezionato.getNome());
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
            System.out.println("Nessun ristorante selezionato. Programma terminato.");
        }

        scanner.close();
    }
}
