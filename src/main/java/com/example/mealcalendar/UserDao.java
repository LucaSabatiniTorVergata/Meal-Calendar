package com.example.mealcalendar;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import java.util.Properties;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UserDao implements UserDaoInterface {

    private static final Logger logger = Logger.getLogger(UserDao.class.getName());

    private static final String FILE_PATH = "users.txt";  // Path per il file di backup
    private boolean useDatabase;  // Usa DB?
    private boolean useDemo;      // Demo in-memory?

    // Proprietà DB
    private static String url;
    private static String dbuser;
    private static String password;

    // Chiave segreta (16 byte per AES-128)
    // In un contesto reale, andrebbe gestita su un keystore o un vault sicuro
    private static final String SECRET_KEY = "0123456789ABCDEF";

    // In-memory demo
    private static List<UserEntity> demoUsers = new ArrayList<>();

    // Caricamento configurazione DB
    static {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("src/main/resources/sqlData.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            dbuser = props.getProperty("db.dbuser");
            password = props.getProperty("db.password");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Error loading database properties", ex);
        }
    }

    public UserDao(boolean useDatabase, boolean useDemo) {
        this.useDatabase = useDatabase;
        this.useDemo = useDemo;

        // Se non usiamo DB e non siamo in DEMO, usiamo un file e lo creiamo se non esiste
        if (!useDatabase && !useDemo) {
            File file = new File(FILE_PATH);
            try {
                if (!file.exists()) {
                    boolean fileCreated = file.createNewFile();
                    if (fileCreated) {
                        logger.info("File creato con successo.");
                    } else {
                        logger.info("Il file esisteva già.");
                    }
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Errore nella creazione del file", e);
            }
        }
    }

    @Override
    public boolean registerUser(UserEntity user) throws IOException {
        try {
            if (useDatabase) {
                return registerUserDB(user);   // Usa il DB
            } else if (useDemo) {
                return registerUserDemo(user); // Demo in-memory
            } else {
                return registerUserFS(user);   // Salva su File System (con cifratura)
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nella registrazione utente", e);
            return false;
        }
    }

    // Demo in-memory
    private boolean registerUserDemo(UserEntity user) {
        demoUsers.add(user);
        return true;
    }

    // File System (con cifratura password)
    private boolean registerUserFS(UserEntity user) throws IOException {
        // Cifriamo la password prima di salvarla
        String encryptedPassword = EncryptionUtils.encrypt(user.getPassword(), SECRET_KEY);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            // Scriviamo: username:email:password_cifrata
            writer.write(user.getUsername() + ":" + user.getEmail() + ":" + encryptedPassword);
            writer.newLine();
        }
        return true;
    }

    // DB (eventualmente si potrebbe qui usare la cifratura o un hash)
    private boolean registerUserDB(UserEntity user) throws SQLException {
        // ***BEST PRACTICE***: sarebbe meglio salvare un hash (bcrypt, PBKDF2, etc.)
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<UserEntity> getAllUsers() throws IOException {
        try {
            if (useDemo) {
                return new ArrayList<>(demoUsers);
            } else if (useDatabase) {
                return getAllUsersDB();
            } else {
                return getAllUsersFS();  // da file system (password saranno decifrate in memoria)
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nel recupero di tutti gli utenti", e);
        }
        return new ArrayList<>();
    }

    private List<UserEntity> getAllUsersFS() throws IOException {
        List<UserEntity> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3) {
                    String username = userParts[0];
                    String email = userParts[1];
                    String encryptedPass = userParts[2];

                    // Decifriamo la password prima di creare l'oggetto
                    String decryptedPass = EncryptionUtils.decrypt(encryptedPass, SECRET_KEY);

                    users.add(new UserEntity(username, email, decryptedPass));
                }
            }
        }
        return users;
    }

    private List<UserEntity> getAllUsersDB() throws SQLException {
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT username, email, password FROM users";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new UserEntity(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password") // attualmente è in chiaro
                ));
            }
        }
        return users;
    }

    @Override
    public UserEntity getUserByUsername(String username) throws IOException {
        try {
            if (useDemo) {
                return getUserByUsernameDemo(username);
            } else if (useDatabase) {
                return getUserByUsernameDB(username);
            } else {
                return getUserByUsernameFS(username);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore nel recupero utente per username", e);
        }
        return null;
    }

    private UserEntity getUserByUsernameDemo(String username) {
        for (UserEntity user : demoUsers) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private UserEntity getUserByUsernameFS(String username) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userParts = line.split(":");
                if (userParts.length >= 3 && userParts[0].trim().equalsIgnoreCase(username.trim())) {
                    // Decifriamo la password prima di restituire l'oggetto
                    String decryptedPass = EncryptionUtils.decrypt(userParts[2], SECRET_KEY);
                    return new UserEntity(userParts[0], userParts[1], decryptedPass);
                }
            }
        }
        return null;
    }

    private UserEntity getUserByUsernameDB(String username) throws SQLException {
        String sql = "SELECT username, email, password FROM users WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(url, dbuser, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserEntity(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password") // attualmente non cifrata in DB
                    );
                }
            }
        }
        return null;
    }

    /**
     * Classe di utilità per crittografia AES.
     * Esempio semplice con AES/CBC/PKCS5Padding + IV random.
     * In un caso reale, si consiglia di custodire la chiave altrove (KeyStore, Vault, ecc.)
     */
    private static class EncryptionUtils {

        private static final String ALGORITHM = "AES";
        private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

        // Cifra la stringa in input (plaintext) e restituisce Base64( IV + testoCifrato )
        public static String encrypt(String plainText, String secretKey) {
            try {
                // Genera IV random
                SecureRandom secureRandom = new SecureRandom();
                byte[] iv = new byte[16];
                secureRandom.nextBytes(iv);

                // Imposta chiave e cipher
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
                byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

                // Concatena IV + dati cifrati in un unico array di byte
                ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + encrypted.length);
                byteBuffer.put(iv);
                byteBuffer.put(encrypted);

                // Codifica in Base64 per poterlo salvare come stringa
                return Base64.getEncoder().encodeToString(byteBuffer.array());

            } catch (Exception e) {
                // In produzione, gestire l'eccezione in modo più appropriato
                throw new RuntimeException("Errore in fase di crittografia", e);
            }
        }

        // Decifra la stringa in input (Base64(IV + testoCifrato)) e restituisce il plaintext
        public static String decrypt(String cipherText, String secretKey) {
            try {
                byte[] cipherData = Base64.getDecoder().decode(cipherText);

                // Estrae IV (primi 16 byte) e testo cifrato (resto)
                ByteBuffer byteBuffer = ByteBuffer.wrap(cipherData);
                byte[] iv = new byte[16];
                byteBuffer.get(iv);

                byte[] encryptedBytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(encryptedBytes);

                // Imposta chiave e cipher
                Cipher cipher = Cipher.getInstance(TRANSFORMATION);
                SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), ALGORITHM);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                byte[] decrypted = cipher.doFinal(encryptedBytes);

                return new String(decrypted, StandardCharsets.UTF_8);

            } catch (Exception e) {
                throw new RuntimeException("Errore in fase di decrittazione", e);
            }
        }
    }
}