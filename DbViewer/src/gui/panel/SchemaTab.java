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

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import application.Application;
import gui.toolbars.TableToolbar;
import model.Column;
import model.ForeignKeyConstraint;
import model.Table;
import sql.RecordsManagement;
import sql.TypesOfActions;

/**
 * SchemaTab represents the content of the tab inside the main working area. It consists of toolbar, table and incorporates
 * potential tabbed pane representing children tabs. Behavior of component is set through the constructor.
 * 
 * @author Viktor
 * @author Nikola
 *
 */
@SuppressWarnings("serial")
public class SchemaTab extends JPanel{

	/**
	 * {@link Table} reference. Model for {@link SchemaTab}
	 */
	private Table table; 
	/**
	 * {@link JTable} reference. Visualisation of data from DB.
	 */
	private JTable content;
	/**
	 * {@link SchemaTabbedPane} reference. Container for children tables.
	 */
	private SchemaTabbedPane children = null;
	/**
	 * {@link JScrollPane} reference.
	 */
	private JScrollPane scrollPane;
	/**
	 * {@link JSplitPane} reference.
	 */
	private JSplitPane splitPane;
	/**
	 * {@link TableToolbar} reference. Container for actions.
	 */
	private TableToolbar toolbar;
	
	/**
	 * {@link Table} has children?
	 */
	private boolean hasChildren = false;
	
	/**
	 * Currently selected row.
	 */
	private int lastSelectedRow = -1;
	/**
	 * 
	 * Constructor for {@link SchemaTab} parametrization.
	 * 
	 * @param table {@link Table} from which the data is reconstructed from
	 * @param generateChildren boolean, {@code true} if {@link SchemaTabbedPane} of children should be generated, otherwise {@code false}
	 */
	public SchemaTab(Table table, boolean generateChildren) {
		//super();
		init(table, generateChildren);
	}	
	/**
	 * Initialization of {@link SchemaTab}
	 * @param table {@link Table} from which the data is reconstucted from
	 * @param generateChildren boolean, {@code true} if {@link SchemaTabbedPane} of children should be generated, otherwise {@code false}
	 */
	private void init(Table table, boolean generateChildren){
		
		this.hasChildren = generateChildren;
		this.table = table;
		setLayout(new java.awt.BorderLayout());
		
		toolbar = new TableToolbar(this);
		add(toolbar, BorderLayout.NORTH);
		
		//function for setting up the table
		if(!Application.test)
			refreshTable();
		
		//table scrollpane
		scrollPane = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//content.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
		//Depending on the passed parameter generate and display children SchemaTabbedPane. 
		if(generateChildren){
			//add both current table, and tabbed pane with child tables
			//check whether table contains any children
			if(!table.getChildren().isEmpty()){
				//if list of children is not empty, create the lower tabbed pane and fill it with children Table tabs.
				children = new SchemaTabbedPane();
				
				//iterate through all the children codes
				for(String code : table.getChildren()){
					//get all tables from table codes from global metaschema map
					Table t = Application.getSchema().getTableMap().get(code);
					//prepare, create and insert the tab, parameter is false because it should not generate its children
					SchemaTab tmp = new SchemaTab(t,false);
					children.addTab(t.getName(), tmp);
				}
				
				splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, children);
				splitPane.setResizeWeight(0.5);
				this.add(splitPane);
				
			} else {
				//if not add only the current table
				this.add(scrollPane);
			}
			
			
		} else {
			//add only the current table
			this.add(scrollPane);
		}
		
		//content.doLayout();
		//this.doLayout();
		
