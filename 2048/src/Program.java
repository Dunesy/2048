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
		double value = -999999999;
		double value2 = 999999999;
		
		double previousBest = 0;
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
	
	public static double MinValue(DecisionTree dt, Node state, int currentDepth, int maxDepth, double alpha, double beta )
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
		
		if (successors.size() == 0)
		{
			return 0;
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

	public static double MaxValue(DecisionTree dt, Node state, int currentDepth, int maxDepth, double alpha, double beta)
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
		int[][] startingBoard = new int[4][4];
		Random generator = new Random(System.currentTimeMillis());
		boolean gameWon = false;
		/*startingBoard[3][0] = 4;
		startingBoard[3][1] = 16;
		startingBoard[3][2] = 16;
		startingBoard[3][3] = 4;
		//Board b = new Board(Board.SlideUp(startingBoard));
		*/
		
		startingBoard[1][1] = 4;
		BoardWindow window = new BoardWindow();
		window.setVisible(true);
		int counter = 0;
		int gameCount = 0;
		
		while (!gameWon)
		{
			gameCount++;
			counter = 0;
			Node currentState = new Node(new Board(startingBoard));
			while (true)
			{
				Board b = (Board)currentState.data;
				System.out.println(Board.PrintOut(b));
				if (Board.Won(b.board) || Board.Lost(b.board))
				{
					if (Board.Won(b.board))
					{
						gameWon = true;
					}
					break;
				}
				//Player A Slides
				Node nextChoice = MinMax(currentState, 6);
				//Player B Randomly Places Tile
				Board choice = (Board)nextChoice.data;
				ArrayList<Board> choices;
										
				
				double p = generator.nextDouble() ;
				if (p < 0.75)
				{
					choices = Board.generateStatesB(choice);
				}
				else
				{
					choices = Board.generateStatesD(choice);
				}
				int index = (int)(generator.nextDouble() * (double)choices.size());	
				if (choices.size() > 0)
				{
					currentState = new Node(choices.get(index));				
				}			
				window.setTiles((Board)currentState.data);
				//Thread.sleep(100);
				window.repaint();
				counter ++;
			}
		
			
		}
		System.out.println(counter);
		System.out.println(gameCount);
	}
	
}
