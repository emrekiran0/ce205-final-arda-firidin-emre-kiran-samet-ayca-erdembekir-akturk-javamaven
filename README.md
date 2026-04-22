# ⚖️ Legal Case Tracker

A console-based **Legal Case Management System** built in Java, designed as a university course final project for **CE205 — Data Structures**. The application manages legal cases end-to-end — from registration and scheduling to document storage — while demonstrating practical use of advanced data structures and algorithms.

> **Course:** CE205 Data Structures · Recep Tayyip Erdogan University  
> **Built with:** Java 8 · Apache Maven · JUnit 4 · JaCoCo

---

## ✨ Key Features

| Module | Description |
|---|---|
| **Case Management** | Add, delete, undo-delete (stack), and browse cases via a doubly linked list with forward/backward navigation |
| **Hearing Scheduler** | Automatically assigns next available court date using a 3D sparse matrix (year × month × day) |
| **Case Sorting** | Sort cases by ID with a **B+ Tree** or by hearing date with **Heap Sort** |
| **Related Case Discovery** | Find potentially connected case types using **BFS** on a graph of legal categories |
| **Case Prediction (SCC)** | Predict cases that may arise from a given type using **Strongly Connected Components** analysis |
| **Client Tracking** | Manage plaintiff records via a custom **singly linked list** |
| **Document Storage** | Create and retrieve legal documents persisted in binary format |
| **User Authentication** | Secure login with passwords encoded via **Huffman Coding** |

---

## 🏗️ Data Structures & Algorithms

This project implements the following from scratch (no external DS libraries):

### Data Structures
- **Hash Table** with four collision resolution strategies:
  - Linear Probing
  - Quadratic Probing
  - Progressive Overflow
  - Double Hashing
- **B+ Tree** — insertion, splitting, and in-order traversal for sorted case retrieval
- **Huffman Tree & MinHeap** — character frequency-based encoding for credential security
- **Sparse Matrix (3D Array)** — efficient hearing date availability tracking
- **Doubly Linked List** — case browsing with prev/next navigation
- **Singly Linked List** — plaintiff record management
- **Stack** — undo support for accidentally deleted cases
- **Graph (Adjacency List)** — models relationships between legal case categories
- **Custom Queue** — used internally for BFS traversal

### Algorithms
- **Heap Sort** — sorts cases by scheduled hearing dates
- **BFS (Breadth-First Search)** — discovers related case types through graph traversal
- **Strongly Connected Components** — identifies cyclically connected case type clusters
- **Huffman Encoding/Decoding** — compresses and decompresses user credentials

---

## 📁 Project Structure

```
├── legalcase-app/
│   ├── pom.xml                          # Maven build configuration
│   └── src/
│       ├── main/java/.../legalcase/
│       │   ├── LegalCaseApp.java        # Entry point
│       │   ├── LegalCase.java           # Core logic (~3100 lines)
│       │   ├── BPlusTree.java           # B+ Tree structure
│       │   ├── BPlusTreeNode.java       # B+ Tree node definition
│       │   ├── Graph.java               # Adjacency list graph
│       │   ├── GraphNode.java           # Graph node definition
│       │   ├── HuffmanTree.java         # Huffman encoding tree
│       │   ├── HuffmanNode.java         # Huffman tree node
│       │   ├── MinHeap.java             # Priority queue for Huffman
│       │   ├── CaseNode.java            # Doubly linked list node
│       │   ├── PlaintiffNode.java       # Singly linked list node
│       │   ├── CaseTypeForSCC.java      # Case type for SCC analysis
│       │   └── CustomQueue.java         # Queue for BFS
│       ├── test/java/.../legalcase/
│       │   ├── LegalCaseAppTest.java    # App-level integration tests
│       │   ├── LegalCaseTest.java       # Unit tests
│       │   └── TestUtility.java         # Test helpers
│       └── site/                        # Maven site resources
├── .github/workflows/
│   └── release.yml                      # CI/CD pipeline
├── assets/                              # Badges & screenshots
└── Doxyfile                             # Doxygen documentation config
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 8+** (JDK)
- **Apache Maven 3.6+**

### Build & Test

```bash
cd legalcase-app
mvn clean test package
```

### Run

```bash
java -jar legalcase-app/target/legalcase-app-1.0-SNAPSHOT.jar
```

### Generate Coverage Report

```bash
cd legalcase-app
mvn clean test
reportgenerator "-reports:target/site/jacoco/jacoco.xml" "-sourcedirs:src/main/java" "-targetdir:coveragereport" -reporttypes:Html
```

---

## 🧪 Testing

- **Framework:** JUnit 4  
- **Coverage:** JaCoCo (integrated in Maven lifecycle)  
- Tests cover core logic, application entry, and I/O redirection for stdin/stdout simulation

---

## ⚙️ CI/CD

The project includes a **GitHub Actions** workflow (`.github/workflows/release.yml`) that automates:
- Building across **Ubuntu**, **macOS**, and **Windows**
- Running tests and generating coverage reports
- Packaging release artifacts

![Ubuntu](assets/badge-ubuntu.svg) ![macOS](assets/badge-macos.svg) ![Windows](assets/badge-windows.svg)

---

## 📊 Architecture Overview

```
┌─────────────┐     ┌──────────────┐     ┌──────────────────┐
│  User Auth  │────▶│  Main Menu   │────▶│  Case Tracking   │
│  (Huffman)  │     │              │     │                  │
└─────────────┘     │              │     │  • Add (Hash)    │
                    │              │     │  • Delete (Stack) │
                    │              │     │  • Browse (DLL)   │
                    │              │     │  • Sort (B+ Tree) │
                    │              │     │  • Dates (Heap)   │
                    │              │     │  • Related (BFS)  │
                    │              │     │  • Predict (SCC)  │
                    │              │     └──────────────────┘
                    │              │
                    │              │────▶│  Documents        │
                    │              │     │  (Binary I/O)     │
                    └──────────────┘     └──────────────────┘
```

---

## 👥 Team

| Name | Role |
|---|---|
| **Arda Firidin** | Developer |
| **Emre Kıran** | Developer |
| **Samet Ayça** | Developer |
| **Erdem Bekir Aktürk** | Developer |

---

## 📄 License

This project is licensed under the terms specified in the [LICENSE](LICENSE) file.
