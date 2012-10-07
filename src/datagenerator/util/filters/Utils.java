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

public class Utils {

	    /**
	     * This method is used to find the file extension
	     * @param f This is the file who's extension is unknown.
	     * @return This String contains the extension of the file.
	     */  
	    public static String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
	    
	    /**
	     * This method ensures that the file has an extension, and if not adds it.
	     * @param f This is the String containing the file path.
	     * @param ex This is the String containing the extension.
	     * @return This is the new String file path with the correct extension.
	     */
	    public static String setExtension(String f, String ex) {
	        String ext = f;
	        int i = f.lastIndexOf('.');

	        if (i > 0 &&  i < f.length() - 1) {
	            ext = f.substring(0, i);
	            ext += "." + ex;
	        }else{
	        	if(i>0){
	        		ext += ex;
	        	}else{
	        		ext += "." + ex;
	        	}
	        		
	        }
	        return ext;
	    }
	
}
