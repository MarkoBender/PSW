package ra213_2012;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import model.Column;
import model.Table;
import model.TableConstraint;

import org.junit.Before;
import org.junit.Test;

public class TableTest {

	private Table table;
	
	@Before
	public void setUp() throws Exception {
		table = new Table();
		table.setName("moja_tabela");
		
		TableConstraint con1 = new TableConstraint();
		con1.setPrimaryKey(true);
		con1.setForeignKey(false);

		TableConstraint con2 = new TableConstraint();
		con2.setPrimaryKey(false);
		con2.setForeignKey(true);

		TableConstraint con3 = new TableConstraint();
		con3.setPrimaryKey(false);
		con3.setForeignKey(false);

		TableConstraint con4 = new TableConstraint();
		con4.setPrimaryKey(false);
		con4.setForeignKey(false);

		TableConstraint con5 = new TableConstraint();
		con5.setPrimaryKey(false);
		con5.setForeignKey(false);

		
		Column col1 = new Column();
		col1.setConstraint(con1);
		Column col2 = new Column();
		col2.setConstraint(con2);
		Column col3 = new Column();
		col3.setConstraint(con3);
		Column col4 = new Column();
		col4.setConstraint(con4);
		Column col5 = new Column();
		col5.setConstraint(con5);
		

		List<Column> columns = new ArrayList<Column>();
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col4);
		columns.add(col5);
		table.setColumns(columns);
		
		table.initializeForeignKey();
		table.initializePrimaryKey();
		

	}

	@Test 
	public void testConstructor(){
		Table myTable = new Table();
		assertNotNull(myTable);
		assertNotNull(myTable.getPrimaryKeys());
		assertNotNull(myTable.getForeignKeys());
		assertNull(myTable.getColumns());
	}
	
	@Test
	public void testNumberOfColumns(){
		assertEquals(table.getColumns().size(), 5);
	}
	
	@Test
	public void testNumberOfForeignKeys(){
		assertEquals(table.getForeignKeys().size(), 1);
	}
	
	@Test
	public void testNumberOfPrimaryKeys(){
		assertEquals(table.getPrimaryKeys().size(), 1);
	}
	
	
	
	@Test
	public void testToString() {
		assertEquals(table.getName(),"moja_tabela");
	}

}
