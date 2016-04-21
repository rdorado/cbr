package cbr.test.scheduling;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cbr.core.Problem;

public class ProblemReader {

	
	public static SchedulingProblem readProblemFromFile(String filename){
		SchedulingProblem resp = new SchedulingProblem();
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String line;
			
			for (int i = 0; i < 6; i++) {
				reader.readLine();
			}
			
			
			reader.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return resp;
	}
	
	public static ArrayList<SchedulingProblem> readFile(String filename){

		ArrayList<SchedulingProblem> resp = new ArrayList<>();
		int nline=8;
		String line=null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			
			
			for (int i = 0; i < 6; i++) {
				reader.readLine();
			}
			
			line=reader.readLine();
			String[] array= line.split(" , ");
			
			int D = Integer.parseInt(array[0]);
			
			while((line=reader.readLine())!=null){
				SchedulingProblem prob = new SchedulingProblem();
				
				prob.D=D;
				String[] aux1 = line.substring(2, line.length()-2).split(" \\]\\[ ");
				
				array = aux1[0].split(" ");
				int N = array.length;
				
				prob.N=N;
				prob.durations=new double[N];
				prob.preemptable=new int[N];
				prob.deadlines=new int[N];
				
				for (int i=0;i<array.length;i++) {
					prob.durations[i]=Double.parseDouble(array[i]);
				}
				
				array = aux1[1].split(" ");
				for (int i=0;i<array.length;i++) {
					prob.preemptable[i]=Integer.parseInt(array[i]);
				}
				
				array = aux1[2].split(" ");
				for (int i=0;i<array.length;i++) {
					prob.deadlines[i]=Integer.parseInt(array[i]);
				}
				

				resp.add(prob);
				
				nline++;
			}
			reader.close();
		} 
		catch (Exception e) {
			System.err.println("Error in  line "+nline+", "+line);
			e.printStackTrace();
		}
		return resp;
	}


	
	public static void main(String[] args) {
		ArrayList<SchedulingProblem> problems = readFile("data/scheduling/test_case.txt");
		
		problems.get(0).print();
	}


}
