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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import datagenerator.fields.AutoIncField;
import datagenerator.fields.CharStringField;
import datagenerator.fields.CustomField;
import datagenerator.fields.CustomWieghtField;
import datagenerator.fields.Field;
import datagenerator.fields.LocationField;
import datagenerator.fields.NameGenderField;
import datagenerator.fields.NumberDoubleField;
import datagenerator.fields.NumberIntField;
import datagenerator.fields.compress.AutoIncCompressedField;
import datagenerator.fields.compress.CharStringCompressedField;
import datagenerator.fields.compress.CompressedField;
import datagenerator.fields.compress.CustomCompressedField;
import datagenerator.fields.compress.CustomWieghtCompressedField;
import datagenerator.fields.compress.LocationCompressedField;
import datagenerator.fields.compress.NameGenderCompressedField;
import datagenerator.fields.compress.NumberDoubleCompressedField;
import datagenerator.fields.compress.NumberIntCompressedField;
import datagenerator.gui.AddDialog;

/**
 * This class exists for the purpose of reading and writing configuration files by taking in
 * arrays of fields and extracting the information into save files.
 * @author Patrick McMorran
 *
 */
public class FieldFactory {

	static public void save(CompressedField[] fields){
		String Save = null;
    	if((Save = Toolkit.saveconfig())!=null){
    		try{
    			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Save));
    			for(int i = 0; i<fields.length; i++){
    				oos.writeObject(fields[i]);
    			}
    			oos.flush();
    			oos.close();
    		}catch(Throwable R){
    			JOptionPane.showMessageDialog(null, "There was an error saving!");
    			R.printStackTrace();
    		}
    	}
	}
	
	static public ArrayList<Field> load(){
		//Field[] F = null;
		ArrayList<Field> Fields = new ArrayList<Field>();
		ArrayList<CompressedField> F = new ArrayList<CompressedField>();
		String Load = null;
    	if((Load = Toolkit.getconfig())!=null){
    		
    		try{
    			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Load));
    			try{
    				CompressedField Temp;
    				while((Temp = (CompressedField) ois.readObject())!= null){
    				F.add(Temp);
    				}
    			}catch(Throwable R){
    				//Working, end of file?
    				//R.printStackTrace();
    			}
    			ois.close();
    		}catch(Throwable r){
    			JOptionPane.showMessageDialog(null, "There was an error loading the file!");
    			r.printStackTrace();
    		}
    	}
		for(int i = 0; i<F.size(); i++){
			if(F.get(i).getType() == AddDialog.AutoInc){
				Fields.add(new AutoIncField((AutoIncCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.CharString){
				Fields.add(new CharStringField((CharStringCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.Custom){
				Fields.add(new CustomField((CustomCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.CustomWeighted){
				Fields.add(new CustomWieghtField((CustomWieghtCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.Location){
				Fields.add(new LocationField((LocationCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.NameGender){
				Fields.add(new NameGenderField((NameGenderCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.NumberDouble){
				Fields.add(new NumberDoubleField((NumberDoubleCompressedField)F.get(i)));
			}else if(F.get(i).getType() == AddDialog.NumberInteger){
				Fields.add(new NumberIntField((NumberIntCompressedField)F.get(i)));
			}else{
				//Invalid Field, toss to side.
			}
		}
		return Fields;
	}
}
