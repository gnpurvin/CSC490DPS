/**
 * 
 */
package qualityoflife;

import java.util.Random;
import java.util.Scanner;

/**
 * @author Greg
 * The DiceRoller class rolls a given number of dice of a given size
 * For example, it can roll 5 d8s
 * Can only roll same-sized dice at a time
 */
public class DiceRoller {
	
	/** highest number on the die */
	private int size;
	
	/** number of dice to roll */
	private int numDice;
	
	/** all valid sizes for a die */
	private static final int[] SIZES = new int[] {4, 6, 8, 10, 12, 20, 100};

	/**
	 * constructor for the dice roller
	 * @param input string formatted as XdY
	 * X is the number of dice to roll
	 * Y is the size of the die being rolled
	 */
	public DiceRoller(String input) {
		Scanner inScan = new Scanner(input.toLowerCase());
		inScan.useDelimiter("d");
		int num = inScan.nextInt();
		int size = inScan.nextInt();
		boolean valid = false;
		inScan.close();
		for(int i = 0; i < SIZES.length; i++) {
			if(size == SIZES[i]) {
				valid = true;
				break;
			}
		}
		if(!valid) {
			throw new IllegalArgumentException("Invalid Die Size.");
		}
		this.size = size;
		this.numDice = num;
	}
	
	public int getSize() {
		return size;
	}

	public int getNumDice() {
		return numDice;
	}

	public static int[] getSizes() {
		return SIZES;
	}

	/**
	 * Rolls a number of dice according to the number of rolls and size of die 
	 * given in the constructor
	 * @return string with the result of the roll
	 */
	public String roll() {
		String result = "rolled " + numDice + "d" + size + " and got ";
		Random rand = new Random();
		int sum = 0;
		for(int i = 0; i < numDice; i++) {
			sum += rand.nextInt(size) + 1;
		}
		result += sum + ".";
		return result;
	}
//	
//	/**
//	 * rolls a single die of the given size
//	 * @return result of the roll
//	 */
//	public int rollSingleInt() {
//		Random rand = new Random();
//		return rand.nextInt(size) + 1; 
//	}

}
