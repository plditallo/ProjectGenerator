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

import datagenerator.util.CSVReader;
import datagenerator.util.QueryGenerator;
/**
 * This generator loads data from a comma spaced value file into memory and then randomly returns entries from
 * the data. This generator is designed to work with weighted data.
 * @author Patrick McMorran
 *
 */
public class CustomWieghtGenerator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Data Generation Info
	private Random R; //Random Class for generating
	private String[] Data; //Data that the use supplies
	private double[] Wieght;
	private String csvFile; //String containing the path to the CSV file
	
	//Database Info
	private static String table_prefix = "temp_custom";//This is the custom_table_prefix
	private Connection conn = null;
	private PreparedStatement psInsertCustom = null;
    private Statement s = null;
    private ResultSet rs = null;
    
	
	//Queries used for the database
	private String getCustom;
	
	private boolean unique;
    private String temp_table;
    static int generatorID = 999999;
    private PreparedStatement psInsert = null;
	
    /**
	 * This is the constructor for the generator.
	 * @param c This is the connection object used to interact with the database.
	 * @param File This is a string containing the path to the CSV file which this generator uses for data.
	 * @param u This boolean value denotes whether the generator should produce unique values. True = unique.
	 */
	public CustomWieghtGenerator(Connection c, String File, boolean u){
		R = new Random();
		this.csvFile = File;
		this.conn = c;	
		getCustom = QueryGenerator.getCustomWeightQuery();
		unique = u;
		this.temp_table = "temp_customwieght_" + generatorID;
		generatorID--;
		}
	
	
	//This function is called to load in the CSV data that it uses
	//to generate random data
	/**
	 * This is used to prepare the generator, creating required tables in the database, loading required
	 * data into memory, and handling any other dependencies.
	 */
	public void PrepareGenerator(){
		CSVReader CSVCustom = null;
		try{
		//If the database was previously prepared, don't load the data;
		
			//This reads the CSV file line by line a parses it.
		CSVCustom = new CSVReader(csvFile);
		//System.out.println("Created CSV Reader");
		
		//This will hold each row parsed
		String[] Tuple;
		//This is a generic SQL statement, which we will use to execute create and drop queries
		s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		//System.out.println("Created SQL Statement");
		
		try{
			//This cleans up a previously messy operation
		s.execute("Drop Table " + this.temp_table);
		}catch(Throwable R){
			
		}
		try{
			//This cleans up a previously messy operation
		s.execute("Drop Table " + table_prefix);
		}catch(Throwable R){
			
		}
		//This executes a create query to make the Name Tables
		s.execute("create table " + table_prefix + "(dat varchar(80), dat1 double)");
		s.execute("create table " + temp_table + "(string varchar(80))");
		//System.out.println("Created Custom Table");
		
		//This is an insert statement, used to fill the Location table with the supplied CSV file
		psInsertCustom = conn.prepareStatement("insert into " + table_prefix + " values (?, ?)");
		psInsert = conn.prepareStatement("insert into " + this.temp_table + " values (?)");
		//System.out.println("Created Insert Statement");
		
		//This loop reads through the CustomCSV file, builds an insert query and executes it line by line.
		//int j = 0;
		while ((Tuple = CSVCustom.getTuple())!=null){
			//System.out.println("Female Row Number: " + j++);
			psInsertCustom.setString(1, Tuple[0]); //First column
			psInsertCustom.setDouble(2, Double.parseDouble(Tuple[1])); //Second column
			psInsertCustom.executeUpdate();
		}
		//System.out.println("Completed loading the CSV to Database");
		
		conn.commit();
		//System.out.println("Commited Changes to Database");
		//end database prep
		
		//Load Custom Data into array
		rs = s.executeQuery(getCustom);
		//System.out.println("Execute Query for Male Names");
		rs.last();//Go to last row in the results
		int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
		rs.first();//Go back to the first row
		//System.out.println("Check Number of Results");
		Data = new String[NumberofResults];
		double[] WieghtTemp = new double[NumberofResults];
		//System.out.println("Created Data Array - Custom");
		for(int i = 0; i<NumberofResults; i++){
			Data[i] = rs.getString(1);
			WieghtTemp[i] = rs.getDouble(2);
			rs.next();
		}
		//System.out.println("Loaded Custom Results into memory");
		rs.close();
		//System.out.println("Close Resultset");
		
		//Drop the table now that the data is collected.
		s.execute("drop table " + table_prefix);
		
		Wieght = getWieght(WieghtTemp);
		
			}catch(Throwable r){
			//System.out.println("Generator Setup Failed");
			r.printStackTrace();
			System.exit(-1);
		}
		
	}
	/**
	 * This method generates a weighted array to use to randomly select values from the csv file.
	 * @param array This is an array of double containing the weights of their corresponding values
	 * supplied in the csv file.
	 * @return This is the weight array used by the program to randomly select data.
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

	/**
	 * This method returns a string containing a randomly selected value from the CSV file supplied to the generator.
	 * @return This String contains a random value from a csv file.
	 */
	public String nextTuple(){
		double rand = R.nextDouble();
		//System.out.println("Random Double For nextMale: " + rand);
		String Tuple = null;
		for(int i = 0; i < this.Wieght.length; i++){
			if(rand<this.Wieght[i]){
				Tuple = this.Data[i];
				break;
			}
		}
		if(this.unique){
			String q = QueryGenerator.getCustomUniqueQuery(Tuple, temp_table);
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
				psInsert.setString(1, Tuple);
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
	 * This performs cleanup operations, deleting temporary tables and indices. 
	 */
	public void DestroyGenerator(){
		try {
			s.execute("Drop Table " + this.temp_table);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
