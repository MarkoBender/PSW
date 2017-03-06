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
 
 package action.tree;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import tree.SchemaTree;
import application.Application;

/**
 * 
 * An Action that extends {@link AbstractAction}
 * when invoked,expands all tree nodes.
 * 
 * @author Marko
 * 
 * @see AbstractAction
 */
public class ExpandTreeAction extends AbstractAction {

	/**
	 * {@link ExpandTreeAction} public constructor
	 * Initializes the object with a localised name
	 * localised description
	 * and a small icon.
	 */
	public ExpandTreeAction() {
		putValue(NAME, Application.getResourceBundle().getString("ExpandTree"));
		putValue(SHORT_DESCRIPTION, Application.getResourceBundle().getString("ExpandTreeDesc"));
		putValue(SMALL_ICON, new ImageIcon("images/icons/expand.png"));
	}
	
	/**
	 * Gets {@link ShemaTree} and expands all its nodes.
	 * @param e
	 * 
	 * @see ShemaTree
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SchemaTree tree = SchemaTree.getInstance();
		int numberOfRows = tree.getRowCount();
		for(int i = 0; i < numberOfRows; i++){
			tree.expandRow(i);
			numberOfRows = tree.getRowCount();
		}
	}
}
