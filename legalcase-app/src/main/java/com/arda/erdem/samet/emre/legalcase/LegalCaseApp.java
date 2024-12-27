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
     * The main entry point of the Legal Case Tracker application.
     * 
     * @details This method initializes the hash table for storing legal case data, 
     * sets up user input and output resources, and manages the application's flow by 
     * invoking the user authentication system and the main menu.
     *
     * @param args Command-line arguments passed to the application (not used in this implementation).
     * @throws IOException If an error occurs during input/output operations.
     *
     * @steps
     * - Initialize the hash table for case management.
     * - Create input and output resources using `Scanner` and `PrintStream`.
     * - Run the user authentication system using `LegalCase.mainEntry`.
     * - Upon successful login, display the main menu of the application.
     * - If login fails, exit the application with a farewell message.
     *
     * @see LegalCase#initializeHashTable(int[], int)
     * @see LegalCase#mainEntry()
     * @see LegalCase#mainMenu()
     */
    public static void main(String[] args) throws IOException {
        LegalCase.initializeHashTable(LegalCase.hashTableProbing, LegalCase.TABLE_SIZE);


        Scanner scanner = new Scanner(System.in);
        LegalCase legalCase = new LegalCase(scanner, System.out);
        

        
        boolean isLoggedIn = LegalCase.mainEntry();

        if (isLoggedIn) {
            LegalCase.mainMenu();
        } else {
            System.out.println("Exiting the application. Goodbye!");
        }
    }
}
