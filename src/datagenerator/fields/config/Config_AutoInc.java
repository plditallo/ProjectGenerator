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
package datagenerator.fields.config;

import java.awt.event.ActionEvent;

import datagenerator.fields.AutoIncField;
import datagenerator.fields.compress.AutoIncCompressedField;



/**
 * This is the GUI class used to configure the AutoIncField Class.
 */
public class Config_AutoInc extends javax.swing.JFrame implements java.awt.event.ActionListener{
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//GUI Variables
		private javax.swing.JButton jButton1;
	    private javax.swing.JLabel jLabel1;
	    private javax.swing.JLabel jLabel2;
	    private javax.swing.JLabel jLabel3;
	    private javax.swing.JTextField jTextField1;//Column Name
	    private javax.swing.JTextField jTextField2;//Starting Number
	    private javax.swing.JTextField jTextField3;//Increment amount
	    private AutoIncField Tuple;
	    
	    /**
	     * This is the default constructor for the class.
	     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
	     */
	    public Config_AutoInc(AutoIncField T){
	    	Tuple = T;
	    	initComponents();
	    	jButton1.addActionListener(this);
	    	this.setTitle("Auto Increment");
	    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
	    }
	    
	    /**
	     * This constructor is used for rebuilding saved Field Configurations by populating
	     * it with data.
	     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
	     * @param Comp This is used for configuring the field.
	     */
	    public Config_AutoInc(AutoIncField T, AutoIncCompressedField Comp){
	    	Tuple = T;
	    	initComponents();
	    	jButton1.addActionListener(this);
	    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
	    	jTextField1.setText(Comp.getName());
	    	jTextField2.setText(new Double(Comp.getStart()).toString());
	    	jTextField3.setText(new Double(Comp.getStep()).toString());
	    }
	    /**
	     * This is a private method which builds the GUI Panel.
	     */
	    private void initComponents() {

	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        jLabel3 = new javax.swing.JLabel();
	        jTextField1 = new javax.swing.JTextField();
	        jTextField2 = new javax.swing.JTextField();
	        jTextField3 = new javax.swing.JTextField();
	        jButton1 = new javax.swing.JButton();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setName("AutoInc"); 

	        jLabel1.setText("Column Name:"); 
	        jLabel1.setName("jLabel1"); 

	        jLabel2.setText("Starting Number:"); 
	        jLabel2.setName("jLabel2"); 

	        jLabel3.setText("Increment Amount"); 
	        jLabel3.setName("jLabel3"); 

	        jButton1.setText("Save"); 
	        jButton1.setName("jButton1"); 

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(layout.createSequentialGroup()
	                        .addContainerGap()
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(jLabel3)
	                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                                .addComponent(jTextField3))
	                            .addGroup(layout.createSequentialGroup()
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                    .addComponent(jLabel2)
	                                    .addComponent(jLabel1))
	                                .addGap(18, 18, 18)
	                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
	                                    .addComponent(jTextField2)))))
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(72, 72, 72)
	                        .addComponent(jButton1)))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel3)
	                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addGap(18, 18, 18)
	                .addComponent(jButton1)
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );

	        pack();
	    }

		@Override
		/**
		 * This is the ActionListener method used to detect the OK button being clicked.
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(jButton1)){
				this.Tuple.Configure(Double.parseDouble(this.jTextField2.getText()), Double.parseDouble(this.jTextField3.getText()), this.jTextField1.getText());
				this.Tuple.setActivation();
				this.setVisible(false);
				this.Tuple.refreshGUI();
			}
			
		}
	    
}
