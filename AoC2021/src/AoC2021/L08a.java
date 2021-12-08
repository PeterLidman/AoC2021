package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class L08a {
	private static List<String> code;

	public static void run(String ext) throws IOException {
		code = Files.readAllLines(Paths.get("./src/AoC2021/L08input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		List<String> c = new ArrayList<>();
		for (String a : code) {
			c.addAll(Arrays.asList(a.split("[|]")[1].trim().split(" ")));
		}
		int count = 0;
		for (String b : c) {
			if (b.length() == 2 || b.length() == 3 || b.length() == 4 || b.length() == 7) {
				count++;
			}
		}
		System.out.println("Answer: " + count);
	}

}
