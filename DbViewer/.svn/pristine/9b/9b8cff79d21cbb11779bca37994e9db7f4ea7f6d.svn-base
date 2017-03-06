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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gui.MainFrame;

/**
 * A keyboard listener for the {@link SchemaTree}. Enables manipulation of the tree via keyboard.
 * 
 * @author Viktor
 *
 */
public class SchemaTreeKeyListener extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
		
		SchemaTree tree = (SchemaTree)e.getSource();
		
		switch(e.getKeyCode()){
			case(KeyEvent.VK_ENTER):
			{
				if(tree.getLastSelectedPathComponent() instanceof TableNode){
	    			TableNode node = (TableNode)tree.getLastSelectedPathComponent();
	    			if (node == null) return;
	    			
	    			MainFrame.getInstance().getMainContentPane().add(node);
	    			tree.expandPath(tree.getLeadSelectionPath());
	    			
	    		} else {
	    			tree.expandPath(tree.getLeadSelectionPath());
	    		}
			}	break;
			
			
		}
	}
	
	
}
