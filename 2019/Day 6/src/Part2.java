import java.util.ArrayList;
import java.util.HashMap;

public class Part2 {

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
		
		ArrayList<TreeNode> youList = new ArrayList<>();
		ArrayList<TreeNode> sanList = new ArrayList<>();
		
		TreeNode you = nodes.get("YOU");
		TreeNode youNext = you.parent;
		while (youNext.parent != null) {
			youNext = youNext.parent;
			youList.add(youNext);
		}
		
		TreeNode san = nodes.get("SAN");
		TreeNode sanNext = san.parent;
		while (sanNext.parent != null) {
			sanNext = sanNext.parent;
			sanList.add(sanNext);
		}
		TreeNode shared = null;
		for (TreeNode node : youList) {
			if (sanList.contains(node)) {
				shared = node;
				break;
			}
		}
		return TreeNode.distanceFromNode(you, shared) + TreeNode.distanceFromNode(san, shared);
	}
}
