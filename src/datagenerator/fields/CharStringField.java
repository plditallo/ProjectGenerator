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

import datagenerator.fields.compress.CharStringCompressedField;
import datagenerator.fields.compress.CompressedField;
import datagenerator.generators.CharStringGenerator;

/**
 * The CharStringField is the Field object that is used by the MainGUI. The CharString field stands for
 * Character String Field. This is a Field Class that generates character strings that can contain 
 * upper/lowercase letters and numbers.
 * @author Patrick McMorran
 */
public class CharStringField implements Field{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name = ""; //This is the Name if the Tuple while the user is using the GUI.
							//This enables the user to tell two tuples of the same type apart.
	private Connection conn = null; //This is the connection to the database

	//Generator Parameters
	boolean[] Num_up_low;
	int length;
	
	CharStringGenerator Generator;
	
	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_CharString Config;

	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	boolean unique = false;
	
	/**
	 * This is the default constructor.
	 */
	public CharStringField(){
		Config = new datagenerator.fields.config.Config_CharString(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public CharStringField(CharStringCompressedField Comp){
		Config = new datagenerator.fields.config.Config_CharString(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.Num_up_low = Comp.getNumUpLow();
		this.length = Comp.getLength();
	}
	
	/**
	 * The Configure method is used as a set method to change the parameters of the field that the user defines.
	 * @param B This is a boolean array that contains three values representing the different character types the
	 * Character String Field Generates. Array[0] is Numerical (0-9), Array[1] is lowercase (a-z) and Array[2] is 
	 * uppercase (A-Z). A true value designates that the generator should use that value. 
	 * @param len This integer represents how many characters should be in the string.
	 * @param Name_ This is the user assigned name of the Field, which is used to generate column titles.
	 */
	public void Configure(boolean[] B, int len, String Name_){
		this.Num_up_low = B;
		this.length = len;
		this.Name = Name_;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		this.conn = c;
		 this.Generator = new CharStringGenerator(Num_up_low, length, unique);
		 if(conn == null){
	        	System.out.println("CONNECTION NULL");
	        }
		 this.Generator.prepareGenerator(this.conn);
	}

	/**
	 * {@inheritDoc}
	 */
	public void Destroy() {
		 this.Generator.destroyGenerator();	
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
	/**
	 * {@inheritDoc}
	 * This method returns the Field Name, 
	 */
	public CompressedField getConfig() {
		return new CharStringCompressedField(Name, activated, unique, Num_up_low, length);
	}
	
}
