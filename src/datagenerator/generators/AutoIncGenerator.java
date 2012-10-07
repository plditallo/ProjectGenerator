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
package datagenerator.generators;

import java.io.Serializable;
import java.sql.PreparedStatement;

/**
 * This Class is a generator that is used for creating an auto-increment field.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("unused")
public class AutoIncGenerator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double StartingInt; //First Integer that The AutoInc Starts at
	private double Step_Amount;
	private boolean unique;
    private String temp_table;
    static int generatorID = 999999;
    private PreparedStatement psInsert = null;
	
    /**
     * This is the constructior for the generator. 
     * @param Start The double value of what number the field should start at.
	 * @param Step The double value of how large a step the field should take.
	 * @param u The Name of the Field.
     */
	public AutoIncGenerator(double Start, double Step, boolean u){
		this.StartingInt = Start;
		this.Step_Amount = Step;
		this.unique = u;
		this.temp_table = "temp_autoinc_" + generatorID;
		generatorID--;
	}
	
	/**
	 * This method is used to retrieve the next number in the auto-increment series.
	 * @return This double is the next number in the auto-increment series.
	 */
	public double nextTuple(){
		double temp = StartingInt;
		StartingInt = StartingInt + Step_Amount;
		return temp;
	}
	
}