		//setTableColumnWidth();
		
	}
	/**
	 * Method which creates (if doesn't exist) / refreshes(if exists) {@link DefaultTableModel} to/from {@link JTable)
	 */

	public void refreshTable(){
		if(content==null){
			DefaultTableModel model=new DefaultTableModel();
			content = new JTable(model){
					@Override
			        public boolean isCellEditable(int row, int column) {                
		                return false;               
					};
			};
			final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
		    content.setRowSorter(sorter);
			
			content.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			content.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if (!arg0.getValueIsAdjusting()) {
						lastSelectedRow=content.getSelectedRow();
						if(hasChildren)
							generateChildrenValues();
			        }
				}
			});
			content.getTableHeader().setReorderingAllowed(false);
			content.setFillsViewportHeight(true);
		}
		DefaultTableModel dm = (DefaultTableModel)content.getModel();
		int selektovanRed=getLastSelectedRow();
		SchemaTab stDijete=null;
		int selRedDijeteta=-1;
		if(children!=null){
			stDijete=(SchemaTab) children.getSelectedComponent();
			selRedDijeteta=stDijete.getLastSelectedRow();
		}
		clearData(dm);
		RecordsManagement rm=new RecordsManagement(table, TypesOfActions.SEARCH_ALL_RECORDS,null,null);
		if(hasChildren)
			dm.setDataVector(rm.getData(), rm.getHeaders());
		else
			dm.setDataVector(new Vector<Vector<Object>>(), rm.getHeaders());
		setLastSelectedRow(selektovanRed);
		if(selektovanRed!=-1){
			content.setRowSelectionInterval(lastSelectedRow, lastSelectedRow);
			if(stDijete!=null && selRedDijeteta!=-1)
				stDijete.getContent().setRowSelectionInterval(selRedDijeteta, selRedDijeteta);
		}
	}
	
	/**
	 * Proposed method for setting the column width. 
	 * 
	 * @category experimental
	 */
	public void setTableColumnWidth() {
		
		content.getColumnModel().getColumn(0).setMaxWidth(20+content.getModel().getRowCount()/2);
		
		int totalWidth = this.getSize().width;
		
		int totalCalculatedWidth = 0;
		
		for(int i=1; i<content.getColumnCount(); i++) {
			String header = (String) content.getColumnModel().getColumn(i).getHeaderValue();
			int add = 0;
			
			if(20+header.length()*15>150)
				add = 150;
			else
				add = 20+header.length()*15;
				
			totalCalculatedWidth += add;
			//content.getColumnModel().getColumn(i).setMinWidth(header.length()*20);
		}
		
		System.out.println("calculated: "+totalCalculatedWidth);
		System.out.println("total: "+totalWidth);

		
		if(totalCalculatedWidth<totalWidth-100) {
			content.setAutoResizeMode( JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		} else {
			content.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
			for(int i=1; i<content.getColumnCount(); i++) {
				String header = (String) content.getColumnModel().getColumn(i).getHeaderValue();
				
				int add;
				
				if(20+header.length()*15>150)
					add = 150;
				else
					add = 20+header.length()*15;
				
				content.getColumnModel().getColumn(i).setMinWidth(add);
				content.getColumnModel().getColumn(i).setWidth(add);
			}
		}
	}
	
	/**
	 * Method for clearing all the data from Table. Prevents memory leakage and explicitly signals to invoke GC on released objects.
	 * 
	 * @author Viktor
	 * @param dm
	 */
	@SuppressWarnings("rawtypes")
	public void clearData(DefaultTableModel dm){
		Vector data = dm.getDataVector();
		
		for(Object v : data){
			((Vector)v).clear();
		}
		
		data.clear();
		
		data = null;
		
		System.gc();
	}
	
	/**
	 * Returns a map with selected row Primary Key values (code is the {@link Column} code, 
	 * value is an {@link Object} representing the actual value.
	 * 
	 * @return map with selected row Primary Key values
	 */
	public HashMap<String,Object> getSelectedRowPrimaryKeys(){
		int row=content.getSelectedRow();
		if(row!=-1){
			ArrayList<String> cCodes=new ArrayList<String>();
			ArrayList<String> cNames=new ArrayList<String>();
			for(int i=0; i<table.getColumns().size(); i++){
				if(table.getColumns().get(i).getConstraint().getPrimaryKey()){
					cCodes.add(table.getColumns().get(i).getCode());
					cNames.add(table.getColumns().get(i).getName());
				}
			}
			HashMap<String,Object> hm=new HashMap<String,Object>();
			
			for(int i=1; i<=table.getColumns().size();i++){
				Object value=(Object) content.getValueAt(row,i);
				String columnName=content.getColumnName(i);
				for(int j=0; j<cNames.size(); j++){
					if(cNames.get(j).equals(columnName)){
						hm.put(cCodes.get(j),value);
						break;
					}
				}
			}
			return hm;
		}else
			return null;
	}
	
	/**
	 * Returns a map with selected row values (code is the {@link Column} code, 
	 * value is an {@link Object} representing the actual value.
	 * 
	 * @return map with selected row values
	 */
	public HashMap<String,Object> getSelectedRowValues(){
		HashMap<String,Object> hm=new HashMap<String,Object>();
		if(lastSelectedRow!=-1){
			for(int i=1; i<=table.getColumns().size();i++){
				Object value=(Object) content.getValueAt(lastSelectedRow,i);
				String columnName=table.getColumns().get(i-1).getCode();
				hm.put(columnName, value);
			}	
		}
		return hm;
	}

	/**
	 * Returns {@link Table} code.
	 * @return
	 */
	public String getCode(){
		return table.getCode();
	}
	/**
	 * Returns {@link Table} name.
	 */
	
	public String getName(){
		if(table==null)
			return "string";
		return table.getName();
	}

	/**
	 * Returns {@link SchemaTabbedPane} object.
	 * @return children
	 */
	public SchemaTabbedPane getChildren() {
		return children;
	}

	/**
	 * Sets {@link SchemaTabbedPane} object.
	 * @param children
	 */
	public void setChildren(SchemaTabbedPane children) {
		this.children = children;
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

	/**
	 * Returns {@link JTable} object.
	 * @return content
	 */
	public JTable getContent() {
		return content;
	}
	
	/**
	 * Returns bool value for having or not having children for current table.
	 * @return hasChildren
	 */
	public boolean hasChilderen(){
		return hasChildren;
	}
	
	/**
	 * Method for generating children values (values which have references (Foreign Key) on the selected row in parent's {@link SchemaTab})
	 */	
	public void generateChildrenValues(){
		if(children!=null){
			for(int i=0; i<children.getTabCount(); i++){
				SchemaTab tabure=(SchemaTab)children.getComponent(i);
				
				Table tabela=tabure.getTable();
				
				HashMap<String,Object> mapa=getSelectedRowValues();
				HashMap<String,Object> za_uslov=new HashMap<String,Object>();
				
				if(mapa!=null)
				{
					for(ForeignKeyConstraint fkc : tabela.getReferences()){
						if(fkc.getReferencedTableCode().equals(table.getCode())){
							for(int j=0; j<fkc.getReferencedColumnCodes().size(); j++){
								String rcc=fkc.getRefeeringColumnCodes().get(j);
								Object rccValue=mapa.get(fkc.getReferencedColumnCodes().get(j));
								za_uslov.put(rcc, rccValue);
							}
						}
					}
				
					DefaultTableModel dm = (DefaultTableModel)tabure.getContent().getModel();
					clearData(dm);
					RecordsManagement rm=new RecordsManagement(tabela,TypesOfActions.SEARCH_CHILDREN_BY_FK,null,za_uslov);
					dm.setDataVector(rm.getData(), rm.getHeaders());
				} else {
					tabure.clearData((DefaultTableModel)tabure.getContent().getModel());
				}
			}
		}
	}
	
	/**
	 * Method for changing the language of components contained inside the {@link SchemaTab}.
	 * 
	 * @param rb Resource bundle containing all the information for translation
	 */
	public void changeLanguage(ResourceBundle rb) {
		DefaultTableModel dm = (DefaultTableModel) content.getModel();

		Vector<String> columnNames = new Vector<>();
		
		columnNames.addElement("");
		
		for(int i = 1; i < content.getColumnCount(); i++) {
			columnNames.addElement(rb.getString(table.getColumns().get(i-1).getCode()));
		}
		
		dm.setColumnIdentifiers(columnNames);
	}
	
	/**
	 * Getter for selected row in {@link JTable}
	 * @return selected row
	 */

	public int getLastSelectedRow() {
		return lastSelectedRow;
	}
	
	/**
	 * Setter for selected row in {@link JTable}
	 * @param lastSelectedRow index of selected row
	 */
	public void setLastSelectedRow(int lastSelectedRow) {
		this.lastSelectedRow = lastSelectedRow;
	}
}
