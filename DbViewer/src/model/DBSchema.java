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

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * This class is a model of relational database schema. It contains all the {@link #tables}
 * as well as the package structure which are described in the model.
 * 
 * @author Viktor
 * @author Jelena
 * @author Nikola
 */
@JsonIgnoreProperties(ignoreUnknown = true)	
public class DBSchema {
	/**
	 * Name of the schema.
	 */
	private String schemaName; 
	/**
	 * {@link Table} collection of this schema.
	 */
	private List<Table> tables;
	/**
	 * {@link Package} collection of this schema.
	 */
	private List<Package> packages; 
	/**
	 * Structure for mapping {@link Table} with its unique code.
	 */
	private HashMap<String, Table> tableMap;
	/**
	 * Structure for mapping {@link Package} with its unique code.
	 */
	private HashMap<String, Package> packageMap; 
	
	/**
	 * Default constructor.
	 */
	public DBSchema() {
		
	}
	/**
	 * Returns schema name.
	 * @return schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}
	/**
	 * Sets schema name.
	 * @param schemaName
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	/**
	 * Returns {@link Table} collection.
	 * @return tables
	 */
	public List<Table> getTables() {
		return tables;
	}
	/**
	 * Sets {@link Table} collection.
	 * @param tables
	 */
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	/**
	 * Returns structure for mapping {@link Table} with its unique code.
	 * @return tableMap
	 */
	public HashMap<String, Table> getTableMap() {
		return tableMap;
	}
	/**
	 * Returns structure for mapping {@link Package} with its unique code.
	 * @return
	 */
	public HashMap<String, Package> getPackageMap() {
		return packageMap;
	}
	/**
	 * Sets structure for mapping {@link Package} with its unique code.
	 * @param packageMap
	 */
	public void setPackageMap(HashMap<String, Package> packageMap) {
		this.packageMap = packageMap;
	}

	/**
	 * This method converts {@code List<Table> tables} to {@code HashMap<String, Table>}, with {@link Table} being identified
	 * with its unique code.
	 * 
	 */
	
	public void initializeMap(){
		tableMap = new HashMap<>();
		
		for(Table t : tables){
			tableMap.put(t.getCode(), t);
		}
	}
	/**
	 * This method converts {@code List<Package> packages} to {@code HashMap<String, Package>}, with {@link Package} being identified
	 * with its unique code.
	 * 
	 */
	public void initializePackageMap(){
		packageMap = new HashMap<>();
		
		for(Package p : packages){
			//packageMap.put(p.getPackageName(), p);
			packageMap.put(p.getPackageCode(), p);
		}
	}
	/**
	 * Returns structure for mapping {@link Package} with its unique code.
	 * @return packages
	 */
	public List<Package> getPackages() {
		return packages;
	}
	
	/**
	 * Sets {@link Package} collection.
	 * @param packages
	 */
	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	/**
	 * Sets structure for mapping {@link Table} with its unique code.
	 * @param tableMap
	 */
	public void setTableMap(HashMap<String, Table> tableMap) {
		this.tableMap = tableMap;
	}

	@Override
	public String toString() {
		return "MetaSchema [schemaName=" + schemaName + ", tables=" + tables + ", packages=" + packages +"]";
	}
	
	/**
	 * Returns {@link Table} by its name.
	 * @param tableName
	 * @return
	 */
	public Table getTableByName(String tableName){
		return tableMap.get(tableName);
	}
	
}
