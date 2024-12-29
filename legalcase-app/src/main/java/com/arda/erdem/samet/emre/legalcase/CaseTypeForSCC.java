/**
 * @file CaseTypeForSCC.java
 * @brief Defines a case type for Strongly Connected Components (SCC) analysis.
 *
 * @details
 * This class represents a type of legal case used in SCC analysis. Each case 
 * type is uniquely identified by an ID and described with a name, enabling 
 * categorization and analysis of relationships between legal cases.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Designed for SCC analysis in the Legal Case Tracker application.
 */

package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents a case type used in Strongly Connected Components (SCC) analysis.
 * Each case type is identified by a unique ID and a descriptive name.
 *
 * @fields
 * - `id` (int): The unique identifier for the case type.
 * - `name` (String): The name or description of the case type.
 *
 * @constructor
 * - `CaseTypeForSCC(int id, String name)`: Initializes a case type with the specified ID and name.
 *   @param id The unique identifier for the case type.
 *   @param name The name or description of the case type.
 *
 *
 */
public class CaseTypeForSCC {
    int id;
    String name;

    /**
     * @brief Constructor for creating a CaseTypeForSCC instance.
     * 
     * @details Initializes a CaseTypeForSCC object with a unique identifier and a name 
     * representing a specific type of legal case.
     * 
     * @param id   The unique identifier for the case type.
     * @param name The name or description of the case type.
     */
    public CaseTypeForSCC(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
