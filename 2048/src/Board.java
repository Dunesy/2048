import java.util.ArrayList;


public class Board implements Evaluator
{			
	public int[][] board;
	public long key = 0;
	public Board()
	{
		board = new int[4][4];	
	}
	
	public Board(int[][] aboard)
	{
		board = aboard;		
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
		
		Board up = new Board(SlideUp(aBoard.board));
		if (!up.equals(aBoard))
			states.add(up);
		Board down = new Board(SlideDown(aBoard.board));
		
		if (!down.equals(aBoard))
			states.add(down);
		
		Board right = new Board(SlideRight(aBoard.board));
		if (!right.equals(aBoard))
			states.add(right);
		
		Board left = new Board(SlideLeft(aBoard.board));
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
					states.add(new Board(stateCopy));
					
					stateCopy = Copy(aBoard.board);
					stateCopy[i][j] = 4;
					states.add(new Board(stateCopy));				
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
					states.add(new Board(stateCopy));														
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
					states.add(new Board(stateCopy));														
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
	
	public int Evaluate() {
		// TODO Auto-generated method stub
		return Evaluate(this.board);
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
		return Math.log(max) / Math.log(2);
	}
	
	public double EvaluateTwo()
	{
		
		//if (Board.Lost(board))
		//	return -100000;
		
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
		double score1 = (16 - counter);					
		double score2 = CascadeHeuristic();
		double score3 = SmoothnessHeuristic();
		//Smoothness Heuristic
		double sum = 1;
		
		return  Max() * score1 + (score2 - score3 * 0.2 );
				
	}
	
	public double EvaluateThree()
	{
		double score = 0;
		for (int i= 0 ; i < 4 ; i++)
		{
			for (int j = 0 ; j < 4 ; j++)
			{				
				if (board[i][j] == 0)
				{
					score += 12000;
				}
				else if (board[i][j] > 4)
				{
					score += board[i][j] * 10;
				}
			}
		}
		return score + organizedSpace() + SmoothnessHeuristic();  //+ isLargestInCorner();
		
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

	public double CascadeHeuristic()
	{
		double score = 0 ;
		double score2 = 0;
		double bestScore = 0;
		double bestScore2 = 0;
		//Top / Down Approach
		for (int i = 0 ; i < 4; i++)
		{
			for (int j = 0 ; j < 3; j++)
			{			
				double value = board[i][j] > 0 ? board[i][j] : 0;
				double value2 = board[i][j + 1] > 0 ? board[i][j + 1] : 0;
				if (value2 >= value)
				{
					score +=  1000;
				}
				else if (value2 <= value)
				{
					score += 1000;
				}
			}
		}
		bestScore = Math.max(score, score2);
		score = 0;
		score2 = 0;
		//Left / Right Approach
		for (int i = 0 ; i < 4; i++)
		{
			for (int j = 0 ; j < 3; j++)
			{			
					double value = board[j][i] > 0 ? board[j][i] : 0;
					double value2 = board[j+1][i] > 0 ? board[j+1][i] : 0;
					
					if (value2 >= value)
					{
						score +=  5000 ;
					}
					else if (value2 <= value)
					{
						score += 5000;
					}
				
			}
		}
		bestScore2 = Math.max(score, score2);
		return bestScore + bestScore2;
	}
	
	public double SmoothnessHeuristic()
	{
		double sum = 0;
		//Vertical
		for (int i = 0 ; i < 4; i++)		
		{
			for (int j = 0 ; j < 3 ; j++)
			{				
				if (board[i][j] != 0 && board[i][j + 1] != 0)
				{
					if (board[i][j] == board[i][j + 1]/2 || board[i][j] == board[i][j + 1] * 2 || board[i][j+1] == board[i][j])
						sum += 1000;
				}
			}
		}	
		for (int i = 0 ; i < 4; i++)		
		{			
			for (int j = 0  ; j < 3 ; j++)
			{	
				if (board[j][i] != 0 && board[j+1][i] != 0)
				{
					if (board[j][i] == board[j+1][i]/2 || board[j][i] == board[j+1][i] * 2 ||  board[j][i] == board[j+1][i])
						sum += 1000;
				}
			}
		}
		return sum;
	}
	
	public double isBestOnTheEdge()
	{
		return 0;
	}
	
	public double organizedSpace()
	{
		double score = 0;
		for (int i = 0 ; i < 4 ; i ++)
		{
			if (board[i][0] > board[i][0] && 
			   board[i][1] > board[i][0] &&
			   board[i][2] > board[i][0])  
			score += 12000;
			if (board[i][0] < board[i][0] && 
					   board[i][1] < board[i][0] &&
					   board[i][2] < board[i][0])
			score += 12000;			
		}
		
		for (int i = 0 ; i < 4 ; i ++)
		{
			if (board[0][i] > board[1][i] && 
			   board[1][i] > board[2][i] &&
			   board[2][i] > board[3][i])  
			score += 12000;
			if (board[0][i] < board[1][i] && 
					   board[1][i] < board[2][i] &&
					   board[2][i] < board[3][i])  
			score += 12000;			
		}
		return score;
	}

	public double isLargestInCorner()
	{
		int largesti = 0;
		int largestj = 0;
		for (int i = 0 ; i< 4; i++)
		{
			for (int j= 0 ; j < 4 ; j++)
			{
				if (board[i][j] > board[largesti][largestj])
				{
					largesti = i;
					largestj = j;
				}
			}
		}
		
		if ((largesti == 3 || largesti == 0) && (largestj == 0 || largestj == 3) )
			return 20000;
		return 0;
	}
}

