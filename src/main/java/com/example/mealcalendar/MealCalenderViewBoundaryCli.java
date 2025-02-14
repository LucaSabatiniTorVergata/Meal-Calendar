package com.example.mealcalendar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealCalenderViewBoundaryCli {

    private static final Logger LOGGER = Logger.getLogger(MealCalenderViewBoundaryCli.class.getName());

    private static boolean sceltaLuogo = false;
    private static boolean vengoDaCalendar = false;
    public static String ristorantescelto;
    public static String ricettascelta;

    private static LocalDate dataselezionata;
    private static String oraselezionata;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Aggiunto per salvare la ricetta selezionata
    private RecipeReturnBean ricettaSelezionata;

    // Nuovo costruttore per il ristorante selezionato
    public MealCalenderViewBoundaryCli() {
    }

    // Costruttore per passare la ricetta selezionata
    public MealCalenderViewBoundaryCli(RecipeReturnBean ricettaSelezionata) {
        this.ricettaSelezionata = ricettaSelezionata;
    }

    // Costruttore per passare il ristorante selezionato
    public MealCalenderViewBoundaryCli(String ristoranteSelezionato) {
        ristorantescelto = ristoranteSelezionato;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== CALENDARIO PASTI ===");

        // Se c'è una ricetta selezionata, la mostriamo
        if (ricettaSelezionata != null) {
            System.out.println("\n🍴 Ricetta Selezionata:");
            System.out.println("Nome: " + ricettaSelezionata.getRecipeName());
            System.out.println("Tipo Dieta: " + ricettaSelezionata.getTypeofDiet());
            System.out.println("Tipo Pasto: " + ricettaSelezionata.getTypeofMeal());
            System.out.println("Numero Ingredienti: " + ricettaSelezionata.getNumIngredients());
            System.out.println("Ingredienti: " + ricettaSelezionata.getIngredients());
            System.out.println("Descrizione: " + ricettaSelezionata.getDescription());
            System.out.println("Autore: " + ricettaSelezionata.getAuthor());
            setRicettaSelezionata(ricettaSelezionata);
            inviomail();
        } else if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
            // Se c'è un ristorante selezionato, lo mostriamo
            System.out.println("\n🍽 Ristorante Selezionato:");
            System.out.println("Ristorante: " + ristorantescelto);
            setRistoranteSelezionato(ristorantescelto);
            inviomail();
        }

        // Selezione data
        while (dataselezionata == null) {
            System.out.print("Inserisci la data (dd/MM/yyyy): ");
            String dataInput = scanner.nextLine();
            try {
                LocalDate data = LocalDate.parse(dataInput, DATE_FORMATTER);
                if (data.isBefore(LocalDate.now())) {
                    System.out.println("Non puoi selezionare una data passata. Riprova.");
                } else {
                    dataselezionata = data;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato data non valido. Riprova.");
            }
        }

        // Selezione ora
        while (oraselezionata == null) {
            System.out.print("Inserisci l'ora (HH:mm): ");
            String oraInput = scanner.nextLine();
            if (oraInput.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
                oraselezionata = oraInput;
            } else {
                System.out.println("Formato ora non valido. Riprova.");
            }
        }

        // Selezione luogo
        while (true) {
            System.out.print("Scegli il luogo (1. home, 2. restaurant): ");
            String luogoInput = scanner.nextLine();
            if (luogoInput.equals("1")) {
                sceltaLuogo = false;
                break;
            } else if (luogoInput.equals("2")) {
                sceltaLuogo = true;
                break;
            } else {
                System.out.println("Scelta non valida. Riprova.");
            }
        }

        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);

        try {
            confirmChoise();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la conferma della scelta", e);
        }
    }

    private void confirmChoise() throws Exception {
        vengoDaCalendar = true;

        CliController cliController = new CliController();

        if (sceltaLuogo) {
            // Vai a trovare il ristorante
            navigateToRestaurant();
        } else {
            // Vai a trovare la ricetta
            RecipeVewBoundaryCli ricercaRicette = new RecipeVewBoundaryCli(true);
            ricercaRicette.start();
        }
    }

    private void navigateToRestaurant() throws Exception {
        // Inizializza la vista per i ristoranti e comunica che provieni dal calendario
        FindRestaurantViewBoundaryCli findRestaurantView = new FindRestaurantViewBoundaryCli(true); // 'true' indica che provieni dal calendario
        findRestaurantView.start(); // Avvia la ricerca dei ristoranti

        // Stampa il ristorante selezionato (se esiste)
        if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
            System.out.println("Ristorante selezionato: " + ristorantescelto);

            // Verifica che siamo entrati nella parte giusta del codice
            System.out.println("Il ristorante selezionato non è nullo o vuoto.");

        } else {
            System.out.println("Nessun ristorante selezionato.");
        }
    }

    public static void inviomail() throws Exception {
        if (sceltaLuogo) {
            // Log per confermare che siamo nella parte giusta del codice
            System.out.println("Scelta luogo = true. Inviamo la mail con il ristorante selezionato.");

            // Log per confermare che il ristorante è selezionato correttamente
            System.out.println("Ristorante scelto: " + ristorantescelto);

            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata, SessionManagerSLT.getInstance().getLoggedInUsername(), ristorantescelto);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();
        } else {
            // Log per il caso delle ricette
            System.out.println("Scelta luogo = false. Inviamo la mail con la ricetta selezionata.");

            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata, SessionManagerSLT.getInstance().getLoggedInUsername(), ricettascelta);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();
        }
    }

    // Metodo per salvare la ricetta selezionata
    public void setRicettaSelezionata(RecipeReturnBean ricetta) {
        this.ricettaSelezionata = ricetta;
        ricettascelta = ricetta.getRecipeName(); // Salva il nome della ricetta
    }

    // Metodo per salvare il ristorante selezionato
    public void setRistoranteSelezionato(String ristorante) {
        ristorantescelto = ristorante;
    }
}
