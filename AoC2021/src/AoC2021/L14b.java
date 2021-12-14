package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

public class L14b {
	private static List<String> poly;
	private static HashMap<String, String> rules;
	private static HashMap<String, Long> pairs;

	public static void run(String ext) throws IOException {
		poly = Files.readAllLines(Paths.get("./src/AoC2021/L14input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		String p = poly.get(0).trim();
		rules = new HashMap<>();
		pairs = new HashMap<>();
		for (int i = 2; i < poly.size(); i++) {
			rules.put(poly.get(i).substring(0, 2), poly.get(i).substring(6, 7));
		}
		for (int i = 1; i < p.length(); i++) {
			incPair(p.substring(i - 1, i + 1));
		}
		for (int i = 0; i < 40; i++) {
			insertPair();
		}
		HashMap<Character, Long> o = new HashMap<>();
		o.put(p.charAt(p.length() - 1), (long) 1);
		for (Entry<String, Long> d : pairs.entrySet()) {
			Character chr = d.getKey().charAt(0);
			Long c = o.get(chr);
			if (c != null) {
				o.put(chr, c + d.getValue());
			} else {
				o.put(chr, d.getValue());
			}
		}
		Optional<Entry<Character, Long>> max = o.entrySet().stream().max(Comparator.comparing(t -> t.getValue()));
		Optional<Entry<Character, Long>> min = o.entrySet().stream().min(Comparator.comparing(t -> t.getValue()));
		System.out.println("Answer: " + (max.get().getValue() - min.get().getValue()));
	}

	private static void incPair(String p) {
		Long c = pairs.get(p);
		if (c != null) {
			pairs.put(p, c + 1);
		} else {
			pairs.put(p, (long) 1);
		}
	}

	private static void insertPair() {
		HashMap<String, Long> newPairs = new HashMap<>();
		for (Entry<String, Long> e : pairs.entrySet()) {
			String s = rules.get(e.getKey());
			String p1 = e.getKey().substring(0, 1) + s;
			String p2 = s + e.getKey().substring(1, 2);

			Long c = newPairs.get(p1);
			if (c != null) {
				newPairs.put(p1, c + e.getValue());
			} else {
				newPairs.put(p1, e.getValue());
			}

			c = newPairs.get(p2);
			if (c != null) {
				newPairs.put(p2, c + e.getValue());
			} else {
				newPairs.put(p2, e.getValue());
			}
		}
		pairs = newPairs;
	}

}
