package program;

import java.util.Random;

public class NumGuess_Logic {

	private int targetNumber;
	private char flag;

	public void generateNumber() {
		Random rand = new Random();
		targetNumber = rand.nextInt(1000) + 1;
	}

	public char numberEqualsTarget(int userNumber) {

		if (userNumber == targetNumber) {
			flag = '=';
		}

		else if (userNumber > targetNumber) {
			flag = 'U';
		}

		else if (userNumber < targetNumber) {
			flag = 'L';
		}

		return flag;

	}

}
