import java.util.ArrayList;


public class Board implements Evaluator
{			
	public int[][] board;
	public String direction;
	public Board()
	{
		board = new int[4][4];	
	}
	
	public Board(int[][] aboard)
	{
		board = aboard;
		direction = "NONE";
	}
	
	public Board(int[][] aboard, String aDirection)
	{
		board = aboard;
		direction = aDirection;
	}
	public boolean equals(Object b)
	{
	   Board toCompare = (Board)b;
	   for (int i = 0 ; i < 4 ; i++)
	   {
		   for (int j = 0 ; j < 4 ; j ++)
		   {
			   if (this.board[i][j] != toCompare.board[i][j])
				   return false;
		   }		   		   
	   }
	   return true;
	}
	
	public static int[][] Copy(int[][] tiles)	
	{		
		int[][] boardCopy = new int[4][4];
		
		for (int i = 0 ; i < 4; i++)
		{
			for (int j = 0 ; j < 4; j++)
			{
				boardCopy[i][j] = tiles[i][j];
			}
		}
		return boardCopy;			
	}
	
	public static int[][] SlideUp(int[][] board)
	{
		int[][] boardState = Copy(board);							
		for (int i = 0; i < 4; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 0;
			for (int j = 1; j < 4; j++ )
			{
				if (boardState[i][previousIndex] == boardState[i][j] && boardState[i][j] > 0)
				{
					boardState[i][previousIndex] *= 2;
					boardState[i][j] = 0;
					previousIndex = j;
				}
				else if (boardState[i][j] > 0 && boardState[i][previousIndex] != boardState[i][j])
				{
					previousIndex = j;
				}
				
			}
			//Shift the Cells
			int index = 0;
			for (int j = 0; j < 4; j++ )
			{
				if (boardState[i][index] > 0)
				{
					index ++;
				}
				else if (boardState[i][j] > 0 && index != j)
				{
					boardState[i][index] = boardState[i][j];					
					boardState[i][j] = 0;
					index++;
				}			
			}
		}
		return boardState;
	}
	
	public static int[][] SlideDown(int[][] board)
	{
		int[][] boardState = Copy(board);		
		for (int i = 0; i < 4; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 3;
			for (int j = 2; j >= 0; j-- )
			{
				if (boardState[i][previousIndex] == boardState[i][j] && boardState[i][j] > 0)
				{
					boardState[i][previousIndex] *= 2;
					boardState[i][j] = 0;
					previousIndex = j;
				}
				else if (boardState[i][j] > 0 && boardState[i][previousIndex] != boardState[i][j])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 3;
			for (int j = 2; j >= 0; j-- )
			{
				if (boardState[i][index] > 0)
				{
					index--;
				}
				else if (boardState[i][j] > 0 && index != j)
				{
					boardState[i][index] = boardState[i][j];
					boardState[i][j] = 0;
					index--;
				}			
			}
		}
		return boardState;
	
	}
	
	public static int[][] SlideRight(int[][] board)
	{
		int[][] boardState = Copy(board);		
		for (int i = 0; i < 4; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 3;
			for (int j = 2; j >= 0; j-- )
			{
				if (boardState[previousIndex][i] == boardState[j][i] && boardState[j][i] > 0)
				{
					boardState[previousIndex][i] *= 2;
					boardState[j][i] = 0;
					previousIndex = j;
					
				}
				else if (boardState[j][i] > 0 && boardState[previousIndex][i] != boardState[j][i])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 3;
			for (int j = 2; j >= 0; j-- )
			{
				if (boardState[index][i] > 0)
				{
					index--;
				}				
				else if (boardState[j][i] > 0 && index != j)
				{
					boardState[index][i] = boardState[j][i];
					boardState[j][i] = 0;
					index--;
				}			
			}
		}
		return boardState;	
	}
	
	public static int[][] SlideLeft(int[][] board)
	{
		int[][] boardState = Copy(board);		
		for (int i = 0; i < 4; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 0;
			for (int j = 1; j < 4; j++ )
			{
				if (boardState[previousIndex][i] == boardState[j][i] && boardState[j][i] > 0)
				{
					boardState[previousIndex][i] *= 2;
					boardState[j][i] = 0;
					previousIndex = j;
				}
				else if (boardState[j][i] > 0 && boardState[previousIndex][i] != boardState[j][i])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 0;
			for (int j = 0; j < 4; j++ )
			{
				if (boardState[index][i] > 0)
				{
					index++;
				}		
				else if (boardState[j][i] > 0 && index != j)
				{
					boardState[index][i] = boardState[j][i];
					boardState[j][i] = 0;
					index++;
				}			
			}
		}
		return boardState;	
	}
	
	public static int Evaluate(int[][] board)
	{
		int counter = 0;
		for (int i = 0 ; i < 4; i ++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				if (board[i][j] > 0)
				{
					counter ++;
				}
			}
		}
		return (16 - counter) + 1;
	}
	
	public static ArrayList<Board> generateStatesA(Board aBoard)
	{
		ArrayList<Board> states = new ArrayList<Board>();
		states.add(new Board(SlideUp(aBoard.board), "UP"));
		states.add(new Board(SlideDown(aBoard.board), "DOWN"));
		states.add(new Board(SlideRight(aBoard.board), "RIGHT"));
		states.add(new Board(SlideLeft(aBoard.board), "LEFT"));
		
		return states;
	}

	public static ArrayList<Board> generateStatesB(Board aBoard)
	{
		ArrayList<Board> states = new ArrayList<Board>();
		for (int i = 0 ; i < 4; i ++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				if (aBoard.board[i][j] == 0)
				{
					int [][] stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 2;
					states.add(new Board(stateCopy, aBoard.direction));
					
					stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 4;
					states.add(new Board(stateCopy, aBoard.direction));
					
				}
			}
		}
		return states;
	}

	public static ArrayList<Board> generateStatesC(Board aBoard)
	{
		ArrayList<Board> states = new ArrayList<Board>();
		for (int i = 0 ; i < 4; i ++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				if (aBoard.board[i][j] == 0)
				{
					int [][] stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 2;
					states.add(new Board(stateCopy, aBoard.direction));														
				}
			}
		}
		return states;
	}

