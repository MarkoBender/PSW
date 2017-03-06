package ra139_2013;

import static org.junit.Assert.*;
import gui.validation.ExactTextLengthVerifier;
import gui.validation.IntegerVerifier;

import javax.swing.JTextField;

import model.DataConstraint;
import model.DataType;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
/**
 * Tests {@link IntegerVerifier} behavior when validating input fields with SQL data type INTEGER.
 * @author Jelena
 *
 */
public class IntegerVerifierTest {
	JTextField inputForValidation;
	
	@Before
	public void setUp() throws Exception {
		inputForValidation = new JTextField();
	}


	/**
	 * Check if MANDATORY empty fields are marked as invalid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testEmptyMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.INTEGER);
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	

	/**
	 * Check if NON-MANDATORY empty fields are marked as valid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return true in order for test to pass
	 * 
	 */
	@Test
	public void testEmptyNonMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.INTEGER);
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	

	/**
	 * Check if MANDATORY fields with alphabetic input are marked as invalid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testNonNumberMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("ABCD");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	/**
	 * Check if NON-MANDATORY fields with alphabetic input are marked as invalid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testNonNumberNonMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("ABCD");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	/**
	 * Check if MANDATORY fields with floating-point input are marked as invalid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testFloatingPointMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("4.44");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
		
	

	/**
	 * Check if NON-MANDATORY fields with floating-point input are marked as invalid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testFloatingPointNonMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("4.44");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	
	
	/**
	 * Check if MANDATORY fields with integer input are marked as valid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return true in order for test to pass
	 * 
	 */
	@Test
	public void testNumberMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("1000");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	/**
	 * Check if NON-MANDATORY fields with integer input are marked as valid.
	 * {@link IntegerVerifier#verify(javax.swing.JComponent)} should return true in order for test to pass
	 * 
	 */
	@Test
	public void testNumberNonMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.INTEGER);
		
		IntegerVerifier verifier = new IntegerVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("1000");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	

	/**
	 * Check if constructor throws {@link IllegalArgumentException} when invalid DataType is specified
	 *
	 * 
	 */
	@Test
	public void testBadDataTypeMandatoryInput() {
		final boolean expectedValue = true;
		boolean exceptionThrown = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(4);
		constraint.setType(DataType.VARCHAR);
		try {
			IntegerVerifier verifier = new IntegerVerifier(constraint, null,  null, null);
		}catch(IllegalArgumentException e) {
			exceptionThrown = true;
		}
		assertEquals(expectedValue, exceptionThrown);
	}
	
	
	
	
}
