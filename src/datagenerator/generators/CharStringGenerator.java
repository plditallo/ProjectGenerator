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
import java.sql.Statement;
import java.util.Random;

import datagenerator.util.QueryGenerator;

/**
 * This is the character string generator.
 * @author Patrick McMorran
 *
 */
public class CharStringGenerator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static char[] Number_Array = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static char[] Letter_Array_Up = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
		'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	private static char[] Letter_Array_Low = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
		'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'q', 'r', 's',
		't', 'u', 'v', 'w', 'x', 'y', 'z'}; 
	private char[] Dat;
	private Random R;
	private int string_len;
	private boolean unique;
    private String temp_table;
    static int generatorID = 999999;
    
    //////////////Database
    private Connection conn;
    private Statement s = null;
    private ResultSet rs = null;
    private PreparedStatement psInsert = null;
	
    /**
     * This is the CharStringGenerator's constructor
     * @param B This is an array of boolean values that designate what types of characters should be
     * in the character string. True designates that the value type should be used.
     * Array[0] = Numerical (0-9)
     * Array[1] = Uppercase (A-Z)
     * Array[2] = Lowercase (a-z)
     * @param len This integer is the required length of the character string.
     * @param u This boolean value denotes if the random values generated should be unique.
     */
	public CharStringGenerator(boolean[] B, int len, boolean u){
		this.string_len = len;
		unique = u;
		R = new Random();
		int array_length = 0;
		if(B[0]){//User wants Numbers
			array_length = array_length + Number_Array.length; 
		}
		if(B[0]){//User wants Uppercase Characters
			array_length = array_length + Letter_Array_Up.length; 
		}
		if(B[0]){//User wants Lowercase Characters
			array_length = array_length + Letter_Array_Low.length; 
		}
		Dat = new char[array_length];
		int g = 0;//For Tracking the Dat array space
		if(B[0]){//User wants Numbers
			for(int i = 0; i < Number_Array.length; i++, g++){
				Dat[g] = Number_Array[i];
			} 
		}
		if(B[1]){//User wants Uppercase Characters 
			for(int i = 0; i < Letter_Array_Up.length; i++, g++){
				Dat[g] = Letter_Array_Up[i];
			}
		}
		if(B[2]){//User wants Lowercase Characters
			for(int i = 0; i < Letter_Array_Low.length; i++, g++){
				Dat[g] = Letter_Array_Low[i];
			}
		}
		this.temp_table = "temp_charstring_" + generatorID;
		generatorID--;
	}
	
	/**
	 * This is used to prepare the generator, creating required tables in the database, loading required
	 * data into memory, and handling any other dependencies.
	 * @param c This is the connection object to the database that the generator will use.
	 */
	public void prepareGenerator(Connection c){
		this.conn = c;
		try{
		s = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
		try{
			//This cleans up a previously messy operation
		s.execute("Drop Table " + this.temp_table);
		}catch(Throwable R){
			
		}
		s.execute("create table " + this.temp_table + "(string varchar(" + (this.string_len+1) + "))");
		psInsert = conn.prepareStatement("insert into " + this.temp_table + " values (?)");
		
		}catch(Throwable R){
			R.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	/**
	 * This performs cleanup operations, deleting temporary tables and indices. 
	 */
	public void destroyGenerator(){
		try{
			conn.commit();
			String Q = "Drop Table " +  this.temp_table;
			this.s.execute(Q);
		}catch(Throwable r){
			r.printStackTrace();
			System.exit(-1);			
		}
	}
	
	/**
	 * This method returns a random character string that meets the generator's specifications.
	 * @return This String contains random characters that meet the generator's criteria.
	 */
	public String nextTuple(){
		String Ret = "";
		for(int i = 0; i < string_len; i++){
			 Ret += Dat[R.nextInt(Dat.length)];
		}
		if(this.unique){
			String q = QueryGenerator.getCharStringUniqueQuery(Ret, temp_table);
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
				psInsert.setString(1, Ret);
				psInsert.executeUpdate();
				return Ret;//if this is unique
				}catch(Throwable R){
					R.printStackTrace();
					System.exit(-1);
				}
			}
		}
		return Ret;
	}
}
