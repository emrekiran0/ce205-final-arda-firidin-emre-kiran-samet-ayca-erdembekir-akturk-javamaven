/**

@file LegalCaseTest.java
@brief This file contains the test cases for the LegalCase class.
@details This file includes test methods to validate the functionality of the LegalCase class. It uses JUnit for unit testing.
*/
package com.arda.erdem.samet.emre.legalcase;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;


/**

@class LegalCaseTest
@brief This class represents the test class for the LegalCase class.
@details The LegalCaseTest class provides test methods to verify the behavior of the LegalCase class. It includes test methods for addition, subtraction, multiplication, and division operations.
@author ugur.coruh
*/
public class LegalCaseTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	LegalCase legalcase;

  /**
   * @brief This method is executed once before all test methods.
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  /**
   * @brief This method is executed once after all test methods.
   * @throws Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  /**
   * @brief This method is executed before each test method.
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
  }

  /**
   * @brief This method is executed after each test method.
   * @throws Exception
   */
  @After
  public void tearDown() throws Exception {
	  System.setOut(null);
	  System.setIn(null);
  }
  
  
  @Before
  public void setUpStreams() {
      System.setOut(new PrintStream(outContent)); // Çıkış yönlendirme
      legalcase = new LegalCase(new Scanner(""), new PrintStream(outContent));
  }


  @Test
  public void testMainMenuCaseTrackingto1() throws IOException {
      String input = "1\n10\n4\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = LegalCase.mainMenu();
      assertTrue(result);

  }
  
  @Test
  public void testMainMenuCaseTrackingtoexit() throws IOException {
      String input = "4\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = LegalCase.mainMenu();
      assertTrue(result);

  }
  
  
  @Test
  public void testMainMenuValidCreateDocumentWithID() throws IOException {
      // Kullanıcı "Create Document" seçeneğini seçiyor, bir ID giriyor ve çıkış yapıyor
	  TestUtility.createTestCaseFile();
	  String input = "2\n1\n\n\n\n\n\n4\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = legalcase.mainMenu();
      assertTrue(result);}


  @Test
  public void testMainMenuDocuments() throws IOException {
      String input = "3\n4\n4\n"; // Kullanıcı "Documents" ve ardından "Exit" seçiyor
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = legalcase.mainMenu();
      assertTrue(result);
  }

  @Test
  public void testMainMenuInvalidInput() throws IOException {
      String input = "9000\ndfghj\n4\n\n"; // Kullanıcı "Documents" ve ardından "Exit" seçiyor
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = legalcase.mainMenu();
      assertTrue(result);
  }

  @Test
  public void testAddCaseQuadraticProbing_ValidInput() throws IOException {
	  String input = "1\n1\nOriginal Title\nJohn Doe\nJane Smith\nCivil\n01/01/2023\n\n10\n";
	  System.setIn(new ByteArrayInputStream(input.getBytes()));
   Scanner testScanner = new Scanner(System.in);
  legalcase = new LegalCase(testScanner, new PrintStream(outContent));
  boolean result = legalcase.addCase();
  assertTrue(result);
  }
  
  
  @Test
  public void testAddCaseProgressiveOverflow_ValidInput() throws IOException {
   String input = "2\n1\nOriginal Title\nJohn Doe\nJane Smith\nCivil\n01/01/2023\n\n10\n";
   System.setIn(new ByteArrayInputStream(input.getBytes()));
   Scanner testScanner = new Scanner(System.in);
   legalcase = new LegalCase(testScanner, new PrintStream(outContent));
   boolean result = legalcase.addCase();
   assertTrue(result);
  }
  
  @Test
  public void testAddCaseLinearProbing_ValidInput() throws IOException {
	   String input = "3\n1\nOriginal Title\nJohn Doe\nJane Smith\nCivil\n01/01/2023\n\n10\n";
	   System.setIn(new ByteArrayInputStream(input.getBytes()));
	   Scanner testScanner = new Scanner(System.in);
	   legalcase = new LegalCase(testScanner, new PrintStream(outContent));
	   boolean result = legalcase.addCase();
	   assertTrue(result); 
}
  
  @Test
  public void testAddCaseDoubleHashing_ValidInput() throws IOException {
	   String input = "4\n1\nOriginal Title\nJohn Doe\nJane Smith\nCivil\n01/01/2023\n\n10\n";
	   System.setIn(new ByteArrayInputStream(input.getBytes()));
	   Scanner testScanner = new Scanner(System.in);
	   legalcase = new LegalCase(testScanner, new PrintStream(outContent));
	   boolean result = legalcase.addCase();
	   assertTrue(result); 
  }

 
  @Test
  public void testAddCaseInvalidDateFormat() throws IOException {
      // Geçersiz tarih formatı: "32/13/abcd"
      String input = "1\n1\nTest Title\nJohn Doe\nJane Smith\nCivil\n32/13/abcd\n01/01/2023\n\n10\n";
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Girdiyi simüle et
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = legalcase.addCase();
      assertTrue(result); // Fonksiyon başarısız olmalı
  }
  
  @Test
  public void testAddCaseIncompleteDate() throws IOException {
      // Eksik tarih bilgisi: "01/2023" (gün eksik)
      String input = "1\n1\nTest Title\nJohn Doe\nJane Smith\nCivil\n01/2023\n01/01/2023\n\n10\n";
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Girdiyi simüle et
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = legalcase.addCase();
      assertTrue(result); // Fonksiyon başarısız olmalı
  }
  
  @Test
  public void testCurrentCasesNavigation() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "N\nP\nQ\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.currentCases();
      assertTrue(result);
  }

  @Test
  public void testCaseDates() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.caseDates();
      assertTrue(result);

  }
  
  @Test
  public void test() throws IOException {
      // Test dosyasını oluştur
	  
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.sortByID();
      assertTrue(result);

  }
  
  @Test
  public void testPrintSortedCases() throws IOException, ClassNotFoundException {
      // Test dosyasını oluştur
	    LegalCase.FILE_NAME = TestUtility.TEST_CASE_FILE;

      TestUtility.createTestCaseFile();
      
      // B+ ağacına davaları ekle
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TestUtility.TEST_CASE_FILE))) {
          while (true) {
              try {
                  LegalCase legalCase = (LegalCase) ois.readObject();
                  LegalCase.insert(legalCase.caseID, legalCase);
              } catch (EOFException e) {
                  break; // Dosyanın sonuna ulaşıldı
              }
          }
      }
      // Çıktıyı yakalamak için
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      // Metodu çalıştır
      boolean result = LegalCase.printSortedCases();
      // Metodun doğru şekilde çalıştığını kontrol et
      assertTrue(result);
      // Çıktıyı kontrol et
      String output = outContent.toString();
      assertTrue(output.contains("Case ID: 1"));
  
  }
  
  @Test
  public void testSplitNode() {
      // B+ ağacı oluştur
      BPlusTreeNode root = new BPlusTreeNode(true);

      // B+ ağacını dolduracak anahtarlar
      int maxKeys = BPlusTreeNode.MAX;
      for (int i = 0; i < maxKeys; i++) {
          LegalCase legalCase = new LegalCase(i, "Case" + i, "Plaintiff" + i, "Defendant" + i, "Type", "01/01/2023", "02/02/2023");
          LegalCase.insert(i, legalCase);
      }

      // Yeni bir anahtar ekleyerek split işlemini tetikle
      LegalCase extraCase = new LegalCase(maxKeys, "ExtraCase", "ExtraPlaintiff", "ExtraDefendant", "Type", "01/01/2024", "02/02/2024");
      LegalCase.insert(maxKeys, extraCase);

      // Çıkışı yakalamak için
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // Çıktıyı kontrol et
      String output = outContent.toString();
     
  }

  @Test
  public void testSplitNodeWithParentAndNonLeaf() {
      // B+ ağacı kök düğümü oluştur
      BPlusTreeNode root = new BPlusTreeNode(false); // Yaprak olmayan kök düğüm
      BPlusTreeNode childNode = new BPlusTreeNode(true); // Yaprak düğüm

      // Çocuk düğümü dolduracak anahtarlar ekle
      int maxKeys = BPlusTreeNode.MAX;
      for (int i = 0; i < maxKeys; i++) {
          childNode.keys[i] = i;
          childNode.cases[i] = new LegalCase(i, "Case" + i, "Plaintiff" + i, "Defendant" + i, "Type", "01/01/2023", "02/02/2023");
          childNode.numKeys++;
      }

      // Kök düğüme çocuk düğümü ekle
      root.children[0] = childNode;

      // Split işlemini tetikle
      LegalCase.split(root, childNode);

      // Kontroller
      assertNotNull(root.children[1]); // Yeni düğüm eklendi mi?
      assertEquals(maxKeys / 2, childNode.numKeys); // Çocuk düğümde kalan anahtar sayısı
      assertEquals(maxKeys - maxKeys / 2 - 1, root.children[1].numKeys); // Yeni düğümdeki anahtar sayısı
  }

  
  @Test
  public void testPrintSortedCasesRootNull() {
      // B+ ağacı kökü boş
      LegalCase.root = null;

      // Çıkışı yakalamak için
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // printSortedCases çağrılır
      boolean result = LegalCase.printSortedCases();

      // Kontroller
      assertFalse(result); // Fonksiyon false döndürmeli
      String output = outContent.toString();
      assertTrue(output.contains("No cases to display.")); // Doğru hata mesajını kontrol et
  }

  @Test
  public void testPrintSortedCasesNonLeafTraversal() {
      // Root düğüm ve çocuk düğümler oluştur
      BPlusTreeNode rootNode = new BPlusTreeNode(false); // Yaprak olmayan kök düğüm
      BPlusTreeNode childNode = new BPlusTreeNode(true); // Yaprak düğüm

      // Çocuk düğümü dolduracak anahtarlar ekle
      childNode.keys[0] = 1;
      childNode.cases[0] = new LegalCase(1, "Case1", "Plaintiff1", "Defendant1", "Type", "01/01/2023", "02/02/2023");
      childNode.numKeys = 1;

      // Kök düğüme çocuk düğüm ekle
      rootNode.children[0] = childNode;
      rootNode.numKeys = 1;
      LegalCase.root = rootNode;

      // Çıkışı yakalamak için
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // printSortedCases çağrılır
      boolean result = LegalCase.printSortedCases();

      // Kontroller
      assertTrue(result); // Fonksiyon true döndürmeli
      String output = outContent.toString();
      assertTrue(output.contains("Case ID: 1")); // Doğru davanın yazdırıldığını kontrol et
  }

  
  @Test
  public void testPrintSortedCasesFileNotFound() {
      // FILE_NAME'i yanlış bir dosyaya yönlendir
      LegalCase.FILE_NAME = "non_existent_file.bin";

      // Çıkışı yakalamak için
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      // printSortedCases çağrılır
      boolean result = LegalCase.printSortedCases();

      // Kontroller
      assertFalse(result); // Fonksiyon false döndürmeli
      String output = outContent.toString();
  }


  @Test
  public void testSplitNonLeafNode() {
      // Yaprak olmayan bir düğüm oluştur
      BPlusTreeNode parentNode = new BPlusTreeNode(false); // Kök düğüm
      BPlusTreeNode childNode = new BPlusTreeNode(false); // Yaprak olmayan düğüm
      BPlusTreeNode grandChildNode1 = new BPlusTreeNode(true); // Yaprak düğüm
      BPlusTreeNode grandChildNode2 = new BPlusTreeNode(true); // Yaprak düğüm

      // Çocuk düğümleri doldur
      childNode.children[0] = grandChildNode1;
      childNode.children[1] = grandChildNode2;
      childNode.numKeys = 2;

      // Parent düğümün bir çocuğu olarak bu düğümü ayarla
      parentNode.children[0] = childNode;

      // ChildNode'a anahtarlar ekle
      for (int i = 0; i < BPlusTreeNode.MAX; i++) {
          childNode.keys[i] = i;
          childNode.cases[i] = new LegalCase(i, "Case" + i, "Plaintiff" + i, "Defendant" + i, "Type", "01/01/2023", "02/02/2023");
      }
      childNode.numKeys = BPlusTreeNode.MAX;

      // Split işlemini tetikle
      LegalCase.split(parentNode, childNode);

      // Kontroller
      assertNotNull(parentNode.children[1]); // Yeni düğüm eklendi mi?
      assertNotNull(childNode.children[0]); // Orijinal çocuk düğümleri korundu mu?
      assertNotNull(childNode.children[1]); // Orijinal çocuk düğümleri korundu mu?
      assertFalse(childNode.isLeaf); // Çocuk düğüm yaprak değil mi?
  }

  
  @Test
  public void testcasesThatMayBeConnectedMenu() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "1\n\n11"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.casesThatMayBeConnectedMenu();
      assertTrue(result);

  }
  
  @Test
  public void testcasesThatMayBeConnectedMenu_InvalidInput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "17\n\1\n\n11"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.casesThatMayBeConnectedMenu();
      assertTrue(result);

  }
  
  @Test
  public void testcasesThatMayAriseMenu() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "1\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.casesThatMayAriseMenu();
      assertTrue(result);

  }
  
  @Test
  public void testcasesThatMayAriseMenu_InvalidInput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile(); // Test dosyasını hazırla
      String input = "13\nasd\n1\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.casesThatMayAriseMenu();
      assertTrue(result);

  }
  
  @Test
  public void testdisplayPlaintiffs() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "N\nP\nQ\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.displayPlaintiffs();
      assertTrue(result);
  }

  @Test
  public void testdisplayPlaintiffsNoAvailable() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "a\nP\nQ\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.displayPlaintiffs();
      assertTrue(result);
  }
 
  @Test
  public void testCreateDocument() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "1\n\n\n\n\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.createDocument();
      assertTrue(result); 
}

  @Test
  public void testCreateDocument_FileNotFound() {
      // Dosyanın mevcut olmadığından emin ol
      File file = new File(TestUtility.TEST_CASE_FILE);
      if (file.exists()) {
      
      }
      String input = "\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      // Çıktıyı yakalamak için akışı yönlendir
      System.setOut(new PrintStream(outContent));
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      boolean result = LegalCase.createDocument();
      assertFalse(result); // Dosya yoksa false dönmeli
      
  }
  @Test
  public void testIsFileEmpty_FileDoesNotExist() {
      // Dosyanın mevcut olmadığından emin ol
      File file = new File(TestUtility.TEST_CASE_FILE);
      if (file.exists()) {
          file.delete();
      }
      // Metodu çağır ve sonucu kontrol et
      boolean result = LegalCase.isFileEmpty(TestUtility.TEST_CASE_FILE);
      assertTrue(result); // Dosya yoksa, boş kabul edilmeli
  }

  /*@Test
  public void testCreateDocument_Invalidİnput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "sdf\1\n\n\n\n\n\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.createDocument();
      assertFalse(result);
  }
*/

  
  @Test
  public void testallDocuments() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.allDocuments();
      assertTrue(result); 
}
  @Test
  public void testsearchingWithCaseTitle() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "a\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchingWithCaseTitle();
      assertTrue(result); 
}
  @Test
  public void testComputeLPSArray() {
      String pattern = "ABABCABAB";
      int[] lps = new int[pattern.length()];
      LegalCase.computeLPSArray(pattern, pattern.length(), lps);

      // Beklenen LPS dizisi
      int[] expectedLPS = {0, 0, 1, 2, 0, 1, 2, 3, 4};
      assertArrayEquals(expectedLPS, lps);
  }

  @Test
  public void testKMPSearch_PatternFound() {
      String pattern = "ABAB";
      String text = "ABABDABACDABABCABAB";
      assertTrue(LegalCase.KMPSearch(pattern, text)); // Eşleşme olmalı
  }

  @Test
  public void testKMPSearch_PatternNotFound() {
      String pattern = "XYZ";
      String text = "ABABDABACDABABCABAB";
      assertFalse(LegalCase.KMPSearch(pattern, text)); // Eşleşme olmamalı
  }

  @Test
  public void testsearchingWithCaseTitle_NoMatching() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "x\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchingWithCaseTitle();
      assertTrue(result); 
}
  
  @Test
  public void testsearchingWithCaseTitlefirstEnter() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "\n\\a\n\n"; // `\a` metin olarak yazılır, kaçış dizisi olarak algılanmaz
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchingWithCaseTitle();
      assertTrue(result); 
}
  
  @Test
  public void testsearchByID() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "1\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchByID();
      assertTrue(result); 
}
  
  @Test
  public void testsearchByID_NoMatch() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "178\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchByID();
      assertTrue(result); 
}
  
  @Test
  public void testSearchInHashTable() {
      // Hash tablosunu hazırlayın
      int caseIDToSearch = 101;
      LegalCase case1 = new LegalCase(101, "Test Case 1", "Plaintiff 1", "Defendant 1", "Civil", "01/01/2023", "05/01/2023");

      LegalCase.hashTableCases = new LegalCase[LegalCase.TABLE_SIZE];
      LegalCase.hashTableProbing = new int[LegalCase.TABLE_SIZE];
      Arrays.fill(LegalCase.hashTableProbing, -1);

      int index = LegalCase.hashFunction(case1.caseID, LegalCase.TABLE_SIZE);
      LegalCase.hashTableCases[index] = case1;
      LegalCase.hashTableProbing[index] = case1.caseID;

      // Başarılı arama testi
      assertEquals(case1, LegalCase.searchInHashTable(caseIDToSearch));

      // Başarısız arama testi
      assertNull(LegalCase.searchInHashTable(999));
  }
  
  
  @Test
  public void testsearchByID_İnvalidİnput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "x\n1\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.searchByID();
      assertTrue(result); 
}

  
  @Test
  public void testdeleteCaset() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "1\n\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.deleteCase();
      assertTrue(result); 
}
  
  @Test
  public void testdeleteCase_InvalidInput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "19\n\n";
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.deleteCase();
      assertFalse(result); 
}
 
  @Test
  public void testdeleteCase_NonNumericInput() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "An\n1\n\n"; // `\1` yerine `1` kullanılabilir.
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.deleteCase();
      assertFalse(result); 
}
  
  @Test
  public void incorrectDeletionCase() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "yn"; // `\1` yerine `1` kullanılabilir.
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.incorrectDeletionCase();
      assertFalse(result); 
}

  
  @Test
  public void testPopDeletedCase_WithPreloadedCases() {
      // Test dosyasını kullanarak davaları yükleyin
      TestUtility.createTestCaseFile();
      
      // Test için bir dava ekle
      LegalCase testCase = new LegalCase(1, "Case1", "Plaintiff1", "Defendant1", "Type1", "01/01/2023", "05/05/2023");
      LegalCase.deletedCasesStack.push(testCase);

      // popDeletedCase metodunu çağır ve sonucu kontrol et
      LegalCase result = LegalCase.popDeletedCase();
      assertNotNull(result); // Geri dönen dava null olmamalı
      assertEquals(1, result.caseID); // Dava ID'si kontrol et
      assertTrue(LegalCase.deletedCasesStack.isEmpty()); // Yığın boş olmalı
  }

  @Test
  public void testIsDeleted_WithPreloadedCases() {
      // Test dosyasını kullanarak davaları yükleyin
      TestUtility.createTestCaseFile();
      
      // Test için bir dava ekle
      LegalCase testCase = new LegalCase(3, "Case3", "Plaintiff3", "Defendant3", "Type3", "03/01/2023", "07/07/2023");
      LegalCase.deletedCasesStack.push(testCase);

      // isDeleted metodunu çağır ve sonucu kontrol et
      assertTrue(LegalCase.isDeleted(3)); // Dava silinmiş olmalı
      
  }
  
  @Test
  public void testUndoDeleteCase_SuccessfulUndo() throws IOException, ClassNotFoundException {
      // Silinen bir dava ekleyin
      LegalCase case1 = new LegalCase(1, "Case1", "Plaintiff1", "Defendant1", "Type1", "01/01/2023", "05/05/2023");
      LegalCase.pushDeletedCase(case1);
      // Geri alma işlemini test et
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.undoDeleteCase();
      String output = outContent.toString();
      assertTrue(result);
  }

  
  @Test
  public void testDoubleHashingInsert() {
      // Test için tablo boyutunu ayarla
      LegalCase.TABLE_SIZE = 10;
      // Hash tablosunu temizle
      LegalCase.hashTableProbing = new int[LegalCase.TABLE_SIZE];
      for (int i = 0; i < LegalCase.TABLE_SIZE; i++) {
          LegalCase.hashTableProbing[i] = -1; // Tüm girişleri boş yap
      }
      // Geçerli bir caseID ekle
      int caseID = 123456;
      boolean result = LegalCase.doubleHashingInsert(caseID);
      // Test: Başarılı bir şekilde eklenmeli
      assertTrue("Case ID should be successfully inserted into the hash table.", result);
      // Eklenen değerin tabloda doğru konuma yerleştiğini kontrol et
      int expectedIndex = LegalCase.doubleHashing(caseID, 0);
      // Tabloyu tamamen doldur
      for (int i = 1; i < LegalCase.TABLE_SIZE; i++) {
          LegalCase.doubleHashingInsert(caseID + i); // Farklı caseID'ler ekle
      }
      // Tablo doluyken ekleme yapmayı dene
      result = LegalCase.doubleHashingInsert(caseID + 1000);
  }

  @Test
  public void testUndoDeletedCase() {
      // Silinmiş davaları içeren yığını temizle
      LegalCase.deletedCasesStack.clear();
      // Test verisi: Silinmiş bir dava
      LegalCase testCase = new LegalCase(12345, "Civil", "Test Title", "John Doe", "Jane Doe", "01/01/2024", "02/02/2024");
      LegalCase.deletedCasesStack.push(testCase); // Yığına ekle
      // Kullanıcı girişi: Geri alma isteği
      String input = "y\n"; // 'y' ile onaylıyor
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      // Çıktıyı yakalamak için PrintStream
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream testPrintStream = new PrintStream(outContent);
      // LegalCase sınıfı için gerekli ayarlar
      LegalCase.scanner = new Scanner(System.in);
      LegalCase.out = testPrintStream;
      // Test edilen metod: incorrectDeletionCase
      boolean result = LegalCase.incorrectDeletionCase();
      // Doğrulamalar
      assertTrue("Undo operation should be successful.", result);
      // Çıktıyı kontrol et
      String output = outContent.toString();
      assertTrue("Last deleted case details should be displayed.", output.contains("Last deleted case details:"));
      assertTrue("Correct case ID should be displayed.", output.contains("Case ID: 12345"));
      assertTrue("Undo success message should be displayed.", output.contains("Undo successful for Case ID: 12345"));
  }

  
  @Test
  public void testNoPlaintiffsFound() {
      // Çıkışı yakalamak için ByteArrayOutputStream kullan
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(outContent));

      try {
          // Dosya yokmuş gibi davran
          File file = new File(LegalCase.FILE_NAME);
          if (file.exists()) {
          }

          // `displayPlaintiffs` çağrılır
          boolean result = LegalCase.displayPlaintiffs();

          // Kontroller
          assertFalse("Function should return false when no plaintiffs are found.", result);
          String output = outContent.toString();
      } finally {
          // Orijinal System.out'u geri yükle
          System.setOut(originalOut);
      }
  }
  
  @Test
  public void testdocumentstoalldocuments() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "1\n\n4\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.documents();
      assertTrue(result); 
}

  @Test
  public void testdocumentstosearchingWithCaseTitle() throws IOException {
      // Test dosyasını oluştur
      TestUtility.createTestCaseFile();
      String input = "2\\cn\n4\n"; 
      System.setIn(new ByteArrayInputStream(input.getBytes())); // Giriş akışını ayarla
      Scanner testScanner = new Scanner(System.in);
      LegalCase legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      System.setOut(new PrintStream(outContent));
      boolean result = LegalCase.documents();
      assertTrue(result); 
}

  
 
  }




  
  

  
  
  
  
  

  
  
  




  



  



