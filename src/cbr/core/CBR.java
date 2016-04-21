package cbr.core;

import java.util.ArrayList;

import cbr.core.db.DBHelper;


public class CBR<P extends Problem, S extends Solution> {

	Class<P> problemClass;
	ArrayList<Algorithm<P>> algorithms = new ArrayList<Algorithm<P>>();
	
	public CBR(Class problemClass, Algorithm<P>... algorithms) {
		this.problemClass = problemClass;
		for (int i = 0; i < algorithms.length; i++) {
			try {
				this.algorithms.add(algorithms[i]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public S solveCase(P problemCase) {
		S resp=null;
		/* Retrieve similar case:
			//algorithm = retrieveBest(problemCase)
		// if (algorithm == Found){
			
	     }
		  else learn from all:
		*/
		for (int i = 0; i < algorithms.size(); i++) {
			Solution s = algorithms.get(i).execute(problemCase);
			
			System.out.print(algorithms.get(i)+": ");
			System.out.println(s);
			
			// save info in db 
			DBHelper helper = DBHelper.getInstance();
			helper.save(algorithms.get(i), s);
			
			if(s.check()) resp = (S)s;
		}
		return resp;
	}

	public void showKnowledge() {
		DBHelper helper = DBHelper.getInstance();		
		helper.showdata( problemClass.getName() );
	}
	
	public void resetKnowledge(){
		DBHelper helper = DBHelper.getInstance();
		helper.removeData( problemClass.getName() );
	}

}
