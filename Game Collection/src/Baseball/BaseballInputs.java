package Baseball;
import Default.SelectGame;

public class BaseballInputs extends Baseball {
	
	public void pitchInput(boolean isUser) {		
		setChanceOfBat(upToBat.getBattingAvg());
		setChanceOfPitch(upToPitch.getEra());
		
		// (row1, row2, row3, col1, col2, col3, chanceOfFoul, pitcherEra)
		TypesOfPitches fastball = new TypesOfPitches(0, 1, 7, 0, 1, 7, 30, chanceOfEra);
		TypesOfPitches curveball = new TypesOfPitches(0, 0, 3, 0, 2, 7, 45, chanceOfEra);
		TypesOfPitches changeup = new TypesOfPitches(0, 1, 6, 0, 5, 8, 45, chanceOfEra);

		int pitchInput = 0;
		int typeOfPitch = 0;

		if (isUser == true) {
			typeOfPitch = SelectGame.getInputFromUser("\nSelect pitch type\n[1]Fastball\n[2]Curveball\n[3]Changeup", 1,
					3);
		} else {
			typeOfPitch = 1 + random.nextInt(3);
		}

		TypesOfPitches selectedPitch = null;
		if (typeOfPitch == 1) {
			selectedPitch = fastball;
		} else if (typeOfPitch == 2) {
			selectedPitch = curveball;
		} else if (typeOfPitch == 3) {
			selectedPitch = changeup;
		}

		boolean isUser2;
		if (isUser == true) {
			isUser2 = false;
		} else {
			isUser2 = true;
		}
		batInput(isUser2);

		if (doesSwing == true) {
			pitchInput = strikeZone[selectedPitch.getRow()][selectedPitch.getCol()];
			strikeZoneAfterBat[selectedPitch.getRow()][selectedPitch.getCol()] = "O";
			pitchesBall = false;
		} else if (doesSwing == false && selectedPitch.getBall() == true) {
			pitchesBall = true;
		} else {
			pitchesBall = false;
		}
		convertToEnum(pitchInput, false);
	}

	public void batInput(boolean isUser) {
		int batInput = 0;
		int doesSwing = 0;

		if (isUser == true) {
			doesSwing = SelectGame.getInputFromUser("Would you like to swing\n[1] Yes\n[2] No", 1, 2);
			System.out.println();

			if (doesSwing == 1) {
				printStrikeZone();
				this.doesSwing = true;
				batInput = SelectGame.getInputFromUser("Enter the number of where you would like to swing", 1, 9);
			} else {
				this.doesSwing = false;
				System.out.println("Batter did not swing");
			}
		}

		else {
			doesSwing = random.nextInt(2);
			if (doesSwing == 1) {
				this.doesSwing = true;
				batInput = 1 + random.nextInt(8);
			} else if (doesSwing == 0) {
				System.out.println("Batter did not swing");
				this.doesSwing = false;
			}
		}

		switch (batInput) {
		case 1:
			strikeZoneAfterBat[0][0] = "X";
			break;
		case 2:
			strikeZoneAfterBat[0][1] = "X";
			break;
		case 3:
			strikeZoneAfterBat[0][2] = "X";
			break;
		case 4:
			strikeZoneAfterBat[1][0] = "X";
			break;
		case 5:
			strikeZoneAfterBat[1][1] = "X";
			break;
		case 6:
			strikeZoneAfterBat[1][2] = "X";
			break;
		case 7:
			strikeZoneAfterBat[2][0] = "X";
			break;
		case 8:
			strikeZoneAfterBat[2][1] = "X";
			break;
		case 9:
			strikeZoneAfterBat[2][2] = "X";
			break;
		}

		convertToEnum(batInput, true);
	}

	

	public void convertToEnum(int proInput, boolean isBatter) {
		POSITION playerInputRow = null;
		POSITION playerInputCol = null;
		switch (proInput) {
		case 1:
			playerInputRow = POSITION.first;
			playerInputCol = POSITION.first;
			break;
		case 2:
			playerInputRow = POSITION.first;
			playerInputCol = POSITION.middle;
			break;
		case 3:
			playerInputRow = POSITION.first;
			playerInputCol = POSITION.third;
			break;
		case 4:
			playerInputRow = POSITION.middle;
			playerInputCol = POSITION.first;
			break;
		case 5:
			playerInputRow = POSITION.middle;
			playerInputCol = POSITION.middle;
			break;
		case 6:
			playerInputRow = POSITION.middle;
			playerInputCol = POSITION.third;
			break;
		case 7:
			playerInputRow = POSITION.third;
			playerInputCol = POSITION.first;
			break;
		case 8:
			playerInputRow = POSITION.third;
			playerInputCol = POSITION.middle;
			break;
		case 9:
			playerInputRow = POSITION.third;
			playerInputCol = POSITION.third;
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
		} else if (battingAvg >= .250 && battingAvg < .300) {
			chanceOfBat = 8;
		} else if (battingAvg >= .300) {
			chanceOfBat = 9;
		}
		this.chanceOfBatAvg = chanceOfBat;
	}

	public void setChanceOfPitch(double era) {
		int chanceOfPitch = 0;

		if (era >= 1 && era <= 1.9) {
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
