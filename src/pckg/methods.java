package pckg;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

public class methods {

	public static String xor(String binary1, String binary2) {
		if (binary1.length() != binary2.length()) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < binary1.length(); i++) {
			result.append(binary1.charAt(i) == binary2.charAt(i) ? '0' : '1');
		}
		return result.toString();
	}

	public static String readFile(String filename) throws Exception {
		return Files.readString(new File(filename).toPath());
	}

	public static void writeFile(String filename, String content) {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write(content);
		} catch (Exception e) {
			System.out.println("Error saving file: " + e.getMessage());
		}
	}

	public static String validateHex(String hex, int length) throws Exception {
		if (hex == null || hex.isEmpty())
			throw new Exception("Input cannot be empty.");
		hex = hex.toUpperCase().replaceAll("\\s", "");
		if (!hex.matches("^[0-9A-F]*$"))
			throw new Exception("Input must be a hexadecimal string.");
		if (length > 0 && hex.length() != length)
			throw new Exception("Input must be exactly " + length + " hex characters (" + (length * 4) + " bits).");
		return hex;
	}

	public static String padHex(String hex) {
		int length = hex.length();
		int byteLen = length / 2;
		int blockSize = 8;

		int paddingNeeded = blockSize - (byteLen % blockSize);

		StringBuilder padded = new StringBuilder(hex);
		String padByteHex = String.format("%02X", paddingNeeded);

		for (int i = 0; i < paddingNeeded; i++) {
			padded.append(padByteHex);
		}
		return padded.toString();
	}

	public static String padOrTrimHex(String hex, int targetLen) {
		if (hex.length() > targetLen)
			return hex.substring(0, targetLen);
		while (hex.length() < targetLen)
			hex += "0";
		return hex;
	}

	public static String asciiToHex(String ascii) {
		StringBuilder hex = new StringBuilder();
		for (char c : ascii.toCharArray()) {
			hex.append(String.format("%02X", (int) c));
		}
		return hex.toString();
	}

	public static String hexToAscii(String hex) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < hex.length(); i += 2) {
			String str = hex.substring(i, i + 2);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public static String hexToBinStr(String hex) {
		StringBuilder bin = new StringBuilder();
		for (char c : hex.toCharArray()) {
			int val = Integer.parseInt(String.valueOf(c), 16);
			String b = Integer.toBinaryString(val);
			while (b.length() < 4)
				b = "0" + b;
			bin.append(b);
		}
		return bin.toString();
	}

	public static String binToHexStr(int[] bits) {
		StringBuilder hex = new StringBuilder();
		for (int i = 0; i < bits.length; i += 4) {
			int val = 0;
			for (int j = 0; j < 4; j++) {
				if (i + j < bits.length)
					val = (val << 1) | bits[i + j];
			}
			hex.append(Integer.toHexString(val).toUpperCase());
		}
		return hex.toString();
	}

	public static int[] hexToBin(String hex) {
		String binStr = hexToBinStr(hex);
		int[] bits = new int[binStr.length()];
		for (int i = 0; i < binStr.length(); i++) {
			bits[i] = binStr.charAt(i) - '0';
		}
		return bits;
	}
}