package AoC2021;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class L02b {
	private static List<String> cmd;

	public static void run(String ext) {
		try {
			cmd = Files.readAllLines(Paths.get("./src/AoC2021/L02input" + ext + ".txt")).stream().map(String::valueOf)
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.out.println("Fel vid filimport: " + e);
		}
		int depth = 0, forward = 0, aim=0;

		for (int a = 0; a < cmd.size(); a++) {
//			System.out.println(cmd.get(a));
			String c[] = cmd.get(a).split(" ");
			if (c[0].equals("up")) {
				aim -= Integer.valueOf(c[1]);
			} else if (c[0].equals("down")) {
				aim += Integer.valueOf(c[1]);
			} else {
				forward += Integer.valueOf(c[1]);
				depth += aim * Integer.valueOf(c[1]);
			}
		}
		System.out.println("Product of depth and forward: " + (depth * forward));
	}

}
