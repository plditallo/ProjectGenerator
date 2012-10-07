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

import datagenerator.fields.compress.AutoIncCompressedField;
import datagenerator.fields.compress.CompressedField;
import datagenerator.fields.config.Config_AutoInc;
import datagenerator.generators.AutoIncGenerator;


/**
 * The AutoIncField is the Field object that is used by the MainGUI. The AutoInc field stands for
 * Auto Increment Field,which starts at a stated number and steps a defined value each time it is polled.
 * @author Patrick McMorran
 */
public class AutoIncField implements Field{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String Name = ""; 
	
	@SuppressWarnings("unused")
	private Connection conn = null; //This is the connection to the database

	//Generator Parameters
	private double StartingInt; //First Integer that The AutoInc Starts at
	private double Step_Amount;
	
	AutoIncGenerator Generator;
	
	datagenerator.gui.Field_GUI GUITUPLE; //This is the row that is in the main window
	datagenerator.fields.config.Config_AutoInc Config;
	
	//This is so the gui knows whether or not the tuple is actually being used.
	boolean activated = false;
	
	boolean unique = false;
	
	/**
	 * This is the Constructor for the AutoIncField
	 */
	public AutoIncField(){
		Config = new Config_AutoInc(this);
	}
	
	/**
	 * This is the constructor used to populate a previously created field.
	 * @param Comp This is the compressed field object used to populate the field.
	 */
	public AutoIncField(AutoIncCompressedField Comp){
		Config = new Config_AutoInc(this, Comp);
		this.activated = Comp.getActivated();
		this.unique = Comp.getUnique();
		this.Name = Comp.getName();
		this.StartingInt = Comp.getStart();
		this.Step_Amount = Comp.getStep();
	}
	
	/**
	 * The Configure method modifies the configuration settings of the Field.
	 * @param Start The double value of what number the field should start at.
	 * @param Step The double value of how large a step the field should take.
	 * @param N The Name of the Field.
	 */
	public void Configure(double Start, double Step, String N){
		this.StartingInt = Start;
		this.Step_Amount = Step;
		this.Name = N;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void Create(Connection c) {
		 this.Generator = new AutoIncGenerator(StartingInt, Step_Amount, unique);
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
		return activated;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setActivation() {
		activated = true;
	}

	@Override
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
	 */
	public CompressedField getConfig() {
		return new AutoIncCompressedField(this.Name, this.activated, this.unique, this.StartingInt, this.Step_Amount); 
	}
	
	
	
}
