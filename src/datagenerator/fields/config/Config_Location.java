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

import datagenerator.fields.compress.LocationCompressedField;

/**
 * This is the GUI class used to configure the LocationField Class.
 */
public class Config_Location extends javax.swing.JFrame implements java.awt.event.ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JCheckBox jCheckBox1;//zip
    private javax.swing.JCheckBox jCheckBox2;//state
    private javax.swing.JCheckBox jCheckBox3;//city
    private javax.swing.JCheckBox jCheckBox4;//phone
    private javax.swing.JCheckBox jCheckBox5;//street
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1; //states
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;//name
    private javax.swing.JButton JButton1;
    private datagenerator.fields.LocationField Tuple;
    
    private static final String[] ALLSTATES = { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
    
    /**
     * This is the default constructor for the class.
     * @param T This is a reference to the parent LocationField which this GUI is used to edit.
     */
    public Config_Location(datagenerator.fields.LocationField T){
    	Tuple = T;
    	initComponents();
    	JButton1.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
    
    /**
     * This constructor is used for rebuilding saved Field Configurations by populating
     * it with data.
     * @param T This is a reference to the parent AutoIncField which this GUI is used to edit.
     * @param Comp This is used for configuring the field.
     */
    public Config_Location(datagenerator.fields.LocationField T, LocationCompressedField Comp){
    	Tuple = T;
    	initComponents();
    	JButton1.addActionListener(this);
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    	boolean[] bool = Comp.getResultConfig();
    	//this.testout(bool);
    	this.jCheckBox1.setSelected(bool[0]);
    	this.jCheckBox2.setSelected(bool[1]);
    	this.jCheckBox3.setSelected(bool[2]);
    	this.jCheckBox4.setSelected(bool[3]);
    	this.jCheckBox5.setSelected(bool[6]);
    	this.jTextField1.setText(Comp.getName());
    	String[] states = Comp.getStates();
    	int[] indices = new int[states.length];
    	//This maps all the state indices.
    	for(int i = 0; i < states.length; i++){
    		this.jList1.setSelectedValue(states[i], false);
    		indices[i] = this.jList1.getSelectedIndex();
    	}
    	this.jList1.setSelectedIndices(indices); //This selects all the states.
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
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        JButton1 = new javax.swing.JButton();
        jCheckBox5 = new javax.swing.JCheckBox();

        this.setTitle("Location Data");
         

        jLabel1.setText("Name:");       

        jLabel2.setText("Data Types:"); 
        

        jCheckBox1.setText("Zipcode"); 

        jCheckBox2.setText("State"); 

        jCheckBox3.setText("City"); 

        jCheckBox4.setText("Phone"); 

        jLabel3.setText("State Selection:"); 


        jList1.setModel(new javax.swing.AbstractListModel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        
        jList1.setVisibleRowCount(13);
        jScrollPane1.setViewportView(jList1);

        JButton1.setText("OK"); 
        
        jCheckBox5.setText("Street Address");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBox1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCheckBox5))
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(JButton1)))
                .addContainerGap())
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
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4)
                    .addComponent(jCheckBox5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JButton1))
        );

        pack();
    }


	@Override
	/**
	 * This is the ActionListener method used to detect the OK button being clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		//JOptionPane.showMessageDialog(null, "Event Fired");
		if(e.getSource().equals(JButton1)){
			//JOptionPane.showMessageDialog(null, "Button Identified");
			//If OK was hit, lets return all the info
			boolean[] B = new boolean[7];
			B[0] = this.jCheckBox1.isSelected();
			B[1] = this.jCheckBox2.isSelected();
			B[2] = this.jCheckBox3.isSelected();
			B[3] = B[4] = B[5] = this.jCheckBox4.isSelected();
			B[6] = this.jCheckBox5.isSelected();
			//this.testout(B);
			//If nothing was selected, select all fields
			if(!this.jCheckBox1.isSelected()&&!this.jCheckBox2.isSelected()&&!this.jCheckBox3.isSelected()&&!this.jCheckBox4.isSelected()&&!this.jCheckBox5.isSelected()){
				B[0]=B[1]=B[2]=B[3]=B[4]=B[5]=B[6]=true;
			}
			//this.testout(B);
			Object[] O = this.jList1.getSelectedValues();
			String[] States;
			//If no States are selected, select all states
			if(O.length < 1){
				States = ALLSTATES;
			}else{
				States = new String[O.length];
				for(int i = 0; i<O.length; i++){
					States[i] = (String) O[i];
				}
			}
			this.Tuple.Configure(States, B, this.jTextField1.getText());
			this.Tuple.setActivation();
			this.setVisible(false);
			this.Tuple.refreshGUI();
		}
		
	}
	@SuppressWarnings("unused")
	private void testout(boolean[] bool){
		String T = "";
		for(int i = 0; i<bool.length; i++){
			T += "Array[" + i + "] = " + booltest(bool[i]) + " / ";
		}
		JOptionPane.showMessageDialog(null, T);
	}
	
	private String booltest(boolean B){
		if(B){
			return "true";
		}else{
			return "false";
		}
	}
    
    
}
