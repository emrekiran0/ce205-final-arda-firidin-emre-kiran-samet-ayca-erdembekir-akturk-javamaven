

/**
 * @brief The primary package for the Legal Case Management System.
 *
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, contains all the 
 * essential classes, data structures, and logic required for implementing 
 * the Legal Case Management System. It includes functionality for managing 
 * legal cases, scheduling, Huffman encoding, and other utility operations.
 */
package com.arda.erdem.samet.emre.legalcase;

import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the main application class for the Legal Case Tracker.
 * This class contains the main entry point of the application, handling initialization,
 * user authentication, and navigation to the main menu.
 *
 * @class LegalCaseApp
 *
 */
public class LegalCaseApp {

	/**
	 * The entry point of the Legal Case Tracker application.
	 * This method initializes the necessary data structures, handles user authentication,
	 * and provides access to the main menu upon successful login.
	 *
	 * @param args Command-line arguments (not used in this application).
	 * @throws IOException If an error occurs during input/output operations.
	 *
	 * @steps
	 * 1. Initialize the hash table for storing case information.
	 * 2. Create a `Scanner` instance for user input and a `LegalCase` instance for managing case operations.
	 * 3. Display the user authentication menu using `mainEntry`.
	 * 4. If the user logs in successfully, display the main application menu.
	 * 5. If the user exits without logging in, display a goodbye message and terminate the application.
	 *
	 * @see LegalCase#initializeHashTable(int[], int) For initializing the hash table.
	 * @see LegalCase#mainEntry() For user authentication.
	 * @see LegalCase#mainMenu() For accessing the main menu of the application.
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
