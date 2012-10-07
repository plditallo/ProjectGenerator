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
package datagenerator.loaders;

/**
 * This interface is implemented by Loaders used by the Loader class.
 * @author Patrick McMorran
 *
 */
public interface TupLoader {
	/**
	 * This method is used to import the loaders data into the database, whose connection should be given
	 * using the constructor. This method creates connections to predefined CSV files and reads their data
	 * into the database.
	 */
	public void Load();
}
