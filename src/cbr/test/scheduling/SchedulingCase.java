package cbr.test.scheduling;

import cbr.core.Problem;

public class SchedulingCase extends Problem{

	int N;
	float duration[];
	boolean fractionable[];
	int deadline[];
	
	SchedulingCase(String line){
		String[] segs =line.split(" ] [ ");
		String tmp = segs[0].substring(2);
		String[] splits;
		
		splits = tmp.split(" ");
		N = splits.length;
		duration = new float[N];
		for (int i = 0; i < N; i++) {
			duration[i] = Float.parseFloat(splits[i]);
		}
		
		tmp = segs[1];
		splits = tmp.split(" ");
		fractionable = new boolean[N];
		for (int i = 0; i < N; i++) {
			fractionable[i] = Boolean.parseBoolean(splits[i]);
		}
		
		tmp = segs[2].substring(0, segs[2].length()-2);
		splits = tmp.split(" ");
		deadline = new int[N];
		for (int i = 0; i < N; i++) {
			deadline[i] = Integer.parseInt(splits[i]);
		}
		
		
	}

	@Override
	public double compare(Problem c) {
		// TODO Auto-generated method stub
		return 0;
	}
}
