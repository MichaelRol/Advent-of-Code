import java.util.ArrayList;

public class Amp {
	int input;
	int phase;
	
	public Amp (int input, int phase) {
		this.input = input;
		this.phase = phase;
	}
	
	public int run(ArrayList<Integer> instructions) {
		int i = 0;
		int output = 0;
		Boolean first = true;
		while (i < instructions.size()) {
			int opcode = instructions.get(i) % 10;
			int mode1 = (instructions.get(i)/100) % 10;
			int mode2 = (instructions.get(i)/1000) % 10;
			int para1 = 0;
			int para2 = 0;
			int para3 = 0;
			
			if (opcode == 9) {
				return output;
			}
			
			if (mode1 == 0) {
				para1 = instructions.get(instructions.get(i+1));
			} else {
				para1 = instructions.get(i+1);
			}
			
			if (opcode != 3 && opcode != 4) {
				if (mode2 == 0) {
					para2 = instructions.get(instructions.get(i+2));
				} else {
					para2 = instructions.get(i+2);
				}
				para3 = instructions.get(i+3);
			}
			switch (opcode) {
			case 1:
				instructions.set(para3, para1 + para2);
				i += 4;
				break;
			case 2:
				instructions.set(para3, para1 * para2);
				i += 4;
				break;
			case 3:
				int toUse;
				if (first) {
					toUse = phase;
					first = false;
				} else {
					toUse = input;
				}
				instructions.set(instructions.get(i+1), toUse);
				i += 2;
				break;
			case 4:
				output = instructions.get(instructions.get(i+1));
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
					instructions.set(para3, 1);
				} else {
					instructions.set(para3, 0);
				}
				i += 4;
				break;
			case 8:
				if (para1 == para2) {
					instructions.set(para3, 1);
				} else {
					instructions.set(para3, 0);
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
