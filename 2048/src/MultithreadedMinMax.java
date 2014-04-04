import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class MultithreadedMinMax 
{
	
	public static boolean CutOff(Node n, int currentDepth, int maxDepth)
	{
		Board b = (Board)n.data;
		return (currentDepth > maxDepth || Board.Lost(b.board));	
	}
	
	public static Node MinMax(Node n, int maxDepth) 
	{		
		Board startingState = (Board)n.data;		
		ArrayList<Board> tiles = Board.generateStatesA(startingState);
		ArrayList<Node> successors = new ArrayList<Node>(); 
		MinMaxThread.dt = new DecisionTree();
		for (Board board : tiles)
		{
			Node childNode = new Node(board);					
			successors.add(childNode);
		}
		double value = -999999999;		
		double previousBest = 0;
		
		ArrayList<MinMaxThread> workers = new ArrayList<MinMaxThread>();
		
		Node currentChoice = successors.get(0);
		for (Node aNode : successors)
		{
			workers.add(new MinMaxThread(aNode, maxDepth));
		}
		
		ExecutorService es = Executors.newFixedThreadPool(successors.size());
		
		for (MinMaxThread worker : workers)
		{
			es.execute(worker);
		}	
		 es.shutdown();
	    while (!es.isTerminated()) {
       }		
		
		for (MinMaxThread worker : workers)
		{
			value = Math.max(value , worker.score);
			if (value != previousBest)
			{
				previousBest = value;
				currentChoice = worker.startingState;
			}
		}
		es.shutdown();
		return currentChoice;
	}
	
}

