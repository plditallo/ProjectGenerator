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
import datagenerator.fields.compress.NumberIntCompressedField;
import datagenerator.generators.NumberIntGenerator;

/**
 * The NumberIntField is the Field object that is used by the MainGUI. The IntDouble field stands for
 * Integer Type Number Data Field. This is a Field Class that generates random integers within a range supplied
 * by the end user.
 * @author Patrick McMorran
 */
public class NumberIntField implements Field{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name = ""; //This is the Name if the Tuple while the user is using the GUI.
							//This enables the user to tell two tuples of the same type apart.
	private Connection conn = null; //This is the connection to the database

	//Generator Parameters
	private int Minimum;
	private int Maximum;
	
	NumberIntGenerator Generator;

	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_NumberInt Config;
	
	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	
	boolean unique = false;
	
	/**
	 * This is the default constructor.
	 */
	public NumberIntField(){
		Config = new datagenerator.fields.config.Config_NumberInt(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public NumberIntField(NumberIntCompressedField Comp){
		Config = new datagenerator.fields.config.Config_NumberInt(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.Minimum = Comp.getMinimum();
		this.Maximum = Comp.getMaximum();
	}
	
	/**
	 * The Configure method is used as a set method to change the parameters of the field that the user defines.
	 * @param min This is an integer representing the lower bound of the number range that can be generated.
	 * @param max This is an integer representing the upper bound of the number range that can be generated.
	 * @param Name_ This is the user assigned name of the Field, which is used to generate column titles.
	 */
	public void Configure(int min, int max, String Name_){
		this.Minimum = min;
		this.Maximum = max;
		this.Name = Name_;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		conn = c;
		this.Generator = new NumberIntGenerator(Minimum, Maximum, unique);
		this.Generator.prepareGenerator(conn);
	}

	/**
	 * {@inheritDoc}
	 */
	public void Destroy() {
		 //Nothing to Destroy	
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
		return new NumberIntCompressedField(Name, activated, unique, Minimum, Maximum);
	}
	
	
}
