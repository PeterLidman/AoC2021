package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class L08b {
	private static List<String> code;

	public static void run(String ext) throws IOException {
		code = Files.readAllLines(Paths.get("./src/AoC2021/L08input" + ext + ".txt")).stream()
				.collect(Collectors.toList());
		int totSum = 0;
		for (String a : code) {
			totSum += getCode(a);
		}
		System.out.println("Sum of all output values: " + totSum);
	}

	static int getCode(String s) {
		String p[] = s.split("[|]");
		HashMap<Integer, Set<Character>> ct = new HashMap<>();

		Set<String> lednr = Set.of(p[0].split(" "));
		for (String a : lednr) {
			if (a.length() == 2) {
				ct.put(1, a.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
			}
			if (a.length() == 3) {
				ct.put(7, a.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
			}
			if (a.length() == 4) {
				ct.put(4, a.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
			}
			if (a.length() == 7) {
				ct.put(8, a.chars().mapToObj(c -> (char) c).collect(Collectors.toSet()));
			}
		}
		for (String a : lednr) {
			Set<Character> c = a.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
			Set<Character> b = new HashSet<>(c);
			b.removeAll(ct.get(4));
			if (a.length() == 5) {
				if (c.containsAll(ct.get(1))) { // 3, alla i 1
					ct.put(3, c);
				} else if (b.size() == 3) { // 2, 2 träffar i 4
					ct.put(2, c);
				} else { // 5
					ct.put(5, c);
				}
			}
			if (a.length() == 6) {
				if (c.containsAll(ct.get(4))) { // 9, alla i 4
					ct.put(9, c);
				} else if (c.containsAll(ct.get(1))) { // 0, alla i 1
					ct.put(0, c);
				} else { // 6
					ct.put(6, c);
				}
			}
		}
		List<String> on = List.of(p[1].trim().split(" "));
		int mult = 1000;
		int v = 0;
		for (String n : on) {
			Set<Character> c = n.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
			for (Entry<Integer, Set<Character>> ns : ct.entrySet()) {
				if (ns.getValue().equals(c)) {
					v += mult * ns.getKey();
					mult = mult / 10;
					break;
				}
			}
		}
		return v;
	}

}
