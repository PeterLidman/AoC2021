package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class L10a {
	private static List<String> _chunks;

	public static void run(String ext) throws IOException {
		_chunks = Files.readAllLines(Paths.get("./src/AoC2021/L10input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		int score = 0;
		for (String a : _chunks) {
			score += getFirstCorruptionScore(a);
		}
		System.out.println("Corruption score: " + score);
	}

	private static int getFirstCorruptionScore(String a) {
		LinkedList<Character> q = new LinkedList<Character>();
		char[] s = a.toCharArray();

		for (int i = 0; i < a.length(); i++) {
			if (s[i] == '{' || s[i] == '(' || s[i] == '<' || s[i] == '[') {
				q.addLast(s[i]);
			} else {
				Character r = stop(q.removeLast());
				if (s[i] != r) {
					if (s[i] == ')') {
						return 3;
					}
					if (s[i] == ']') {
						return 57;
					}
					if (s[i] == '}') {
						return 1197;
					}
					if (s[i] == '>') {
						return 25137;
					}
				}
			}
		}
		return 0;
	}

	static Character stop(Character c) {
		switch (c) {
		case '(':
			return ')';
		case '[':
			return ']';
		case '<':
			return '>';
		case '{':
			return '}';
		}
		return c;
	}

}