	public static ArrayList<Board> generateStatesD(Board aBoard)
	{
		ArrayList<Board> states = new ArrayList<Board>();
		for (int i = 0 ; i < 4; i ++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				if (aBoard.board[i][j] == 0)
				{
					int [][] stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 4;
					states.add(new Board(stateCopy, aBoard.direction));														
				}
			}
		}
		return states;
	}
	
	public String toString()
	{
		return PrintOut(this);
	}
	
	public static String PrintOut(Board aBoard)
	{
		String s = "";
		for (int i = 0 ; i < 4 ; i ++)
		{			
			for (int j = 0 ; j < 4; j ++)
			{
				s += aBoard.board[j][i] + " ";
			}
			s += "\n";
		}
		s += aBoard.direction;
		return s;	
	}

		
	public int Evaluate() {
		// TODO Auto-generated method stub
		return Evaluate(this.board);
	}

	public double EvaluateTwo()
	{
		//Heuristic 1 Tile Reduction				
		double counter = 0;
		for (int i = 0 ; i < 4; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{			
				if (board[i][j] > 0)
				{
					counter++;
				}
			}
		}
		double score1 = (16 - counter) / 16.0;			
		double score2 = 0;
		double sum = 0;
		
		double tile = 0 ;
		//Heuristic 2 Best Tile
		for (int i = 0 ; i < 4; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{			
				if (board[i][j] > 0)
				{
					tile = Math.max(board[i][j], tile);
				}
			}
		}
		score2 = tile / 2048;
		//Heuristic 2 Right / Left Cascade 		
		for (int i = 0 ; i < 4; i++)		
		{
			for (int j = 0 ; j < 4; j++)
			{
				if (j == 1 || j == 2)
				{
					sum += board[j][i];
				}
				else
				{
					sum += 2 * board[j][i];
				}
			}
		}
		
		score2 = sum / 49152.0;
		
		//Heuristic 3 Top / Down Cascade 	
		double score3 = 0;
		sum = 0;
		for (int i = 0 ; i < 4; i++)		
		{
			for (int j = 0 ; j < 4; j++)
			{
				if (j == 1 || j == 2)
				{
					sum += board[j][i];
				}
				else
				{
					sum += 2 * board[j][i];
				}
			}
		}
		score3 = sum / 49152.0;
		
		 return 0.3 * score1 + 0.35 * score2 + 0.35 * score3;
	}
	
	public static boolean Won(int[][] board)
	{
		for (int i = 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)		
			{
				if (board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}

	public static boolean Lost(int[][] board)
	{
		Board tester = new Board(board);
		ArrayList<Board> testStates = generateStatesA(tester);
		for (Board b : testStates)
		{
			if (!tester.equals(b))
				return false;
		}
		return true;
	}

	

	
}

