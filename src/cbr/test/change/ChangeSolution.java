package cbr.test.change;

import cbr.core.Result;
import cbr.core.Solution;

public class ChangeSolution extends Solution<ChangeProblem> {
	
	public int intermediateValue;
	
	@Result
	public int values[];
	
	@Result
	public int total;
	
	@Result
	public double logTotal(){
		return Math.log(total);
	}
	
	@Override
	public double compare(Solution s) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		String resp = "";
		for (int i = 0; i < values.length; i++) {
			resp+= values[i]+" ";
		}
		return resp+", Total: "+total;
	}

	@Override
	public boolean check() {
		if(total<0||total>getProblem().amount) return false;
		return true;
	}
	
}
