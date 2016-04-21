package cbr.test.change;

import cbr.core.Feature;
import cbr.core.Problem;


public class ChangeProblem extends Problem {

	int n; //n different coins
	
	@Feature( maxlen=100, min=0, max=2000 )
	public int[] coins;
	
	@Feature( min=1, max=10000)
	public int amount;
	
	public ChangeProblem(int[] coins, int amount) {
		this.n=coins.length;
		this.coins=coins;
		this.amount=amount;
	}

	@Override
	public double compare(Problem c) {
		if(!(c instanceof ChangeProblem)) return 1;
		ChangeProblem cc = (ChangeProblem)c;
		if(n!=cc.n) return 1;
		
		return 0;
	}

}
