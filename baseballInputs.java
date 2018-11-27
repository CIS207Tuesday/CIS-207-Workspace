public class baseballInputs extends Baseball {

	public void batInput(boolean isUser) {
		int batInput = 0;
		int doesSwing = 0;

		if (isUser == true) {
			doesSwing = selectGame.getInputFromUser("Would you like to swing\n[1] Yes\n[2] No?", 1, 2);
			System.out.println();

			if (doesSwing == 1) {
				printStrikeZone();
				this.doesSwing = true;
				batInput = selectGame.getInputFromUser("Enter the number of where you would like to swing", 1, 9);
			} else if (doesSwing != 1) {
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

	public void pitchInput(boolean isUser) {
		// (row1, row2, row3, col1, col2, col3, chanceOfFoul, pitcherEra)
		TypesOfPitches fastball = new TypesOfPitches(0, 1, 7, 0, 1, 7, 30, chanceOfEra);
		TypesOfPitches curveball = new TypesOfPitches(0, 0, 3, 0, 2, 7, 45, chanceOfEra);
		TypesOfPitches changeup = new TypesOfPitches(0, 1, 6, 0, 5, 8, 45, chanceOfEra);
		int pitchInput = 0;
		int typeOfPitch = 0;

		if (isUser == true) {
			typeOfPitch = selectGame.getInputFromUser("\nSelect pitch type\n[1]Fastball\n[2]Curveball\n[3]Changeup", 1,
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
			foul = false;
		} else if (doesSwing == false && selectedPitch.getFoulBall() == true) {
			foul = true;
		}
		convertToEnum(pitchInput, false);
	}

	public void convertToEnum(int proInput, boolean isBatter) {
		POSSITION playerInputRow = null;
		POSSITION playerInputCol = null;
		switch (proInput) {
		case 1:
			playerInputRow = POSSITION.first;
			playerInputCol = POSSITION.first;
			break;
		case 2:
			playerInputRow = POSSITION.first;
			playerInputCol = POSSITION.middle;
			break;
		case 3:
			playerInputRow = POSSITION.first;
			playerInputCol = POSSITION.third;
			break;
		case 4:
			playerInputRow = POSSITION.middle;
			playerInputCol = POSSITION.first;
			break;
		case 5:
			playerInputRow = POSSITION.middle;
			playerInputCol = POSSITION.middle;
			break;
		case 6:
			playerInputRow = POSSITION.middle;
			playerInputCol = POSSITION.third;
			break;
		case 7:
			playerInputRow = POSSITION.third;
			playerInputCol = POSSITION.first;
			break;
		case 8:
			playerInputRow = POSSITION.third;
			playerInputCol = POSSITION.middle;
			break;
		case 9:
			playerInputRow = POSSITION.third;
			playerInputCol = POSSITION.third;
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
}
