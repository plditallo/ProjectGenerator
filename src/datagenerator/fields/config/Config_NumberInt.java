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

import datagenerator.fields.compress.NumberIntCompressedField;


/**
 * This is the GUI class used to configure the NumberIntField Class.
 */
public class Config_NumberInt extends javax.swing.JFrame implements java.awt.event.ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    
    private datagenerator.fields.NumberIntField Tuple;
    
    /**
     * This is the default constructor for the class.
     * @param T This is a reference to the parent NumberIntField which this GUI is used to edit.
     */
    public Config_NumberInt(datagenerator.fields.NumberIntField T){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	this.setTitle("Number :: Integer");
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    /**
     * This constructor is used for rebuilding saved Field Configurations by populating
     * it with data.
     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
     * @param Comp This is used for configuring the field.
     */
    public Config_NumberInt(datagenerator.fields.NumberIntField T, NumberIntCompressedField Comp){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    	this.jTextField1.setText(Comp.getName());
    	this.jTextField2.setText(new Integer(Comp.getMinimum()).toString());
    	this.jTextField3.setText(new Integer(Comp.getMaximum()).toString());
    }
    
    /**
     * This is a private method which builds the GUI Panel.
     */
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Integer"); 

        jLabel1.setText("Name:"); 
        

        jLabel2.setText("Minimum:"); 
       
       
        jLabel3.setText("Maximum:"); 
        
       
        jButton1.setText("OK"); 
        
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
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );

        pack();
    }



	@Override
	/**
	 * This is the ActionListener method used to detect the OK button being clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jButton1)){
		this.Tuple.Configure(Integer.parseInt(this.jTextField2.getText()), Integer.parseInt(this.jTextField3.getText()), this.jTextField1.getText());
		this.Tuple.setActivation();
		this.setVisible(false);
		this.Tuple.refreshGUI();
		}
		
	}
	
}
