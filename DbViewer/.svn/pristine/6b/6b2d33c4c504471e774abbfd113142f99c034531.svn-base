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

import javax.swing.tree.DefaultMutableTreeNode;

import application.Application;
import model.Table;

/**
 * TableNode is a specialization of {@link DefaultMutableTreeNode} which carries additional information about the referenced {@link Table}.
 * @author Viktor
 * @author Jelena
 *
 */
public class TableNode extends DefaultMutableTreeNode {
	/**
	 * {@link Table} reference to model for {@link TableNode}
	 */
	private Table table;
	
	/**
	 * TableNode constructor.
	 * 
	 * @param table {@link Table} which this node references.
	 */
	public TableNode(Table table){
		super(table);
		this.table = table;
		
		//Adds table children recursively
		for(String s : table.getChildren()) {
			this.add(new TableNode(Application.getSchema().getTableMap().get(s)));
		}
	}
	
	/**
	 * Returns {@link Table} object.
	 * @return table
	 */

	public Table getTable() {
		return table;
	}

	/**
	 * Sets {@link Table} object.
	 * @param table
	 */
	public void setTable(Table table) {
		this.table = table;
	}
}
