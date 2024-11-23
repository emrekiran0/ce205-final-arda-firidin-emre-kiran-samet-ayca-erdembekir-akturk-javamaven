package com.arda.erdem.samet.emre.legalcase;

import static java.lang.System.out;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * @class LegalCase
 * @brief This class represents a LegalCase that performs mathematical operations.
 * @details The LegalCase class provides methods to perform mathematical operations such as addition, subtraction, multiplication, and division.
 * It also supports logging functionality using the logger object.
 * @author ugur.coruh
 */

public class LegalCase {
	public static final int MAX_ATTEMPTS = 1000;
	private int caseID;
    private String title;
    private String type;
    private String plaintiff;
    private String defendant;
    private String status;
    private String hearingDate;
	private LegalCase next;

    // Constructor
    public LegalCase(int caseID, String title, String type, String plaintiff, String defendant, String status, String hearingDate) {
        this.caseID = caseID;
        this.title = title;
        this.type = type;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.status = status;
        this.hearingDate = hearingDate;}
	
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        mainMenu();
        
    }
    
    static int MAX_YEARS = 10;
    static int MAX_MONTHS = 12;
    static int MAX_DAYS = 31;
    
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

    public static int getInput() {
        int choice;
        while (true) {
            out.print("Seçiminizi girin: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                return choice;
            } else {
                out.println("Geçersiz seçim! Lütfen tekrar deneyin.");
                scanner.nextLine(); // Geçersiz girişi temizle
            }
        }
    }
    
    private static int[][][] sparseMatrix = new int[MAX_YEARS][MAX_MONTHS][MAX_DAYS]; // Sparse matrix
    
    public static boolean isValidDate(int day, int month, int year) {
        if (month < 1 || month > MAX_MONTHS) { // Ayın 1 ile 12 arasında olup olmadığını kontrol eder
            return false;
        }
        if (day < 1 || day > MAX_DAYS) { // Günün 1 ile 31 arasında olup olmadığını kontrol eder
            return false;
        }
        return true; // Tarih geçerliyse `true` döner
    }
   
    private static void initializeSparseMatrix(int[][][] sparseMatrix) {
        for (int year = 0; year < MAX_YEARS; year++) {
            for (int month = 0; month < MAX_MONTHS; month++) {
                for (int day = 0; day < MAX_DAYS; day++) {
                    sparseMatrix[year][month][day] = 0; // Tüm tarihleri boş olarak işaretle
                }
            }
        }
    }
	
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

    public static boolean mainMenu() {
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
                //    createDocument(); // Belge oluşturma fonksiyonu
                    break;
                case 3:
                //   documents(); // Belgeler ile ilgili fonksiyon
                    break;
                case 4:
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
        } while (choice != 4);
        return true;
    }

   
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
                  //  deleteCase();
                    break;
                case 3:
                  //  incorrectDeletionCase();
                    break;
                case 4:
                  //  currentCases();
                    break;
                case 5:
                  //  caseDates();
                    break;
                case 6:
                 //   displayPlaintiffs();
                    break;
                case 7:
                  //  casesThatMayBeConnectedMenu();
                    break;
                case 8:
                 //   casesThatMayAriseMenu();
                    break;
                case 9:
                  //  sortByID();
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
    public static int hashFunction(int caseID, int TABLE_SIZE) {
        return caseID % TABLE_SIZE;
    }
    public static int TABLE_SIZE = 10000; // Sabit bir değer
    
    public static void initializeHashTable(int[] hashTableProbing, int TABLE_SIZE) {
        for (int i = 0; i < TABLE_SIZE; i++) {
            hashTableProbing[i] = -1;  // -1 indicates that the slot is empty
        } }
    

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

        out.println("Hash table is full. Cannot insert case ID: " + caseID);
        return false;
        
    }



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

        out.println("Hash table is full. Cannot insert case ID: " + caseID);
        return false;
    }


    // Hash fonksiyonu
    private static int hashFunction(int caseID) {
        return caseID % TABLE_SIZE;
    }
   
     public static int[] hashTableProbing = new int[TABLE_SIZE]; // Hash table için dizi
		
     public static int doubleHashing(int caseID, int attempt) {
         int primaryHash = hashFunction(caseID);       // Birinci hash fonksiyonu
         int secondaryHash = secondHashFunction(caseID); // İkinci hash fonksiyonu
         return (primaryHash + attempt * secondaryHash) % TABLE_SIZE; // Double hashing
     }
     
     public static boolean linearProbing(int caseID) {
    	    int index = hashFunction(caseID); // Başlangıç indeksi
    	    int i = 0;

    	    // Case ID'yi uygun yere yerleştir
    	    hashTableProbing[(index + i) % TABLE_SIZE] = caseID;

    	    // Debug çıktısı
    	    out.printf("Case ID: %d ----- Inserted at Index: %d (linear probing)%n", caseID, (index + i) % TABLE_SIZE);

    	    return true;
    	}

     
     public static int secondHashFunction(int caseID) {
         return 7 - (caseID % 7); // Double hashing için ikinci hash fonksiyonu
     }
     
     public static boolean doubleHashingInsert(int caseID) {
         int index = hashFunction(caseID); // İlk hash fonksiyonu ile başlangıç indeksi
         int stepSize = secondHashFunction(caseID); // İkinci hash fonksiyonu ile adım boyutu
         int i = 0;

         // Boş bir yer bulana kadar ilerle
         while (hashTableProbing[(index + i * stepSize) % TABLE_SIZE] != -1) {
             i++; // Bir sonraki adımı dene
             if (i >= TABLE_SIZE) {
                 out.println("Hash table is full. Cannot insert case ID: " + caseID);
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
    
     public static boolean isHashTableFull() {
         for (int i = 0; i < TABLE_SIZE; i++) {
             if (hashTableProbing[i] == -1) {
                 return false; // Tablo henüz dolu değil
             }
         }
         return true; // Tablodaki tüm slotlar dolu
     
     }
     public static void insertIntoHashTable(LegalCase newCase) {
    	    int index = hashFunction(newCase.caseID); // Hash fonksiyonu ile index hesaplanır

    	    // Eğer hash tablosunda bu index'e denk gelen bir değer varsa çakışmayı kontrol et
    	    while (hashTableProbing[index] != -1) {
    	        index = (index + 1) % TABLE_SIZE; // Linear probing ile sonraki indeksi kontrol et
    	    }

    	    // Uygun boş index bulundu, yeni davayı tabloya ekle
    	    hashTableProbing[index] = newCase.caseID;

    	    
    	}

     public static boolean addCase() {
    	    clearScreen();
    	    initializeHashTable(hashTableProbing, TABLE_SIZE); // Tabloyu sıfırla (opsiyonel)

    	    String fileName = "cases.bin";

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
    	        caseID = rand.nextInt(900) + 100;

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
    	            default:
    	                out.println("Invalid strategy choice! Defaulting to Quadratic Probing.");
    	                inserted = quadraticProbing(caseID);
    	                break;
    	        }
    	        attempt++;
    	      } while (!inserted && attempt < LegalCase.MAX_ATTEMPTS);

    	    if (!inserted) {
    	        out.println("Failed to insert Case ID after maximum attempts.");
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

    	        String[] parts = date.split("/");
    	        if (parts.length == 3 && isValidDate(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]))) {
    	            break;
    	        } else {
    	            out.println("Invalid date format! Please enter the date in dd/mm/yyyy format.");
    	        }
    	    }

    	    int[] scheduledDate = new int[3];
    	    findNextAvailableDate(sparseMatrix, scheduledDate);

    	    if (scheduledDate[0] == 0 && scheduledDate[1] == 0 && scheduledDate[2] == 0) {
    	        out.println("No available date for scheduling.");
    	        return false;
    	    }

    	    String scheduled = String.format("%02d/%02d/%d", scheduledDate[0], scheduledDate[1], scheduledDate[2]);

    	    LegalCase newCase = new LegalCase(caseID, caseTitle, plaintiff, defendant, caseType, date, scheduled);

    	    // Save case to file
    	    try (FileOutputStream fos = new FileOutputStream(fileName, true)) {
    	        
    	    } catch (IOException e) {
    	        System.out.println("Debug: Failed to create FileOutputStream. Error: " + e.getMessage());
    	    }


    	    // Insert into hash table
    	    insertIntoHashTable(newCase);

    	    out.println("\nScheduled Hearing Date: " + scheduled);
    	    out.print("Please press Enter to return to the Case Tracking Menu...");
    	    scanner.nextLine();

    	    return true;
    	}
}


    
  




   


 

