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
package datagenerator.util.filters;

import java.io.File;

/**
 * This Filter is used by the file chooser to only display folders, csv and txt files,
 * which are the file formats used to save and load data sets.
 * @author Patrick McMorran
 */
public class CsvTextFilter extends javax.swing.filechooser.FileFilter {

	/**
	 * This method takes in a file, and returns a boolean value denoting if the file
	 * met the filter criteria.
	 * @param f This is the file which need to be checked against the filter.
	 * @return This boolean value returns true if the file meets filter requirements, otherwise false.
	 */
	public boolean accept(File f) {
		 if (f.isDirectory()) {
				return true;
			    }

			    String extension = Utils.getExtension(f);
			    if (extension != null) {
				if (extension.equalsIgnoreCase("csv") || extension.equalsIgnoreCase("txt")){
				        return true;
				} else {
				    return false;
				}
			    }

			    return false;
	}

	@Override
	public String getDescription() {
		return "csv and txt files";
	}

}
