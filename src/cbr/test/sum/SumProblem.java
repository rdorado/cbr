package cbr.test.sum;

import cbr.core.Feature;
import cbr.core.Problem;

public class SumProblem extends Problem{

	@Feature(min=-100, max=100)
	public float a;
	
	@Feature(min=-100, max=100)
	public float b;

	@Override
	public double compare(Problem p) {
		SumProblem sp = (SumProblem)p;
		return (Math.abs(a-sp.a)+Math.abs(b-sp.b))/400;
	}
	
}
