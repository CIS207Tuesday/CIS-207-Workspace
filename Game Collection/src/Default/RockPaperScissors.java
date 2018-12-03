package Default;

import java.security.SecureRandom;
import java.util.Scanner;

public class RockPaperScissors {

	static Scanner input = new Scanner(System.in);
	static SecureRandom random = new SecureRandom();

	static final String[] menu = { "Rock", "Paper", "Scissors" }; // Strings that the program will refer to when
																	// printing out either of these 3 strings
	static final int minWins[] = { 2, 3, 4 }; // Minimum amount of wins needed to win game
	static final int maxWins[] = { 3, 5, 7 }; // Maximum amount of wins / rounds

	enum OPTIONS {
		ROCK, PAPER, SCISSORS // Used to determine who wins the round
	};

	private static int playerWins; // Counter for player wins
	private static int computerWins; // Counter for computer wins
	private static String playerName;
	private static final String computerName = "Computer";
	private static boolean runGame; // Sentenal for running the game

	private static void setPlayerName() { // Sets player's name
		System.out.println("Enter name of user:");
		String name = input.nextLine();
		playerName = name;
	}

	private static OPTIONS playerOption; // Holds the enum value of the User's / computer's choice
	private static OPTIONS computerOption;

	public void mainRps() {
		setPlayerName(); // Refers to set player method
		System.out.println();

		// Sets all necessary variables to the defualt values

		playerWins = 0;
		computerWins = 0;
		runGame = true;

		System.out.println("Select how man rounds you want to play:"); // Prints out menu for user to select amount of
																		// rounds
		for (int counter = 0; counter < maxWins.length; counter++) {
			System.out.printf("%n[%d] Best of " + maxWins[counter], (counter + 1)); // A one is added to the %d to be
																					// consistent with user imput
		}
		System.out.println();
		int roundSelect = SelectGame.getInputFromUser("", 1, 3) - 1; // Any time a one is added or subtracted, it is to remain consistent with
												// user input

		System.out.println();
		System.out.println();
		System.out.println("Enter 1 for Rock, 2 for paper, or 3 for scissors."); // Begining instructions

		while (runGame == true) {

			int playerSel;
			int compSel;

			for (int counter = 0; counter < menu.length; counter++) {
				System.out.printf("%n[%d] " + menu[counter], (counter + 1));
			}

			System.out.println();
			playerSel = SelectGame.getInputFromUser ("\n\nSelect an option\n________________", 1, 3);
			//prints and tests RPC Options menu
			convertToEnum(true, playerSel, playerName); // Converts user input to enum

			System.out.println();
			System.out.println();

			compSel = 1 + random.nextInt(3); // Creates random value for the computer's "Selection"
			convertToEnum(false, compSel, computerName); // Converts computer input to enum

			System.out.println();
			System.out.println();

			selectRoundWinner((playerSel - 1), (compSel - 1)); // Refers to method to select round winner

			System.out.println();
			System.out.println();
			selectGameWinner(roundSelect);
		}
	}

	public static void convertToEnum(boolean isUser, int input, String name) { // 'isUser' Determines if it is
																				// converting either
																				// user or computer input

		OPTIONS selectInput = null; // A "random" enum variable to store a value that will later be assigned to
									// either the user or computer.

		switch (input) {
		case 1:
			selectInput = OPTIONS.ROCK;
			break;
		case 2:
			selectInput = OPTIONS.PAPER;
			break;
		case 3:
			selectInput = OPTIONS.SCISSORS;
			break;
		}

		// This determines wether the user or computer will have their enum OPTION set
		// to either rock, paper, or scissors

		if (isUser == true) {
			playerOption = selectInput;
		} else {
			computerOption = selectInput;
		}
		System.out.println(name + " selected " + menu[input - 1]);
	}

	public static void selectRoundWinner(int playerSel, int compSel) {

		// IF USER WINS

		String action[] = { " Smashes ", " Covers ", " Cuts " }; // Array that will print out the corresponding action
																	// based on the choices of the user / comp

		if ((playerOption == OPTIONS.ROCK && computerOption == OPTIONS.SCISSORS) // "If user beats computer"
				|| (playerOption == OPTIONS.PAPER && computerOption == OPTIONS.ROCK)
				|| (playerOption == OPTIONS.SCISSORS && computerOption == OPTIONS.PAPER)) {
			System.out.println(menu[playerSel] + action[playerSel] + menu[compSel]); // Combines the user's choice, the
																						// corresponding action, and the
																						// computer's choice to
																						// print out the outcome
			System.out.printf("%n%s Won Round", playerName);
			playerWins++;
		} else if ((computerOption == OPTIONS.ROCK && playerOption == OPTIONS.SCISSORS) // "If computer beats user"
				|| (computerOption == OPTIONS.PAPER && playerOption == OPTIONS.ROCK)
				|| (computerOption == OPTIONS.SCISSORS && playerOption == OPTIONS.PAPER)) {
			System.out.println(menu[compSel] + action[compSel] + menu[playerSel]);
			System.out.println("Computer Won Round");
			computerWins++;
		}

		else { // If the user and computer have the same OPTIONS value
			System.out.println("User and Computer tied");
		}
		// Prints out the amount of rounds each particpant has
		System.out.printf("%n%n%s wins: %d", playerName, playerWins);
		System.out.printf("%n%s wins: %d%n", computerName, computerWins);
	}

	public static void selectGameWinner(int selectRounds) { // Selects winner if either one "person" has the maximum
															// amount of wins, or if one person has the minimum amount
															// of wins while the other person has less than the minimum
		if (playerWins == maxWins[selectRounds]
				|| (playerWins >= minWins[selectRounds] && computerWins < minWins[selectRounds])) {
			System.out.println("User wins");
			runGame = false;
		}

		else if (computerWins == maxWins[selectRounds]
				|| (computerWins >= minWins[selectRounds] && playerWins < minWins[selectRounds])) {
			System.out.println("Computer Won");
			runGame = false; // Stops game 
		}
	}
}
