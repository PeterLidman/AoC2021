package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L11a {
	private static List<String> d;
	private static Integer grid[][] = new Integer[10][10];
	private static int s,c;

	public static void run(String ext) throws IOException {
		d = Files.readAllLines(Paths.get("./src/AoC2021/L11input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		s = d.get(0).length();
		c=0;
		for (int x = 0; x < d.size(); x++) {
			for (int y = 0; y < s; y++) {
				grid[x][y] = Integer.valueOf(d.get(x).substring(y, y + 1));
			}
		}
		for(int i=0; i<100;i++) {
			incAll();
			flash();
		}
		//show();
		System.out.println("Number of flashes: " + c);
	}

	static void show() {
		for (int x = 0; x < d.size(); x++) {
			for (int y = 0; y < s; y++) {
				System.out.print(grid[x][y]);
			}
			System.out.println();
		}
		System.out.println();
	}

	static void incAll() {
		for (int x = 0; x < d.size(); x++) {
			for (int y = 0; y < s; y++) {
				grid[x][y]++;
			}
		}
	}

	static void flash() {
		for (int x = 0; x < d.size(); x++) {
			for (int y = 0; y < s; y++) {
				spreadFlash(x, y);
			}
		}
		for (int x = 0; x < d.size(); x++) {
			for (int y = 0; y < s; y++) {
				if (grid[x][y] < 0) {
					grid[x][y] = 0;
				}
			}
		}
	}

	private static void spreadFlash(int x, int y) {
		if (grid[x][y] > 9) {
			c++;
			grid[x][y] = -1000;
			if (x > 0) {
				grid[x - 1][y]++;
				if (y > 0) {
					grid[x - 1][y - 1]++;
				}
				if (y < 9) {
					grid[x - 1][y + 1]++;
				}
			}
			if (y > 0) {
				grid[x][y - 1]++;
			}
			if (x < 9) {
				grid[x + 1][y]++;
				if (y > 0) {
					grid[x + 1][y - 1]++;
				}
				if (y < 9) {
					grid[x + 1][y + 1]++;
				}
			}
			if (y < 9) {
				grid[x][y + 1]++;
			}

			if (x > 0) {
				spreadFlash(x - 1, y);
				if (y > 0) {
					spreadFlash(x - 1, y - 1);
				}
				if (y < 9) {
					spreadFlash(x - 1, y + 1);
				}
			}
			if (y > 0) {
				spreadFlash(x, y - 1);
			}
			if (x < 9) {
				spreadFlash(x + 1, y);
				if (y > 0) {
					spreadFlash(x + 1, y - 1);
				}
				if (y < 9) {
					spreadFlash(x + 1, y + 1);
				}
			}
			if (y < 9) {
				spreadFlash(x, y + 1);
			}
		}
	}

}
