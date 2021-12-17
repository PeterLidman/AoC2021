package AoC2021;

public class L17a {
	static int targetx;
	static int targety;
	static int maxy;

	public static void run(String ext) {
		// anta att vi kommer till xv=0, faller rakt ner
		// hitta max yv

		// 6-7 på xv
		// 6+5+4+3+2+1=21
		// 7+6+5+4+3+2+1=28
		// 8+7+6+5+4+3+2+1=36
		// 9+8+7+6+5+4+3+2+1=45
		// 10+9+8+7+6+5+4+3+2+1=55
		// 11+10+9+8+7+6+5+4+3+2+1=66
		// 12+11+10+9+8+7+6+5+4+3+2+1=78
		// 13+12+11+10+9+8+7+6+5+4+3+2+1=91
		// 14+13+12+11+10+9+8+7+6+5+4+3+2+1=105
		// 11-13 på xv

		int maxyy = 0;
		int c = 0;
		for (int yv = 0; yv < 99999; yv++) {
//		for (int xv = 6; xv <= 7; xv++) {
			for (int xv = 11; xv <= 13; xv++) {
				maxy = 0;
				c += simulate(xv, yv) ? 1 : 0;
				System.out.println("xv= " + xv + " yv " + yv + "ok =? " + c);
				System.out.println("Taget x= " + targetx + " y= " + targety + " maxy " + maxy);
				maxyy = Math.max(maxyy, maxy);
			}
		}
		System.out.println("maxxy" + maxyy);
	}

	static boolean simulate(int xv, int yv) {
		int x = 0, y = 0, max = -999;
		while (true) {
			x += xv;
			y += yv;
			max = Math.max(max, y);
			if (isInsideArea(x, y)) {
				targetx = x;
				targety = y;
				maxy = max;
				return true;
			}
			if (isOutside(x, y)) {
				return false;
			}
			if (xv != 0) { // drag
				if (xv > 0) {
					xv--;
				} else {
					xv++;
				}
			}
			yv--; // gravity
		}
	}

	static boolean isOutside(int x, int y) {
//		target area: x=20..30, y=-10..-5
//		return x > 30 || y < -10;

//		target area: x=60..94, y=-171..-136
		return x > 94 || y < -171;
	}

	static boolean isInsideArea(int x, int y) {
//		target area: x=20..30, y=-10..-5
//		return x >= 20 && x <= 30 && y >= -10 && y <= -5;

//		target area: x=60..94, y=-171..-136
		return x >= 60 && x <= 94 && y >= -171 && y <= -136;
	}
}