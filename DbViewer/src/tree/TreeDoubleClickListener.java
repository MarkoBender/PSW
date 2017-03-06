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

import gui.MainFrame;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener for the {@link SchemaTree}.
 * 
 * @author Viktor
 *
 */
public class TreeDoubleClickListener extends MouseAdapter {
	
	/**
	 * Instance of {@link SchemaTree} to which listener is connected to.
	 */
	private SchemaTree tree; 
	
	/**
	 * Default constructor.
	 * 
	 * @param tree
	 */
    public TreeDoubleClickListener(SchemaTree tree) {
        this.tree = tree;
    }
    
    /**
     * Sets the action when the mouse is clicked.
     */
    public void mouseClicked(MouseEvent e) { 
    	
    	switch(e.getClickCount()){
    		/*case(1):
    		{
    			tree.expandPath(tree.getSelectionPath());
    		} break;
    		*/
    		case(2):
    		{
    			if(tree.getLastSelectedPathComponent() instanceof TableNode){
        			TableNode node = (TableNode)tree.getLastSelectedPathComponent();
        			if (node == null) return;
        			
        			MainFrame.getInstance().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        			
        			MainFrame.getInstance().getMainContentPane().add(node);
        			
        			MainFrame.getInstance().setCursor(Cursor.getDefaultCursor());
        		}
    		} break;
    	}

    }
}
