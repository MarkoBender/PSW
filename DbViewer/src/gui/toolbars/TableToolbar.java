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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import action.record.AddRecordDialogAction;
import action.record.AdvancedSearchAction;
import action.record.DeleteRecordDialogAction;
import action.record.FirstRecordAction;
import action.record.LastRecordAction;
import action.record.ModifyRecordDialogAction;
import action.record.NextRecordAction;
import action.record.PreviousRecordAction;
import action.record.RefreshTableAction;
import action.tab.DemoteAction;
import action.tab.PromoteAction;
import gui.panel.SchemaTab;

/**
 * Table toolbar represents a toolbar with actions for manipulating a representation of a relational table in this application.
 * Table toolbar is assigned to each instance of {@link SchemaTab}.
 * 
 * @author Viktor
 * @author Nikola
 * @author Marko
 */
@SuppressWarnings("serial")
public class TableToolbar extends JToolBar {
	
	/**
	 * {@link SchemaTab} in which is current {@link TableToolbar} contained
	 */ 

	private SchemaTab tab; 
	
	/**
	 * Constructor. Creates and initializes the toolbar for the selected {@link SchemaTab}.
	 * 
	 * @param tab 
	 */
	public TableToolbar(SchemaTab tab) {
		super();
		this.tab = tab; 
		this.setFloatable(false);
		
		initialize(); 
	}
	
	/**
	 * Initializes the toolbar and all the needed actions and components.
	 */
	private void initialize() {
		add(new AddRecordDialogAction(tab)); 
		add(new DeleteRecordDialogAction(tab));
		add(new ModifyRecordDialogAction(tab));
		
		addSeparator();
		
		add(new RefreshTableAction(tab));
		
		addSeparator();
		
		add(new AdvancedSearchAction(tab));
		
		addSeparator();
		
		JTextField searchField = new JTextField(40);
		
		/**
		 * Registers the text to display in a tool tip.   The text 
		 * displays when the cursor lingers over the component.
		 *
		 * @param text  the string to display.  If the text is null, 
		 *              the tool tip is turned off for this component.
		 */	
		searchField.setToolTipText("Search Field");
		
		/**
		 * Key listener implements the quick search logic. It is necessary to unset the current table selection on change,
		 * by setting the last selected row index to -2.
		 * 
		 * The search is case insensitive.
		 */
		searchField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				tab.setLastSelectedRow(-2);
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				String text = searchField.getText();
				TableRowSorter<TableModel> sorter=(TableRowSorter<TableModel>) tab.getContent().getRowSorter();
				if (text.length() == 0) {
					sorter.setRowFilter(null);
		        } else {
		        	sorter.setRowFilter(RowFilter.regexFilter("(?iu)" + Pattern.quote(text)));
		        }
				tab.setLastSelectedRow(-2);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				tab.setLastSelectedRow(-2);
			}
			
		});
		add(searchField);
		
		addSeparator();
		
		
		add(new PreviousRecordAction(tab));
		add(new NextRecordAction(tab));
		add(new LastRecordAction(tab));
		add(new FirstRecordAction(tab));
		
		
		//PromoteAction p = new PromoteAction();
		//DemoteAction d = new DemoteAction();
		if(tab.hasChilderen()){
			if(!tab.getTable().getParents().isEmpty())
				add(new DemoteAction(tab,this));
		}
		else{
			add(new PromoteAction(tab));
		}
	}
	
	/**
	 * Getter for returning {@link SchemaTab} in which is current {@link TableToolbar} contained.
	 * @return tab
	 */

	public SchemaTab getTab() {
		return tab;
	}

	
}