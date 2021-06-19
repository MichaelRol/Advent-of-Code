import java.util.ArrayList;

public class Part1 {
	public static int run(ArrayList<Integer> input) {
		int output = 0;
		for (int i = 0; i < input.size(); i += 4) {
			int opcode = input.get(i) % 10;
			int mode1 = (input.get(i)/100) % 10;
			int mode2 = (input.get(i)/1000) % 10;
			int para1 = 0;
			int para2 = 0;
			int para3 = 0;
			
			if (mode1 == 0 && opcode != 9) {
				para1 = input.get(input.get(i+1));
			} else {
				para1 = input.get(i+1);
			}
			
			if (opcode == 1 || opcode == 2) {
				if (mode2 == 0) {
					para2 = input.get(input.get(i+2));
				} else {
					para2 = input.get(i+2);
				}
				para3 = input.get(i+3);
			}
			
			if (opcode == 1) {
				input.set(para3, para1 + para2);
			} else if (opcode == 2) {
				input.set(para3, para1 * para2);
			} else if (opcode == 3) {
				input.set(input.get(i+1), 1);
				i = i - 2;
			} else if (opcode == 4) {
				output = input.get(input.get(i+1));
				i = i - 2;
			} else if (opcode == 9) {
				return(output);
			} else {
				System.out.println("Problem.");
			}
		}
		return 0;
	}

}
