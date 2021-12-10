package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class L10b {
	private static List<String> _chunks;

	public static void run(String ext) throws IOException {
		_chunks = Files.readAllLines(Paths.get("./src/AoC2021/L10input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		long score = 0;
		List<Long> l= new ArrayList<>();
		for (String a : _chunks) {
			score = getFirstCorruptionScore(a);
			if(score > 0) {
				l.add(score);
			}
		}
		Collections.sort(l);
		System.out.println("Middle score: " + l.get(l.size()/2));
	}

	private static long getFirstCorruptionScore(String a) {
		LinkedList<Character> q = new LinkedList<Character>();
		char[] s = a.toCharArray();

		for (int i = 0; i < a.length(); i++) {
			if (s[i] == '{' || s[i] == '(' || s[i] == '<' || s[i] == '[') {
				q.addLast(s[i]);
			} else {
				Character r = stop(q.removeLast());
				if (s[i] != r) {
					return 0;
				}
			}
		}
		long cs = 0;
		while (q.size() > 0) {
			cs *= 5;
			switch (q.removeLast()) {
			case '(':
				cs++;
				break;
			case '[':
				cs += 2;
				break;
			case '<':
				cs += 4;
				break;
			case '{':
				cs += 3;
				break;
			}
		}
		return cs;
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
