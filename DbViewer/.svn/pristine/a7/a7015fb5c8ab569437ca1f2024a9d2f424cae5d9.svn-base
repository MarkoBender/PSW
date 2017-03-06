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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Column;
import model.DBSchema;
import model.DataConstraint;
import model.DataType;
import model.ForeignKeyConstraint;
import model.Package;
import model.Table;
import model.TableConstraint;
import sql.DbConnection;

/**
 * This class is a model interpreter based on getting database informations with {@link DatabaseMetaData}.
 * Data is then processed: necessary tables, fields, relations and attributes are set.
 * 
 * @author Jelena
 * @author Nikola
 * @see DatabaseMetaData
 */

public class SQLInterpreter implements DBInterpreter {
/**
 * Used to control weather or not debug prints are displayed.
 */
	private static final boolean DEBUG = false; 
	/**
	 * Prints text on console only if debug is enabled.
	 * @param text
	 */
	private static void print(String text) {
		if(DEBUG) {
			System.out.println(text);
		}
	}
	/**
	 * Instance of {@link Connection} class.
	 */
	private Connection connection=null;
		
	public static void main(String[] args) {
		
		SQLInterpreter interpreter = new SQLInterpreter();
		interpreter.interpretSchema(); 	
	}
	/**
	 * Fills {@link DBSchema} instance with data from database.
	 * @param metaSchema - DBSchema containing all tables, columns, etc, from database.
	 */
	private void getMetadata(DBSchema metaSchema) {
		print("getMetadata usao, trazi se ime!");
		String schemaName = getMetaSchemaName(); 
		print("Ime metaseme je:" + schemaName); 
		if(schemaName == null) {
			System.out.println("Database connection failed to acquire schema name");
			throw new IllegalStateException("Database connection failed to acquire schema name"); 
		}
		
		print("getPackages usao, traze se paketi!"); 
		List<Package> packages = getPackages(); 		
		
		//<Parent, Child>
		packages = getParentChild(packages); 
				
		print("Nasao pakete! getTables usao, traze se tabele!"); 
		List<Table> tables = getTables(); 
		for(Table t : tables) {
			List<Column> columnsForTable = getColumnsForTable(t); 
			t.setColumns(columnsForTable);	
			//t.setColumns(new ArrayList<Column>());;
			//System.out.println("PO_OZNAKA U TABELI: " + t.getPackageName());
		}
		print("Nasao tabele!"); 
		
		metaSchema.setSchemaName(schemaName);
		metaSchema.setPackages(packages);
		metaSchema.setTables(tables);
		
		//System.out.println("KRAJ!");
	}
/**
 * Gets sub-packages for specified packages.
 * @param packages2
 * @return
 */
	private List<Package> getParentChild(List<Package> packages2) {
		Map<String, String> mapa = new HashMap<>(); 
		String sql = "SELECT * FROM struktura_podsistema"; 
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null;
		ArrayList<Package> tmp = new ArrayList<>();
		try{
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				String oznakaNadredjenog = rs.getString("po_oznaka");
				print("Oznaka nadredjenog paketa je:" + oznakaNadredjenog); 				
				String oznakaPodredjenog = rs.getString("pod_po_oznaka"); 
				print("Oznaka podredjenog paketa je:" + oznakaPodredjenog); 
				for(Package p : packages2) {
					if(p.getPackageCode().equals(oznakaNadredjenog)) {
						//System.out.println("DOBAR NADREDJENI!!!!!!!!!!!!!!");
						for(Package pp : packages2) {
							if(pp.getPackageCode().equals(oznakaPodredjenog)) {
								//System.out.println("DOBAR PODREDJENI!!!!!!!!!!!!!!");
								tmp.add(pp);
								p.getSubPackages().add(pp); 
							}
						}
					}
				}
			} 
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(Package p : tmp) 
			packages2.remove(p);
	
		return packages2;
	}

/**
 * Uses database meta-data in order to 
 * retrieve database schema name.
 * @return
 */
	private String getMetaSchemaName() {
		String schemaName = null; 
		DbConnection connection = null; 
		DatabaseMetaData dbmd = null; 
		ResultSet rs = null; 
		
		try{
			connection = (DbConnection) DbConnection.getInstance(); 
			dbmd = connection.getConnection().getMetaData();   
			
			rs = dbmd.getTables(null, null, null, null); 
			while(rs.next()) {
				print("Setovanje imena meta seme!"); 
				schemaName = rs.getString("TABLE_CAT");
				print("Ime metaseme je:" + schemaName); 
			}		
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return schemaName; 	
	}
	/**
	 * Gets all packages from database.
	 * @return
	 */
	private List<Package> getPackages() {
		print("getPackages usao"); 
		List<Package> packages = new ArrayList<>(); 
		String sql = "SELECT * FROM podsistem"; 
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 
		try{
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				model.Package packageSchema = new Package(); 
				packages.add(packageSchema); 
				String oznakaPaketa = rs.getString("po_oznaka");
				print("Oznaka paketa je:" + oznakaPaketa); 
				packageSchema.setPackageCode(oznakaPaketa);
				
				String nazivPaketa = rs.getString("po_naziv"); 
				print("Naziv paketa je:" + nazivPaketa); 
				packageSchema.setPackageName(nazivPaketa);
				List<Package> subPackages = new ArrayList<>(); 
				packageSchema.setSubPackages(subPackages); 
			} 
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return packages; 
	}
/**
 * Gets all tables from database.
 * @return
 */
	private List<Table> getTables() {
		print("getTables usao!"); 
		List<Table> tables = new ArrayList<>(); 
		String sql = "SELECT po_oznaka, tab_kod, tab_naziv FROM Tabele"; 
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 
		try{
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(); 
			while(rs.next()) {
				Table table = new Table(); 
				tables.add(table);
				
				String oznaka = rs.getString("po_oznaka"); 
				table.setPackageName(oznaka);
				print("Oznaka paketa u kom je tabela je:" + oznaka); 
				
				String kod = rs.getString("tab_kod");
				table.setCode(kod);
				print("Kod tabele je:" + kod); 
				
				String naziv = rs.getString("tab_naziv"); 
				table.setName(naziv);
				print("Ime tabele je:" + naziv); 
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return tables; 
	}
	/**
	 * Gets all columns for specified table from database.
	 * @return
	 */	
	private List<Column> getColumnsForTable(Table table) {
		print("getColumnsForTable usao!"); 
		List<Column> columns = new ArrayList<>(); 
		String sql = "SELECT * FROM Atributi WHERE Tab_kod = '" + table.getCode() + "'";
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 
		
		try{
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(); 
			
			while(rs.next()) {
				Column column = new Column(); 
				
				String code = rs.getString("atr_kod"); 
				column.setCode(code);
				print("Kod kolone je:" + code); 
				
				String name = rs.getString("atr_labela");
				column.setName(name);
				print("Ime kolone je:" + name); 
				
				DataConstraint dataConstraint = new DataConstraint(); 
				
				String columnType = rs.getString("atr_tip"); 
				print("Tip kolone je:" + columnType); 
				
				DataType type = DataType.forValue(columnType);
				dataConstraint.setType(type);
				
				int length = rs.getInt("atr_duzina");
				dataConstraint.setLength(length);
				print("duzina je:" + length); 
				
				int precision = rs.getInt("atr_preciznost");		
				dataConstraint.setPrecision(precision);
				print("preciznost je:" + precision); 
				
				column.setType(dataConstraint);
				
				// set table contraint object if column is primary / foreign key
				TableConstraint constraint = new TableConstraint();
				column.setConstraint(constraint);
				if(rs.getInt("atr_deo_pk") == 1) {
					constraint.setPrimaryKey(true);
				}
			
				if(rs.getInt("atr_obavezno") == 1) {
					constraint.setMandatory(true);	
				}
				
				columns.add(column);
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		// get TableContraints details
		for(Column column : columns) {
		   getTableContraint(column, table);
		   getValueConstraint(column, table); 
		}
		
		return columns; 
	}
	/**
	 * Gets value constraints for specified column from database.
	 * @param column
	 * @param table
	 */
	private void getValueConstraint(Column column, Table table) {
		column.setValueConstraints(null);	
	}

/**
 * Gets table constraint for specified table column from database.
 * @param column
 * @param table
 */
	private void getTableContraint(Column column, Table table) {
		print("getTableConstraint usao!"); 
		TableConstraint tableConstraint = column.getConstraint();
		
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 
		String skKod = null;
						
		String sql = "SELECT * FROM Kolone_u_stranom_kljucu WHERE atr_atr_kod = '" + column.getCode() + "' AND atr_tab_kod = '" + table.getCode() + "'"; 
		try {
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(); 
			
			if(rs.next()) { 
				tableConstraint.setForeignKey(true);
				skKod = rs.getString("sk_kod"); 
				print("skKod tabele je:" + skKod); 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(tableConstraint.getForeignKey().booleanValue()) {
			getForeignKeyConstraint(tableConstraint, skKod, table);
		}
	}

/**
 * Gets foreign-key constraints, map all referring columns to referenced columns.
 * @param tableConstraint
 * @param skKod
 * @param table
 */
	private void getForeignKeyConstraint(TableConstraint tableConstraint, String skKod, Table table) {
		print("getForeignKeyConstraint usao!"); 
		ForeignKeyConstraint foreignKeyConstraint = new ForeignKeyConstraint(); 
		PreparedStatement preparedStatement = null; 
		ResultSet rs = null; 
		List<String> refeeringColumnCodes = new ArrayList<>(); // kol11, kol12
		List<String> referencedColumnCodes = new ArrayList<>(); // kol22, kol23
		foreignKeyConstraint.setRefeeringColumnCodes(refeeringColumnCodes);
		foreignKeyConstraint.setReferencedColumnCodes(referencedColumnCodes);
	 
		
		String sql = "SELECT * FROM KOLONE_U_STRANOM_KLJUCU INNER JOIN STRANI_KLJUC on KOLONE_U_STRANOM_KLJUCU.SK_KOD = STRANI_KLJUC.SK_KOD WHERE KOLONE_U_STRANOM_KLJUCU.sk_kod = '" + skKod + "'";
		
		try{
			preparedStatement = connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(); 
			
			while(rs.next()) {
				String codeFK = rs.getString("sk_kod"); 
				foreignKeyConstraint.setCode(codeFK);
				print("codeFk je:" + codeFK); 
				
				String nameFK = rs.getString("sk_labela");
				foreignKeyConstraint.setName(nameFK);
				print("nameFK je:" + nameFK); 
				
				String referencedTableCode = rs.getString("tab_kod");
				foreignKeyConstraint.setReferencedTableCode(referencedTableCode);
				print("referencedTableCode je:" + referencedTableCode); 
				
				String referencedColumnCode = rs.getString("atr_kod");
				print("referencedColumnCode je:" + referencedColumnCode); 
				String refeeringColumnCode = rs.getString("atr_atr_kod"); 
				print("refeeringColumnCode je:" + refeeringColumnCode); 
				referencedColumnCodes.add(referencedColumnCode); 
				foreignKeyConstraint.setReferencedColumnCodes(referencedColumnCodes);
				
				refeeringColumnCodes.add(refeeringColumnCode); 						
				foreignKeyConstraint.setRefeeringColumnCodes(refeeringColumnCodes);
				
				 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		if(refeeringColumnCodes.size() > 0) {
			table.getReferences().add(foreignKeyConstraint);
		}
	}
		

	@Override
	public DBSchema interpretSchema() {
		
		connection = DbConnection.getInstance().getConnection();
		
		DBSchema metaSchema = new DBSchema();
		
		getMetadata(metaSchema);
		
		//Initializing primary and foreign key hash lists - helper structures.
		for(Table t : metaSchema.getTables()){
			t.initializePrimaryKey();
			t.initializeForeignKey();
		}
		
		//Initializing the hashMap as a helper structure in metaSchema.
		metaSchema.initializeMap();
		metaSchema.initializePackageMap();
		
		setParentChildRelations(metaSchema);
		
		
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
