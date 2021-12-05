package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class L05b {
	private static List<String> _lines;
	private static HashMap<Position, Integer> _vents;

	public static void run(String ext) {
		try {
			_lines = Files.readAllLines(Paths.get("./src/AoC2021/L05input" + ext + ".txt")).stream()
					.map(String::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}

		_vents = new HashMap<>();

		for (int a = 0; a < _lines.size(); a++) {
			int x1, x2, y1, y2;
			String l[], p1[], p2[];
			l = _lines.get(a).trim().split(" -> ");
			p1 = l[0].split(",");
			p2 = l[1].split(",");
			x1 = Integer.valueOf(p1[0]);
			y1 = Integer.valueOf(p1[1]);
			x2 = Integer.valueOf(p2[0]);
			y2 = Integer.valueOf(p2[1]);
			addLine(x1, y1, x2, y2);
		}
		System.out.println("Overlapping: " + _vents.values().stream().filter(v -> v > 1).count());
	}

	private static void addLine(int x1, int y1, int x2, int y2) {
		if (x1 == x2) { // vertikal
			int ystart = y1 < y2 ? y1 : y2;
			int ystop = y1 < y2 ? y2 : y1;
			for (int y = ystart; y <= ystop; y++) {
				increment(x1, y);
			}
		} else if (y1 == y2) { // horisontell
			int xstart = x1 < x2 ? x1 : x2;
			int xstop = x1 < x2 ? x2 : x1;
			for (int x = xstart; x <= xstop; x++) {
				increment(x, y1);
			}
		} else { // diagonal
			int xstart = x1 < x2 ? x1 : x2;
			int xstop = x1 < x2 ? x2 : x1;
			int ystart = x1 < x2 ? y1 : y2;// kolla på x, vilket y vi tar
			int ystop = x1 < x2 ? y2 : y1;
			if (ystart < ystop) { // increase y
				for (int x = xstart, y = ystart; x <= xstop; x++, y++) {
					increment(x, y);
				}
			} else { // decrease y
				for (int x = xstart, y = ystart; x <= xstop; x++, y--) {
					increment(x, y);
				}
			}
		}
	}

	private static void increment(int x, int y) {
		Integer activity;
		if (null != (activity = _vents.get(new Position(x, y)))) {
			_vents.put(new Position(x, y), ++activity);
		} else {
			_vents.put(new Position(x, y), 1);
		}
	}
}
