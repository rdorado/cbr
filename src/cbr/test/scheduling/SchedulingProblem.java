package cbr.test.scheduling;

import cbr.core.Feature;
import cbr.core.Problem;

public class SchedulingProblem extends Problem{


	public int D;
	
	@Feature(min=0, max=1000)
	public double durations[];
	
	@Feature(min=0, max=1)
	public int preemptable[];
	
	@Feature(min=0, max=1000)
	public int deadlines[];
	
	@Feature(min=0, max=1000)
	public int N;
	
	@Override
	public double compare(Problem p) {

		double wpf = ((SchedulingProblem)p).calculatePf();
		double wpi = calculatePf();
		
	
		return Math.abs(wpf-wpi);
	}

	public double calculatePf() {
		double count = 0;
		for (int i = 0; i < preemptable.length; i++) {
			count+=preemptable[i];
		}
		
		return count/preemptable.length;
	}

	public void print() {
		System.out.println("Problem");
		System.out.println("  Time window: "+D);
		System.out.println("  N tasks: "+N);
		System.out.println("  Tasks:");
		for (int i = 0; i < durations.length; i++) {
			System.out.print("    Duration: "+durations[i]+", Deadline: "+deadlines[i]+", Preemtable: "+preemptable[i]+"\n");
		}

		
	}
	
}
