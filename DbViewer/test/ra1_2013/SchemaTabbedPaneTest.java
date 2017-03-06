package ra1_2013;

import static org.junit.Assert.*;

import javax.swing.JTabbedPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.Application;
import gui.panel.SchemaTabbedPane;
import model.Table;
import tree.TableNode;

/**
 * JUnit test for {@link SchemaTabbedPane}.
 * 
 * @author Viktor
 *
 */
public class SchemaTabbedPaneTest {
	
	private static SchemaTabbedPane stp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Application.test = true;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Application.test = false;
	}

	@Before
	public void setUp() throws Exception {
		stp = new SchemaTabbedPane();
		
		Table t1 = new Table(), t2 = new Table(), t3 = new Table();
		t1.setCode("t1");
		t2.setCode("t2");
		t3.setCode("t3");
		
		stp.add(t1);
		stp.add(t2);
		stp.add(t3);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Testing the constructor.
	 */
	@Test
	public void testSchemaTabbedPane() {
		SchemaTabbedPane test = new SchemaTabbedPane();
		
		// checks whether the instance has been successfully made
		assertNotNull(test);
		// checks whether the instance is of expected type
		assertTrue(test instanceof SchemaTabbedPane);
		assertTrue(test instanceof JTabbedPane);
		// checks whether the initial tab count is 0
		assertEquals(test.getTabCount(), 0);
	}
	
	/**
	 * Testing the tab creation with {@link TableNode} as parameter. Checking the adding logic and tab uniqueness 
	 * according to the Table Code, which is an unique identifier of the Table object inside the Table Node.
	 */
	@Test
	public void testAddTableNode() {
		assertEquals(stp.getTabCount(), 3);
		Table t1 = new Table();
		t1.setCode("test1");
		
		TableNode tn1 = new TableNode(t1);
		
		// adding unique table in the table node
		stp.add(tn1);
		assertEquals(stp.getTabCount(), 4);
		
		// adding the same node again
		stp.add(tn1);
		assertEquals(stp.getTabCount(), 4);
		
		// adding the node with table already opened as tab
		t1.setCode("t1");
		tn1 = new TableNode(t1);
		
		stp.add(tn1);
		assertEquals(stp.getTabCount(), 4);
		
		// removing all opened tabs
		stp.removeAll();
		assertEquals(stp.getTabCount(), 0);
	}
	
	/**
	 * Testing the tab creation with {@link TableNode} as parameter. The Table for the node is not set.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddTableNodeWithoutTable() {
		assertEquals(stp.getTabCount(), 3);
		
		TableNode tn1 = new TableNode(null);
		
		stp.add(tn1);
	}
	
	/**
	 * Testing the tab creation with {@link Table} as parameter. Checking the adding logic and tab uniqueness 
	 * according to the Table Code, which is an unique identifier of the Table object.
	 */
	@Test
	public void testAddTable() {
		// adding table which already has a tab opened
		Table t1 = new Table();
		t1.setCode("t1");
		
		stp.add(t1);
		assertEquals(stp.getTabCount(), 3);
		
		// adding unique tables and opening new tabs
		t1.setCode("test1");
		stp.add(t1);
		assertEquals(stp.getTabCount(), 4);
		
		Table t2 = new Table();
		t2.setCode("test2");
		
		stp.add(t2);
		assertEquals(stp.getTabCount(), 5);
		
		// removing all opened tabs
		stp.removeAll();
		assertEquals(stp.getTabCount(), 0);
	}
	
	/**
	 * Testing the tab creation with {@link Table} as parameter, but without Table Code being set.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddTableWithoutCode() {
		assertEquals(stp.getTabCount(), 3);
		
		Table t = new Table();
		stp.add(t);
		stp.add(t);
	}
	
	/**
	 * Testing the method for checking whether the {@link Table} already has an associated and opened Tab.
	 */
	@Test
	public void testTabWithCodePresentPosition() {
		// checking with table code with an opened tab
		assertTrue(stp.TabWithCodePresentPosition("t1")!=-1);
		
		// checking with table code without an opened tab
		assertTrue(stp.TabWithCodePresentPosition("test1")==-1);
		
		stp.removeAll();
		
		// checking with table code with previously opened, but subsequently closed tab
		assertTrue(stp.TabWithCodePresentPosition("t1")==-1);
		
		Table t1 = new Table();
		t1.setCode("test1");
		
		stp.add(t1);
		// checking with table code without an opened tab
		assertTrue(stp.TabWithCodePresentPosition("test1")!=-1);
	}

}
