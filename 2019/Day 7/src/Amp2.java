import java.util.ArrayList;

public class Amp2 {
	int input;
	ArrayList<Integer> instructions;
	Boolean halt;
	int output;
	int i;
	Boolean pause;
	String name;
	
	public Amp2 (int input, ArrayList<Integer> instructions) {
		this.input = input;
		this.instructions = instructions;
		this.halt = false;
		this.i = 0;
	}
	
	public void run() {
		this.pause = false;
		while (i < instructions.size()) {
			int opcode = instructions.get(i) % 10;
			int mode1 = (instructions.get(i)/100) % 10;
			int mode2 = (instructions.get(i)/1000) % 10;
			int para1 = 0;
			int para2 = 0;
			int para3 = 0;

			if (opcode == 9) {
				this.halt = true;
				return;
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
				if (!pause) {
					instructions.set(instructions.get(i+1), input);
					i+=2;
					pause = true;
					break;
				} else {
					return;
				}
			case 4:
				this.output = instructions.get(instructions.get(i+1));
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
				return;
			}
		}
		return;
	}

}
