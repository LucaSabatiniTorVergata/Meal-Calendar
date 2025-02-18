package com.example.mealcalendar;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloViewBoundaryCli {
    private final Scanner scanner = new Scanner(System.in);
    private final CliController cliController = new CliController();
    private static final Logger LOGGER = Logger.getLogger(HelloViewBoundaryCli.class.getName());

    // Rimuovi `throws Exception` e gestisci le eccezioni all'interno
    public void start() {
        try {
            System.out.println("\n===== Calendario dei Pasti (CLI) =====");
            System.out.println("1. Registrazione");
            System.out.println("2. Login (se già registrato)");
            System.out.println("3. Entra come Guest");
            System.out.println("4. Utilizza File System");
            System.out.println("5. Utilizza Database");
            System.out.println("6. Utilizza Demo (RAM)");
            System.out.println("0. Esci");
            System.out.print("Scegli un'opzione: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> register();
                case "2" -> cliController.navigateTo("login");
                case "3" -> cliController.navigateTo("guest");
                case "4" -> {
                    useFileSystem();
                    start(); // Torna al menu principale
                }
                case "5" -> {
                    useDatabase();
                    start(); // Torna al menu principale
                }
                case "6" -> {
                    useRam();
                    start(); // Torna al menu principale
                }
                case "0" -> System.out.println("👋 Uscita dal programma...");
                default -> {
                    System.out.println("❌ Scelta non valida.");
                    start();
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Errore nell'esecuzione dell'app.", e);
            System.out.println("❌ Si è verificato un errore. Riprova.");
        }
    }

    public void register() throws MealCalendarException {
        System.out.println("\n===== Registrazione =====");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Completa tutti i campi!");
            return;
        }

        UserDaoInterface userDao = UserDaoFactory.createUserDao();
        RegisterController controller = new RegisterController(userDao);

        UserBean userRegisterBean = new UserBean(username, email, password);
        try {
            boolean result = controller.register(userRegisterBean);  // Assicurati che questo ritorni un booleano

            if (result) {
                System.out.println("✅ Registrazione completata!");
                cliController.navigateTo("login");
            } else {
                System.out.println("❌ Username già esistente.");
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Errore durante la registrazione.", e);
            System.out.println("❌ Errore durante la registrazione.");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Errore imprevisto durante la registrazione.", e);
            System.out.println("❌ Errore imprevisto durante la registrazione.");
        } catch (Exception e) {
            throw new MealCalendarException("Errore durante la registrazione: " + e.getMessage(), e);
        }
    }

    // Metodo per utilizzare il File System
    private void useFileSystem() {
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(false);
        UserDaoFactory.setUseDatabase(false);
        UserDaoFactory.useDemo(false);
        System.out.println("🔹 Utilizzando il File System per i dati utenti.");
    }

    // Metodo per utilizzare il Database
    private void useDatabase() {
        SessionManagerSLT.getInstance().setFSDataBase(true);
        SessionManagerSLT.getInstance().setDemo(false);
        UserDaoFactory.setUseDatabase(true);
        UserDaoFactory.useDemo(false);
        System.out.println("🔹 Utilizzando il Database per i dati utenti.");
    }

    // Metodo per utilizzare la demo in RAM
    private void useRam() {
        SessionManagerSLT.getInstance().setFSDataBase(false);
        SessionManagerSLT.getInstance().setDemo(true);
        UserDaoFactory.setUseDatabase(false);
        UserDaoFactory.useDemo(true);
        System.out.println("🔹 Utilizzando la demo per i dati utenti.");
    }
}
