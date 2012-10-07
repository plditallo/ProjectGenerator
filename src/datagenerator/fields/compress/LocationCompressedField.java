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
public class LocationCompressedField implements CompressedField{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Name;
	private boolean activated;
	private boolean unique;
	
	private boolean[] ResultConfig = null;
	private String[] States = null; 

	
	/**
	 * This is the default constructor to create the compressed field.
	 * @param Name This is the name of the Field Class it represents.
	 * @param activated This is the boolean value of the activated field.
	 * @param unique This is the boolean value of the unique field.
	 * @param ResultConfig This is the boolean array configuration of the field output.
	 * @param States This is an array containing all a list of selected states.
	 */
	public LocationCompressedField(String Name, boolean activated, boolean unique, boolean[] ResultConfig, String States[]){
		this.Name = Name;
		this.activated = activated;
		this.unique = unique;
		this.ResultConfig = ResultConfig;
		this.States = States;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getType() {
		return AddDialog.Location;
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
	 * This method returns an array of Strings containing the selected states.
	 * @return This array is composed of Strings with the names of states.
	 */
	public String[] getStates(){
		return this.States;
	}
	
	/**
	 * This method returns a boolean array that configures how output should be displayed.
	 * @return This boolean array contains the ResultConfig Field.
	 */
	public boolean[] getResultConfig(){
		return this.ResultConfig;
	}

}
