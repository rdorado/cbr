package cbr.test.scheduling;

import cbr.core.Result;
import cbr.core.Solution;

public class SchedulingSolution extends Solution<SchedulingProblem> {

	@Result
	public int[] idtasks;
	
	@Result
	public float[] starts;

	@Result
	public float[] ends;
	
	@Result
	public double profit(){
		double resp=0;
		if(idtasks==null||starts==null||ends==null) return 0;
		if(idtasks.length==0||starts.length==0||ends.length==0) return 0;
		
		int n=idtasks.length;
		int N=getProblem().N;
		
		double[] worked = new double[N];
		for (int i = 0; i < n; i++) {
			worked[idtasks[i]] += (ends[i] - starts[i]);
		}
	
		for (int i = 0; i < n; i++) {
			if( equals(worked[idtasks[i]],getProblem().durations[idtasks[i]]) ) resp+=worked[i];
		}
		System.out.println("-->"+n);
		return resp;
	}
	
	boolean equals(double a,double b){
	//	System.out.println(a+" "+b+" "+Math.abs(a-b));
		if( Math.abs(a-b) < 0.0001) return true;
		return false;
	}
	
	@Override
	public double compare(Solution s) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean check() {
		if(idtasks==null || idtasks.length==0) return false;
		if(starts==null || starts.length==0) return false;
		if(ends==null || ends.length==0) return false;
		if(starts.length!=ends.length||starts.length!=idtasks.length) return false;
		
		int[] _idtasks = new int[idtasks.length];
		System.arraycopy(idtasks, 0, _idtasks, 0, idtasks.length);
		
		float[] _starts = new float[starts.length];
		System.arraycopy(starts, 0, _starts, 0, starts.length);
		
		float[] _ends = new float[ends.length];
		System.arraycopy(ends, 0, _ends, 0, ends.length);
		
		int n=_idtasks.length;
		
		/* Sort by id to check if the sum of work on task_i < 1 */
		int last=n-1;
		while(last>0){
			last=0;
			for(int i=0;i<last;i++){
				if(_starts[i]>_starts[i+1]){
					float ftmp=_starts[i];
					_starts[i]=_starts[i+1];
					_starts[i+1]=ftmp;
					
					int itmp = _idtasks[i];
					_idtasks[i]=_idtasks[i+1];
					_idtasks[i+1]=itmp;
					
					ftmp=_ends[i];
					_ends[i]=_ends[i+1];
					_ends[i+1]=ftmp;
				}
			}
		}
		
		int N = getProblem().N;
		float[] sums = new float[N];
		int[] timesByTask = new int[N];
		if(_ends[n-1] > getProblem().D) return false;
		if(_starts[0]>_ends[0]) return false;
		sums[_idtasks[0]]+=_ends[0]-_starts[0];
		timesByTask[_idtasks[0]]++;
		for (int i = 1; i < n; i++) {
			if(_starts[i]>_ends[i]) return false;
			if(_ends[i-1]>_starts[i]) return false;
			sums[_idtasks[i]]+=_ends[i]-_starts[i];
			timesByTask[_idtasks[i]]++;
		}
		
		for (int i = 0; i < N; i++) {
			
			if(getProblem().preemptable[i]==0){
				if(timesByTask[i]>1) return false;
				if(sums[i]!=getProblem().durations[i]||sums[i]!=0) return false;
			}
			else{
				if(sums[i]>getProblem().durations[i]) return false;
			}
			
		}
		
		return true;
	}

}
