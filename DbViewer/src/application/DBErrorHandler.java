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
 
package application;

import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import gui.MainFrame;
import model.Column;
import model.Table;

/***
 * Handles {@link SQLException}. 
 * @author Nikola
 * @author Marko
 * 
 */

public class DBErrorHandler {
	
	/**
	 * Reference to the singleton object.
	 */
	private static DBErrorHandler instance=null;
	
	/**
	 * Returns reference to singleton object.
	 * @return {@link DBErrorHandler} instance
	 */
	public static DBErrorHandler getInstance(){
		if(instance==null)
			instance=new DBErrorHandler();
		return instance;
	}
	
	/**
	 * Method for showing {@link JOptionPane} with predefind error text, depending on passed paramethers.
	 * @param e - catched {@link SQLException}
	 * @param table - in which {@link Table} was catched {@link SQLException}
	 */
	public static void showSqlErrorMessage(SQLException e, Table table){
		

		int errorCode = e.getErrorCode();
		List<Column> columns = table.getColumns();
		String title;
		StringBuilder message;
		switch(errorCode){
		case 547:
				if(e.getMessage().contains("DELETE")){
					title = "Error code: " + e.getErrorCode();
					message = new StringBuilder(Application.getResourceBundle().getString("Error1"));
					String errorMessage = e.getMessage();
					List<Table> tables = Application.getSchema().getTables();
					for(Table t : tables){
						if(errorMessage.contains(t.getCode())){
							message.append("     tabela:");
							message.append(t.getName());
							message.append("\n");
							for(Column c : t.getColumns()){
								if(errorMessage.contains(c.getCode())){
									message.append("     obelezje: ");
									message.append(c.getName());
									
								}
							}
						}
					}
				}else{
					title = "Error code: " + e.getErrorCode();
					message = new StringBuilder(Application.getResourceBundle().getString("Error6"));
					String errorMessage = e.getMessage();
				}
				break;
		case 2627:
			title = "Error code: " + e.getErrorCode();
			message = new StringBuilder(Application.getResourceBundle().getString("Error2"));
			for(Column c : columns){
				if(c.getConstraint().getPrimaryKey()){
					message.append("    -");	
					message.append(c.getName());
					message.append("\n");
				}
			}
			break;
		case 515:
			title = "Error code: " + e.getErrorCode();
			message = new StringBuilder(Application.getResourceBundle().getString("Error3"));
			for(Column c : columns){
				if(c.getConstraint().getMandatory()){
					message.append("    -");	
					message.append(c.getName());
					message.append("\n");
				}
			}
			break;
		case 102:
			title = "Error code: " + e.getErrorCode();
			message = new StringBuilder(Application.getResourceBundle().getString("SyntaxError"));
			message.append(": "+e.getMessage().split("'")[1]+".");
			break;
		case 257:
			title = "Error code: " + e.getErrorCode();
			message = new StringBuilder(Application.getResourceBundle().getString("Error5"));
			break;
		case 156:
			title = "Error code: " + e.getErrorCode();
			message = new StringBuilder(Application.getResourceBundle().getString("SyntaxError"));
			message.append(": "+e.getMessage().split("'")[1]+".");
			break;
		default:
			title = "Error code: " + e.getErrorCode();
			if(e.getMessage().contains("Invalid parameter")) {
				message = new StringBuilder(Application.getResourceBundle().getString("Error4"));
				message.append(". \n Parameter number "+e.getMessage().split("index")[1]+" must not be empty");
			} else {
				message = new StringBuilder(Application.getResourceBundle().getString("Error4"));
			}
		}
		JOptionPane.showMessageDialog(MainFrame.getInstance(),message.toString(), title,JOptionPane.ERROR_MESSAGE);
	}
}
