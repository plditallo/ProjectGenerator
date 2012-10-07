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
import datagenerator.fields.compress.CustomCompressedField;
import datagenerator.generators.CustomGenerator;

/**
 * The CustomField is the Field object that is used by the MainGUI. The Custom field stands for
 * Custom User Data Field. This is a Field Class that generates randomly selected data from the  
 * first column of a csv file the user supplied.
 * @author Patrick McMorran
 */
public class CustomField implements Field{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name = ""; //This is the Name if the Tuple while the user is using the GUI.
							//This enables the user to tell two tuples of the same type apart.
	private Connection conn = null; //This is the connection to the database

	//Generator Parameters
	private String FilePath;
	
	private CustomGenerator Generator;
	
	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_Custom Config;
	
	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	
	boolean unique = false;
	
	/**
	 * This is the default constructor.
	 */
	public CustomField(){
		Config = new datagenerator.fields.config.Config_Custom(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public CustomField(CustomCompressedField Comp){
		Config = new datagenerator.fields.config.Config_Custom(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.FilePath = Comp.getFilePath();
	}
	
	/**
	 * The Configure method is used as a set method to change the parameters of the field that the user defines.
	 * @param File This is a String that contains the file path to the CSV file where the data the generator will
	 * use is located.
	 * @param Name_ This is the user assigned name of the Field, which is used to generate column titles.
	 */
	public void Configure(String File, String Name_){
		this.FilePath = File;
		this.Name = Name_;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		conn = c;
		this.Generator = new CustomGenerator(conn, FilePath, unique);
		this.Generator.PrepareGenerator();
	}

	/**
	 * {@inheritDoc}
	 */
	public void Destroy() {
		 Generator.DestroyGenerator();	
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
		return (Generator.nextTuple() + ", ");
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
		return this.Name + ", ";
	}

	@Override
	public CompressedField getConfig() {
		return new CustomCompressedField(Name, activated, unique, FilePath);
	}
	
	
}
