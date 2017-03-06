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
 * Tests {@link ExactTextLengthVerifier} behavior when validating input fields with SQL data type CHAR.
 * @author Jelena
 *
 */
public class ExactTextLengthVerifierTest {
	
	JTextField inputForValidation;
	
	@Before
	public void setUp() throws Exception {
		inputForValidation = new JTextField();
	}

	
	/**
	 * Check if MANDATORY empty fields are marked as invalid.
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testEmptyMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	

	/**
	 * Check if NON-MANDATORY empty fields are marked as valid.
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return true in order for test to pass
	 * 
	 */
	@Test
	public void testEmptyNonMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	

	/**
	 * Check if fields containing less text than expected are marked as invalid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testLessThanExpectedMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("1234");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}

	/**
	 * Check if fields containing less text than expected are marked as invalid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testLessThanExpectedNonMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("1234");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	/**
	 * Check if fields containing more text than expected are marked as invalid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testMoreThanExpectedMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("123456");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}
	
	/**
	 * Check if fields containing more text than expected are marked as invalid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testMoreThanExpectedNonMandatoryInput() {
		final boolean expectedValue = false;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("123456");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}

	
	/**
	 * Check if fields containing exact amount of text are marked as valid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testExactMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("12345");
		boolean outputValue = verifier.verify(inputForValidation);
		assertEquals(expectedValue, outputValue);
	}

	/**
	 * Check if fields containing exact amount of text are marked as valid,
	 * {@link ExactTextLengthVerifier#verify(javax.swing.JComponent)} should return false in order for test to pass
	 * 
	 */
	@Test
	public void testExactNonMandatoryInput() {
		final boolean expectedValue = true;
		final boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(5);
		constraint.setType(DataType.CHAR);
		
		ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  mandatory, null);
		inputForValidation.setText("12345");
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
		constraint.setType(DataType.INTEGER);
		try {
			ExactTextLengthVerifier verifier = new ExactTextLengthVerifier(constraint, null,  null, null);
		}catch(IllegalArgumentException e) {
			exceptionThrown = true;
		}
		assertEquals(expectedValue, exceptionThrown);
	}
	
}
