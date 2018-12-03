package Baseball;

import java.security.SecureRandom;
import java.util.Scanner;
import Default.SelectGame;

public class Baseball {

	static BaseballEvents events = new BaseballEvents();

	private static Scanner input = new Scanner(System.in);
	protected static SecureRandom random = new SecureRandom();

	static final int[][] strikeZone = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }; // Represents a "strikeZone"
	protected static String[][] strikeZoneAfterBat = { { "1", "2", "3" }, { "4", "5", "6" }, { "7", "8", "9" } };

	// Creates objects from the 'players' constructor class
	private static Batter Betts = new Batter("Betts", .346);
	private static Batter Martinez = new Batter("Martinez", .330);
	private static Batter Benintendi = new Batter("Benintendi", .290);
	private static Batter Bogaerts = new Batter("Bogaerts", .288);
	private static Pitcher Price = new Pitcher("Price", 3.58);
	private static Pitcher Barnes = new Pitcher("Barnes", 1.04);

	private static Batter Rendon = new Batter("Rendon", .308);
	private static Batter Kendrick = new Batter("Kendrick", .303);
	private static Batter Eaton = new Batter("Eaton", .285);
	private static Batter Murphy = new Batter("Murphy", .293);
	private static Pitcher Scherzer = new Pitcher("Scherzer", 2.5);
	private static Pitcher Grace = new Pitcher("Grace", 2.8);

	private static Batter Castellanos = new Batter("Castellanos", .265);
	private static Batter Iglesias = new Batter("Iglesias", .278);
	private static Batter Cabrera = new Batter("Cabrera", .300);
	private static Batter Hicks = new Batter("Hicks", .260);
	private static Pitcher Boyd = new Pitcher("Boyd", 4.3);
	private static Pitcher Wilson = new Pitcher("Wilson", 3.9);

	static Players BostonRedSox[] = { Betts, Martinez, Benintendi, Bogaerts, Price, Barnes };// Takes those objects and
																								// puts them in arrays

	static Players WashingtonNationals[] = { Rendon, Kendrick, Eaton, Murphy, Scherzer, Grace };
	static Players DetroitTigers[] = { Castellanos, Iglesias, Cabrera, Hicks, Boyd, Wilson };

	static Players userTeam[]; // After the user selects a team array, that array will be stored there
	static Players compTeam[];

	static Batter upToBat; // Stores a 'Players' object when that object is up to bat / pitch
	static Pitcher upToPitch;

	enum POSITION {
		first, middle, third // Enum to allow for the pitch/strike location in the strike zone to be stored
								// by row & column
	};

	protected static POSITION batterRow; // Holds location for which row the batter swung
	protected static POSITION batterCol; // Holds location for which column the pitcher pitched
	protected static POSITION pitcherRow;
	protected static POSITION pitcherCol;

	protected static boolean hitsBall;

	protected static int chanceOfBatAvg; // Holds the value for how well the batter's/ pitcher's batting avg/era is
	protected static int chanceOfEra;

	private int chanceOfPos() { // This method is a scale that returns a value determined by a scale which
								// measures the location of where the pitcher pitched and batter swung
		int chance = 0;

		if (doesSwing == true) {
			if (getBatterRow() == pitcherRow) {
				chance = 80;
			} else if (getBatterRow() == POSITION.middle
					&& (pitcherRow == POSITION.first || pitcherRow == POSITION.third)) {
				chance = 40;
			} else if (pitcherRow == POSITION.middle
					&& (getBatterRow() == POSITION.first || getBatterRow() == POSITION.third)) {
				chance = 40;
			} else if ((getBatterRow() != pitcherRow)
					&& (getBatterRow() != POSITION.middle && pitcherRow != POSITION.middle)) {
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
			if (pitchesBall == true) {
				chance -= 10;
			}
		}
		return chance;
	}

	private int chanceOfStats() { // Combines the scaled batting avg and Era numbers and w
		int chance = (chanceOfBatAvg - chanceOfEra) * 3;
		return chance;
	}

	protected int chanceOfSwing() { // Determines the chance of the batter hitting the ball
		int chance = 0;
		if (chanceOfPos() > 0) {
			chance = chanceOfPos() + chanceOfStats();
			if (chance > 99) {
				chance = 99;
			}
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

	protected static String userTeamName;
	protected static String compTeamName;

	protected static int userScoreCounter;
	protected static int compScoreCounter;

	protected static boolean doesSwing;
	protected static boolean pitchesBall;
	protected static int ballCounter;
	protected static int strikeCounter;
	protected static int outCounter;

	public void selectTeam(boolean isUser) {
		do {
			Players[] team = null;
			String name = null;
			int select = 0;
			if (isUser == true)
				select = SelectGame.getInputFromUser(
						"\nSelect team \n[1] Detroit Tigers \n[2] Washington Nationals\n[3] Boston Red Sox", 1, 3);
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
		System.out.printf("The user selected the %s%nThe computer selected the %s%n%n", userTeamName, compTeamName);

		boolean isUserPitching = false;

		int batterCounter = 0;
		int pitcherCounter = 5;

		int inningCounter = 1;
		int printInnings = 0;

		while (x == 0) {

			for (inningCounter = inningCounter; inningCounter <= 6;) {

				String inningHalf;
				if ((inningCounter % 2) == 0) {
					inningHalf = "Bottom of";
				} else {
					printInnings++;
					inningHalf = "Top of";
				}

				do {

					System.out.println(getInning(inningHalf, printInnings));

					do {
						strikeCounter = 0;
						ballCounter = 0;

						if (batterCounter > 3) {
							batterCounter = 0;
						}

						if (isUserPitching == false) {
							upToBat = (Batter) userTeam[batterCounter];
							upToPitch = (Pitcher) compTeam[pitcherCounter];
						} else {
							upToBat = (Batter) compTeam[batterCounter];
							upToPitch = (Pitcher) userTeam[pitcherCounter];
						}

						if (events.getBase1() == upToBat || events.getBase2() == upToBat
								|| events.getBase3() == upToBat) {
							batterCounter++;
						}

					} while (events.getBase1() == upToBat || events.getBase2() == upToBat
							|| events.getBase3() == upToBat);

					System.out.printf("%n%s is up to bat.%n%s is up to pitch%n%n", upToBat.getName(),
							upToPitch.getName());

					while (strikeCounter < 3 && ballCounter < 4) {
						bbInput.pitchInput(isUserPitching);
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
						printStrikeZone();
						setHitsBall();
						if (hitsBall == true) {
							System.out.printf("%n%s hit the ball!%n", upToBat.getName());
							events.hitsBall(isUserPitching);
						}
						resetAfterBat();
						if (events.getBatterChanged() == true) {
							events.setBatterChanged(false);
							strikeCounter = 0;
							ballCounter = 0;
							printResults(false, inningHalf, printInnings);
							break;
						}
						printResults(false, inningHalf, printInnings);
					}

					if (strikeCounter == 3) {
						outCounter++;
						System.out.println("Strike out!");
					} else if (ballCounter == 4) {
						System.out.printf("%n%s walked %s!%n", upToPitch.getName(), upToBat.getName());
						events.moveBases(true, isUserPitching);
					}

					batterCounter++;

				} while (outCounter < 3);

				events.resetBases();
				outCounter = 0;

				if (isUserPitching == true) {
					isUserPitching = false;
				} else if (isUserPitching == false) {
					isUserPitching = true;
				}
				
				inningCounter++;
			}
			
		}
	}

	public void printResults(boolean devOptions, String inningHalf, int innings) {
		if (devOptions == true) {
			System.out.printf("%nChance of Pos: %d%nChance of Stats: %d%nChance of Swing: %d%nHits ball: %b%n",
					chanceOfPos(), chanceOfStats(), chanceOfSwing(), hitsBall);
		}

		if (hitsBall == false && doesSwing == true) {
			strikeCounter++;
			System.out.printf("%n%s missed%nStrike %d!%n", upToBat.getName(), strikeCounter);
		} else if (pitchesBall == true) {
			ballCounter++;
			System.out.printf("%n%s threw a ball.%nBall %d!%n", upToPitch.getName(), ballCounter);
		} else if (pitchesBall == false && doesSwing == false) {
			strikeCounter++;
			System.out.printf("%n%s threw a strike. %nStrike %d!%n", upToPitch.getName(), strikeCounter);
		}

		events.printBases();
		System.out.print(getInning(inningHalf, innings));
		System.out.printf("%nStrikes: %d%nBalls: %d%nOuts: %d%n%n%s's Score: %d%n%s's Score: %d%n%n", strikeCounter,
				ballCounter, outCounter, userTeamName, userScoreCounter, compTeamName, compScoreCounter);

	}

	public String getInning(String inningHalf, int inning) {
		return ("Inning: " + inningHalf + " " + inning);
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

	public static POSITION getBatterRow() {
		return batterRow;
	}
}