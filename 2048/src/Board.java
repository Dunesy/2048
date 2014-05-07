import java.util.ArrayList;


public class Board implements Evaluator
{			
	public int[][] board;
	public long key = 0;
	public int gameScore = 0;
	public Board()
	{
		board = new int[4][4];	
	}
	
	public Board(int[][] aboard)
	{
		board = aboard;		
	}
	
	public Board(int[][] aboard, int gameScore)
	{
		board = aboard;	
		this.gameScore = gameScore;
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
	
	public static Board SlideUp(Board a)
	{
		int[][] boardState = Copy(a.board);
		int gameScore = 0;
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
					gameScore += boardState[i][previousIndex];
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
		Board newBoard = new Board(boardState, a.gameScore + gameScore);
		return newBoard;
	}
	
	public static Board SlideDown(Board a)
	{
		int[][] boardState = Copy(a.board);
		int gameScore = 0;
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
					gameScore += boardState[i][previousIndex];
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
		return new Board(boardState, gameScore + a.gameScore);
	
	}
	
	public static Board SlideRight(Board a)
	{
		int[][] boardState = Copy(a.board);
		int gameScore = 0;
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
					gameScore += boardState[previousIndex][i];
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
		return new Board(boardState, gameScore + a.gameScore);
	}
	
	public static Board SlideLeft(Board a)
	{
		int[][] boardState = Copy(a.board);	
		int gameScore = 0;
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
					gameScore += boardState[previousIndex][i];
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
		return new Board(boardState, gameScore + a.gameScore);
	}
	
	public static ArrayList<Board> generateStatesA(Board aBoard)
	{
		ArrayList<Board> states = new ArrayList<Board>();
		
		Board up = SlideUp(aBoard);
		if (!up.equals(aBoard))
			states.add(up);
		Board down = SlideDown(aBoard);
		
		if (!down.equals(aBoard))
			states.add(down);
		
		Board right = SlideRight(aBoard);
		if (!right.equals(aBoard))
			states.add(right);
		
		Board left = SlideLeft(aBoard);
		if (!left.equals(aBoard))
			states.add(left);
		
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
					states.add(new Board(stateCopy, aBoard.gameScore));
					
					stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 4;
					states.add(new Board(stateCopy, aBoard.gameScore));				
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
					states.add(new Board(stateCopy, aBoard.gameScore));														
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
					states.add(new Board(stateCopy, aBoard.gameScore));														
				}
			}
		}
		return states;
	}
	
	public String toString()
	{
		return PrintOut(this);
	}
	
	public long getKey()
	{
		if (key == 0)
		{
			for (int i = 0 ; i < 4; i++)
			{
				for (int j = 0 ; j < 4; j++)
				{
					key += board[j][i];
					key *= 10;
				}
			}
			key /= 10;
		}
		return key;
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
		return s;	
	}

	public double sumBoard()
	{
		double sum = 0;
		for (int i = 0 ;  i < 4; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				sum += board[i][j];
			}
		}
		return sum;
	}
	
	public double Max()
	{
		double max = 0;
		for (int i = 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{
				max = Math.max(max, board[i][j]);
			}
		}
		return max;
	}
	
	public double Evaluate()
	{
		double score = 0;
		for (int i= 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{				
				if (board[i][j] == 0)
				{
					score += 20000;
				}				
			}
		}
		return score + gameScore + organizedSpace() + SmoothnessHeuristic();	
	}
	
	public static boolean Won(int[][] board)
	{
		for (int i = 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)		
			{
				if (board[i][j] >= 2048)
					return true;
			}
		}
		return false;
	}

	public static boolean Lost(int[][] board)
	{
		Board tester = new Board(board);
		for (int i= 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4; j++)
			{	if (j < 3)
				if (board[j][i] == 0 || board[j + 1][i] == 0 || board[j][i] == board[j + 1][i])
				{
					return false;
				}
				if (i < 3)
				if (board[j][i] == 0 || board[j][i + 1] == 0 || board[j][i] == board[j][i + 1])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	
	public double SmoothnessHeuristic()
	{
		double sum = 0;
		//Vertical
		for (int i = 0 ; i < 4; i++)		
		{
			for (int j = 0 ; j < 3 ; j++)
			{				
				if (board[i][j] != 0 && board[i][j + 1] != 0 && board[i][j] > 256 && board[i][j + 1] > 256)
				{
					double largest = Math.max(board[i][j], board[i][j + 1]);
					double smallest = Math.min(board[i][j], board[i][j + 1]);
					if (board[i][j] == board[i][j + 1]/2 || board[i][j] == board[i][j + 1] * 2 || board[i][j+1] == board[i][j])
					//if (smallest / largest >= .25)
					{
						
						sum += 5000;
					}
				}
			}
		}	
		for (int i = 0 ; i < 4; i++)		
		{			
			for (int j = 0  ; j < 3 ; j++)
			{	
				if (board[j][i] != 0 && board[j+1][i] != 0)
				{					
					double largest = Math.max(board[j][i], board[j + 1][i]);
					double smallest = Math.min(board[j][i], board[j + 1][i]);
					if (board[j][i] == board[j+1][i]/2 || board[j][i] == board[j+1][i] * 2 ||  board[j][i] == board[j+1][i] && board[j+1][i] > 256 && board[j][i] > 256)
					//if (smallest / largest >= .25)
					sum += 5000;
				}
			}
		}
		return sum;
	}
		
	public double organizedSpace()
	{
		double score = 0;
	
		for (int i = 0 ; i < 4 ; i ++)
		{		
			if (board[i][0] > board[i][1] && 
			   board[i][1] >= board[i][2])
			  // board[i][2] >= board[i][3])  
				
			score +=  10000;
			if (
					   board[i][1] <= board[i][2] &&
					   board[i][2] < board[i][3])
			{
				//score +=  1000 * Math.log(board[i][3] * board[i][2] > 0 ? board[i][2] : 1 * board[i][1] > 0 ? board[i][1] : 1)/ Math.log(2) * multiplier;
			}
		}
		
		for (int i = 0 ; i < 4 ; i ++)
		{
			double multiplier = 1.5 * (3 - i) ;
						
			if (board[0][i] > board[1][i] && 
			   board[1][i] >= board[2][i]
			   )  
			score += 1000 *  Math.log(board[0][i] * board[1][i] > 0 ? board[1][i] : 0 * board[2][i] > 0 ? board[2][i] : 1)/ Math.log(2) * multiplier;
			if (
					   board[1][i] <= board[2][i] &&
					   board[2][i] < board[3][i])  
			{
			score += 1000 *  Math.log(board[3][i] * board[2][i] > 0 ? board[2][i] : 1  * board[1][i] > 0 ? board[1][i] : 1)/ Math.log(2) * multiplier;
			}
		}
		return score;
	}
	
}

