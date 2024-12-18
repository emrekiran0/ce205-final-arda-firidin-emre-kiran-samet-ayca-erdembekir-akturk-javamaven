package com.arda.erdem.samet.emre.legalcase;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUtility {

    public static final String TEST_CASE_FILE = "test_cases.bin"; // Test dosyasının adı

    /**
     * Kullanıcı girişlerini simüle eder.
     *
     * @param userInput Simüle edilecek kullanıcı girişi
     */
    public static void simulateUserInput(String userInput) {
        try (FileWriter fileWriter = new FileWriter("test_input.txt")) { // Giriş dosyası oluştur
            fileWriter.write(userInput); // Kullanıcı girişini dosyaya yaz
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test davaları dosyasını oluşturur.
     */
    public static void createTestCaseFile() {
        LegalCase[] cases = {
            new LegalCase(1, "Case1", "Plaintiff1", "Defendant1", "Type1", "01/01/2023", "05/05/2023"),
            new LegalCase(2, "Case2", "Plaintiff2", "Defendant2", "Type2", "02/01/2023", "06/06/2023"),
            new LegalCase(3, "Case3", "Plaintiff3", "Defendant3", "Type3", "03/01/2023", "07/07/2023")
        };

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_CASE_FILE))) {
            for (LegalCase legalCase : cases) {
                oos.writeObject(legalCase); // Nesneleri dosyaya yaz
            }
            System.out.println("Test cases file '" + TEST_CASE_FILE + "' created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
