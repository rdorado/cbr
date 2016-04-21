package cbr.core;

public abstract class Problem {

	
	/**
	 * This method must be implemented to compare cases and return a real number between 0 and 1.
	 */
	public abstract double compare(Problem p);
	
	public final double compareTo(Problem p) throws NotValidCompareFunctionException{
		double resp = compare(p);
		
		if(resp<0||resp>1) throw new NotValidCompareFunctionException();
		return resp;
	}

	
	
}
