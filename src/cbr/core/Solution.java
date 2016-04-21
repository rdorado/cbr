package cbr.core;

public abstract class Solution<P extends Problem> {

	P problem;
	
	/**
	 * This method must be implemented to compare cases and return a real number between 0 and 1.
	 */
	public abstract double compare(Solution s);

	public abstract boolean check();
	
	public Class getProblemClass() {
		return problem.getClass();
	}

	public P getProblem() {
		return problem;
	}
	
}
