package it.gaethanos.Console;

import it.gaethanos.Utils.ConfigManager;
import it.gaethanos.ProcessLauncher;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ItalianConsole {
    public ItalianConsole() throws IOException {
    }

    public void startItalian(Object interruptedException) throws InterruptedException, IOException {
        ConfigManager config = new ConfigManager();
        Scanner scanner = new Scanner(System.in);

        // INFO
        System.out.println("---------------------------------------------------");
        System.out.println("Minecraft Server Auto Installer (MCSAI) è uno strumento per");
        System.out.println("l'installazione semplificata e la configurazione in GUI");
        System.out.println("per il proprio Server di Minecraft!");
        System.out.println(" ");
        System.out.println("Utilizza le risposte indicate in console per selezionare");
        System.out.println("una risposta, in caso il processo verrà terminato.");
        System.out.println("---------------------------------------------------");
        System.out.println(" ");

        // VARIABILI PER SCANNER
        String software = "";
        double version = 1.21;
        String ram = "";
        String java = "";
        String port = "";
        String onlinemode = "";
        String render = "";
        String simDistance = "";
        String maxPlayers = "";
        String spawnProtection = "";
        String difficulty = "";
        String netherEnabled = "";
        String endEnabled = "";
        String worldType = "";


        System.out.println(" ");
        System.out.println("Quale dei seguenti Software prefirisci usare? (default Vanilla)");
        System.out.println("1. Spigot     2. Paper");
        System.out.println("3. Purpur     4. Vanilla");
        software = scanner.nextLine();

        System.out.println("Seleziona una versione tra queste (Verrà scaricata l'ultima sottoversione):");
        System.out.println("1.21\n1.20\n1.19\n1.18\n1.16\n1.12\n1.8");
        version = Double.parseDouble(scanner.nextLine());

        System.out.println("Definisci in MB la quantità di ram da assegnare:");
        ram = scanner.nextLine();

        System.out.println("Definisci il percorso di una versione di Java custom (Usa 'java' per default):");
        java = scanner.nextLine();

        System.out.println("Specifica la porta del Server:");
        port = scanner.nextLine();

        System.out.println("Il server sarà in online-mode? (true/false)");
        onlinemode = scanner.nextLine();

        System.out.println("Definisci in numeri la Distanza di resa:");
        render = scanner.nextLine();

        System.out.println("Definisci in numeri la Distanza di Simulazione:");
        simDistance = scanner.nextLine();

        System.out.println("Limite massimo di giocatori:");
        maxPlayers = scanner.nextLine();

        System.out.println("Definisci in numeri la distanza dello spawn protection:");
        spawnProtection = scanner.nextLine();

        System.out.println("Definisci la difficoltà di gioco (peaceful/easy/normal/hard):");
        difficulty = scanner.nextLine();

        System.out.println("Il nether è abilitato? (true/false):");
        netherEnabled = scanner.nextLine();

        System.out.println("L'end è abilitato? (true/false):");
        endEnabled = scanner.nextLine();

        System.out.println("Seleziona il tipo di mondo (flat/normal):");
        worldType = scanner.nextLine();

        // SETUP
        // Aggiorna il file di configurazione
        config.setConfig("software", software);
        config.setConfig("version", String.valueOf(version));
        config.setConfig("ram", ram);
        config.setConfig("java", java);

        // SLEEP
        System.out.println("Inizializzazione in corso della configurazione...");
        Thread.sleep(5000);

        // DOWNLOAD SOFTWARE
        downloadSoftware(config, software, version);

        // EULA
        System.out.println("Auto-accettazione EULA...");
        String eula = "eula=true";
        try {
            FileWriter eulaWriter = new FileWriter("eula.txt");
            eulaWriter.write(eula);
            eulaWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(3000);

        // CONFIG
        try {
            System.out.println("Configurazione del server in corso...");

            String configOnlineMode = "online-mode=" + onlinemode + "\n";
            String configPort = "server-port=" + port + "\n";
            String configDistance = "view-distance=" + render + "\n";
            String configSimDistance = "simulation-distance=" + simDistance + "\n";
            String configMaxPlayers = "max-players=" + maxPlayers + "\n";
            String configSpawnProtection = "spawn-protection=" + spawnProtection + "\n";
            String configDifficulty = "difficulty=" + difficulty + "\n";
            String configNetherEnabled = "allow-nether=" + netherEnabled + "\n";
            String configWorldType = "level-type=minecraft\\:" + worldType + "\n";
            String disableSecureProfile = "enforce-secure-profile=false";

            FileWriter serverConfig = new FileWriter("server.properties");
            serverConfig.write(configDistance);
            serverConfig.write(configPort);
            serverConfig.write(configOnlineMode);
            serverConfig.write(configSimDistance);
            serverConfig.write(configMaxPlayers);
            serverConfig.write(configSpawnProtection);
            serverConfig.write(configDifficulty);
            serverConfig.write(configNetherEnabled);
            serverConfig.write(configWorldType);
            System.out.println("---CHAT REPORT SYSTEM---");
            System.out.println("Disabilitando il profilo sicuro...");
            serverConfig.write(disableSecureProfile);
            serverConfig.close();
            config.setConfig("configured", "1");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String bukkitEndEnabled = "allow-end:" + " " + endEnabled;

            FileWriter bukkitConfig = new FileWriter("bukkit.yml");
            bukkitConfig.write(bukkitEndEnabled);
            bukkitConfig.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(5000);
        ProcessLauncher launcher = new ProcessLauncher();
        launcher.run();
    }


    private void downloadSoftware(ConfigManager config, String software, double version) {
        System.out.println("Download della versione: " + version + ".x " + "in corso...");
        // Costruisce la chiave per ottenere l'URL dal file di configurazione
        String versionKey = software.toLowerCase() + "-" + version;
        String downloadUrl = config.getConfig(versionKey);

        if (downloadUrl == null || downloadUrl.isEmpty()) {
            System.out.println("Errore: URL di download non trovato per la versione specificata.");
            return;
        }

        // Nome del file scaricato
        String fileName = software + "-" + version + ".jar";

        // Download del file
        try {
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                System.out.println("Download completato!");
            }
        } catch (IOException e) {
            System.out.println("Errore durante il download del software.");
            e.printStackTrace();
            return;
        }

        // Rinomina il file scaricato in server.jar
        File downloadedFile = new File(fileName);
        File renamedFile = new File("server.jar");

        if (downloadedFile.renameTo(renamedFile)) {
            System.out.println("Il file server.jar è stato salvato");
        } else {
            System.out.println("Errore nella rinomina del file.");
        }
    }
}

