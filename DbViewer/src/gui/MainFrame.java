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
 
 package gui;

import gui.menus.MainMenuBar;
import gui.panel.SchemaTabbedPane;
import gui.toolbars.MainToolBar;
import model.DBSchema;
import tree.SchemaTree;
import interpreter.DBInterpreter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import application.ActionManager;
import application.Application;

import javax.swing.ImageIcon;

/**
 * Main frame which represents the GUI of the application.
 * 
 * @author Viktor
 * @author Nikola
 * @author Jelena
 * @author Marko
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	/**
	 * Tree structure for displaying hierarchical data
	 */
	private SchemaTree tree;
	/**
	 * Menu bar
	 */
	private MainMenuBar mainMenuBar;
	/**
	 * Main content pane
	 */
	private SchemaTabbedPane mainContentPane;
	/**
	 * Main toolbar of the application
	 */
	private MainToolBar mainToolBar;
	/**
	 * Splits the tree and main content pane display
	 */
	private JSplitPane mainSplitPane;
	/**
	 * Interpreter of {@link DBSchema}
	 */
	private DBInterpreter interpreter;
	
	/**
	 * Instance of main frame used for singleton pattern.
	 */
	private static MainFrame instance = null;
	
	/**
	 * Returns instance of main frame.
	 * @return instance
	 */
	public static MainFrame getInstance(){
		if(instance == null)
			instance = new MainFrame();
		return instance;
	}
	
	/**
	 * Default constructor - initializes the application.
	 */
	private MainFrame() {
	
	}
	
	/**
	 * Destructor of {@link MainFrame}
	 */
	public void destroy() {
		SchemaTree.destroy();
		tree = null;
		instance = null;
	}
	
	/**
	 * Initializes the Tree and main panel. Initially disables the CRUD actions until the first table gets open.
	 */
	public void initialize() {
		initializeTree();
		initializeDisplay();
		
		// CRUD actions are initially disabled
		ActionManager.disableCRUDActions();
	}
	
	/**
	 * Initializes and makes layout of the GUI.
	 */
	private void initializeDisplay() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	
		this.setSize(new Dimension(screenSize.width * 7/10, screenSize.height * 7/10));
		this.setMinimumSize(new Dimension(screenSize.width * 7/10, screenSize.height * 7/10));
		
		setIconImage(new ImageIcon("images/icons/icon_256.png").getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setTitle(Application.getResourceBundle().getString("DBViewer"));
		
		setLayout(new BorderLayout());
		
		mainMenuBar = new MainMenuBar();
		setJMenuBar(mainMenuBar);
		
		mainToolBar = new MainToolBar();
		add(mainToolBar,BorderLayout.NORTH);
		
		JScrollPane treePane = new JScrollPane(tree);
		treePane.setMinimumSize(new Dimension(300,100));
		
		mainContentPane = new SchemaTabbedPane(); 
		
		mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treePane,mainContentPane);
		add(mainSplitPane, BorderLayout.CENTER);
	}
	
	/**
	 * Initializes the tree and instantiates the model of data of the application {@link DBSchema}, by invoking the interpreter of 
	 * the meta-data from some data source.
	 */
	private void initializeTree() {
		SchemaTree.destroy();
		tree = SchemaTree.getInstance();
		
		tree.setMinimumSize(new Dimension(300,100));
	}
	
	/**
	 * Gets the {@MainFrame}s {@link SchemaTree}
	 * 
	 * @return SchemaTree
	 */
	public SchemaTree getTree() {
		return tree;
	}

	/**
	 * Sets the {@MainFrame}s {@link SchemaTree}
	 * 
	 * @param tree
	 */
	public void setTree(SchemaTree tree) {
		this.tree = tree;
	}

	/**
	 * Gets the {@MainFrame}s {@link MainMenuBar}
	 * 
	 * @return MainMenuBar
	 */
	public MainMenuBar getMainMenuBar() {
		return mainMenuBar;
	}

	/**
	 * Sets the {@MainFrame}s {@link MainMenuBar}
	 * 
	 * @param mainMenuBar
	 */
	public void setMainMenuBar(MainMenuBar mainMenuBar) {
		this.mainMenuBar = mainMenuBar;
	}

	/**
	 * Gets the {@MainFrame}s {@link MainToolBar}
	 * 
	 * @return MainToolBar
	 */
	public MainToolBar getMainToolBar() {
		return mainToolBar;
	}

	/**
	 * Sets the {@MainFrame}s {@link MainToolBar}
	 * 
	 * @param mainToolBar
	 */
	public void setMainToolBar(MainToolBar mainToolBar) {
		this.mainToolBar = mainToolBar;
	}

	/**
	 * Gets the {@MainFrame}s MainSplitPane
	 * 
	 * @return JSplitPane
	 */
	public JSplitPane getMainSplitPane() {
		return mainSplitPane;
	}

	/**
	 * Sets the {@MainFrame}s MainSplitPane
	 * 
	 * @param mainSplitPane
	 */
	public void setMainSplitPane(JSplitPane mainSplitPane) {
		this.mainSplitPane = mainSplitPane;
	}

	/**
	 * Sets the {@MainFrame}s instance
	 * @param instance
	 */
	public static void setInstance(MainFrame instance) {
		MainFrame.instance = instance;
	}

	/**
	 * Gets the {@MainFrame}s MainContentPane
	 * @return SchemaTabbedPane
	 */
	public SchemaTabbedPane getMainContentPane() {
		return mainContentPane;
	}

	/**
	 * Sets the {@MainFrame}s MainContentPane
	 * @param mainContentPane
	 */
	public void setMainContentPane(SchemaTabbedPane mainContentPane) {
		this.mainContentPane = mainContentPane;
	}
	
	/**
	 * Method for updating the language of the component and subcomponents.
	 */
	public void updateLanguage() {
		setTitle(Application.getResourceBundle().getString("DBViewer"));
		tree.changeLanguage(Application.getDynamicBundle());
		mainMenuBar.changeLanguage();
		mainContentPane.changeLanguage(Application.getDynamicBundle());
		ActionManager.updateActions();
	}

	/**
	 * Sets the {@MainFrame}s {@link DBInterpreter}
	 * @param interpreter
	 */
	public void setInterpreter(DBInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	/**
	 * Gets the {@MainFrame}s {@link DBInterpreter}
	 * @return DBInterpreter
	 */
	public DBInterpreter getInterpreter() {
		return interpreter;
	}
	
}
