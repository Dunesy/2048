import java.util.ArrayList;

public class MinMaxThread implements Runnable
{
	static DecisionTree dt = new DecisionTree(); 
	Node startingState;
	double score = 0;
	public int depth = 0;
	public boolean complete = false;
	public double alpha, beta;
	
	MinMaxThread(Node n, int maxDepth)
	{
		dt.addNode(n);
		startingState = n;
		depth = maxDepth;
		alpha = -99999999;
		beta =   99999999;
	}
	
	@Override
	public void run()
	{
		score = MinValue(startingState, 1, depth, alpha , beta);		
	}
	
	public static boolean CutOff(Node n, int currentDepth, int maxDepth)
	{
		Board b = (Board)n.data;
		return (currentDepth > maxDepth || Board.Lost(b.board));	
	}
	
	public double MinValue(Node state, int currentDepth, int maxDepth, double alpha, double beta)
	{
		if (CutOff(state, currentDepth, maxDepth))
			return state.Evaluate(); 
	
		if (state.getScore() > 0)
			return state.getScore();
		
		Board b = (Board)state.data;
		ArrayList<Board> tiles = Board.generateStatesB(b);
		ArrayList<Node> successors = new ArrayList<Node>(); 		
		for (Board board : tiles)
		{
			Node childNode = new Node(board);			
			childNode = dt.addNode(childNode);
			successors.add(childNode);
		}		
		if (successors.size() == 0)
		{
			return 0;
		}
		double value = 0;
		for (int i = 0 ; i < successors.size(); i++)
		{
			double probabilityOfEvent = (i < successors.size() / 2 ? 0.9 : 0.1) * 2.0 / ((double)successors.size());
			value += probabilityOfEvent * MaxValue(successors.get(i), currentDepth + 1, maxDepth, alpha , beta);					
		}			
		//beta = Math.min(beta , value);		
		state.setScore(value);
		successors.clear();
		return value;
	}

	public double MaxValue(Node state, int currentDepth, int maxDepth, double alpha, double beta)
	{
		if (CutOff(state, currentDepth, maxDepth))
			return state.Evaluate();

		if (state.getScore() > 0)
			return state.getScore();
		
		Board b = (Board)state.data;
		ArrayList<Board> tiles = Board.generateStatesA(b);
		ArrayList<Node> successors = new ArrayList<Node>(); 
		for (Board board : tiles)
		{
			Node childNode = new Node(board);
			childNode = dt.addNode(childNode);			
			successors.add(childNode);
		}	
		double value = 0;
		for (Node n : successors)
		{
			value = Math.max(value , MinValue(n, currentDepth + 1, maxDepth, alpha, beta));
			//if (beta <= alpha)
			//	break;	
		}
		state.setScore(value);
		
		successors.clear();
		return value;

	}
}	
