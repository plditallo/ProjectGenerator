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

import javax.swing.JOptionPane;

import datagenerator.fields.CustomWieghtField;
import datagenerator.fields.compress.CustomWieghtCompressedField;


/**
 * This is the GUI class used to configure the CustomWieghtField Class.
 */
public class Config_CustomWieght extends javax.swing.JFrame implements java.awt.event.ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1; //Name
    private datagenerator.fields.CustomWieghtField Tuple;
    
    String File = null;
   
    /**
     * This is the default constructor for the class.
     * @param T This is a reference to the parent CustomWieghtField which this GUI is used to edit.
     */
    public Config_CustomWieght(CustomWieghtField T){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	jButton2.addActionListener(this);
    	this.setTitle("Wieghted Custom Data");
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    /**
     * This constructor is used for rebuilding saved Field Configurations by populating
     * it with data.
     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
     * @param Comp This is used for configuring the field.
     */
    public Config_CustomWieght(CustomWieghtField T, CustomWieghtCompressedField Comp){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	jButton2.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    	this.jTextField1.setText(Comp.getName());
    	this.File = Comp.getFilePath();
    }
    
    /**
     * This is a private method which builds the GUI Panel.
     */
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Wieghted Custom");

        jLabel1.setText("Name:");
        jLabel1.setName("jLabel1");

        jLabel2.setText("Data File:");
        jLabel2.setName("jLabel2");

        jButton1.setText("Browse:");
        jButton1.setName("jButton1");

        jButton2.setText("OK");
        jButton2.setName("jButton2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton2)))
                .addContainerGap(10, Short.MAX_VALUE))
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
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

	@Override
	/**
	 * This is the ActionListener method used to detect the OK button being clicked.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(jButton1)){
			File = datagenerator.util.Toolkit.readData();
			
		}else if(arg0.getSource().equals(jButton2)){
			if(File == null){
				JOptionPane.showMessageDialog(null, "You must select a file to add this Field.");
			}else{
				this.Tuple.Configure(File, this.jTextField1.getText());
				this.Tuple.setActivation();
				this.setVisible(false);
				this.Tuple.refreshGUI();	
			}
			
		}
		
	}
    
}
