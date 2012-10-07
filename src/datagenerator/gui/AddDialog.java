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

/**
 * This is the Dialog box that is used to select the different field types the user would like to add
 * to the data set they are going to generate.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("serial")
public class AddDialog extends javax.swing.JFrame {

	//Add Dialog
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.ButtonGroup Radialgroup;
    private java.awt.event.ActionListener Action;
    
    //Static Type Variables
    public static final int AutoInc = 0;
    public static final int CharString = 1;
    public static final int Custom = 2;
    public static final int CustomWeighted = 3;
    public static final int Location = 4;
    public static final int NameGender = 5;
    public static final int NumberDouble = 6;
    public static final int NumberInteger = 7;
    
    /**
     * This is the Constructor method.
     * @param A This is the action listener of the MainGui which allows this dialog to interact with
     * the rest of the program.
     */
    public AddDialog(java.awt.event.ActionListener A){
    	this.Action = A;
    	AddDialogInit();
    }
    //Returns Add button
    /**
     * This method returns a reference to the add button.
     * @return A JButton that is used for add.
     */
    public javax.swing.JButton getAdd(){
    	return jButton1;
    }
    //Return Cancel button
    /**
     * This method returns a reference to the cancel button.
     * @return A JButton that is used for cancel.
     */
    public javax.swing.JButton getCan(){
    	return jButton2;
    }
    
    /**
     * This method returns an integer that designates the type of Field to be added.
     * @return This integer represents the kind of Field being added.
     */
    public int getType(){
    	if(jRadioButton1.isSelected()){
    		return AutoInc;
    	}else if(jRadioButton2.isSelected()){
    		return CharString;
    	}else if(jRadioButton3.isSelected()){
    		return Custom;
    	}else if(jRadioButton4.isSelected()){
    		return CustomWeighted;
    	}else if(jRadioButton5.isSelected()){
    		return Location;
    	}else if(jRadioButton6.isSelected()){
    		return NameGender;
    	}else if(jRadioButton7.isSelected()){
    		return NumberDouble;
    	}else if(jRadioButton8.isSelected()){
    		return NumberInteger;
    	}else{
    		return -1;//Error, Somehow invalid button selected.
    	}
    }
    
    /**
     * This is the default constructor.
     */
    public AddDialog(){
    	AddDialogInit();
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
    }
	
	/**
	 * This method constructs the add dialog.
	 */
	private void AddDialogInit(){
		jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        
        jButton1.addActionListener(Action);
        jButton2.addActionListener(Action);
        
        setName("Add Field"); 
        
        Radialgroup = new javax.swing.ButtonGroup();
        Radialgroup.add(jRadioButton1);
        Radialgroup.add(jRadioButton2);
        Radialgroup.add(jRadioButton3);
        Radialgroup.add(jRadioButton4);
        Radialgroup.add(jRadioButton5);
        Radialgroup.add(jRadioButton6);
        Radialgroup.add(jRadioButton7);
        Radialgroup.add(jRadioButton8);
        
        jRadioButton1.setText("Auto Increment"); 

        jRadioButton2.setText("Character String"); 

        jLabel1.setFont(new java.awt.Font("Tahoma",java.awt.Font.PLAIN,24)); 
        jLabel1.setText("Add Field"); 
        jLabel1.setName("jLabel1"); 

        jRadioButton3.setText("Custom Data"); 

        jRadioButton4.setText("Wieghted Custom Data");

        jRadioButton5.setText("Location"); 

        jRadioButton6.setText("Name-Gender"); 

        jRadioButton7.setText("Double"); 

        jRadioButton8.setText("Integer"); 

        jButton1.setText("Add"); 

        jButton2.setText("Cancel"); 

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(10, 10, 10)
                        .addComponent(jButton2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadioButton8)
                        .addComponent(jRadioButton7)
                        .addComponent(jRadioButton6)
                        .addComponent(jRadioButton5)
                        .addComponent(jRadioButton4)
                        .addComponent(jRadioButton3)
                        .addComponent(jRadioButton2)
                        .addComponent(jRadioButton1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addGap(22, 22, 22)))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addGap(20, 20, 20))
        );
      
        pack();
	}
	
}
