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
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import datagenerator.loaders.LocationLoader;
import datagenerator.loaders.NameLoader;
import datagenerator.loaders.TupLoader;


//This Class Connects to the Derby database, and uses the generators
//to build the data file the user requests.
/**
 * This class is a GUI similar to that of the Generator class. This class uses different loaders to read CSV
 * data into the database, which is later used to generate datasets.
 * 
 * @author Patrick McMorran
 */
@SuppressWarnings({ "serial" })
public class Loader extends javax.swing.JFrame implements Runnable, ActionListener {
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
	private TupLoader Columns[];
	private MainGui parent;
	//Database Variables
	public static final String builtin_Prefix = "builtin_";
	public static final String data_Prefix = "generated_";
	public static final String temporary_Prefix = "temp_";
	public static final String framework = "embedded";
	public static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String protocol = "jdbc:derby:";
	
	Thread TT;
	/**
	 * This is the constructor for the Loader Class.
	 * @param Parent This is a reference the the configuration panel so that the user can return there on completion.
	 */
	public Loader(MainGui Parent){
		this.parent = Parent;
		loadDriver();
		System.out.println("Started Server/Driver");
		this.initComponents();
		this.setVisible(true);
		this.jButton1.addActionListener(this);
		
		// /* //This Retirects the console to the Textbox in the gui
		PrintStream con = new PrintStream(new datagenerator.gui.TextAreaOutputStream(jTextArea1));//Creates Class for console
		System.setOut(con);//Redirect Standard out
		System.setErr(con);//Redirect Standard error
		//*/
	
		TT = new Thread(this);
		TT.start();
	}
	
	
	
	
	/**
	 * This method creates a connection to the database, and then uses each loader to read data into the
	 * database for the generator.
	 */
	public void LoadData(){
		Connection conn = null;
		
		this.jTextField1.setText("Loading Data.");
		System.out.println("Connection Named");
		try{
			String dbName = "DataGeneratorDB";//Database Name this program uses for datageneration.
	        System.out.println("Database Name set");
	        
	        File DBDirectory = new File(dbName);
	        //tests if directory exists
	        if(DBDirectory.exists()){
	        	if(0==JOptionPane.showConfirmDialog(null, "Are you sure you would like delete the current database and reload the information?", "Are you sure you would like delete the current database and reload the information?", JOptionPane.YES_NO_OPTION)){
	        		deleteDir(DBDirectory);
		        	System.out.println("Database Deleted!");
	        	}else{
	        		complete = true;
	        		jButton1.setText("Complete");
	        		TT.interrupt();
	    			try {
	    				TT.join();
	    			} catch (InterruptedException e) {
	    				// TODO Auto-generated catch block
	    				//e.printStackTrace();
	    			}
	    			//System.exit(0);
	        	}
	        	
	        }
	        
	        //This creates the database connection for the program.
	        conn = DriverManager.getConnection(protocol + dbName + ";create=true");
	        System.out.println("Created new Database");
	        
	        
	        
	        ////////////////////////////////////////////////////////
	        /////////Database Started And Ready
	        ////////////////////////////////////////////////////////
	        //Currently only two types of tuples require database data to be preloaded.
	        
	        //Database Data to be loaded
	        Columns = new TupLoader[2];
	        Columns[0] = new LocationLoader(conn, this.progressBar);
	        Columns[1] = new NameLoader(conn, this.progressBar);
	        
	        //Buildup
	        this.progressBar.setMaximum(Columns.length);//Configure progress bar
	        //This loop prepares all of the DataGenerators
	        for(int i = 0; i<Columns.length && !cancel;i++){
	        	this.progressBar.setValue(i);
	        	Columns[i].Load();
	        }
	        
	        this.progressBar.setValue(Columns.length);
	        
	        System.out.println("Load Complete");
	        
	        
	        
	        
	        ////////////////////////////////////////////////////////
	        /////////End Database Workload
	        ////////////////////////////////////////////////////////
	        //Close Database when complete
	        shutdownDriver();
	        System.out.println("Shutdown the Database/Connection");
	       
	        //jButton1.setEnabled(true);
		}catch(Throwable r){
			r.printStackTrace();
		}
		jButton1.setText("Complete");
		complete = true;
	}
	
	//This Function loads the DerbyDriver(If needed) and starts the Derby Server.
	/**
	 * This function loads the driver used to connect to the derby database and starts the derby server.
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
	  * This method closes the connection to the database and shuts down the Derby Server.
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
	  * This method builds the GUI that shows the progress the loader is making while loading,
	  * and gives an interface for the user to interact with. 
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
	        //jButton1.setEnabled(false);
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
	 * This method is used to run the loader in a thread separate from main.
	 */
	public void run() {
		this.LoadData();
		return;
	}

	/**
	 * This is the method used for handling user interaction with the GUI.
	 */
	@Override
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
	
	/**
	 * This method is used to delete the old database data so that the new data can be loaded in it's place.
	 * It recursively calls itself to delete all the files and subfolders contained within.
	 * @param dir This is a File class which points to the directory that the database is stored in.
	 * @return This returns a boolean value noting if the delete is successful.
	 */
	public static boolean deleteDir(File dir) { 
		if (dir.isDirectory()) { 
			String[] children = dir.list(); 
			for (int i=0; i<children.length; i++) { 
				boolean success = deleteDir(new File(dir, children[i])); 
				if (!success) { 
					return false; 
					} 
				} 
			} // The directory is now empty so delete it 
		return dir.delete(); 
		}
	 
}
