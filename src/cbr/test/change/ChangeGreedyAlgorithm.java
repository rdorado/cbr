package cbr.test.change;

import cbr.core.Algorithm;

public class ChangeGreedyAlgorithm extends Algorithm<ChangeProblem>{

	@Override
	public ChangeSolution solve(ChangeProblem problem) {
		ChangeSolution resp = new ChangeSolution();
		
		int n = problem.n;
		resp.values = new int[n];
		int tmp = problem.amount;
		
		int mincoin = problem.coins[n-1];
		int i=0;
		
		while(tmp >= mincoin){
			if(tmp>problem.coins[i]){
				resp.values[i] = tmp/problem.coins[i];
				tmp=tmp-(resp.values[i]*problem.coins[i]);
				resp.total+=resp.values[i];
			}
			i++;
		}
		
		return resp;
	}

}
