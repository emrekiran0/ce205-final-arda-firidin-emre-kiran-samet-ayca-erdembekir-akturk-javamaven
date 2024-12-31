/**
 * @file LegalCase.java
 * @brief Main class for the Legal Case Management System.
 *
 * @details
 * This file contains the core implementation of the Legal Case Management System.
 * The system supports:
 * - Case Management: Adding, deleting, updating, and sorting legal cases.
 * - Client Tracking: Managing plaintiff and defendant information.
 * - Hearing Scheduler: Scheduling and managing hearing dates.
 * - Document Storage: Storing and retrieving legal documents.
 *
 * ### Key Features:
 * - **Data Structures**: Uses Hash Table, B+ Tree, Stack, Sparse Matrix, and Heap Sort.
 * - **Collision Resolution**: Implements various algorithms such as linear probing, quadratic probing, and double hashing.
 * - **Huffman Encoding**: Secures user credentials with Huffman coding for encryption.
 * - **File Handling**: Reads and writes binary files for persistence.
 *
 * ### Dependencies:
 * - Java I/O for file handling.
 * - Custom data structures for efficient operations.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note
 * - Ensure the necessary binary files (e.g., `cases.bin`, `documents.bin`, `user.huff`) are available before running the program.
 * - This project is designed for educational purposes and demonstrates advanced concepts in data structures and algorithms.
 *
 * @authors
 * - Arda, Erdem
 * - Samet, Emre
 *
 * @date 2024-12-29
 */


package com.arda.erdem.samet.emre.legalcase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.InputMismatchException;

/**
 * Represents a legal case with details such as case ID, title, involved parties, type, and dates.
 * This class is used as the core data structure for storing and managing legal case information.
 *
 * @implements Serializable Allows the `LegalCase` objects to be serialized and deserialized for file operations.
 *
 * @fields
 * - `caseID` (int): A unique identifier for the legal case.
 * - `title` (String): The title or name of the legal case.
 * - `plaintiff` (String): The name of the plaintiff involved in the case.
 * - `defendant` (String): The name of the defendant involved in the case.
 * - `type` (String): The type or category of the legal case (e.g., Criminal, Civil).
 * - `date` (String): The opening date of the legal case in `dd/MM/yyyy` format.
 * - `scheduled` (String): The scheduled hearing date of the legal case in `dd/MM/yyyy` format.
 *
 * @staticFields
 * - `scanner` (Scanner): A static `Scanner` object for user input across the application.
 * - `out` (PrintStream): A static `PrintStream` object for printing output across the application.
 * - `deletedCasesStack` (Stack<LegalCase>): A stack to manage deleted cases for undo operations.
 * - `USER_FILE` (String): The name of the file used for storing user data in a compressed format.
 * - `huffmanCodes` (Map<Character, String>): A map containing Huffman codes for secure password storage.
 * - `MAX_ATTEMPTS` (int): The maximum number of attempts allowed for certain operations.
 *
 * @note This class supports serialization for saving and retrieving legal case objects from files.
 */

public class LegalCase implements Serializable {
	
	/**
	 * @brief Global variables and constants for the `LegalCase` class.
	 *
	 * @details
	 * - `scanner`: A global `Scanner` instance for user input.
	 * - `out`: A `PrintStream` instance for output operations.
	 * - `serialVersionUID`: Ensures proper serialization compatibility.
	 * - `deletedCasesStack`: A stack for storing deleted `LegalCase` objects for potential restoration.
	 * - `USER_FILE`: The file name used to store user credentials in a compressed format.
	 * - `huffmanCodes`: A map containing Huffman-encoded character mappings.
	 * - `MAX_ATTEMPTS`: The maximum number of attempts allowed for certain operations.
	 *
	 * @fields
	 * - `caseID`: The unique identifier for each legal case.
	 * - `title`: The title or name of the legal case.
	 * - `plaintiff`: The name of the plaintiff involved in the case.
	 * - `defendant`: The name of the defendant involved in the case.
	 * - `type`: The type or category of the case.
	 * - `date`: The opening date of the case.
	 * - `scheduled`: The scheduled hearing date for the case.
	 */
    public static Scanner scanner;
    
    /**
     * @brief The output stream for displaying information to the user.
     * 
     * @details This stream is used to print messages or information
     * to the console or any designated output destination.
     */
    public static PrintStream out;
    /**
     * @brief A unique identifier for serialization.
     *
     * @details This field ensures compatibility during the deserialization process 
     * by verifying that the sender and receiver of a serialized object maintain 
     * the same class version. It prevents `InvalidClassException` when deserializing 
     * objects from older or newer versions of the class.
     *
     * @note The `serialVersionUID` is recommended for all classes implementing 
     * the `Serializable` interface to explicitly define the version.
     */
    private static final long serialVersionUID = 1L;
    static Stack<LegalCase> deletedCasesStack = new Stack<>();

    /**
     * @brief File name for storing user data.
     *
     * @details This constant represents the name of the file used to store 
     * user credentials securely. The file contains user data encoded with 
     * Huffman encoding for added security.
     *
     * @note Changing the file name requires updating all references to this constant.
     */
    public static  String USER_FILE = "user.huff";
    
    /**
     * @brief Stores Huffman codes for characters.
     *
     * @details This map holds the Huffman encoding for each character, used 
     * for secure encoding and decoding of user passwords. Keys represent 
     * characters, and values represent their corresponding Huffman codes.
     *
     * @note The map is initialized during the application's setup process 
     * and should not be modified directly.
     */
    private static final Map<Character, String> huffmanCodes = new HashMap<>();
    
    static {
        Map<Character, Integer> frequencyMap = initializeFrequencyMap();
        HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
        huffmanCodes.putAll(huffmanTree.getHuffmanCodes());
    }

    /**
     * @brief Defines the maximum number of attempts allowed.
     *
     * @details This constant specifies the upper limit on the number of 
     * attempts for certain operations, such as finding an available slot 
     * in a hash table or retrying a failed operation.
     *
     * @note Exceeding this limit may result in an error or alternative 
     * handling logic, depending on the context.
     */
    private static final int MAX_ATTEMPTS = 1000;
    
    /**
     * @brief The unique identifier for the legal case.
     */
    public int caseID;

    /**
     * @brief The title or subject of the legal case.
     */
    public String title;

    /**
     * @brief The name of the plaintiff involved in the case.
     */
    public String plaintiff;

    /**
     * @brief The name of the defendant involved in the case.
     */
    public String defendant;

    /**
     * @brief The type or category of the case (e.g., civil, criminal).
     */
    public String type;

    /**
     * @brief The date when the case was filed or started.
     */
    public String date;

    /**
     * @brief The scheduled hearing date for the case.
     */
    public String scheduled;



    // Constructor
	/**
	 * @brief Constructs a new `LegalCase` object.
	 *
	 * @details Initializes a `LegalCase` instance with the provided attributes:
	 * - `caseID`: A unique identifier for the legal case.
	 * - `title`: The title or name of the legal case.
	 * - `type`: The type or category of the case (e.g., civil, criminal).
	 * - `defendant`: The name of the defendant in the case.
	 * - `plaintiff`: The name of the plaintiff in the case.
	 * - `date`: The date when the case was opened.
	 * - `scheduled`: The scheduled hearing date for the case.
	 *
	 * @param caseID The unique identifier for the legal case.
	 * @param title The title of the legal case.
	 * @param type The type of the legal case.
	 * @param defendant The defendant involved in the case.
	 * @param plaintiff The plaintiff involved in the case.
	 * @param date The opening date of the case.
	 * @param scheduled The scheduled hearing date of the case.
	 */
    public LegalCase(int caseID,  String title,String type, String defendant, String plaintiff,  String date, String scheduled) {
    	this.caseID = caseID;
        this.title = title;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.type = type;
        this.date = date;
        this.scheduled = scheduled;
      
    }
	
    /**
     * @brief Constructs a new `LegalCase` object with input and output streams.
     *
     * @details This constructor initializes a `LegalCase` instance by associating it 
     * with the provided `Scanner` for user input and `PrintStream` for output. 
     * It allows seamless interaction with the user through these streams.
     *
     * @param scanner A `Scanner` object to handle user input.
     * @param out A `PrintStream` object to handle output display.
     */
    public LegalCase(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }

   

	static int MAX_YEARS = 10;
    static int MAX_MONTHS = 12;
    static int MAX_DAYS = 31;
    
    /**
     * @brief The file name used for storing case data.
     * 
     * @details This constant represents the name of the file where all 
     * legal case information is saved and retrieved.
     */
    public static  String FILE_NAME = "cases.bin";
    
    /**
     * @brief The file name used for storing document data.
     * 
     * @details This constant represents the name of the file where all 
     * legal case documents are saved and retrieved.
     */
    public static  String DOCUMENT_FILE_NAME = "documents.bin";

    /**
     * Clears the console screen for better readability.
     * This method works on both Windows and Unix-based operating systems by 
     * executing the appropriate system commands to clear the terminal or command prompt.
     * If an exception occurs during the process, it will be printed to the standard error.
     *
     * @note On Unix-based systems, this uses the "clear" command.
     *       On Windows systems, this uses the "cls" command.
     *
     * @throws Exception if the process fails to execute the clear command.
     */
    
    
    
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads an integer input from the user with input validation.
     * Ensures that the user enters a valid integer. If the input is invalid,
     * an error message is displayed, and the user is prompted to try again.
     *
     * @return A valid integer input provided by the user.
     *
     * @note The method uses `scanner` to read input and `out` to display messages.
     *       The method also clears any invalid data from the scanner buffer.
     */
    
