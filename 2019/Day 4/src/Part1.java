
public class Part1 {
	public static int run(int lowerBound, int upperBound) {
		int n0 = (lowerBound / 100000) % 10;
		int count = 0;
		for (int a = n0; a < 6; a++) {
			for (int b = a; b < 10; b++) {
				for (int c = b; c < 10; c++) {
					for (int d = c; d < 10; d++) {
						for (int e = d; e < 10; e++) {
							for (int f = e; f < 10; f++) {
								int num = buildNum(a, b, c, d, e, f);
								if (num >= lowerBound && num <= upperBound) {
									if (checkAdjacents(a, b, c, d, e, f)) {
										count++;
									}
								}
							}
						}
					}
				}
			}
		}
		return count;
	}
	
	private static int buildNum(int a, int b, int c, int d, int e, int f) {
		return a * 100000 + b * 10000 + c * 1000 + d * 100 + e * 10 + f;
	}
	
	private static boolean checkAdjacents(int a, int b, int c, int d, int e, int f) {
		if (a == b || b == c || c == d || d == e || e == f) {
			return true;
		}
		return false;
	}
}
