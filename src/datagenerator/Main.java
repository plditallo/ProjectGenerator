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
package datagenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import datagenerator.gui.MainGui;

/**
 * The Main class contains the main method of the data generator and launches the initial splash screen
 * that greets the user, asking them if they wish to load new data into the database or to generate new
 * data sets. 
 * @deprecated This class is no longer used in favor of an improved UI using the Main GUI Class.
 * @author Patrick McMorran
 */
@SuppressWarnings("serial")
public class Main extends javax.swing.JFrame implements ActionListener {

	private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    
    /**
     *  The Main method creates the GUI that prompts the end user for interaction.
     */
    public Main() {
        initComponents();
        this.setVisible(true);
    }

    /**
     * InitComponents builds the JFrame window and pane that pop up to greet the user.
     */
    private void initComponents() {
    	
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); 

        jButton1.setText("Reload Database"); 
        jButton1.setName("jButton1"); 
        jButton1.addActionListener(this);
        
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24));
        jLabel1.setText("DataGen Admin Utility!"); 
        jLabel1.setName("jLabel1"); 

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18));
        jButton4.setText("Launch DataGenerator!"); 
        jButton4.setName("jButton4"); 
        jButton4.addActionListener(this);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(113, 113, 113))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new Main().setVisible(true); //This Splash Screen was scrapped in favor of an improved menu system.
            	new datagenerator.gui.MainGui();
            }
        });
    }

	/**
	 * @param arg0 This method handles all events thrown by the GUI the Main class creates.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(jButton1)){//Load
			//new datagenerator.gui.Loader();
			this.setVisible(false);
		}else if(arg0.getSource().equals(jButton4)){//Launch
			new MainGui().setVisible(true);
			this.setVisible(false);
		}
	}
	 
	

   
}
