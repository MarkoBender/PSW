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
 
 package interpreter;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.codehaus.jackson.map.ObjectMapper;

import application.Application;
import dialogs.LoginDialog;
import model.DBSchema;
import model.ForeignKeyConstraint;
import model.Table;

/**
 * This class is a model interpreter based on JSON file. It relies on Jackson ObjectMapper class to perform parsing 
 * and mapping JSON to objects. Objects are then processed: necessary fields, relations and attributes are set.
 * 
 * @author Viktor
 * @author Jelena
 * @see ObjectMapper
 */

public class JSONInterpreter implements DBInterpreter{
	/**
	 * Path to JSON file.
	 */
	private String path;
	
	/**
	 * JSONInterpreter constructor.
	 * @param path Path to JSON input file.
	 */
	public JSONInterpreter(String path) {
		this.path = path;
	}
	
	@Override
	public DBSchema interpretSchema() {
		ObjectMapper mapper = new ObjectMapper();
		DBSchema metaSchema = null;
		
		try {
			//if iteration > 1 - stack overflow
			if(Application.testIteration > 1) {
				return null;
			}
			
			metaSchema = mapper.readValue(new File(path), DBSchema.class);	//mapping JSON to objects in metaSchema

			Application.testIteration++;

			
			//Initializing primary and foreign key hash lists - helper structures.
			for(Table t : metaSchema.getTables()){
				t.initializePrimaryKey();
				t.initializeForeignKey();
			}
			
			//Initializing the hashMap as a helper structure in metaSchema.
			metaSchema.initializeMap();
			metaSchema.initializePackageMap();
			
			setParentChildRelations(metaSchema);
			

			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(LoginDialog.getInstance(),Application.getResourceBundle().getString("JSONErrorText"), Application.getResourceBundle().getString("JSONError"),JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(-1);
		}
		
		return metaSchema;
	}
	
	/**
	 * Setting up parent-child relations in Tables in metaSchema.
	 * 
	 * If Table A references Table B and:
	 * (1) the referenced fields (foreign keys) match the primary keys of both A and B.
	 * (2) primary key of A contains the whole primary key of B,
	 * (3) where primary key of A can contain other elements as well
	 * then Table A is a child and Table B is parent.
	 * 
	 * @param metaSchema {@link DBSchema} in which relations need to be set.
	 */
	private void setParentChildRelations(DBSchema metaSchema) {
		//Iterate through every table
		for(Table t : metaSchema.getTableMap().values()){
			//if table has foreign key (if list of foreignKeys is not empty), it is a candidate to be a child
			if(!t.getForeignKeys().isEmpty()){
				//iterate through the list of foreignKeyConstraints to check for the parent-child condition.
				for(ForeignKeyConstraint k : t.getReferences()){
					//load the referenced table - a potential parent
					Table potentialID = metaSchema.getTableMap().get(k.getReferencedTableCode());
					
					//set a variable for easier decomposition of the algorithm
					Boolean check = true;
					
					//check if all the primary keys in the potential parent are referenced from this potential child (1)
					for(String referenced : k.getReferencedColumnCodes()){
						//if a potential parent does not have the referenced column as its primary key, condition (1) fails
						if(!potentialID.getPrimaryKeys().contains(referenced))
							check = false;
					}
					
					//check if all the fields that are refeering are the primary keys in the potential child (2)
					for(String referenced : k.getRefeeringColumnCodes()){
						if(!t.getPrimaryKeys().contains(referenced))
							check = false;
					}
					
					//if table references itself, do not make it a child to itself
					if(t.getCode().equals(potentialID.getCode()))
						check = false;
					
					//if all the conditions are set, potential ID table is the parent, and current table 't' is a child.
					if(check){
						//add potentialID table as a parent to table 't'
						t.getParents().add(potentialID.getCode());	
						//add table 't' as a child to the table 'potentialID'
						metaSchema.getTableMap().get(potentialID.getCode()).getChildren().add(t.getCode());
						
						//potentialID.getChildren().add(t.getCode());
					}

				}
			}
		}
	}
}
