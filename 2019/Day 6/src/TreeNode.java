import java.util.LinkedList;
import java.util.List;

public class TreeNode{

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


}