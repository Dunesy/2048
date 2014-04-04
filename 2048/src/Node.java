
@SuppressWarnings("hiding")
public class Node
{
	Evaluator data;
	public int depth;
	public long key ;
	double score; 
	
	Node(Evaluator adata)
	{		
		data = adata;	
		score = -1;
		key = 0;
	}

	
	public boolean equals(Object b)
	{
		Node adata = (Node)b;
		return this.data.equals(adata.data);
	}
	
	public double Evaluate()
	{		
		return score >= 0 ? score : (score = data.EvaluateThree());	
	}
	
	public void setScore(double aScore)
	{
		score = aScore;
	}
	
	public double getScore()
	{
		return score;
	}
	
	public long Key()
	{
		return data.getKey();
	}
}
