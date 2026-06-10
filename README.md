# DES Encryption System

A pure Java implementation of the **Data Encryption Standard (DES)** algorithm, built from scratch without relying on any external cryptographic libraries for the core cipher logic. Developed as part of the CSEC2350 coursework.

---

## Features

- **Encrypt & Decrypt** — Full DES cipher supporting ECB mode with PKCS-style padding
- **Key Expansion Visualization** — Step-by-step display of all 16 subkeys generated from the master key (PC-1, shifts, PC-2)
- **Encryption Visualization** — Round-by-round trace of the Feistel network (E-expansion, XOR, S-box substitution, P-box permutation)
- **Random Key Generation** — Generate secure 64-bit DES keys via Java's `KeyGenerator` (JCA) or `SecureRandom`
- **Flexible Input** — Accept plaintext/ciphertext and keys as manual HEX, ASCII/plaintext, or from a file
- **Subkey Export** — Save all 16 round subkeys to a file for inspection

---

## Project Structure

```
DESproject/
├── src/
│   ├── module-info.java
│   └── pckg/
│       ├── DES.java       # Core DES algorithm (IP, FP, E, S-boxes, P, PC1, PC2, Feistel rounds)
│       ├── main.java      # Interactive CLI menu
│       └── methods.java   # Utility helpers (hex/binary/ASCII conversions, file I/O, validation)
├── bin/                   # Compiled .class files
├── key.txt                # Sample key file
└── subkey.txt             # Sample subkey output
```

---

## Getting Started

### Prerequisites

- Java 11 or higher
- An IDE such as Eclipse or IntelliJ, **or** the JDK command-line tools

### Build & Run (Command Line)

```bash
# Compile
javac -d bin src/module-info.java src/pckg/*.java

# Run
java -cp bin pckg.main
```

### Run in Eclipse

1. Import the project: **File → Import → Existing Projects into Workspace**
2. Select the `DESproject` folder
3. Run `main.java` as a Java Application

---

## Usage

When you run the program, you are presented with an interactive menu:

```
==========================================
1. Key Expansion (Show Subkeys)
2. Encrypt Message
3. Decrypt Message
4. Generate Random DES Key
5. Visualize Encryption Process (toggle)
6. Exit
==========================================
```

### Encrypt a message

1. Select **2. Encrypt Message**
2. Enter plaintext as HEX, ASCII, or load from a file
3. Enter a 64-bit key (16 hex characters) manually, as plaintext, or from a file
4. The ciphertext is printed in HEX

### Decrypt a message

1. Select **3. Decrypt Message**
2. Enter the ciphertext in HEX (or load from file)
3. Enter the same key used for encryption
4. The plaintext is printed in HEX and ASCII

### Visualize the algorithm

Toggle **option 5** before encrypting/decrypting to see a full step-by-step trace of every round, including intermediate bit values at each stage.

---

## Algorithm Details

This implementation follows the original DES specification (FIPS PUB 46-3):

| Component | Description |
|-----------|-------------|
| Block size | 64 bits |
| Key size | 64 bits (56 effective) |
| Rounds | 16 Feistel rounds |
| Permutations | IP, FP, PC-1, PC-2, E-expansion, P-box |
| Substitution | 8 S-boxes (SB1–SB8) |
| Key schedule | Left circular shifts per SHIFT schedule |
| Padding | PKCS-style byte padding to 64-bit block boundary |
| Mode | ECB (Electronic Codebook) |

> **Note:** DES is considered cryptographically broken for modern use due to its 56-bit effective key length. This project is intended purely for **educational purposes** — to understand and visualize the internals of a classic symmetric cipher.

---

## License

This project is for educational use (CSEC2350 coursework). Not intended for production or security-critical applications.
