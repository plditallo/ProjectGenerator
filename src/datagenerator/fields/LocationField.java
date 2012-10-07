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

import java.sql.Connection;

import datagenerator.fields.compress.CompressedField;
import datagenerator.fields.compress.LocationCompressedField;
import datagenerator.generators.LocationGenerator;

/**
 * The LocationField is the Field object that is used by the MainGUI. The Location field stands for
 * Location Data Field. This is a Field Class that generates random locations and corresponding phone 
 * numbers that wieghted using the population of each US Zipcode. This enables the end user to generate 
 * locations that are representative of certain demographics.
 * @author Patrick McMorran
 */
public class LocationField implements Field{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name = ""; //This is the Name if the Tuple while the user is using the GUI.
							//This enables the user to tell two tuples of the same type apart.
	private Connection conn = null; //This is the connection to the database
	private boolean[] ResultConfig = null; //This array Decides what output the Tuple Gives
	private String[] States = null; //This array configures the output of the location generator
	private LocationGenerator Location_Generator = null; //The Location Generator
	
	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_Location Config;
	
	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	
	boolean unique = false;
	
	/**
	 * This is the default constructor.
	 */
	public LocationField(){
		Config = new datagenerator.fields.config.Config_Location(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public LocationField(LocationCompressedField Comp){
		Config = new datagenerator.fields.config.Config_Location(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.ResultConfig = Comp.getResultConfig();
		this.States = Comp.getStates();
	}
	
	/**
	 * The Configure method is used as a set method to change the parameters of the field that the user defines.
	 * @param s This is an String array that contains the names of each state that the generator should use to generate
	 * location data.
	 * @param b This is a boolean array that designates which columns the generator should output. The order goes:
	 * Array[0] = zipcode 
	 * Array[1] = state
	 * Array[2] = city
	 * Array[3] = areacode
	 * Array[4] = prefix
	 * Array[5] = extension
	 * Array[6] = street
	 * A true Value means the generator will generate this value. A false value will not output a value.
	 * @param Nam This is the user assigned name of the Field, which is used to generate column titles.
	 */
	public void Configure(String[] s, boolean[] b, String Nam){
		this.ResultConfig = b;
		this.States = s;
		this.Name = Nam;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		 this.conn = c;
		 Location_Generator = new LocationGenerator(conn, States, ResultConfig, unique);
	     Location_Generator.PrepareGenerator();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Destroy() {
		 Location_Generator.DisassembleGenerator();	
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return this.Name;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return 0;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String nextField() {
		String[] Random_Data = Location_Generator.nextTuple();
		String Data = "";
		for(int i = 0; i < Random_Data.length; i++){
			if(this.ResultConfig[i]){
				Data += Random_Data[i] + ", ";
			}
		}
		return Data;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void Edit() {
		Config.setVisible(true);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void addGUI(datagenerator.gui.Field_GUI o) {
		this.GUITUPLE = o;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void refreshGUI() {
		this.GUITUPLE.RefreshPanel();
	}
	
	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean getActivation() {
		// TODO Auto-generated method stub
		return activated;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setActivation() {
		activated = true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setUnique(boolean val) {
		unique = val;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getTitles() {
		String[] columns = {"zipcode", "state", "city", "areacode", "prefix", "extension", "street"};
		String Data = "";
		for(int i = 0; i < columns.length; i++){
			if(this.ResultConfig[i]){
				Data += this.Name + "-" + columns[i] + ", ";
			}
		}
		return Data;
	}

	@Override
	public CompressedField getConfig() {
		return new LocationCompressedField(Name, activated, unique, ResultConfig, States);
	}
	
}
