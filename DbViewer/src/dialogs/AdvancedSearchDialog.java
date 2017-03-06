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
 
 package dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import application.Application;
import gui.MainFrame;
import gui.panel.SchemaTab;
import model.Column;
import model.Table;
import sql.AdvancedSearchComponent;
import sql.AdvancedSearchLogicalOperator;
import sql.RecordsManagement;


@SuppressWarnings("serial")
/**
 * Advanced search dialog is a component for automating and making an SQL search query more user-friendly.
 * 
 * @author Viktor
 * @author Nikola
 *
 */
public class AdvancedSearchDialog extends JDialog {
	/**
	 * {@link Column} collection which is included in search.
	 */
	private ArrayList<Column> columns=new ArrayList<Column>();
	
	/**
	 * Main display panel.
	 */
	private JPanel panel = null;
	
	/**
	 * Panel for displaying selected conditions.
	 */
	private JPanel optionsPanel = null;
	
	/**
	 * {@link SchemaTab} to which this dialog is created.
	 */
	private SchemaTab tab;
	
	/**
	 * {@link Table} model to which this dialog is created.
	 */
	private Table table;
	
	/**
	 * Scroll pane to accommodate various number of conditions.
	 */
	private JScrollPane scrollPane;
	
	/**
	 * Constructor of the advanced search dialog, depending on the {@link SchemaTab} to which it is created, since the dialog
	 * relies on the information from {@link Table}.
	 * 
	 * @param tab {@link SchemaTab} parent component
	 */
	public AdvancedSearchDialog(SchemaTab tab) {
		super(MainFrame.getInstance());
		
		this.tab = tab;
		this.table=tab.getTable();
		
		// get all the columns from table
		for(Column column:table.getColumns()){
			int sqlType=column.getSqlType();
			//if(sqlType!=Types.BLOB)
				columns.add(column);
		}
		
		initPanel();
		
	}
	
	/**
	 * Helper function for initializing the GUI of the dialog
	 */
	private void initPanel() {
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		setSize(400, 400);
		setTitle(table.getName());
		setModal(true);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 400));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		optionsPanel = new JPanel();
		//optionsPanel.setPreferredSize(new Dimension(380, 400));
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.setAlignmentX(CENTER_ALIGNMENT);
		optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		
		scrollPane = new JScrollPane(optionsPanel);
		scrollPane.setPreferredSize(new Dimension(380, 300));
		
		add(panel);
		
		panel.add(scrollPane);
		
		
		JPanel columnCondition = new JPanel();
		columnCondition.setPreferredSize(new Dimension(400, 50));
		
		JComboBox<Column> selectColumn = new JComboBox<>();
		selectColumn.setPreferredSize(new Dimension(220,30));
		selectColumn.setMaximumSize(new Dimension(220,30));
		for(int i=0; i<columns.size(); i++) {
			selectColumn.addItem(columns.get(i));
		}
		columnCondition.add(selectColumn);	
		
		JButton addCondition = new JButton(Application.getResourceBundle().getString("addCondition"));
		addCondition.setPreferredSize(new Dimension(150,30));
		columnCondition.add(addCondition);
		
		panel.add(columnCondition);
		
		AdvancedSearchDialog tmp = this;
		addCondition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Column selected = (Column) selectColumn.getSelectedItem();
				optionsPanel.add(new AdvancedSearchComponent(selected, tmp));
			}
		});
		
		
		JPanel buttonsPanel1 = new JPanel();
		buttonsPanel1.setPreferredSize(new Dimension(400, 50));
		
		panel.add(buttonsPanel1);
		
		JButton addLogicalOperation = new JButton(Application.getResourceBundle().getString("addOperator"));
		addLogicalOperation.setPreferredSize(new Dimension(150,30));
		buttonsPanel1.add(addLogicalOperation);
		
		addLogicalOperation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				optionsPanel.add(new AdvancedSearchLogicalOperator(optionsPanel));
				scrollPane.doLayout();
			}
		});
		
		JButton clearAllConditions = new JButton(Application.getResourceBundle().getString("clearAll"));
		clearAllConditions.setPreferredSize(new Dimension(150,30));
		buttonsPanel1.add(clearAllConditions);
		
		clearAllConditions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				/*int componentCount;
				for(int i=0; i<optionsPanel.getComponentCount(); i++){
					Component c = optionsPanel.getComponent(i);
					c.setVisible(false);
					//c.doLayout();
					optionsPanel.remove(i);
				}*/
				
				optionsPanel.removeAll();
				
				optionsPanel.setVisible(false);
				optionsPanel.validate();
				optionsPanel.setVisible(true);
				
				optionsPanel.invalidate();
				optionsPanel.doLayout();
				scrollPane.doLayout();
			}
		});
		
		JPanel buttonsPanel2 = new JPanel();
		buttonsPanel2.setPreferredSize(new Dimension(400, 50));
		
		panel.add(buttonsPanel2);
		
		JButton search = new JButton(Application.getResourceBundle().getString("Search"));
		search.setPreferredSize(new Dimension(150,30));
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				RecordsManagement rm = new RecordsManagement(optionsPanel.getComponents(), tab.getTable());
				rm.executeAdvancedSearchRecords();
				
				DefaultTableModel dm = (DefaultTableModel)tab.getContent().getModel();
				
				tab.clearData(dm);
				dm.setDataVector(rm.getData(), rm.getHeaders());

			}
		});
		
		buttonsPanel2.add(search);
		
		JButton cancel = new JButton(Application.getResourceBundle().getString("Cancel"));
		cancel.setPreferredSize(new Dimension(150,30));
		cancel.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonsPanel2.add(cancel);
		
	}

	/**
	 * Gets the Dialogs options {@link JPanel}.
	 * 
	 * @return {@link JPanel}
	 */
	public JPanel getOptionsPanel() {
		return optionsPanel;
	}

	/**
	 * Sets the Dialogs options {@link JPanel}.
	 * 
	 * @param optionsPanel
	 */
	public void setOptionsPanel(JPanel optionsPanel) {
		this.optionsPanel = optionsPanel;
	}
	
}
