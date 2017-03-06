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
 
 package gui.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import action.tab.CloseTabAction;

/**
 * Specialized {@link JPanel} class for enabling the closing button on visual representation of a single tab.
 * 
 * @author Viktor
 *
 */
public class ClosableTabComponent extends JPanel{
	/**
	 * {@link SchemaTab} reference.
	 */
	private SchemaTab tab;
	/**
	 * Name to display.
	 */
	private String displayName;
	/**
	 * Close button.
	 */
	private JButton close;
	/**
	 * Label containing the name
	 */
	private JLabel name;
	
	/**
	 * Constructs a {@link ClosableTabComponent} with reference to specific {@link SchemaTab} in order to get neccessary 
	 * information for displaying and closing the tab.
	 * 
	 * @param tab {@link SchemaTab} to which this tab header will be linked to
	 */
	public ClosableTabComponent(SchemaTab tab){
		this.displayName = tab.getName();
		this.setOpaque(false);
		this.tab = tab;
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		
		close = new JButton(new CloseTabAction(tab));
		close.setAlignmentX(RIGHT_ALIGNMENT);
		close.setOpaque(false);
		close.setContentAreaFilled(false);
		close.setBorderPainted(true);
		close.setPreferredSize(new Dimension(10, 10));

		
		name = new JLabel(displayName);
		name.setOpaque(false);

		
		this.add(name);
		this.add(close);
		
	}
	
	/**
	 * Returns the {@link SchemaTab} object.
	 * @return tab
	 */

	public SchemaTab getTab() {
		return tab;
	}

	/**
	 * Sets the {@link SchemaTab} object.
	 * @param tab
	 */
	public void setTab(SchemaTab tab) {
		this.tab = tab;
	}

	/**
	 * Returns displayed name.
	 * @return displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	
	/**
	 * Sets displayed name.
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	/**
	 * Updates the text on the label which represents the name of the tab component.
	 */
	public void updateTabText(String text) {
		setDisplayName(text);
		name.setText(text);
	}
	
}
