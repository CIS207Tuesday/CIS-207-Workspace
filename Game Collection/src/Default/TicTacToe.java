package Default;

import java.security.SecureRandom;
import java.util.Scanner;

public class TicTacToe {

	static Scanner input = new Scanner(System.in);
	static SecureRandom random = new SecureRandom();

	private static String mainGrid[][] = { { "A1", "A2", "A3" }, { "B1", "B2", "B3" }, { "C1", "C2", "C3" } }; // Array
																												// that
	private static String grid[][] = mainGrid; // holds
	// value for
	// location
	// on
	// ticTacToe
	// grid
	private static int gameCounter = 0; // Stores number of turns
	private static boolean doInput;
	private static boolean runGame; // Game will run if true

	private static String player1Char = null; // Holds either an X or O string based on what players selects
	private static String player2Char = null;

	public void mainTicTacToe() {

		// Gets input for amount of players
		int playerAmount = SelectGame.getInputFromUser("Enter amount of players (1-2)?", 1, 2);
		boolean isUser = true;

		if (playerAmount == 1) { // Determines if playing against another User or computer
			isUser = false;
		}

		else if (playerAmount == 2) {
			isUser = true;
		}

		System.out.println("\n Would player1 like to be X's or O's"); // Allows player1 to select wether they are X's or
																		// O's
		System.out.println("[1] X's\n[2] O's");
		int charSelect = SelectGame.getInputFromUser("\n Would player1 like to be X's or O's", 1, 2);

		if (charSelect == 1) {
			player1Char = "X ";
			player2Char = "O ";
		}

		else if (charSelect == 2) {
			player1Char = "O ";
			player2Char = "X ";
		}

		runGame = true;
		while (runGame == true) {

			printGrid();
			inputGrid(player1Char, true);
			checkWinner(player1Char);
			if (isUser == true) {
				printGrid();
			}
			inputGrid(player2Char, isUser);
			checkWinner(player2Char);
		}
	}

	public void printGrid() { // Prints out Tic-Tac-Toe grid
		System.out.println("     A   B   C");
		System.out.println("  ________________");
		System.out.println();
		for (int rows = 0; rows < grid.length; rows++) {
			System.out.print((rows + 1) + " | ");
			for (int columns = 0; columns < grid[rows].length; columns++) {
				System.out.print("[" + grid[rows][columns] + "]");
			}
			System.out.print(" |\n");
		}
		System.out.println("  ________________");
	}

	public void inputGrid(String inputGrid, boolean isUser) {
		String select = null;
		doInput = true; // Will loop until switch statement recieves a valid response
		while (doInput == true) {

			if (isUser == true) { // Asks for the user to input where they would like to place their X/O on the
									// grid
				System.out.println("input");
				select = input.next();
			} else {
				// a copy of original grid for computer to select from for input
				String compSelGrid[][] = { { "A1", "A2", "A3" }, { "B1", "B2", "B3" }, { "C1", "C2", "C3" } };
				select = compSelGrid[random.nextInt(3)][random.nextInt(3)];
			}

			if (select.equalsIgnoreCase("A1")) // If input equals a correct spot on grid
				checkInput(select, "A1", inputGrid, 0, 0, isUser); // User input, Case, X/O character, location of row
																	// // on grid, location of column on grid, isUser
			else if (select.equalsIgnoreCase("A2"))
				checkInput(select, "A2", inputGrid, 0, 1, isUser);
			else if (select.equalsIgnoreCase("A3"))
				checkInput(select, "A3", inputGrid, 0, 2, isUser);
			else if (select.equalsIgnoreCase("B1"))
				checkInput(select, "B1", inputGrid, 1, 0, isUser);
			else if (select.equalsIgnoreCase("B2"))
				checkInput(select, "B2", inputGrid, 1, 1, isUser);
			else if (select.equalsIgnoreCase("B3"))
				checkInput(select, "B3", inputGrid, 1, 1, isUser);
			else if (select.equalsIgnoreCase("C1"))
				checkInput(select, "C1", inputGrid, 2, 0, isUser);
			else if (select.equalsIgnoreCase("C2"))
				checkInput(select, "C2", inputGrid, 2, 1, isUser);
			else if (select.equalsIgnoreCase("C3"))
				checkInput(select, "C3", inputGrid, 2, 2, isUser);
			else
				System.out.println("The input you entered was incorrect.");
		}
	}

	public void checkInput(String select, String gridLocation, String inputGrid, int x, int y, boolean isUser) {

		if (select.equalsIgnoreCase(gridLocation)) {
			grid[x][y] = inputGrid;
			doInput = false;
			gameCounter++;
		} else {
			if (isUser == true) {
				System.out.println("That spot was already selected.");
			}
		}
	}

	public void checkWinner(String input) { // Checks to see if there is either a line of X's or O's to determine winner
		// Winning across
		if (grid[0][0] == input && grid[0][1] == input && grid[0][2] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (grid[1][0] == input && grid[1][1] == input && grid[1][2] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (grid[2][0] == input && grid[2][1] == input && grid[2][2] == input) {
			displayWinner(input);
			runGame = false;
		}

		// Winning vertically
		else if (grid[0][0] == input && grid[1][0] == input && grid[2][0] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (grid[0][1] == input && grid[1][1] == input && grid[2][1] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (grid[0][2] == input && grid[1][2] == input && grid[2][2] == input) {
			displayWinner(input);
			runGame = false;
		}

		// Winning diagnolly
		else if (grid[0][0] == input && grid[1][1] == input && grid[2][2] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (grid[0][2] == input && grid[1][1] == input && grid[2][0] == input) {
			displayWinner(input);
			runGame = false;
		}

		else if (gameCounter >= 9) { // If all spots are taken and nobody has won, print this v
			System.out.println("Tie game");
			runGame = false;
		}
	}

	public void displayWinner(String input) {
		String name = null;
		printGrid();
		if (input == player1Char) { // If the character for player1 is passed through the check winner method
			name = "User";
			System.out.println(name + " won!");

		} else if (input == player2Char) { // If the character for player2 is passed through the check winner method
			name = " User2";
			System.out.println(name + " won!");

		}

	}
}