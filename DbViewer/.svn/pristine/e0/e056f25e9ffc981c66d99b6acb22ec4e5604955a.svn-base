/*
 * ************************************************************************************************************************************************
 * 
 *     PSW - DBViewer
 * __________________
 * The MIT License (MIT)
 * Copyright (c) 2016 Jelena Jankovic RA 139/2013, Nikola Kukavica RA 98/2013, Viktor Sanca RA 1/2013, Marko Bender 213/2012
 * 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT  
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR  
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 *************************************************************************************************************************************************/
 
 package sql;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

/**
 * Represents the logical operators which are put between instances of {@link AdvancedSearchComponent} in advanced search.
 * 
 * @author Viktor
 *
 */
public class AdvancedSearchLogicalOperator extends JPanel{
	private static final long serialVersionUID = 5262999327126449475L;
	
	/**
	 * available logical operators
	 */
	private static String[] logicalOperators = {"AND","OR","NOT","(",")","ANY","NONE"};
	/**
	 * combo box for displaying and picking the operator
	 */
	private JComboBox<String> operator;
	/**
	 * parent panel of the component
	 */
	private JPanel parent;
	
	/**
	 * Constructor which creates an instance of {@link AdvancedSearchLogicalOperator} and places it into the {@link JPanel} provided.
	 * 
	 * @param parent Panel in which the {@link AdvancedSearchLogicalOperator} instance will be placed in
	 */
	public AdvancedSearchLogicalOperator(JPanel parent) {
		this.parent = parent;
		
		setPreferredSize(new Dimension(100, 30));
		setMaximumSize(new Dimension(100, 30));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		operator = new JComboBox<>();
		
		// populating the combo box with the possible operations
		for(int i=0; i<logicalOperators.length; i++) {
			operator.addItem(logicalOperators[i]);
		}
		
		add(operator);
		
		JButton remove = new JButton(new ImageIcon("images/icons/remove_record.png"));
		remove.setPreferredSize(new Dimension(15, 15));
		remove.setMaximumSize(new Dimension(15, 15));
		remove.setOpaque(false);
		remove.setContentAreaFilled(false);
		remove.setBorderPainted(false);
		
		Component tmp = this;
		
		// adding the button for removing the logical condition
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.remove(tmp);	
				parent.repaint();
				parent.doLayout();
			}
		});
		
		add(remove);
	}
	
	/**
	 * Returns {@link JComboBox} selected item.
	 * @return string
	 */
	public String getSelectedOperator(){
		return (String) operator.getSelectedItem();
	}
}
