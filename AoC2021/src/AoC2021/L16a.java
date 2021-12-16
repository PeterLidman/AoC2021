package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L16a {
	private static List<String> in;
	private static long totLength;
	private static long sumVersion;

	public static void run(String ext) throws IOException {
		in = Files.readAllLines(Paths.get("./src/AoC2021/L16input" + ext + ".txt")).stream()
				.collect(Collectors.toList());
//		String t = "D2FE28";
//		String t = "38006F45291200";
//		String t = "EE00D40C823060";
//		String t = "8A004A801A8002F478";
//		String t = "620080001611562C8802118E34";
//		String t = "C0015000016115A2E0802F182340";
//		String t = "A0016C880162017C3686B18A3D4780";
		String t = in.get(0);

		sumVersion = 0;
		totLength = t.length();
		t = hexToBinary(t);
		while (t.length() > 14) {
			t = parseBits(t);
		}
		System.out.println("Sum version numbers: " + sumVersion);
	}

	static String getLiteral(String in) {
		sumVersion += getVersion(in);
		in = in.substring(6);
		totLength -= 6;
		while (in.charAt(0) == '1') {
			totLength -= 5;
			in = in.substring(5);
		}
		totLength -= 5;
		in = in.substring(5);
		return in;
	}

	static String parseBits(String in) {
		if (getTypeId(in) == 4) {
			in = getLiteral(in);
		} else { // operator
			if (getLengthTypeId(in) == 0) { // 15 bits, total length in bits
				sumVersion += getVersion(in);
				long ll = intFromBinary(in.substring(7, 22));
				long sl = totLength;
				in = in.substring(22);
				while (sl - totLength > ll) {
					in = parseBits(in);
				}
			} else { // 11 bits, number of sub packets
				sumVersion += getVersion(in);
				long sp = intFromBinary(in.substring(7, 18));
				in = in.substring(18);
				for (int i = 0; i < sp; i++) {
					in = parseBits(in);
				}
			}
		}
		return in;
	}

	static long intFromBinary(String in) {
		long pos = 1, ret = 0;

		for (int i = in.length() - 1; i >= 0; i--) {
			ret += in.charAt(i) == '1' ? pos : 0;
			pos *= 2;
		}
		return ret;
	}

	static int getLengthTypeId(String in) {
		return in.charAt(6) == '1' ? 1 : 0;
	}

	static int getTypeId(String in) {
		return (in.charAt(3) == '1' ? 4 : 0) + (in.charAt(4) == '1' ? 2 : 0) + (in.charAt(5) == '1' ? 1 : 0);
	}

	static int getVersion(String in) {
		return (in.charAt(0) == '1' ? 4 : 0) + (in.charAt(1) == '1' ? 2 : 0) + (in.charAt(2) == '1' ? 1 : 0);
	}

	static String hexToBinary(String in) {
		String out = "";
		for (int i = 0; i < in.length(); i++) {
			out += decode(in.substring(i, i + 1));
		}
		return out;
	}

	static String decode(String in) {
		switch (in) {
		case "0":
			return "0000";
		case "1":
			return "0001";
		case "2":
			return "0010";
		case "3":
			return "0011";
		case "4":
			return "0100";
		case "5":
			return "0101";
		case "6":
			return "0110";
		case "7":
			return "0111";
		case "8":
			return "1000";
		case "9":
			return "1001";
		case "A":
			return "1010";
		case "B":
			return "1011";
		case "C":
			return "1100";
		case "D":
			return "1101";
		case "E":
			return "1110";
		case "F":
			return "1111";
		}
		return "";
	}
}