package ra1_2013;

import static org.junit.Assert.*;

import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.toedter.calendar.JDateChooser;

import gui.panel.SchemaTabbedPane;
import model.Column;
import model.DataConstraint;
import model.DataType;
import sql.AdvancedSearchComponent;

/**
 * JUnit test for {@link AdvancedSearchComponent}.	
 * 
 * @author Viktor
 *
 */
public class AdvancedSearchComponentTest {
	
	private AdvancedSearchComponent numericComponent, integerComponent, charComponent, varcharComponent, dateComponent, blobComponent;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// seting up the advanced search component with data of numeric type in the column
		DataConstraint numericDC = new DataConstraint();
		numericDC.setType(DataType.NUMERIC);
		
		Column numericColumn = new Column();
		numericColumn.setType(numericDC);
		
		numericComponent = new AdvancedSearchComponent(numericColumn, null);
		
		
		// seting up the advanced search component with data of integer type in the column
		DataConstraint integerDC = new DataConstraint();
		integerDC.setType(DataType.INTEGER);
		
		Column integerColumn = new Column();
		integerColumn.setType(integerDC);
		
		integerComponent = new AdvancedSearchComponent(integerColumn, null);
		
		
		// seting up the advanced search component with data of char type in the column
		DataConstraint charDC = new DataConstraint();
		charDC.setType(DataType.CHAR);
		
		Column charColumn = new Column();
		charColumn.setType(charDC);
		
		charComponent = new AdvancedSearchComponent(charColumn, null);
		
		
		// seting up the advanced search component with data of varchar type in the column
		DataConstraint varcharDC = new DataConstraint();
		varcharDC.setType(DataType.VARCHAR);
		
		Column varcharColumn = new Column();
		varcharColumn.setType(varcharDC);
		
		varcharComponent = new AdvancedSearchComponent(varcharColumn, null);
		
		
		// seting up the advanced search component with data of date type in the column
		DataConstraint dateDC = new DataConstraint();
		dateDC.setType(DataType.TIMESTAMP);
		
		Column dateColumn = new Column();
		dateColumn.setType(dateDC);
		
		dateComponent = new AdvancedSearchComponent(dateColumn, null);
		
		
		// seting up the advanced search component with data of blob type in the column
		DataConstraint blobDC = new DataConstraint();
		blobDC.setType(DataType.BLOB);
		
		Column blobColumn = new Column();
		blobColumn.setType(blobDC);
		
		blobComponent = new AdvancedSearchComponent(blobColumn, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Testing the constructor.
	 */
	@Test
	public void testAdvancedSearchComponent() {
		Column c = new Column();
		c.getName();
		
		DataConstraint dc = new DataConstraint();
		dc.setType(DataType.INTEGER);
		
		c.setType(dc);
		
		AdvancedSearchComponent asc = new AdvancedSearchComponent(c, null);
		
		// checks whether the instance has been successfully made
		assertNotNull(asc);
		// checks whether the instance is of expected type
		assertTrue(asc instanceof AdvancedSearchComponent);
		assertTrue(asc instanceof JPanel);
	}
	
	/**
	 * Testing the constructor with null parameters. Construction generates a null pointer exception.
	 */
	@Test(expected=NullPointerException.class)
	public void testAdvancedSearchComponentNull() {
		
		AdvancedSearchComponent asc = new AdvancedSearchComponent(null, null);
		
	}
	
	/**
	 * Checks whether a good prepared statement is being generated from the {@link AdvancedSearchComponent}.
	 */
	@Test
	public void testGetPreparedStatementPart() {
		assertEquals(numericComponent.getPreparedStatementPart(), " null  = ?");
		
		// setting up an advanced search component for an expected prepared statement: " test  > ?"
		DataConstraint numericDC = new DataConstraint();
		numericDC.setType(DataType.NUMERIC);
		
		Column numericColumn = new Column();
		numericColumn.setType(numericDC);
		numericColumn.setCode("test");
		
		numericComponent = new AdvancedSearchComponent(numericColumn, null);
		numericComponent.getTemplateJCB().setSelectedItem(">");
		
		assertEquals(numericComponent.getPreparedStatementPart(), " test  > ?");
	}
	
	/**
	 * Tests whether the right component has been set up for the various data types supported.
	 */
	@Test
	public void testGetKomponenta() {
		assertTrue(numericComponent.getKomponenta() instanceof JSpinner);
		assertTrue(integerComponent.getKomponenta() instanceof JSpinner);
		
		assertTrue(charComponent.getKomponenta() instanceof JTextField);
		assertTrue(varcharComponent.getKomponenta() instanceof JTextField);
		
		assertTrue(dateComponent.getKomponenta() instanceof JDateChooser);
		
		assertTrue(blobComponent.getKomponenta() == null);
	}
	
	/**
	 * Test whether the right component is set.
	 */
	@Test
	public void testSetKomponenta() {
		blobComponent.setKomponenta(new JPanel());
		assertTrue(blobComponent.getKomponenta() instanceof JPanel);
	}
	
	/**
	 * Test whether appropriate combo box has been set up. Length of the options list is checked against the already set up combo box model.
	 */
	@Test
	public void testGetTemplateJCB() {
		
		assertEquals(numericComponent.getTemplateJCB().getModel().getSize(), 7);
		assertEquals(integerComponent.getTemplateJCB().getModel().getSize(), 7);
		
		assertEquals(charComponent.getTemplateJCB().getModel().getSize(), 10);
		assertEquals(varcharComponent.getTemplateJCB().getModel().getSize(), 10);
		
		assertEquals(dateComponent.getTemplateJCB().getModel().getSize(), 6);
		assertEquals(blobComponent.getTemplateJCB().getModel().getSize(), 2);
	}
	
	/**
	 * Test whether the options combo box is possible to be set up.
	 */
	@Test
	public void testSetTemplateJCB() {
		String[] test = {"a","b","c","d"};
		
		numericComponent.setTemplateJCB(new JComboBox<>(test));
		
		assertEquals(numericComponent.getTemplateJCB().getModel().getSize(), 4);
	}
	
	/**
	 * Test whether the options combo box is possible to be set up with null parameter.
	 */
	@Test(expected=NullPointerException.class)
	public void testSetTemplateJCBNull() {
		numericComponent.setTemplateJCB(null);
		assertEquals(numericComponent.getTemplateJCB().getModel().getSize(), 0);
	}
}
