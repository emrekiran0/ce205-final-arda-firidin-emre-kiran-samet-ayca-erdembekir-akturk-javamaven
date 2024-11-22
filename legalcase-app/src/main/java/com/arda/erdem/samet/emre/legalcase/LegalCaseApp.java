/**

@file LegalCaseApp.java
@brief This file serves as the main application file for the LegalCase App.
@details This file contains the entry point of the application, which is the main method. It initializes the necessary components and executes the LegalCase App.
*/
/**

@package com.arda.erdem.samet.emre.legalcase
@brief The com.arda.erdem.samet.emre.legalcase package contains all the classes and files related to the LegalCase App.
*/
package com.arda.erdem.samet.emre.legalcase;

/**
 *
 * @class LegalCaseApp
 * @brief This class represents the main application class for the LegalCase
 *        App.
 * @details The LegalCaseApp class provides the entry point for the LegalCase
 *          App. It initializes the necessary components, performs calculations,
 *          and handles exceptions.
 * @author ugur.coruh
 */
public class LegalCaseApp {
 

  /**
   * @brief The main entry point of the LegalCase App.
   *
   * @details The main method is the starting point of the LegalCase App. It
   *          initializes the logger, performs logging, displays a greeting
   *          message, and handles user input.
   *
   * @param args The command-line arguments passed to the application.
   */
  public static void main(String[] args) {
	  LegalCase legalCase = new LegalCase(); // LegalCase sınıfından bir nesne oluştur
      legalCase.mainMenu(); // Ana menüyü başlatmak için mainMenu metodunu çağır
  }

}
