/*******************************************************************************
 * Copyright 2011 Patrick McMorran
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package datagenerator.generators;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import datagenerator.util.QueryGenerator;


/**
 * This class generates random names and genders.
 * @author Patrick McMorran
 *
 */
public class NameGenderGenerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Configuration Parameters
	private int Gender;
	
	//Data for Generation
	private double[] MaleWieght;
	private String[] MaleNames;
	private double[] FemaleWieght;
	private String[] FemaleNames;
	private double[] LastWieght;
	private String[] LastNames;
	
	//
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int BOTH = 2;
	//
	private Random R = new Random();
	//Database stuff
	private Connection conn;
    private Statement s = null;
    private ResultSet rs = null;
    
    private boolean unique;
    private String temp_table;
    static int generatorID = 999999;
    private PreparedStatement psInsert = null;
    private boolean[] unconf;
    
    ////////////////////////
    //Queries
    ////////////////////////
    //This String holds the Query to select the information of the desired states
    private String GetMaleNames;
    private String GetFemaleNames;
    private String GetLastNames;
	
    /**
     * 
     * @param c This Connection object is used for the generator to interact with the database.
     * @param i This integer is used to designate what gender name the generator should produce.
     * Male = 0, Female = 1, Both = 2.
     * @param u This is a boolean value that denotes whether the generator should generate unique values. 
	 * true = unique, false = nonunique;
     * @param u2 This boolean array designates what data the generator should output.
	 * Array[0] = First Name
	 * Array[1] = Last Name
	 * Array[2] = Gender
     */
	public NameGenderGenerator(Connection c, int i, boolean u, boolean[] u2){
		this.unique = u;
		this.Gender = i;
		this.conn = c;	
		unconf = u2;
		this.GetMaleNames = QueryGenerator.getMaleNames();
		this.GetFemaleNames = QueryGenerator.getFemaleNames();
		this.GetLastNames = QueryGenerator.getLastNames();
		this.temp_table = "temp_namegender_" + generatorID;
		generatorID--;
		}
	
	
	//This function is called to load in the CSV data that it uses
	//to generate random data
	/**
	 * This is used to prepare the generator, creating required tables in the database, loading required
	 * data into memory, and handling any other dependencies.
	 */
	public void PrepareGenerator(){
		try{		
		s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		//System.out.println("Created SQL Statement");
		//Load Male Names into array
		rs = s.executeQuery(GetMaleNames);
		//System.out.println("Execute Query for Male Names");
		rs.last();//Go to last row in the results
		int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
		rs.first();//Go back to the first row
		//System.out.println("Check Number of Results");
		double[] MaleWieght_Temp = new double[NumberofResults];
		MaleNames = new String[NumberofResults];
		//System.out.println("Created Data Array - Male");
		for(int i = 0; i<NumberofResults; i++){
			MaleNames[i] = rs.getString(1);
			MaleWieght_Temp[i] = rs.getDouble(2);
			rs.next();
		}
		//System.out.println("Loaded Male Results into memory");
		rs.close();
		//System.out.println("Close Resultset");
		
		//Load Female Names into an array
		rs = s.executeQuery(GetFemaleNames);
		//System.out.println("Execute Query for Male Names");
		rs.last();//Go to last row in the results
		NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
		rs.first();//Go back to the first row
		//System.out.println("Check Number of Results");
		double[] FemaleWieght_Temp = new double[NumberofResults];
		FemaleNames = new String[NumberofResults];
		//System.out.println("Created Data Array - Female");
		for(int i = 0; i<NumberofResults; i++){
			FemaleNames[i] = rs.getString(1);
			FemaleWieght_Temp[i] = rs.getDouble(2);
			rs.next();
		}
		//System.out.println("Loaded Female Results into memory");
		rs.close();
		//System.out.println("Close Resultset");
		
		//Load Last Names into an array
		rs = s.executeQuery(GetLastNames);
		//System.out.println("Execute Query for Last Names");
		rs.last();//Go to last row in the results
		NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
		rs.first();//Go back to the first row
		//System.out.println("Check Number of Results");
		double[] LastWieght_Temp = new double[NumberofResults];
		LastNames = new String[NumberofResults];
		//System.out.println("Created Data Array - Last");
		for(int i = 0; i<NumberofResults; i++){
			LastNames[i] = rs.getString(1);
			LastWieght_Temp[i] = rs.getDouble(2);
			rs.next();
		}
		//System.out.println("Loaded Female Results into memory");
		rs.close();
		//System.out.println("Close Resultset");
		
		///Generate the Wieght Arrays
		MaleWieght = getWieght(MaleWieght_Temp);
		FemaleWieght = getWieght(FemaleWieght_Temp);
		LastWieght = getWieght(LastWieght_Temp);
		try{
			//This cleans up a previously messy operation
		s.execute("Drop Table " + this.temp_table);
		}catch(Throwable R){
			
		}
		s.execute("create table " + this.temp_table + "(firstn varchar(80), lastn varchar(80), gender varchar(10))");
		s.execute(QueryGenerator.getNameIndexQuery(this.unconf, this.temp_table));
		psInsert = conn.prepareStatement("insert into " + this.temp_table + " values (?, ?, ?)");
		
		}catch(Throwable r){
			//System.out.println("Generator Setup Failed");
			r.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	/**
	 * This method is used to generate corresponding weight arrays for first and last names.
	 * @param array This is an array of doubles who's indices correspond with the name arrays.
	 * @return This returns an array of doubles that the generator uses to select names.
	 */
	private double[] getWieght(double[] array){
		double wieght_total = 0;
		double wieght_sum = 0;
		double[] wieght = new double[array.length];
		for(int i = 0; i<array.length; i++){
			wieght_total = wieght_total+array[i];
		}
		for(int i = 0; i<wieght.length; i++){
			wieght[i] = wieght_sum = wieght_sum + array[i]/wieght_total;
			//System.out.println("Wieght " + i + ": " + wieght[i]);
		}
		return wieght;
	}

	public String[] nextTuple(){
		String[] Tuple = new String[3];
		String Firstname;//Get a First Name
		String Gender;//Get a Gender
		String Lastname;//Gets a Last Name
		
		if(this.Gender == MALE){
			Gender = "Male";
			Firstname = nextMale();
		}else if(this.Gender == FEMALE){
			Gender = "Female";
			Firstname = nextFemale();
		}else{///Configured to give Both male and Female
			int rand = R.nextInt(2);//Generate 1 or 0
			if(rand == MALE){
				Gender = "Male";
				Firstname = nextMale();
			}else{
				Gender = "Female";
				Firstname = nextFemale();
			}
		}
		Lastname = nextLast();
		//Now lets populate the Tuple
		Tuple[0] = Firstname;
		Tuple[1] = Lastname;
		Tuple[2] = Gender;
		try {
			rs.close();
			//System.out.println("Closed Result Set");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.close();
			e.printStackTrace();
		}
		//Return the newly assembled Data
		//verifyData(); //Used for Debugging
		if(this.unique){
			String q = QueryGenerator.getUniqueNameQuery(this.unconf, temp_table, Tuple);
			int NumberofResults = 1;
			try{
			rs = s.executeQuery(q);
			rs.last();//Go to last row in the results
			NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
			rs.first();
			}catch(Throwable R){
				//Query Failed
				R.printStackTrace();
				System.exit(0);
			}
			if(NumberofResults != 0){
				//This is not unique!
				return nextTuple();
			}else{
				try{
				for(int i = 0; i<Tuple.length; i++){
				psInsert.setString(i+1, Tuple[i]);
				}
				psInsert.executeUpdate();
				return Tuple;//if this is unique
				}catch(Throwable R){
					R.printStackTrace();
					System.exit(-1);
				}
			}
		}
		return Tuple;
	}
	/**
	 * This method returns a random male name.
	 * @return This String contains a randomly selected male name.
	 */
	private String nextMale(){
		double rand = R.nextDouble();
		//System.out.println("Random Double For nextMale: " + rand);
		for(int i = 0; i < this.MaleWieght.length; i++){
			if(rand<this.MaleWieght[i]){
				return this.MaleNames[i];
			}
		}
		System.exit(-1);//If it gets here, then something went wrong with the weighting
		return "MALE ERROR";
	}
	
	/**
	 * This method returns a random female name.
	 * @return This String contains a randomly selected female name.
	 */
	private String nextFemale(){
		double rand = R.nextDouble();
		//System.out.println("Random Double For nextFemale: " + rand);
		for(int i = 0; i < this.FemaleWieght.length; i++){
			if(rand<this.FemaleWieght[i]){
				return this.FemaleNames[i];
			}
		}
		System.exit(-1);//If it gets here, then something went wrong with the weighting
		return "FEMALE ERROR";
	}
	
	/**
	 * This method returns a random last name.
	 * @return This String contains a randomly selected last name.
	 */
	private String nextLast(){
		double rand = R.nextDouble();
		//System.out.println("Random Double For nextLast: " + rand);
		for(int i = 0; i < this.LastWieght.length; i++){
			if(rand<this.LastWieght[i]){
				return this.LastNames[i];
			}
		}
		System.exit(-1);//If it gets here, then something went wrong with the weighting
		return "MALE ERROR";
	}
	
	
	//This function cleans up the database
	/**
	 * This performs cleanup operations, deleting temporary tables and indices. 
	 */
	public void DisassembleGenerator(){
	try{
		s.execute("Drop Table " + this.temp_table);
	}catch(Throwable r){
		//System.out.println("The Location Cleanup Failed");
		r.printStackTrace();
		System.exit(-1);			
	}
	}
	
	
}
