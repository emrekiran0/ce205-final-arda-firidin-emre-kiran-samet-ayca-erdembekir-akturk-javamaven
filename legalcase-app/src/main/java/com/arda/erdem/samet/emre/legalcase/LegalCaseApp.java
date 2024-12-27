package com.arda.erdem.samet.emre.legalcase;

import java.io.IOException;
import java.util.Scanner;

/**
 * @class LegalCaseApp
 * @brief This class represents the main application class for the LegalCase App.
 * @details The LegalCaseApp class provides the entry point for the LegalCase App. 
 * It initializes the necessary components, performs calculations, and handles exceptions.
 * @author ugur
 */
public class LegalCaseApp {

    /**
     * @brief The main entry point of the LegalCase App.
     *
     * @details The main method is the starting point of the LegalCase App. It initializes the 
     * logger, performs logging, displays a greeting message, and handles user input.
     *
     * @param args The command-line arguments passed to the application.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // Hash tablosunu başlat
        LegalCase.initializeHashTable(LegalCase.hashTableProbing, LegalCase.TABLE_SIZE);

        // Scanner ve LegalCase nesnesi oluştur
        Scanner scanner = new Scanner(System.in);
        LegalCase legalCase = new LegalCase(scanner, System.out);
        

        // Kullanıcı giriş ekranını çalıştır
        boolean isLoggedIn = LegalCase.mainEntry();

        // Kullanıcı giriş yaptıysa ana menüyü çalıştır
        if (isLoggedIn) {
            LegalCase.mainMenu();
        } else {
            System.out.println("Exiting the application. Goodbye!");
        }
    }
}
