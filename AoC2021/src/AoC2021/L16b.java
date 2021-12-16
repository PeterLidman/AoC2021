package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L16b {
	private static List<String> in;
	private static String t;
	private static long totLength;

	public static void run(String ext) throws IOException {
		in = Files.readAllLines(Paths.get("./src/AoC2021/L16input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

//		t = "D2FE28";
//		t = "38006F45291200";
//		t = "EE00D40C823060";
//		t = "8A004A801A8002F478";
//		t = "620080001611562C8802118E34";
//		t = "C0015000016115A2E0802F182340";
//		t = "A0016C880162017C3686B18A3D4780";
		t = in.get(0);
//		t = "C200B40A82";
//		t = "04005AC33890";
//		t = "880086C3E88112";
//		t = "CE00C43D881120";
//		t = "D8005AC2A8F0";
//		t = "F600BC2D8F";
//		t = "9C005AC2F8F0";
//		t = "9C0141080250320F1802104A08";
		t = hexToBinary(t);
		totLength = t.length();
		System.out.println("Result: " + getValue());
	}

	static long getLiteral() {
		t = t.substring(6);
		totLength -= 6;
		String lit = "";
		while (t.charAt(0) == '1') {
			lit += t.substring(1, 5);
			totLength -= 5;
			t = t.substring(5);
		}
		lit += t.substring(1, 5);
		totLength -= 5;
		t = t.substring(5);
		return longFromBinary(lit);
	}

	static long getValue() {
		int id = getTypeId(t);

		if (id == 4) {
			return getLiteral();
		} else { // operator
			if (getLengthTypeId(t) == 0) { // 15 bits, total length in bits
				long ll = longFromBinary(t.substring(7, 22));
				totLength -= 22;
				t = t.substring(22);
				long sl = totLength;

				switch (id) {
				case 0:
					long sum = (long) 0;
					while (sl - ll < totLength) {
						sum += getValue();
					}
					return sum;
				case 1:
					long prod = (long) 1;
					while (sl - ll < totLength) {
						prod *= getValue();
					}
					return prod;
				case 2:
					long min = Long.MAX_VALUE;
					while (sl - ll < totLength) {
						min = Math.min(min, getValue());
					}
					return min;
				case 3:
					long max = Long.MIN_VALUE;
					while (sl - ll < totLength) {
						max = Math.max(max, getValue());
					}
					return max;
				case 5:
					long a = getValue();
					long b = getValue();
					return a > b ? 1 : 0;
				case 6:
					long aa = getValue();
					long bb = getValue();
					return aa < bb ? 1 : 0;
				case 7:
					long aaa = getValue();
					long bbb = getValue();
					return aaa == bbb ? 1 : 0;
				}
			} else { // 11 bits, number of sub packets
				long sp = longFromBinary(t.substring(7, 18));
				t = t.substring(18);
				totLength -= 18;

				switch (id) {
				case 0:
					long sum = (long) 0;
					for (int i = 0; i < sp; i++) {
						sum += getValue();
					}
					return sum;
				case 1:
					long prod = (long) 1;
					for (int i = 0; i < sp; i++) {
						prod *= getValue();
					}
					return prod;
				case 2:
					long min = Long.MAX_VALUE;
					for (int i = 0; i < sp; i++) {
						min = Math.min(min, getValue());
					}
					return min;
				case 3:
					long max = Long.MIN_VALUE;
					for (int i = 0; i < sp; i++) {
						max = Math.max(max, getValue());
					}
					return max;
				case 5:
					long a = getValue();
					long b = getValue();
					return a > b ? 1 : 0;
				case 6:
					long aa = getValue();
					long bb = getValue();
					return aa < bb ? 1 : 0;
				case 7:
					long aaa = getValue();
					long bbb = getValue();
					return aaa == bbb ? 1 : 0;
				}
			}
		}
		return -99999999;
	}

	static long longFromBinary(String in) {
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