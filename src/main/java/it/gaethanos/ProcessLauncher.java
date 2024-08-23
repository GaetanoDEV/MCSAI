package it.gaethanos;

import it.gaethanos.Utils.ConfigManager;
import it.gaethanos.Rcon.RconClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProcessLauncher {
    ConfigManager config = new ConfigManager();

    // Configurazione RCON
    String rconHost = "localhost";
    int rconPort = 25575;
    String rconPassword = "";

    public void run() {
        List<String> command = new ArrayList<>();
        command.add(config.getConfig("java"));
        command.add("-Xms" + config.getConfig("xms") + "M");
        command.add("-Xmx" + config.getConfig("ram") + "M");
        command.add("-Djline.terminal=jline.UnsupportedTerminal");
        command.add("-Dfile.encoding=UTF-8");
        command.add("-jar");
        command.add(config.getConfig("jar"));
        command.add("-nojline");
        command.add("--nogui");

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        String dir = System.getProperty("user.dir");
        processBuilder.directory(new File(dir));

        try {
            Process process = processBuilder.start();

            // Flag per verificare se il server è avviato
            final boolean[] serverReady = {false};

            // Thread per leggere l'output del server
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                        // Verifica se il server è pronto
                        if (line.contains("Done")) {
                            serverReady[0] = true;
                            synchronized (serverReady) {
                                serverReady.notifyAll();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Thread per leggere gli errori del server
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.err.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Connessione e invio comandi tramite RCON
            new Thread(() -> {
                // Attesa fino a quando il server non è pronto
                synchronized (serverReady) {
                    while (!serverReady[0]) {
                        try {
                            serverReady.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try (RconClient rconClient = RconClient.open(rconHost, rconPort, rconPassword);
                     Scanner scanner = new Scanner(System.in)) {

                    while (true) {
                        String commandmc = scanner.nextLine();
                        String response = rconClient.sendCommand(commandmc);

                        System.out.println(response);


                    }

                }
            }).start();

            // Attende il termine del processo
            int exitCode = process.waitFor();
            System.out.println("Il processo è terminato con codice di uscita: " + exitCode);
            System.exit(0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
