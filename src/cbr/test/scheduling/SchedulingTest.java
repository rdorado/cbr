package cbr.test.scheduling;

import java.util.ArrayList;

import cbr.core.CBR;

public class SchedulingTest {

	
	public static void main(String[] args) {
		ArrayList<SchedulingProblem> problems = ProblemReader.readFile("data/scheduling/test_case.txt");
		
		
		CBR<SchedulingProblem,SchedulingSolution> cbr = new CBR(SchedulingProblem.class, new SchedulingDumbAlgorithm(), new SchedulingDPAlgorithm());
		
		for (SchedulingProblem sp : problems) {
		
			cbr.solveCase(sp);
		}
		
		
	}
	
	
}
