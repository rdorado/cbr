package cbr.test.change;

import cbr.core.CBR;

public class ChangeTest {
	
	public static void main(String[] args) {

		
		
		CBR<ChangeProblem,ChangeSolution> cbr = new CBR<ChangeProblem,ChangeSolution>(ChangeProblem.class, new ChangeGreedyAlgorithm(), new ChangeDPAlgorithm());

		
		ChangeProblem case1 = new ChangeProblem(new int[]{10, 7, 1}, 14);
		//ChangeProblem case2 = new ChangeProblem(new int[]{10, 7, 1}, 14001);
		ChangeProblem case2 = new ChangeProblem(new int[]{10, 7, 1}, 14014);
		ChangeProblem case3 = new ChangeProblem(new int[]{50, 10, 7, 1}, 14014);
		
		
		cbr.solveCase(case1);
		
		System.out.println();
		
		cbr.solveCase(case2);
		
		System.out.println();
		
		cbr.solveCase(case3);
	}
	
}
