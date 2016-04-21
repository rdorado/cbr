package cbr.test.sum;

import cbr.core.CBR;

public class SumTest {

	public static void main(String[] args) {
		CBR<SumProblem, SumSolution> cbr = new CBR<SumProblem, SumSolution>( SumProblem.class, new SumAlgorithm() );
		 
		/*
		 * Some cases for the CBR
		 */
		/** Case 1*/
		SumProblem case1 = new SumProblem();
		case1.a = 1;
		case1.b = 4;		
		SumSolution r1 = cbr.solveCase(case1);

		/** Case 2*/
		SumProblem case2 = new SumProblem();
		case2.a = 100;
		case2.b = -90;		
		SumSolution r2 = cbr.solveCase(case2);
		
		/** Case 3*/
		SumProblem case3 = new SumProblem();
		case3.a = -10;
		case3.b = 4;		
		SumSolution r3 = cbr.solveCase(case3);

		/** Case 4*/
		SumProblem case4 = new SumProblem();
		case4.a = 0;
		case4.b = 0;		
		SumSolution r4 = cbr.solveCase(case4);		
		
		
		/** Displays the stored knowledge*/
		cbr.showKnowledge();
		
		
		/** Erases the stored knowledge!!! */
		//cbr.resetKnowledge();
		
	}
	
}
