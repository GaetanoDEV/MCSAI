package it.gaethanos;

import it.gaethanos.Console.EnglishConsole;
import it.gaethanos.Console.ItalianConsole;
import it.gaethanos.Utils.ConfigManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // CONFIG
        ConfigManager config = new ConfigManager();

        // AVVIA IL SERVER SE IL CONFIG ESISTE
        File file = new File("mcsai.properties");
        ProcessLauncher launcher = new ProcessLauncher();


        String configured = config.getConfig("configured");
        if (Objects.equals(configured, "1")) {
            launcher.run();
        } else {
            // Se "configured" non è "1", procedi con le domande
            // DEFINISCE SCANNER
            Scanner scanner = new Scanner(System.in);

            // SPLASH LOGO
            System.out.println(" ");
            System.out.println("   _      ____  ____  _____ ____  _     _____ ____                  \n" +
                    "  / \\__/|/   _\\/ ___\\/  __//  __\\/ \\ |\\/  __//  __\\                 \n" +
                    "  | |\\/|||  /  |    \\|  \\  |  \\/|| | //|  \\  |  \\/|                 \n" +
                    "  | |  |||  \\__\\___ ||  /_ |    /| \\// |  /_ |    /                 \n" +
                    "  \\_/  \\|\\____/\\____/\\____\\\\_/\\_\\\\__/  \\____\\\\_/\\_\\                 \n" +
                    "                                                                    \n" +
                    " ____  _     _____  ____    _  _      ____  _____  ____  _     _    \n" +
                    "/  _ \\/ \\ /\\/__ __\\/  _ \\  / \\/ \\  /|/ ___\\/__ __\\/  _ \\/ \\   / \\   \n" +
                    "| / \\|| | ||  / \\  | / \\|  | || |\\ |||    \\  / \\  | / \\|| |   | |   \n" +
                    "| |-||| \\_/|  | |  | \\_/|  | || | \\||\\___ |  | |  | |-||| |_/\\| |_/\\\n" +
                    "\\_/ \\|\\____/  \\_/  \\____/  \\_/\\_/  \\|\\____/  \\_/  \\_/ \\|\\____/\\____/\n" +
                    "                                                                    ");
            System.out.println("                 v1.0 - by @Gaethanos__");
            System.out.println(" ");
            // FINE SPLASH LOGO

            // SELETTORE LINGUA
            System.out.println("> 1 - Italiano \n> 2 - English");
            String lingua = scanner.nextLine();

            // DEFINIZIONE CLASSI
            ItalianConsole it = new ItalianConsole();
            EnglishConsole en = new EnglishConsole();

            // CONTROLLI
            if (Objects.equals(lingua, "1")) {
                it.startItalian(config);
            } else if (Objects.equals(lingua, "2")) {
                en.startEnglish();
            } else {
                System.out.println("Scelta non valida.");
            }
        }
    }
}
