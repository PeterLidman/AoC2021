package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class L12b {
	private static List<String> nod;
	private static List<Rum> graf;
	private static Set<String> mayVisitAgain;

	public static void run(String ext) throws IOException {
		nod = Files.readAllLines(Paths.get("./src/AoC2021/L12input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		graf = new ArrayList<>();
		mayVisitAgain = new HashSet<>();
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
		int c = countRoutes(s, new ArrayList<String>(), mayVisitAgain);
		System.out.println("Number of paths: " + c);
	}

	private static int countRoutes(Rum rum, List<String> visited, Set<String> may) {
		Set<String> newMay = new HashSet<>(may);

		if (rum.namn.equals("end")) {
			visited.add(rum.namn);
			return 1;
		}
		if (rum.namn.charAt(0) >= 'a' && rum.namn.charAt(0) <= 'z' && visited.contains(rum.namn)) {
			if (rum.namn.equals("start")) {
				return 0;
			}
			if (newMay.contains(rum.namn)) {
				newMay.clear();
			} else {
				return 0;
			}
		}
		List<String> newVisited = new ArrayList<>(visited);
		newVisited.add(rum.namn);
		int c = 0;
		for (Rum passage : rum.passage) {
			c += countRoutes(passage, newVisited, newMay);
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
			if (r1.namn.charAt(0) >= 'a' && r1.namn.charAt(0) <= 'z') {
				mayVisitAgain.add(r1.namn);
			}
			graf.add(r1);
		}
		if (r2 == null) {
			r2 = new Rum(rumB);
			if (r2.namn.charAt(0) >= 'a' && r2.namn.charAt(0) <= 'z') {
				mayVisitAgain.add(r2.namn);
			}
			graf.add(r2);
		}
		r1.addPassage(r2);
		r2.addPassage(r1);
	}

}
