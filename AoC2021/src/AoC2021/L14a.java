package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class L14a {
	private static List<String> poly;
	private static HashMap<String, String> rules;

	public static void run(String ext) throws IOException {
		poly = Files.readAllLines(Paths.get("./src/AoC2021/L14input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		String p = poly.get(0).trim();
		rules = new HashMap<>();
		for (int i = 2; i < poly.size(); i++) {
			rules.put(poly.get(i).substring(0, 2), poly.get(i).substring(6, 7));
		}
		for (int i = 0; i < 10; i++) {
			p = insert(p);
		}
		Map<Character, Long> c = p.chars().mapToObj(x -> (char) x)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		Optional<Entry<Character, Long>> max = c.entrySet().stream().max(Comparator.comparing(t -> t.getValue()));
		Optional<Entry<Character, Long>> min = c.entrySet().stream().min(Comparator.comparing(t -> t.getValue()));
		System.out.println("Answer: " + (max.get().getValue() - min.get().getValue()));
	}

	private static String insert(String p) {
		String out = p.substring(0, 1);
		String prev = out;
		String curr;
		for (int i = 1; i < p.length(); i++) {
			curr = p.substring(i, i + 1);
			out += rules.get(prev + curr) + curr;
			prev = curr;
		}
		return out;
	}
}