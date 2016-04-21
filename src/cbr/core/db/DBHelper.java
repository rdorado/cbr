package cbr.core.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import cbr.core.Algorithm;
import cbr.core.Feature;
import cbr.core.Result;
import cbr.core.Solution;
import cbr.test.change.ChangeProblem;
import cbr.test.scheduling.SchedulingProblem;

public class DBHelper {
	
	private Class driverClass;
	private String user;
	private String password;
	private String url;
	private static DBHelper instance;
	Connection conn;
	
	private DBHelper(){
	}

	public static DBHelper getInstance() {
		if(instance==null){
			instance = new DBHelper();
			Properties props = new Properties();
			try {
				props.load(new FileInputStream("db.conf"));
				
				instance.driverClass = Class.forName( (String)props.get("driver") );
				instance.password = (String)props.get("password");
				instance.user = (String)props.get("user");
				instance.url = (String)props.get("url");
				
				instance.createDB();
			//	instance.conn = DriverManager.getConnection(instance.url, );
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return instance;
	}

	private void createDB() {
		try {
			conn = DriverManager.getConnection(url, user, password);
			Statement st = conn.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public void removeData(String tablename) {
		try {
			conn = DriverManager.getConnection(url, user, password);
			Statement st = conn.createStatement();
			
			st.executeUpdate("DELETE FROM '"+tablename+"'");		
			st.close();
			
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
		
	}
	
	
	public void showdata(String tablename) {
		try {
			conn = DriverManager.getConnection(url, user, password);
			Statement st = conn.createStatement();
			
			ResultSet rs = st.executeQuery("SELECT * FROM '"+tablename+"'");
		
			ResultSetMetaData rsmd = rs.getMetaData();
			
			int n = rsmd.getColumnCount();
			while(rs.next()){
				System.out.print("|");
				for (int i = 1; i <= n; i++) {
					System.out.print(rs.getString(i)+"|");
				}
				System.out.println();
			}
			
			rs.close();
			st.close();
			
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
	public void save(Algorithm a, Solution s) {
		
		try {
			conn = DriverManager.getConnection(url, user, password);
			Statement st = conn.createStatement();
			
			
			String inputsSQL = "";
			String insertISQL = "";
			String whereSQL = "";
			for(Field field : s.getProblemClass().getFields()){
				Feature feat = field.getAnnotation(Feature.class);
				field.setAccessible(true);
				try {
					if(!field.isAnnotationPresent(Feature.class)) continue;
					if( field.getType().isArray() ){
						String str = getInfoFromArray(field.get(s.getProblem()));
						
						System.out.println( "I:"+field.getName()+"="+str );
					}
					else{
						System.out.println( "I:"+field.getName() +"="+field.get(s.getProblem()).toString());
					}
					inputsSQL+=",I_"+field.getName()+" TEXT";
					insertISQL+=",'"+getAsString(field.get(s.getProblem()))+"'";
					whereSQL+="AND "+"I_"+field.getName()+"='"+getAsString(field.get(s.getProblem()))+"' ";
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			String outputsSQL = "";
			String insertOSQL = "";
			for (Field field : s.getClass().getFields()) {
				try {
					//
					
					if(!field.isAnnotationPresent(Result.class)) continue;
					if( field.getType().isArray() ){
						String str = getInfoFromArray(field.get(s));
						
						System.out.println( "O:"+field.getName()+"="+str );
					}
					else{
						System.out.println( "O:"+field.getName() +"="+field.get(s).toString());
					}
					outputsSQL+=",O_"+field.getName()+" TEXT";
					insertOSQL+=",'"+getAsString(field.get(s))+"'";
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			for(Method meth : s.getClass().getMethods()){
				try {
					if(!meth.isAnnotationPresent(Result.class)) continue;
					System.out.println("O:"+meth.getName()+"="+meth.invoke(s));
					outputsSQL+=",O_"+meth.getName()+" TEXT";
					insertOSQL+=",'"+getAsString(meth.invoke(s))+"'";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// SELECT * FROM '"+s.getProblemClass().getName()+"' WHERE algorithm="'"+a.getClass().getName().toString()+"'"+whereSQL 
			
			//st.execute("CREATE TABLE IF NOT EXISTS '"+s.getProblemClass().getName()+"' (algorithm VARCHAR(255), data VARCHAR(255)"+inputsSQL+")");
			//st.execute("INSERT INTO '"+s.getProblemClass().getName()+"' VALUES('"+a.getClass().toString()+"','"+s.toString()+"')");
			
			
			st.execute("CREATE TABLE IF NOT EXISTS '"+s.getProblemClass().getName()+"' (algorithm VARCHAR(255)"+inputsSQL+outputsSQL+")");
			st.execute("INSERT INTO '"+s.getProblemClass().getName()+"' VALUES('"+a.getClass().getName().toString()+"'"+insertISQL+insertOSQL+")");
			
			st.close();
			
			conn.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
	}
	
	private String getAsString(Object object) {
		if(object==null) return null;
		if(object.getClass().isArray()){
			int s = Array.getLength(object);
			String resp="";
			for (int i = 0; i < s; i++) {
				resp+=","+Array.get(object, i);
			}
			return resp.substring(1);
		}
		else{
			return object.toString();
		}
	}

	private String getInfoFromArray(Object object) {
		String resp = "";
		if(object==null) return "";
		int len = Array.getLength(object);
		for (int i = 0; i < len; i++) {
			resp+=Array.get(object, i).toString()+",";
		}
		if(resp.length()==0) return resp;
		return resp.substring(0, resp.length()-1);
	}

	public static void main(String[] args) {
		DBHelper helper = DBHelper.getInstance();
		helper.showdata(SchedulingProblem.class.getName());
	}



	
}
