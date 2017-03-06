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

import interpreter.DBInterpreter;
import interpreter.JSONInterpreter;
import interpreter.SQLInterpreter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import model.DBSchema;
import model.Package;
import model.Table;
import sql.DbConnection;
import application.Application;
import gui.MainFrame;

/**
 * SchemaTree is a specialization of {@link JTree} used for initializing {@link DBSchema} and storing package structure.
 * 
 * @author Viktor
 * @author Jelena
 * @author Nikola
 */
public class SchemaTree extends JTree {

	/**
	 * Instance of {@link SchemaTree} used in singleton pattern.
	 */
	private static SchemaTree instance = null;
	
	/**
	 * Package tree map contains all the packages defined in the model specification.
	 */
	private Map<String, DefaultMutableTreeNode> packageTreeMap = new HashMap<>(); 
	
	/**
	 * Disposes of the instance, which consequentially triggers new instance creation on getInstance method call.
	 */
	public static void destroy() {
		instance = null;
	}
	
	/**
	 * Instantiate {@link SchemaTree} from JSON file.
	 * @return
	 */
	public static SchemaTree getInstance(){
		if(instance==null)
			instance = new SchemaTree();
		return instance;
	}
	
	/**
	 * Parametrized constructor for creating {@link SchemaTree} from JSON file.
	 * 
	 */
	private SchemaTree(){
		super();
		if(MainFrame.getInstance().getInterpreter() instanceof SQLInterpreter) {
			initializeSQL();
		} else if(MainFrame.getInstance().getInterpreter() instanceof JSONInterpreter){
			initializeJSON();
		}
	}
	
