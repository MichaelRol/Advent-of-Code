import java.util.LinkedList;
import java.util.List;

public class TreeNode implements Comparable<TreeNode>{

    String data;
    TreeNode parent;
    List<TreeNode> children;

    public TreeNode(String data) {
        this.data = data;
        this.children = new LinkedList<TreeNode>();
    }

    public TreeNode addChild(TreeNode child) {
        TreeNode childNode = child;
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    
    public static int distanceFromRoot(TreeNode node) {
    	int distance = 0;
    	while (node.parent != null) {
    		node = node.parent;
    		distance++;
    	}
    	return distance;
    }    
    
    public static int distanceFromNode(TreeNode start, TreeNode end) {
    	int distance = 0;
    	while (start.parent != end) {
    		start = start.parent;
    		distance++;
    	}
    	return distance;
    }

	@Override
	public int compareTo(TreeNode o) {
		if (this.data == o.data) {
			return 1; 
		}
		return 0;
	}


}