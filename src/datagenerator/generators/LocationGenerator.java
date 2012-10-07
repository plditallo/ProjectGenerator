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
import java.util.Random;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import datagenerator.util.QueryGenerator;

/**
 * This class is used to generate random location data, and directly interacts with the database.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("unused")
public class LocationGenerator implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		//Configuration Parameters
		private String[] states_selected;
		
		//Data for Generation
		private double[] Population;
		private String[] Zipcode;
		private String[] State;
		private String[] City;
		//
		private double[] Population_Wieght;
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
	    ////////////////////////
	    //Queries
	    ////////////////////////
	    //This String holds the Query to select the information of the desired states
	    private String GetStatesInfo;
		boolean[] TupleConf;
	    
		/**
		 * This is the constructor for the location generator.
		 * @param c This is the connection object used to interact with the database.
		 * @param States This is an array of strings, each string containing the name of a state. This
		 * state is used to define the scope of the location data the generator should use.
		 * @param B This is a boolean array that designates which columns the generator should output. The order goes:
	 * Array[0] = zipcode 
	 * Array[1] = state
	 * Array[2] = city
	 * Array[3] = areacode
	 * Array[4] = prefix
	 * Array[5] = extension
	 * Array[6] = street
		 * @param u This boolean value designates if the generator should generate unique values. True = unique.
		 */
		public LocationGenerator(Connection c, String[] States, boolean B[], boolean u){
			this.states_selected = States;
			this.conn = c;
			this.TupleConf = B;
			this.GetStatesInfo = QueryGenerator.getStateQuery(states_selected);
			this.unique = u;
			this.temp_table = "temp_location_" + generatorID;
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
			try{
				//This cleans up a previously messy operation
			s.execute("Drop Table " + this.temp_table);
			}catch(Throwable R){
				
			}
			s.execute("create table " + this.temp_table + "(zipcode varchar(6), state varchar(60), city varchar(120), areacode varchar(3), prefix varchar(3), extension varchar(4),  street varchar(120))");
			//This fetches the query to create an index on the desired data, and then executes it
			s.execute(QueryGenerator.getLocationIndexQuery(this.TupleConf, this.temp_table));
			
			///////
			psInsert = conn.prepareStatement("insert into " + this.temp_table + " values (?, ?, ?, ?, ?, ?, ?)");
			rs = s.executeQuery(GetStatesInfo);
			//System.out.println("Execute Query for the Specified States");
			rs.last();//Go to last row in the results
			int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
			rs.first();//Go back to the first row
			//System.out.println("Check Number of Results");
			Population = new double[NumberofResults];
			Zipcode = new String[NumberofResults];
			State = new String[NumberofResults];
			City = new String[NumberofResults];
			//System.out.println("Created Data Arrays");
			for(int i = 0; i<NumberofResults; i++){
				Zipcode[i] = rs.getString(1);
				Population[i] = rs.getDouble(2);
				State[i] = rs.getString(3);
				City[i] = rs.getString(4);
				rs.next();
			}
			//System.out.println("Loaded Results into memory");
			rs.close();
			//System.out.println("Close Resultset");
			Population_Wieght = getWieght();
			//System.out.println("Create WieghtArray");
			
			}catch(Throwable r){
				System.out.println("Location Generator Setup Failed");
				r.printStackTrace();
				System.exit(-1);
			}
			
		}
		
		/**
		 * This method is used to generate weight arrays for zipcodes based off of the population in each zipcode.
		 * @return This double is an array of weights corresponding to each zipcode value.
		 */
		private double[] getWieght(){
			double population_total = 0;
			double wieght_sum = 0;
			double[] wieght = new double[this.Population.length];
			for(int i = 0; i<this.Population.length; i++){
				population_total = population_total+this.Population[i];
			}
			for(int i = 0; i<wieght.length; i++){
				wieght[i] = wieght_sum = wieght_sum + this.Population[i]/population_total;
				//System.out.println("Wieght " + i + ": " + wieght[i]);
			}
			return wieght;
		}
		//This method is used for debugging to verify the data.
		private void verifyData(){
			for(int i = 0; i<this.City.length; i++){
				//System.out.println(this.Zipcode[i] + "  " + this.State[i] + "  " + this.City[i]);
			}
		}
		
		/**
		 * Because this generator generates multiple fields, it returns an array of strings with an array
		 * location for each requested field.
		 * @return Each array location contains a string holding one of the fields the generator was configured for.
		 */
		public String[] nextTuple(){
			
			String[] Tuple = new String[7];
			int index = nextLine(); //This is the index of all the information, excluding phone
			String AreaCode = null;//Get a corresponding Area Code
			String Prefix = null; //Get a Prefix
			String Extension = null; //Gets the last 4 digits of the Phone number
			String Street = null;
			//if(TupleConf[3]||TupleConf[4]||TupleConf[5]){
			AreaCode = nextArea(index);//Get a corresponding Area Code
			Prefix = nextPrefix(index, AreaCode); //Get a Prefix
			Extension = nextExtension(); //Gets the last 4 digits of the Phone number
			Street = nextStreet(index);
			//}
			//System.out.println("Index for Data:" + index);
			//Now lets populate the Tuple // 
			Tuple[0] = this.Zipcode[index];
			Tuple[1] = this.State[index];
			Tuple[2] = this.City[index];
			Tuple[3] = AreaCode;
			Tuple[4] = Prefix;
			Tuple[5] = Extension;
			Tuple[6] = Street;
			
			/*///////////Look inside: 
			for(int i = 0; i<Tuple.length;i++){
				System.out.println(Tuple[i]);
			}// */
			/////////////
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
				//checking process - This ensures that the Location is unique
					String Qu = QueryGenerator.getUniqueLocationQuery(this.TupleConf, this.temp_table, Tuple);
					int NumberofResults = 1;
					try{
					rs = s.executeQuery(Qu);
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
				//}
			}
			return Tuple;
		}
		
		/**
		 * This returns an integer that corresponds to a zipcode's array location and corresponding data.
		 * @return An integer that is the index number of the zipcode, city, state arrays.
		 */
		private int nextLine(){
			double rand = R.nextDouble();
			//System.out.println("Random Double For nextLine: " + rand);
			for(int i = 0; i < this.Population_Wieght.length; i++){
				if(rand<this.Population_Wieght[i]){
					return i;
				}
			}
			System.exit(-1);//If it gets here, then something went wrong with the weighting
			return 0;
		}
		
		//This function cleans up the database
		/**
		 * This performs cleanup operations, deleting temporary tables and indices. 
		 */
		public void DisassembleGenerator(){
		try{
			conn.commit();
			String Q = "Drop Table " +  this.temp_table;
			this.s.execute(Q);
			
			
		}catch(Throwable r){
			//System.out.println("The Location Cleanup Failed");
			r.printStackTrace();
			System.exit(-1);			
		}
		}
		
		/**
		 * This returns a street name located in the given zipcode.
		 * @param zipcode_index This is an integer containing the index of the zipcode array that
		 * contains the zipcode selected by the generator. 
		 * @return This String contains a randomly selected street name from the zipcode.
		 */
		private String nextStreet(int zipcode_index){
			try{
			String Query = QueryGenerator.getZipStreets(this.Zipcode[zipcode_index]);
			//System.out.println(this.Zipcode[zipcode_index]);
			rs = s.executeQuery(Query);
			//rs = s.executeQuery("SELECT COUNT(*)" + "FROM builtin_streets");
			rs.last();//Go to last row in the results
			int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
			//System.out.println(NumberofResults);
			//System.out.println("Rows: " + rs.getString(1));
			//ResultSetMetaData M = rs.getMetaData();
			//System.out.println("Number of Cols: " + M.getColumnCount());
			//for(int i = 0; i<M.getColumnCount(); i++){
				//System.out.println(M.getColumnName(i+1));
			//}
			rs.first();//Go back to the first row
			int street_in = R.nextInt(NumberofResults);
			for(int i = 1; i < street_in; i++){
				rs.next();
			}
			String StreetName = rs.getString(1);
			Query = QueryGenerator.getPopulation(this.Zipcode[zipcode_index]);
			rs = s.executeQuery(Query);
			rs.first();
			int population = rs.getInt(1);
			int popmax = population/NumberofResults;
			String Address = new Integer(R.nextInt(popmax)).toString();
			Address += " " + StreetName;
			return Address;
			}catch(Throwable r){
				r.printStackTrace();
				return null;
			}
		}
		//Based on the zipcode, the program randomly selects a corresponding areacode for it.
		
		/**
		 * This method generates a random area code that corresponds with the generator's selected zipcode.
		 * @param zipcode_index This is the array index of the selected zipcode.
		 * @return This String contains a randomly selected area code corresponding to the zipcode. 
		 */
		private String nextArea(int zipcode_index){
			try{
			String Query = QueryGenerator.getAreaCodes(this.Zipcode[zipcode_index]);
			rs = s.executeQuery(Query);
			rs.last();//Go to last row in the results
			int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
			rs.first();//Go back to the first row
			int area_in = R.nextInt(NumberofResults);
			for(int i = 1; i < area_in; i++){
				rs.next();
			}
			return rs.getString(1);
			
			}catch(Throwable r){
				//Getting area code failed :<
				//System.out.println("Getting Area Code Failed");
				System.exit(-1);
				return null;//This line should never be executed
			}
		}
		
		/**
		 * This method selects a random phone number prefix contained within the selected zip and area codes.
		 * @param zipcode_index This is the array index of the selected zipcode.
		 * @param AreaCode This is a string containing the selected area code.
		 * @return This is a String containing the 3 digit phone prefix.
		 */
		private String nextPrefix(int zipcode_index, String AreaCode){
			try{
				String Query = QueryGenerator.getPrefix(this.Zipcode[zipcode_index], AreaCode);
				rs = s.executeQuery(Query);
				rs.last();//Go to last row in the results
				int NumberofResults = rs.getRow();//Get the row ID, so that we know how many rows there are
				rs.first();//Go back to the first row
				//System.out.println("Number of Prefix Results:" + NumberofResults);
				int prefix_in = R.nextInt(NumberofResults);
				//System.out.println("Prefix Index:" + prefix_in);
				for(int i = 1; i < prefix_in; i++){
					//System.out.println("Prefix i: " + rs.getString(2));
					rs.next();
				}
				return rs.getString(2);
				}catch(Throwable r){
					//Getting area code failed :<
					//System.out.println("Getting Prefix Code Failed");
					r.printStackTrace();
					System.exit(-1);
					return null;//This line should never be executed
				}
		}
		
		/**
		 * This method generates a random 4 digit number to be the phone number extension.
		 * @return This String contains the 4 digit phone number extension.
		 */
		private String nextExtension(){
			String Extension = "";
			for(int i=0; i<4; i++){
				Extension += R.nextInt(10);
			}
			return Extension;
		}
		
}
