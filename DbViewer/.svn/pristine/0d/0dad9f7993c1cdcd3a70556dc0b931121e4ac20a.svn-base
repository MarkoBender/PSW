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
 
 package sql;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Types;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import dialogs.AdvancedSearchDialog;
import model.Column;

/**
 * Advanced search component is a component used in {@link AdvancedSearchDialog}. It contains graphical representation of 
 * a single condition for an SQL query.
 * 
 * @author Viktor
 * @author Nikola
 */
public class AdvancedSearchComponent extends JPanel{
	private static final long serialVersionUID = -8255436723373278786L;
	
	/**
	 * Options for the numeric types of data.
	 */
	private static String[] numberOptions = {"=","<",">","<=",">=","<>","LIKE"};
	/**
	 * Options for the textual types of data.
	 */
	private static String[] stringOptions = {"=","<",">","<=",">=","<>","LIKE","BEGINS WITH","ENDS WITH","CONTAINS"};
	/**
	 * Options for the datetime types of data.
	 */
	private static String[] dateOptions = {"=",">=","<=","<>","<",">"};
	/**
	 * Options for the binary types of data.
	 */
	private static String[] blobOptions = {"EXISTS","NOT EXISTS"};
	
	
	/**
	 * Reference to the {@link Column} model.
	 */
	private Column column;
	/**
	 * Reference to the {@link AdvancedSearchDialog}.
	 */
	private AdvancedSearchDialog dialog;
	/**
	 * Combo box for displaying the possible conditions.
	 */
	private JComboBox templateJCB;
	/**
	 * Component which will represent an input depending on the column data type.
	 */
	private JComponent komponenta;
	
	/**
	 * Constructor. Creates and {@link AdvancedSearchComponent} with reference to the model and dialog.
	 * 
	 * @param column {@link Column} model reference for values and type
	 * @param dialog reference to the dialog where the component is being added
	 */
	public AdvancedSearchComponent(Column column, AdvancedSearchDialog dialog){
		this.column = column;
		this.dialog = dialog;
		
		initPanel();
	}
	
	/**
	 * Helper method for initializing the GUI of the component, and deciding the display of the JComponent depending on the
	 * data type of the column.
	 */
	private void initPanel() {
		setPreferredSize(new Dimension(280, 30));
		setMaximumSize(new Dimension(280, 30));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		JLabel label = new JLabel(column.getName());
		
		int sqlType=column.getSqlType();
		
		//ComboBoxItemListener cbil=new ComboBoxItemListener(column,dialog);
		templateJCB=new JComboBox();
		
		if(sqlType==Types.NUMERIC || sqlType==Types.INTEGER){
			templateJCB.setModel(new javax.swing.DefaultComboBoxModel(numberOptions));
			komponenta=new JSpinner();
		}
		else if(sqlType==Types.CHAR || sqlType==Types.VARCHAR){
			templateJCB.setModel(new javax.swing.DefaultComboBoxModel(stringOptions));
			komponenta=new JTextField(15);
		}
		else if(sqlType==Types.TIMESTAMP){
			templateJCB.setModel(new javax.swing.DefaultComboBoxModel(dateOptions));
			komponenta=new JDateChooser();
		} else if(sqlType==Types.BLOB){
			templateJCB.setModel(new javax.swing.DefaultComboBoxModel(blobOptions));
			komponenta=null;
		}
		
		//templateJCB.addItemListener(cbil);		
		add(label);
		add(templateJCB);
		
		if(komponenta!=null)
			add(komponenta);
		
		//dialog.getMap().put(column, komponenta);
		//dialog.getMapForDB().put(column.getCode(), komponenta);
		
		
		JButton remove = new JButton(new ImageIcon("images/icons/remove_record.png"));
		remove.setPreferredSize(new Dimension(15, 15));
		remove.setMaximumSize(new Dimension(15, 15));

		remove.setOpaque(false);
		remove.setContentAreaFilled(false);
		remove.setBorderPainted(false);
		
		Component tmp = this;
		
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.getOptionsPanel().remove(tmp);	
				dialog.getOptionsPanel().repaint();
				dialog.getOptionsPanel().doLayout();
				
				//dialog.getMap().remove(column);
				//dialog.getMapForDB().remove(column.getCode());
			}
		});
		
		add(remove);
	}
	
	/**
	 * Gets the prepared statement for this component. This is the part of the bigger prepared statement for the advanced search
	 * query which will be executed with all the conditions from the {@link AdvancedSearchDialog}.
	 * 
	 * @return String which represents the prepared statement part of this component
	 */
	public String getPreparedStatementPart() {
		StringBuilder sb = new StringBuilder();
		
		//If there is no value, this part of the query will not add any significance.
		if(komponenta!=null)
			if(checkValueDependingOnTheComponent(komponenta) == null || checkValueDependingOnTheComponent(komponenta).length()==0)
				return "";
		
		//If there is no operand, query is not valid.
		if(templateJCB.getSelectedItem().toString().equals(""))
			return "";
		
		sb.append(" "+column.getCode()+" ");
		
		switch(templateJCB.getSelectedItem().toString().toLowerCase()){
		case ("begins with"):
			sb.append(" LIKE ");
			sb.append("?");
			break;
		case ("ends with"):
			sb.append(" LIKE ");
			sb.append("?");
			break;
		case ("contains"):
			sb.append(" LIKE ");
			sb.append("?");
			break;
		case ("exists"):
			sb.append(" IS NOT NULL ");
			break;
		case ("not exists"):
			sb.append(" IS NULL ");
			break;
		default:
			sb.append(" "+templateJCB.getSelectedItem().toString()+" ");
			sb.append("?");
		}

		return sb.toString();
	}
	
	/**
	 * Returns the value of the user input in the component, depending on the type of display component and datatype.
	 * 
	 * @param komponenta Instance of {@link JComponent} used.
	 * @return value as string
	 */
	private String checkValueDependingOnTheComponent(JComponent komponenta){
		
		String retVal = "";
		
		if(komponenta instanceof JSpinner){
			retVal = ((JSpinner)komponenta).getValue().toString();
		}
		else if(komponenta instanceof JTextField){
			retVal = ((JTextField)komponenta).getText();
		}
		else if(komponenta instanceof JDateChooser){
			if(((JDateChooser)komponenta).getDate()!=null)
				retVal = ((JDateChooser)komponenta).getDate().toString();
		}
		
		return retVal;
	}

	/**
	 * Returns {@link JComponent} object.
	 * @return komponenta
	 */
	public JComponent getKomponenta() {
		return komponenta;
	}

	/**
	 * Sets {link JComponent} object.
	 * @param komponenta
	 */
	public void setKomponenta(JComponent komponenta) {
		this.komponenta = komponenta;
	}

	/**
	 * Returns {@link JComboBox} object.
	 * @return templateJCB
	 */
	public JComboBox getTemplateJCB() {
		return templateJCB;
	}

	/**
	 * Sets {@link JComboBox} object.
	 * @param templateJCB
	 */
	public void setTemplateJCB(JComboBox templateJCB) {
		this.templateJCB = templateJCB;
	}
}
