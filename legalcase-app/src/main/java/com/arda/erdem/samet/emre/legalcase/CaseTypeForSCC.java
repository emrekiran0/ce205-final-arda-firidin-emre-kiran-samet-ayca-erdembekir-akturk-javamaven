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
 * @example
 * ```java
 * CaseTypeForSCC caseType = new CaseTypeForSCC(1, "Divorce Cases");
 * System.out.println("Case Type ID: " + caseType.id + ", Name: " + caseType.name);
 * ```
 */
public class CaseTypeForSCC {
    int id;
    String name;

    public CaseTypeForSCC(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
