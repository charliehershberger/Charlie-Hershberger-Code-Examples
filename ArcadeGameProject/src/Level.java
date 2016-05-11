import java.io.EOFException;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Scanner;

/**
 * 
 * Loads levels from assigned text files and sends them to Board.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */
public class Level {

	private final Board theBoard;
	private int levelNumber;
	private String filename;

	/**
	 * Constructs a level object
	 * 
	 * @param board
	 *            The board it is drawing on
	 * @param levelNumberToSet
	 *            The level number
	 */
	public Level(Board board, int levelNumberToSet) {

		this.theBoard = board;
		this.levelNumber = levelNumberToSet;
		this.filename = ("./src/Level" + this.levelNumber + ".txt");

	}

	/**
	 * makes the board based on the level files and returns a new 2D array
	 * representing the game board
	 * 
	 * @return a 2D array representing the game board based on the text file for
	 *         the level number of the level
	 */
	@SuppressWarnings("resource")
	public GameBoardObject[][] makeLevel() {

		GameBoardObject[][] gameArray;
		int gameArrayWidth;
		int gameArrayHeight;

		int type = 0;
		int xBoard = 0;
		int yBoard = 0;

		// take in the file to process
		File inputFile = new File(this.filename);
		// make a scanner
		Scanner inScanner;
		try {
			// extra try to catch errors in scanner
			inScanner = new Scanner(inputFile);

			try {
				// throw errors when there are problems
				if (!(inScanner.hasNextInt()))
					throw new EOFException("Missing Width");

				gameArrayWidth = inScanner.nextInt();

				if (!(inScanner.hasNextInt()))
					throw new EOFException("Missing Height");

				gameArrayHeight = inScanner.nextInt();

				gameArray = new GameBoardObject[gameArrayHeight][gameArrayWidth];
				// parses the level for parts
				while (inScanner.hasNextInt()) {
					// does a type exist, if so set a variable
					if (!(inScanner.hasNextInt()))
						throw new EOFException("Missing Type");
					type = inScanner.nextInt();

					// does a x exist, if so set a variable
					if (!(inScanner.hasNextInt()))
						throw new EOFException("Missing X Position");
					xBoard = inScanner.nextInt();
					// does a y exist, if so set a variable
					if (!(inScanner.hasNextInt()))
						throw new EOFException("Missing Y Position");
					yBoard = inScanner.nextInt();
					// is the placement valid?

					if (!(isValidInArray(xBoard, yBoard, gameArrayHeight, gameArrayWidth))) {
						throw new InvalidParameterException("Specified Location not on board");
					}
					// create the object based on the signature
					if (type == 1) {
						gameArray[xBoard][yBoard] = new Mushroom(xBoard, yBoard, this.theBoard);

					}
					if (type == 2) {
						gameArray[xBoard][yBoard] = new Flea(xBoard, yBoard, this.theBoard);

					}
					if (type == 3) {
						gameArray[xBoard][yBoard] = new Scorpion(xBoard, yBoard, this.theBoard);
					}
					if (type == 4) {
						gameArray[xBoard][yBoard] = new Spider(xBoard, yBoard, this.theBoard);
					}

					if (type == 5) {
						gameArray[xBoard][yBoard] = new Centipede(2, xBoard, yBoard, this.theBoard);

					}

				}

				return gameArray;

			}

			// alway close the scanner
			finally {
				inScanner.close();
			}

		}

		catch (Exception e) {
			System.out.println(e.getMessage());

			System.exit(1);
			return null;
		}

	}

	/**
	 * Determines whether an object can be placed on the game board based on
	 * size of board given
	 * 
	 * @param boardLoc
	 *            Dimension of test location
	 * @return Boolean Whether testLoc will fit on size of board given in text
	 *         file
	 */
	@SuppressWarnings("static-method")
	private boolean isValidInArray(int inWidth, int inHeight, int gameArrayHeight, int gameArrayWidth) {

		return (inHeight >= 0 && inWidth >= 0 && inHeight < gameArrayHeight && inWidth < gameArrayWidth);
	}

	/**
	 * Static method used to determine whether a level file exists for a certain
	 * level number
	 * 
	 * @param levelNumber
	 *            the number of the file for the level
	 * 
	 * @return boolean is this a valid file?
	 */
	public static boolean isValidLevelFile(int levelNumber) {
		return new File("./src/Level" + levelNumber + ".txt").isFile();
	}
}
