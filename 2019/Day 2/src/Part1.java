import java.util.ArrayList;

public class Part1 {
	public static int run(ArrayList<Integer> input) {
		input.set(1, 12);
		input.set(2, 2);
		for (int i = 0; i < input.size(); i += 4) {
			if (input.get(i) == 1) {
				input.set(input.get(i+3), input.get(input.get(i+1)) + input.get(input.get(i+2)));
			} else if (input.get(i) == 2) {
				input.set(input.get(i+3), input.get(input.get(i+1)) * input.get(input.get(i+2)));
			} else if (input.get(i) == 99) {
				return(input.get(0));
			} else {
				System.out.println("Problem.");
			}
		}
		return 0;
	}
}
