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

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import application.Application;
import dialogs.AdvancedSearchDialog;
import model.Column;
import model.DBSchema;
import model.Table;

/**
 * Class for creating, preparing and executing different types of queries with database.
 * @author Nikola
 * @author Viktor
 *
 */
public class RecordsManagement {
	
	/**
	 * uspesno - attribute which shows, was there error while query was executing 
	 */
	public boolean uspesno=true;
	
	/**
	 * table - attribute which shows with which table in database are we working with
	 */
	public Table table;
	
	/**
	 * newData - attribute which contains column codes and JComponent object with its value (sent from @see AddOrModifyDialog)
	 */
	public HashMap<String,JComponent> newData;
	
	/**
	 * constraints - attribute which contains column codes and its real values (sent from @see AddOrModifyDialog)
	 */
	public HashMap<String,Object> constraints;
	
	/**
	 * connection - attribute for setting connection with database
	 */
	public Connection connection=null;
	
	/**
	 * statement - query which we execute when we want to work with database
	 */
	public PreparedStatement statement=null;
	
	/**
	 * resultSet - values returned by executing of some queries
	 */
	public ResultSet resultSet=null;
	
	/**
	 * query - text representation of query statement
	 */
	public StringBuilder query=null;
	
	/**
	 * data - processed resultSet data values
	 */
	public Vector<Vector<Object>> data=null;
	
	/**
	 * headers - processed table colum names
	 */
	public Vector<String> headers=null;
	
	/**
	 * recordValues - array of strings for every single value from database, except image type
	 */
	private ArrayList<String> recordValues=new ArrayList<String>();
	
	/**
	 * Getter for recordValues
	 * @return recordValues
	 */
	public ArrayList<String> getRecordValues() {
		return recordValues;
	}
	
	/**
	 * recordImages - array of bites for every single image
	 */
	private ArrayList<byte[]> recordImages=new ArrayList<byte[]>();
	
	/**
	 * Getter for recordImages
	 * @return recordImages
	 */
	public ArrayList<byte[]> getRecordImages() {
		return recordImages;
	}
	
	/**
	 * cbi - array of ComboBoxItems (model for foreign key values)
	 */
	private ArrayList<ComboBoxItem> cbi=new ArrayList<ComboBoxItem>();
	/**
	 * Getter for cbi
	 * @return cbi
	 */
	public ArrayList<ComboBoxItem> getCbi() {
		return cbi;
	}
	
	/**
	 * List of prepared queries from {@link AdvancedSearchDialog}.
	 */
	private ArrayList<String> queryList;
	/**
	 * Array of components from {@link AdvancedSearchDialog} which will carry information about queries, contained in query list.
	 */
	private Component[] queryComponents;
	
	/**
	 * Constructor. Depending on TypesOfActions passed values calls different type of functions.
	 * @param table - with which table in database are we working with
	 * @param toa - @see TypesOfActions
	 * @param newData - data which we want to add/modify to/in database
	 * @param constraints - constraints under which are we working (which data we want to update)
	 */
	public RecordsManagement(Table table, TypesOfActions toa, HashMap<String, JComponent> newData, HashMap<String,Object> constraints){
		super();
		this.table=table;
		this.newData=newData;
		this.constraints=constraints;
		connection = DbConnection.getInstance().getConnection();
		switch(toa){
			case SEARCH_ALL_RECORDS:
				executeSearchAllRecords();
				break;
			case SEARCH_SPECIFIC_RECORD:
				executeSearchSpecificRecord();
				break;
			case REMOVE_RECORD:
				executeRemoveRecord();
				break;
			case ADD_RECORD:
				executeAddRecord();
				break;
			case MODIFY_RECORD:
				executeModifyRecord();
				break;
			case GET_VALUES_BY_FK:
				executeGetValuesByFK();
				break;
			case SEARCH_CHILDREN_BY_FK:
				executeSearchChildrenByFK();
				break;
			//case ADVANCED_SEARCH_RECORDS:
			//	executeAdvancedSearchRecords();
			//	break;
			default:
				break;
		}

	}
	
