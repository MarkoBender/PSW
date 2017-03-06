package ra98_2013;

import static org.junit.Assert.*;

import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import gui.validation.NumericVerifier;
import model.DataConstraint;
import model.DataType;

public class NumericVerifierTest {

	private JTextField input;
	
	@Before
	public void setUp() throws Exception {
		input = new JTextField();
	}
	
	@Test
	public void wrongDataType(){
		boolean expectedValue = true;
		boolean exceptionThrown = false;
		DataConstraint constraint = new DataConstraint();
		constraint.setType(DataType.TIMESTAMP);
		try {
			NumericVerifier verifier = new NumericVerifier(constraint, null,  null, null);
		}catch(IllegalArgumentException e) {
			exceptionThrown = true;
		}
		assertEquals(expectedValue, exceptionThrown);
	}

	@Test
	public void mandatoryNotFilled() {
		boolean expectedValue = false;
		boolean mandatory = true;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void nonMandatoryNotFilled() {
		boolean expectedValue = true;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testInteger() {
		boolean expectedValue = true;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("111");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testIntegerWithDot() {
		boolean expectedValue = true;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("111.");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testDotInteger() {
		boolean expectedValue = true;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText(".11");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testIntegerDotInteger() {
		boolean expectedValue = true;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("111.11");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testLengthWrong() {
		boolean expectedValue = false;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("1111.11");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}
	
	@Test
	public void testPrecisionWrong() {
		boolean expectedValue = false;
		boolean mandatory = false;
		// mandatory 
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(3);
		constraint.setPrecision(2);
		constraint.setType(DataType.NUMERIC);
		NumericVerifier verifier = new NumericVerifier(constraint, null,  mandatory, null);
		input.setText("111.111");
		boolean outputValue = verifier.verify(input);
		assertEquals(expectedValue, outputValue);
	}

}
