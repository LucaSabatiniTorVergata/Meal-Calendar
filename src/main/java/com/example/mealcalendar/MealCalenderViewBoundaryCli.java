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
    private RecipeReturnBean ricettaSelezionata;
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MealCalenderViewBoundaryCli");

    public MealCalenderViewBoundaryCli() {}

    public MealCalenderViewBoundaryCli(RecipeReturnBean ricettaSelezionata) {
        this.ricettaSelezionata = ricettaSelezionata;
    }

    // Rimosso costruttore che imposta direttamente ristorantescelto
    // Aggiunto metodo per impostare ristorantescelto
    public void setRistoranteSelezionato(String ristoranteSelezionato) {
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
        // Se né la ricetta né il ristorante sono stati selezionati, lanciamo l'eccezione personalizzata
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
                setRicettaSelezionata(ricettaSelezionata);
                inviomail();
            } else if (ristorantescelto != null && !ristorantescelto.isEmpty()) {
                printer.print("\n🍽 Ristorante Selezionato:");
                printer.print("Ristorante: " + ristorantescelto);
                setRistoranteSelezionato(ristorantescelto);
                inviomail();
            }
        } catch (Exception e) {
            throw new MailSendingException("Errore durante la selezione della ricetta o del ristorante.", e);
        }
    }

    private void readDate(Scanner scanner) {
        while (dataselezionata == null) {
            printer.print("Inserisci la data (dd/MM/yyyy): ");
            String dataInput = scanner.nextLine();
            try {
                LocalDate data = LocalDate.parse(dataInput, DATE_FORMATTER);
                if (data.isBefore(LocalDate.now())) {
                    printer.print("Non puoi selezionare una data passata. Riprova.");
                } else {
                    dataselezionata = data;
                }
            } catch (DateTimeParseException e) {
                printer.print("Formato data non valido. Riprova.");
            }
        }
    }

    private void readTime(Scanner scanner) {
        while (oraselezionata == null) {
            printer.print("Inserisci l'ora (HH:mm): ");
            String oraInput = scanner.nextLine();
            if (oraInput.matches("(\\d{2}):(\\d{2})") && oraInput.matches("([01]?\\d|2[0-3]):[0-5]\\d")) {
                oraselezionata = oraInput;
            } else {
                printer.print("Formato ora non valido. Riprova.");
            }
        }
    }

    private void readLocation(Scanner scanner) {
        while (true) {
            printer.print("Scegli il luogo (1. home, 2. restaurant): ");
            String luogoInput = scanner.nextLine();
            if (luogoInput.equals("1")) {
                sceltaLuogo = false;
                break;
            } else if (luogoInput.equals("2")) {
                sceltaLuogo = true;
                break;
            } else {
                printer.print("Scelta non valida. Riprova.");
            }
        }
    }

    private void confirmChoise() throws Exception {
        vengoDaCalendar = true;

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
            printer.print("Il ristorante selezionato non è nullo o vuoto.");
        } else {
            printer.print("Nessun ristorante selezionato.");
        }
    }

    public static void inviomail() throws MailSendingException {
        AntiCodeSmellPrinter localPrinter = new AntiCodeSmellPrinter("MealCalenderViewBoundaryCli");
        try {
            if (sceltaLuogo) {
                localPrinter.print("Scelta luogo = true. Inviamo la mail con il ristorante selezionato.");
                localPrinter.print("Ristorante scelto: " + ristorantescelto);
                MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata,
                        SessionManagerSLT.getInstance().getLoggedInUsername(), ristorantescelto);
                MealcalendarController controller = new MealcalendarController(bean);
                controller.invioMail();
            } else {
                localPrinter.print("Scelta luogo = false. Inviamo la mail con la ricetta selezionata.");
                MealcalendarBean bean = new MealcalendarBean(dataselezionata, oraselezionata,
                        SessionManagerSLT.getInstance().getLoggedInUsername(), ricettascelta);
                MealcalendarController controller = new MealcalendarController(bean);
                controller.invioMail();
            }
        } catch (Exception e) {
            throw new MailSendingException("Errore durante l'invio della mail.", e);
        }
    }

    public void setRicettaSelezionata(RecipeReturnBean ricetta) {
        this.ricettaSelezionata = ricetta;
        ricettascelta = ricetta.getRecipeName();
    }
}
