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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to parse and read CSV files, similar to the buffered reader class.
 * @author Patrick McMorran
 *
 */
public class CSVReader {
	
	BufferedReader bfile;
	int currentline;
	int linecount;
	
	/**
	 * This constructor builds the class using a String Object containing the file path.
	 * @param filepath This string should contain the file path of the CSV file.
	 */
	public CSVReader(String filepath){
		try {
			this.linecount = this.LineCount(new BufferedReader(new FileReader(filepath)));
			this.bfile = new BufferedReader(new FileReader(filepath));
			this.currentline = 1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method is used to determine the length of the file being read.
	 * @param temp This is a buffered reader connected to the file in question.
	 * @return This integer is the number of lines in the file.
	 */
	private int LineCount(BufferedReader temp) {
	   int i = 0;
	   try{
	   while(temp.readLine()!=null){
		   i++;
	   }
	   temp.close();
	   }catch(Throwable R){
		   
	   }
	   return i;
	}

	/**
	 * This method returns the number of lines in the file.
	 * @return This integer is the number of lines in the file;
	 */
	public int length(){
		return this.linecount;
	}
	
	/**
	 * This method returns the number of the current line the reader is pointing at.
	 * @return The current line number.
	 */
	public int position(){
		return this.currentline;
	}
	//This Function Reads a line of the CSV file
	//Returns null if the 
	/**
	 * This method reads and parses the next line of the CSV file, returning an array of strings. Each
	 * string contains a value from the CSV file, which are organized in the order of reading. So
	 * Array[0] is column 1, Array[1] is column 2, ect. This method returns null of the end of file has
	 * been reached.
	 * @return This String array contains 1 line of values from the CSV. Array[0] is column 1, Array[1] is column 2, ect.
	 */
	public String[] getTuple(){
		String line;
		try{
		line = bfile.readLine();
		}catch(Throwable r){
			return null;
		}
		if(line == null){
			this.currentline++;
			try {
				bfile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Closing the CSV Failed :(");
				e.printStackTrace();
			}
			return null;
		}else{
		int i = CountColumns(line);
		return ParseTuple(line, i);
		}
	}
	
	//This breaks each line of the csv into an array of strings
	/**
	 * This method is used to parse the line from the csv file into an array of strings.
	 * @param line This is a string containing the next read from a line of the csv file.
	 * @param columns This is an integer that contains the number of columns contained within the csv file.
	 * @return This is an array of Strings containing the values of each  
	 */
	private String[] ParseTuple(String line, int columns){
		String[] Tuple = new String[columns];
		int beginning = 0, end = 0, ends = 0; 
		for(int i = 0, j = end; i<Tuple.length; i++){//This finds n-1 columns
			j = end;
			while(j<line.length()&&line.charAt(j)!=','){
				
				if(line.charAt(j)=='"'){//This block accounts for string values
					j++;//Shift away from quote
					beginning = j;
					while(line.charAt(j)!='"'){//If inside a string, ignore
					j++;//Find quote	
					}
					ends = j;
					Tuple[i]=line.substring(beginning, ends);
					j++;//Shift away from quote
				}else{//This accounts for numeric values
					j++;
				}
			}
			end = j;
			if(Tuple[i]==null){
				if(line.charAt(beginning)== '"'){
					beginning++;
				}
				if(i>0){
					beginning++;
				}
				Tuple[i]=line.substring(beginning, end); 
			}
			beginning = end++;
		}
		return Tuple;
	}
	//This counts the number of columns that the csv file has
	/**
	 * This method Counts the number of columns in the line of the CSV file to then parse it.
	 * @param Tuple This is the a string containing the line who's columns require counting.
	 */
	private int CountColumns(String Tuple){
		int len = Tuple.length();
		int columns = 1;
		for(int i = 0; i<len; i++){
			if(Tuple.charAt(i)==','){//if there is a comma space, then 1 data type is found.
				columns++;
			}else if(Tuple.charAt(i)=='"'){
				i++;//Shift away from quote
				while(Tuple.charAt(i)!='"'){//If inside a string, ignore
				i++;//Find quote	
				}
				//i++;//Shift away from quote
			}
		}
	return columns;	
	}
	
}
