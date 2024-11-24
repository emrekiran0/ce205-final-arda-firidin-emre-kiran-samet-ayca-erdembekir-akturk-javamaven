package com.arda.erdem.samet.emre.legalcase;

import static java.lang.System.out;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

/**
 * @class LegalCase
 * @brief This class represents a LegalCase that performs mathematical operations.
 * @details The LegalCase class provides methods to perform mathematical operations such as addition, subtraction, multiplication, and division.
 * It also supports logging functionality using the logger object.
 * @author ugur.coruh
 */

public class LegalCase implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final int MAX_ATTEMPTS = 1000;
	private int caseID;
    private String title;
    private String type;
    private String plaintiff;
    private String defendant;
    private String status;
    private String hearingDate;
	private LegalCase next;
	private Object date;
	private Object scheduled;

    // Constructor
    public LegalCase(int caseID, String title, String type, String plaintiff, String defendant, String status, String hearingDate) {
        this.caseID = caseID;
        this.title = title;
        this.type = type;
        this.plaintiff = plaintiff;
        this.defendant = defendant;
        this.status = status;
        this.hearingDate = hearingDate;
        this.date = date;
        this.scheduled = scheduled;}
	
    private static final Scanner scanner = new Scanner(System.in);

    static int MAX_YEARS = 10;
    static int MAX_MONTHS = 12;
    static int MAX_DAYS = 31;
    
    public static final String FILE_NAME = "cases.bin";
    
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
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                return choice;
            } else {
                out.println("Invalid choice! Please try again.");
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
                	currentCases();
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

     public static class AppendableObjectOutputStream extends ObjectOutputStream {
    	    public AppendableObjectOutputStream(OutputStream out) throws IOException {
    	        super(out);
    	    }

    	    @Override
    	    protected void writeStreamHeader() throws IOException {
    	        // Header'ı yazmaz, böylece dosyaya ekleme yapılabilir
    	        reset();
    	    }
    	}
     

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

             System.out.println("Case has been appended successfully to " + fileName);
         } catch (IOException e) {
             System.out.println("Error appending case to file: " + e.getMessage());
         }
     }

     
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

    	    // Dosyaya yazma işlemini gerçekleştiren fonksiyonu çağırıyoruz
    	    appendCaseFile(newCase, FILE_NAME);

    	    // Insert into hash table
    	    insertIntoHashTable(newCase);

    	    out.println("\nScheduled Hearing Date: " + scheduled);
    	    out.print("Please press Enter to return to the Case Tracking Menu...");
    	    scanner.nextLine();

    	    return true;
    	}

     
     public static class CaseNode {
    	    LegalCase data;
    	    CaseNode next;
    	    CaseNode prev;

    	    public CaseNode(LegalCase data) {
    	        this.data = data;
    	        this.next = null;
    	        this.prev = null;
    	    }
    	}

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
    	    }
    	}

     
     public static boolean printCase(CaseNode node) {
    	    System.out.println("\nCase ID: " + node.data.caseID);
    	    System.out.println("Case Title: " + node.data.title);
    	    System.out.println("Plaintiff: " + node.data.plaintiff);
    	    System.out.println("Defendant: " + node.data.defendant);
    	    System.out.println("Case Type: " + node.data.type);
    	    System.out.println("Beginning Date: " + node.data.date);
    	    System.out.println("Scheduled Hearing Date: " + node.data.scheduled);
    	    System.out.println("-----------------------------");
    	    return true;
    	}

     
     
     public static boolean currentCases() {
    	    Scanner scanner = new Scanner(System.in);

    	    CaseNode head = null;
    	    CaseNode currentNode = null;

    	    File file = new File(FILE_NAME);
    	    if (!file.exists()) {
    	        System.out.println("Error: File does not exist. Please add cases first.");
    	        return false;
    	    }

    	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
    	        System.out.println("\n===== Current Cases =====\n");

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

    	            System.out.println("Options:");
    	            if (currentNode.prev != null) {
    	                System.out.println("P - Previous case");
    	            }
    	            if (currentNode.next != null) {
    	                System.out.println("N - Next case");
    	            }
    	            System.out.println("Q - Quit");
    	            System.out.print("Enter your choice: ");
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
    	                System.out.println("Invalid choice. Please try again.");
    	            }
    	        }

    	    } catch (IOException | ClassNotFoundException e) {
    	        System.out.println("Error reading cases: " + e.getClass().getName() + ": " + e.getMessage());
    	    }

    	    return true;
    	}}


    
  




   


 

