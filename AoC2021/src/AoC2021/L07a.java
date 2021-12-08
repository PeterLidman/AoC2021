package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L07a {
	private static List<String> _crabs;

	public static void run(String ext) {
		try {
			_crabs = Files.readAllLines(Paths.get("./src/AoC2021/L07input" + ext + ".txt")).stream()
					.map(String::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}
		List<Integer> c = Stream.of(_crabs.get(0).trim().split(",")).map(a -> Integer.valueOf(a))
				.collect(Collectors.toList());

		int fcmin = Integer.MAX_VALUE;
		int fcminpos = 0;
		for (int i = 0; i < c.size(); i++) {
			int fc = 0;
			for (Integer a : c) {
				fc += Math.abs(a - i);
			}
			if (fc < fcmin) {
				fcmin = fc;
				fcminpos = i;
			}
		}
		System.out.println("Minimal fuel= " + fcmin + " at position= " + fcminpos);
	}

}
