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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import datagenerator.fields.Field;


//This Class Connects to the Derby database, and uses the generators
//to build the data file the user requests.
@SuppressWarnings({ "serial", "unused" })
	/**
	 * 
	 * The Generator Class is a GUI that manages, monitors and displays the progress of the data generation process.
	 * It Prepares the tuples and assembles the data, writing it line by line. This Class also creates and manages the
	 * connection to the local database that the different fields use for data generation. This class is the workhorse
	 * of the overall program. This is a runnable class, so this could be expanded to support threading and generation
	 * of multiple data sets at a time.
	 * @author Patrick McMorran
	 */
public class Generator extends javax.swing.JFrame implements Runnable, ActionListener {
	//GUI Vars
	private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JProgressBar progressBar;
    private boolean cancel = false;
    private boolean complete = false;
	
    private MainGui parent;
    
    Connection conn = null;
	//Database Variables
    
	public static final String builtin_Prefix = "builtin_";
	public static final String data_Prefix = "generated_";
	public static final String temporary_Prefix = "temp_";
	public static final String framework = "embedded";
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String protocol = "jdbc:derby:";
	public static boolean database_load = false;
	private Field[] Columns;
	private String OutputFilePath;
	int NumberofRecords; //Number of records user wants generated.
	
	Thread TT;
	/**
	 * This is the constructor method for the generator. It takes in a preconfigured array of Fields 
	 * and generates data according to their individual settings. 
	 * @param Col An array of Field objects collected by the MainGui
	 * @param output A String that contains the name of the file where the data will be saved.
	 * @param Records An integrity containing the total number of records the generator should create.
	 * @param Parent This is a reference to the configuration panel, so the user may return to it.
	 */
	public Generator(Field[] Col, String output, int Records, MainGui Parent){
		this.Columns = Col;
		this.OutputFilePath = output;
		this.NumberofRecords = Records;
		this.parent = Parent;
		///Optimizations
		Properties p = System.getProperties();
		p.put("derby.storage.pageCacheSize", "10000");
		p.put("derby.storage.pageSize", "32768");
		
		//Used for Tuning
		//System.setProperty("derby.language.logQueryPlan", "true");
		//////////////////////////////
		loadDriver();
		System.out.println("Started Server/Driver");
		this.initComponents();
		this.setVisible(true);
		this.jButton1.addActionListener(this);
		
		 //This Retirects the console to the Textbox in the gui
		PrintStream con = new PrintStream(new TextAreaOutputStream(jTextArea1));//Creates Class for console
		System.setOut(con);//Redirect Standard out
		System.setErr(con);//Redirect Standard error
		//*/
		TT = new Thread(this);
		TT.start();
	}
	
	/**
	 * The GenerateData Method Starts prepares the Fields and their respective generators, uses them to create
	 * the required data, and then cleans up the temporary space used. This method also updates the GUI with its
	 * progress so the end user can monitor the program.
	 */
	public void GenerateData(){
		Runtime R = Runtime.getRuntime();

		this.jTextField1.setText("Building Generators/Loading Data.");
		//System.out.println("Connection Named");
		try{
			
			////Property Verification
			Properties sprops = System.getProperties(); 
			System.out.println("derby.storage.pageCacheSize value: " + sprops.getProperty("derby.storage.pageCacheSize")); 
			System.out.println("derby.storage.pageSize value: " + sprops.getProperty("derby.storage.pageSize"));
			
			/////
			String dbName = "DataGeneratorDB";//Database Name this program uses for datageneration.
	        //System.out.println("Database Name set");
	        System.out.println("Preparing Generator:");
	        //This creates the database connection for the program.
	        conn = DriverManager.getConnection(protocol + dbName + ";create=true");
	        if(conn == null){
	        	System.out.println("CONNECTION NULL");
	        }
	       
	        ////////////////////////////////////////////////////////
	        /////////Database Started And Ready
	        ////////////////////////////////////////////////////////
	        
	        //Buildup
	        BufferedWriter CSVoutput = new BufferedWriter(new FileWriter(this.OutputFilePath));
	        //System.out.println("Created Writer for CSV output");
	        
	        this.progressBar.setMaximum(this.Columns.length);//Configure progress bar
	        //This loop prepares all of the DataGenerators
	        for(int i = 0; i<this.Columns.length && !cancel;i++){
	        	this.progressBar.setValue(i);
	        	Columns[i].Create(conn);
	        }
	        
	        //Run 
	        this.jTextField1.setText("Generating Data");
	        this.progressBar.setValue(0);
	        this.progressBar.setMaximum(this.NumberofRecords);
	        System.out.println("Data Generation Commencing");
	        
	        /////////////////////////////
	        //write titles
	        String title = "";
	        for(int i = 0; i<this.Columns.length; i++){
        		title+=(Columns[i].getTitles());
        	}
        	title.trim();
        	title = title.substring(0, title.length()-1);
        	CSVoutput.write(title);
        	CSVoutput.newLine();
        	
	        for(int j = 0; j < this.NumberofRecords && !cancel; j++){
	        	
	        	//System.out.println("Generating Record: " + j);
	        	String line = "";
	        	this.progressBar.setValue(j);
	        	for(int i = 0; i<this.Columns.length; i++){
	        		line+=(Columns[i].nextField());
	        	}
	        	line.trim();
	        	line = line.substring(0, line.length()-1);
	        	
	        	CSVoutput.write(line);
	        	CSVoutput.newLine();
	        	
	        	if(this.NumberofRecords%5==0){
	        	CSVoutput.flush();
	        	R.gc();
	        	}
	        }
	        CSVoutput.flush();
	        //Generation Complete
	      //  System.out.println("Record Generation Complete");
	        this.progressBar.setValue(this.NumberofRecords);
	        
	      //  this.jTextField1.setText("Cleaning Up");
	        //Breakdown
	        CleanupMess();
	        System.out.println("Cleaned Up Workspace");
	        
	        
	        
	        
	        ////////////////////////////////////////////////////////
	        /////////End Database Workload
	        ////////////////////////////////////////////////////////
	        //Close Database when complete
	        shutdownDriver();
	        //System.out.println("Shutdown the Database/Connection");
	        CSVoutput.close();
	        this.jButton1.setText("Complete");
		}catch(Throwable r){
			r.printStackTrace();
		}
		complete = true;
	}
	
