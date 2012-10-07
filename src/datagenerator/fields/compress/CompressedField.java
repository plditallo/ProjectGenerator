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

import java.io.Serializable;

/**
 * This class is designed as an extremely light data structure to save configuration data.
 * @author Patrick McMorran
 *
 */
public interface CompressedField extends Serializable{

		/**
		 * This method returns an int which defines what kind of field this compressed field corresponds to.
		 * @return This integer corresponds to a public static named integer in the AddDialog.
		 */
		public int getType();
		
		/**
		 * This method returns a String object containing the assigned name of the Field.
		 * @return This String contains the name of the Field.
		 */
		public String getName();
		
		/**
		 * This method returns a boolean denoting of the field was activated before saving
		 * @return This boolean denotes if the field is activated.
		 */
		public boolean getActivated();
		
		/**
		 * This method returns a boolean denoting if the Field is unique
		 * @return This boolean denotes if the Field is Unique.
		 */
		public boolean getUnique();
}
