import java.util.ArrayList;
import java.util.Random;


public class Program {
	
	public static boolean CutOff(Node n, int currentDepth, int maxDepth)
	{
		Board b = (Board)n.data;
		return (currentDepth > maxDepth || Board.Won(b.board) || Board.Lost(b.board));	
	}
	
	public static Node MinMax(Node n, int maxDepth) 
	{
		int currentDepth = 0;
		DecisionTree dt = new DecisionTree();
		Board startingState = (Board)n.data;		
		ArrayList<Board> tiles = Board.generateStatesA(startingState);
		ArrayList<Node> successors = new ArrayList<Node>(); 
		for (Board board : tiles)
		{
			Node childNode = new Node(board);
			childNode = dt.addNode(childNode);			
			successors.add(childNode);
		}
		int value = -999999999;
		int value2 = 999999999;
		
		int previousBest = 0;
		Node currentChoice = null;
		for (Node aNode : successors)
		{
			value = Math.max(value , MinValue(dt, aNode, currentDepth + 1, maxDepth, value, value2));
			if (value != previousBest)
			{
				previousBest = value;
				currentChoice = aNode;
			}
		}	
		return currentChoice;
	}
	
	public static int MinValue(DecisionTree dt, Node state, int currentDepth, int maxDepth, int alpha, int beta )
	{
		if (CutOff(state, currentDepth, maxDepth))
			return state.Evaluate(); 

		Board b = (Board)state.data;
		ArrayList<Board> tiles = Board.generateStatesB(b);
		ArrayList<Node> successors = new ArrayList<Node>(); 
		for (Board board : tiles)
		{
			Node childNode = new Node(board);
			childNode = dt.addNode(childNode);			
			successors.add(childNode);
		}
				
		for (Node n : successors)
		{
			beta = Math.min(beta , MaxValue(dt, n, currentDepth + 1, maxDepth, alpha, beta));
			if (beta <= alpha)
			{
				break;
			}
		}
		successors.clear();
		return beta;
	}

	public static int MaxValue(DecisionTree dt, Node state, int currentDepth, int maxDepth, int alpha, int beta)
	{
		if (CutOff(state, currentDepth, maxDepth))
			return state.Evaluate();

		Board b = (Board)state.data;
		ArrayList<Board> tiles = Board.generateStatesA(b);
		ArrayList<Node> successors = new ArrayList<Node>(); 
		for (Board board : tiles)
		{
			Node childNode = new Node(board);
			childNode = dt.addNode(childNode);			
			successors.add(childNode);
		}
		
		for (Node n : successors)
		{
			alpha = Math.max(alpha , MinValue(dt, n, currentDepth + 1, maxDepth, alpha, beta));
			if (beta <= alpha)
				break;
		}
		successors.clear();
		return alpha;

	}
	
	public static void main(String[] args) throws InterruptedException	
	{
		int[][] startingBoard = new int[5][5];
		Random generator = new Random(System.currentTimeMillis());
		//startingBoard[0][2] = 2;
		startingBoard[2][2] = 2;
		//startingBoard[4][2] = 2;
		BoardWindow window = new BoardWindow();
		window.setVisible(true);
		int counter = 0;		
		Node currentState = new Node(new Board(startingBoard));
		while (true)
		{
			Board b = (Board)currentState.data;
			System.out.println(Board.PrintOut(b.board));
			if (Board.Won(b.board) || Board.Lost(b.board))
				break;
			
			//Player A Slides
			Node nextChoice = MinMax(currentState, 4);
			//Player B Randomly Places Tile
			Board choice = (Board)nextChoice.data;
			ArrayList<Board> choices = Board.generateStatesB(choice);
			int index = (int)(generator.nextDouble() * (double)choices.size());
			currentState = new Node(choices.get(index));
			window.setTiles((Board)currentState.data);
			
			Thread.sleep(300);
			window.repaint();
			counter ++;
		}
		
		System.out.println(counter);
		
	}
	
}
