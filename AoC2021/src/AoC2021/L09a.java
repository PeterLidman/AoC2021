package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L09a {
	private static List<String> smoke;
	private static int rows;
	private static int cols;

	public static void run(String ext) throws IOException {
		smoke = Files.readAllLines(Paths.get("./src/AoC2021/L09input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		rows = smoke.size();
		cols = smoke.get(0).length();
		int sum = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				sum += getLow(i, j);
			}
		}
		System.out.println("Sum risk levels: " + sum);
	}

	static int getLow(int x, int y) {
		int v = Integer.valueOf(smoke.get(x).substring(y, y + 1));
		int top = x > 0 ? Integer.valueOf(smoke.get(x - 1).substring(y, y + 1)) : 10;
		int bot = x < rows - 1 ? Integer.valueOf(smoke.get(x + 1).substring(y, y + 1)) : 10;
		int left = y > 0 ? Integer.valueOf(smoke.get(x).substring(y - 1, y)) : 10;
		int right = y < cols - 1 ? Integer.valueOf(smoke.get(x).substring(y + 1, y + 2)) : 10;
		return v < top && v < bot && v < left && v < right ? v + 1 : 0;
	}

}
