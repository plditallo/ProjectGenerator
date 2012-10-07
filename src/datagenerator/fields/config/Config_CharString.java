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
import java.awt.event.ActionListener;

import datagenerator.fields.*;
import datagenerator.fields.compress.CharStringCompressedField;



/**
 * This is the GUI class used to configure the CharStringField Class.
 */
public class Config_CharString extends javax.swing.JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;//Numbers
    private javax.swing.JCheckBox jCheckBox2;//Upper
    private javax.swing.JCheckBox jCheckBox3;//Lower
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;//Name
    private javax.swing.JTextField jTextField2;//Length
    
    private CharStringField Tuple;
    
    /**
     * This is the default constructor for the class.
     * @param T This is a reference to the parent CharStringField which this GUI is used to edit.
     */
    public Config_CharString(CharStringField T){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	this.setTitle("Character String");
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    /**
     * This constructor is used for rebuilding saved Field Configurations by populating
     * it with data.
     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
     * @param Comp This is used for configuring the field.
     */
    public Config_CharString(CharStringField T, CharStringCompressedField Comp){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    	boolean[] bool = Comp.getNumUpLow();
    	this.jCheckBox1.setSelected(bool[0]);
    	this.jCheckBox2.setSelected(bool[1]);
    	this.jCheckBox3.setSelected(bool[2]);
    	this.jTextField1.setText(Comp.getName());
    	this.jTextField2.setText(new Integer(Comp.getLength()).toString());
    }
    
    /**
     * This is a private method which builds the GUI Panel.
     */
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("CharString"); 

        jLabel1.setText("Name:");  

        jLabel2.setText("Length:");


        jButton1.setText("Ok"); 

        jCheckBox1.setText("Numbers"); 

        jCheckBox2.setText("Uppercase"); 

        jCheckBox3.setText("Lowercase"); 

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                        .addComponent(jCheckBox2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jCheckBox3)))
                .addContainerGap())
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
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }    
    

	@Override
	/**
	 * This is the ActionListener method used to detect the OK button being clicked.
	 */
	public void actionPerformed(ActionEvent arg0) {
		//If ok button pressed
		if(arg0.getSource().equals(jButton1)){
			boolean[] B = new boolean[3];
			B[0] = jCheckBox1.isSelected();
			B[1] = jCheckBox1.isSelected();
			B[2] = jCheckBox1.isSelected();
			if(!B[0]&&!B[1]&&!B[2]){
				B[0] = B[1] = B[2] = true;
			}
			this.Tuple.Configure(B, Integer.parseInt(this.jTextField2.getText()), this.jTextField1.getText());
			this.Tuple.setActivation();
			this.setVisible(false);
			this.Tuple.refreshGUI();
		}
	}
	
}
