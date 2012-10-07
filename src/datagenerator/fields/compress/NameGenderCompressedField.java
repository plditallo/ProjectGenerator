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
package datagenerator.fields.compress;

import datagenerator.gui.AddDialog;

/**
 * This Compressed Field is a datastructure used for storing configuration information of an
 * AutoIncField.
 * @author Patrick McMorran
 *
 */
public class NameGenderCompressedField implements CompressedField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private boolean activated;
	private boolean unique;
	
	private boolean[] ResultConfig; //This array Decides what output the Tuple Gives
	private int Gender;

	/**
	* This is the default constructor to create the compressed field.
	 * @param Name This is the name of the Field Class it represents.
	 * @param activated This is the boolean value of the activated field.
	 * @param unique This is the boolean value of the unique field.
	 * @param ResultConfig This boolean array configures the Field output.
	 * @param Gender This number represents the Field's gender selection.
	 */
	public NameGenderCompressedField(String Name, boolean activated, boolean unique, boolean[] ResultConfig, int Gender){
		this.Name = Name;
		this.activated = activated;
		this.unique = unique;
		this.ResultConfig = ResultConfig;
		this.Gender = Gender;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return AddDialog.NameGender;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean getActivated() {
		return activated;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getName() {
		return this.Name;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean getUnique() {
		return this.unique;
	}
	
	/**
	 * This method returns the boolean array that configures the field output.
	 * @return This boolean array configures the field output.
	 */
	public boolean[] getResultConfig(){
		return this.ResultConfig;
	}
	
	/**
	 * This method returns an integer that represents what gender the field has selected.
	 * @return This integer represents the field's gender selection.
	 */
	public int getGender(){
		return this.Gender;
	}
}