	/**
	 * Method for interpreting JSON file and setting the global {@link DBSchema} object.
	 */
	private void initializeJSON() {
		
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// interpreter creation
		DBInterpreter i = MainFrame.getInstance().getInterpreter();
	
		DBSchema metaSchema = null;

		try {
			// interpreter invocation
			metaSchema = i.interpretSchema();

			// non-recoverable error happened in JSON file definition
			if(metaSchema == null){
				System.out.println("shit happened");
				return;
			}
			
			Application.setSchema(metaSchema);
			
			// checking for consistency
			checkModelAndDatabaseConsistency();

			DefaultMutableTreeNode top = new DefaultMutableTreeNode(metaSchema.getSchemaName()); 
			DefaultTreeModel model = new DefaultTreeModel(top);
			this.setModel(model);
			setCellRenderer(new TreeRenderer());  
			
			// gets dynamic resource bundle for data
			ResourceBundle rb = Application.getDynamicBundle();
			
			// iterating and instantiating the table with packages
			for(model.Package p : metaSchema.getPackages()) {
				DefaultMutableTreeNode rootPackage = new DefaultMutableTreeNode(p);	
				//packageTreeMap.put(p.getPackageName(), rootPackage); 
				packageTreeMap.put(p.getPackageCode(), rootPackage); 
				top.add(rootPackage);
				addChildren(rootPackage);
			}

			// iterating and instantiating the table node with all the children, and placing it into proper packages defined in the model
			for(Table t : metaSchema.getTableMap().values()){
				if(t.getParents().isEmpty() && packageTreeMap.containsKey(t.getPackageName())) {
					DefaultMutableTreeNode node = packageTreeMap.get(t.getPackageName()); 
					node.add(new TableNode(t));
				}
			}

			
			TreeDoubleClickListener listener = new TreeDoubleClickListener(this); 
			addMouseListener(listener);

			addKeyListener(new SchemaTreeKeyListener());
			
		} catch (NullPointerException e){
			
			JOptionPane.showMessageDialog(MainFrame.getInstance(),Application.getResourceBundle().getString("JSONErrorText"), Application.getResourceBundle().getString("JSONError"),JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

	}
	
	/**
	 * Method for adding nodes into the tree depending on the type od the node (Table or Package).
	 * Method is recursive and it populates the whole depth of the branch of the tree.
	 * 
	 * @param node
	 */
	private void addChildren(DefaultMutableTreeNode node) {
		model.Package p = (Package) node.getUserObject(); 
		if(p.getSubPackages().size() == 0) {
			return; 
		}
		
		for(Package p1 : p.getSubPackages()) {
			DefaultMutableTreeNode subNode = new DefaultMutableTreeNode(p1);
			//packageTreeMap.put(p1.getPackageName(), subNode); 
			packageTreeMap.put(p1.getPackageCode(), subNode); 
			node.add(subNode);
			addChildren(subNode);
			
		}
	}
	
	/**
	 * Checks the consistency between model provided in {@link DBSchema} and database to which it connects to.
	 * If there is discrepancy between the two, application is shutting down since that represents a major error requiring repair beyond the application capabilities.
	 * 
	 */
	private void checkModelAndDatabaseConsistency() {
		Connection conn = DbConnection.getInstance().getConnection();
		PreparedStatement ps;
		
		try {
			// get all the tables from the database
			ps = conn.prepareStatement("SELECT * FROM INFORMATION_SCHEMA.TABLES");
			ResultSet tables = ps.executeQuery();
			
			int counter = 0;
			
			// check whether all the tables defined in the model are existing in the database
			while(tables.next()) {
				if(Application.getSchema().getTableMap().containsKey(tables.getObject(3).toString())){
					counter++;
				}
			}
			
			// if there is a difference, that is an error, message is written and application needs to be shut down
			if(counter != Application.getSchema().getTableMap().size()) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(),Application.getResourceBundle().getString("DBErrorText"), Application.getResourceBundle().getString("DBError"),JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Changes the display language of this component.
	 * 
	 * @param rb Resource bundle containing necessary information for component translation
	 */
	public void changeLanguage(ResourceBundle rb) {
		for(int i = 0; i < getModel().getChildCount(getModel().getRoot()); i++) {
			changeLevelLanguage((DefaultMutableTreeNode) getModel().getChild(getModel().getRoot(), i), rb);
		}
	}
	
	/**
	 * Function for recursive tree structure iteration and translation of subcomponents to the language given in the resource bundle.
	 * 
	 * @param node Tree node
	 * @param rb Resource bundle containing necessary information for node component translation
	 */
	private void changeLevelLanguage(DefaultMutableTreeNode node, ResourceBundle rb) {
		
		if(node.getUserObject() instanceof Package) {
			((Package)node.getUserObject()).setPackageName(rb.getString(((Package)node.getUserObject()).getPackageCode()));
			Package p = (Package)node.getUserObject(); 
			node.setUserObject(p);
			((DefaultTreeModel)getModel()).nodeChanged(node);
		}
		
		if(node.getUserObject() instanceof Table) {
			((Table)node.getUserObject()).setName(rb.getString(((TableNode)node).getTable().getCode()));
			((Table)node.getUserObject()).changeColumnNames(rb);
			((DefaultTreeModel)getModel()).nodeChanged(node);
		}
		
		for(int i = 0; i < node.getChildCount(); i++) {
			changeLevelLanguage((DefaultMutableTreeNode) node.getChildAt(i), rb);
		}
	}
	

	/**
	 * Method for interpreting SQLInterpreter and setting the global {@link DBSchema} object.
	 */
	private void initializeSQL() {
		
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		// interpreter creation
		DBInterpreter i = new SQLInterpreter();
		
		DBSchema metaSchema = null;

		try {
			// interpreter invocation
			metaSchema = i.interpretSchema();

			// non-recoverable error happened in JSON file definition
			if(metaSchema == null){
				System.out.println("shit happened");
				return;
			}
			
			Application.setSchema(metaSchema);
			
			// checking for consistency
			checkModelAndDatabaseConsistency();

			DefaultMutableTreeNode top = new DefaultMutableTreeNode(metaSchema.getSchemaName()); 
			DefaultTreeModel model = new DefaultTreeModel(top);
			this.setModel(model);
			setCellRenderer(new TreeRenderer());  
			
			// gets dynamic resource bundle for data
			ResourceBundle rb = Application.getDynamicBundle();
			
			// iterating and instantiating the table with packages
			for(model.Package p : metaSchema.getPackages()) {
				DefaultMutableTreeNode rootPackage = new DefaultMutableTreeNode(p);	
				//packageTreeMap.put(p.getPackageName(), rootPackage); 
				packageTreeMap.put(p.getPackageCode(), rootPackage); 
				top.add(rootPackage);
				addChildren(rootPackage);
			}

			// iterating and instantiating the table node with all the children, and placing it into proper packages defined in the model
			for(Table t : metaSchema.getTableMap().values()){
				if(t.getParents().isEmpty() && packageTreeMap.containsKey(t.getPackageName())) {
					DefaultMutableTreeNode node = packageTreeMap.get(t.getPackageName()); 
					node.add(new TableNode(t));
				}
			}

			
			TreeDoubleClickListener listener = new TreeDoubleClickListener(this); 
			addMouseListener(listener);

			addKeyListener(new SchemaTreeKeyListener());
			
		} catch (NullPointerException e){
			
			JOptionPane.showMessageDialog(MainFrame.getInstance(), Application.getResourceBundle().getString("DBErrorText"), Application.getResourceBundle().getString("DBError"),JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}

	}
	
}
