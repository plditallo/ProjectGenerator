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
public class NumberIntCompressedField implements CompressedField{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private boolean activated;
	private boolean unique;
	
	int Minimum;
	int Maximum;
	
	public NumberIntCompressedField(String Name, boolean activated, boolean unique, int Minimum, int Maximum){
		this.Name = Name;
		this.activated = activated;
		this.unique = unique;
		this.Minimum = Minimum;
		this.Maximum = Maximum;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return AddDialog.NumberInteger;
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
	 * This method returns the maximum value of the Field.
	 * @return This integer represents the maximum value of the range.
	 */
	public int getMaximum(){
		return this.Maximum;
	}
	
	/**
	 * This method returns the minimum value of the Field.
	 * @return This integer represents the minimum value of the range.
	 */
	public int getMinimum(){
		return this.Minimum;
	}
}
