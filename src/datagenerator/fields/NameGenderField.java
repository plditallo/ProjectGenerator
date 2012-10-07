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
import datagenerator.fields.compress.NameGenderCompressedField;
import datagenerator.generators.NameGenderGenerator;

/**
 * The NameGenderField is the Field object that is used by the MainGUI. The NameGender field stands for
 * Name and Gender Data Field. This is a Field Class that generates randomly selected first/last names and their
 * corresponding gender, or of a specific gender.
 * @author Patrick McMorran
 */
public class NameGenderField implements Field{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name = ""; //This is the Name if the Tuple while the user is using the GUI.
							//This enables the user to tell two tuples of the same type apart.
	private Connection conn = null; //This is the connection to the database
	private boolean[] ResultConfig = null; //This array Decides what output the Tuple Gives
	private int Gender = 2; //This Decides the gender output of the Generator
	private NameGenderGenerator Name_Generator = null; //The Location Generator
	
	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_NameGender Config;
	
	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	
	boolean unique = false;
	
	/**
	 * This is the default constructor.
	 */
	public NameGenderField(){
		Config = new datagenerator.fields.config.Config_NameGender(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public NameGenderField(NameGenderCompressedField Comp){
		Config = new datagenerator.fields.config.Config_NameGender(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.Gender = Comp.getGender();
		this.ResultConfig = Comp.getResultConfig();
	}
	
	/**
	 * The Configure method is used as a set method to change the parameters of the field that the user defines.
	 * @param G This Number represents which gender the generator should output. Male = 0, Female = 1, Both = 2.
	 * @param b This boolean array designates what data the generator should output.
	 * Array[0] = First Name
	 * Array[1] = Last Name
	 * Array[2] = Gender
	 * @param s This is the user assigned name of the Field, which is used to generate column titles.
	 */
	public void Configure(int G, boolean[] b, String s){
		this.ResultConfig = b;
		this.Gender = G;
		this.Name = s;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		this.conn = c;
		Name_Generator = new NameGenderGenerator(this.conn, this.Gender, unique, this.ResultConfig);
	    Name_Generator.PrepareGenerator();
	}

	/**
	 * {@inheritDoc}
	 */
	public void Destroy() {
		Name_Generator.DisassembleGenerator();	
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
		String[] Random_Data = Name_Generator.nextTuple();
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
		String[] columns = {"First", "Last", "Gender"};
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
		return new NameGenderCompressedField(Name, activated, unique, ResultConfig, Gender);
	}
	
}
