# ⚖️ Legal Case Tracker

![Java](https://img.shields.io/badge/Java-8+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.6+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![License](https://img.shields.io/badge/License-GPL%20v3-blue?style=for-the-badge)
![Platform](https://img.shields.io/badge/Platform-Windows%20|%20macOS%20|%20Linux-lightgrey?style=for-the-badge)

**A comprehensive legal case management system built with advanced data structures and algorithms in Java.**

> 📚 Originally developed as a university project for the **CE205 – Data Structures** course at Recep Tayyip Erdoğan University (2024–2025 academic year).

---

## Overview

Legal Case Tracker is a console-based Java application designed for lawyers and law offices to efficiently manage legal proceedings. The project demonstrates real-world applications of fundamental and advanced data structures, including B+ Trees, Hash Tables, Huffman Coding, Graphs, and more.

### Key Features

| Feature | Description |
|---------|-------------|
| **Case Management** | Create, update, delete, and search legal cases |
| **Party Tracking** | Manage plaintiff and defendant information |
| **Hearing Scheduler** | Automatic hearing date scheduling via Sparse Matrix |
| **Secure Authentication** | Huffman-encoded credential storage |
| **Undo Support** | Restore deleted cases using a Stack-based undo system |
| **Persistent Storage** | File-based serialization for data persistence |

---

## Data Structures & Algorithms

A core goal of this project is applying data structures to solve real problems:

| Data Structure | Use Case | Time Complexity |
|----------------|----------|-----------------|
| **B+ Tree** | Ordered storage and retrieval of case records | O(log n) |
| **Hash Table** | Fast case lookup by ID (Quadratic Probing, Double Hashing) | O(1) average |
| **Huffman Tree** | Password encoding/decoding for authentication | O(n log n) |
| **Graph** (Adjacency List) | Modeling plaintiff–defendant relationship networks | O(V + E) |
| **Stack** | Undo mechanism for deleted case restoration | O(1) |
| **Min Heap** | Priority queue operations | O(log n) |
| **Sparse Matrix** | Hearing calendar management | O(1) access |
| **Custom Queue** | Operation ordering and processing | O(1) |

### Collision Resolution Techniques
- **Quadratic Probing** — Reduces primary clustering
- **Double Hashing** — Minimizes all forms of clustering
- **Progressive Overflow** (Linear Probing) — Simple fallback strategy

---

## Project Structure

```
legal-case-tracker-java/
├── legalcase-app/
│   ├── pom.xml                          # Maven build config
│   └── src/
│       ├── main/java/.../legalcase/
│       │   ├── LegalCaseApp.java        # Entry point
│       │   ├── LegalCase.java           # Core business logic
│       │   ├── BPlusTree.java           # B+ Tree implementation
│       │   ├── BPlusTreeNode.java       # B+ Tree node
│       │   ├── HuffmanTree.java         # Huffman encoding
│       │   ├── HuffmanNode.java         # Huffman tree node
│       │   ├── Graph.java               # Adjacency list graph
│       │   ├── GraphNode.java           # Graph node
│       │   ├── MinHeap.java             # Min-Heap / priority queue
│       │   ├── CustomQueue.java         # Custom queue implementation
│       │   ├── CaseNode.java            # Case linked-list node
│       │   ├── CaseTypeForSCC.java      # SCC case categorization
│       │   └── PlaintiffNode.java       # Plaintiff linked-list node
│       └── test/java/.../legalcase/
│           ├── LegalCaseAppTest.java
│           └── LegalCaseTest.java
├── .github/                             # CI/CD workflows
├── assets/                              # Badges & images
└── README.md
```

---

## Getting Started

### Prerequisites

- **Java JDK 8+** — [Download](https://adoptium.net/)
- **Apache Maven 3.6+** — [Download](https://maven.apache.org/download.cgi)
- **Git**

### Build & Run

```bash
# Clone the repository
git clone https://github.com/emrekiran0/legal-case-tracker-java.git

# Navigate to the app directory
cd legal-case-tracker-java/legalcase-app

# Build the project
mvn clean install

# Run the application
java -jar target/legalcase-app-1.0-SNAPSHOT.jar
```

---

## Testing & CI

- **Unit Tests**: JUnit 4 with comprehensive test coverage
- **Code Coverage**: JaCoCo reports
- **CI/CD**: GitHub Actions pipeline for automated build & test across Windows, macOS, and Linux

```bash
# Run tests
mvn test

# Generate coverage report
mvn jacoco:report
```

---

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 8+ | Core language |
| Apache Maven | Build automation & dependency management |
| JUnit 4 | Unit testing |
| JaCoCo | Code coverage analysis |
| SLF4J / Logback | Logging framework |
| GitHub Actions | CI/CD pipeline |
| Doxygen | API documentation generation |

---

## Skills Demonstrated

- Design and implementation of advanced data structures from scratch
- Hash table collision resolution strategies (Quadratic Probing, Double Hashing, Linear Probing)
- Tree-based data structures (B+ Tree, Huffman Tree)
- Graph algorithms and relationship modeling
- Object-oriented design principles
- Maven-based project management and build automation
- CI/CD pipeline setup with multi-platform support
- Unit testing and code coverage analysis

---

## Team

| Contributor | Role |
|------------|------|
| **Arda Fırıdın** | Developer & Designer |
| **Emre Kıran** | Developer |
| **Samet Ayça** | Developer |
| **Erdem Bekir Aktürk** | Developer |

---

## License

This project is licensed under the [GNU General Public License v3.0](LICENSE).

---

