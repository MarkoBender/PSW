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
 
 package gui.toolbars;

import javax.swing.JToolBar;

import action.tree.CollapseTreeAction;
import action.tree.ExpandTreeAction;
import action.tree.TreeMoveDownAction;
import action.tree.TreeMoveUpAction;
import application.ActionManager;


/**
 * Applications main tool bar, used for storing tree-related actions.
 * 
 * @author Marko
 *
 */
public class MainToolBar extends JToolBar{

	/**
	 * Constructor of {@link MainToolBar} class.
	 * Creates a toolbar with its actions.
	 * 
	 * @see CollapseTreeAction
	 * @see ExpandTreeAction
	 * @see TreeMoveUpAction
	 * @see TreeMoveDownAction
	 */
	public MainToolBar() {


		add(ActionManager.getInstance().getCollapseTreeAction());
		add(ActionManager.getInstance().getExpandTreeAction());
		addSeparator();
		add(ActionManager.getInstance().getTreeMoveUpAction());
		add(ActionManager.getInstance().getTreeMoveDownAction());
		


	}
	
}
