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
 
 package action.record;

import gui.panel.SchemaTab;
import gui.toolbars.TableToolbar;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import dialogs.AdvancedSearchDialog;


/**
 * An action used for opening {@link AdvancedSearchDialog} that is used for searching the current table.
 * @author Marko
 *
 */
public class AdvancedSearchAction extends AbstractAction {

	/**
	 * {@link SchemaTab} that the action is related to.
	 */
	private SchemaTab tab;
	
	/**
	 * Public constructor, sets the small icon and {@link SchemaTab} that this action is related to.
	 * @param schemaTab
	 */
	public AdvancedSearchAction(SchemaTab schemaTab) {
		putValue(SMALL_ICON, new ImageIcon("images/icons/search.png"));
		this.tab = schemaTab;
	}
	
	/**
	 * Displays {@link AdvancedSearchDialog}. If parent of chosen component (type {@link JComponent}) 
	 * is of type {@link TableToolbar} then tab (type {@link SchemaTab}) is fetched. 
	 * If parent of chosen component is not of type {@link TableToolbar} then selectedComponent is fetched. 
	 * Gets number of row that is selected.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
			
		int row=tab.getContent().getSelectedRow();
		AdvancedSearchDialog advancedSearchDialog = new AdvancedSearchDialog(tab);
		advancedSearchDialog.setVisible(true);

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
