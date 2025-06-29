package com.example.mealcalendar.seteatingtime;

import com.example.mealcalendar.AntiCodeSmellPrinter;
import com.example.mealcalendar.MailSendingException;
import com.example.mealcalendar.SessionManagerSLT;
import com.example.mealcalendar.findrecipe.RecipeNotSelectedException;
import com.example.mealcalendar.findrecipe.RecipeReturnBean;
import com.example.mealcalendar.findrecipe.RecipeViewControllerCli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MealCalenderViewControllerCli {

    private static final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MealCalenderViewBoundaryCli");

    private static final Logger LOGGER = Logger.getLogger(MealCalenderViewControllerCli.class.getName());
    private static boolean sceltaLuogo = false;
    private static boolean vengoDaCalendar = false;
    private static LocalDate dataselezionata;
    private static String oraselezionata;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private RecipeReturnBean ricettaSelezionata;

    private static String ristorantescelto;
    private static String ricettascelta;

    public MealCalenderViewControllerCli() {}

    public MealCalenderViewControllerCli(RecipeReturnBean ricettaSelezionata) {
        this.ricettaSelezionata = ricettaSelezionata;
    }

    public static void setRistoranteSelezionato(String ristoranteSelezionato) {
        ristorantescelto = ristoranteSelezionato;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        printer.print("=== CALENDARIO PASTI(Se si vuole tornare alla schemata iniziale riavviare il programma) ===");

        readDate(scanner);
        readTime(scanner);
        readLocation(scanner);

        LOGGER.log(Level.INFO, "Scelta luogo: {0}", sceltaLuogo);

        try {


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore durante la conferma della scelta", e);
        }


    }

    public void printSelectionAndSendMail() throws MailSendingException, RecipeNotSelectedException {
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

    private static void readTime(Scanner scanner) {
        while (oraselezionata == null) {
            printer.print("Inserisci l'ora (HH:mm): ");
            String oraInput = scanner.nextLine();
            if (oraInput.matches("([01]?\\d|2[0-3]):[0-5]\\d")) {
                oraselezionata = oraInput;
            } else {
                printer.print("Formato ora non valido. Riprova.");
            }
        }
    }

    private static void readLocation(Scanner scanner) {
        String luogoInput;
        while (true) {
            printer.print("Scegli il luogo (1. home, 2. restaurant): ");
            luogoInput = scanner.nextLine();

            // Verifica dell'input
            if (luogoInput.equals("1")) {
                sceltaLuogo = false;
                return;  // Esci dal metodo direttamente
            } else if (luogoInput.equals("2")) {
                sceltaLuogo = true;
                return;  // Esci dal metodo direttamente
            } else {
                printer.print("Scelta non valida. Riprova.");
            }
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
