package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class L15a {
	private static List<String> matrix;
	private static int[][] grid;
	private static int[][] dist;
	private static int col;
	private static int row;
	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	record Cell(int x, int y, int dist) {
	}

	public static void run(String ext) throws IOException {
		matrix = Files.readAllLines(Paths.get("./src/AoC2021/L15input" + ext + ".txt")).stream()
				.collect(Collectors.toList());

		col = matrix.get(0).length();
		row = matrix.size();
		grid = new int[col][row];
		dist = new int[col][row];

		for (int j = 0; j < col; j++) {
			for (int i = 0; i < row; i++) {
				grid[i][j] = Integer.valueOf(matrix.get(j).substring(i, i + 1));
				dist[i][j] = Integer.MAX_VALUE;
			}
		}
		System.out.println("lowest total risk: " + (lowestRisk() - dist[0][0]));
	}

	private static int lowestRisk() {
		dist[0][0] = grid[0][0];
		PriorityQueue<Cell> pq = new PriorityQueue<>(row * col, new distComp());
		pq.add(new Cell(0, 0, dist[0][0]));

		while (!pq.isEmpty()) {
			Cell curr = pq.poll();
			for (int i = 0; i < 4; i++) {
				int rows = curr.x + dx[i];
				int cols = curr.y + dy[i];
				if (isInside(rows, cols)) {
					if (dist[rows][cols] > dist[curr.x][curr.y] + grid[rows][cols]) {
						if (dist[rows][cols] != Integer.MAX_VALUE) {
							Cell adj = new Cell(rows, cols, dist[rows][cols]);
							pq.remove(adj);
						}
						dist[rows][cols] = dist[curr.x][curr.y] + grid[rows][cols];
						pq.add(new Cell(rows, cols, dist[rows][cols]));
					}
				}
			}
		}
		return dist[row - 1][col - 1];
	}

	static boolean isInside(int i, int j) {
		return i >= 0 && i < row && j >= 0 && j < col;
	}

	static class distComp implements Comparator<Cell> {

		@Override
		public int compare(Cell o1, Cell o2) {
			if (o1.dist < o2.dist) {
				return -1;
			} else if (o1.dist > o2.dist) {
				return 1;
			}
			return 0;
		}
	}
}