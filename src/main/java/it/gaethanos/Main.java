package it.gaethanos;

import it.gaethanos.Console.EnglishConsole;
import it.gaethanos.Console.ItalianConsole;
import it.gaethanos.Console.Utils.ConfigManager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        // CONFIG
        ConfigManager config = new ConfigManager();
        // DEFICISCE SCANNER
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
        // FINE SPLSH LOGO

        // LINGUA
        String lingua = "";


        // SELETTORE LINGUA
        System.out.println("> 1 - Italiano \n> 2 - English");
        lingua = scanner.nextLine();


        // DEFINIZIONE CLASSI
        ItalianConsole it = new ItalianConsole();
        EnglishConsole en = new EnglishConsole();

        // CONTROLLI
        if (Objects.equals(lingua, "1")) {
            Object InterruptedException = null;
            it.startItalian(InterruptedException);
        }
        if (Objects.equals(lingua, "2")) {
            en.startEnglish();
        }
    }
}