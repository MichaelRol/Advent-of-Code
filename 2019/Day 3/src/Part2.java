import java.util.List;
import java.util.Map;
import java.util.HashMap;
public class Part2 {
	public static int run(List<String> wire1str, List<String> wire2str) {
		Map<String, Integer> wire1 = new HashMap<String, Integer>();
		
		int[] wire1curr = {0, 0};
		int[] wire2curr = {0, 0};
		int steps = 0;
		for (String cmd : wire1str) {
			if (cmd.charAt(0) == 'U') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[1]++;
					wire1.put(wire1curr[0] + " " + wire1curr[1], ++steps);
				}
			} else if (cmd.charAt(0) == 'D') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[1]--;
					wire1.put(wire1curr[0] + " " + wire1curr[1], ++steps);
				}
			} else if (cmd.charAt(0) == 'R') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[0]++;
					wire1.put(wire1curr[0] + " " + wire1curr[1], ++steps);
				}
			} else if (cmd.charAt(0) == 'L') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire1curr[0]--;
					wire1.put(wire1curr[0] + " " + wire1curr[1], ++steps);
				}
			}
		}
		int min = Integer.MAX_VALUE;
		steps = 1;
		for (String cmd : wire2str) {
			if (cmd.charAt(0) == 'U') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[1]++;
					if (wire1.containsKey(wire2curr[0] + " " + wire2curr[1])) {
						if (wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps < min) {
							min = wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps;
						}
					}

					steps++;
				}
			} else if (cmd.charAt(0) == 'D') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[1]--;
					if (wire1.containsKey(wire2curr[0] + " " + wire2curr[1])) {
						if (wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps < min) {
							min = wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps;
						}
					}

					steps++;
				}
			} else if (cmd.charAt(0) == 'R') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[0]++;
					if (wire1.containsKey(wire2curr[0] + " " + wire2curr[1])) {
						if (wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps < min) {
							min = wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps;
						}
					}

					steps++;
				}
			} else if (cmd.charAt(0) == 'L') {
				for (int i = 0; i < Integer.parseInt(cmd.substring(1, cmd.length())); i++) {
					wire2curr[0]--;
					if (wire1.containsKey(wire2curr[0] + " " + wire2curr[1])) {
						if (wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps < min) {
							min = wire1.get(wire2curr[0] + " " + wire2curr[1]) + steps;
						}
					}

					steps++;
				}
			}
			
		}
		return min;
	}
}
