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
package datagenerator.util;

/**
 * This class has several static methods for dynamically generating SQL queries used by various parts of
 * the program to interact with the database.
 * @author Patrick McMorran
 *
 */
public class QueryGenerator {
	
	/**
	 * This method returns a string containing a query to retrieve data on specified states.
	 * @param States This is an array of strings containing each state that should be called.
	 * @return This String contains the query to get location data specific to the defined states 
	 */
	public static String getStateQuery(String[] States){
		if(States.length==1){
			return getAState(States);
		}else if(States.length==51){
			return getAllStates(States);
		}else{
			return getSomeStates(States);
		}
	}
	//This function returns a query string for the state information of all the states
	/**
	 * This function returns a query for retrieving the data of All the states.
	 * @param States This is an array of Strings containing the names of states that should be in the query.
	 * @return This is a String containing the query.
	 */
	private static String getAllStates(String[] States){
		String Query = null;
		Query = "SELECT builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city " +
			"FROM builtin_location " +
			"GROUP BY builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city";
		return Query;
	}
	
	/**
	 * This method returns a query that returns the population of a given zipcode.
	 * @param Zip This is a String containing a 5 digit zipcode.
	 * @return This String contains the SQL Query Generated.
	 */
	public static String getPopulation(String Zip){
		String Query = null;
		Query = "SELECT builtin_location.population " +
			"FROM builtin_location " +
			"WHERE (builtin_location.zipcode = '" +
			Zip + "') ";// +
			//"GROUP BY builtin_location.population";
		return Query;
	}
	
	/**
	 * This method returns a String containing a query to fetch all the street names located in a zipcode.
	 * @param Zip This String contains the 5 digit zipcode used for the query.
	 * @return This String contains the SQL Query Generated.
	 */
	public static String getZipStreets(String Zip){
		String Query = "SELECT builtin_streets.street " +
		"FROM builtin_streets " +
		"WHERE ((builtin_streets.zipcode)='" + Zip + "')";
		return Query;
	}
	
	//This function returns a query string for the state information of the specified states
	/**
	 * This function returns a query for retrieving the data of All the states.
	 * @param States This is an array of Strings containing the names of states that should be in the query.
	 * @return This is a String containing the query.
	 */
	private static String getSomeStates(String[] States){
		String Query = null;
		String PartA = "SELECT builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city " +
		"FROM builtin_location " +
		"GROUP BY builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city " +
		"HAVING ";
		String Side_R = "OR (((builtin_location.state)='";
		String Side_L = "'))";
		Query = PartA + "(((builtin_location.state)='" + States[0] + Side_L;
		for(int i = 1; i<States.length; i++){
			Query += " " + Side_R + States[i] + Side_L;
		}
		return Query;
	}
	//This function returns a query string for the state information of all the states
	/**
	 * This function returns a query for retrieving the data of All the states.
	 * @param States This is an array of Strings containing the names of states that should be in the query.
	 * @return This is a String containing the query.
	 */
	private static String getAState(String[] States){
		String Query = null;
		String PartA = "SELECT builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city " +
		"FROM builtin_location " +
		"GROUP BY builtin_location.zipcode, builtin_location.population, builtin_location.state, builtin_location.city " +
		"HAVING (((builtin_location.state)='";
		String PartB = "'))";
		Query = PartA + States[0] + PartB;
		return Query;
	}
	
	//This function returns a query to get all the area codes of a selected zipcode
	
	/**
	 * This method returns all the area codes within a given zipcode.
	 * @param Zipcode This String contains the zipcode.
	 * @return This String contains the generated SQL Query.
	 */
	public static String getAreaCodes(String Zipcode){
		String Query = "SELECT builtin_location.areacode " +
		"FROM builtin_location " +
		"GROUP BY builtin_location.areacode, builtin_location.zipcode " +
		"HAVING (((builtin_location.zipcode)='" + Zipcode + "'))";
		return Query;
	}
	
