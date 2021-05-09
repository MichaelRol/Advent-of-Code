import java.util.ArrayList;

public class Part2 {
	public static int run(ArrayList<Integer> input) {
		for (int x = 0; x <= 99; x++) {
			for (int y = 0; y <= 99; y++) {
				ArrayList<Integer> inputToUse = new ArrayList<Integer>(input);
				inputToUse.set(1, x);
				inputToUse.set(2, y);
				for (int i = 0; i < inputToUse.size(); i += 4) {
					if (inputToUse.get(i) == 1) {
						inputToUse.set(inputToUse.get(i+3), inputToUse.get(inputToUse.get(i+1)) + inputToUse.get(inputToUse.get(i+2)));
					} else if (inputToUse.get(i) == 2) {
						inputToUse.set(inputToUse.get(i+3), inputToUse.get(inputToUse.get(i+1)) * inputToUse.get(inputToUse.get(i+2)));
					} else if (inputToUse.get(i) == 99) {
						if (inputToUse.get(0) == 19690720) {
							return 100 * x + y;
						}
					}
				}
			}
		}
		
		return 0;
	}
}