	/**
	 * The CleanupMess method calls the Destroy methods of each Field, cleaning up the database of 
	 * all temporary data generated by each field.
	 */
	private void CleanupMess(){
		for(int i = 0; i < this.Columns.length; i++){
			this.Columns[i].Destroy();
		}
		
	}
	
	/**
	 * This Function loads the DerbyDriver(If needed) and starts the Derby Database Server.
	 */
	 private void loadDriver() {
		   
	     try {
	         Class.forName(driver).newInstance();
	         System.out.println("Loaded the appropriate driver");
	     } catch (ClassNotFoundException cnfe) {
	         System.err.println("\nUnable to load the JDBC driver " + driver);
	         System.err.println("Please check your CLASSPATH.");
	         cnfe.printStackTrace(System.err);
	     } catch (InstantiationException ie) {
	         System.err.println(
	                     "\nUnable to instantiate the JDBC driver " + driver);
	         ie.printStackTrace(System.err);
	     } catch (IllegalAccessException iae) {
	         System.err.println(
	                     "\nNot allowed to access the JDBC driver " + driver);
	         iae.printStackTrace(System.err);
	     }
	 }
	
	 /**
	  * The shutdownDriver method closes the connection to the database and shuts down the server.
	  */
	 private void shutdownDriver(){
         try
         {
             // the shutdown=true attribute shuts down Derby
             DriverManager.getConnection("jdbc:derby:;shutdown=true");

             // To shut down a specific database only, but keep the
             // engine running (for example for connecting to other
             // databases), specify a database in the connection URL:
             //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
         }
         catch (SQLException se)
         {
             if (( (se.getErrorCode() == 50000)
                     && ("XJ015".equals(se.getSQLState()) ))) {
                 // we got the expected exception
                 System.out.println("Derby shut down normally");
                 // Note that for single database shutdown, the expected
                 // SQL state is "08006", and the error code is 45000.
             } else {
                 // if the error code or SQLState is different, we have
                 // an unexpected exception (shutdown failed)
                 System.err.println("Derby did not shut down normally");
                 se.printStackTrace();
             }
         }
	 }
	 
	 /**
	  * The initCompenents method creates and initializes the GUI that the end user sees.
	  */
	 private void initComponents() {

	        jScrollPane1 = new javax.swing.JScrollPane();
	        jTextArea1 = new javax.swing.JTextArea();
	        progressBar = new javax.swing.JProgressBar();
	        jTextField1 = new javax.swing.JTextField();
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jButton1 = new javax.swing.JButton();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setName("Form"); // NOI18N

	        jScrollPane1.setName("jScrollPane1"); 

	        jTextArea1.setColumns(20);
	        jTextArea1.setEditable(false);
	        jTextArea1.setRows(5);
	        jTextArea1.setName("jTextArea1"); 
	        jScrollPane1.setViewportView(jTextArea1);

	        progressBar.setName("progressBar"); 

	        jTextField1.setEditable(false);
	        jTextField1.setText("Current Step"); 
	        jTextField1.setName("jTextField1"); 

	        jLabel1.setText("Current Progress:"); 
	        jLabel1.setName("jLabel1"); 

	        jLabel2.setFont(new java.awt.Font("Tahoma",java.awt.Font.PLAIN,36)); 
	        jLabel2.setText("Generating Data"); 
	        jLabel2.setName("jLabel2"); 

	        jButton1.setText("Cancel"); 
	        jButton1.setName("jButton1"); 

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
	                        .addContainerGap())
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addComponent(jLabel2)
	                        .addGap(138, 138, 138))
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGap(121, 121, 121)
	                        .addComponent(jLabel1)
	                        .addGap(41, 41, 41)
	                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
	                        .addComponent(jButton1)
	                        .addContainerGap())
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
	                        .addContainerGap())))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jLabel2)
	                        .addGap(8, 8, 8)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(jLabel1)))
	                    .addComponent(jButton1))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
	        );

	        pack();
	    }// </editor-fold>


	/**
	 * This method is used to run the generator in a separate thread from main.
	 */
	public void run() {
		this.GenerateData();
		return;
	}

	@Override
	/**
	 * This method is used to detect user interaction with the generator.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(this.jButton1)){
		if(complete){
			this.parent.setVisible(true);
			this.setVisible(false);
		}else{
			//this.cancel = true;
			TT.interrupt();
			try {
				TT.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			shutdownDriver();
	        System.out.println("Shutdown the Database/Connection");
		}
	}
		
	}
	 
}
