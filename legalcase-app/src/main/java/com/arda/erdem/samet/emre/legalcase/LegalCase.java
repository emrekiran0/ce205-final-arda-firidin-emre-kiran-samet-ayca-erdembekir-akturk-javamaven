package com.arda.erdem.samet.emre.legalcase;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.InputMismatchException;

/**
 * @class LegalCase
 * @brief This class represents a LegalCase that performs mathematical operations.
 * @details The LegalCase class provides methods to perform mathematical operations such as addition, subtraction, multiplication, and division.
 * It also supports logging functionality using the logger object.
 * @author ugur.coruh
 */

public class LegalCase implements Serializable {
    public static Scanner scanner;
    public static PrintStream out;
    private static final long serialVersionUID = 1L;
    private static Stack<LegalCase> deletedCasesStack = new Stack<>();


    private static final int MAX_ATTEMPTS = 1000;
    public int caseID;
    public static String title;
    public static String type;
    public String plaintiff;
    public String defendant;
	public String date;
	public String scheduled;


    // Constructor
    public LegalCase(String type, int caseID, String title, String defendant, String plaintiff, String scheduled, String date) {
        this.type = type;
    	this.caseID = caseID;
        this.title = title;
        this.defendant = defendant;
        this.plaintiff = plaintiff;
        this.scheduled = scheduled;
        this.date = date;
    }
	
    public LegalCase(Scanner scanner, PrintStream out) {
        this.scanner = scanner;
        this.out = out;
    }


	static int MAX_YEARS = 10;
    static int MAX_MONTHS = 12;
    static int MAX_DAYS = 31;
    
    public static final String FILE_NAME = "cases.bin";
    public static final String DOCUMENT_FILE_NAME = "documents.bin";

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
                scanner.nextLine(); // Tamponu temizlemek için ekleyelim
                return choice;
            } else {
                out.println("Invalid choice! Please try again.");
                scanner.nextLine(); // Tampondaki tüm kalan veriyi temizle
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

         } catch (IOException e) {
             out.println("Error appending case to file: " + e.getMessage());
         }
     }

     public static boolean isValidDateFormat(String date) {
    	    return date.matches("\\d{2}/\\d{2}/\\d{4}"); // Tarihin "dd/mm/yyyy" formatında olup olmadığını kontrol eder
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
    	                    }
    	                } catch (NumberFormatException e) {
    	                    out.println("Invalid date format! Please enter numbers only (dd/mm/yyyy).");
    	                }
    	            } else {
    	                out.println("Invalid date format! Please enter the date in dd/mm/yyyy format.");
    	            }
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

    	    LegalCase newCase = new LegalCase(type, caseID, title, defendant, plaintiff, scheduled, date);
    	    

    	    // Dosyaya yazma işlemini gerçekleştiren fonksiyonu çağırıyoruz
    	    appendCaseFile(newCase, FILE_NAME);

    	    // Insert into hash table
    	    insertIntoHashTable(newCase);

    	    out.println("\nScheduled Hearing Date: " + scheduled);
    	    out.print("Please press Enter to return to the Case Tracking Menu...");
    	    scanner.nextLine();

    	    return true;
    	}

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
    	        out.println("Error reading cases: " + e.getClass().getName() + ": " + e.getMessage());
    	    }

    	    return true;
    	}
    
     //Push deleted case to the stack
     public static void pushDeletedCase(LegalCase deletedCase) {
    deletedCasesStack.push(deletedCase);
     }

     // Pop a deleted case from the stack
     public static LegalCase popDeletedCase() {
    if (!deletedCasesStack.isEmpty()) {
        return deletedCasesStack.pop();
    } else {
        out.println("No deleted cases available to restore.");
        return null;
    }
}
     
     // Delete from hash table
    public static void deleteFromHashTable(int caseID) {
    int index = hashFunction(caseID);
    if (hashTableProbing[index] == caseID) {
        hashTableProbing[index] = -1; // Mark slot as empty
        out.println("Case ID " + caseID + " removed from hash table.");
    }
}
     //Delete case method
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

// View and optionally undo the last deleted case
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

// Check if a case is deleted
	public static boolean isDeleted(int caseID) {
    for (LegalCase deletedCase : deletedCasesStack) {
        if (deletedCase.caseID == caseID) {
            return true; // Silinmiş bir dava bulundu
        }
    }
    return false; // Silinmiş bir dava yok
}
   
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
        out.println("Error reading cases: " + e.getMessage());
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

// B+ Ağacının kök düğümü
private static BPlusTreeNode root;

// Yeni dava ekleme
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

// Bir düğüme anahtar ve dava ekleme
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

