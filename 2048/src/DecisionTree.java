import java.util.Set;
import java.util.HashSet;;


public class DecisionTree
{	
	Set<Node> nodeSet;
	
	DecisionTree()
	{		
		nodeSet = new HashSet<Node>();			
	}
	
	public Node find(Node node)
	{
		for (Node n : nodeSet)
		{
			if (n.equals(node))
				return n;
		}
		return null;
	}
	
	public Node addNode(Node node)
	{
		if (nodeSet.add(node))
		{
			return node;
		}
		else
		{
			return find(node);
		}
	}
	
}
