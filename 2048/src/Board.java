import java.util.ArrayList;


public class Board implements Evaluator
{			
	public int[][] board;
	String direction;
	public Board()
	{
		board = new int[5][5];	
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
	   for (int i = 0 ; i < 5 ; i++)
	   {
		   for (int j = 0 ; j < 5 ; j ++)
		   {
			   if (this.board[i][j] != toCompare.board[i][j])
				   return false;
		   }		   		   
	   }
	   return true;
	}
	
	public static int[][] Copy(int[][] tiles)	
	{		
		int[][] boardCopy = new int[5][5];
		
		for (int i = 0 ; i < 5; i++)
		{
			for (int j = 0 ; j < 5; j++)
			{
				boardCopy[i][j] = tiles[i][j];
			}
		}
		return boardCopy;			
	}
	
	public static int[][] SlideUp(int[][] board)
	{
		int[][] boardState = Copy(board);							
		for (int i = 0; i < 5; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 0;
			for (int j = 1; j < 4; j++ )
			{
				if (boardState[i][previousIndex] == boardState[i][j] && boardState[i][j] > 0)
				{
					boardState[i][j] *= 2;
					boardState[i][previousIndex] = 0;
					previousIndex = j;
					j++;
				}
				else if (boardState[i][j] > 0 && boardState[i][previousIndex] != boardState[i][j])
				{
					previousIndex = j;
				}
				
			}
			//Shift the Cells
			int index = 0;
			for (int j = 0; j < 5; j++ )
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
		for (int i = 0; i < 5; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 4;
			for (int j = 3; j >= 0; j-- )
			{
				if (boardState[i][previousIndex] == boardState[i][j] && boardState[i][j] > 0)
				{
					boardState[i][j] *= 2;
					boardState[i][previousIndex] = 0;
					previousIndex = j;
					j--;
				}
				else if (boardState[i][j] > 0 && boardState[i][previousIndex] != boardState[i][j])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 4;
			for (int j = 4; j >= 0; j-- )
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
		for (int i = 0; i < 5; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 4;
			for (int j = 3; j >= 0; j-- )
			{
				if (boardState[previousIndex][i] == boardState[j][i] && boardState[j][i] > 0)
				{
					boardState[j][i] *= 2;
					boardState[previousIndex][i] = 0;
					previousIndex = j;
					j--;
				}
				else if (boardState[j][i] > 0 && boardState[previousIndex][i] != boardState[j][i])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 4;
			for (int j = 4; j >= 0; j-- )
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
		for (int i = 0; i < 5; i ++ )
		{
			//Collapse The Cells
			int previousIndex = 0;
			for (int j = 1; j < 5; j++ )
			{
				if (boardState[previousIndex][i] == boardState[j][i] && boardState[j][i] > 0)
				{
					boardState[j][i] *= 2;
					boardState[previousIndex][i] = 0;
					previousIndex = j;
					j++;
				}
				else if (boardState[j][i] > 0 && boardState[previousIndex][i] != boardState[j][i])
				{
					previousIndex = j;
				}		
			}
			//Shift the Cells
			int index = 0;
			for (int j = 0; j < 5; j++ )
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
		for (int i = 0 ; i < 5; i ++)
		{
			for (int j = 0 ; j < 5 ; j++)
			{
				if (board[i][j] > 0)
				{
					counter ++;
				}
			}
		}
		return 25 - counter;
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
		for (int i = 0 ; i < 5; i ++)
		{
			for (int j = 0 ; j < 5 ; j++)
			{
				if (aBoard.board[i][j] == 0)
				{
					int [][] stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 2;
					states.add(new Board(stateCopy));
				}
			}
		}
		return states;
	}

	
	public static String PrintOut(int[][] board)
	{
		String s = "";
		for (int i = 0 ; i < 5 ; i ++)
		{			
			for (int j = 0 ; j < 5; j ++)
			{
				s += board[j][i] + " ";
			}
			s += "\n";
		}
		return s;	
	}

	
	@Override	
	public int Evaluate() {
		// TODO Auto-generated method stub
		return Evaluate(this.board);
	}

	
	public static boolean Won(int[][] board)
	{
		for (int i = 0 ; i < 5 ; i++)
		{
			for (int j = 0 ; j < 5 ; j++)		
			{
				if (board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}

	public static boolean Lost(int[][] board)
	{
		for (int i = 0 ; i < 5 ; i++)
		{
			for (int j = 0 ; j < 5 ; j++)		
			{
				if (board[i][j] == 0)
					return false;
			}
		}
		return false;
	}
}

