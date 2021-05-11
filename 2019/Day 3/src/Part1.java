import java.util.List;
import java.util.ArrayList;
public class Part1 {
	public static int run(List<String> wire1str, List<String> wire2str) {
		ArrayList<int[]> wire1 = new ArrayList<int[]>();
		ArrayList<int[]> wire2 = new ArrayList<int[]>();
		
		int[] wire1curr = {0, 0};
		int[] wire2curr = {0, 0};
		for (String cmd : wire1str) {
			if (cmd.charAt(0) == 'U') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[1]++;
					wire1.add(wire1curr.clone());
				}
			} else if (cmd.charAt(0) == 'D') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[1]--;
					wire1.add(wire1curr.clone());
				}
			} else if (cmd.charAt(0) == 'R') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[0]++;
					wire1.add(wire1curr.clone());
				}
			} else if (cmd.charAt(0) == 'L') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[0]--;
					wire1.add(wire1curr.clone());
				}
			}
		}
		int min = Integer.MAX_VALUE;
		for (String cmd : wire2str) {
			if (cmd.charAt(0) == 'U') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[1]++;
					wire2.add(wire2curr.clone());
				}
			} else if (cmd.charAt(0) == 'D') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[1]--;
					wire2.add(wire2curr.clone());
				}
			} else if (cmd.charAt(0) == 'R') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[0]++;
					wire2.add(wire2curr.clone());
				}
			} else if (cmd.charAt(0) == 'L') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[0]--;
					wire2.add(wire2curr.clone());
				}
			}
			
		}
		
		for (int x = 0; x < wire1.size(); x++) {
			for (int y = 0; y < wire2.size(); y++) {
				if (wire1.get(x)[0] == wire2.get(y)[0] && wire1.get(x)[1] == wire2.get(y)[1]) {
//					System.out.println(wire1.get(x)[0] + ", " + wire1.get(x)[1]);
					if (Math.abs(wire1.get(x)[0]) + Math.abs(wire1.get(x)[1]) < min) {
						min = Math.abs(wire1.get(x)[0]) + Math.abs(wire1.get(x)[1]); 
					}
				}
			}
		}
		return min;
	}
}
