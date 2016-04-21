package cbr.test.sum;

import cbr.core.Algorithm;

public class SumAlgorithm extends Algorithm<SumProblem>{

	@Override
	public SumSolution solve(SumProblem p) {
		
		float sum = p.a + p.b;
		SumSolution result = new SumSolution();
		result.value = sum;
		
		return result;
	}

}
