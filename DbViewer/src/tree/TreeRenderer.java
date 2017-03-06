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
 
 package tree;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.Table;

/**
 * This class represents custom implementation of cell renderer that creates 
 * tree representation of different classes from model.
 * 
 * @author Jelena 
 *
 */
public class TreeRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = 1L;
		/**
		 * Presents icon of closed tree node.
		 */
		private static final Icon packageIconClose = new ImageIcon("images/icons/packing.png");
		/**
		 * Presents icon of opened tree node.
		 */
		private static final Icon packageIconOpen = new ImageIcon("images/icons/unpacking.png");
	    /**
	     * Presents icon of tree node of type table.
	     */
		private static final Icon fileIcon = new ImageIcon("images/icons/contract.png");
/**
 * Returns custom tree cell renderer. 
 */
	    @Override
	    public Component getTreeCellRendererComponent(JTree tree, Object value,
	        boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
	    	 
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
	        Object o = node.getUserObject(); 
	        if(o instanceof model.Package || o instanceof String) {
	        	setLeafIcon(packageIconClose);
	        	setOpenIcon(packageIconOpen);
	        	setClosedIcon(packageIconClose);
	        }	
	        else if(o instanceof Table){
	        	Table t = (Table) o; 
	        	//setIcon(fileIcon);
	        	setLeafIcon(fileIcon);
	        	setOpenIcon(fileIcon);
	        	setClosedIcon(fileIcon);
	        	//setIcon(fileIcon); 
	        } 
	        
	        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
	        
	        return this;
	    }
}
