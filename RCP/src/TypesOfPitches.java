import java.security.SecureRandom;

public class TypesOfPitches {
	private static SecureRandom random = new SecureRandom();

	private static int rowOfPitch;
	private static int colOfPitch;
	private static int chanceOfBall;

	public TypesOfPitches(int row1, int row2, int row3, int col1, int col2, int col3, int ball, int era) {
		int num1 = row1;
		int num2 = row2;
		int num3 = row3;

		switch (era) {
		case 5:
			ball += 5;
			break;
		case 7:
			ball -= 5;
			break;
		case 8:
			ball -= 10;
			break;
		}

		chanceOfBall = ball;

		for (int i = 0; i < 2; i++) {

			int randomNum = random.nextInt(10);
			int setPitch = 0;

			if (randomNum >= num1 && randomNum <= num2) {
				setPitch = 0;
			}

			else if (randomNum > num2 && randomNum <= num3) {
				setPitch = 1;
			}

			else if (randomNum > num3) {
				setPitch = 2;
			}

			if (i == 0) {
				rowOfPitch = setPitch;
			} else if (i == 1) {
				colOfPitch = setPitch;
			}

			num1 = col1;
			num2 = col2;
			num3 = col3;
		}

	}

	public int getRow() {
		return rowOfPitch;
	}

	public int getCol() {
		return colOfPitch;
	}

	public boolean getBall() {
		if (chanceOfBall <= 1 + random.nextInt(100)) {
			return true;
		}
		else {
			return false;
		}
	}
}
