package ra213_2012;

import static org.junit.Assert.assertEquals;
import gui.validation.MaxTextLengthVerifier;

import javax.swing.JTextField;

import model.DataConstraint;
import model.DataType;

import org.junit.Before;
import org.junit.Test;

public class MaxTextLengthVerifierTest {

	private JTextField input;
	private MaxTextLengthVerifier verifier;
	
	@Before
	public void setUp() throws Exception {
		input = new JTextField();
		DataConstraint constraint = new DataConstraint();
		constraint.setLength(10);
		constraint.setPrecision(2);
		constraint.setType(DataType.VARCHAR);
		verifier = new MaxTextLengthVerifier(constraint,null,false,null);
	}

	@Test
	public void testConstraintNotBroken() {
		input.setText("123456789011");
		boolean output = verifier.verify(input);
		boolean expected = false;
		assertEquals(expected, output);
	}
	
	
	@Test
	public void testConstraintBroken() {
		input.setText("123");
		boolean output = verifier.verify(input);
		boolean expected = true;
		assertEquals(expected, output);
	}

}