// Düğüm bölme işlemi
private static void split(BPlusTreeNode parent, BPlusTreeNode node) {
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

// Davaları sıralı olarak yazdırır
public static void printSortedCases() {
    if (root == null) {
        System.out.println("No cases to display.");
        return;
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
}

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
        System.out.println("Error reading cases: " + e.getMessage());
        return false;
    }

    // Ağacı sıralı şekilde yazdır
    System.out.println("\n===== Sorted Case Dates =====\n");
    LegalCase.printSortedCases();

    System.out.print("Please press Enter to return to the Case Tracking Menu...");
    scanner.nextLine();
    out.print(" ");
    scanner.nextLine();
    return true;
}

    // Dava türleri için sabit isim listesi
    static final String[] caseNames = {
        "Criminal", "Civil", "Commercial", "Administrative",
        "Divorce", "Custody", "Traffic", "Dismissal",
        "Compensation", "Inheritance", "Title deed"};

    // Kenar ekleme işlemi
    static void addEdge(Graph graph, int src, int dest) {
        GraphNode newNode = new GraphNode(dest);
        newNode.next = graph.adjLists[src];
        graph.adjLists[src] = newNode;

        newNode = new GraphNode(src);
        newNode.next = graph.adjLists[dest];
        graph.adjLists[dest] = newNode;
    }

    // BFS algoritması
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

    // Menü
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
    
        // Maksimum dava türü sayısı
        static final int MAX = 44;

        // Dava türleri listesi
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

        // Kenar ekleme fonksiyonu
        static void addEdge(int u, int v) {
            adj[u][v] = 1;
        }

        // Cases That May Arise Menüsü
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

            out.print("Please Make Your Choice (1-11): ");
            int caseChoice = scanner.nextInt();

            if (caseChoice >= 1 && caseChoice <= 11) {
                out.println("\nSelected Case Type: " + caseTypeSCC[(caseChoice - 1) * 4].name);
                out.println("\nCases That May Arise:");

                for (int i = (caseChoice - 1) * 4 + 1; i < MAX; i++) {
                    if (i % 4 == 0) break;
                    out.println("- " + caseTypeSCC[i].name);
                }
            } else {
                out.println("Invalid choice.");
            }

            out.println("\n\nPlease press Enter to return to the Case Tracking Menu...");
            try {
                System.in.read();
            } catch (Exception e) {
                out.println("Error reading input.");
            }

            return true;
        }

        public int getCaseID() {
            return caseID;
        }

        // Getter for plaintiff
        public String getPlaintiff() {
            return plaintiff;
        }
       
        private static PlaintiffNode XOR(PlaintiffNode a, PlaintiffNode b) {
            return (a == null ? b : (b == null ? a : new PlaintiffNode(null)));
        }

        public static PlaintiffNode addPlaintiffNode(PlaintiffNode head, LegalCase data) {
            PlaintiffNode newNode = new PlaintiffNode(data);
            newNode.xorLink = head;

            if (head != null) {
                head.xorLink = XOR(newNode, head.xorLink);
            }

            return newNode; // Yeni head düğümünü döndür
        }
        // Davacı bilgilerini yazdırma
        public static void printPlaintiff(PlaintiffNode node) {
            if (node == null || node.data == null) {
                out.println("No plaintiff found.");
                return;
            }
            out.println("Case ID: " + node.data.getCaseID());
            out.println("Plaintiff: " + node.data.getPlaintiff());
            out.println("-----------------------------");
        }

        // Davacı isimlerini ve dava ID'lerini ekrana çıktı veren fonksiyon
        // Davacı isimlerini ve dava ID'lerini ekrana çıktı veren fonksiyon
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

        private static void pause() {
            out.println("\nPress Enter to continue...");
            try {
                System.in.read();
            } catch (IOException ignored) {
            }
        }
        
        public static class LegalCaseDocument implements Serializable {
            private static final long serialVersionUID = 1L;
            int caseID;
            String title, plaintiff, defendant, winner, loser, decision, sentence;

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
                out.println("Error saving document: " + e.getMessage());
            }
			return false;
        }

        public static boolean createDocument() {
            clearScreen();

            File file = new File(FILE_NAME);
            if (!file.exists()) {
                out.println("Error: File not found!");
                
            }

            LegalCase selectedCase = null;
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
            }

            out.print("\nEnter Case ID to create a document for: ");
            int id = scanner.nextInt();

            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                while (true) {
                    try {
                        LegalCase currentCase = (LegalCase) ois.readObject();
                        if (currentCase.caseID == id) {
                            selectedCase = currentCase;
                            break;
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                out.println("Error reading cases: " + e.getMessage());
                
            }

            if (selectedCase == null) {
                out.println("Case ID not found!");
                out.println("Press Enter to return to the menu...");
                scanner.nextLine();
                scanner.nextLine();
               
            }

            clearScreen();
            out.println("\n===== Selected Case =====");
            out.println("Case ID: " + selectedCase.caseID);
            out.println("Title: " + selectedCase.title);
            out.println("Plaintiff: " + selectedCase.plaintiff);
            out.println("Defendant: " + selectedCase.defendant);
            out.println("Type: " + selectedCase.type);
            out.println("Date: " + selectedCase.date);
            out.println("Scheduled: " + selectedCase.scheduled);

            scanner.nextLine(); // Clear buffer
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
			return false;
        }

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
                out.println("Error reading document file: " + e.getMessage());
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
   
    // Compute LPS Array for KMP Algorithm
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

    // KMP Algorithm for Pattern Matching
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

    // Search for Case Titles in the Document File
    public static boolean searchingWithCaseTitle() {
        clearScreen();

        File file = new File("documents.bin");
        if (!file.exists()) {
            out.println("Error: Document file not found!");
            return false;
        }

        out.print("Enter the Case Title to search: ");
        String searchTitle = scanner.nextLine();

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
            out.println("Error reading document file: " + e.getMessage());
            return false;
        }

        if (!found) {
            out.println("No matching case title found.");
        }

        out.println("Press Enter to return to the menu...");
        scanner.nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler
        return true;
    }
   
 // Search for a case in the hash table
    public static LegalCase[] hashTableCases = new LegalCase[TABLE_SIZE]; // LegalCase nesnelerini tutar

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
    
    // Search for a case by ID
    public static boolean searchByID() {
        clearScreen();

        out.print("Enter Case ID to search: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Buffer temizleme

        // Hash table'da arama
        LegalCase foundCase = searchInHashTable(id);
        if (foundCase != null) {
            out.println("\n===== Case Found in Hash Table =====");
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
                out.println("Case ID " + id + " not found in both hash table and file.");
            }
        }

        out.println("Press Enter to return to the Case Tracking Menu...");
        scanner.nextLine(); // Kullanıcıdan Enter tuşuna basmasını bekler
        return true;
    }
    // Print the details of a LegalCase
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
    }}

    




   


 

