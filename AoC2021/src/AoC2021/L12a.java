package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class L12a {
	private static List<String> nod;
	private static List<Rum> graf;

	public static void run(String ext) throws IOException {
		nod = Files.readAllLines(Paths.get("./src/AoC2021/L12input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		graf = new ArrayList<>();
		for (String a : nod) {
			String s[] = a.split("-");
			addToGraf(s[0].trim(), s[1].trim());
		}
		Rum s = null;
		for (Rum b : graf) {
			if (b.namn.equals("start")) {
				s = b;
				break;
			}
		}
		int c = countRoutes(s, new ArrayList<String>());
		System.out.println("Number of paths: " + c);
	}

	private static int countRoutes(Rum rum, List<String> visited) {
		if (rum.namn.equals("end")) {
			visited.add(rum.namn);
			return 1;
		}
		if (rum.namn.charAt(0) >= 'a' && rum.namn.charAt(0) <= 'z' && visited.contains(rum.namn)) {
			return 0;
		}
		List<String> newVisited = new ArrayList<>(visited);
		newVisited.add(rum.namn);
		int c = 0;
		for (Rum passage : rum.passage) {
			c += countRoutes(passage, newVisited);
		}
		return c;
	}

	private static void addToGraf(String rumA, String rumB) {
		Rum r1 = null, r2 = null;

		for (Rum b : graf) {
			if (b.namn.equals(rumA)) {
				r1 = b;
			}
			if (b.namn.equals(rumB)) {
				r2 = b;
			}
		}
		if (r1 == null) {
			r1 = new Rum(rumA);
			graf.add(r1);
		}
		if (r2 == null) {
			r2 = new Rum(rumB);
			graf.add(r2);
		}
		r1.addPassage(r2);
		r2.addPassage(r1);
	}

}
