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

import javax.swing.JLabel;

/**
 * The Field_GUI Class is the a Panel Class which is used to display the different field selected by the end user.
 * @author Patrick McMorran
 *
 */
@SuppressWarnings("unused")
public class Field_GUI extends javax.swing.JPanel implements java.awt.event.ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox checkbox1;
    //private javax.swing.JLabel jLabel2;
    
    private datagenerator.fields.Field Tuple_;
    Object[] O;
    javax.swing.JPanel ParentPanel;
    
    /**
     * This is the default constructor.
     * @param T This Field Class is the Field the GUI will represent.
     * @param P This is a reference to the parent panel of the MainGui class.
     */
    public Field_GUI(datagenerator.fields.Field T, javax.swing.JPanel P){
    	Tuple_ = T;
    	ParentPanel = P;
    	initComponents();
    	checkbox1.setSelected(Tuple_.getConfig().getUnique());
    	checkbox1.addActionListener(this);
    	Tuple_.addGUI(this);
    }
    
   
    /**
     * This method updates the Field pane to display the current data.
     */
    public void RefreshPanel(){
    	jLabel1.setText(Tuple_.getName());
    	ParentPanel.revalidate();
    }
    
    /**
     * This method returns a true value if the Field is valid.
     * @return This boolean value defines if the Field is valid.
     */
    public boolean Validation(){
    	if(Tuple_.getName() == null || Tuple_.getName().equals("")){
    		return false;
    	}else{
    		return true;
    	}
    }
    
    /**
     * This method returns the field class that the gui interacts with.
     * @return This field is the field this gui represents.
     */
    public datagenerator.fields.Field getField(){
    	return Tuple_;
    }
    
    /**
     * This method is used to set an action listener for the GUI's Remove Button.
     * @param A This is a reference to the Actionlistener that has access to the MainGui.
     */
    public void setRemoval(ActionListener A){
    	jButton1.addActionListener(A);
    }
    
    /**
     * This method returns a reference to the remove button that the Field_GUI uses.
     * @return This JButton is the remove button of the Field_GUI.
     */
    public javax.swing.JButton GetRemove(){
    	return jButton1;
    }
    
    /**
     * This method constructs and initializes the GUI.
     */
	 private void initComponents(){
	        jLabel1 = new javax.swing.JLabel(Tuple_.getName());
	        jButton1 = new javax.swing.JButton();
	        jButton2 = new javax.swing.JButton();
	        checkbox1 = new javax.swing.JCheckBox("Unique");
	       // jLabel2 = new JLabel();
	        
	        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); 
	        //jLabel1.setText(Tuple_.getName()); 
	        jLabel1.setName("jLabel1");

	        jButton1.setText("Remove");
	        jButton1.setName("jButton1");
	        //jButton1.addActionListener(ParentPanel);

	        jButton2.setText("Edit"); 
	        jButton2.setName("jButton2"); 
	        jButton2.addActionListener(this);
	        
	        //jLabel2.setText("Unique");
	        //checkbox1.setName("Unique");
	        
	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	        this.setLayout(layout);
	        layout.setHorizontalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    //.addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addComponent(jButton2)
	                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                    .addComponent(jButton1))
	            );
	            layout.setVerticalGroup(
	                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                .addGroup(layout.createSequentialGroup()
	                    //.addContainerGap()
	                    //.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jButton1)
	                            .addComponent(checkbox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            //.addComponent(jLabel2)
	                            .addComponent(jLabel1)
	                            .addComponent(jButton2)))
	                    //.addContainerGap())
	            );
	    }
	
	 /**
	  * This method opens the edit pane for the Field_GUI.
	  */
	public void edit(){
		Tuple_.Edit();
	}
	
	/**
	 * This method returns a boolean value that denotes if the unique checkbox has been checked.
	 * @return This boolean denotes of the unique checkbox has been checked.
	 */
	public boolean getUnique(){
		return checkbox1.isSelected();
	}
	
	/**
	 * This is an Actionlistener method, used to detect if the edit button has been selected.
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(jButton2)){
			Tuple_.Edit();
		}else if(e.getSource().equals(checkbox1)){
			Tuple_.setUnique(checkbox1.isSelected());
		}
	}
}
