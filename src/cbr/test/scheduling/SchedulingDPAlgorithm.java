package cbr.test.scheduling;

import java.util.ArrayList;

import cbr.core.Algorithm;
import cbr.core.Problem;
import cbr.core.Solution;

public class SchedulingDPAlgorithm extends Algorithm<SchedulingProblem> {

	@Override
	public SchedulingSolution solve(SchedulingProblem problem) {
		SchedulingSolution sol = new SchedulingSolution();
		
		System.out.println();
		int N = problem.N;		
		int[] _deadlines = new int[N];
		System.arraycopy(problem.deadlines, 0, _deadlines, 0, N);
		double[] _durations = new double[N];	
		System.arraycopy(problem.durations, 0, _durations, 0, N);
		int[] _ids = new int[N];
		for(int i=0;i<N;i++) _ids[i] = i;
		
		/* Order by deadline*/
		int last=N-1;
		while(last!=0){
			int tmp=0;
			for (int i = 0; i < last; i++) {
				if(_deadlines[i]>_deadlines[i+1]){
					int itmp = _deadlines[i];
					_deadlines[i]=_deadlines[i+1];
					_deadlines[i+1]=itmp;
					
					double dtmp=_durations[i];
					_durations[i]=_durations[i+1];
					_durations[i+1]=dtmp;
					
					itmp = _ids[i];
					_ids[i] = _ids[i+1];
					_ids[i+1] = itmp;
					
					tmp=i;
				}
			}
			last=tmp;
		}
		
		/* Construct the "optimum" table*/
		Row[] table = new Row[N+1];
		for (int i = 0; i <= N; i++) table[i] = new Row();
		table[0].add(0);
		for (int i = 1; i <= N; i++) {
			table[i].calculateRow(table[i-1],_durations[i-1],_deadlines[i-1]);
		//	table[i].print();
		}
		
		/* Find and add tasks to the optimum solution*/
		double best = table[N].last();
		int i=N;
		ArrayList<Integer> ids = new ArrayList<Integer>(); 
		while(i>0){
		//	System.out.println( table[i].last()+"=="+best );
			if(table[i].last() == best){
				best-=_durations[i-1];
				ids.add(_ids[i-1]);
		//		System.out.println( _ids[i-1] );
			}
			i--;
		}
		
		/* Collect the solution into the Solution object*/
		int np = ids.size();
		_deadlines = new int[np];
		_durations = new double[np];
		sol.idtasks = new int[np];
		sol.starts = new float[np];
		sol.ends = new float[np];
		
		for (i=0;i<ids.size();i++) {
			_deadlines[i] = problem.deadlines[ids.get(i)];
			_durations[i] = problem.durations[ids.get(i)];
			sol.idtasks[i] = ids.get(i);
		}
		
		last=np-1;
		while(last>0){
			last=0;
			for(i=0;i<np-1;i++){
				if(_deadlines[i]>_deadlines[i+1]){
					int itmp = _deadlines[i];
					_deadlines[i] = _deadlines[i+1];
					_deadlines[i+1] = itmp;
					
					double dtmp = _durations[i];
					_durations[i] = _durations[i+1];
					_durations[i+1] = dtmp;
			
					itmp = sol.idtasks[i];
					sol.idtasks[i] = sol.idtasks[i+1];
					sol.idtasks[i+1] = itmp;
					
					last=i;
				}
			}
		}
		
		float acum = 0;
		for (i=0;i<np;i++) {
			sol.starts[i] = acum;
			acum+=_durations[i];
			sol.ends[i] = acum;
		}
		
		return sol;
	}

	
	class Row{
		RowNode head;
		RowNode tail;
		int size=0;
		void add(double value){
			if(head==null) tail=head=new RowNode(value);
			else{
				RowNode tmp=head;
				while(tmp.next!=null&&tmp.next.value<=value){
					tmp=tmp.next;
				} 
				if(tmp.value==value) return;
				RowNode node = new RowNode(value);
				node.next=tmp.next;
				tmp.next = node;
				if(node.next==null) tail=node;
			}
			size++;
		}
		public boolean contains(double value) {
			RowNode tmp=head;
			while(tmp!=null){
				if(tmp.value==value) return true;
				tmp=tmp.next;
			}
			return false;
		}
		public int size() {
			return size;
		}
		public double last() {
			return tail.value;
		}
		public void print() {
			RowNode tmp=head;
			System.out.print("[ ");
			while(tmp!=null){
				System.out.print(tmp.value+" ");
				tmp=tmp.next;
			}
			System.out.println("]");
		}
		
		public void calculateRow(Row row, double size, int deadline) {
			double[] vals = row.getAsArray();
			for (int i = 0; i < vals.length; i++) add(vals[i]);
			
			int i=0;
			while(i<vals.length && vals[i]+size<=deadline){
				add(vals[i]+size);
				i++;
			}
			
		}
		private double[] getAsArray() {
			double[] resp=new double[size];
			int cont=0;
			RowNode tmp=head;
			while(tmp!=null){
				resp[cont++]=tmp.value;
				tmp=tmp.next;
			}
			return resp;
		}
	}
	class RowNode{
		double value;
		RowNode next;
		RowNode(double value){
			this.value=value;
		}
	}
}
