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
package datagenerator.gui;

import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import datagenerator.fields.*;
import datagenerator.fields.compress.CompressedField;
import datagenerator.util.FieldFactory;
import datagenerator.util.Toolkit;

/*
 * In the Generator, Add the quick index lookup table for speedy comparisons on wieghted data.
 */
/**
 * This class is the GUI used to configure the settings which will be given to the data generator for 
 * generation.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("serial")
public class MainGui extends javax.swing.JFrame implements ActionListener{
	
	//Main GUI Pieces
	private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Menu fileMenu; //File
    private java.awt.Menu editMenu;
    private java.awt.Menu databaseMenu;
    private java.awt.MenuBar menuBar1;
    private MenuItem newConfig;
    private MenuItem saveConfig;
    private MenuItem loadConfig;
    private MenuItem Exit;
    private MenuItem addField;
    private MenuItem GenerateData;
    private MenuItem ReloadData;
    private AddDialog ADD;
    
    private javax.swing.JPanel Panel;
    //List of the Tuples
    ArrayList<Field_GUI> fields;
    javax.swing.table.DefaultTableModel Model;
    GridLayout Layout;
    
    /**
     * This is the default constructor.
     */
    public MainGui(){
    	initComponents();
    	ADD = new AddDialog(this);
    	ADD.setVisible(false);
    	this.setVisible(true);
    	fields = new ArrayList<Field_GUI>();
    }
    
   /**
    * This method constructs and builds the GUI that interacts with the user.
    */
    private void initComponents() {
    	
    	this.setTitle("Data Generator");
        menuBar1 = new java.awt.MenuBar();
        fileMenu = new java.awt.Menu("File");
        editMenu = new java.awt.Menu("Edit");
        databaseMenu = new java.awt.Menu("Database");
        jScrollPane1 = new javax.swing.JScrollPane();
        
        
        this.setMenuBar(menuBar1);
         
        menuBar1.add(fileMenu);
        newConfig = new MenuItem("New Configuration");
        saveConfig = new MenuItem("Save Configuration");
        loadConfig = new MenuItem("Load Configuration");
        Exit = new MenuItem("Exit");
        
        menuBar1.add(editMenu);
        addField = new MenuItem("Add Field");
        
        menuBar1.add(databaseMenu);
        GenerateData = new MenuItem("Generate Dataset");
        ReloadData = new MenuItem("(Re)Load Database");
        
        
        fileMenu.add(newConfig);
        fileMenu.add(loadConfig);
        fileMenu.add(saveConfig);
        fileMenu.add(Exit);
        editMenu.add(addField);
        databaseMenu.add(GenerateData);
        databaseMenu.add(ReloadData);
        
        newConfig.addActionListener(this);
        loadConfig.addActionListener(this);
        saveConfig.addActionListener(this);
        Exit.addActionListener(this);
        addField.addActionListener(this);
        GenerateData.addActionListener(this);
        ReloadData.addActionListener(this);
        
        Panel = new javax.swing.JPanel();
            
       
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); 
        
        jScrollPane1.setViewportView(Panel);
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );
       
        
        pack();
    }
    
    /**
     * This method is called to delete the current config loaded and start from scratch.
     */
    private void newConfig(){
    	while(!fields.isEmpty()){
    		Panel.remove(fields.get(0));
    		fields.remove(0);
    	}
    	this.refresher();
    }
    
    /**
     * This method is used to serialize and write the current configuration data to a file for later use.
     */
    private void saveConfig(){
    	CompressedField[] Cfields = new CompressedField[fields.size()];
    	for(int i = 0; i<fields.size(); i++){
    		Cfields[i] = fields.get(i).getField().getConfig();
    	}
    	FieldFactory.save(Cfields);
    	this.refresher();
    }
    
    /**
     * This method is used to unserialize and read previous configuration data from a file.
     */
    private void loadConfig(){
    	ArrayList<Field> Fields = FieldFactory.load();
    	if(Fields.size()>0){
    		this.newConfig();
    		for(int i = 0; i<Fields.size(); i++){
    			Field_GUI temp = new Field_GUI(Fields.get(i), Panel);
    			
    			fields.add(temp);
    			Panel.add(temp);
    		}
    	}
    	this.refresher();
    }

   /**
    * This method is used to handling different action events created by the end user on several GUIs.
    */
	public void actionPerformed(ActionEvent evt) {
		this.Prune();
        //New Tuple (Launches the Add Dialog)
		if(evt.getSource().equals(this.newConfig)){//This creates a new configuration screen
			newConfig();
		}else if(evt.getSource().equals(this.loadConfig)){//This opens a dialog to load a configuration from a file.
			loadConfig();
		}else if(evt.getSource().equals(this.saveConfig)){//This opens a dialog to save a configuration to file.
			saveConfig();
		}else if(evt.getSource().equals(addField)){
        	ADD.setVisible(true);
        }else if(evt.getSource().equals(GenerateData)){//Starts the Data Generator
        	if(0==JOptionPane.showConfirmDialog(null, "Are you sure you would like to generate the data?", "Are you sure you would like to generate the data?", JOptionPane.YES_NO_OPTION)){
        		generateData();
        	}
        }else if(evt.getSource().equals(this.ReloadData)){//Calls the Loader to reload the internal database data
        	new Loader(this);
        	this.setVisible(false);
        }else if(evt.getSource().equals(Exit)){//Exits Program
        	System.exit(0);
        }else if(evt.getSource().equals(ADD.getAdd())){//Adds the new Tuple to the Gui
        	ADD.setVisible(false);
        	addTuple(ADD.getType());
        }else if(evt.getSource().equals(ADD.getCan())){//Cancel on the add
        	ADD.setVisible(false);
        }else{
        	//javax.swing.JOptionPane.showMessageDialog(null,"Calling Remove Tuple");
        	removeTuple(evt.getSource());
        }
		
	}
	//This function scrolls through the arraylist, finds the Tuple to be removed, and
	//performs the operation
	/**
	 * This Method removes one of the previously added Fields and updates the GUI.
	 */
	private void removeTuple(Object O){
		for(int i = 0; i < fields.size(); i++){
			if(fields.get(i).GetRemove().equals(O)){
				Panel.remove(fields.get(i));
				fields.remove(i);
				break;
			}
		}
		///*
		//The update of the gui has be be called after the method completes.
		//So I use the Invoke Later method to start a thread after this method completes
		//to refresh the GUI.
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				Panel.revalidate();
				Panel.repaint();
			}});
			//*/
		
	}
	//This takes the arraylist, prepares an array, and feeds it into the generator.
	/**
	 * This method prepares the settings defined by the user and feeds it into the generator,
	 * starting the generation process.
	 */
	private void refresher(){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				Panel.revalidate();
				Panel.repaint();
			}});
	}
	private void generateData(){
		datagenerator.fields.Field[] Tuples_ = new Field[fields.size()];
		for(int i = 0; i<fields.size(); i++){
			Tuples_[i] = fields.get(i).getField();
			//Tuples_[i].setUnique(fields.get(i).getUnique());
		}
		setVisible(false);
		String Save;
		if((Save = Toolkit.saveData())!=null){
			int Record;
			if((Record = Integer.parseInt(JOptionPane.showInputDialog("Please Enter How many Records you would like."))) > 0){
				new datagenerator.gui.Generator(Tuples_, Save, Record, this);
			}else{
				this.setVisible(true);//User Aborted
			}
		}else{
			this.setVisible(true);//User Aborted
		}
	}
	//This adds a new tuple for the user and opens it's configuration
	/**
	 * This method adds a new Field and opens it's edit pane for the user to configure.
	 */
	private void addTuple(int Type){
		if(Type == AddDialog.AutoInc){
			Field_GUI Tup = new Field_GUI(new AutoIncField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.CharString){
			Field_GUI Tup = new Field_GUI(new CharStringField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.Custom){
			Field_GUI Tup = new Field_GUI(new CustomField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.CustomWeighted){
			Field_GUI Tup = new Field_GUI(new CustomWieghtField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.Location){
			Field_GUI Tup = new Field_GUI(new LocationField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.NameGender){
			Field_GUI Tup = new Field_GUI(new NameGenderField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.NumberDouble){
			Field_GUI Tup = new Field_GUI(new NumberDoubleField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else if(Type == AddDialog.NumberInteger){
			Field_GUI Tup = new Field_GUI(new NumberIntField(), Panel);
			Tup.setRemoval(this);
			fields.add(Tup);
			Panel.add(Tup);
			Tup.edit();
		}else{
			//INVALID, Do NADA
		}
	}
	
	//Goes through and removes all unactivated tuples
	/**
	 * This cleans up any unactivated fields from the field list, removing those that have not been configured
	 * by the user, or add was canceled.
	 */
	public void Prune(){
		for(int i = 0; i < fields.size(); i++){
			if(!fields.get(i).getField().getActivation()){
				Panel.remove(fields.get(i));
				fields.remove(i);
				i = -1;
			}
		}
	}
    
}
