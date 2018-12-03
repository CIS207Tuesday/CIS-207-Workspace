package Baseball;

public class BaseballEvents extends Baseball {
	private Bases homePlate = new Bases(false, null);
	private Bases base1 = new Bases(false, null);
	private Bases base2 = new Bases(false, null);
	private Bases base3 = new Bases(false, null);

	private static int chanceOfOut;
	private boolean batterChanged;

	private static boolean isOut;

	private static enum field {
		inField, midField, outField
	}

	private static field currentPos;

	public void hitsBall(boolean isUserPitching) {
		if (chanceOfFoulBall() == false) {
			inAir(isUserPitching);
			setIsOut();
			
			if (isOut == false) {
				moveBases(true, isUserPitching);
			} 
			
			else {
				outCounter++;
				if (currentPos == field.outField) {
					System.out.printf("A player in the outfield caught the ball. %s is out%n", upToBat.getName());	
					moveBases(false, isUserPitching);
				} 
				
				else if (currentPos == field.inField) {
					System.out.printf("A player in the infield caught the ball and threw to first base.  %s is out%n", upToBat.getName());
				}
				
				else if (currentPos == field.midField) {
					String message;
					int selectMessage = random.nextInt(2);

					if (selectMessage == 0) {
						message = "A player in the midfield caught the ball.";
					} else {
						message = "A player in the midfield picked up the ball and threw to first base";
					}
					
					System.out.printf(message + " %s is out%n", upToBat.getName());
				}
			}
			setBatterChanged(true);
		} else {

			System.out.println("Foul Ball!");

			if (strikeCounter < 2) {
				strikeCounter++;
			}
		}
	}

	public boolean chanceOfFoulBall() {
		int chance = 35;
		chance -= chanceOfBatAvg;

		return chance >= 1 + random.nextInt(100);
	}

	public void inAir(boolean isUserPitching) {

		if (chanceOfSwing() < 40) {
			catchBallInAir(0, 6, 9);
		} else if (chanceOfSwing() >= 40 && chanceOfSwing() < 60) {
			catchBallInAir(0, 5, 8);
		} else if (chanceOfSwing() <= 60 && chanceOfSwing() > 80) {
			catchBallInAir(0, 3, 7);
		} else if (chanceOfSwing() >= 80) {
			catchBallInAir(0, 1, 5);
		}

		int chanceOfOut = 0;

		if (currentPos == field.inField) {
			chanceOfOut = 60;
			System.out.println("Ball went into the infield");
		} else if (currentPos == field.midField) {
			chanceOfOut = 40;
			System.out.println("Ball went into the midfield");
		} else if (currentPos == field.outField) {
			chanceOfOut = 25;
			System.out.println("Ball went into the outfield");
			if (isOut == true) {
				moveBases(false, isUserPitching);
			}
		}

		this.chanceOfOut = chanceOfOut;

	}

	public void catchBallInAir(int num1, int num2, int num3) {
		int sel = random.nextInt(10);

		if (sel >= num1 && sel < num2) {
			currentPos = field.inField;
		} else if (sel >= num2 && sel < num3) {
			currentPos = field.midField;
		} else if (sel >= num3) {
			currentPos = field.outField;
		}
	}

	public void setIsOut() {
		if (chanceOfOut >= 1 + random.nextInt(100)) {
			isOut = true;
		} else {
			isOut = false;
		}

	}

	private static String name1 = "Empty";
	private static String name2 = "Empty";
	private static String name3 = "Empty";

	public void moveBases(boolean batterIsNotOut, boolean isUserPitching) {
		String name1 = "Empty";
		String name2 = "Empty";
		String name3 = "Empty";

		homePlate.setArgs(true, upToBat);

		if (base1.getLoaded() == false && batterIsNotOut == true) {
			base1.setArgs(true, homePlate.getPlayerOnBase());
			name1 = base1.getPlayerOnBase().getName();
			System.out.printf("%s ran to first base and is safe!%n", base1.getPlayerOnBase().getName());

		} else if (base2.getLoaded() == false && base1.getLoaded() == true) {
			base2.setArgs(true, base1.getPlayerOnBase());
			base1.setArgs(true, homePlate.getPlayerOnBase());

			name1 = base1.getPlayerOnBase().getName();
			name2 = base2.getPlayerOnBase().getName();

			System.out.printf("%s ran to first base and is safe!%n%s ran to second base and is safe!%n",
					base1.getPlayerOnBase().getName(), base2.getPlayerOnBase().getName());

		} else {

			if (base3.getLoaded() == true) {
				if (isUserPitching == false) {
					userScoreCounter++;
					System.out.println(userTeamName + " Scored!");
				} else {
					compScoreCounter++;
					System.out.println(compTeamName + " Scored!");
				}

			}
			if (base2.getLoaded() == true) {
				base3.setArgs(true, base2.getPlayerOnBase());
				name3 = base3.getPlayerOnBase().getName();
				System.out.printf("%s ran to third base and is safe!%n", base2.getPlayerOnBase().getName());
			}
			if (base1.getLoaded() == true) {
				base2.setArgs(true, base1.getPlayerOnBase());
				name2 = base2.getPlayerOnBase().getName();
				System.out.printf("%s ran to second base and is safe!%n", base1.getPlayerOnBase().getName());
			}
			if (batterIsNotOut == true) {
				base1.setArgs(true, homePlate.getPlayerOnBase());
				name1 = base1.getPlayerOnBase().getName();
				System.out.printf("%s ran to first base and is safe!%n", homePlate.getPlayerOnBase().getName());
			}

			// Add scoring

		}

		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;

	}

	public void resetBases() {
		this.name1 = "Empty";
		this.name2 = "Empty";
		this.name3 = "Empty";

		homePlate.setArgs(false, null);
		base1.setArgs(false, null);
		base2.setArgs(false, null);
		base2.setArgs(false, null);
	}

	public void printBases() {
		System.out.printf("%nBase 1: %s%nBase 2: %s%nBase 3: %s%n%n", name1, name2, name3);
	}

	public void setBatterChanged(boolean batterChanged) {
		this.batterChanged = batterChanged;
	}

	public boolean getBatterChanged() {
		return batterChanged;
	}

	public Players getBase1() {
		return base1.getPlayerOnBase();
	}

	public Players getBase2() {
		return base2.getPlayerOnBase();
	}

	public Players getBase3() {
		return base3.getPlayerOnBase();
	}
}
