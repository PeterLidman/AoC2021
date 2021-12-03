package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L03a {
	private static List<String> binary;

	public static void run(String ext) {
		try {
			binary = Files.readAllLines(Paths.get("./src/AoC2021/L03input" + ext + ".txt")).stream()
					.map(String::valueOf).collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}
		int gamma = 0, epsilon = 0, bas = 1;
		for (int i = binary.get(0).length(); i > 0; i--) {
			int c = 0;
			for (int a = 0; a < binary.size(); a++) {
				c += binary.get(a).charAt(i - 1) == '1' ? 1 : -1;
			}
			if (c > 0) { // mest ettor
				gamma += bas;
			} else {
				epsilon += bas;
			}
			bas *= 2;
//			System.out.println(i + " " + c);
		}
		System.out.println("Gamma: " + gamma + " Epsilon: " + epsilon + " = " + (epsilon*gamma));
	}
}