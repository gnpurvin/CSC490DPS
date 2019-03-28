package qualityoflife;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the DiceRoller class for each size of die
 * @author Greg
 */
class DiceRollerTest {
	
	/**
	 * tests the constructor to make sure it won't make a die of an invalid size
	 */
	@Test
	void testInvalid() {
		DiceRoller d = null;
		d = new DiceRoller("5D20");
		assertEquals(5, d.getNumDice());
		assertEquals(20, d.getSize());
		System.out.println(d.roll());
	}

}