	/**
	 * This method returns an SQL query that retrieves all of the phone prefixes within a given Zipcode and Areacode. 
	 * @param Zipcode This is a String that contains the Zipcode for the Query.
	 * @param Areacode This is a String that contains the Areacode for the Query.
	 * @return This String contains the SQL Query.
	 */
	public static String getPrefix(String Zipcode, String Areacode){
		String Query = "SELECT builtin_location.areacode, builtin_location.prefix " +
		"FROM builtin_location " +
		"GROUP BY builtin_location.areacode, builtin_location.zipcode, builtin_location.prefix " +
		"HAVING (((builtin_location.areacode)='" + Areacode + "') AND ((builtin_location.zipcode)='" + Zipcode + "'))";
		return Query;
	}
	
	/**
	 * This returns a String with an SQL Query that is used to return Male Name Data.
	 * @return This String contains an SQL Query.
	 */
	public static String getMaleNames(){
		String Query = "SELECT builtin_malenames.name, builtin_malenames.wieght " +
		"FROM builtin_malenames";
		return Query;
	}
	
	/**
	 * This returns a String with an SQL Query that is used to return Female Name Data.
	 * @return This String contains an SQL Query.
	 */
	public static String getFemaleNames(){
		String Query = "SELECT builtin_femalenames.name, builtin_femalenames.wieght " +
		"FROM builtin_femalenames";
		return Query;
	}
	
	/**
	 * This returns a String with an SQL Query that is used to return Last Name Data.
	 * @return This String contains an SQL Query.
	 */
	public static String getLastNames(){
		String Query = "SELECT builtin_lastnames.name, builtin_lastnames.wieght " +
		"FROM builtin_lastnames";
		return Query;
	}
	
	/**
	 * This returns a String with an SQL Query that is used to return Custom Data.
	 * @return This String contains an SQL Query.
	 */
	public static String getCustomQuery(){
		String Query = "SELECT temp_custom.dat " +
		"FROM temp_custom";
		return Query;
	}
	
	/**
	 * This returns a String with an SQL Query that is used to return Custom Weighted Data.
	 * @return This String contains an SQL Query.
	 */
	public static String getCustomWeightQuery(){
		String Query = "SELECT temp_custom.dat, temp_custom.dat1 " +
		"FROM temp_custom";
		return Query;
	}
	
	/**
	 * This generates a query that is used to build an index on the unique data table used to 
	 * keep track of previously generated location data.
	 * @param settings This is used to configure the query to only track data that the generator
	 * is configured to produce.
	 * @param Table_name This String is the name of the Field's unique table.
	 * @return This String contains the query generated.
	 */
	public static String getLocationIndexQuery(boolean[] settings, String Table_name){
		String query = "CREATE INDEX Idx_" + Table_name + " ON " + Table_name+ "("; //Query Start
		String[] columns = {"zipcode", "state", "city", "areacode", "prefix", "extension", "street"};
		int index_count = 0;
		for(int i = 0; i < settings.length; i++){
			if(settings[i]){
				if(index_count != 0){
					query += " ";
				}
				query += columns[i] + ",";
				index_count++;
			}
		}
		query = query.substring(0, query.length()-1);
		query += " )";//Endcap
		return query;
	}
	
	/**
	 * This generates a query that is used to build an index on the unique data table used to 
	 * keep track of previously generated name data.
	 * @param settings This is used to configure the query to only track data that the generator
	 * is configured to produce.
	 * @param Table_name This String is the name of the Field's unique table.
	 * @return This String contains the query generated.
	 */
	public static String getNameIndexQuery(boolean[] settings, String Table_name){
		String query = "CREATE INDEX Idx_" + Table_name + " ON " + Table_name+ "("; //Query Start
		String[] columns = {"firstn", "lastn", "gender"};
		int index_count = 0;
		for(int i = 0; i < settings.length; i++){
			if(settings[i]){
				if(index_count != 0){
					query += " ";
				}
				query += columns[i] + ",";
				index_count++;
			}
		}
		query = query.substring(0, query.length()-1);
		query += ")";//Endcap
		return query;
	}
	
