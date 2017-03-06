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

import gui.MainFrame;
import gui.panel.SchemaTab;
import gui.toolbars.TableToolbar;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.Table;
import application.Application;


/**
 * An Action that extends {@link AbstractAction}.
 * When invoked, it moves the current parent table up,
 * making it a child table to the selected parent.
 * 
 * @author Marko
 * 
 * @see AbstractAction
 * @see SchemaTab
 * @see MainContentPane
 */
public class DemoteAction extends AbstractAction {

	/**
	 * {@link SchemaTab} that the action is related to.
	 */
	private SchemaTab tab;
	
	/**
	 * {@link TableToolbar} that the action is related to.
	 */
	private TableToolbar toolbar;
	
	/**
	 * {@link DemoteAction} public constructor
	 * Initializes the object with a small icon.
	 */
	public DemoteAction(SchemaTab schemaTab,TableToolbar tableToolbar) {
		putValue(SMALL_ICON, new ImageIcon("images/icons/demote.png"));
		this.tab = schemaTab;
		this.toolbar = tableToolbar;
	}
	
	/**
	 * Moves the current parent table down, making it
	 * a child table to one of its parents.
	 * If there are multiple parents, pops a popup
	 * menu to let the use choose which parent is going 
	 * to be shown.
	 * 
	 * @see MainContentPane
	 */
	@Override
	public void actionPerformed(ActionEvent e) {		

		Table table = tab.getTable();
		Set<String> parents = table.getParents();
		final JPopupMenu popup = new JPopupMenu();
		if(parents.size() > 1) {
		for(String parent : parents){
					
	        popup.add(new JMenuItem(new AbstractAction(parent) {
	            public void actionPerformed(ActionEvent e) {
	            	Table currentTable = Application.getSchema().getTableByName(parent);
	    			MainFrame.getInstance().getMainContentPane().add(currentTable);
	            }
	        }));	
		}	
		
		 	popup.add(new JMenuItem(new AbstractAction("SVI RODITELJI") {
	        	public void actionPerformed(ActionEvent e) {
	        		for(String parent : parents) { 
	        			Table currentTable = Application.getSchema().getTableByName(parent);
	        			MainFrame.getInstance().getMainContentPane().add(currentTable);
	        		}
	        	}
		 	}));
			toolbar.add(popup);
			popup.show((JComponent)e.getSource(), 5, 5);
		}else{
			for(String parent : parents){         
		        Table currentTable = Application.getSchema().getTableByName(parent);
		    	MainFrame.getInstance().getMainContentPane().add(currentTable);
		       	
			}	
		}		
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

	/**
	 * Gets the actions {@link TableToolbar} , used to get the toolbar that the action is related to.
	 * 
	 * @return {@link TableToolbar}
	 */
	public TableToolbar getToolbar() {
		return toolbar;
	}

	/**
	 * Sets the actions {@link TableToolbar} , used to get the toolbar that the action is related to.
	 * 
	 * @param toolbar
	 */
	public void setToolbar(TableToolbar toolbar) {
		this.toolbar = toolbar;
	}
	
	
}
