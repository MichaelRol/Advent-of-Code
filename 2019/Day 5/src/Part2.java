import java.util.ArrayList;

public class Part2 {
	public static int run(ArrayList<Integer> input) {
		int i = 0;
		int output = 0;
		while (i < input.size()) {
			int opcode = input.get(i) % 10;
			int mode1 = (input.get(i)/100) % 10;
			int mode2 = (input.get(i)/1000) % 10;
			int para1 = 0;
			int para2 = 0;
			int para3 = 0;
			
			if (opcode == 9) {
				return output;
			}
			
			if (mode1 == 0) {
				para1 = input.get(input.get(i+1));
			} else {
				para1 = input.get(i+1);
			}
			
			if (opcode != 3 && opcode != 4) {
				if (mode2 == 0) {
					para2 = input.get(input.get(i+2));
				} else {
					para2 = input.get(i+2);
				}
				para3 = input.get(i+3);
			}
			switch (opcode) {
			case 1:
				input.set(para3, para1 + para2);
				i += 4;
				break;
			case 2:
				input.set(para3, para1 * para2);
				i += 4;
				break;
			case 3:
				input.set(input.get(i+1), 5);
				i += 2;
				break;
			case 4:
				output = input.get(input.get(i+1));
				i += 2;
				break;
			case 5:
				if (para1 != 0) {
					i = para2;
				} else {
					i += 3;
				}
				break;
			case 6:
				if (para1 == 0) {
					i = para2;
				} else {
					i += 3;
				}
				break;
			case 7:
				if (para1 < para2) {
					input.set(para3, 1);
				} else {
					input.set(para3, 0);
				}
				i += 4;
				break;
			case 8:
				if (para1 == para2) {
					input.set(para3, 1);
				} else {
					input.set(para3, 0);
				}
				i += 4;
				break;
			default:
				System.out.println("Problem: " + opcode);
				return 0;
			}
		}
		return 0;
	}

}
