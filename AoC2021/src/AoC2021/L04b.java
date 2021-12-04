package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L04b {
	private static List<String> input;

	public static void run(String ext) {
		try {
			input = Files.readAllLines(Paths.get("./src/AoC2021/L04input" + ext + ".txt")).stream().map(String::valueOf)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}
		List<Integer> id = new ArrayList<>();
		List<bingoBoard> bb = new ArrayList<>();
		Stream.of(input.get(0).split(",")).forEach(n -> id.add(Integer.valueOf(n)));

		int inputRow = 2;
		while (inputRow < input.size()) {
			List<Integer> ib = new ArrayList<>();
			for (int i = inputRow; i < inputRow + 5; i++) {
				Stream.of(input.get(i).trim().split("\\s+")).forEach(n -> ib.add(Integer.valueOf(n)));
			}
			bb.add(new bingoBoard(id, ib));
			inputRow += 6;
		}

		int score = 0, worstIndex = 0;
		for (bingoBoard b : bb) {
			int index = b.winsAtIndex();
			if (index > worstIndex) {
				worstIndex = index;
				score = b.scoreAtIndex(worstIndex);
			}
		}
		System.out.println("Score: " + score + " at index: " + worstIndex);
	}
}
