import java.util.ArrayList;
import java.util.Random;


public class Program {
	
	public static Random generator = new Random(System.currentTimeMillis());
	public static final int INIT_DEPTH = 6;
	public static final int MID_DEPTH = 8;
	public static final int LATE_DEPTH = 10;
	public static Board CreateStartingBoard()  
	{
		double p = generator.nextDouble() ;
		Board b = new Board();
		for (int i = 0 ; i < 2; i++)
		{
			if (p < 0.9)
			{
				ArrayList<Board> states = Board.generateStatesC(b);
				b = states.get(generator.nextInt(states.size()));
			}
			else
			{
				ArrayList<Board> states = Board.generateStatesD(b);
				b = states.get(generator.nextInt(states.size()));
			}
		}
		return b;
	}
	
	public static Board CreateTestBoard()
	{
		int[][] tiles = new int[4][4];
		for (int i = 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4; j++)
			{
				tiles[i][j] = i;
			}
		}
		Board b = new Board(tiles);
		return b;
	}
	
	public static void main(String[] args) throws InterruptedException	
	{						
		BoardWindow window = new BoardWindow();
		window.setVisible(true);
			
		int gameCount = 0;
										
			for (int i = 0 ; i < 10 ; i ++)
			{
			Node currentState = new Node(CreateStartingBoard());
			int counter = 0;	
			while (true)
			{			
				Board b = (Board)currentState.data;
				counter ++;
				
				if (Board.Lost(b.board))
				{			
					System.out.println("GameScore " + b.gameScore);
					if (Board.Won(b.board))
					{
						System.out.println("Won");
						gameCount ++;					
					}
					else
					{
						System.out.println("Lost");
					}
					break;
				}
				//Player A Slides
				Node nextChoice = null;
				if (b.Max() < 1024)
				{
					 nextChoice = MultithreadedMinMax.MinMax(currentState, INIT_DEPTH);
				}
				else if (b.Max() < 2048)
				{
					nextChoice = MultithreadedMinMax.MinMax(currentState, MID_DEPTH);
				}				
				else
				{
					nextChoice = MultithreadedMinMax.MinMax(currentState, LATE_DEPTH);
				}
				//Player B Randomly Places Tile
				Board choice = (Board)nextChoice.data;
				ArrayList<Board> choices;														
				
				double p = generator.nextDouble() ;
				if (p < 0.9)
				{
					choices = Board.generateStatesC(choice);
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
				window.repaint();	
				//System.out.println(currentState.data.EvaluateThree());
								
				//if (counter > 600)
				//	Thread.sleep(200);
															
			}
			System.out.println("Moves " + counter);
			
		}	
			System.out.println(gameCount);
			
			
	}
}