	/**
	 * This method generates a query that is used to verify that a location entry has not already 
	 * been generated.
	 * @param settings This is a boolean array that defines what data the generator is generating.
	 * @param Table_name This is a String containing the name of the Field's Unique table.
	 * @param tuple This array of Strings contains the randomly generated data that the generator needs
	 * to ensure it hasn't been previously generated.
	 * @return This String contains the SQL Query.
	 */
	public static String getUniqueLocationQuery(boolean[] settings, String Table_name, String[] tuple){
		String Query = "SELECT " + Table_name + ".zipcode, " + Table_name + ".state, " + Table_name + ".city, " + Table_name + ".areacode, " + Table_name + ".prefix, " + Table_name + ".extension, " + Table_name + ".street";
		String[] Wheres = {"((" + Table_name + ".zipcode)='", "((" + Table_name + ".state)='", "((" + Table_name + ".city)='", "((" + Table_name + ".areacode)='", "((" + Table_name + ".prefix)='", "((" + Table_name + ".extension)='", "((" + Table_name + ".street)='"}; 
		String endcap = "')";
		String and = " AND ";
		Query += " FROM " + Table_name;
		Query += " WHERE (";
		//build where
		boolean notfirst = false;
		for(int i = 0; i<settings.length; i++){
			if(settings[i]){
				if(notfirst){
					Query += and;
				}
				Query += Wheres[i];
				Query += tuple[i];
				Query += endcap;
				notfirst = true;
			}
		}
		Query += ")";
		return Query;
	}
	
	/**
	 * This method returns a string containing a query that checks the if the supplied character string
	 * is unique.
	 * @param CharString This is the character String that was randomly generated.
	 * @param TableName This is the name of the table that the CharStringField uses to track generated queries.
	 * @return This String Contains the Query.
	 */
	public static String getCharStringUniqueQuery(String CharString, String TableName){
		String Query = null;
		Query = "SELECT " + TableName + ".string " +
			"FROM " + TableName + 
			" WHERE (" + TableName + ".string = '" +
			CharString + "')";
		return Query;
	}
	
	/**
	 * This method returns a string containing a query that checks the if the supplied String
	 * is unique. 
	 * @param CharString This is the character String that was randomly selected.
	 * @param TableName This is the name of the table that the CharStringField uses to track generated queries.
	 * @return This String Contains the Query.
	 */
	public static String getCustomUniqueQuery(String CharString, String TableName){
		String Query = null;
		Query = "SELECT " + TableName + ".string " +
			"FROM " + TableName + 
			" WHERE (" + TableName + ".string = '" +
			CharString + "')";
		return Query;
	}
	
	/**
	 * This method generates a query that is used to verify that a name entry has not already 
	 * been generated.
	 * @param settings This is a boolean array that defines what data the generator is generating.
	 * @param Table_name This is a String containing the name of the Field's Unique table.
	 * @param tuple This array of Strings contains the randomly generated data that the generator needs
	 * to ensure it hasn't been previously generated.
	 * @return This String contains the SQL Query.
	 */
	public static String getUniqueNameQuery(boolean[] settings, String Table_name, String[] tuple){
		String Query = "SELECT " + Table_name + ".firstn, " + Table_name + ".lastn, " + Table_name + ".gender";
		String[] Wheres = {"((" + Table_name +".firstn)='", "((" + Table_name + ".lastn)='"}; 
		String endcap = "')";
		String and = " AND ";
		Query += " FROM " + Table_name;
		Query += " WHERE (";
		//build where
		boolean notfirst = false;
		for(int i = 0; i<settings.length-1; i++){
			if(settings[i]){
				if(notfirst){
					Query += and;
				}
				Query += Wheres[i];
				Query += tuple[i];
				Query += endcap;
				notfirst = true;
			}
		}
		Query += ")";
		return Query;
	}
	
}
