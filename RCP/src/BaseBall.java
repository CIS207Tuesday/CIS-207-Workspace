import java.security.SecureRandom;
import java.util.Scanner;

public class BaseBall {

	private static Scanner input = new Scanner(System.in);
	private static SecureRandom random = new SecureRandom();

	private final int[][] strikeZone = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }; // Represents a "strikeZone"

	// Creates objects from the 'players' constructor class
	private static Players Rendon = new Players("Rendon", .308);
private static Players Kednrick = new Players("Kendrick", .303);
private static Players Eaton = new Players("Eaton", .285);
private static Players Murphy = new Players("Murphy", .293);
private static Players Scherzer = new Players("Scherzer", 2.5, null);
private static Players Andy = new Players("Grace", 2.8, null);
private static Players Frank = new Players("Castellanos", .265);
private static Players George = new Players("Iglesias", .278);
private static Players Henry = new Players("Cabrera", .300);
private static Players Hicks = new Players("Hicks", .260);
private static Players Boyd = new Players("Boyd", 4.3, null);
private static Players Wilson = new Players("Wilson", 3.9, null);
	

static Players Nationals[] = { Rendon, Kendrick, Eaton, Murphy, Scherzer, Grace  }; 
																		
static Players Tigers[] = { Castellanos, Iglesias, Cabrera, Hicks, Boyd, Wilson };

	static Players userTeam[]; // After the user selects a team array, that array will be stored there
	static Players compTeam[];

	Players upToBat; // Stores a 'Players' object when that object is up to bat / pitch
	Players upToPitch;

	private enum pos {
		first, middle, third // Enum to allow for the pitch/strike location in the strike zone to be stored
								// by row & column
	};

	private static pos batterRow; // Holds location for which row the batter swung
	private static pos batterCol; // Holds location for which column the pitcher pitched
	private static pos pitcherRow;
	private static pos pitcherCol;

	private int chanceOfBatAvg; // Holds the value for how well the batter's/ pitcher's batting avg/era is
	private int chanceOfEra;

	private int chanceOfPos() { // This method is a scale that returns a value determined by a scale which
								// measures the location of where the pitcher pitched and batter swung
		int chance = 0;

		if (batterRow == pitcherRow) {
			chance = 80;
		} else if (batterRow == pos.middle && (pitcherRow == pos.first || pitcherRow == pos.third)) {
			chance = 40;
		} else if (pitcherRow == pos.middle && (batterRow == pos.first || batterRow == pos.third)) {
			chance = 40;
		} else if ((batterRow != pitcherRow) && (batterRow != pos.middle && pitcherRow != pos.middle)) {
			chance = 0;
		}

		if (chance > 0) {
			if (batterCol == pitcherCol) {
				chance += 10;
			} else if (batterCol == pos.middle && (pitcherCol == pos.first || pitcherCol == pos.third)) {

			} else if ((batterCol != pitcherCol) && (batterCol != pos.middle && pitcherCol != pos.middle)) {
				chance -= 10;
			}
		}

		return chance;
	}

	private int chanceOfStats() { // Combines the scaled batting avg and Era numbers and w
		int chance = chanceOfBatAvg - chanceOfEra;
		return chance;
	}

	private int chanceOfSwing() { // Determines the chance of the batter hitting the ball
		int chance = 0;
		if (chanceOfPos() > 0) {

			chance = chanceOfPos() + chanceOfStats();
		}

		return chance;
	}

	public void selectTeam() {
		System.out.println("Select team (1 or 2)");
		int sel = input.nextInt();
		if (sel == 1) { // Sets the userTeam array to whatever team they select
			userTeam = team1;
			compTeam = team2;
		}

		else {
			userTeam = team2;
			compTeam = team1;
		}
		int x = 0;
		while (x == 0) {
			upToBat = userTeam[random.nextInt(userTeam.length - 1)];
			upToPitch = compTeam[4];
			System.out.println();
			System.out.println(upToBat.getName() + " batting avg is: " + upToBat.getAvg());
			System.out.println(upToPitch.getName() + " era is: " + upToPitch.getEra());
			pitchInput(true);
			batInput(true);
			setChanceOfBat(upToBat.getAvg());
			setChanceOfPitch(upToPitch.getEra());
			System.out.println("Chance of pos is: " + chanceOfPos());
			System.out.println("Chance of stat is: " + chanceOfStats());
			System.out.println("Chance of Swing is: " + chanceOfSwing());
		}
	}

	public void printStrikeZone() {
		for (int rows = 0; rows < strikeZone.length; rows++) {
			for (int columns = 0; columns < strikeZone[rows].length; columns++) {
				System.out.print("[" + strikeZone[rows][columns] + "]");
			}
			System.out.println();
		}
	}

	public void batInput(boolean isUser) {
		int batInput = 0;

		printStrikeZone();

		if (isUser == true) {
			int x = 0;
			do {
				System.out.println("Enter the number of where you would like to swing");
				batInput = input.nextInt();
				if (batInput < 1 && batInput > 9) {
					System.out.println("Incorrect input");
					x = 1;
				}
			} while (x > 1);
		}
		convertToEnum(batInput, true);
	}

	public void pitchInput(boolean isUser) {
		int pitchInput = 0;

		printStrikeZone();

		if (isUser == true) {
			int x = 0;
			do {
				System.out.println("Enter the number of where you would like to pitch");
				pitchInput = input.nextInt();
				if (pitchInput < 1 && pitchInput > 9) {
					System.out.println("Incorrect input");
					x = 1;
				}
			} while (x > 1);
		}
		convertToEnum(pitchInput, false);
	}

	public void convertToEnum(int proInput, boolean isBatter) {
		pos playerInputRow = null;
		pos playerInputCol = null;
		switch (proInput) {
		case 1:
			playerInputRow = pos.first;
			playerInputCol = pos.first;
			break;
		case 2:
			playerInputRow = pos.first;
			playerInputCol = pos.middle;
			break;
		case 3:
			playerInputRow = pos.first;
			playerInputCol = pos.third;
			break;
		case 4:
			playerInputRow = pos.middle;
			playerInputCol = pos.first;
			break;
		case 5:
			playerInputRow = pos.middle;
			playerInputCol = pos.middle;
			break;
		case 6:
			playerInputRow = pos.middle;
			playerInputCol = pos.third;
			break;
		case 7:
			playerInputRow = pos.third;
			playerInputCol = pos.first;
			break;
		case 8:
			playerInputRow = pos.third;
			playerInputCol = pos.middle;
			break;
		case 9:
			playerInputRow = pos.third;
			playerInputCol = pos.third;
			break;
		}

		if (isBatter == true) {
			batterRow = playerInputRow;
			batterCol = playerInputCol;
		} else {
			pitcherRow = playerInputRow;
			pitcherCol = playerInputCol;
		}
	}

	public void setChanceOfBat(double battingAvg) {
		int chanceOfBat = 0;
		
		if (battingAvg >= .100 && battingAvg <= .149) {
			chanceOfBat = 5;
		} else if (battingAvg >= .150 && battingAvg <= .199) {
			chanceOfBat = 6;
		} else if (battingAvg >= .200 && battingAvg <= .249) {
			chanceOfBat = 7;
		} else if (battingAvg >= .300) {
			chanceOfBat = 8;
		}
		this.chanceOfBatAvg = chanceOfBat;
	}

	public void setChanceOfPitch(double era) {
		int chanceOfPitch = 0;
		
		if (era == 1 && era <= 1.9) {
			chanceOfPitch = 8;
		} else if (era >= 2 && era <= 2.9) {
			chanceOfPitch = 7;
		} else if (era >= 3 && era <= 3.9) {
			chanceOfPitch = 6;
		} else if (era >= 4 && era < 6) {
			chanceOfPitch = 5;
		}
		this.chanceOfEra = chanceOfPitch;
	}
}