	/**
	 * Constructor used for creating an instance with passing components which contain information about the query from the
	 * {@link AdvancedSearchDialog}, or any list of supported components: {@link AdvancedSearchComponent}, {@link AdvancedSearchLogicalOperator}.
	 * 
	 * @param queryComponents Array of supported components which contain information about the query
	 * @param table Table model which provides the information about the columns for executing the query
	 */
	public RecordsManagement(Component[] queryComponents, Table table){
		
		this.table=table;
		this.queryComponents = queryComponents;
		
		connection = DbConnection.getInstance().getConnection();
		queryList = new ArrayList<>();
		
		// place all the components in a single list for easier passing through the methods
		for(Component c : queryComponents) {
			if(c instanceof AdvancedSearchLogicalOperator) {
				
				queryList.add(((AdvancedSearchLogicalOperator)c).getSelectedOperator());
			} else if(c instanceof AdvancedSearchComponent) {
				String s = ((AdvancedSearchComponent) c).getPreparedStatementPart();
				if(s!=null && s.length()>0) {
					queryList.add(s);
				}
			}
		}
		
	}
	
	/**
	 * Method for preparing, creating and executing simple SELECT statement (all values from specific table)
	 * 
	 */
	public void executeSearchAllRecords(){
		query=new StringBuilder("SELECT ");
		for(int i=0; i<table.getColumns().size(); i++){
			Column column=table.getColumns().get(i);
			if(column.getSqlType()!=Types.BLOB)
				query.append(column.getCode());
			else
				query.append("CASE WHEN "+column.getCode()+" IS NOT NULL THEN 'True' ELSE 'False' END");
			if(i!=table.getColumns().size()-1){
				query.append(", ");
			}
		}
		query.append(" FROM "+table.getCode());
		
		try {
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
		} catch (SQLException e1) {
			//Application.showSqlErrorMessage(e1, table);
			Application.getDbeh().showSqlErrorMessage(e1, table);
		}
	}
	