    public static int getInput() {
        int choice;
        while (true) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Tamponu temizlemek için ekleyelim
                return choice;
            } else {
                out.println("Invalid choice! Please try again.");
                scanner.nextLine(); // Tampondaki tüm kalan veriyi temizle
            }
        }
    }


    /**
     * @brief Sparse matrix for storing scheduled hearing dates.
     *
     * @details
     * The `sparseMatrix` is a three-dimensional array used to track 
     * the availability of hearing dates. It is organized by year, month, and day.
     * Each entry in the matrix represents a specific day and contains an integer value:
     * - `0`: The date is available.
     * - `1`: The date is occupied.
     *
     * ### Structure:
     * - Dimensions:
     *   - First dimension: Years (e.g., `MAX_YEARS` specifies the range of years tracked).
     *   - Second dimension: Months (1-12, representing January to December).
     *   - Third dimension: Days (1-31, representing the days of each month).
     *
     * @note
     * - The `MAX_YEARS`, `MAX_MONTHS`, and `MAX_DAYS` constants determine 
     *   the maximum size of the matrix.
     * - The sparse matrix is initialized to all zeros, indicating that 
     *   all dates are initially available.
     * - Ensure that valid ranges are maintained for months (1-12) and days (1-31) 
     *   depending on the year and month.
     *
     * @see addCase() for date assignment logic.
     */
    private static int[][][] sparseMatrix = new int[MAX_YEARS][MAX_MONTHS][MAX_DAYS]; // Sparse matrix
    
    
    /**
     * Validates a given date based on predefined constraints.
     * Checks whether the provided day, month, and year form a valid date.
     * The method ensures that the day falls within 1-31, and the month falls within 1-12.
     *
     * @param day   The day of the date to validate (1-31).
     * @param month The month of the date to validate (1-12).
     * @param year  The year of the date to validate. The method assumes that the year is valid.
     *
     * @return `true` if the date is valid, `false` otherwise.
     *
     * @note This method does not account for leap years or month-specific day limits 
     *       (e.g., February having 28 or 29 days).
     */
    
    public static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > MAX_MONTHS) { // Ayın 1 ile 12 arasında olup olmadığını kontrol eder
            return false;
        }
        if (day < 1 || day > MAX_DAYS) { // Günün 1 ile 31 arasında olup olmadığını kontrol eder
            return false;
        }
        return true; // Tarih geçerliyse `true` döner
    }
   
    /**
     * Initializes the given sparse matrix to mark all dates as unoccupied.
     * This method sets every element of the provided 3D matrix to `0`, indicating
     * that all dates are available and not scheduled.
     *
     * @param sparseMatrix A 3D integer array representing the sparse matrix where
     *                     years, months, and days are indexed to manage dates.
     *
     * @note This method assumes the sparse matrix dimensions are predefined with
     *       constants `MAX_YEARS`, `MAX_MONTHS`, and `MAX_DAYS`.
     */
    
    
    public static void initializeSparseMatrix(int[][][] sparseMatrix) {
        for (int year = 0; year < MAX_YEARS; year++) {
            for (int month = 0; month < MAX_MONTHS; month++) {
                for (int day = 0; day < MAX_DAYS; day++) {
                    sparseMatrix[year][month][day] = 0; // Tüm tarihleri boş olarak işaretle
                }
            }
        }
    }
	
    /**
     * Finds the next available date in the sparse matrix and marks it as scheduled.
     * This method searches through the sparse matrix to locate the first unoccupied date
     * and assigns it to the `scheduledDate` array. The method also updates the matrix
     * to mark the date as occupied.
     *
     * @param sparseMatrix A 3D integer array representing the sparse matrix where
     *                     years, months, and days are indexed to manage dates.
     *                     A value of `0` indicates availability, while `1` indicates occupancy.
     * @param scheduledDate An integer array of size 3 used to store the scheduled date
     *                      as [day, month, year]. The found date will be stored in this array.
     *
     * @note The year in the scheduled date starts from 2024 and is incremented based
     *       on the sparse matrix indexing. The method uses the `isValidDate` function
     *       to validate each candidate date.
     * @note If no valid date is found, the array remains unchanged.
     */
    
    private static void findNextAvailableDate(int[][][] sparseMatrix, int[] scheduledDate) {
        for (int year = 0; year < sparseMatrix.length; year++) {
            for (int month = 0; month < sparseMatrix[year].length; month++) {
                for (int day = 0; day < sparseMatrix[year][month].length; day++) {
                    if (sparseMatrix[year][month][day] == 0 && isValidDate(day + 1, month + 1, 2024 + year)) {
                        sparseMatrix[year][month][day] = 1; // Tarihi işaretle
                        scheduledDate[0] = day + 1;        // Gün
                        scheduledDate[1] = month + 1;      // Ay
                        scheduledDate[2] = 2024 + year;    // Yıl
                        return;
                    }
                }
            }
        }
    }

    /**
     * Displays the main menu of the Legal Case Tracker application.
     * This method presents the user with several options, such as case tracking,
     * document creation, and document management. The user can navigate the menu
     * using numbered inputs. The menu loop continues until the user selects the exit option.
     *
     * @return `true` if the menu loop exits successfully.
     * @throws IOException If an error occurs during input reading (e.g., using `System.in.read()`).
     *
     * @menuOptions
     * - `1`: Navigate to the Case Tracking module.
     * - `2`: Create a new document using the Create Document module.
     * - `3`: View and manage existing documents using the Documents module.
     * - `4`: Exit the application.
     *
     * @note The method uses the `clearScreen` method to clear the console between displays
     *       for better readability.
     * @note If an invalid choice is entered, the user is prompted to retry.
     */
    
    public static boolean mainMenu() throws IOException {
        initializeHashTable(hashTableProbing, TABLE_SIZE); // Tabloyu sıfırla (opsiyonel)

        int choice;

        do {
            clearScreen(); // clearScreen fonksiyonunu daha önce yazdığımız gibi kullanıyoruz
            out.println("\n===== Legal Case Tracker Menu =====\n");
            out.println("1. Case Tracking");
            out.println("2. Create Document");
            out.println("3. Documents");
            out.println("4. Exit");
            out.print("\nEnter your choice: ");

            choice = getInput(); // Kullanıcıdan giriş alıyoruz

            switch (choice) {
                case 1:
                    caseTracking(); // Dava Takibi ile ilgili fonksiyon
                    break;
                case 2:
                 createDocument(); // Belge oluşturma fonksiyonu
                    break;
                case 3:
                  documents(); // Belgeler ile ilgili fonksiyon
                    break;
                case 4:
                    out.println("Exiting...");
                    break;
                default:
                    out.print("Invalid choice! Please press a key to continue: ");
                    System.in.read();
                    break;
            }
        } while (choice != 4);
        return true;
    }
   
    /**
     * Displays the Case Tracking menu and handles user interactions.
     * Allows the user to navigate through various case management options, including
     * adding, deleting, and viewing cases, as well as sorting and handling related cases.
     *
     * @return `true` if the menu loop exits successfully.
     *
     * @menuOptions
     * - `1`: Add a new case to the tracker.
     * - `2`: Delete an existing case.
     * - `3`: Restore an incorrectly deleted case.
     * - `4`: Display current active cases.
     * - `5`: View scheduled case dates.
     * - `6`: Display the list of plaintiffs.
     * - `7`: Explore cases that may be connected.
     * - `8`: Investigate cases that may arise.
     * - `9`: Sort cases by their ID.
     * - `10`: Exit the Case Tracking menu.
     *
     * @note If an invalid choice is entered, the user is prompted to retry.
     * @throws IOException If an error occurs while waiting for user input.
     */
    
    public static boolean caseTracking() {
        int choice;

        do {
            clearScreen(); // Ekranı temizlemek için clearScreen metodunu çağırıyoruz
            out.println("\n===== Case Tracking Menu =====");
            out.println("1. Add Case");
            out.println("2. Delete Case");
            out.println("3. Incorrect Deletion");
            out.println("4. Current Cases");
            out.println("5. Case Dates");
            out.println("6. Plaintiffs");
            out.println("7. Cases That May Be Connected");
            out.println("8. Cases That May Arise");
            out.println("9. Sort By ID");
            out.println("10. Exit");
            out.print("\nEnter your choice: ");

            choice = getInput(); // Kullanıcıdan seçim alıyoruz

            switch (choice) {
                case 1:
                 addCase();
                    break;
                case 2:
                   deleteCase();
                    break;
                case 3:
                  incorrectDeletionCase();
                    break;
                case 4:
                	currentCases();
                    break;
                case 5:
                    caseDates();
                    break;
                case 6:
                    displayPlaintiffs();
                    break;
                case 7:
                  casesThatMayBeConnectedMenu();
                    break;
                case 8:
                  casesThatMayAriseMenu();
                    break;
                case 9:
                    sortByID();
                    break;
                case 10:
                    out.println("Exiting...");
                    break;
                default:
                    out.print("Invalid choice! Please press a key to continue: ");
                    try {
                        System.in.read(); // Kullanıcıdan bir tuşa basmasını bekliyoruz
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } while (choice != 10);

        return true;
    }
   
    /**
     * Calculates the hash value for a given case ID.
     * This method uses a simple modulo operation to compute the index for the case ID
     * in the hash table.
     *
     * @param caseID The unique identifier of the case.
     * @param TABLE_SIZE The size of the hash table.
     * @return The computed hash value, which is the index in the hash table.
     */
    
    public static int hashFunction(int caseID, int TABLE_SIZE) {
        return caseID % TABLE_SIZE;
    }
    
    
    /**
     * @brief Size of the hash table used for storing case IDs.
     *
     * @details
     * The `TABLE_SIZE` constant defines the fixed size of the hash table used 
     * in the application. It is primarily used to determine the total number of 
     * available slots in the hash table for storing and retrieving case IDs efficiently.
     *
     * ### Characteristics:
     * - **Fixed Size**: The table size is set to `10000`.
     * - **Purpose**: Ensures that the hash table has enough capacity to minimize collisions 
     *   and maintain efficient lookup, insertion, and deletion operations.
     *
     * @note
     * - A larger table size reduces the likelihood of collisions but increases memory usage.
     * - The value of `TABLE_SIZE` should ideally be a prime number for better hash table distribution,
     *   though in this case, it is not.
     */
    public static int TABLE_SIZE = 10000; 
    
    
    /**
     * Initializes the hash table by marking all slots as empty.
     * This method sets each slot in the hash table array to `-1`, which indicates that
     * the slot is unoccupied and ready for new entries.
     *
     * @param hashTableProbing The hash table array to initialize.
     * @param TABLE_SIZE The total number of slots in the hash table.
     *
     * @note This method is typically called once during application startup.
     */
    public static void initializeHashTable(int[] hashTableProbing, int TABLE_SIZE) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            hashTableProbing[i] = -1;  // -1 indicates that the slot is empty
        } }
    
    /**
     * Inserts a case ID into the hash table using quadratic probing for collision resolution.
     * Quadratic probing resolves collisions by checking slots at progressively larger intervals.
     *
     * @param caseID The unique identifier of the case to insert.
     * @return `true` if the case ID is successfully inserted, `false` if the table is full.
     *
     * @note If the table is full, an error message is displayed, and insertion is not performed.
     */
    public static boolean quadraticProbing(int caseID) {
        int index = hashFunction(caseID, TABLE_SIZE);
        int i = 0;

        while (i < TABLE_SIZE) {
            int newIndex = (index + i * i) % TABLE_SIZE;

            if (hashTableProbing[newIndex] == -1) { // Boş yer bulundu
                hashTableProbing[newIndex] = caseID;
                out.printf("Case ID: %d inserted at Index: %d%n", caseID, newIndex);
                   
                return true;
            }

            i++; // Bir sonraki deneme
        }

        
        return false;
        
    }

    
    /**
     * Inserts a case ID into the hash table using progressive overflow for collision resolution.
     * This method uses linear probing by incrementing the index one step at a time until an empty slot is found.
     *
     * @param caseID The unique identifier of the case to insert.
     * @return `true` if the case ID is successfully inserted, `false` if the table is full.
     *
     * @note Progressive overflow may cause clustering, which can affect performance.
     */
    public static boolean progressiveOverflow(int caseID) {
        int index = hashFunction(caseID);
        int i = 0;

        while (i < TABLE_SIZE) {
            int newIndex = (index + i) % TABLE_SIZE;

            if (hashTableProbing[newIndex] == -1) { // Boş yer bulundu
                hashTableProbing[newIndex] = caseID;
                out.printf("Case ID: %d inserted at Index: %d (progressive overflow)%n", caseID, newIndex);
                return true;
            }

            i++; // Bir sonraki deneme
        }

        
        return false;
    }

    /**
     * Computes the hash value for a given case ID using a simple modulo operation.
     * The result determines the initial index in the hash table for the case ID.
     *
     * @param caseID The unique identifier of the case to be hashed.
     * @return The computed hash value, which represents the index in the hash table.
     *
     * @note This function assumes that the hash table size (`TABLE_SIZE`) is a prime number
     *       or appropriately chosen to reduce collisions.
     */
    private static int hashFunction(int caseID) {
        return caseID % TABLE_SIZE;
    }
   
    /**
     * A static integer array representing the hash table for case IDs.
     * Each index in the array corresponds to a slot in the hash table, where:
     * - `-1` indicates the slot is empty.
     * - Any positive integer represents an occupied slot with a stored case ID.
     *
     * @note The size of the table is determined by the constant `TABLE_SIZE`.
     */
     public static int[] hashTableProbing = new int[TABLE_SIZE]; // Hash table için dizi
		
     
     /**
      * Computes the index for a given case ID using double hashing.
      * Double hashing combines two hash functions to minimize clustering and improve collision resolution.
      *
      * @param caseID The unique identifier of the case to insert.
      * @param attempt The current attempt number, used to calculate the step size.
      * @return The computed index in the hash table.
      *
      * @note The secondary hash function ensures a different step size for each case ID,
      *       reducing the risk of collisions during multiple attempts.
      * @see secondHashFunction(int caseID) For the implementation of the secondary hash function.
      */
     public static int doubleHashing(int caseID, int attempt) {
         int primaryHash = hashFunction(caseID);       // Birinci hash fonksiyonu
         int secondaryHash = secondHashFunction(caseID); // İkinci hash fonksiyonu
         return (primaryHash + attempt * secondaryHash) % TABLE_SIZE; // Double hashing
     }
     
     /**
      * Resolves collisions in the hash table using linear probing.
      * Linear probing searches for the next available slot by incrementing the index sequentially.
      *
      * @param caseID The unique identifier of the case to insert.
      * @return `true` if the case ID is successfully inserted.
      *
      * @note Linear probing may lead to clustering, where consecutive slots are occupied, reducing efficiency.
      * @see doubleHashing(int caseID, int attempt) For an alternative collision resolution method.
      */
     public static boolean linearProbing(int caseID) {
    	    int index = hashFunction(caseID); // Başlangıç indeksi
    	    int i = 0;

    	    // Case ID'yi uygun yere yerleştir
    	    hashTableProbing[(index + i) % TABLE_SIZE] = caseID;

    	    // Debug çıktısı
    	    out.printf("Case ID: %d ----- Inserted at Index: %d (linear probing)%n", caseID, (index + i) % TABLE_SIZE);

    	    return true;
    	}
   
     /**
      * Computes the secondary hash value for double hashing.
      * The secondary hash function ensures a non-zero step size for resolving collisions.
      *
      * @param caseID The unique identifier of the case to be hashed.
      * @return The computed secondary hash value, which determines the step size.
      *
      * @note The step size is calculated using the formula `7 - (caseID % 7)`, where `7` is a prime number.
      *       This ensures the step size is relatively prime to the hash table size (`TABLE_SIZE`).
      */
     public static int secondHashFunction(int caseID) {
         return 7 - (caseID % 7); // Double hashing için ikinci hash fonksiyonu
     }
     
     /**
      * @brief Inserts a case ID into the hash table using double hashing for collision resolution.
      *
      * @details
      * Double hashing ensures efficient collision handling by combining two hash functions.
      * The primary hash determines the initial index, and the secondary hash provides a step size
      * for resolving collisions. The function iterates until an empty slot is found or the table is full.
      *
      * @param caseID The unique ID of the case to be inserted into the hash table.
      * 
      * @return 
      * - **`true`**: If the case ID is successfully inserted into the table.
      * - **`false`**: If the hash table is full and no empty slot is available.
      *
      * @note
      * - Ensure the hash table size is prime to maximize the effectiveness of double hashing.
      * - The `secondHashFunction` must always return a non-zero value to avoid infinite loops.
      */
     public static boolean doubleHashingInsert(int caseID) {
         int index = hashFunction(caseID); // İlk hash fonksiyonu ile başlangıç indeksi
         int stepSize = secondHashFunction(caseID); // İkinci hash fonksiyonu ile adım boyutu
         int i = 0;

         // Boş bir yer bulana kadar ilerle
         while (hashTableProbing[(index + i * stepSize) % TABLE_SIZE] != -1) {
             i++; // Bir sonraki adımı dene
             if (i >= TABLE_SIZE) {
                 
                 return false; // Tablo dolu
             }
        
      // Boş bir yere yerleştir
         hashTableProbing[(index + i * stepSize) % TABLE_SIZE] = caseID;

         // Debug çıktısı
         out.printf("Case ID: %d ----- Inserted at Index: %d (double hashing)%n", caseID, (index + i * stepSize) % TABLE_SIZE);

         return true;
     }

         // Boş bir yere yerleştir
         hashTableProbing[(index + i * stepSize) % TABLE_SIZE] = caseID;
         

         // Debug çıktısı
         out.printf("Case ID: %d ----- Inserted at Index: %d (double hashing)%n", caseID, (index + i * stepSize) % TABLE_SIZE);

         return true;
     }
    
     /**
      * Checks whether the hash table is completely occupied.
      * Iterates through all slots in the hash table to determine if there is any empty space.
      *
      * @return `true` if all slots are occupied, `false` if there is at least one empty slot.
      */
     public static boolean isHashTableFull() {
         for (int i = 0; i < TABLE_SIZE; i++) {
             if (hashTableProbing[i] == -1) {
                 return false; // Tablo henüz dolu değil
             }
         }
         return true; // Tablodaki tüm slotlar dolu
     
     }
     
     /**
      * Inserts a new case into the hash table using linear probing for collision resolution.
      * Searches for the first available slot starting from the computed hash index.
      *
      * @param newCase The `LegalCase` object to insert into the hash table.
      *
      * @note This method updates the hash table to associate the given case ID with an available slot.
      */
     public static void insertIntoHashTable(LegalCase newCase) {
    	    int index = hashFunction(newCase.caseID); // Hash fonksiyonu ile index hesaplanır

    	    // Eğer hash tablosunda bu index'e denk gelen bir değer varsa çakışmayı kontrol et
    	    while (hashTableProbing[index] != -1) {
    	        index = (index + 1) % TABLE_SIZE; // Linear probing ile sonraki indeksi kontrol et
    	    }

    	    // Uygun boş index bulundu, yeni davayı tabloya ekle
    	    hashTableProbing[index] = newCase.caseID;

    	    
    	}

     /**
      * A custom implementation of `ObjectOutputStream` that allows appending to existing files.
      * This class overrides the stream header writing mechanism to ensure compatibility with
      * previously written objects in the same file.
      *
      * @note This class is useful for managing serialized objects in files without overwriting
      *       the existing data.
      */
     public static class AppendableObjectOutputStream extends ObjectOutputStream {
    	 /**
    	     * Constructs a new `AppendableObjectOutputStream` with the specified output stream.
    	     *
    	     * @param out The output stream to which objects are written.
    	     * @throws IOException If an I/O error occurs while creating the stream.
    	     */
    	 
    	 
    	    public AppendableObjectOutputStream(OutputStream out) throws IOException {
    	        super(out);
    	    }
    	    
    	    /**
    	     * Overrides the default stream header writing behavior to support appending.
    	     *
    	     * @throws IOException If an I/O error occurs while resetting the stream.
    	     */
    	    
    	    
    	    @Override
    	    protected void writeStreamHeader() throws IOException {
    	        // Header'ı yazmaz, böylece dosyaya ekleme yapılabilir
    	        reset();
    	    }
    	}
     
     /**
      * Appends a `LegalCase` object to a binary file.
      * This method writes the provided `LegalCase` object to the specified file in binary format.
      * If the file already exists, it prevents the header from being rewritten to avoid data corruption.
      * Otherwise, it writes the full header for a new file.
      *
      * @param legalCase The `LegalCase` object to append to the file.
      * @param fileName  The name of the file to which the case will be appended.
      *
      * @note The method uses the `AppendableObjectOutputStream` class to handle
      *       appending without overwriting the file header when the file exists.
      *
      * @throws IOException If an I/O error occurs during the file writing process.
      *
      */
     public static void appendCaseFile(LegalCase legalCase, String fileName) {
         try {
             // Dosya var mı kontrol et
             boolean fileExists = new File(fileName).exists();

             try (FileOutputStream fos = new FileOutputStream(fileName, true);
                  ObjectOutputStream oos = fileExists
                          ? new AppendableObjectOutputStream(fos) // Header eklenmesini önler
                          : new ObjectOutputStream(fos)) {       // İlk yazma işlemi için

                 // Yeni dava bilgilerini binary formatta dosyaya yaz
                 oos.writeObject(legalCase);
             }

         } catch (IOException e) {
             
         }
     }

     /**
      * Validates the format of a given date string.
      * Checks whether the input date matches the "dd/mm/yyyy" format using a regular expression.
      *
      * @param date The date string to validate.
      * @return `true` if the date matches the "dd/mm/yyyy" format, `false` otherwise.
      *
      *
      * @note This method does not verify the logical validity of the date
      *       (e.g., "31/02/2024" would pass this check but is not a valid calendar date).
      */
     public static boolean isValidDateFormat(String date) {
    	    return date.matches("\\d{2}/\\d{2}/\\d{4}"); // Tarihin "dd/mm/yyyy" formatında olup olmadığını kontrol eder
    	}
  
     /**
      * Adds a new legal case to the system.
      * This method handles the input of case details, assigns a unique case ID using a selected collision resolution strategy,
      * validates the input data (including dates), and schedules the hearing date automatically.
      * The case is saved to a binary file and inserted into the hash table for efficient retrieval.
      *
      * @return `true` if the case is successfully added, `false` otherwise.
      *
      * @menuOptions
      * - `1`: Quadratic Probing for collision resolution.
      * - `2`: Progressive Overflow for collision resolution.
      * - `3`: Linear Probing for collision resolution.
      * - `4`: Double Hashing for collision resolution.
      *
      * @steps
      * 1. Select a collision resolution strategy.
      * 2. Generate a unique case ID.
      * 3. Input case details, including title, plaintiff, defendant, type, and opening date.
      * 4. Validate the date format and logical correctness.
      * 5. Schedule the hearing date using the sparse matrix.
      * 6. Save the case details to a file.
      * 7. Insert the case into the hash table.
      *
      * @throws IOException If an error occurs while interacting with the file or input/output streams.
      *
      * @note The method ensures user input is validated at every step and provides retry prompts for invalid entries.
      */
     
     public static boolean addCase() {
    	    clearScreen();
    	    initializeHashTable(hashTableProbing, TABLE_SIZE); // Tabloyu sıfırla (opsiyonel)


    	    Random rand = new Random();
    	    int caseID;
    	    int attempt = 0;
    	    boolean inserted = false;

    	    int choice;
    	    do {
    	        clearScreen();
    	        out.println("----- Select Collision Resolution Strategy -----");
    	        out.println("1- Quadratic Probing");
    	        out.println("2- Progressive Overflow");
    	        out.println("3- Linear Probing");
    	        out.println("4- Double Hashing");
    	        out.print("\nEnter your choice: ");
    	        choice = getInput();

    	        // Generate random Case ID
    	        caseID = rand.nextInt(900000) + 100000;

    	        switch (choice) {
    	            case 1:
    	                inserted = quadraticProbing(caseID);
    	                break;
    	            case 2:
    	                inserted = progressiveOverflow(caseID);
    	                break;
    	            case 3:
    	                inserted = linearProbing(caseID);
    	                break;
    	            case 4:
    	                inserted = doubleHashingInsert(caseID);
    	                break;
    	        }
    	        attempt++;
    	    } while (!inserted && attempt < LegalCase.MAX_ATTEMPTS);

    	    if (!inserted) {
    	        
    	        return false;
    	    }

    	    scanner.nextLine(); // Clear buffer
    	    out.print("Enter Case Title: ");
    	    String caseTitle = scanner.nextLine();

    	    out.print("Plaintiff: ");
    	    String plaintiff = scanner.nextLine();

    	    out.print("Defendant: ");
    	    String defendant = scanner.nextLine();

    	    out.println("\nCase Types:");
    	    out.println("- Criminal         - Civil         - Commercial        - Administrative");
    	    out.println("- Divorce          - Custody       - Traffic           - Dismissal");
    	    out.println("- Compensation     - Inheritance   - Title deed");
    	    out.print("\nEnter Case Type: ");
    	    String caseType = scanner.nextLine();

    	    String date;
    	    while (true) {
    	        out.print("Date of Opening of the Case (dd/mm/yyyy): ");
    	        date = scanner.nextLine();

    	        // Tarihin formatını kontrol et
    	        if (isValidDateFormat(date)) {
    	            String[] parts = date.split("/");
    	            if (parts.length == 3) {
    	                try {
    	                    int day = Integer.parseInt(parts[0]);
    	                    int month = Integer.parseInt(parts[1]);
    	                    int year = Integer.parseInt(parts[2]);
    	                    if (isValidDate(day, month, year)) {
    	                        break; // Geçerli bir tarih bulundu
    	                    } else {
    	                        out.println("Invalid date! Please check the day, month, and year values.");
    	     }
    	                } catch (NumberFormatException e) {
    	                    
    	                    
    	                }
    	            } 
    	        } else {
    	            out.println("Invalid date format! Please enter the date in dd/mm/yyyy format.");
    	        }
    	    }


    	    int[] scheduledDate = new int[3];
    	    findNextAvailableDate(sparseMatrix, scheduledDate);

    	    if (scheduledDate[0] == 0 && scheduledDate[1] == 0 && scheduledDate[2] == 0) {
    	       
    	        return false;
    	    }

    	    String scheduled = String.format("%02d/%02d/%d", scheduledDate[0], scheduledDate[1], scheduledDate[2]);

    	    LegalCase newCase = new LegalCase(caseID, caseTitle,caseType, defendant,plaintiff,   date, scheduled);
    	    

    	    // Dosyaya yazma işlemini gerçekleştiren fonksiyonu çağırıyoruz
    	    appendCaseFile(newCase, FILE_NAME);

    	    // Insert into hash table
    	    insertIntoHashTable(newCase);

    	    out.println("\nScheduled Hearing Date: " + scheduled);
    	    out.print("Please press Enter to return to the Case Tracking Menu...");
    	    scanner.nextLine();

    	    return true;
    	}

     /**
      * Prints the details of a legal case.
      * This method retrieves case details from a `CaseNode` object and displays them in a formatted manner.
      *
      * @param node The `CaseNode` object containing the legal case data to print.
      * @return `true` after successfully printing the case details.
      *
      *
      * @note The printed output includes the case ID, title, plaintiff, defendant, type, opening date, and scheduled hearing date.
      */
     public static boolean printCase(CaseNode node) {
    	    out.println("\nCase ID: " + node.data.caseID);
    	    out.println("Case Title: " + node.data.title);
    	    out.println("Plaintiff: " + node.data.plaintiff);
    	    out.println("Defendant: " + node.data.defendant);
    	    out.println("Case Type: " + node.data.type);
    	    out.println("Beginning Date: " + node.data.date);
    	    out.println("Scheduled Hearing Date: " + node.data.scheduled);
    	    out.println("-----------------------------");
    	    return true;
    	}
     
     /**
      * Appends a new node to the end of a doubly linked list of legal cases.
      * This method creates a new `CaseNode` containing the provided `LegalCase` object and adds it to the end of the list.
      *
      * @param head The head of the doubly linked list.
      * @param data The `LegalCase` object to store in the new node.
      * @return The head of the updated linked list.
      *
      *
      * @note If the list is empty (`head == null`), the new node becomes the head of the list.
      * @note This method maintains the doubly linked structure by updating the `prev` and `next` pointers accordingly.
      */
     public static CaseNode appendNode(CaseNode head, LegalCase data) {
    	    CaseNode newNode = new CaseNode(data);

    	    if (head == null) {
    	        return newNode; // Eğer liste boşsa, yeni düğüm baş olur
    	    } else {
    	        CaseNode temp = head;
    	        while (temp.next != null) {
    	            temp = temp.next; // Listenin sonuna git
    	        }
    	        temp.next = newNode; // Yeni düğümü sona ekle
    	        newNode.prev = temp; // Önceki düğümü ayarla
    	        return head; // Baş düğümü geri döndür
    	    }}
     
     /**
      * Displays a list of current legal cases stored in a binary file.
      * This method reads all cases from the specified file, adds them to a doubly linked list, and allows the user to navigate through the cases.
      *
      * @return `true` if the cases are successfully loaded and displayed, `false` if the file does not exist or an error occurs.
      *
      * @steps
      * 1. Check if the file exists. If not, display an error and exit.
      * 2. Read all cases from the binary file and store them in a doubly linked list.
      * 3. Allow the user to navigate the cases using the following options:
      *    - `P` or `p`: Go to the previous case.
      *    - `N` or `n`: Go to the next case.
      *    - `Q` or `q`: Quit the navigation.
      * 4. Handle invalid inputs gracefully by prompting the user to retry.
      *
      * @throws IOException If an error occurs while reading the file.
      * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
      *
      * @note The binary file must contain serialized `LegalCase` objects.
      * @see appendNode(LegalCase, CaseNode) For adding cases to the doubly linked list.
      * @see printCase(CaseNode) For displaying case details.
      */
     public static boolean currentCases() {

    	    CaseNode head = null;
    	    CaseNode currentNode = null;

    	    File file = new File(FILE_NAME);
    	    if (!file.exists()) {
    	        out.println("Error: File does not exist. Please add cases first.");
    	        return false;
    	    }
    	   

    	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
    	        out.println("\n===== Current Cases =====\n");

    	        // Dosyadan tüm davaları oku ve listeye ekle
    	        while (true) {
    	            try {
    	                LegalCase currentCase = (LegalCase) ois.readObject();
    	                head = appendNode(head, currentCase); // Tüm davalar listeye eklenir
    	            } catch (EOFException e) {
    	                break; // Dosyanın sonuna ulaşıldı
    	            }
    	        }

    	        currentNode = head;
    	        char choice;

    	        while (true) {
    	            clearScreen();
    	            printCase(currentNode); // Mevcut düğümü ekrana yazdır

    	            out.println("Options:");
    	            if (currentNode.prev != null) {
    	            	out.println("P - Previous case");
    	            }
    	            if (currentNode.next != null) {
    	                out.println("N - Next case");
    	            }
    	            out.println("Q - Quit");
    	            out.print("Enter your choice: ");
    	            choice = scanner.next().charAt(0);

    	            if (choice == 'P' || choice == 'p') {
    	                if (currentNode.prev != null) {
    	                    currentNode = currentNode.prev; // Önceki düğüme git
    	                }
    	            } else if (choice == 'N' || choice == 'n') {
    	                if (currentNode.next != null) {
    	                    currentNode = currentNode.next; // Sonraki düğüme git
    	                }
    	            } else if (choice == 'Q' || choice == 'q') {
    	                break; // Çık
    	            } else {
    	                out.println("Invalid choice. Please try again.");
    	                out.print(" ");
    	                scanner.nextLine();
    	                out.print("");
    	                scanner.nextLine();
    	            }
    	        }

    	    } catch (IOException | ClassNotFoundException e) {
    	        
    	    }

    	    return true;
    	}
    
     /**
      * Pushes a deleted legal case onto the stack for restoration.
      * This method adds the provided `LegalCase` object to the top of the stack for potential recovery.
      *
      * @param deletedCase The `LegalCase` object to push onto the stack.
      *
      * @note The stack is used as a temporary storage for recently deleted cases.
      */
     public static void pushDeletedCase(LegalCase deletedCase) {
    deletedCasesStack.push(deletedCase);
     }

     /**
      * Pops the most recently deleted legal case from the stack.
      * This method retrieves and removes the last deleted case from the stack for restoration.
      *
      * @return The `LegalCase` object representing the last deleted case, or `null` if the stack is empty.
      *
      * @note If the stack is empty, an error message is displayed.
      */
     public static LegalCase popDeletedCase() {
    if (!deletedCasesStack.isEmpty()) {
        return deletedCasesStack.pop();
    } else {
        out.println("No deleted cases available to restore.");
        return null;
    }
}
     
     /**
      * Deletes a case from the hash table based on its ID.
      * This method marks the corresponding hash table slot as empty by setting it to `-1`.
      *
      * @param caseID The unique identifier of the case to delete.
      *
      * @note The method assumes the case ID exists in the hash table.
      * @see hashFunction(int) For calculating the hash index.
      */
    public static void deleteFromHashTable(int caseID) {
    int index = hashFunction(caseID);
    if (hashTableProbing[index] == caseID) {
        hashTableProbing[index] = -1; // Mark slot as empty
        
    }
}
    /**
     * Deletes a legal case from the system.
     * This method removes the case from the binary file, adds it to a stack for potential restoration,
     * and updates the hash table to reflect the deletion.
     *
     * @return `true` if the case is successfully deleted, `false` if the case is not found.
     *
     * @steps
     * 1. Prompt the user to enter a case ID.
     * 2. Read all cases from the binary file and write non-matching cases to a temporary file.
     * 3. If the case is found:
     *    - Push it onto the stack for restoration.
     *    - Delete it from the hash table.
     *    - Replace the original file with the updated temporary file.
     * 4. If the case is not found, display a message and delete the temporary file.
     *
     * @throws IOException If an error occurs during file operations.
     * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
     *
     * @note The method ensures the original file is not modified unless the operation is successful.
     * @see pushDeletedCase(LegalCase) For storing the deleted case in a stack.
     * @see deleteFromHashTable(int) For removing the case ID from the hash table.
     */
    public static boolean deleteCase() {
        clearScreen();
        Scanner scanner = new Scanner(System.in);

        int id = -1; // Varsayılan geçersiz değer
        while (true) {
            try {
                out.print("Enter Case ID to delete: ");
                id = scanner.nextInt(); // Kullanıcıdan tam sayı al
                scanner.nextLine(); // Tampondaki fazlalıkları temizle
                break; // Geçerli bir giriş alındıysa döngüden çık
            } catch (InputMismatchException e) {
                out.println("Invalid input! Please enter a valid numeric Case ID.");
                scanner.nextLine(); // Geçersiz girişi temizle
            }
        }

        File file = new File(FILE_NAME);
        File tempFile = new File("temp.bin");

        boolean found = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(tempFile))) {

            while (true) {
                try {
                    LegalCase currentCase = (LegalCase) ois.readObject();

                    if (currentCase.caseID == id) {
                        found = true;
                        pushDeletedCase(currentCase); // Silinen davayı yığına ekle
                        out.println("Case ID " + id + " deleted successfully.");
                    } else {
                        oos.writeObject(currentCase); // Diğer davaları geçici dosyaya yaz
                    }

                } catch (EOFException e) {
                    break; // Dosyanın sonuna ulaşıldı
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            out.println("Error handling files: " + e.getMessage());
        }

        if (found) {
            file.delete(); // Orijinal dosyayı sil
            tempFile.renameTo(file); // Geçici dosyayı orijinal dosyayla değiştir
            deleteFromHashTable(id); // Hash tablosundan kaldır
        } else {
            tempFile.delete(); // Geçici dosyayı sil
            out.println("Case ID " + id + " not found.");
        }

        out.println("\nPlease press Enter to return to Case Tracking Menu...");
        scanner.nextLine();

        return found;
    }

    /**
     * Restores the last deleted case from the stack.
     * This method retrieves the most recently deleted `LegalCase` from the stack and appends it back to the binary file.
     *
     * @return `true` if the undo operation is successful, `false` if the stack is empty or an error occurs.
     *
     * @throws IOException If an error occurs during the file writing process.
     *
     * @note This method requires the `AppendableObjectOutputStream` class to handle appending without rewriting the file header.
     * @see pushDeletedCase(LegalCase) For adding deleted cases to the stack.
     */
     public static boolean undoDeleteCase() {
    if (deletedCasesStack.isEmpty()) {
        out.println("No cases available to undo.");
        return false;
    }

    LegalCase lastDeletedCase = deletedCasesStack.pop(); // Silinen davayı yığından çıkar
    try (ObjectOutputStream oos = new AppendableObjectOutputStream(new FileOutputStream(FILE_NAME, true))) {
        oos.writeObject(lastDeletedCase); // Dosyaya geri ekle
        out.println("Undo successful for Case ID: " + lastDeletedCase.caseID);
        return true;
    } catch (IOException e) {
        out.println("Error undoing delete: " + e.getMessage());
        return false;
    }
}

     /**
      * Views the last deleted case and offers the option to undo the deletion.
      * This method retrieves and displays the details of the last deleted `LegalCase` from the stack.
      * The user is prompted to confirm whether to restore the case.
      *
      * @return `true` if the undo operation is performed, `false` otherwise.
      *
      * @note If the stack is empty, an error message is displayed.
      * @see undoDeleteCase() For performing the undo operation.
      */
     public static boolean incorrectDeletionCase() {
    if (deletedCasesStack.isEmpty()) {
        out.println("No deleted cases to view.");
        return false;
    }

    LegalCase lastDeletedCase = deletedCasesStack.peek(); // Yığındaki son davayı gör
    out.println("Last deleted case details:");
    out.println("Case ID: " + lastDeletedCase.caseID);
    out.println("Case Title: " + lastDeletedCase.title);


    out.print("Do you want to undo the last deleted case? (y/n): ");
    char confirmation = scanner.nextLine().toLowerCase().charAt(0);

    if (confirmation == 'y') {
        return undoDeleteCase(); // Geri alma işlemini gerçekleştir
    } else {
        out.println("Undo operation cancelled.");
        return false;
    }
}

     /**
      * Checks if a specific case ID exists in the deleted cases stack.
      * This method iterates through the stack to determine if the specified case ID matches any deleted case.
      *
      * @param caseID The unique identifier of the case to check.
      * @return `true` if the case ID is found in the stack, `false` otherwise.
      *
      * @note The stack is used to store recently deleted cases for potential restoration.
      */
	public static boolean isDeleted(int caseID) {
    for (LegalCase deletedCase : deletedCasesStack) {
        if (deletedCase.caseID == caseID) {
            return true; // Silinmiş bir dava bulundu
        }
    }
    return false; // Silinmiş bir dava yok
}
	/**
	 * Compares two dates in the "dd/MM/yyyy" format.
	 * This method parses the input strings into `Date` objects and compares them using `Date.compareTo()`.
	 *
	 * @param date1 The first date string to compare.
	 * @param date2 The second date string to compare.
	 * @return A negative integer if `date1` is earlier than `date2`, zero if they are equal, 
	 *         or a positive integer if `date1` is later than `date2`.
	 *
	 * @throws ParseException If the input date strings are not in the expected format.
	 *
	 * @note Invalid date strings result in a `0` return value, treating them as equal.
	 */
public static int compareDates(String date1, String date2) {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    try {
        Date d1 = sdf.parse(date1);
        Date d2 = sdf.parse(date2);
        return d1.compareTo(d2);
    } catch (ParseException e) {
        e.printStackTrace();
        return 0; // Geçersiz tarih durumunda eşit kabul edilir
    }
}

/**
 * Maintains the max heap property for a given subtree in an array of `LegalCase` objects.
 * This method ensures that the root of the subtree is the largest element by comparing the scheduled dates.
 *
 * @param cases The array of `LegalCase` objects.
 * @param n The size of the heap.
 * @param i The index of the root node of the subtree.
 *
 * @note This method uses the `compareDates` function to compare scheduled hearing dates.
 * @see heapSort(LegalCase[]) For sorting the array using heap sort.
 */
public static void heapify(LegalCase[] cases, int n, int i) {
    int largest = i; // Root
    int left = 2 * i + 1; // Sol çocuk
    int right = 2 * i + 2; // Sağ çocuk

    if (left < n && compareDates(cases[left].scheduled, cases[largest].scheduled) > 0) {
        largest = left;
    }

    if (right < n && compareDates(cases[right].scheduled, cases[largest].scheduled) > 0) {
        largest = right;
    }

    if (largest != i) {
        LegalCase temp = cases[i];
        cases[i] = cases[largest];
        cases[largest] = temp;

        // Alt ağacı heapify et
        heapify(cases, n, largest);
    }
}

/**
 * Sorts an array of `LegalCase` objects by their scheduled hearing dates using heap sort.
 * This method first builds a max heap and then repeatedly extracts the maximum element, rearranging the heap.
 *
 * @param cases The array of `LegalCase` objects to sort.
 *
 * @note The sorting is performed in-place, and the array is sorted in ascending order of scheduled hearing dates.
 * @see heapify(LegalCase[], int, int) For maintaining the heap property.
 */
public static void heapSort(LegalCase[] cases) {
    int n = cases.length;

    // Max heap oluştur
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(cases, n, i);
    }

    // Elemanları ayır ve heapify et
    for (int i = n - 1; i > 0; i--) {
        LegalCase temp = cases[0];
        cases[0] = cases[i];
        cases[i] = temp;

        heapify(cases, i, 0);
    }
}

