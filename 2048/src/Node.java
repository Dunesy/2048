import java.util.ArrayList;

@SuppressWarnings("hiding")
public class Node
{
	Evaluator data;
	public int depth;
	double score; 
	
	Node(Evaluator adata)
	{		
		data = adata;	
		score = 0;
	}

	
	public boolean equals(Object b)
	{
		Node adata = (Node)b;
		return this.data.equals(adata.data);
	}
	
	public double Evaluate()
	{		
		return score > 0 ? score : (score = data.EvaluateTwo());	
	}
	
}
