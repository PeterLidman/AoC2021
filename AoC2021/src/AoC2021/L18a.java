package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L18a {
	private static List<String> expr;

	public static void run(String ext) throws IOException {
		expr = Files.readAllLines(Paths.get("./src/AoC2021/L18input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		String s;
		s = stringAdd(expr.get(0), expr.get(1));
		s = stringReduce(s);
		for (int i = 2; i < expr.size(); i++) {
			s = stringAdd(s, expr.get(i));
			s = stringReduce(s);
		}
		System.out.println("mag= " + stringMagnitude(s));
	}

	private static Integer stringMagnitude(String in) {
		if (in.charAt(0) != '[' && !in.contains(",")) {
			return Integer.valueOf(in);
		}
		in = in.substring(1, in.length() - 1);
		int cb = 0;
		for (int i = 0; i < in.length(); i++) {
			cb += in.charAt(i) == '[' ? 1 : 0;
			cb += in.charAt(i) == ']' ? -1 : 0;
			if (in.charAt(i) == ',' && cb == 0) {
				return 3 * stringMagnitude(in.substring(0, i)) + 2 * stringMagnitude(in.substring(i + 1));
			}
		}
		return 0;
	}

	private static String stringAdd(String a, String b) {
		return "[" + a + "," + b + "]";
	}

	private static String stringReduce(String in) {
		boolean reduce = true;
		String out;
		while (reduce) {
			reduce = false;
			out = stringExplode(in);
			while (!out.equals(in)) {
				reduce = true;
				in = out;
				out = stringExplode(in);
			}
			out = stringSplit(in);
			if (!out.equals(in)) {
				reduce = true;
				in = out;
			}
		}
		return in;
	}

	private static String stringSplit(String b) {
		String ret = b;
		int ln = 0, av, bv;
		for (int i = 0; i < b.length(); i++) {
			if (!(b.charAt(i) == ',' || b.charAt(i) == '[' || b.charAt(i) == ']')) {
				if (!(b.charAt(i + 1) == ',' || b.charAt(i + 1) == '[' || b.charAt(i + 1) == ']')) {
					ln = Integer.valueOf(b.substring(i, i + 2));
					if (ln > 9) {
						av = ln / 2;
						bv = ln % 2 > 0 ? av + 1 : av;
						ret = b.substring(0, i) + "[" + String.valueOf(av) + "," + String.valueOf(bv) + "]"
								+ b.substring(i + 2);
						return ret;
					}
				}
			}
		}
		return ret;
	}

	private static String stringExplode(String in) {
		int cb = 0;
		for (int i = 0; i < in.length(); i++) {
			cb += in.charAt(i) == '[' ? 1 : 0;
			cb += in.charAt(i) == ']' ? -1 : 0;
			if (cb == 5) {
				int numa = 0;
				int numb = 0;
				String parta = in.substring(0, i);
				String partb = "";
				if (in.charAt(i + 2) == ',') {
					numa = Integer.valueOf(in.substring(i + 1, i + 2));
					if (in.charAt(i + 4) == ']') {
						numb = Integer.valueOf(in.substring(i + 3, i + 4));
						partb = in.substring(i + 5);
					} else { // >9
						numb = Integer.valueOf(in.substring(i + 3, i + 5));
						partb = in.substring(i + 6);
					}
				} else { // > 9
					numa = Integer.valueOf(in.substring(i + 1, i + 3));
					if (in.charAt(i + 5) == ']') {
						numb = Integer.valueOf(in.substring(i + 4, i + 5));
						partb = in.substring(i + 6);
					} else { // >9
						numb = Integer.valueOf(in.substring(i + 4, i + 6));
						partb = in.substring(i + 7);
					}
				}
				in = stringCombine(parta, partb, numa, numb);
				return in;
			}
		}
		return in;
	}

	static String stringCombine(String a, String b, int av, int bv) {
		String result = "";
		if (!stringContainsAnyNumber(a)) {
			result = a + "0" + addToFirstNumber(b, bv);
		} else if (!stringContainsAnyNumber(b)) {
			result = addToLastNumber(a, av) + "0" + b;
		} else { // both sides got number
			result = addToLastNumber(a, av) + "0" + addToFirstNumber(b, bv);
		}
		return result;
	}

	private static String addToLastNumber(String a, int av) {
		String ret = "";
		int ln = 0;
		for (int i = a.length() - 1; i >= 0; i--) {
			if (!(a.charAt(i) == ',' || a.charAt(i) == '[' || a.charAt(i) == ']')) {
				if (!(a.charAt(i - 1) == ',' || a.charAt(i - 1) == '[' || a.charAt(i - 1) == ']')) {
					ln = Integer.valueOf(a.substring(i - 1, i + 1));
					ret = a.substring(0, i - 1) + String.valueOf(av + ln) + a.substring(i + 1);
				} else {
					ln = Integer.valueOf(a.substring(i, i + 1));
					ret = a.substring(0, i) + String.valueOf(av + ln) + a.substring(i + 1);
				}
				break;
			}
		}
		return ret;
	}

	private static String addToFirstNumber(String b, int bv) {
		String ret = "";
		int ln = 0;
		for (int i = 0; i < b.length(); i++) {
			if (!(b.charAt(i) == ',' || b.charAt(i) == '[' || b.charAt(i) == ']')) {
				if (!(b.charAt(i + 1) == ',' || b.charAt(i + 1) == '[' || b.charAt(i + 1) == ']')) {
					ln = Integer.valueOf(b.substring(i, i + 2));
					ret = b.substring(0, i) + String.valueOf(bv + ln) + b.substring(i + 2);
				} else {
					ln = Integer.valueOf(b.substring(i, i + 1));
					ret = b.substring(0, i) + String.valueOf(bv + ln) + b.substring(i + 1);
				}
				break;
			}
		}
		return ret;
	}

	static boolean stringContainsAnyNumber(String a) {
		boolean ret = false;
		for (int i = 0; i < a.length(); i++) {
			if (!(a.charAt(i) == '[' || a.charAt(i) == ']')) {
				ret = true;
				break;
			}
		}
		return ret;
	}

}
