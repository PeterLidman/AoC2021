package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class L03b {
	private static List<String> binary;

	public static void run(String ext) {
		try {
			binary = Files.readAllLines(Paths.get("./src/AoC2021/L03input" + ext + ".txt")).stream()
					.map(String::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}
		List<String> binaryCopy = new ArrayList<>(binary); // shallow copy will suffice

		int o2 = 0, co2 = 0, l = binary.get(0).length();
		int bas = (int) Math.pow(2, l - 1);

		for (int i = 0; i < l; i++) {
			int c = 0;
			for (int a = 0; a < binary.size(); a++) {
				c += binary.get(a).charAt(i) == '1' ? 1 : -1;
			}
			if (c >= 0) { // mest ettor eller lika
				o2 += bas;
				filterList('1', i, binary);
			} else {
				filterList('0', i, binary);
			}
			bas /= 2;
		}

		bas = (int) Math.pow(2, l - 1);
		for (int i = 0; i < l; i++) {
			int c = 0;
			for (int a = 0; a < binaryCopy.size(); a++) {
				c += binaryCopy.get(a).charAt(i) == '1' ? 1 : -1;
			}
			if (binaryCopy.size() == 1) { // specialfall
				co2 += c > 0 ? bas : 0;
			} else if (c >= 0) { // mest ettor eller lika
				filterList('0', i, binaryCopy);
			} else {
				co2 += bas;
				filterList('1', i, binaryCopy);
			}
			bas /= 2;
		}
		System.out.println("O2: " + o2 + " CO2: " + co2 + " = " + (o2 * co2));
	}

	private static void filterList(char keep, int plats, List<String> bin) {
		if (bin.size() == 1) {
			return;
		}
		List<String> temp = new ArrayList<>();
		bin.forEach(a -> {
			if (a.charAt(plats) == keep) {
				temp.add(a);
			}
		});
		bin.clear();
		bin.addAll(temp);
	}

}
