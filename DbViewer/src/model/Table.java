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
 
 package model;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


/**
 * This class represents the structure of Table in relational model.
 * 
 * @author Viktor
 * @author Jelena
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Table{
	/**
	 * Unique table identifier.
	 */
	private String code; 
	/**
	 * Table name.
	 */
	private String name; 
	/**
	 * {@link Column} collection of this table.
	 */
	private List<Column> columns; 
	/**
	 * {@link ForeignKeyConstraint} collection of this table.
	 */
	private List<ForeignKeyConstraint> references = new ArrayList<>();
	/**
	 * Name of the {@link Package} where this table belongs.
	 */
	private String packageName; 
	/**
	 * Semantic column codes contain the {@link Column} codes which carry the semantic of this table.
	 */
	private List<String> semanticColumnCodes;
	/**
	 * Set of primary keys for current table.
	 */
	private Set<String> primaryKeys = new HashSet<String>();
	/**
	 * Set of foreign keys for current table.
	 */
	private Set<String> foreignKeys = new HashSet<String>();
	/**
	 * Set of children table codes.
	 */
	private Set<String> children = new HashSet<>();
	/**
	 * Set of parent table codes.
	 */
	private Set<String> parents = new HashSet<>();
	
	/**
	 * Default constructor.
	 */
	public Table() {
		
	}
	
	/**
	 * Method for setting up the set of primary keys. Invocation is needed only once after setting all the necessary {@link Column} objects in the list.
	 */
	public void initializePrimaryKey() {
		for(Column c : columns){
			if(c.getConstraint().getPrimaryKey())
				primaryKeys.add(c.getCode());
		}
	}
	
	/**
	 * Method for setting up the set of foreign keys. Invocation is needed only once after setting all the necessary {@link Column} objects in the list.
	 */
	public void initializeForeignKey() {
		for(Column c : columns){
			if(c.getConstraint().getForeignKey()) {
				foreignKeys.add(c.getCode());
				
			}
		}
	}
	
	/**
	 * Return foreign key set. 
	 * @return foreignKeys
	 */
	public Set<String> getForeignKeys() {
		return foreignKeys;
	}
	/**
	 * Sets foreign key set.
	 * @param foreignKeys
	 */
	public void setForeignKeys(HashSet<String> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}
	/**
	 * Returns children set.
	 * @return children
	 */
	public Set<String> getChildren() {
		return children;
	}
	/**
	 * Sets children set.
	 * @param children
	 */
	public void setChildren(HashSet<String> children) {
		this.children = children;
	}
	/**
	 * Returns parent set.
	 * @return parents
	 */
	public Set<String> getParents() {
		return parents;
	}
	/**
	 * Sets parent set.
	 * @param parents
	 */
	public void setParents(HashSet<String> parents) {
		this.parents = parents;
	}
	/**
	 * Return primary key set.
	 * @return primaryKeys
	 */
	public Set<String> getPrimaryKeys() {
		return primaryKeys;
	}
	/**
	 * Set primary key set.
	 * @param primaryKeys
	 */
	public void setPrimaryKeys(HashSet<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	/**
	 * Returns table name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets table name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns collection of columns.
	 * @return columns
	 */
	public List<Column> getColumns() {
		return columns;
	}
	/**
	 * Sets collection of columns.
	 * @param columns
	 */
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	/**
	 * Returns table code.
	 * @return code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Sets table code
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * Returns {@link ForeignKeyConstraint} collection.
	 * @return references
	 */
	public List<ForeignKeyConstraint> getReferences() {
		return references;
	}
	/**
	 * Sets {@link ForeignKeyConstraint} collection.
	 * @param references
	 */
	public void setReferences(List<ForeignKeyConstraint> references) {
		this.references = references;
	}
	/**
	 * Returns package code in which is current table contained.
	 * @return packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * Sets package code in which is current table contained.
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Returns collection of semantic column codes.
	 * @return semanticColumnCodes
	 */
	public List<String> getSemanticColumnCodes() {
		return semanticColumnCodes;
	}
	/**
	 * Sets collection of semantic column codes.
	 * @param semanticColumnCodes
	 */
	public void setSemanticColumnCodes(List<String> semanticColumnCodes) {
		this.semanticColumnCodes = semanticColumnCodes;
	}
	
	/**
	 * Changes column names depending on {@link ResourceBundle}.
	 * @param rb
	 */
	public void changeColumnNames(ResourceBundle rb) {
		for(Column c : columns) {
			c.setName(rb.getString(c.getCode()));
		}
	}
	/*public List<String> getChildrenAsList() {
		List<String> childrenTables = new ArrayList<>();
		childrenTables.addAll(getChildren()); 
		return childrenTables; 
	}*/
}