/**
 * Displays a sorted list of legal cases based on their scheduled hearing dates.
 * This method reads cases from a binary file, sorts them using heap sort, and prints them in ascending order.
 *
 * @return `true` if the cases are successfully read and sorted, `false` if the file does not exist or an error occurs.
 *
 * @steps
 * 1. Check if the file exists. If not, display an error message and exit.
 * 2. Read all cases from the binary file into a list.
 * 3. Convert the list to an array and sort it using `heapSort`.
 * 4. Display the sorted cases, including their IDs and scheduled hearing dates.
 *
 * @throws IOException If an error occurs while reading the file.
 * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
 *
 * @note The file must contain serialized `LegalCase` objects.
 * @see heapSort(LegalCase[]) For sorting the array of cases.
 * @see compareDates(String, String) For comparing the scheduled dates.
 */
public static boolean caseDates() {
    clearScreen();

    File file = new File(FILE_NAME);
    if (!file.exists()) {
        out.println("Error: File does not exist. Please add cases first.");
        return false;
    }

    List<LegalCase> caseList = new ArrayList<>();
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        while (true) {
            try {
                LegalCase legalCase = (LegalCase) ois.readObject();
                caseList.add(legalCase);
            } catch (EOFException e) {
                break; // Dosyanın sonuna ulaşıldı
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        
        return false;
    }

    // Listeyi diziye çevir ve sıralama yap
    LegalCase[] caseArray = caseList.toArray(new LegalCase[0]);
    heapSort(caseArray);

    // Sıralı davaları yazdır
    out.println("\n===== Sorted Case Dates =====\n");
    for (LegalCase legalCase : caseArray) {
        out.println("Case ID: " + legalCase.caseID);
        out.println("Scheduled Hearing Date: " + legalCase.scheduled);
        out.println("-----------------------------");
    }

    out.print("Please press Enter to return to the Case Tracking Menu...");
	scanner.nextLine();
    return true;
}

/**
 * Represents the root node of the B+ tree.
 * The root node is the entry point for all operations performed on the B+ tree, such as insertions and searches.
 *
 * @note Initially, the root is `null`, indicating an empty tree.
 *       The root is updated during the insertion process when necessary.
 */
public static BPlusTreeNode root;

/**
 * Inserts a new key and associated legal case into the B+ tree.
 * This method finds the appropriate leaf node for the given key, inserts the key and case,
 * and handles node splitting if the leaf node exceeds the maximum capacity.
 *
 * @param key The key to insert into the B+ tree.
 * @param newCase The `LegalCase` object associated with the key.
 *
 * @note If the tree is empty, this method initializes the root as a new leaf node.
 * @note If node splitting occurs, the parent node is updated or a new root is created.
 *
 * @see insertInNode(BPlusTreeNode, int, LegalCase) For inserting key and case into a node.
 * @see split(BPlusTreeNode, BPlusTreeNode) For handling node splits.
 */
public static void insert(int key, LegalCase newCase) {
    if (root == null) {
        root = new BPlusTreeNode(true);
        root.keys[0] = key;
        root.cases[0] = newCase;
        root.numKeys = 1;
    } else {
        BPlusTreeNode current = root;
        BPlusTreeNode parent = null;

        while (!current.isLeaf) {
            parent = current;
            int i;
            for (i = 0; i < current.numKeys; i++) {
                if (key < current.keys[i]) {
                    break;
                }
            }
            current = current.children[i];
        }

        insertInNode(current, key, newCase);
        if (current.numKeys == BPlusTreeNode.MAX) {
            split(parent, current);
        }
    }
}

/**
 * Inserts a key and associated legal case into a specific B+ tree node.
 * This method shifts existing keys and cases to create space for the new key and case,
 * maintaining the sorted order of keys in the node.
 *
 * @param node The B+ tree node where the key and case will be inserted.
 * @param key The key to insert.
 * @param newCase The `LegalCase` object associated with the key.
 *
 * @note This method assumes that the node has space for at least one more key.
 */
private static void insertInNode(BPlusTreeNode node, int key, LegalCase newCase) {
    int i = node.numKeys - 1;
    while (i >= 0 && node.keys[i] > key) {
        node.keys[i + 1] = node.keys[i];
        node.cases[i + 1] = node.cases[i];
        i--;
    }
    node.keys[i + 1] = key;
    node.cases[i + 1] = newCase;
    node.numKeys++;
}

/**
 * Splits a B+ tree node into two nodes when it exceeds the maximum capacity.
 * This method creates a new node, redistributes keys and cases between the original and new nodes,
 * and updates the parent node to reflect the split.
 *
 * @param parent The parent node of the node being split. If `null`, a new root is created.
 * @param node The B+ tree node to split.
 *
 * @note If the node is a leaf, the `next` pointer is updated to maintain the linked list structure of leaf nodes.
 * @note If the node is the root, a new root node is created to accommodate the split.
 *
 * @see insertInNode(BPlusTreeNode, int, LegalCase) For updating the parent node during a split.
 */
static void split(BPlusTreeNode parent, BPlusTreeNode node) {
    int midIndex = BPlusTreeNode.MAX / 2;
    BPlusTreeNode newNode = new BPlusTreeNode(node.isLeaf);

    newNode.numKeys = BPlusTreeNode.MAX - midIndex - 1;
    for (int i = 0; i < newNode.numKeys; i++) {
        newNode.keys[i] = node.keys[midIndex + 1 + i];
        newNode.cases[i] = node.cases[midIndex + 1 + i];
    }

    if (!node.isLeaf) {
        for (int i = 0; i <= newNode.numKeys; i++) {
            newNode.children[i] = node.children[midIndex + 1 + i];
        }
    }

    node.numKeys = midIndex;

    if (node.isLeaf) {
        newNode.next = node.next;
        node.next = newNode;
    }

    if (parent == null) {
        parent = new BPlusTreeNode(false);
        parent.keys[0] = node.keys[midIndex];
        parent.children[0] = node;
        parent.children[1] = newNode;
        parent.numKeys = 1;
        root = parent;
    } else {
        insertInNode(parent, node.keys[midIndex], null);
        for (int i = parent.numKeys; i > 0; i--) {
            if (parent.children[i - 1] == node) {
                parent.children[i] = newNode;
                break;
            }
        }
    }
}

/**
 * Prints all legal cases in the B+ tree in sorted order based on case IDs.
 * This method traverses the leaf nodes of the B+ tree and prints the details
 * of all cases stored in the tree.
 *
 * @return `true` if cases are printed successfully, `false` if the tree is empty.
 *
 * @note The B+ tree must be populated with cases before calling this method.
 */
public static boolean printSortedCases() {
    if (root == null) {
        System.out.println("No cases to display.");
        return false;
    }

    BPlusTreeNode current = root;
    while (!current.isLeaf) {
        current = current.children[0];
    }

    while (current != null) {
        for (int i = 0; i < current.numKeys; i++) {
            LegalCase legalCase = current.cases[i];
            System.out.println("Case ID: " + legalCase.caseID);
            System.out.println("Case Title: " + legalCase.title);
            System.out.println("Plaintiff: " + legalCase.plaintiff);
            System.out.println("Defendant: " + legalCase.defendant);
            System.out.println("Case Type: " + legalCase.type);
            System.out.println("Beginning Date: " + legalCase.date);
            System.out.println("Scheduled Hearing Date: " + legalCase.scheduled);
            System.out.println("-----------------------------");
        }
        current = current.next;
    }
	return true;
}

/**
 * Sorts legal cases by their IDs and displays them in ascending order.
 * This method reads all cases from a binary file, inserts them into a B+ tree,
 * and prints the sorted cases using in-order traversal.
 *
 * @return `true` if cases are sorted and displayed successfully, `false` if the file does not exist or an error occurs.
 *
 * @throws IOException If an error occurs while reading the file.
 * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
 *
 * @see insert(int, LegalCase) For adding cases to the B+ tree.
 * @see printSortedCases() For printing sorted cases.
 */
public static boolean sortByID() {
    clearScreen();

    File file = new File(FILE_NAME);
    if (!file.exists()) {
        System.out.println("Error: File does not exist. Please add cases first.");
        return false;
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        while (true) {
            try {
                // Dosyadan davaları oku ve ağaca ekle
                LegalCase legalCase = (LegalCase) ois.readObject();
                LegalCase.insert(legalCase.caseID, legalCase);
            } catch (EOFException e) {
                break; // Dosyanın sonuna ulaşıldı
            }
        }
    } catch (IOException | ClassNotFoundException e) {
        
        return false;
    }

    // Ağacı sıralı şekilde yazdır
    System.out.println("\n===== Sorted Case Dates =====\n");
    LegalCase.printSortedCases();

    System.out.print("Please press Enter to return to the Case Tracking Menu...");
    scanner.nextLine();
    return true;
}

    // Dava türleri için sabit isim listesi
    static final String[] caseNames = {
        "Criminal", "Civil", "Commercial", "Administrative",
        "Divorce", "Custody", "Traffic", "Dismissal",
        "Compensation", "Inheritance", "Title deed"};

    /**
     * Adds an edge between two vertices in an undirected graph.
     * This method updates the adjacency list representation of the graph to reflect the connection.
     *
     * @param graph The `Graph` object representing the graph.
     * @param src The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     *
     * @note The graph is assumed to use zero-based indexing for its vertices.
     */
    static void addEdge(Graph graph, int src, int dest) {
        GraphNode newNode = new GraphNode(dest);
        newNode.next = graph.adjLists[src];
        graph.adjLists[src] = newNode;

        newNode = new GraphNode(src);
        newNode.next = graph.adjLists[dest];
        graph.adjLists[dest] = newNode;
    }

    /**
     * Performs Breadth-First Search (BFS) on a graph starting from a given vertex.
     * This method traverses the graph to find and display all case types connected
     * to the selected case type.
     *
     * @param graph The `Graph` object representing the graph.
     * @param startCaseType The index of the case type to start the BFS traversal.
     *
     * @note This method resets the visited status of all vertices after traversal.
     * @see addEdge(Graph, int, int) For adding edges to the graph.
     */
    static void BFS(Graph graph, int startCaseType) {
        CustomQueue queue = new CustomQueue(graph.numVertices);

        graph.visited[startCaseType] = true;
        queue.enqueue(startCaseType);

        out.println("Selected Case: " + caseNames[startCaseType]);
        out.println("\nCases That May Be Related");
        out.println("-----------------------------");

        while (!queue.isEmpty()) {
            int currentCase = queue.dequeue();
            GraphNode temp = graph.adjLists[currentCase];

            while (temp != null) {
                int adjCase = temp.caseType;

                if (!graph.visited[adjCase]) {
                    out.println("** " + caseNames[adjCase]);
                    graph.visited[adjCase] = true;
                    queue.enqueue(adjCase);
                }
                temp = temp.next;
            }
        }

        // Ziyaret edilenleri sıfırla
        for (int i = 0; i < graph.numVertices; i++) {
            graph.visited[i] = false;
        }
    }

    /**
     * Displays a menu for exploring cases that may be connected.
     * This method allows the user to select a case type and view all related case types
     * using Breadth-First Search (BFS).
     *
     * @return `true` when the user chooses to return to the main menu.
     *
     * @steps
     * 1. Initialize the graph with predefined case type connections.
     * 2. Display the list of case types and prompt the user for a selection.
     * 3. Perform BFS to find and display connected case types.
     * 4. Allow the user to return to the main menu or make another selection.
     *
     * @note Predefined edges are added to the graph to establish connections between case types.
     * @see BFS(Graph, int) For traversing the graph.
     */
    static boolean casesThatMayBeConnectedMenu() {
        clearScreen(); // İlk açılışta ekranı temizle
        int NUM_CASE_TYPES = caseNames.length;
        Graph graph = new Graph(NUM_CASE_TYPES);

        // Kenarları ekle
        addEdge(graph, 4, 5); // Divorce - Custody
        addEdge(graph, 4, 8); // Divorce - Compensation
        addEdge(graph, 9, 10); // Inheritance - Title deed
        addEdge(graph, 2, 1); // Commercial - Civil
        addEdge(graph, 0, 7); // Criminal - Dismissal
        addEdge(graph, 6, 0); // Traffic - Criminal

        int choice;

        while (true) {
            
            out.println("\n===== Cases That May Be Connected Menu =====");
            for (int i = 0; i < NUM_CASE_TYPES; i++) {
                out.println(i + ". " + caseNames[i]);
            }
            out.println(NUM_CASE_TYPES + ". Return to Main Menu"); // Çıkış seçeneği

            out.print("Please Enter The Number Next To Your Case: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {

                out.println("Invalid input. Please enter a number.");
                scanner.next(); // Geçersiz girdiyi temizle
                continue;
            }
            clearScreen();
            if (choice >= 0 && choice < NUM_CASE_TYPES) {
                clearScreen(); // Seçim yapıldıktan sonra ekranı temizle
                BFS(graph, choice); // Geçerli bir dava türü seçildiğinde BFS çalıştır
            } else if (choice == NUM_CASE_TYPES) {
                return true; // Ana menüye dön
            } else {
                out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    /**
     * The maximum number of case types supported in the system.
     * This constant defines the size of the adjacency matrix and related structures
     * used for managing case type relationships.
     */
        static final int MAX = 44;

        /**
         * A list of predefined case types for the system.
         * This array contains all supported case types, represented by `CaseTypeForSCC` objects,
         * each with a unique identifier and descriptive name.
         *
         * @note The identifiers are used for indexing purposes in graphs, adjacency matrices,
         *       and other structures related to case type management.
         * @note Duplicate names, such as "Divorce Cases" and "Custody Cases," are intentional to reflect
         *       different contexts or scenarios in the system.
         *
         */
        static final CaseTypeForSCC[] caseTypeSCC = {
            new CaseTypeForSCC(0, "Administrative Cases"),
            new CaseTypeForSCC(1, "Cancellation Cases"),
            new CaseTypeForSCC(2, "Full Jurisdiction Cases"),
            new CaseTypeForSCC(3, "Expropriation Cases"),
            new CaseTypeForSCC(4, "Civil Cases"),
            new CaseTypeForSCC(5, "Divorce Cases"),
            new CaseTypeForSCC(6, "Alimony Cases"),
            new CaseTypeForSCC(7, "Inheritance Cases"),
            new CaseTypeForSCC(8, "Commercial Cases"),
            new CaseTypeForSCC(9, "Debt Cases"),
            new CaseTypeForSCC(10, "Bankruptcy Cases"),
            new CaseTypeForSCC(11, "Tort Cases"),
            new CaseTypeForSCC(12, "Criminal Cases"),
            new CaseTypeForSCC(13, "Compensation Cases"),
            new CaseTypeForSCC(14, "Insurance Cases"),
            new CaseTypeForSCC(15, "Civil Liability Cases"),
            new CaseTypeForSCC(16, "Compensation Cases"),
            new CaseTypeForSCC(17, "Non-pecuniary Compensation Cases"),
            new CaseTypeForSCC(18, "Pecuniary Compensation Cases"),
            new CaseTypeForSCC(19, "Bodily Injury Compensation Cases"),
            new CaseTypeForSCC(20, "Custody Cases"),
            new CaseTypeForSCC(21, "Child Support Cases"),
            new CaseTypeForSCC(22, "Parents Cases"),
            new CaseTypeForSCC(23, "Change Of Residence Cases"),
            new CaseTypeForSCC(24, "Divorce Cases"),
            new CaseTypeForSCC(25, "Custody Cases"),
            new CaseTypeForSCC(26, "Alimony Cases"),
            new CaseTypeForSCC(27, "Compensation Cases"),
            new CaseTypeForSCC(28, "Dismissal Cases"),
            new CaseTypeForSCC(29, "Unjust Termination Cases"),
            new CaseTypeForSCC(30, "Severance Pay Cases"),
            new CaseTypeForSCC(31, "Reemployment Cases"),
            new CaseTypeForSCC(32, "Inheritance Cases"),
            new CaseTypeForSCC(33, "Land Registration Cases"),
            new CaseTypeForSCC(34, "Testament Annulment Cases"),
            new CaseTypeForSCC(35, "Rejection Inheritance Cases"),
            new CaseTypeForSCC(36, "Title Deed Cases"),
            new CaseTypeForSCC(37, "Border Dispute Cases"),
            new CaseTypeForSCC(38, "Corruption Allegations Cases"),
            new CaseTypeForSCC(39, "Faulty Registration Correction Cases"),
            new CaseTypeForSCC(40, "Traffic Cases"),
            new CaseTypeForSCC(41, "Non-pecuniary Compensation Cases"),
            new CaseTypeForSCC(42, "Pecuniary Compensation Cases"),
            new CaseTypeForSCC(43, "Inheritance Cases")
        };

        // Adjacency matrix
        static int[][] adj = new int[MAX][MAX];

        /**
         * Adds an edge between two vertices in an adjacency matrix representation of a graph.
         * This method updates the matrix to reflect the connection between the two vertices.
         *
         * @param u The source vertex of the edge.
         * @param v The destination vertex of the edge.
         *
         * @note The graph is assumed to use zero-based indexing for its vertices.
         */
        static void addEdge(int u, int v) {
            adj[u][v] = 1;
        }

        /**
         * Displays the "Cases That May Arise" menu and handles user interaction.
         * This method allows the user to select a case type and view related cases that may arise,
         * based on predefined connections in the adjacency graph.
         *
         * @return `true` after displaying the related cases and waiting for user confirmation to return to the main menu.
         *
         * @steps
         * 1. Initialize the graph by adding edges to represent relationships between case types.
         * 2. Display the menu with available case type options.
         * 3. Validate user input and determine the selected case type.
         * 4. Show the selected case type and related cases that may arise.
         *
         * @note The adjacency graph is statically defined, and the related cases are determined by their predefined relationships.
         * @see addEdge(int, int) For defining relationships between case types.
         */
        static boolean casesThatMayAriseMenu() {
            clearScreen();

            // Kenarları ekle
            addEdge(0, 1);
            addEdge(0, 2);
            addEdge(0, 3);
            addEdge(4, 5);
            addEdge(4, 6);
            addEdge(4, 7);
            addEdge(8, 9);
            addEdge(8, 10);
            addEdge(8, 11);
            addEdge(12, 13);
            addEdge(12, 14);
            addEdge(12, 15);
            addEdge(16, 17);
            addEdge(16, 18);
            addEdge(16, 19);
            addEdge(20, 21);
            addEdge(20, 22);
            addEdge(20, 23);
            addEdge(24, 25);
            addEdge(24, 26);
            addEdge(24, 27);
            addEdge(28, 29);
            addEdge(28, 30);
            addEdge(28, 31);
            addEdge(32, 33);
            addEdge(32, 34);
            addEdge(32, 35);
            addEdge(36, 37);
            addEdge(36, 38);
            addEdge(36, 39);
            addEdge(40, 41);
            addEdge(40, 42);
            addEdge(40, 43);

            out.println("===== Cases That May Arise Menu =====\n");

            for (int i = 1; i <= 11; i++) {
                out.println(i + "-) " + caseTypeSCC[(i - 1) * 4].name);
            }

            int caseChoice = -1;
            boolean validInput = false;

            // Geçerli bir giriş alınana kadar döngü
            while (!validInput) {
                out.print("Please Make Your Choice (1-11): ");
                if (scanner.hasNextInt()) {
                    caseChoice = scanner.nextInt();
                    scanner.nextLine(); // Buffer temizleme
                    if (caseChoice >= 1 && caseChoice <= 11) {
                        validInput = true; // Geçerli giriş
                    } else {
                        out.println("Invalid choice. Please enter a number between 1 and 11.");
                    }
                } else {
                    out.println("Invalid input! Please enter a valid numeric choice.");
                    scanner.nextLine(); // Geçersiz girdiyi temizle
                }
            }

            // Seçilen dava türünü göster ve ilgili işlemleri yap
            clearScreen();
            out.println("\nSelected Case Type: " + caseTypeSCC[(caseChoice - 1) * 4].name);
            out.println("\nCases That May Arise:");

            for (int i = (caseChoice - 1) * 4 + 1; i < MAX; i++) {
                if (i % 4 == 0) break;
                out.println("- " + caseTypeSCC[i].name);
            }

            out.println("\n\nPlease press Enter to return to the Case Tracking Menu...");
            scanner.nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler

            return true;
        }

        /**
         * Retrieves the unique identifier for the legal case.
         *
         * @return The `caseID` of the legal case.
         *
         */
        public int getCaseID() {
            return caseID;
        }

        /**
         * Retrieves the plaintiff's name for the legal case.
         *
         * @return The `plaintiff` associated with the legal case.
         *
         */
        public String getPlaintiff() {
            return plaintiff;
        }
       
        /**
         * Performs an XOR operation on two `PlaintiffNode` objects.
         * This method calculates the XOR of the memory addresses of two nodes,
         * which is used for maintaining a doubly linked XOR list.
         *
         * @param a The first `PlaintiffNode`.
         * @param b The second `PlaintiffNode`.
         * @return The result of the XOR operation, representing the combined link.
         *
         * @note If either `a` or `b` is `null`, the method returns the non-null node.
         */
        private static PlaintiffNode XOR(PlaintiffNode a, PlaintiffNode b) {
            return (a == null ? b : (b == null ? a : new PlaintiffNode(null)));
        }

        /**
         * Adds a new plaintiff node to the XOR linked list.
         * This method creates a new `PlaintiffNode` with the given legal case data,
         * links it to the existing list using XOR operations, and updates the head pointer.
         *
         * @param head The current head node of the XOR linked list.
         * @param data The `LegalCase` object to associate with the new node.
         * @return The updated head node of the XOR linked list.
         *
         *
         * @note This method uses the `XOR` function to maintain the XOR linked list structure.
         * @see XOR(PlaintiffNode, PlaintiffNode) For calculating XOR links.
         */
        public static PlaintiffNode addPlaintiffNode(PlaintiffNode head, LegalCase data) {
            PlaintiffNode newNode = new PlaintiffNode(data);
            newNode.xorLink = head;

            if (head != null) {
                head.xorLink = XOR(newNode, head.xorLink);
            }

            return newNode; // Yeni head düğümünü döndür
        }
        
        /**
         * Prints the details of a plaintiff from a given plaintiff node.
         * This method retrieves and displays the case ID and plaintiff name associated with the node.
         *
         * @param node The `PlaintiffNode` containing the legal case data to print.
         *
         * @note If the node or its data is `null`, a message is displayed indicating no plaintiff is found.
         */
        public static void printPlaintiff(PlaintiffNode node) {
            if (node == null || node.data == null) {
                out.println("No plaintiff found.");
                return;
            }
            out.println("Case ID: " + node.data.getCaseID());
            out.println("Plaintiff: " + node.data.getPlaintiff());
            out.println("-----------------------------");
        }

        /**
         * Displays a list of plaintiffs and their associated case IDs using an XOR linked list.
         * This method reads legal cases from a binary file, constructs an XOR linked list of plaintiffs,
         * and allows the user to navigate through the list interactively.
         *
         * @return `true` if plaintiffs are displayed successfully, `false` if no plaintiffs are found or an error occurs.
         *
         * @steps
         * 1. Read all legal cases from the binary file.
         * 2. Add each case to the XOR linked list using `addPlaintiffNode`.
         * 3. If the list is empty, display an appropriate message.
         * 4. Allow the user to navigate the list with the following options:
         *    - `P`: Move to the previous plaintiff.
         *    - `N`: Move to the next plaintiff.
         *    - `Q`: Quit the navigation.
         *
         * @throws IOException If an error occurs while reading the file.
         * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
         *
         * @note The XOR linked list is traversed interactively based on user input.
         * @see addPlaintiffNode(PlaintiffNode, LegalCase) For adding nodes to the XOR linked list.
         * @see XOR(PlaintiffNode, PlaintiffNode) For computing XOR links between nodes.
         * @see printPlaintiff(PlaintiffNode) For displaying plaintiff details.
         */
        public static boolean displayPlaintiffs() {
            PlaintiffNode head = null;

            // Davaları XOR bağlı listeye ekle (dosyadan verileri oku)
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                while (true) {
                    try {
                        LegalCase currentCase = (LegalCase) ois.readObject();
                        if (currentCase != null) {
                            head = addPlaintiffNode(head, currentCase); // Listeye ekle
                        }
                    } catch (EOFException e) {
                        break; // Dosyanın sonuna ulaşıldı
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                out.println("Error reading cases: " + e.getMessage());
                return false;
            }

            // Eğer liste boşsa kullanıcıya bilgi ver
            if (head == null) {
                out.println("No plaintiffs found.");
                return false;
            }

            // Liste üzerinde gezinme
            PlaintiffNode current = head;
            PlaintiffNode prev = null;
            PlaintiffNode next;


            while (current != null) {
                clearScreen();
                printPlaintiff(current);

                // Mevcut düğümün bir sonraki düğümünü hesapla
                next = XOR(prev, current.xorLink);

                // Kullanıcı seçenekleri
                out.println("Options:");
                if (prev != null) {
                    out.println("P - Previous plaintiff"); // Önceki düğüm
                }
                if (next != null) {
                    out.println("N - Next plaintiff"); // Sonraki düğüm
                }
                out.println("Q - Quit");
                out.print("Enter your choice: ");
                char choice = scanner.next().charAt(0);

                if (choice == 'P' || choice == 'p') {
                    if (prev == null) {
                        out.println("No previous plaintiff available.");
                        pause();
                    } else {
                        PlaintiffNode temp = current;
                        current = prev; // Önceki düğüme geç
                        prev = XOR(temp, current.xorLink);
                    }
                } else if (choice == 'N' || choice == 'n') {
                    if (next == null) {
                        out.println("No next plaintiff available.");
                        pause();
                    } else {
                        PlaintiffNode temp = current;
                        prev = current;
                        current = next; // Sonraki düğüme geç
                    }
                } else if (choice == 'Q' || choice == 'q') {
                    break; // Çıkış
                } else {
                    out.println("Invalid choice. Please try again.");
                    pause();
                }
            }

            return true;
        }

        /**
         * Pauses the execution and waits for the user to press Enter.
         * This method is used to provide a break in the program flow and allow the user
         * to review the current output before proceeding.
         *
         * @note This method ignores any I/O exceptions during execution.
         */
        private static void pause() {
            out.println("\nPress Enter to continue...");
            try {
                System.in.read();
            } catch (IOException ignored) {
            }
        }
        
        /**
         * Represents a detailed legal case document.
         * This class encapsulates the data for a legal case, including information about the winner,
         * loser, decision, and sentence, in addition to basic case details.
         *
         * @implements Serializable To allow saving and loading of objects from binary files.
         *
         * @fields
         * - `caseID`: The unique identifier of the case.
         * - `title`: The title of the case.
         * - `plaintiff`: The name of the plaintiff involved in the case.
         * - `defendant`: The name of the defendant involved in the case.
         * - `winner`: The name of the party who won the case.
         * - `loser`: The name of the party who lost the case.
         * - `decision`: The decision made in the case.
         * - `sentence`: The sentence or resolution provided for the case.
         */
        public static class LegalCaseDocument implements Serializable {
        	
        	/**
        	 * @brief Unique identifier for serialization.
        	 *
        	 * @details
        	 * The `serialVersionUID` is a unique identifier used during the serialization and deserialization 
        	 * process to verify that a loaded class corresponds to the serialized object. If no match is found, 
        	 * an `InvalidClassException` is thrown.
        	 *
        	 * @note 
        	 * - Always define `serialVersionUID` explicitly in a `Serializable` class to avoid issues 
        	 *   during deserialization when the class structure changes.
        	 * - The value can be generated automatically by the IDE or defined manually.
        	 */
            private static final long serialVersionUID = 1L;
            int caseID;
            String title, plaintiff, defendant, winner, loser, decision, sentence;

            /**
             * @brief Constructor for initializing a `LegalCaseDocument` object.
             *
             * @details
             * Creates a new `LegalCaseDocument` instance with all relevant case details including
             * the case ID, title, parties involved, and the legal outcome.
             *
             * @param caseID The unique identifier for the legal case.
             * @param title The title of the legal case.
             * @param plaintiff The name of the plaintiff in the case.
             * @param defendant The name of the defendant in the case.
             * @param winner The name of the winning party.
             * @param loser The name of the losing party.
             * @param decision The decision made in the case.
             * @param sentence The sentence or judgment given for the case.
             *
             * @note This constructor is used to encapsulate all the essential details of a legal case 
             * into a single object for storage or processing.
             */
            public LegalCaseDocument(int caseID, String title, String plaintiff, String defendant,
                                     String winner, String loser, String decision, String sentence) {
                this.caseID = caseID;
                this.title = title;
                this.plaintiff = plaintiff;
                this.defendant = defendant;
                this.winner = winner;
                this.loser = loser;
                this.decision = decision;
                this.sentence = sentence;
            }
        }

        /**
         * Appends a legal case document to the document file.
         * This method writes the provided `LegalCaseDocument` object to a binary file in append mode,
         * preserving the existing data in the file.
         *
         * @param document The `LegalCaseDocument` object to save.
         * @return `true` if the document is saved successfully, `false` otherwise.
         *
         * @throws IOException If an error occurs during file writing.
         *
         * @note This method uses `AppendableObjectOutputStream` to prevent overwriting the file header.
         * @see AppendableObjectOutputStream For appending objects to a binary file.
         */
        public static boolean appendDocument(LegalCaseDocument document) {
            try {
                boolean fileExists = new File(DOCUMENT_FILE_NAME).exists();

                try (FileOutputStream fos = new FileOutputStream(DOCUMENT_FILE_NAME, true);
                     ObjectOutputStream oos = fileExists
                             ? new AppendableObjectOutputStream(fos)
                             : new ObjectOutputStream(fos)) {

                    oos.writeObject(document);
                }

                out.println("Document saved successfully to " + DOCUMENT_FILE_NAME);
            } catch (IOException e) {
                
            }
			return false;
        }

        /**
         * Creates a legal case document based on a selected case.
         * This method allows the user to select an existing case by ID, input additional details such as the winner,
         * loser, decision, and sentence, and save the document to a file.
         *
         * @return `true` if the document is created and saved successfully, `false` otherwise.
         *
         * @steps
         * 1. Display all existing cases from the binary file.
         * 2. Prompt the user to select a case by entering its ID.
         * 3. Validate the input and ensure the case ID exists in the file.
         * 4. Gather additional information (winner, loser, decision, and sentence) from the user.
         * 5. Create a `LegalCaseDocument` object and save it using `appendDocument`.
         *
         * @throws IOException If an error occurs while reading or writing files.
         * @throws ClassNotFoundException If the binary file contains incompatible or corrupted data.
         *
         * @note The binary file must contain serialized `LegalCase` objects for selection.
         * @see appendDocument(LegalCaseDocument) For saving the created document.
         */
        public static boolean createDocument() {
            clearScreen();

            File file = new File(FILE_NAME);
            if (!file.exists()) {
                out.println("Error: File not found!");
                out.println("Press Enter to return to the menu...");
                scanner.nextLine(); // Kullanıcının Enter'a basmasını bekler
                return false;
            }

        
            LegalCase selectedCase = null;

            // Dosyadaki mevcut davaları göster
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                out.println("\n===== Current Cases =====");

                while (true) {
                    try {
                        LegalCase currentCase = (LegalCase) ois.readObject();
                        out.println("Case ID: " + currentCase.caseID);
                        out.println("Title: " + currentCase.title);
                        out.println("Plaintiff: " + currentCase.plaintiff);
                        out.println("Defendant: " + currentCase.defendant);
                        out.println("Type: " + currentCase.type);
                        out.println("Date: " + currentCase.date);
                        out.println("Scheduled: " + currentCase.scheduled);
                        out.println("-----------------------------");
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                out.println("Error reading cases: " + e.getMessage());
                return false;
            }

            boolean validCaseID = false; // Geçerli bir ID bulundu mu
            int id = -1;

            // Geçerli bir Case ID alana kadar döngü
            while (!validCaseID) {
                out.print("\nEnter Case ID to create a document for: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    scanner.nextLine(); // Buffer temizleme

                    // Girilen Case ID'nin dosyada olup olmadığını kontrol et
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        while (true) {
                            try {
                                LegalCase currentCase = (LegalCase) ois.readObject();
                                if (currentCase.caseID == id) {
                                    selectedCase = currentCase;
                                    validCaseID = true; // Geçerli bir ID bulundu
                                    break;
                                }
                            } catch (EOFException e) {
                                break;
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        
                        return false;
                    }

                    if (!validCaseID) {
                        out.println("Case ID not found! Please try again.");
                    }
                } else {
                    out.println("Invalid input! Please enter a valid numeric Case ID.");
                    scanner.next(); // Geçersiz girdiyi temizle
                }
            }

            // Seçilen davanın bilgilerini göster
            clearScreen();
            out.println("\n===== Selected Case =====");
            out.println("Case ID: " + selectedCase.caseID);
            out.println("Title: " + selectedCase.title);
            out.println("Plaintiff: " + selectedCase.plaintiff);
            out.println("Defendant: " + selectedCase.defendant);
            out.println("Type: " + selectedCase.type);
            out.println("Date: " + selectedCase.date);
            out.println("Scheduled: " + selectedCase.scheduled);

            out.print("Winner: ");
            String winner = scanner.nextLine();

            out.print("Loser: ");
            String loser = scanner.nextLine();

            out.print("Decision: ");
            String decision = scanner.nextLine();

            out.print("Sentence: ");
            String sentence = scanner.nextLine();

            LegalCaseDocument document = new LegalCaseDocument(
                    selectedCase.caseID,
                    selectedCase.title,
                    selectedCase.plaintiff,
                    selectedCase.defendant,
                    winner,
                    loser,
                    decision,
                    sentence
            );

            appendDocument(document);

            out.println("Document created and saved successfully!");
            out.println("Press Enter to return to the menu...");
            scanner.nextLine();
            return true;
        }
        
        /**
         * Displays a menu for document-related operations.
         * This method provides options to view all documents, search for documents by case title,
         * or search for documents by case ID.
         *
         * @return `true` after the user exits the menu.
         *
         * @menuOptions
         * - `1`: View all documents.
         * - `2`: Search for a document using a case title.
         * - `3`: Search for a document using a case ID.
         * - `4`: Exit the menu.
         *
         * @see allDocuments() For displaying all documents.
         * @see searchingWithCaseTitle() For searching documents by title.
         * @see searchByID() For searching documents by case ID.
         */
        public static boolean documents() {
            int choice;

            do {
                clearScreen();
                out.println("\n===== Case Tracking Menu =====");
                out.println("1. All Documents");
                out.println("2. Searching With Case Title");
                out.println("3. Searching With ID");
                out.println("4. Exit");
                out.print("\nEnter your choice: ");

                choice = getInput();

                switch (choice) {
                    case 1:
                        allDocuments();
                        break;
                    case 2:
                        searchingWithCaseTitle();
                        break;
                    case 3:
                        searchByID();
                        break;
                    case 4:
                        out.println("Exiting...");
                        break;
                    default:
                        out.print("Invalid choice! Please press Enter to continue: ");
                        scanner.nextLine(); // Kullanıcıdan giriş bekler
                        break;
                }
            } while (choice != 4);

            return true;
        }

        /**
         * Displays all legal case documents stored in the document file.
         * This method reads and prints the details of all `LegalCaseDocument` objects from a binary file.
         *
         * @return `true` if the documents are displayed successfully, `false` if the document file does not exist or an error occurs.
         *
         * @throws IOException If an error occurs while reading the file.
         * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
         *
         * @note The document file must contain serialized `LegalCaseDocument` objects.
         */
        public static boolean allDocuments() {
            clearScreen();

            File docFile = new File(DOCUMENT_FILE_NAME);
            if (!docFile.exists()) {
                out.println("Error: Document file not found!");
                return false;
            }

            out.println("\n===== All Documents =====\n");

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(docFile))) {
                while (true) {
                    try {
                        // Dosyadan bir `LegalCaseDocument` nesnesi oku
                        LegalCaseDocument document = (LegalCaseDocument) ois.readObject();

                        // Nesne bilgilerini ekrana yazdır
                        out.println("Case ID: " + document.caseID);
                        out.println("Title: " + document.title);
                        out.println("Plaintiff: " + document.plaintiff);
                        out.println("Defendant: " + document.defendant);
                        out.println("Winner: " + document.winner);
                        out.println("Loser: " + document.loser);
                        out.println("Decision: " + document.decision);
                        out.println("Sentence: " + document.sentence);
                        out.println("-----------------------------");
                    } catch (EOFException e) {
                        break; // Dosyanın sonuna ulaşıldı
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                
                return false;
            }

            out.println("\nPress Enter to return to the menu...");
            try {
                new Scanner(System.in).nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler
            } catch (Exception e) {
                out.println("Error reading input.");
            }

            return true;
        }
   
        /**
         * Computes the Longest Prefix Suffix (LPS) array for the KMP algorithm.
         * This method preprocesses the pattern to calculate the LPS array, which is used
         * to optimize pattern matching in the KMP algorithm.
         *
         * @param pattern The pattern string for which the LPS array is computed.
         * @param M The length of the pattern.
         * @param lps The array to store the LPS values.
         *
         * @note This method is a helper function for the `KMPSearch` algorithm.
         */
    public static void computeLPSArray(String pattern, int M, int[] lps) {
        int len = 0;
        lps[0] = 0;
        int i = 1;

        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    /**
     * Performs pattern matching using the Knuth-Morris-Pratt (KMP) algorithm.
     * This method searches for the occurrence of a pattern string within a text string
     * using an optimized prefix-suffix comparison.
     *
     * @param pattern The pattern string to search for.
     * @param text The text string in which to search for the pattern.
     * @return `true` if the pattern is found in the text, `false` otherwise.
     *
     * @see computeLPSArray(String, int, int[]) For computing the LPS array used in the search.
     */
    public static boolean KMPSearch(String pattern, String text) {
        int M = pattern.length();
        int N = text.length();
        int[] lps = new int[M];

        computeLPSArray(pattern, M, lps);

        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < N) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }

            if (j == M) {
                return true;
            } else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    /**
     * Searches for legal case documents by case title using the KMP algorithm.
     * This method reads documents from a binary file, searches for matches
     * with the given case title, and displays matching documents.
     *
     * @return `true` if the search is completed successfully, `false` if the document file does not exist or an error occurs.
     *
     * @steps
     * 1. Prompt the user to input a case title for searching.
     * 2. Validate the input and ensure it is not empty.
     * 3. Read all documents from the binary file and check for title matches using `KMPSearch`.
     * 4. Display matching documents or an appropriate message if no matches are found.
     *
     * @throws IOException If an error occurs while reading the file.
     * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
     *
     * @see KMPSearch(String, String) For matching the input title with document titles.
     */
    public static boolean searchingWithCaseTitle() {
        clearScreen();

        File file = new File("documents.bin");
        if (!file.exists()) {
            out.println("Error: Document file not found!");
            return false;
        }

        out.print("Enter the Case Title to search: ");
        String searchTitle = scanner.nextLine();

        // Kullanıcı giriş yapmazsa tekrar iste
        while (searchTitle.isEmpty()) {
        	clearScreen();
            out.println("Invalid input! Please enter a valid case title.");
            out.print("Enter the Case Title to search: ");
            searchTitle = scanner.nextLine().trim();
        }
       
        boolean found = false;
        out.println("\n===== Search Results =====\n");

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    // `LegalCaseDocument` nesnesini oku
                    LegalCaseDocument document = (LegalCaseDocument) ois.readObject();

                    // Başlık eşleşmesini KMP ile kontrol et
                    if (KMPSearch(searchTitle.toLowerCase(), document.title.toLowerCase())) {
                        out.println("Case ID: " + document.caseID);
                        out.println("Title: " + document.title);
                        out.println("Plaintiff: " + document.plaintiff);
                        out.println("Defendant: " + document.defendant);
                        out.println("Winner: " + document.winner);
                        out.println("Loser: " + document.loser);
                        out.println("Decision: " + document.decision);
                        out.println("Sentence: " + document.sentence);
                        out.println("-----------------------------");
                        found = true;
                    }
                } catch (EOFException e) {
                    break; // Dosyanın sonuna ulaşıldı
                }
            }
        } catch (IOException | ClassNotFoundException e) {
           
            return false;
        }

        if (!found) {
        	clearScreen();
            out.println("No matching case title found.");
        }

        out.println("Press Enter to return to the menu...");
        scanner.nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler
        return true;
    }
   
    /**
     * @brief An array to store `LegalCase` objects in the hash table.
     *
     * @details 
     * This static array serves as the primary storage for `LegalCase` instances 
     * within the hash table. Each index corresponds to a specific hash value calculated 
     * by the hash function. It ensures efficient retrieval and management of case data.
     *
     * @note 
     * The array size is determined by the `TABLE_SIZE` constant, which defines the maximum 
     * capacity of the hash table.
     */
    public static LegalCase[] hashTableCases = new LegalCase[TABLE_SIZE]; // LegalCase nesnelerini tutar

    /**
     * Searches for a legal case in the hash table using linear probing.
     * This method retrieves a `LegalCase` object if the given case ID exists in the hash table.
     *
     * @param caseID The unique identifier of the case to search for.
     * @return The `LegalCase` object if found, or `null` if the case ID does not exist in the hash table.
     *
     * @note This method uses linear probing for collision resolution.
     * @see hashFunction(int, int) For calculating the hash table index.
     */
    public static LegalCase searchInHashTable(int caseID) {
        int index = hashFunction(caseID, TABLE_SIZE); // Başlangıç indeksi hesaplanır
        int startIndex = index;

        while (hashTableProbing[index] != -1) { // Eğer mevcutsa
            if (hashTableProbing[index] == caseID) {
                return hashTableCases[index]; // İlgili dava döndürülür
            }
            index = (index + 1) % TABLE_SIZE; // Linear probing ile bir sonraki indeks
            if (index == startIndex) {
                break; // Döngüyü kır
            }
        }
        return null; // Bulunamadıysa null döner
    }
    
    /**
     * Searches for a legal case by ID in the hash table and the binary file.
     * This method first attempts to find the case in the hash table and, if unsuccessful, searches the binary file.
     *
     * @return `true` if the case is found or the search is completed, `false` otherwise.
     *
     * @steps
     * 1. Prompt the user to enter a case ID.
     * 2. Search for the case in the hash table using `searchInHashTable`.
     * 3. If not found, search the binary file for the case ID.
     * 4. Display the case details if found; otherwise, notify the user.
     *
     * @throws IOException If an error occurs while reading the file.
     * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
     *
     * @see searchInHashTable(int) For searching the hash table.
     * @see printCaseDetails(LegalCase) For displaying case details.
     */
    public static boolean searchByID() {
        clearScreen();
        int id = -1; // ID değişkeni
        while (true) {
            out.print("Enter Case ID to search: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                scanner.nextLine(); // Buffer temizleme
                break; // Geçerli giriş alındığında döngüden çık
            } else {
            	clearScreen();
                out.println("Invalid input! Please enter a valid numeric Case ID.");
                scanner.nextLine(); // Hatalı girdiyi temizle
            }
        }

        // Hash table'da arama
        LegalCase foundCase = searchInHashTable(id);
        if (foundCase != null) {
            
            printCaseDetails(foundCase);
        } else {
            // Dosyada arama
            boolean found = false;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                while (true) {
                    try {
                        LegalCase currentCase = (LegalCase) ois.readObject();
                        if (currentCase.caseID == id) {
                            found = true;
                            out.println("\n===== Case Found in File =====");
                            printCaseDetails(currentCase);
                            break;
                        }
                    } catch (EOFException e) {
                        break; // Dosyanın sonuna ulaşıldı
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                out.println("Error reading file: " + e.getMessage());
            }

            if (!found) {
            	clearScreen();
                out.println("Case ID " + id + " not found in both hash table and file.");
            }
        }

        out.println("Press Enter to return to the Case Tracking Menu...");
        scanner.nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler
        return true;
    }

    /**
     * Prints the details of a given `LegalCase` object.
     * This method displays the case ID, title, plaintiff, defendant, type, opening date, and scheduled hearing date.
     *
     * @param legalCase The `LegalCase` object whose details will be printed.
     * @return `true` after printing the case details.
     *
     */
    public static boolean printCaseDetails(LegalCase legalCase) {
        out.println("Case ID: " + legalCase.caseID);
        out.println("Case Title: " + legalCase.title);
        out.println("Plaintiff: " + legalCase.plaintiff);
        out.println("Defendant: " + legalCase.defendant);
        out.println("Case Type: " + legalCase.type);
        out.println("Date of Opening: " + legalCase.date);
        out.println("Scheduled Hearing Date: " + legalCase.scheduled);
        out.println("-----------------------------");
		return true;
    }
    
    /**
     * Checks if a specified file is empty or does not exist.
     * This method determines whether a file contains any serialized objects by attempting to read its contents.
     *
     * @param fileName The name of the file to check.
     * @return `true` if the file is empty or does not exist, `false` otherwise.
     *
     * @throws IOException If an error occurs while reading the file.
     * @throws ClassNotFoundException If the file contains incompatible or corrupted data.
     */
    public static boolean isFileEmpty(String fileName) {
        File file = new File(fileName);

        if (!file.exists()) {
            return true; // Eğer dosya yoksa, boş kabul edilir
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    ois.readObject(); // Dosyada en az bir nesne var mı kontrol et
                    return false; // Nesne bulundu, dosya boş değil
                } catch (EOFException e) {
                    break; // Dosyanın sonuna ulaşıldı
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true; // Dosya tamamen boşsa
    }
        
    /**
     * Initializes a frequency map for Huffman coding.
     * This method generates random frequencies for alphanumeric characters to be used in password encoding.
     *
     * @return A `Map` containing characters as keys and their frequencies as values.
     *
     * @note This method is part of the Huffman encoding mechanism for secure password storage.
     */
    private static Map<Character, Integer> initializeFrequencyMap() {
        Map<Character, Integer> map = new HashMap<>();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        for (char ch : characters.toCharArray()) {
            map.put(ch, (int) (Math.random() * 100 + 1)); // Random frequencies
        }
        return map;
    }

    /**
     * Encodes a password using Huffman coding.
     * This method replaces each character in the password with its corresponding Huffman code.
     *
     * @param password The password string to encode.
     * @return The encoded password as a string.
     *
     * @note The Huffman codes must be pre-generated and stored in the `huffmanCodes` map.
     */
    private static String encodePassword(String input) {
        StringBuilder encoded = new StringBuilder();
        for (char ch : input.toCharArray()) {
            String code = huffmanCodes.get(ch);
            if (code != null) {
                encoded.append(code);
            } else {
                out.println("Error: Character not found in Huffman codes.");
                return null;
            }
        }
        return encoded.toString();
    }

    /**
     * Registers a new user with a username and encoded password.
     * This method prompts the user for credentials, encodes the password using Huffman coding,
     * and appends the credentials to the user file.
     *
     * @return `true` if the registration is successful, `false` otherwise.
     *
     * @throws IOException If an error occurs while writing to the file.
     *
     * @see encodePassword(String) For encoding the password.
     */
    static boolean registerUser() {
        clearScreen();
        out.print("Enter username: ");
        String username = scanner.nextLine();
        out.print("Enter password: ");
        String password = readPassword(scanner);

        String encodedUsername = encodePassword(username);
        String encodedPassword = encodePassword(password);

        // Dosyaya yazma
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(encodedUsername + ":" + encodedPassword);
            writer.newLine();
        } catch (IOException e) {
            
            return false;
        }
        clearScreen();
        return true;
    }

    /**
     * Authenticates a user by checking their credentials against the user file.
     * This method prompts the user for credentials, encodes the password using Huffman coding,
     * and verifies the credentials against the stored user data.
     *
     * @return `true` if the login is successful, `false` otherwise.
     *
     * @throws IOException If an error occurs while reading the file.
     *
     * @see encodePassword(String) For encoding the password.
     */
    public static boolean loginUser() {
        clearScreen();
        out.print("Enter username: ");
        String username = scanner.nextLine();
        out.print("Enter password: ");
        String password = readPassword(scanner);

        String encodedUsername = encodePassword(username);
        String encodedPassword = encodePassword(password);

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(encodedUsername) && parts[1].equals(encodedPassword)) {
                    
                    return true;
                }
            }
        } catch (IOException e) {
           
        }

        out.println("Invalid username or password.");
        return false;
    }


    /**
     * Entry point for the user authentication system.
     * This method displays a menu for registering, logging in, or exiting the system.
     *
     * @return `true` if the user successfully logs in, `false` if they choose to exit.
     *
     * @menuOptions
     * - `1`: Register a new user.
     * - `2`: Log in as an existing user.
     * - `3`: Exit the system.
     *
     * @see registerUser() For registering new users.
     * @see loginUser() For authenticating users.
     */
    static boolean mainEntry() {
        while (true) {
            out.println("\n===== User Authentication System =====");
            out.println("1. Register");
            out.println("2. Login");
            out.println("3. Exit");
            out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    if (loginUser()) {
                        return true;
                    }
                    break;
                case 3:
                    out.println("Exiting...");
                    return false;
                default:
                    out.println("Invalid choice! Please enter a valid option.");
            }
        }
    }

    /**
     * Reads a password from the user securely, masking the input with asterisks (`*`).
     * This method supports both console-based and fallback input methods.
     *
     * @param scanner The `Scanner` object for reading user input.
     * @return The password entered by the user.
     *
     * @note If a secure console is available, the password is read without displaying it on the screen.
     *       Otherwise, characters are masked as they are entered.
     */
    public static String readPassword(Scanner scanner) {
        out.print("Enter password : "); // Kullanıcıya bilgi ver
        return scanner.nextLine(); // Parolayı açıkça okur
    }
    
    

    }


