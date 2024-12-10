/**

@file LegalCaseTest.java
@brief This file contains the test cases for the LegalCase class.
@details This file includes test methods to validate the functionality of the LegalCase class. It uses JUnit for unit testing.
*/
package com.arda.erdem.samet.emre.legalcase;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


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
  public void testMainMenuCaseTrackingto1() {
      String input = "1\n10\n4\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = LegalCase.mainMenu();
      assertTrue(result);

  }
  
  @Test
  public void testMainMenuCaseTrackingtoexit() {
      String input = "4\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      boolean result = LegalCase.mainMenu();
      assertTrue(result);

  }


  @Test
  public void testMainMenuValidCreateDocumentWithID() {
      // Kullanıcı "Create Document" seçeneğini seçiyor, bir ID giriyor ve çıkış yapıyor
      String input = "2\n12345\n4\n"; // Menüde "2" seçimi (Create Document), ardından ID (12345) ve "4" (Exit)
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner testScanner = new Scanner(System.in);
      legalcase = new LegalCase(testScanner, new PrintStream(outContent));
      
      boolean result = LegalCase.mainMenu();
      assertTrue(result);}


  /*@Test
  public void testMainMenuDocuments() {
      String input = "3\n4\n"; // Kullanıcı "Documents" ve ardından "Exit" seçiyor
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      boolean result = LegalCase.mainMenu();
      assertTrue(result);

      // Çıkış mesajlarını kontrol et
      String output = outContent.toString();
      assertTrue(output.contains("===== Legal Case Tracker Menu ====="));
      assertTrue(output.contains("Documents"));
      assertTrue(output.contains("Exiting..."));
  }

  @Test
  public void testMainMenuInvalidChoice() {
      String input = "5\n4\n\n"; // Kullanıcı geçersiz bir seçim yapıyor ve ardından "Exit" seçiyor
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      boolean result = LegalCase.mainMenu();
      assertTrue(result);

      // Çıkış mesajlarını kontrol et
      String output = outContent.toString();
      assertTrue(output.contains("Invalid choice! Please press a key to continue"));
      assertTrue(output.contains("Exiting..."));
  }*/

}

