package it.gaethanos.Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {
    private static final String CONFIG_FILE_NAME = "mcsai.properties";
    private Properties properties;

    public ConfigManager() {
        properties = new Properties();
        loadConfiguration();
    }

    // Metodo per recuperare un valore dal file di configurazione
    public String getConfig(String key) {
        return properties.getProperty(key);
    }

    // Metodo per impostare un valore nel file di configurazione
    public void setConfig(String key, String value) {
        properties.setProperty(key, value);
        saveConfiguration();
    }

    // Carica la configurazione, creandola se non esiste
    private void loadConfiguration() {
        File configFile = new File(CONFIG_FILE_NAME);

        if (!configFile.exists()) {
            createDefaultConfig();
        }

        try (InputStream input = new FileInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Salva la configurazione corrente sul file
    private void saveConfiguration() {
        try (OutputStream output = new FileOutputStream(CONFIG_FILE_NAME)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per creare il file di configurazione con i valori di default
    private void createDefaultConfig() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (inputStream == null) {
                return;
            }

            // Scrive il file di configurazione nella directory corrente
            Files.copy(inputStream, Paths.get(CONFIG_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
