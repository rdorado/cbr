package cbr.core;

public abstract class Algorithm<P extends Problem > {
	
	
	
	public Solution execute(P problem){
		Solution s = solve(problem);
		s.problem = problem;
		return s;
	}
	
	public abstract Solution solve(P problem);
	
}
