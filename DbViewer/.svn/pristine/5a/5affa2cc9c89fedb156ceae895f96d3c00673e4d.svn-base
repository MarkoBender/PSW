package ra139_2013;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import security.MD5Hashing;
/**
 * Tests if MD5 hash is performed properly.
 * @author Jelena
 *
 */
public class MD5HashingText {
	

	
	@Before
	public void setUp() throws Exception {
	
	}
/**
 * Tests if MD5 algorithm is implemented property by providing a known
 * input and comparing with known proper, expected output.
 */
	@Test
	public void testHashOnInputString() {
		final String input = "TEST_STRING_MD5"; 
		final String expectedOutput = "6d953fee14761e106642449382586ccb";
		String output = MD5Hashing.getMD5(input);
		assertEquals(expectedOutput, output);	
	}

}
