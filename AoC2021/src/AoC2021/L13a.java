package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class L13a {
	private static List<String> dots;

	record dot(int x, int y) {
	}

	record fold(char direction, int position) {
	}

	static List<dot> ld;
	static List<fold> lf;
	static Pattern pd = Pattern.compile("(\\d+),(\\d+)");
	static Pattern pf = Pattern.compile("fold along ([xy])=(\\d+)");

	public static void run(String ext) throws IOException {
		dots = Files.readAllLines(Paths.get("./src/AoC2021/L13input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		ld = new ArrayList<>();
		lf = new ArrayList<>();
		for (String a : dots) {
			if (a.length() > 0) {
				if (a.charAt(0) == 'f') {
					Matcher m = pf.matcher(a);
					m.find();
					lf.add(new fold(m.group(1).charAt(0), Integer.valueOf(m.group(2))));
				} else {
					Matcher m = pd.matcher(a);
					m.find();
					ld.add(new dot(Integer.valueOf(m.group(1)), Integer.valueOf(m.group(2))));
				}
			}
		}
		foldAt(lf.get(0).direction, lf.get(0).position);
		System.out.println(ld.size() + " dots are visible");
	}

	static void foldAt(char d, int at) {
		List<dot> tdots = new ArrayList<>(ld);
		if (d == 'y') {
			for (int i = 0; i < tdots.size(); i++) {
				if (tdots.get(i).y > at) { // flytta upp
					dot td = new dot(tdots.get(i).x, 2 * at - tdots.get(i).y);
					if (!ld.contains(td)) {
						ld.add(td);
					}
					ld.remove(new dot(tdots.get(i).x, tdots.get(i).y));
				}
			}
		} else {
			for (int i = 0; i < tdots.size(); i++) {
				if (tdots.get(i).x > at) { // flytta in
					dot td = new dot(2 * at - tdots.get(i).x, tdots.get(i).y);
					if (!ld.contains(td)) {
						ld.add(td);
					}
					ld.remove(new dot(tdots.get(i).x, tdots.get(i).y));
				}
			}
		}
	}

	static void show() {
		int maxx = 0, maxy = 0;
		for (int i = 0; i < ld.size(); i++) {
			if (ld.get(i).x > maxx) {
				maxx = ld.get(i).x;
			}
			if (ld.get(i).y > maxy) {
				maxy = ld.get(i).y;
			}
		}
		for (int y = 0; y <= maxy; y++) {
			for (int x = 0; x <= maxx; x++) {
				System.out.print(ld.contains(new dot(x, y)) ? "#" : ".");
			}
			System.out.println();
		}
		System.out.println();
	}
}