package pckg;

import java.util.Scanner;

public class main {

	private static final Scanner scanner = new Scanner(System.in);

	private static String getDataHex(boolean encryptMode) throws Exception {

	    String label = encryptMode ? "Plaintext" : "Ciphertext";
	    System.out.println("\n--- Enter " + label + " ---");
	    System.out.println("1. Manual HEX input");

	    if (encryptMode) {
	        System.out.println("2. Manual ASCII input");
	    }

	    System.out.println("3. Load from file (HEX or ASCII)");
	    System.out.print("Choice: ");

	    String choice = scanner.nextLine();
	    String resultHex = "";   

	    switch (choice) {
	        case "1":
	            System.out.print("Enter " + label + " (HEX): ");
	            resultHex = methods.validateHex(scanner.nextLine(), 0);
	            break;

	        case "2":
	            if (encryptMode) {
	                System.out.print("Enter " + label + " (ASCII): ");
	                resultHex = methods.asciiToHex(scanner.nextLine());
	                break;
	            }
	           
	        case "3":
	            System.out.print("Enter filename: ");
	            String fileContents = methods.readFile(scanner.nextLine()).trim();

	            if (fileContents.matches("^[0-9A-F]+$")) {
	                resultHex = fileContents;
	                System.out.println("Loaded as HEX.");
	            } else {
	                resultHex = methods.asciiToHex(fileContents);
	                System.out.println("Loaded as ASCII and converted to HEX.");
	            }
	            break;

	        default:
	            throw new Exception("Invalid choice.");
	    }

	    if (encryptMode) {
	        resultHex = methods.padHex(resultHex);
	    }

	    return resultHex;
	}

	
	private static String getKeyHex() throws Exception {

	    System.out.println("\n--- Enter Key ---");
	    System.out.println("1. Manual HEX input");
	    System.out.println("2. Manual Plaintext input");
	    System.out.println("3. Load from file");
	    System.out.print("Choice: ");

	    String keyHex = "";

	    switch (scanner.nextLine()) {

	        case "1":
	            System.out.print("Enter Key (HEX): ");
	            keyHex = methods.validateHex(scanner.nextLine(), 16);
	            break;

	        case "2":
	            System.out.print("Enter Key (String): ");
	            keyHex = methods.asciiToHex(scanner.nextLine());
	            keyHex = methods.padOrTrimHex(keyHex, 16); 
	            break;

	        case "3":
	            System.out.print("Enter filename: ");
	            keyHex = methods.validateHex(
	                    methods.readFile(scanner.nextLine()).trim(),
	                    16
	            );
	            break;

	        default:
	            throw new Exception("Invalid choice");
	    }

	    return keyHex;
	}

	public static void main(String[] args) {

	    System.out.println("DES ENCRYPTION SYSTEM-CSEC2350-v1.0");

	    while (true) {

	        System.out.println("\n==========================================");
	        System.out.println("1. Key Expansion (Show Subkeys)");
	        System.out.println("2. Encrypt Message");
	        System.out.println("3. Decrypt Message");
	        System.out.println("4. Generate Random DES Key");
	        System.out.println("5. Visualize Encryption Process");
	        System.out.println("6. Exit");
	        System.out.println("==========================================");
	        System.out.print("Enter your choice: ");

	        String menuChoice = scanner.nextLine();

	        try {
	            switch (menuChoice) {

	                case "1":
	                    DES.KeyExpansion();
	                    break;

	                case "2":
	                    String encData = getDataHex(true);
	                    String encKey  = getKeyHex();
	                    DES.Cipher(encData, encKey, true);
	                    break;

	                case "3":
	                    String decData = getDataHex(false);
	                    String decKey  = getKeyHex();
	                    DES.Cipher(decData, decKey, false);
	                    break;

	                case "4":
	                    DES.RandomKeyGenerate();
	                    break;

	                case "5":
	                    DES.visualize = !DES.visualize;
	                    System.out.println(
	                            "Visualization mode is now: " +
	                            (DES.visualize ? "ON" : "OFF")
	                    );
	                    break;

	                case "6":
	                    System.out.println("Exiting...");
	                    System.exit(0);
	                    break;

	                default:
	                    System.out.println("Invalid choice. Please try again.");
	            }

	        } catch (Exception e) {
	       
	            System.out.println("\nError: " + e.getMessage());
	        }
	    }
	}


	}