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

import datagenerator.fields.*;
import datagenerator.gui.Generator;


@SuppressWarnings("unused")
public class GeneratorTestCase {

	/**
	 * This class was rendered obsolete by the implementation of the save/load configuration feature
	 * @deprecated This class Creates a test case for the Generator to then run.
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Create a tuple of each
		AutoIncField AutoInc = new AutoIncField();
		CharStringField CharString = new CharStringField();
		CustomField Custom = new CustomField();
		CustomWieghtField CustomWieght = new CustomWieghtField();
		LocationField Location = new LocationField();
		NameGenderField NameGender = new NameGenderField();
		NumberDoubleField DoubleGenerator = new NumberDoubleField();
		NumberIntField IntGenerator = new NumberIntField();
		System.out.println("Created the Tuples");
		
		Field[] Tuples = new Field[8];
		Tuples[0] = AutoInc;
		Tuples[1] = CharString;
		Tuples[2] = Custom;
		Tuples[3] = CustomWieght;
		Tuples[4] = Location;
		Tuples[5] = NameGender;
		Tuples[6] = DoubleGenerator;
		Tuples[7] = IntGenerator;
		System.out.println("Created the Tuples Array");
		
		//Configure Auto Inc
		AutoInc.Configure(0, 1.5, "AutoInc");
		AutoInc.setUnique(true);
		System.out.println("Configured AutoInc");
		
		//Configure Char String
		boolean [] charConfig = new boolean [3];
		java.util.Arrays.fill(charConfig,true);
		CharString.Configure(charConfig, 15, "Character Gen");
		CharString.setUnique(true);
		System.out.println("Configured CharString");
		
		//Configure Custom
		Custom.Configure("Data/Custom.txt", "Custom Male");
		Custom.setUnique(true);
		System.out.println("Configured Custom Male");
		
		//Configure Custom Weighted
		CustomWieght.Configure("Data/CustomW.txt", "CustomWieghted Male");
		CustomWieght.setUnique(true);
		System.out.println("Configured Custom Male Wieghted");
		
		//Configure Location
		boolean [] locConfig = new boolean [7];
		java.util.Arrays.fill(locConfig,true);
		String[] states = {"Connecticut", "Texas"};
		Location.Configure(states, locConfig, "Location Generator");
		Location.setUnique(true);
		System.out.println("Configured Location");
		
		//Configure NameGender
		boolean [] nameConfig = new boolean [3];
		java.util.Arrays.fill(nameConfig,true);
		NameGender.Configure(2, nameConfig, "Name Generator");
		NameGender.setUnique(true);
		System.out.println("Configured Name Generator");
		
		//Configure Double Generator
		DoubleGenerator.Configure(2.2, 5.8, "Double Generator");
		DoubleGenerator.setUnique(true);
		System.out.println("Configured Double")
		;
		//Configure Integer Generator
		IntGenerator.Configure(1, 17, "Integer Generator");
		IntGenerator.setUnique(true);
		System.out.println("Configured Integer");
		
		//Create the Generator
		//new Generator(Tuples, "Test_Output3.csv", 4);
		System.out.println("Created Test Generator");
		
		//GenTest.GenerateData();
		/*
		//Prepare the Generator Thread
		Thread Generation = new Thread(GenTest);
		System.out.println("Configured Created Generation Thread");
		
		//Start the Generator Thread
		Generation.run();
		System.out.println("Started Thread");
		
		try {
			Generation.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}
