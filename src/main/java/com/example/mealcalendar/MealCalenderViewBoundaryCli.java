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
    private static LocalDate dataselezionata;
    private static String oraselezionata;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private RecipeReturnBean ricettaSelezionata;
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MealCalenderViewBoundaryCli");

    private static String ristorantescelto;
    private static String ricettascelta;

    public MealCalenderViewBoundaryCli() {}

    public MealCalenderViewBoundaryCli(RecipeReturnBean ricettaSelezionata) {
        this.ricettaSelezionata = ricettaSelezionata;
    }

    public static void setRistoranteSelezionato(String ristoranteSelezionato) {
        ristorantescelto = ristoranteSelezionato;
    }

    public static String getRistoranteSelezionato() {
        return ristorantescelto;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        printer.print("=== CALENDARIO PASTI ===");

        try {
            printSelectionAndSendMail();
        } catch (MailSendingException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la selezione della ricetta o del ristorante", e);
        }

        readDate(scanner);
        readTime(scanner);
        readLocation(scanner);

        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);

        try {
            confirmChoise();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la conferma della scelta", e);
        }
    }

    private void readLocation(Scanner scanner) {
    }

    private void readTime(Scanner scanner) {
    }

    private void readDate(Scanner scanner) {
    }

    private void printSelectionAndSendMail() throws MailSendingException, RecipeNotSelectedException {
        if (ricettaSelezionata == null && (ristorantescelto == null || ristorantescelto.isEmpty())) {
            throw new RecipeNotSelectedException("Nessuna ricetta o ristorante selezionato per il calendario.");
        }
        try {
            if (ricettaSelezionata != null) {
                printer.print("\n🍴 Ricetta Selezionata:");
                printer.print("Nome: " + ricettaSelezionata.getRecipeName());
                printer.print("Tipo Dieta: " + ricettaSelezionata.getTypeofDiet());
                printer.print("Tipo Pasto: " + ricettaSelezionata.getTypeofMeal());
                printer.print("Numero Ingredienti: " + ricettaSelezionata.getNumIngredients());
                printer.print("Ingredienti: " + ricettaSelezionata.getIngredients());
                printer.print("Descrizione: " + ricettaSelezionata.getDescription());
                printer.print("Autore: " + ricettaSelezionata.getAuthor());
                inviomail();
            } else if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
                printer.print("\n🍽 Ristorante Selezionato:");
                printer.print("Ristorante: " + ristorantescelto);
                inviomail();
            }
        } catch (Exception e) {
            throw new MailSendingException("Errore durante la selezione della ricetta o del ristorante.", e);
        }
    }

    private static void confirmChoise() throws Exception {
        vengoDaCalendar = true;

        if (sceltaLuogo) {
            navigateToRestaurant();
        } else {
            RecipeVewBoundaryCli ricercaRicette = new RecipeVewBoundaryCli(true);
            ricercaRicette.start();
        }
    }

    private static void navigateToRestaurant() throws Exception {
        FindRestaurantViewBoundaryCli findRestaurantView = new FindRestaurantViewBoundaryCli(true);
        findRestaurantView.start();

        if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
            System.out.println("Ristorante selezionato: " + ristorantescelto);
        } else {
            System.out.println("Nessun ristorante selezionato.");
        }
    }

    public static void inviomail() throws MailSendingException {
        AntiCodeSmellPrinter localPrinter = new AntiCodeSmellPrinter("MealCalenderViewBoundaryCli");
        try {
            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata,
                    SessionManagerSLT.getInstance().getLoggedInUsername(),
                    sceltaLuogo ? ristorantescelto : ricettascelta);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();
        } catch (Exception e) {
            throw new MailSendingException("Errore durante l'invio della mail.", e);
        }
    }
}