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
		for(int i = 0; i < 1000; i++) {
			try {
				d = new DiceRoller(i);
				if(i != 4 && i != 6 && i != 8 && i != 10 && i != 12 && i != 20 && i != 100) {
					System.out.println(i);
					fail();
				}
			} catch(IllegalArgumentException e) {
				d.roll();
			}
		}
	}

	/**
	 * tests rolling a d4
	 * output printed to console
	 */
	@Test
	void testFour() {
		int testnum = 4;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d6
	 * output printed to console
	 */
	@Test
	void testSix() {
		int testnum = 6;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d8
	 * output printed to console
	 */
	@Test
	void testEight() {
		int testnum = 8;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d10
	 * output printed to console
	 */
	@Test
	void testTen() {
		int testnum = 10;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d12
	 * output printed to console
	 */
	@Test
	void testTwelve() {
		int testnum = 12;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d20
	 * output printed to console
	 */
	@Test
	void testTwenty() {
		int testnum = 20;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}
	
	/**
	 * tests rolling a d100
	 * output printed to console
	 */
	@Test
	void testHundred() {
		int testnum = 100;
		System.out.println("Test for d" + testnum);
		int[] results = new int[testnum];
		DiceRoller d = new DiceRoller(testnum);
		for(int i = 0; i < 100000; i++) {
			int roll = d.roll();
			results[roll - 1]++;
		}
		for(int i = 1; i <= results.length; i++) {
			System.out.println(i + "'s: " + results[i-1]);
		}
	}

}
