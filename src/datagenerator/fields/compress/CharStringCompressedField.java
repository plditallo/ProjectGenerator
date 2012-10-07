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
public class CharStringCompressedField implements CompressedField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private boolean activated;
	private boolean unique;
	
	private boolean[] Num_up_low;
	private int length;
	
	
	/**
	 * This is the default constructor to create the compressed field.
	 * @param Name This is the name of the Field Class it represents.
	 * @param activated This is the boolean value of the activated field.
	 * @param unique This is the boolean value of the unique field.
	 * @param Num_up_low This is the boolean array that configures the data types.
	 * @param length This integer denotes how many characters are in the string.
	 */
	public CharStringCompressedField(String Name, boolean activated, boolean unique, boolean[] Num_up_low, int length){
		this.Name = Name;
		this.activated = activated;
		this.unique = unique;
		this.Num_up_low = Num_up_low;
		this.length = length;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return AddDialog.CharString;
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
	 * This method returns a boolean array containing the boolean configuration of character types.
	 * @return This boolean array contains the character type configuration.
	 */
	public boolean[] getNumUpLow(){
		return this.Num_up_low;
	}
	
	/**
	 * This method returns the number of characters the string should be.
	 * @return This integer represents the string length.
	 */
	public int getLength(){
		return this.length;
	}

}
