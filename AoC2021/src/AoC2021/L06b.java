package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L06b {
	private static List<String> _fish;
	static int _days = 256;
	static HashMap<Integer, Long> _memo = new HashMap<>();

	public static void run(final String ext) {
		try {
			_fish = Files.readAllLines(Paths.get("./src/AoC2021/L06input" + ext + ".txt")).stream().map(String::valueOf)
					.collect(Collectors.toList());
		} catch (final IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}

		final Map<Integer, Long> occ = Stream.of(_fish.get(0).split(","))
				.collect(Collectors.groupingBy(f -> Integer.valueOf(f), Collectors.counting()));

		System.out.println("No Fish: " + occ.entrySet().stream().map(e -> e.getValue() * countFish(e.getKey()))
				.mapToLong(Long::longValue).sum());
	}

	static long countFish(final int startAge) {
		long spawn = 0;
		for (int i = startAge + 1; i <= _days; i += 7) {
			spawn += countSpawn(i);
		}
		return spawn + 1;
	}

	static long countSpawn(final int birthDay) {
		if (_memo.containsKey(birthDay)) {
			return _memo.get(birthDay);
		}
		long sum = 0;
		if (_days - birthDay > 8) {
			for (int i = birthDay + 9; i <= _days; i += 7) {
				sum += countSpawn(i);
			}
			_memo.put(birthDay, sum + 1);
			return sum + 1;
		}
		return 1;
	}

}
