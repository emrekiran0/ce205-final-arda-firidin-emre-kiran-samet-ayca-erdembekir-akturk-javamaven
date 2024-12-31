package com.arda.erdem.samet.emre.legalcase;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class TestUtility {

    public static final String TEST_CASE_FILE = "test_cases.bin"; 
    public static final String TEST_USER_FILE = "test_user.huff"; 
   

   
    public static void createTestCaseFile() {
        LegalCase[] cases = {
            new LegalCase(1, "Case1", "Plaintiff1", "Defendant1", "Type1", "01/01/2023", "05/05/2023"),
            new LegalCase(2, "Case2", "Plaintiff2", "Defendant2", "Type2", "02/01/2023", "06/06/2023"),
            new LegalCase(3, "Case3", "Plaintiff3", "Defendant3", "Type3", "03/01/2023", "07/07/2023")
        };

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEST_CASE_FILE))) {
            for (LegalCase legalCase : cases) {
                oos.writeObject(legalCase); 
            }
            System.out.println("Test cases file '" + TEST_CASE_FILE + "' created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

   
     
    public static void createTestUserFile(HuffmanTree huffmanTree) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_USER_FILE))) {
            String username = "testUser";
            String password = "testPassword";

            String encodedUsername = encodeWithHuffman(username, huffmanTree);
            String encodedPassword = encodeWithHuffman(password, huffmanTree);

            writer.write(encodedUsername + ":" + encodedPassword);
            writer.newLine();
            System.out.println("Test user file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  

    public static String encodeWithHuffman(String input, HuffmanTree huffmanTree) {
        StringBuilder encoded = new StringBuilder();
        Map<Character, String> huffmanCodes = huffmanTree.getHuffmanCodes();	

        for (char ch : input.toCharArray()) {
            String code = huffmanCodes.get(ch);
            if (code != null) {
                encoded.append(code);
            } else {
                throw new IllegalArgumentException("Character not found in Huffman codes: " + ch);
            }
        }

        return encoded.toString();
    }

    
}
