package ra98_2013;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.DBSchema;
import model.Table;
import model.Package;

public class DBSchemaTest {
	private DBSchema dbs;
	private Table t1;
	private Table t2;
	@Before
	public void setUp() throws Exception {
		dbs=new DBSchema();
		t1=new Table();
		t2=new Table();
		Table t3=new Table();
		Table t4=new Table();
		
		Package p1=new Package();
		Package p2=new Package();
		
		t1.setCode("t1");
		t2.setCode("t2");
		t3.setCode("t3");
		t4.setCode("t3");
		
		p1.setPackageCode("p1");
		p2.setPackageCode("p2");
		
		ArrayList<Table> lt=new ArrayList<Table>();
		dbs.setTables(lt);
		dbs.getTables().add(t1);
		dbs.getTables().add(t2);
		dbs.getTables().add(t3);
		dbs.getTables().add(t4);
		
		ArrayList<Package> lp=new ArrayList<Package>();
		dbs.setPackages(lp);
		dbs.getPackages().add(p1);
		dbs.getPackages().add(p2);
		
		dbs.initializeMap();
		dbs.initializePackageMap();
	}
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor(){
		DBSchema test=new DBSchema();
		assertNotNull(test);
		assertNull(test.getTables());
		assertNull(test.getPackages());
		assertNull(test.getPackageMap());
		assertNull(test.getTableMap());
	}

	/**
	 * Tests whether initializeMap and initializePackageMap are working properly.
	 */
	@Test
	public void testNumberOfElements() {
		assertEquals(dbs.getPackages().size(), 2);
		assertEquals(dbs.getTables().size(),4);
		assertEquals(dbs.getPackageMap().size(),2);
		assertEquals(dbs.getTableMap().size(),3);
	}
	
	/**
	 * Tests whether getTableByName is working properly.
	 */
	@Test
	public void testName(){
		assertNotEquals(dbs.getTableByName("t1"),t2);
		assertEquals(dbs.getTableByName("t1"),t1);
	}

}
