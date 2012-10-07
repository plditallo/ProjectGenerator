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
 * This class is used by the Loader Class to import Name data into the database.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("unused")
public class NameLoader implements TupLoader {

	private Connection conn;
	private PreparedStatement psInsertFemale = null;
	private PreparedStatement psInsertMale = null;
	private PreparedStatement psInsertLast = null;
	private Statement s = null;
	private javax.swing.JProgressBar progressBar;
	
	/**
	 * This is the constructor.
	 * @param c This Connection Object is used to interact with the database to import the data.
	 */
	public NameLoader(Connection c, javax.swing.JProgressBar progressBar){
		this.conn = c;
		this.progressBar = progressBar;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Load() {
		CSVReader CSVMale = null;
		CSVReader CSVFemale = null;
		CSVReader CSVLast = null;
		try{
		//If the database was previously prepared, don't load the data;
		
			//This reads the CSV file line by line a parses it.
		CSVMale = new CSVReader("Data/builtin_malenames.csv");
		CSVFemale = new CSVReader("Data/builtin_femalenames.csv");
		CSVLast = new CSVReader("Data/builtin_lastnames.csv");
		//System.out.println("Created CSV Readers");
		
		//This will hold each row parsed
		String[] Tuple;
		//This is a generic SQL statement, which we will use to execute create and drop queries
		s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		System.out.println("Created SQL Statement");
		
		//If database prepared, don't create table
		
		//This executes a create query to make the Name Tables
		s.execute("create table builtin_femalenames(name varchar(80), wieght double)");
		s.execute("create table builtin_malenames(name varchar(80), wieght double)");
		s.execute("create table builtin_lastnames(name varchar(80), wieght double)");
		System.out.println("Created Location Table");
		//This is an insert statement, used to fill the Location table with the supplied CSV file
		psInsertFemale = conn.prepareStatement("insert into builtin_femalenames values (?, ?)");
		psInsertMale = conn.prepareStatement("insert into builtin_malenames values (?, ?)");
		psInsertLast = conn.prepareStatement("insert into builtin_lastnames values (?, ?)");
		//System.out.println("Created Insert Statement");
		
		//This loop reads through the FemaleCSV file, builds an insert query and executes it line by line.
		int j = 0;
		System.out.println("Starting to load Female names.");
		this.progressBar.setMaximum(CSVFemale.length());
		while ((Tuple = CSVFemale.getTuple())!=null){
			this.progressBar.setValue(j);
			//System.out.println("Female Row Number: " + j++);
//			JOptionPane.showMessageDialog(null, "1: " + Tuple[0] + "  2: " + Tuple[1]);
			psInsertFemale.setString(1, Tuple[0]); //Name
			psInsertFemale.setDouble(2, Double.parseDouble(Tuple[1])); //Weight
	        psInsertFemale.executeUpdate();//This executes the newly filled query
		}
		System.out.println("Completed loading Female CSV to Database");
		
		//This loop reads through the MaleCSV file, builds an insert query and executes it line by line.
		j = 0;
		this.progressBar.setMaximum(CSVMale.length());
		System.out.println("Starting to load Male names.");
		while ((Tuple = CSVMale.getTuple())!=null){
			//System.out.println("Male Row Number: " + j++);
			this.progressBar.setValue(j);
			psInsertMale.setString(1, Tuple[0]); //Name
			psInsertMale.setDouble(2, Double.parseDouble(Tuple[1])); //Weight
	        psInsertMale.executeUpdate();//This executes the newly filled query
		}
		System.out.println("Completed loading Male CSV to Database");
		
		//This loop reads through the LastNameCSV file, builds an insert query and executes it line by line.
		j = 0;
		this.progressBar.setMaximum(CSVLast.length());
		System.out.println("Starting to load Last names.");
		while ((Tuple = CSVLast.getTuple())!=null){
			this.progressBar.setValue(j);
			//System.out.println("Last Row Number: " + j++);
			psInsertLast.setString(1, Tuple[0]); //Name
			psInsertLast.setDouble(2, Double.parseDouble(Tuple[1])); //Weight
	        psInsertLast.executeUpdate();//This executes the newly filled query
		}
		System.out.println("Completed loading Last CSV to Database");
		
		conn.commit();
		System.out.println("Commited Changes to Database");
		//end database prep
		}catch(Throwable R){
			//Something in the load broke
			System.out.println("LOAD FAILED");
			R.printStackTrace();
		}
	}

}
