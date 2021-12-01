package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class L01b {
	private static List<Integer> depth;

	public static void run(String ext) {
		try {
			depth = Files.readAllLines(Paths.get("./src/AoC2021/L01input" + ext + ".txt")).stream()
					.map(Integer::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("fel vid filimport: " + e);
		}

		List<Integer> threeSum = new ArrayList<>();
		for (int a = 0; a < depth.size() - 2; a++) {
			threeSum.add(depth.get(a) + depth.get(a + 1) + depth.get(a + 2));
		}

		int currentDepth = threeSum.get(0);
		int noIncreased = 0;
		int newDepth;
		for (int a = 1; a < threeSum.size(); a++) {
			newDepth = threeSum.get(a);

			if (currentDepth < newDepth) {
				noIncreased++;
			}
			currentDepth = newDepth;

		}
		System.out.println("Depth increased " + noIncreased + " times");
	}
}
