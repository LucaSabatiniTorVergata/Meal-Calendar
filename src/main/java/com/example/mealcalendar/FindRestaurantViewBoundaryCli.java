package com.example.mealcalendar;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

public class FindRestaurantViewBoundaryCli {

    private final boolean vengoDaCalendar;

    public FindRestaurantViewBoundaryCli(boolean vengoDaCalendar) {
        this.vengoDaCalendar = vengoDaCalendar;
    }

    public FindRestaurantViewBoundaryCli() {
        this.vengoDaCalendar = false;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);

        // Richiedi i dati di input e valida
        String tipoDieta = chiediTipoDieta(scanner);
        String pasto = chiediTipoPasto(scanner);
        double distanza = chiediDistanza(scanner);

        if (distanza == -1) {
            return;  // Se la distanza non è valida, termina
        }

        // Crea filtro e ottieni lista ristoranti
        FiltersRestaurantBean filtro = new FiltersRestaurantBean(tipoDieta, pasto, distanza);
        List<ReturnRestaurantsBean> listaRistoranti = trovaRistoranti(filtro);

        if (listaRistoranti.isEmpty()) {
            System.out.println("Nessun ristorante trovato con i filtri specificati.");
            return;
        }

        // Mostra i ristoranti e gestisci la scelta dell'utente
        ReturnRestaurantsBean ristoranteSelezionato = scegliRistorante(scanner, listaRistoranti);
        if (ristoranteSelezionato == null) {
            return;
        }

        // Gestisci la selezione
        gestisciSceltaRistorante(ristoranteSelezionato);
    }

    private String chiediTipoDieta(Scanner scanner) {
        System.out.println("Inserisci il tipo di dieta (Vegano, Vegetariano, Onnivoro): ");
        String tipoDieta = scanner.nextLine().trim();
        return tipoDieta.equalsIgnoreCase("Onnivoro") ? "" : tipoDieta;
    }

    private String chiediTipoPasto(Scanner scanner) {
        System.out.println("Inserisci il tipo di pasto (Colazione, Pranzo, Cena): ");
        return scanner.nextLine().trim();
    }

    private double chiediDistanza(Scanner scanner) {
        System.out.println("Inserisci la distanza massima (in km): ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Valore di distanza non valido. Inserisci un numero.");
            return -1;
        }
    }

    private List<ReturnRestaurantsBean> trovaRistoranti(FiltersRestaurantBean filtro) throws IOException {
        ChooseRestaurantController controller = new ChooseRestaurantController(filtro);
        return controller.trovaRistorante();
    }

    private ReturnRestaurantsBean scegliRistorante(Scanner scanner, List<ReturnRestaurantsBean> listaRistoranti) {
        System.out.println("\nRistoranti trovati:");
        for (int i = 0; i < listaRistoranti.size(); i++) {
            ReturnRestaurantsBean ristorante = listaRistoranti.get(i);
            System.out.println((i + 1) + ". " + ristorante.getNome() + " - " + ristorante.getIndirizzo());
        }

        System.out.println("\nInserisci il numero del ristorante per selezionarlo, oppure 0 per uscire:");
        int scelta = leggiScelta(scanner);

        if (scelta > 0 && scelta <= listaRistoranti.size()) {
            return listaRistoranti.get(scelta - 1);
        }
        System.out.println("Nessun ristorante selezionato.");
        return null;
    }

    private int leggiScelta(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Scelta non valida.");
            return 0;
        }
    }

    private void gestisciSceltaRistorante(ReturnRestaurantsBean ristoranteSelezionato) throws Exception {
        if (vengoDaCalendar) {
            System.out.println("✅ Hai selezionato: " + ristoranteSelezionato.getNome());
            MealCalenderViewBoundaryCli.ristorantescelto = ristoranteSelezionato.getNome();
            System.out.println("\n🔄 Sto tornando al calendario con il ristorante selezionato...");
            new CliController().navigateTo("calendariopasti");
        } else {
            apriGoogleMaps(ristoranteSelezionato);
        }
    }

    private void apriGoogleMaps(ReturnRestaurantsBean ristoranteSelezionato) {
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
    }
}
