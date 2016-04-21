package cbr.test.scheduling;

import java.util.ArrayList;

import cbr.core.Algorithm;
import cbr.core.Solution;

public class SchedulingDumbAlgorithm extends Algorithm<SchedulingProblem>{

	@Override
	public SchedulingSolution solve(SchedulingProblem problem) {
		SchedulingSolution resp = new SchedulingSolution();
		
		ArrayList<Integer> seq = new ArrayList<Integer>();
		
		int N = problem.N;
		int last=N-1;
		int[] _deadlines = new int[N];
		System.arraycopy(problem.deadlines, 0, _deadlines, 0, problem.deadlines.length);
		
		double[] _durations = new double[N];
		System.arraycopy(problem.durations, 0, _durations, 0, problem.durations.length);
		
		int[] taskIds = new int[N];
		for (int i = 0; i < N; i++) taskIds[i]=i;
		
		/** Sort by deadline*/
		while(last>0){
			last=0;
			for (int i = 0; i < N-1; i++) {
				if(_durations[i]>_durations[i+1]){
					double ftmp = _durations[i];
					_durations[i] = _durations[i+1];
					_durations[i+1] = ftmp;
					
					int itmp = _deadlines[i];
					_deadlines[i] = _deadlines[i+1];
					_deadlines[i+1] = itmp;
					
					itmp = taskIds[i];
					taskIds[i] = taskIds[i+1];
					taskIds[i+1] = itmp;
				}
			}
		}
		
		int acum=0;
		for(int i=0;i<N;i++){
			if(acum<_deadlines[i]){
				seq.add(taskIds[i]);
				acum+=_durations[i];
			}
		}
		
		int n = seq.size();
		resp.idtasks = new int[n];
		resp.starts = new float[n];
		resp.ends = new float[n];
		
		float acum2=0;
		
		for (int i = 0; i < n; i++) {
			resp.idtasks[i] = seq.get(i);
			resp.starts[i] = acum2;
			resp.ends[i] = (float)(acum2+problem.durations[ resp.idtasks[i] ]);
			acum2 = resp.ends[i];
		}
		
		return resp;
	}

}
