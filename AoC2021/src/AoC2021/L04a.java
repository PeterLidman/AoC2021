package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class L04a {
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

		int score = 0, bestIndex = 1_000;
		for (bingoBoard b : bb) {
			int index = b.winsAtIndex();
			if (index < bestIndex) {
				bestIndex = index;
				score = b.scoreAtIndex(bestIndex);
			}
		}
		System.out.println("Score: " + score + " at index: " + bestIndex);
	}
}

class bingoBoard {
	private List<Integer> _draw;
	private List<Integer> _board;

	bingoBoard(List<Integer> draw, List<Integer> board) {
		_draw = new ArrayList<>(draw);
		_board = new ArrayList<>(board);
	}

	public int winsAtIndex() {
		int i, besti = 1_000;

		for (int jj = 0; jj < 5; jj++) {
			i = 0;
			for (int ii = 0 + jj * 5; ii < 5 + jj * 5; ii++) { // horisontell
				int v = _draw.indexOf(_board.get(ii));
				i = v < 0 ? 1_000 : Math.max(i, v);
			}
			besti = i < 999 ? Math.min(i, besti) : besti;
			i = 0;
			for (int ii = 0 + jj; ii < 25 + jj; ii += 5) { // vertikal
				int v = _draw.indexOf(_board.get(ii));
				i = v < 0 ? 1_000 : Math.max(i, v);
			}
			besti = i < 999 ? Math.min(i, besti) : besti;
		}
		return besti;
	}

	public int scoreAtIndex(int index) {
		int unmarkedSum = 0;

		for (Integer a : _board) {
			if (_draw.indexOf(a) > index || _draw.indexOf(a) < 0) {
				unmarkedSum += a;
			}
		}
		return unmarkedSum * _draw.get(index);
	}
}
