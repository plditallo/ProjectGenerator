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
package datagenerator.util;

import javax.swing.JFileChooser;

import datagenerator.util.filters.CsvTextFilter;
import datagenerator.util.filters.DcfgFilter;
import datagenerator.util.filters.Utils;

/**
 * This class is used to provide a GUI interface for opening and saving files. 
 * @author Patrick McMorran
 *
 */
public class Toolkit {
	
	/**
	 * This method returns a string containing a file path using an open dialog.
	 * @return This String contains the file path.
	 */
	static public String readData(){
		String file = null;
		file = getfile();
		return file;
	}
	
	/**
	 * This method returns a string containing a file path using a save dialog.
	 * @return This String contains the file path.
	 */
	static public String saveData(){
		String file = null;
		file = savefile();
		return file;
	}
	
	/**
	 * This method is used to launch the GUI that fetches the file location from the user.
	 * @return This String contains the file location.
	 */
	static private String getfile(){
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileFilter(new CsvTextFilter());
		
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Open Data File");
		int rval = chooser.showOpenDialog(null);
		if( rval == JFileChooser.APPROVE_OPTION){
			String dataPath = chooser.getSelectedFile().getPath();
			return dataPath;
		}
		return null;
	}
	
	/**
	 * This method is used to launch the GUI that fetches the file location from the user.
	 * @return This String contains the file location.
	 */
	static private String savefile(){
		JFileChooser chooser = new JFileChooser();
		
	    chooser.setFileFilter(new CsvTextFilter());
	    
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Save Data File");
		int rval = chooser.showSaveDialog(null);
		if( rval == JFileChooser.APPROVE_OPTION){
			String dataPath = chooser.getSelectedFile().getPath();
			dataPath = Utils.setExtension(dataPath, "csv");
			return dataPath;
		}
		return null;
	}
	
	/**
	 * This method is used to launch the GUI that fetches the file location from the user.
	 * @return This String contains the file location.
	 */
	static public String getconfig(){
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileFilter(new DcfgFilter());
		
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setDialogTitle("Open Data File");
		int rval = chooser.showOpenDialog(null);
		if( rval == JFileChooser.APPROVE_OPTION){
			String dataPath = chooser.getSelectedFile().getPath();
			return dataPath;
		}
		return null;
	}
	
	/**
	 * This method is used to launch the GUI that fetches the file location from the user.
	 * @return This String contains the file location.
	 */
	static public String saveconfig(){
		JFileChooser chooser = new JFileChooser();
		
	    chooser.setFileFilter(new DcfgFilter());
	    
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setDialogTitle("Save Data File");
		int rval = chooser.showSaveDialog(null);
		if( rval == JFileChooser.APPROVE_OPTION){
			String dataPath = chooser.getSelectedFile().getPath();
			dataPath = Utils.setExtension(dataPath, "dcfg");
			return dataPath;
		}
		return null;
	}
}
	

