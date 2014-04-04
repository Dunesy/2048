import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class DecisionTree
{	
	ConcurrentHashMap <Long,Node> nodeSet;
	
	DecisionTree()
	{		
		nodeSet = new ConcurrentHashMap<Long,Node>();			
	}
	
	public Node addNode(Node node)
	{
		if (nodeSet.containsKey(node.Key()))
			return nodeSet.get(node.Key());
		else
		{
			nodeSet.put(node.Key(), node);
			return node;
		}
	}
	
}
