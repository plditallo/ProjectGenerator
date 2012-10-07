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
package datagenerator.fields;

import java.io.Serializable;
import java.sql.Connection;

import datagenerator.fields.compress.CompressedField;

	/** 
	 * Field Classes are designed to work similar to the java.util.Random Class. Once prepared and configured
	 * the Field has a nextField method which produces the one record of the desired random data. The Field should
	 * also have a configure method who's parameters take in the different configuration options chosen by the user.
	 * @author Patrick McMorran
	 */
public interface Field extends Serializable {
	
	/**
	 * The getName method fetches the name from the Field.
	 * @return This method returns a string containing the Field's name.
	 */
	public String getName();//Used to populate Gui
	
	/**
	 * This retrieves an integer which maps to reveal the Field's type.
	 * @return This method returns an int denoting the Field's type.
	 */
	public int getType();//To retrieve Tuple Type
	
	/**
	 * This method takes a database connection object and uses it to prepare the generator.
	 * @param c the database that the generator should connect to.
	 */
	public void Create(Connection c);//Used by Generator to prepare Tuple
	
	/**
	 * This method is similar to the nextInt method of the random class, in that it returns a string
	 * containing the randomly generated field(s) that Field Introduced.  
	 * @return This method returns a string containing the randomly generated data. If the Field Generates 
	 * multiple columns, they will be comma spaced.
	 */
	public String nextField();//Used to get the line
	
	/**
	 * This is a cleanup method that causes the Field to clean up the temporary data left in the
	 * database after generation.
	 */
	public void Destroy();//Used to cleanup the Tuple Calculation Stuff
	
	/**
	 * This method launches a GUI that is custom made to allow the user to configure the different options
	 * the field has to offer.
	 */
	public void Edit();//Used to launch the GUI that configures the Tuple.
	
	/**
	 * This method is used to give the Field a pointer to the GUI panel that it is contained within.
	 * @param o This is a reference to the GUI pane that holds this Field when the user is configuring
	 * the parameters for the entire data set.
	 */
	public void addGUI(datagenerator.gui.Field_GUI o);
	
	/**
	 * This method refreshes the GUI panel that the Field is stored in.
	 */
	public void refreshGUI();
	
	/**
	 * This method returns a boolean value representing whether the Field has been activated or not.
	 * @return This is a boolean that denotes if this Field has been activated.
	 */
	public boolean getActivation();
	
	/**
	 * This method Activates the field, which means the user configured the field.
	 */
	public void setActivation();
	
	/**
	 * This method sets the boolean value that designates whether or not the Field should contain unique values.
	 * @param val This boolean value denotes if the field should only contain unique data.
	 */
	public void setUnique(boolean val);
	
	/**
	 * This returns the column titles of the Field.
	 * @return This returns a String of the Field Title(s), separated with commas if there are multiple columns.
	 */
	public String getTitles();
	
	/**
	 * This method returns a CompressedField containing the variables with each configuration value.
	 * @return This Compressed Field will be of the a corresponding type to the field.
	 */
	public CompressedField getConfig();
	
}
