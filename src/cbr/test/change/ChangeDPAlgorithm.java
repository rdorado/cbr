package cbr.test.change;

import cbr.core.Algorithm;

public class ChangeDPAlgorithm extends Algorithm<ChangeProblem>{

	@Override
	public ChangeSolution solve(ChangeProblem problem) {
		ChangeSolution resp = new ChangeSolution();
		int n = problem.n;
		resp.values = new int[n];
		int[] table = new int[problem.amount+1];
		int[] track = new int[problem.amount+1];
		
		for(int i=1;i<=problem.amount;i++){
			track[i] = minIndx(i, table, problem.coins);
			table[i] = table[i-problem.coins[track[i]]]+1;
		}
		
		int i=problem.amount;
		while(i>0) {
			resp.values[track[i]]++;
			i = i - problem.coins[track[i]];
		}
		
		resp.total=table[problem.amount];
		return resp;
	}


	private int minIndx(int value, int[] table, int[] coins) {
		int min = Integer.MAX_VALUE;
		int indx = -1;
		for(int i=0;i<coins.length;i++){
			
			int tablei = (value-coins[i])>=0?table[value-coins[i]]:Integer.MAX_VALUE;
			if(min>tablei){
				min = tablei;
				indx = i;
			}
		}

		return indx;
	}

}
