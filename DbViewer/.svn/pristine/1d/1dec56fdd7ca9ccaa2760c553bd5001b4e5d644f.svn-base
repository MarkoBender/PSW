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
 
 package gui.panel;

import java.util.ResourceBundle;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import application.ActionManager;
import model.Table;
import tree.TableNode;

/**
 * SchemaTabbedPane represents the container of SchemaTab tabs. It is a specialization of {@link JTabbedPane}.
 * 
 * @author Viktor
 *
 */
public class SchemaTabbedPane extends JTabbedPane {
	
	/**
	 * Constructor for {@link SchemaTabbedPane}
	 */
	public SchemaTabbedPane() {
		super();
		
		this.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if(((JTabbedPane)arg0.getSource()).getTabCount()>0)
					ActionManager.enableCRUDActions();
				else
					ActionManager.disableCRUDActions();
				
			}
		});
	}
	
	/**
	 * 
	 * Method for adding content to the {@link SchemaTabbedPane}. Content is passed through {@link TableNode} parameter.
	 * From the node the {@link Table} is extracted and table content is used in building the {@link SchemaTab} content.
	 * 
	 * Allows only unique occurrences of tabs, if tab is already present only focus is set.
	 * 
	 * @param node - {@link TableNode} is a specific node from {@link SchemaTree}
	 */
	public void add(TableNode node) {
		int position = TabWithCodePresentPosition(node.getTable().getCode());
		if(position ==-1){
			SchemaTab tab = new SchemaTab(node.getTable(),true);

			this.addTab(tab.getName(), tab);
			this.setTabComponentAt(this.getTabCount()-1, new ClosableTabComponent(tab));
			this.setSelectedComponent(tab);
		} else {
			this.setSelectedIndex(position);
		}
	}
	
	/**
	 * 
	 * Method for adding content to the {@link SchemaTabbedPane}. Content is passed through {@link Table} parameter,
	 * table content is used in building the {@link SchemaTab} content.
	 * 
	 * Allows only unique occurrences of tabs, if tab is already present only focus is set.
	 * 
	 * @param table - {@link Table} 
	 */
	public void add(Table table) {
		int position = TabWithCodePresentPosition(table.getCode());
		if(position ==-1){
			SchemaTab tab = new SchemaTab(table,true);

			this.addTab(tab.getName(), tab);
			this.setTabComponentAt(this.getTabCount()-1, new ClosableTabComponent(tab));
			this.setSelectedComponent(tab);
		} else {
			this.setSelectedIndex(position);
		}
	}
	
	/**
	 * Method for checking whether JTabbedPane extension {@link SchemaTabbedPane} contains specific extension of
	 * JTab - {@link SchemaTab}. Tab is uniquely identified by its {@link Table} code.
	 * 
	 * @param code {@link Table} unique identifier, code field
	 * @return Index if the tab is present, -1 if not found
	 */
	public int TabWithCodePresentPosition(String code) {
		for(int i=0; i<this.getTabCount(); i++){
			if(((SchemaTab)this.getComponentAt(i)).getCode().equals(code))
				return i;
		}
		return -1;
	}
	
	/**
	 * Method for changing the display language of the {@link SchemaTabbedPane} and currently displayed content of active and opened instances of {@link SchemaTab}.
	 * 
	 * @param rb Resource bundle containing the translation with appropriate codes
	 */
	public void changeLanguage(ResourceBundle rb) {
		for(int i = 0; i < getTabCount(); i++) {
			SchemaTab t = (SchemaTab) getComponentAt(i);
			t.changeLanguage(rb);
			
			if(getTabComponentAt(i) instanceof ClosableTabComponent)
				((ClosableTabComponent)getTabComponentAt(i)).updateTabText(t.getTable().getName());
			else
				setTitleAt(i, t.getTable().getName());
			
			if(t.getChildren()!=null)
				t.getChildren().changeLanguage(rb);
		}

	}
	
}
