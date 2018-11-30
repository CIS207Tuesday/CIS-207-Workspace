import java.security.SecureRandom;
import java.util.Scanner;

public class Baseball {

	private static Scanner input = new Scanner(System.in);
	protected static SecureRandom random = new SecureRandom();

	static final int[][] strikeZone = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }; // Represents a "strikeZone"
	protected static String[][] strikeZoneAfterBat = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", "9" } };

	// Creates objects from the 'players' constructor class
	private static Players Betts = new Players("Betts", .346);
	private static Players Martinez = new Players("Martinez", .330);
	private static Players Benintendi = new Players("Benintendi", .290);
	private static Players Bogaerts = new Players("Bogaerts", .288);
	private static Players Price = new Players("Price", 3.58, "p");
	private static Players Barnes = new Players("Barnes", 1.04, "p");
	private static Players Rendon = new Players("Rendon", .308);
	private static Players Kendrick = new Players("Kendrick", .303);
	private static Players Eaton = new Players("Eaton", .285);
	private static Players Murphy = new Players("Murphy", .293);
	private static Players Scherzer = new Players("Scherzer", 2.5, null);
	private static Players Grace = new Players("Grace", 2.8, null);
	private static Players Castellanos = new Players("Castellanos", .265);
	private static Players Iglesias = new Players("Iglesias", .278);
	private static Players Cabrera = new Players("Cabrera", .300);
	private static Players Hicks = new Players("Hicks", .260);
	private static Players Boyd = new Players("Boyd", 4.3, null);
	private static Players Wilson = new Players("Wilson", 3.9, null);

	static Players BostonRedSox[] = { Betts, Martinez, Benintendi, Bogaerts, Price, Barnes };// Takes those objects and
																								// puts them in arrays

	static Players WashingtonNationals[] = { Rendon, Kendrick, Eaton, Murphy, Scherzer, Grace };
	static Players DetroitTigers[] = { Castellanos, Iglesias, Cabrera, Hicks, Boyd, Wilson };

	static Players userTeam[]; // After the user selects a team array, that array will be stored there
	static Players compTeam[];

	Players upToBat; // Stores a 'Players' object when that object is up to bat / pitch
	Players upToPitch;

	enum POSITION {
		first, middle, third // Enum to allow for the pitch/strike location in the strike zone to be stored
								// by row & column
	};

	protected static POSITION batterRow; // Holds location for which row the batter swung
	protected static POSITION batterCol; // Holds location for which column the pitcher pitched
	protected static POSITION pitcherRow;
	protected static POSITION pitcherCol;

	protected static boolean hitsBall;

	private int chanceOfBatAvg; // Holds the value for how well the batter's/ pitcher's batting avg/era is
	protected int chanceOfEra;

	private int chanceOfPos() { // This method is a scale that returns a value determined by a scale which
								// measures the location of where the pitcher pitched and batter swung
		int chance = 0;

		if (doesSwing == true) {
			if (batterRow == pitcherRow) {
				chance = 80;
			} else if (batterRow == POSITION.middle && (pitcherRow == POSITION.first || pitcherRow == POSITION.third)) {
				chance = 40;
			} else if (pitcherRow == POSITION.middle && (batterRow == POSITION.first || batterRow == POSITION.third)) {
				chance = 40;
			} else if ((batterRow != pitcherRow) && (batterRow != POSITION.middle && pitcherRow != POSITION.middle)) {
				chance = 0;
			}

			if (chance > 0) {
				if (batterCol == pitcherCol) {
					chance += 10;
				} else if (batterCol == POSITION.middle
						&& (pitcherCol == POSITION.first || pitcherCol == POSITION.third)) {

				} else if ((batterCol != pitcherCol)
						&& (batterCol != POSITION.middle && pitcherCol != POSITION.middle)) {
					chance -= 10;
				}
			}
			if (foul == true) {
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

	public void setHitsBall() {
		int hitsBall = 1 + random.nextInt(100);

		if (hitsBall <= chanceOfSwing())
			Baseball.hitsBall = true;
		else {
			Baseball.hitsBall = false;
		}

		if (chanceOfSwing() == 0)
			Baseball.hitsBall = false;
	}

	private static String userTeamName;
	private static String compTeamName;

	public void selectTeam(boolean isUser) {
		do {
			Players[] team = null;
			String name = null;
			int select = 0;
			if (isUser == true)
				select = SelectGame.getInputFromUser(
						"Select team \n[1] Detroit Tigers \n[2] Washington Nationals\n[3] Boston Red Sox", 1, 3);
			else {
				select = 1 + random.nextInt(3);
			}

			switch (select) {
			case 1:
				team = DetroitTigers;
				name = "Detroit Tigers";
				break;
			case 2:
				team = WashingtonNationals;
				name = "Washington Nationals";
				break;
			case 3:
				team = BostonRedSox;
				name = "Boston Red Sox";
				break;
			}

			if (isUser == true) {
				userTeam = team;
				userTeamName = name;
			} else {
				compTeam = team;
				compTeamName = name;
			}
		} while (userTeam == compTeam);

	}

	public void playGame() {
		BaseballInputs bbInput = new BaseballInputs();
		int x = 0;
		selectTeam(true);
		selectTeam(false);
		System.out.printf("The user selected the %s%nThe computer selected the %s%n%n%n", userTeamName, compTeamName);

		boolean isUserPitching = false;

		int batterCounter = 0;
		int pitcherCounter = 5;

		while (x == 0) {

			do {

				strikeCounter = 0;
				ballCounter = 0;

				if (isUserPitching == false) {
					upToBat = userTeam[batterCounter];
					upToPitch = compTeam[pitcherCounter];
				} else {
					upToBat = compTeam[batterCounter];
					upToPitch = userTeam[pitcherCounter];
				}

				System.out.printf("%n%s is up to bat.%n%s is up to pitch%n", upToBat.getName(), upToPitch.getName());

				while (strikeCounter < 3 && ballCounter < 4) {
					bbInput.pitchInput(isUserPitching);
					printStrikeZone();
					pitchSwingResults(false);
					resetAfterBat();
				}

				if (strikeCounter == 3) {
					outCounter++;
					System.out.println("Strike out!");
				} else if (ballCounter == 4) {
					System.out.printf("%n%s walked %s%n!", upToPitch.getName(), upToBat.getName());
				}

				if (isUserPitching == true) {
					isUserPitching = false;
				} else {
					isUserPitching = true;
				}

			} while (outCounter > 3);

			if (isUserPitching == true) {
				isUserPitching = false;
			} else if (isUserPitching = false) {
				isUserPitching = true;
			}

			batterCounter++;
		}
	}

	public void pitchSwingResults(boolean devOptions) {
		setHitsBall();
		if (devOptions == true) {
			System.out.printf("%nChance of Pos: %d%nChance of Stats: %d%nChance of Swing: %d%nHits ball: %b%n",
					chanceOfPos(), chanceOfStats(), chanceOfSwing(), hitsBall);
		}

		if (hitsBall == true) {
			System.out.println("Batter hit ball");
		} else if (hitsBall == false && doesSwing == true) {
			System.out.println("Batter missed");
			strikeCounter++;
		} else if (foul == true) {
			System.out.println("Pitcher threw a ball.");
			ballCounter++;
		} else if (foul == false && doesSwing == false) {
			strikeCounter++;
		}

		System.out.printf("%nStrikes: %d%nBalls: %d%n%n%n", strikeCounter, ballCounter);

	}

	public void printStrikeZone() {
		for (int rows = 0; rows < strikeZoneAfterBat.length; rows++) {
			for (int columns = 0; columns < strikeZoneAfterBat[rows].length; columns++) {
				System.out.print("[" + strikeZoneAfterBat[rows][columns] + "]");
			}
			System.out.println();
		}
	}

	public void resetAfterBat() {
		strikeZoneAfterBat[0][0] = "1";
		strikeZoneAfterBat[0][1] = "2";
		strikeZoneAfterBat[0][2] = "3";
		strikeZoneAfterBat[1][0] = "4";
		strikeZoneAfterBat[1][1] = "5";
		strikeZoneAfterBat[1][2] = "6";
		strikeZoneAfterBat[2][0] = "7";
		strikeZoneAfterBat[2][1] = "8";
		strikeZoneAfterBat[2][2] = "9";
	}

	protected static boolean doesSwing;
	protected static boolean foul;
	protected static int ballCounter;
	protected static int strikeCounter;
	protected static int outCounter;
}
