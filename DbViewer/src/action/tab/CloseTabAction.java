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
 
 package action.tab;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.panel.SchemaTab;
import gui.panel.SchemaTabbedPane;

/**
 * Closes a selected tab.
 * 
 * @author  Viktor
 *
 */
public class CloseTabAction extends AbstractAction {

	/**
	 * {@link SchemaTab} that the action is related to.
	 */
	private SchemaTab tab;
	
	/**
	 * Public constructor, sets a small icon and a {@link SchemaTab} that the actions is related to.
	 * 
	 * @param schemaTab
	 */
	public CloseTabAction(SchemaTab schemaTab) {
		putValue(SMALL_ICON, new ImageIcon("images/icons/remove_record.png"));
		this.tab = schemaTab;
	}
	
	/**
	 * Removes the actions {@link SchemaTab} from {@link SchemaTabbedPane} that contains it.
	 * 
	 * @param arg0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		SchemaTabbedPane pane = (SchemaTabbedPane) ((JButton)arg0.getSource()).getParent().getParent().getParent();
		pane.remove(tab);
	}

	/**
	 * Gets the actions {@link SchemaTab} , used to get the table that the action is related to.
	 * 
	 * @return {@link SchemaTab}
	 */
	public SchemaTab getTab() {
		return tab;
	}

	/**
	 * Sets the actions {@link SchemaTab} , used to get the table that the action is related to.
	 * 
	 * @param tab
	 */
	public void setTab(SchemaTab tab) {
		this.tab = tab;
	}
}
