package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L06a {
	private static List<String> _fish;
	static int _days = 80;

	public static void run(String ext) {
		try {
			_fish = Files.readAllLines(Paths.get("./src/AoC2021/L06input" + ext + ".txt")).stream().map(String::valueOf)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}

		Map<Integer, Long> occ = Stream.of(_fish.get(0).split(","))
				.collect(Collectors.groupingBy(f -> Integer.valueOf(f), Collectors.counting()));

		System.out.println("No Fish: " + occ.entrySet().stream().map(e -> e.getValue() * countFish(e.getKey()))
				.mapToLong(Long::longValue).sum());
	}

	static int countFish(int startAge) {
		int spawn = 0;
		for (int i = startAge + 1; i <= _days; i += 7) {
			spawn += countSpawn(i);
		}
		return spawn + 1;
	}

	static int countSpawn(int birthDay) {
		int sum = 0;
		if (_days - birthDay > 8) {
			for (int i = birthDay + 9; i <= _days; i += 7) {
				sum += countSpawn(i);
			}
			return sum + 1;
		}
		return 1;
	}

}
