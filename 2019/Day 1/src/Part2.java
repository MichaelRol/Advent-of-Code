import java.util.ArrayList;

public class Part2 {
	public static int run(ArrayList<Integer> input) {
		int total = 0;
		for (int i = 0; i < input.size(); i++) {
			total += fuelReq(input.get(i));
		}
		return total;
	}
	
	private static int fuelReq(int num) {
		num /= 3;
		num = (int) Math.floor(num) - 2;
		if (num <= 0) {
			return 0;
		} else {
			return num + fuelReq(num);
		}
	}

}
