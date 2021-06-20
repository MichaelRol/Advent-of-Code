import java.util.ArrayList;
import java.util.HashMap;

public class Part1 {

	public static int run(ArrayList<String> lines) {
		HashMap<String, TreeNode> nodes = new HashMap<>();
		for (String line : lines) {
			String first = "";
			String second = "";
			try {
			first = line.split("\\)")[0].strip();
			second = line.split("\\)")[1].strip();
			} catch (Exception e) {
				System.out.println(line + e);
			}
			if (!nodes.containsKey(first)) {
				nodes.put(first, new TreeNode(first));
			}
			if (!nodes.containsKey(second)) {
				nodes.put(second, new TreeNode(second));
			}
			
			nodes.get(first).addChild(nodes.get(second));
		}
		int total = 0;
		for (TreeNode node : nodes.values()) {
			total += TreeNode.distanceFromRoot(node);
		}
		return total;
	}

}
