package com.example.mealcalendar;

import com.example.mealcalendar.login.RegisterController;
import com.example.mealcalendar.login.UserBean;
import com.example.mealcalendar.login.UserDaoFactory;
import com.example.mealcalendar.login.UserDaoInterface;
import com.example.mealcalendar.seteatingtime.MealCalendarException;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloViewControllerCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private static final Logger LOGGER = Logger.getLogger(HelloViewControllerCli.class.getName());
    private final AntiCodeSmellPrinter printer = new AntiCodeSmellPrinter("MealCalendarCLI");

    public void start() {
        try {
            printer.print("\n===== Calendario dei Pasti (CLI) =====");
            printer.print("1. Registrazione");
            printer.print("2. Login (se già registrato)");
            printer.print("3. Entra come Guest");
            printer.print("4. Utilizza File System");
            printer.print("5. Utilizza Database");
            printer.print("6. Utilizza Demo (RAM)");
            printer.print("0. Esci");
            printer.print("Scegli un'opzione: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> cliController.navigateTo("login");
                case "3" -> cliController.navigateTo("guest");
                case "4" -> {
                    useFileSystem();
                    start();
                }
                case "5" -> {
                    useDatabase();
                    start();
                }
                case "6" -> {
                    useRam();
                    start();
                }
                case "0" -> printer.print("👋 Uscita dal programma...");
                default -> {
                    printer.print("❌ Scelta non valida.");
                    start();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nell'esecuzione dell'app.", e);
            printer.print("❌ Si è verificato un errore. Riprova.");
        }
    }

    public void register() throws MealCalendarException {
        printer.print("\n===== Registrazione =====");
        printer.print("Username: ");
        String username = scanner.nextLine();
        printer.print("Email: ");
        String email = scanner.nextLine();
        printer.print("Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            printer.print("❌ Completa tutti i campi!");
            return;
        }

        UserDaoInterface userDao = UserDaoFactory.createUserDao();
        RegisterController controller = new RegisterController(userDao);

        UserBean userRegisterBean = new UserBean(username, email, password);
        try {
            boolean result = controller.register(userRegisterBean);

            if (result) {
                printer.print("✅ Registrazione completata!");
                cliController.navigateTo("login");
            } else {
                printer.print("❌ Username già esistente.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione.", e);
            printer.print("❌ Errore durante la registrazione.");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Errore imprevisto durante la registrazione.", e);
            printer.print("❌ Errore imprevisto durante la registrazione.");
        } catch (Exception e) {
            throw new MealCalendarException("Errore durante la registrazione: " + e.getMessage(), e);
        }
    }

    private void useFileSystem() {
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(false);
        UserDaoFactory.setUseDatabase(false);
        UserDaoFactory.useDemo(false);
        printer.print("🔹 Utilizzando il File System per i dati utenti.");
    }

    private void useDatabase() {
        SessionManagerSLT.getInstance().setFSDataBase(true);
        SessionManagerSLT.getInstance().setDemo(false);
        UserDaoFactory.setUseDatabase(true);
        UserDaoFactory.useDemo(false);
        printer.print("🔹 Utilizzando il Database per i dati utenti.");
    }

    private void useRam() {
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(true);
        UserDaoFactory.setUseDatabase(false);
        UserDaoFactory.useDemo(true);
        printer.print("🔹 Utilizzando la demo per i dati utenti.");
    }
}
