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
package datagenerator.loaders;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

import datagenerator.util.CSVReader;

/**
 * This class is used by the loader class to import location data into the database.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("unused")
public class LocationLoader implements TupLoader{
	//Database stuff
	private Connection conn;
	private PreparedStatement psInsert = null;
	private PreparedStatement psInserts = null;
    private Statement s = null;
    private javax.swing.JProgressBar progressBar;
    
    /**
     * This is the constructor.
     * @param c This is a connection object used to interact with the database.
     */
	public LocationLoader(Connection c, javax.swing.JProgressBar progressBar){
		this.conn = c;
		this.progressBar = progressBar;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Load() {
		Runtime R = Runtime.getRuntime();
		CSVReader CSV = null;
		CSVReader CSV2 = null;
		try{
		//If the database was previously prepared, don't load the data;
		
			//This reads the CSV file line by line a parses it.
		CSV = new CSVReader("Data/builtin_location.csv");
		CSV2 = new CSVReader("Data/builtin_streets.csv");
		System.out.println("Created CSV Reader");
		
		//This will hold each row parsed
		String[] Tuple;
		//This is a generic SQL statement, which we will use to execute create and drop queries
		s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		System.out.println("Created SQL Statement");
		
		//This executes a create query to make the Location table
		s.execute("create table builtin_location(zipcode varchar(6), population int, state varchar(60), city varchar(120), areacode varchar(3), prefix varchar(3))");
		conn.commit();
		s.execute("create table builtin_streets(zipcode varchar(6), street varchar(60))");
		System.out.println("Created Location Tables");
		//This is an insert statement, used to fill the Location table with the supplied CSV file
		psInsert = conn.prepareStatement("insert into builtin_location values (?, ?, ?, ?, ?, ?)");
		psInserts = conn.prepareStatement("insert into builtin_streets values (?, ?)");
		System.out.println("Created Insert Statement");
		//This loop reads through the CSV file, builds an insert query and executes it line by line.
		int j = 0;
		System.out.println("Starting to load location data.");
		System.out.println("Warning:This can take a significant amount of time.");
		this.progressBar.setMaximum(CSV.length());
		while ((Tuple = CSV.getTuple())!=null){
			j++;
			this.progressBar.setValue(j);
			if(j%10000==0){
				//System.out.println("Completed " + j + " Entries.");
				conn.commit();
				R.gc();
			}
			//System.out.println("Row Number: " + j++);
			psInsert.setString(1, Tuple[0]); //Zipcode
			psInsert.setInt(2, Integer.parseInt(Tuple[1])); //Population
	        psInsert.setString(3, Tuple[2]); //Long State Name
	        psInsert.setString(4, Tuple[3]); //City Name
	        psInsert.setString(5, Tuple[4]); //Area Code
	        psInsert.setString(6, Tuple[5]); //Prefix
	        psInsert.executeUpdate();//This executes the newly filled query
		}
		System.out.println("Starting to load street names.");
		System.out.println("WARNING: This Generally Takes a LONG Time.");
		this.progressBar.setMaximum(CSV2.length());
		j = 0;
		while ((Tuple = CSV2.getTuple())!=null){
			j++;
			this.progressBar.setValue(j);
			/*
			if(j%100000==0){
				System.out.println("Completed " + j + " Entries.");
				//System.out.println(Tuple[0] + " : " + Tuple[1]);
				conn.commit();
			}*/
			//System.out.println("Row Number: " + j++);
			psInserts.setString(1, Tuple[0]); //Zipcode
			psInserts.setString(2, Tuple[1]); //Street Name
			psInserts.executeUpdate();//This executes the newly filled query
			if(j%10000==0){
				R.gc();
			}
			
		}
		System.out.println("Completed loading CSV to Database");
		conn.commit();
		s.execute("CREATE INDEX zipcodeIndexL ON builtin_location(zipcode)");
		System.out.println("Creating Zipcode Location Index");
		s.execute("CREATE INDEX stateIndex ON builtin_location(state)");
		System.out.println("Creating State Location Index");
		s.execute("CREATE INDEX zipcodeIndexS ON builtin_streets(zipcode)");
		System.out.println("Creating Street Index");
		//end database prep
		}catch(Throwable r){
			//LOAD FAILED
			r.printStackTrace();
			//JOptionPane.showMessageDialog(null, "READ");
			//System.exit(-1);
		}
	}

}
