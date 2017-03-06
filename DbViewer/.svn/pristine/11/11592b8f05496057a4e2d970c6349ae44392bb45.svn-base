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

import application.Application;
import tree.SchemaTree;

/**
 * 
 * An Action that extends {@link AbstractAction}
 * when invoked, if there is a tree node selected,
 * moves the selection up.
 * 
 * @author Marko
 * 
 * @see AbstractAction
 */
public class TreeMoveUpAction extends AbstractAction {


	/**
	 * {@link TreeMoveUpAction} public constructor
	 * Initializes the object with a localised name
	 * and a small icon.
	 */
	public TreeMoveUpAction() {
		putValue(SMALL_ICON, new ImageIcon("images/icons/treeUp.png"));
		putValue(SHORT_DESCRIPTION, Application.getResourceBundle().getString("TreeUp"));
	}
	
	/**
	 * Gets {@link ShemaTree} and if a node is selected,
	 * moves the selection one node up.
	 * @param arg0
	 * 
	 * @see ShemaTree
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SchemaTree tree = SchemaTree.getInstance();
		int[] selectedRows = tree.getSelectionRows();
		if(selectedRows.length != 0){
			int selectedRow = selectedRows[0];
			if(selectedRow != 0){
				tree.setSelectionInterval(selectedRow-1, selectedRow-1);
			}
		} 
	}
}
