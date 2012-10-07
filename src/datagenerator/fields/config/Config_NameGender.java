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

import datagenerator.fields.compress.NameGenderCompressedField;
import datagenerator.generators.NameGenderGenerator;

/**
 * This is the GUI class used to configure the NameGenderField Class.
 */
public class Config_NameGender extends javax.swing.JFrame implements java.awt.event.ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;//First
    private javax.swing.JCheckBox jCheckBox2;//Last
    private javax.swing.JCheckBox jCheckBox3;//Gender
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JRadioButton jRadioButton1;//Male
    private javax.swing.JRadioButton jRadioButton2;//Female
    private javax.swing.JRadioButton jRadioButton3;//Both
    private javax.swing.JTextField jTextField1;//Name
    private javax.swing.ButtonGroup Group;
    
    private datagenerator.fields.NameGenderField Tuple;
    
    /**
     * This is the default constructor for the class.
     * @param T This is a reference to the parent NameGenderField which this GUI is used to edit.
     */
    public Config_NameGender(datagenerator.fields.NameGenderField T){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	this.setTitle("Name-Gender");
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    /**
     * This constructor is used for rebuilding saved Field Configurations by populating
     * it with data.
     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
     * @param Comp This is used for configuring the field.
     */
    public Config_NameGender(datagenerator.fields.NameGenderField T, NameGenderCompressedField Comp){
    	Tuple = T;
    	initComponents();
    	jButton1.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    	boolean[] bool = Comp.getResultConfig();
    	this.jCheckBox1.setSelected(bool[0]);
    	this.jCheckBox2.setSelected(bool[1]);
    	this.jCheckBox3.setSelected(bool[2]);
    	this.jTextField1.setText(Comp.getName());
    	int gender = Comp.getGender();
    	if(gender == NameGenderGenerator.MALE){
    		this.jRadioButton1.setSelected(true);
    	}else if(gender == NameGenderGenerator.FEMALE){
    		this.jRadioButton2.setSelected(true);
    	}else if(gender == NameGenderGenerator.BOTH){
    		this.jRadioButton3.setSelected(true);
    	}
    }
    
    /**
     * This is a private method which builds the GUI Panel.
     */
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        Group = new javax.swing.ButtonGroup();
        Group.add(jRadioButton1);
        Group.add(jRadioButton2);
        Group.add(jRadioButton3);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Name Gender");

        jLabel1.setText("Name:");


        jLabel2.setText("Results:");
        

        jCheckBox1.setText("First Name");
       
        jCheckBox2.setText("Last Name");
        
        jCheckBox3.setText("Gender");
       
        jLabel3.setText("Gender Types:");

        jRadioButton1.setText("Male");

        jRadioButton2.setText("Female");

        jRadioButton3.setText("Both");

        jButton1.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton3)))))
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
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3))
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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.jButton1)){
			//OK button clicked
			boolean[] B = new boolean[3];
			B[0] = this.jCheckBox1.isSelected();
			B[1] = this.jCheckBox2.isSelected();
			B[2] = this.jCheckBox3.isSelected();
			//If none selected, select all
			if(B[0]==B[1]==B[2]==false){
				B[0]=B[1]=B[2]=true;
			}
			int gender;
			if(this.jRadioButton1.isSelected()){
				gender = 0;
			}else if(this.jRadioButton2.isSelected()){
				gender = 1;
			}else{
				gender = 2;
			}
			
			this.Tuple.Configure(gender, B, this.jTextField1.getText());
			this.Tuple.setActivation();
			this.setVisible(false);
			this.Tuple.refreshGUI();
		
		}
		
	}
}
