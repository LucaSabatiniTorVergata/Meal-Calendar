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
            } else {
                printer.print("\n🍽 Ristorante Selezionato:");
                printer.print("Ristorante: " + ristorantescelto);
                inviomail();
            }
        } catch (Exception e) {
            throw new MailSendingException("Errore durante la selezione della ricetta o del ristorante.", e);
        }
    }

    private static void readDate(Scanner scanner) {
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
    }

    private static void readTime(Scanner scanner) {
        while (oraselezionata == null) {
            System.out.print("Inserisci l'ora (HH:mm): ");
            String oraInput = scanner.nextLine();
            if (oraInput.matches("([01]?\\d|2[0-3]):[0-5]\\d")) {
                oraselezionata = oraInput;
            } else {
                System.out.println("Formato ora non valido. Riprova.");
            }
        }
    }

    private static void readLocation(Scanner scanner) {
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
    }

    private void confirmChoise() throws Exception {
        setVengoDaCalendar(true);
        if (sceltaLuogo) {
            navigateToRestaurant();
        } else {
            RecipeVewBoundaryCli ricercaRicette = new RecipeVewBoundaryCli(true);
            ricercaRicette.start();
        }
    }

    private void navigateToRestaurant() throws Exception {
        FindRestaurantViewBoundaryCli findRestaurantView = new FindRestaurantViewBoundaryCli(true);
        findRestaurantView.start();
        if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
            printer.print("Ristorante selezionato: " + ristorantescelto);
        } else {
            printer.print("Nessun ristorante selezionato.");
        }
    }

    public static void inviomail() throws MailSendingException {
        try {
            MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata,
                    SessionManagerSLT.getInstance().getLoggedInUsername(), sceltaLuogo ? ristorantescelto : ricettascelta);
            MealcalendarController controller = new MealcalendarController(bean);
            controller.invioMail();
        } catch (Exception e) {
            throw new MailSendingException("Errore durante l'invio della mail.", e);
        }
    }

    private static void setVengoDaCalendar(boolean value) {
        vengoDaCalendar = value;
    }
}