	/**
	 * Method for preparing, creating and executing specific SELECT statement (specific values from specific table under some constraints)
	 * Generating values for to @see AddOrModifyDialog (ModifyDialog)
	 */
	public void executeSearchSpecificRecord(){
		StringBuilder query = new StringBuilder("SELECT * FROM " + table.getCode()+" WHERE");
		int k=0;
		for (String key : constraints.keySet()) {
		    query.append(" "+key+"=? ");
		    if(k!=constraints.size()-1)
				query.append("AND");
		    k++;
		    
		}
		int brojac=1;
		try {
			statement = connection.prepareStatement(query.toString());
			for (String key : constraints.keySet()) {
				statement.setObject(brojac, constraints.get(key));
				brojac++;
			}
			resultSet = statement.executeQuery();
			getResultSetValues();
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
	}
	
	/**
	 * Method for processing resultSet values and putting them into arrays-recordValues and recordImages
	 */
	public void getResultSetValues(){
		try {
			while (resultSet.next()) {
				for(int j=1; j<=table.getColumns().size();j++){
					Column column=table.getColumns().get(j-1);
					if(resultSet.getObject(j)==null){
						recordValues.add("");
					}
					else{
						recordValues.add(resultSet.getObject(j).toString());
						if(column.getSqlType()==Types.BLOB){
							byte[] imageBytes=resultSet.getBytes(j);
							recordImages.add(imageBytes);
						}
					}
				}
			}
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
	}
	
	/**
	 * Prepare, make and execute remove statement
	 */
	public void executeRemoveRecord(){
		query=new StringBuilder("DELETE FROM "+table.getCode()+" WHERE ");
		try {
			int counter=0;
			for(String key : constraints.keySet()){
				query.append(key+"=?");
				if(counter != constraints.size()-1){
					query.append(" AND ");
				}
				counter++;;
			}
			statement=connection.prepareStatement(query.toString());
			counter=1;
			for(String key : constraints.keySet()){
				statement.setObject(counter, constraints.get(key));
				counter++;
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
	}
	
	/**
	 * Method for preparing, making and executing simple INSERT statement (adding specific values to specific table)
	 * 
	 */
	public void executeAddRecord(){
		StringBuilder query = new StringBuilder("INSERT INTO " + table.getCode());
		query.append("(");
		List<Column> columns = table.getColumns();
		for(int i = 0; i<columns.size(); i++){
			query.append(columns.get(i).getCode());
			if(i != columns.size()-1)
				query.append(",");
		}
		query.append(") ");
		query.append("VALUES (");
		
		for(int i = 0; i<columns.size(); i++){
			query.append("?");
			if(i != columns.size()-1)
				query.append(",");
		}
		query.append(")");
		
		InputStream fis=null;
		try {
			statement = connection.prepareStatement(query.toString());
			for(int i = 0; i<columns.size(); i++){
				int columnType = columns.get(i).getSqlType();
				JComponent input = newData.get(columns.get(i).getCode());
				
				if(input instanceof JTextField){
					JTextField textInput = (JTextField) input;
					String value = textInput.getText();
					if(value.equals(""))
						statement.setNull(i+1, columnType);
					else
						statement.setObject(i+1, value,columnType);
				}
				else if(input instanceof JDateChooser){
					JDateChooser dc=(JDateChooser) input;
					if(dc.getDate()!=null) {	//dodat uslov jer može da puca ako nije mandatory polje - 27.12.2016.
						java.sql.Date date = new java.sql.Date(dc.getDate().getTime());
						if(date==null)
							statement.setNull(i+1, columnType);
						else
							statement.setObject(i+1, date, columnType);
					} else {
						statement.setNull(i+1, columnType);
					}
				}
				else if(input instanceof JComboBox){
					JComboBox jcb=(JComboBox) input;
					Object obj=jcb.getSelectedItem();
					String value=((ComboBoxItem)obj).getValue();
					obj=(Object)value;
					if(value.equals(""))
						statement.setNull(i+1, columnType);
					else
						statement.setObject(i+1, obj, columnType);
				}
				else if(input instanceof JPanel){
					JPanel panel=(JPanel) input;
					JLabel label=(JLabel) panel.getComponent(0);
					String value = label.getText();
					ImageIcon imageIcon=(ImageIcon)label.getIcon();
					if (value.equals(""))
						statement.setNull(i+1, columnType);
					else{
						Image img=imageIcon.getImage();
						BufferedImage bimage = new BufferedImage(img.getWidth(null), 
				                    img.getHeight(null), BufferedImage.TYPE_INT_ARGB);						
						bimage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
						File file = new File(i+".jpg");
					    ImageIO.write(bimage, "jpg", file);
					    fis = new FileInputStream(file);
						statement.setBinaryStream(i+1, fis, (int)file.length());
					}
				}
			}
			statement.executeUpdate();
			if(fis!=null)
				fis.close();
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			 Application.getDbeh().showSqlErrorMessage(e, table);
			uspesno=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for preparing, making and executing simple UPDATE statement (modifying specific values to specific table under specific constraints-Primary Keys)
	 * 
	 */
	public void executeModifyRecord(){
		List<Column> columns = table.getColumns();
		StringBuilder query = new StringBuilder("UPDATE " + table.getCode()+ " SET");
		int n=0;
		for (String key: newData.keySet()){
			query.append(" "+key+"=?");
			if(n!=newData.size()-1)
				query.append(",");
			n++;
		}
		query.append(" WHERE");
		n=0;
		for (String key: constraints.keySet()){
			query.append(" "+key+"=? ");
			if(n!=constraints.size()-1)
				query.append("AND");
			n++;
		}
		InputStream fis=null;
		int brojac=1;
		try {
			statement = connection.prepareStatement(query.toString());
			for (String key: newData.keySet()){
				JComponent input=newData.get(key);
				int columnType=-1;
				for(Column c:columns){
					if(key.equals(c.getCode())){
						columnType=c.getSqlType();
						break;
					}
				}
				if(input instanceof JTextField){
					JTextField textInput = (JTextField) input;
					String value = textInput.getText();
					if(value.equals(""))
						statement.setNull(brojac, columnType);
					else
						statement.setObject(brojac, value, columnType);
				}
				else if(input instanceof JDateChooser){
					JDateChooser dc=(JDateChooser) input;
					java.sql.Date date = new java.sql.Date(dc.getDate().getTime());
					if(date==null)
						statement.setNull(brojac, columnType);
					else
						statement.setObject(brojac, date, columnType);
				}
				else if(input instanceof JComboBox){
					JComboBox jcb=(JComboBox) input;
					Object obj=jcb.getSelectedItem();
					String value=((ComboBoxItem)obj).getValue();
					obj=(Object)value;
					if(value.equals(""))
						statement.setNull(brojac, columnType);
					else
						statement.setObject(brojac, obj, columnType);
				}
				else if(input instanceof JPanel){
					JPanel panel=(JPanel) input;
					JLabel label=(JLabel) panel.getComponent(0);
					String value = label.getText();
					ImageIcon imageIcon=(ImageIcon)label.getIcon();
					if (value.equals(""))
						statement.setNull(brojac, columnType);
					else{
						Image img=imageIcon.getImage();
						BufferedImage bimage = new BufferedImage(img.getWidth(null), 
				                    img.getHeight(null), BufferedImage.TYPE_INT_ARGB);						
						bimage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
						File file = new File(brojac+".jpg");
					    ImageIO.write(bimage, "jpg", file);
					    fis = new FileInputStream(file);
						statement.setBinaryStream(brojac, fis, (int)file.length());
					}
				}
				brojac++;
			}
			
			for (String key: constraints.keySet()){
				int columnType=-1;
				for(Column c:columns){
					if(key.equals(c.getCode())){
						columnType=c.getSqlType();
						break;
					}
				}
				String value=constraints.get(key).toString();
				if(value.equals(""))
					statement.setNull(brojac, columnType);
				else
					statement.setObject(brojac, value);
				brojac++;
			}
			statement.executeUpdate();
			if(fis!=null)
				fis.close();
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
			uspesno=false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for preparing, making and executing specific SELECT statement.
	 * Getting values for specific foreign key.
	 * Creating @see ComboBoxItem objects and adding them to the array.
	 */
	public void executeGetValuesByFK(){
		ArrayList<String> texts=new ArrayList<String>();
		ArrayList<String> values=new ArrayList<String>();
		int selectedIndex=-1;
		
		String referencedColumnCode="";
		String referencedTableCode="";
		for(String s: constraints.keySet()){
			referencedColumnCode=s;
			referencedTableCode=(String) constraints.get(s);
		}
		
		DBSchema dbs=Application.getSchema();
		
		Table tabela=dbs.getTableMap().get(referencedTableCode);
		
		ArrayList<String> nazivi=(ArrayList<String>) tabela.getSemanticColumnCodes();
		
		StringBuilder kveri=new StringBuilder("SELECT");
		int duyina=0;
		if(nazivi!=null && nazivi.size()>0){
			duyina=nazivi.size();
			for(int j=0; j<nazivi.size(); j++){
				kveri.append(" "+nazivi.get(j));
				if(j!=nazivi.size()-1)
					kveri.append(",");
			}
		}else{
			Set<String> kljucevi=tabela.getPrimaryKeys();
			duyina=kljucevi.size();
			int brojac=0;
			for(String s: kljucevi){
				kveri.append(" "+s);
				if(brojac!=kljucevi.size()-1)
					kveri.append(",");
				brojac++;
			}
		}
		kveri.append(" FROM "+referencedTableCode+" ORDER BY "+referencedColumnCode);
		try {
			statement = connection.prepareStatement(kveri.toString());
			
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				StringBuilder sb=new StringBuilder();
				for(int m=1; m<=duyina;m++){
					sb.append(resultSet.getString(m));
					if(m!=duyina)
						sb.append("-");
				}
				texts.add(sb.toString());
			}

		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, tabela);
		}
		
		StringBuilder query = new StringBuilder("SELECT " + referencedColumnCode + " FROM " +
				referencedTableCode +" ORDER BY "+referencedColumnCode);
		
		statement = null;
		resultSet = null;
		try {
			statement = connection.prepareStatement(query.toString());
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				values.add(resultSet.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		for(int n=0;n<texts.size();n++){
			cbi.add(new ComboBoxItem(texts.get(n),values.get(n)));
		}
	}
	/**
	 * Method for preparing, creating and executing specific SELECT statement (specific values from specific table under some constraints)
	 * Generating values to children which contains parent's values as Foreign Key.
	 */

	public void executeSearchChildrenByFK(){
		StringBuilder query = new StringBuilder("SELECT * FROM " + table.getCode()+" WHERE");
		int k=0;
		for (String key : constraints.keySet()) {
		    query.append(" "+key+"=? ");
		    if(k!=constraints.size()-1)
				query.append("AND");
		    k++;   
		}
		
		k=1;
		try {
			statement = connection.prepareStatement(query.toString());
			for (String key : constraints.keySet()) {
				statement.setObject(k, constraints.get(key));
				k++;
			}
			
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
	}
	
	/**
	 * Creates a prepared statement from the elements of the advanced search dialog, populates the prepared statement 
	 * and executes the query, setting the global class result set.
	 */
	public void executeAdvancedSearchRecords() {
	
		if(queryList.isEmpty())
			return;
		
		StringBuilder query = new StringBuilder("SELECT ");
		
		for(int i=0; i<table.getColumns().size(); i++){
			Column column=table.getColumns().get(i);	
			if(column.getSqlType()!=Types.BLOB)
				query.append(column.getCode());
			else
				query.append("CASE WHEN "+column.getCode()+" IS NOT NULL THEN 'True' ELSE 'False' END");
				if(i!=table.getColumns().size()-1){
					query.append(", ");
				}
		}
		
		query.append(" FROM " + table.getCode()+" WHERE ");
		
		for (String element : queryList) {
			query.append(" "+element+" ");
		}
		
		try {
			statement = connection.prepareStatement(query.toString());
			//System.out.println(statement.toString());	
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			//e.printStackTrace();
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
		
		int entityCounter = 0, index = 1;
		
		// iterating through advanced search dialog components
		for(Component c : queryComponents) {
			if(c instanceof AdvancedSearchComponent) {
				entityCounter++;
				
				JComponent komponenta = ((AdvancedSearchComponent) c).getKomponenta();
				JComboBox uslov = ((AdvancedSearchComponent) c).getTemplateJCB();
				
				if(komponenta instanceof JSpinner){
					try {
						statement.setInt(index, (int)((JSpinner)komponenta).getValue());
					} catch (SQLException e) {
						//Application.showSqlErrorMessage(e, table);
						//e.printStackTrace();
						Application.getDbeh().showSqlErrorMessage(e, table);
					}
				}
				else if(komponenta instanceof JTextField){
					try {
						switch(uslov.getSelectedItem().toString().toLowerCase()){
						case ("begins with"):
							statement.setString(index, ((JTextField)komponenta).getText()+"%");
							break;
						case ("ends with"):
							statement.setString(index, "%"+((JTextField)komponenta).getText());
							break;
						case ("contains"):
							statement.setString(index, ("%"+((JTextField)komponenta).getText()+"%"));
							break;
						default:
							statement.setString(index, ((JTextField)komponenta).getText());
						}
					} catch (SQLException e) {
						//Application.showSqlErrorMessage(e, table);
						//e.printStackTrace();
						Application.getDbeh().showSqlErrorMessage(e, table);
					}
				}
				else if(komponenta instanceof JDateChooser){
					if(((JDateChooser)komponenta).getDate()!=null){
						java.util.Date date = ((JDateChooser)komponenta).getDate();
						Date sqlDate = new Date(date.getTime());
						
						try {
							statement.setDate(index, sqlDate);
						} catch (SQLException e) {
							//Application.showSqlErrorMessage(e, table);
							Application.getDbeh().showSqlErrorMessage(e, table);
							//e.printStackTrace();
						}
					}
				}
				index++;
			}
		}
		
		if(entityCounter>0)
			try {
				//System.out.println(statement);
				resultSet = statement.executeQuery();
			} catch (SQLException e) {
				//Application.showSqlErrorMessage(e, table);
				Application.getDbeh().showSqlErrorMessage(e, table);
				//e.printStackTrace();
			}
	}
	
	/**
	 * Processing resultSet values. Converting them into Vector<Vector<Object>>
	 * @return data
	 */
	
	public Vector<Vector<Object>> getData(){
		List<Column> columns = table.getColumns();
		int columnCount = columns.size();
		data = new Vector<Vector<Object>>();
	    int counter = 1;
	    try {
	    	if(resultSet == null)
	    		return data;
			while (resultSet.next()) {
			    Vector<Object> vector = new Vector<Object>();
			    vector.add(counter);
			    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
		    		vector.add(resultSet.getObject(columnIndex));
			    }
			    data.add(vector);
			    counter++;
			}
		} catch (SQLException e) {
			//Application.showSqlErrorMessage(e, table);
			Application.getDbeh().showSqlErrorMessage(e, table);
		}
		return data;
	}
	
	/**
	 * Processing table column names. Converting them into Vector<String>
	 */
	public Vector<String> getHeaders(){
		List<Column> columns = table.getColumns();
		headers = new Vector<String>();
	    headers.add("");
	    for (Column column : columns) {
	    	headers.add(column.getName());
	    }
		return headers;	
	}
}