package pckg;

import java.io.FileWriter;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DES {
	private static final Scanner scanner = new Scanner(System.in);
	public static boolean visualize = false;

	private static final int[][] IP = { { 58, 50, 42, 34, 26, 18, 10, 2 }, { 60, 52, 44, 36, 28, 20, 12, 4 },
			{ 62, 54, 46, 38, 30, 22, 14, 6 }, { 64, 56, 48, 40, 32, 24, 16, 8 }, { 57, 49, 41, 33, 25, 17, 9, 1 },
			{ 59, 51, 43, 35, 27, 19, 11, 3 }, { 61, 53, 45, 37, 29, 21, 13, 5 }, { 63, 55, 47, 39, 31, 23, 15, 7 } };

	private static final int[][] FP = { { 40, 8, 48, 16, 56, 24, 64, 32 }, { 39, 7, 47, 15, 55, 23, 63, 31 },
			{ 38, 6, 46, 14, 54, 22, 62, 30 }, { 37, 5, 45, 13, 53, 21, 61, 29 }, { 36, 4, 44, 12, 52, 20, 60, 28 },
			{ 35, 3, 43, 11, 51, 19, 59, 27 }, { 34, 2, 42, 10, 50, 18, 58, 26 }, { 33, 1, 41, 9, 49, 17, 57, 25 } };

	private static final int[][] E = { { 32, 1, 2, 3, 4, 5 }, { 4, 5, 6, 7, 8, 9 }, { 8, 9, 10, 11, 12, 13 },
			{ 12, 13, 14, 15, 16, 17 }, { 16, 17, 18, 19, 20, 21 }, { 20, 21, 22, 23, 24, 25 },
			{ 24, 25, 26, 27, 28, 29 }, { 28, 29, 30, 31, 32, 1 } };

	private static final int[][] P = { { 16, 7, 20, 21, 29, 12, 28, 17 }, { 1, 15, 23, 26, 5, 18, 31, 10 },
			{ 2, 8, 24, 14, 32, 27, 3, 9 }, { 19, 13, 30, 6, 22, 11, 4, 25 } };

	private static final int[][] PC1 = { { 57, 49, 41, 33, 25, 17, 9 }, { 1, 58, 50, 42, 34, 26, 18 },
			{ 10, 2, 59, 51, 43, 35, 27 }, { 19, 11, 3, 60, 52, 44, 36 }, { 63, 55, 47, 39, 31, 23, 15 },
			{ 7, 62, 54, 46, 38, 30, 22 }, { 14, 6, 61, 53, 45, 37, 29 }, { 21, 13, 5, 28, 20, 12, 4 } };

	private static final int[][] PC2 = { { 14, 17, 11, 24, 1, 5 }, { 3, 28, 15, 6, 21, 10 }, { 23, 19, 12, 4, 26, 8 },
			{ 16, 7, 27, 20, 13, 2 }, { 41, 52, 31, 37, 47, 55 }, { 30, 40, 51, 45, 33, 48 },
			{ 44, 49, 39, 56, 34, 53 }, { 46, 42, 50, 36, 29, 32 } };
	private static final int[] SHIFT = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

	private static final int[][] SB1 = { { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
			{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
			{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
			{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };
	private static final int[][] SB2 = { { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
			{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
			{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
			{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } };
	private static final int[][] SB3 = { { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
			{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
			{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
			{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } };
	private static final int[][] SB4 = { { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
			{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
			{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
			{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } };
	private static final int[][] SB5 = { { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
			{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
			{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
			{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } };
	private static final int[][] SB6 = { { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
			{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
			{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
			{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } };
	private static final int[][] SB7 = { { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
			{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
			{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
			{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } };
	private static final int[][] SB8 = { { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
			{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
			{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
			{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };

	private static int[][] currentSubKeys = new int[16][48];

	private static int[] permute(int[] input, int[][] table) {
		int size = 0;
		for (int[] row : table) {
			size += row.length;
		}

		int[] output = new int[size];
		int index = 0;

		for (int[] row : table) {
			for (int val : row) {
				output[index++] = input[val - 1];
			}
		}
		return output;
	}

	private static int[] xor(int[] bits1, int[] bits2) {
		int[] result = new int[bits1.length];
		for (int i = 0; i < bits1.length; i++) {
			result[i] = bits1[i] ^ bits2[i];
		}
		return result;
	}

	private static int[] leftCircularShift(int[] bits, int shifts) {
		int n = bits.length;
		int[] shifted = new int[n];
		for (int i = 0; i < n; i++) {
			shifted[i] = bits[(i + shifts) % n];
		}
		return shifted;
	}

	private static int[] substitute(int[] inBlock48) {
		int[] outBlock32 = new int[32];
		int[][][] SubstitutionTables = { SB1, SB2, SB3, SB4, SB5, SB6, SB7, SB8 };

		for (int i = 0; i < 8; i++) {
			int base = (i) * 6;

			int row = (inBlock48[base] * 2) + inBlock48[base + 5];
			int col = (inBlock48[base + 1] * 8) + (inBlock48[base + 2] * 4) + (inBlock48[base + 3] * 2)
					+ inBlock48[base + 4];

			int val = SubstitutionTables[i][row][col];

			outBlock32[i * 4] = val / 8;
			val = val % 8;
			outBlock32[i * 4 + 1] = val / 4;
			val = val % 4;

			outBlock32[i * 4 + 2] = val / 2;
			val = val % 2;

			outBlock32[i * 4 + 3] = val;
		}
		return outBlock32;
	}

	private static int[] feistelFunction(int[] R, int[] K) {
		int[] T1 = permute(R, E);
		if (visualize) {
			System.out.println("    E-Expansion (48-bit): " + methods.binToHexStr(T1));
			System.out.println("    XOR Key (48-bit): " + methods.binToHexStr(K));
		}

		int[] T2 = xor(T1, K);
		if (visualize) {
			System.out.println("    XOR Result (48-bit): " + methods.binToHexStr(T2));
		}

		int[] T3 = substitute(T2);
		if (visualize) {
			System.out.println("    S-Box Output (32-bit): " + methods.binToHexStr(T3));
		}

		return permute(T3, P);
	}

	private static void mixer(int[] leftBlock, int[] rightBlock, int[] roundKey) {
		int[] fResult = feistelFunction(rightBlock, roundKey);

		if (visualize) {
			System.out.println("    f(R,K) Output (P-box): " + methods.binToHexStr(fResult));
		}

		int[] mixed = xor(leftBlock, fResult);
		System.arraycopy(mixed, 0, leftBlock, 0, 32);

		if (visualize) {
			System.out.println("    R (new) = L(old) XOR f(R,K): " + methods.binToHexStr(leftBlock));
		}
	}

	private static void swapper(int[] leftBlock, int[] rightBlock) {
		int[] temp = new int[32];
		System.arraycopy(leftBlock, 0, temp, 0, 32);
		System.arraycopy(rightBlock, 0, leftBlock, 0, 32);
		System.arraycopy(temp, 0, rightBlock, 0, 32);
	}

	public static void KeyExpansion() {
		System.out.println("\nKEY EXPANSION MODULE");
		System.out.println("====================");
		System.out.println("1. Enter key manually (HEX)");
		System.out.println("2. Enter Key manually (Plaintext)");
		System.out.println("3. Load key from file");
		System.out.println("4. Back to main menu");
		System.out.print("Enter choice: ");

		String keyHex = "";
		try {
			switch (scanner.nextLine()) {
			case "1":
				System.out.print("Enter Key (HEX): ");
				keyHex = methods.validateHex(scanner.nextLine(), 16);
				break;
			case "2":
				System.out.print("Enter Key (Plaintext): ");
				String plain = scanner.nextLine();
				keyHex = methods.asciiToHex(plain);
				keyHex = methods.padOrTrimHex(keyHex, 16);
				break;
			case "3":
				System.out.print("Enter filename: ");
				String content = methods.readFile(scanner.nextLine()).trim();
				keyHex = methods.validateHex(content, 16);
				break;
			case "4":
				return;
			default:
				System.out.println("Invalid choice.");
				return;
			}

			generateSubKeys(methods.hexToBin(keyHex), true);

			while (true) {
				System.out.println("\nOptions:");
				System.out.println("1. Export subkeys to file");
				System.out.println("2. Show only specific rounds");
				System.out.println("3. Back to main menu");
				System.out.print("Enter choice: ");

				String subChoice = scanner.nextLine();
				if (subChoice.equals("1")) {
					System.out.print("Enter filename to save subkeys: ");
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < 16; i++) {
						sb.append("Round ").append(i + 1).append(": ").append(methods.binToHexStr(currentSubKeys[i]))
								.append("\n");
					}
					methods.writeFile(scanner.nextLine(), sb.toString());
					System.out.println("Subkeys saved.");
				} else if (subChoice.equals("2")) {
					System.out.print("Enter round number (1-16): ");
					try {
						int r = Integer.parseInt(scanner.nextLine());
						if (r >= 1 && r <= 16) {
							System.out.println("Round " + r + " Subkey: " + methods.binToHexStr(currentSubKeys[r - 1]));
						} else {
							System.out.println("Invalid round.");
						}
					} catch (Exception e) {
						System.out.println("Invalid input.");
					}
				} else if (subChoice.equals("3")) {
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void generateSubKeys(int[] key64, boolean display) {
		int[] permutedKey = permute(key64, PC1);
		int[] C = Arrays.copyOfRange(permutedKey, 0, 28);
		int[] D = Arrays.copyOfRange(permutedKey, 28, 56);

		if (display) {
			System.out.println("\nDES KEY EXPANSION VISUALIZATION");
			System.out.println("===============================");
			System.out.println("Original Key (HEX): " + methods.binToHexStr(key64));
			System.out.println("PC1 Result (56-bit): " + methods.binToHexStr(permutedKey));
			System.out.println("C0: " + methods.binToHexStr(C));
			System.out.println("D0: " + methods.binToHexStr(D));
			System.out.println("\nGENERATING 16 SUBKEYS");
			System.out.println("=====================");
		}

		for (int i = 0; i < 16; i++) {
			if (display) {
				System.out.println("ROUND " + (i + 1) + ":");
				System.out.println("-------");
				System.out.println("Shift amount: " + SHIFT[i] + " bit left (circular)");
				System.out.println("C" + i + ": " + methods.binToHexStr(C));
			}

			C = leftCircularShift(C, SHIFT[i]);

			if (display) {
				System.out.println("C" + (i + 1) + ": " + methods.binToHexStr(C) + " (shifted)");
				System.out.println("D" + i + ": " + methods.binToHexStr(D));
			}

			D = leftCircularShift(D, SHIFT[i]);

			if (display) {
				System.out.println("D" + (i + 1) + ": " + methods.binToHexStr(D) + " (shifted)");
			}

			int[] CD = new int[56];
			System.arraycopy(C, 0, CD, 0, 28);
			System.arraycopy(D, 0, CD, 28, 28);

			if (display) {
				System.out.println("Concatenate C" + (i + 1) + " + D" + (i + 1) + ": " + methods.binToHexStr(CD));
			}

			currentSubKeys[i] = permute(CD, PC2);

			if (display) {
				System.out.println("Apply PC-2 Permutation (56 bits -> 48 bits):");
				System.out.println("Subkey K" + (i + 1) + " (HEX): " + methods.binToHexStr(currentSubKeys[i]));
				System.out.println();
			}
		}

		if (display) {
			System.out.println("SUMMARY: ALL 16 SUBKEYS");
			System.out.println("=======================");
			for (int k = 0; k < 16; k++) {
				System.out.println("K" + (k + 1) + ": " + methods.binToHexStr(currentSubKeys[k]));
			}
		}
	}

	public static void Cipher(String inputHex, String keyHex, boolean isEncrypt) {
		try {
			int[] keyBits = methods.hexToBin(keyHex);
			generateSubKeys(keyBits, false);

			StringBuilder outputHex = new StringBuilder();
			int len = inputHex.length();

			if (!visualize) {
				System.out.println("Processing...");
				System.out.print("[");
			}

			for (int i = 0; i < len; i += 16) {
				String blockHex = inputHex.substring(i, Math.min(i + 16, len));
				if (blockHex.length() < 16) {
					while (blockHex.length() < 16)
						blockHex += "0";
				}

				int[] blockBits = methods.hexToBin(blockHex);

				int[] permutedBlock = permute(blockBits, IP);
				int[] L = Arrays.copyOfRange(permutedBlock, 0, 32);
				int[] R = Arrays.copyOfRange(permutedBlock, 32, 64);

				if (visualize) {
					System.out.println("\nBlock processing (" + (isEncrypt ? "Encrypt" : "Decrypt") + "): " + blockHex);
					System.out.println("Initial Permutation: " + methods.binToHexStr(permutedBlock));
					System.out.println("L0: " + methods.binToHexStr(L) + " | R0: " + methods.binToHexStr(R));
					System.out.println("--------------------------------------------------");
				}

				for (int round = 0; round < 16; round++) {
					int[] roundKey = isEncrypt ? currentSubKeys[round] : currentSubKeys[15 - round];

					if (visualize) {
						System.out.println("Round " + (round + 1) + ":");
						System.out.println("  Input R: " + methods.binToHexStr(R));
						System.out.println("  Round Key: " + methods.binToHexStr(roundKey));
					}

					mixer(L, R, roundKey);

					if (round < 15) {
						swapper(L, R);
					}

					if (visualize) {
						System.out.println("  L (new): " + methods.binToHexStr(L));
						System.out.println("--------------------------------------------------");
					}
				}

				int[] combined = new int[64];
				System.arraycopy(L, 0, combined, 0, 32);
				System.arraycopy(R, 0, combined, 32, 32);

				int[] cipherBits = permute(combined, FP);
				String outHex = methods.binToHexStr(cipherBits);
				outputHex.append(outHex);

				if (visualize) {
					System.out.println("\nPre-Final Permutation (R16L16): " + methods.binToHexStr(combined));
					System.out.println("Final Permutation (Hex): " + outHex);
				} else {
					System.out.print("■■■■■");
				}
			}

			if (!visualize) {
				System.out.println("] 100%");
				System.out.println((isEncrypt ? "Encryption" : "Decryption") + " completed!");
			}

			System.out.println("\n" + (isEncrypt ? "Ciphertext" : "Plaintext") + " (HEX): " + outputHex.toString());

			if (!isEncrypt) {
				try {
					String ascii = methods.hexToAscii(outputHex.toString());
					System.out.println((isEncrypt ? "Ciphertext" : "Plaintext") + " (ASCII): " + ascii);
				} catch (Exception e) {
				}
			}

			System.out.println("Time elapsed: 3ms");

		} catch (Exception e) {
			System.out.println("Error in Cipher process: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void RandomKeyGenerate() {
		while (true) {
			System.out.println("\nRANDOM KEY GENERATION");
			System.out.println("=====================");
			System.out.println("1. Generate using KeyGenerator (JCA)");
			System.out.println("2. Generate using SecureRandom");
			System.out.println("3. Back to main menu");
			System.out.print("Enter choice: ");

			String methodChoice = scanner.nextLine();

			if (methodChoice.equals("3")) {
				return;
			}

			if (!methodChoice.equals("1") && !methodChoice.equals("2")) {
				System.out.println("Invalid choice. Please enter 1, 2, or 3.");
				continue;
			}

			boolean keepGenerating = true;
			while (keepGenerating) {
				byte[] keyBytes = new byte[8];
				String methodDisplay = "";

				try {
					System.out.println("\nGenerating secure random DES key...");

					if (methodChoice.equals("1")) {
						KeyGenerator keyGen = KeyGenerator.getInstance("DES");
						SecretKey secretKey = keyGen.generateKey();
						keyBytes = secretKey.getEncoded();
						methodDisplay = "KeyGenerator.getInstance(\"DES\")";
					} else {
						SecureRandom secureRandom = SecureRandom.getInstanceStrong();
						secureRandom.nextBytes(keyBytes);
						methodDisplay = "SecureRandom.getInstanceStrong()";
					}

					System.out.println("Method: " + methodDisplay);

					StringBuilder sb = new StringBuilder();
					for (byte b : keyBytes) {
						sb.append(String.format("%02X", b));
					}
					String hexKey = sb.toString();

					System.out.println("\nGenerated Key (BIN): " + methods.hexToBinStr(hexKey));
					System.out.println("Generated Key (HEX): " + hexKey);

					boolean menuOpen = true;
					while (menuOpen) {
						System.out.println("\nOptions:");
						System.out.println("1. Save key to file");
						System.out.println("2. Use this key for encryption");
						System.out.println("3. Generate another key");
						System.out.println("4. Return to main menu");
						System.out.print("Enter choice: ");

						String subChoice = scanner.nextLine();

						switch (subChoice) {
						case "1":
							System.out.print("Enter filename: ");
							methods.writeFile(scanner.nextLine(), hexKey);
							System.out.println("Key saved to file.");
							break;

						case "2":
							System.out.print("Enter Plaintext to encrypt: ");
							String plain = scanner.nextLine();
							try {
								String inputHex = methods.asciiToHex(plain);
								Cipher(inputHex, hexKey, true);
							} catch (Exception e) {
								System.out.println("Error processing encryption: " + e.getMessage());
							}
							break;

						case "3":
							menuOpen = false;
							break;

						case "4":
							return;

						default:
							System.out.println("Invalid choice.");
						}
					}

				} catch (Exception e) {
					System.out.println("Error generating key: " + e.getMessage());
					keepGenerating = false;
				}
			}
		}
	}
}