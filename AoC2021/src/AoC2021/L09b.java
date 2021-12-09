package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class L09b {
	private static List<String> smoke;
	private static int rows;
	private static int cols;

	public static void run(String ext) throws IOException {
		smoke = Files.readAllLines(Paths.get("./src/AoC2021/L09input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		rows = smoke.size();
		cols = smoke.get(0).length();
		List<Integer> top3 = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				top3.add(getLow(i, j));
			}
		}
		top3.sort(Comparator.naturalOrder());
		System.out.println("Product of basins: "
				+ top3.get(top3.size() - 1) * top3.get(top3.size() - 2) * top3.get(top3.size() - 3));
	}

	static int getLow(int x, int y) {
		int v = Integer.valueOf(smoke.get(x).substring(y, y + 1));
		int top = x > 0 ? Integer.valueOf(smoke.get(x - 1).substring(y, y + 1)) : 10;
		int bot = x < rows - 1 ? Integer.valueOf(smoke.get(x + 1).substring(y, y + 1)) : 10;
		int left = y > 0 ? Integer.valueOf(smoke.get(x).substring(y - 1, y)) : 10;
		int right = y < cols - 1 ? Integer.valueOf(smoke.get(x).substring(y + 1, y + 2)) : 10;
		return v < top && v < bot && v < left && v < right ? getBasin(x, y) : 0;
	}

	private static int getBasin(int x, int y) {
		int v = Integer.valueOf(smoke.get(x).substring(y, y + 1));
		if (v == 9) {
			return 0;
		}
		v = 1;
		String b = smoke.get(x);
		smoke.set(x, b.substring(0, y) + "9" + b.substring(y + 1));
		v += x > 0 ? getBasin(x - 1, y) : 0;
		v += x < rows - 1 ? getBasin(x + 1, y) : 0;
		v += y > 0 ? getBasin(x, y - 1) : 0;
		v += y < cols - 1 ? getBasin(x, y + 1) : 0;
		return v;
	}

}
