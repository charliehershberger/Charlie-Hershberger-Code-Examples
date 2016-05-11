import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 * 
 * The Board on which every GameBoardObject is held, allowing for the game to be
 * played.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modify: 11/12/2015
 */
@SuppressWarnings("serial")
public class Board extends JComponent {

	public static final int DEFAULT_LEVEL = 0;

	private ArrayList<GameBoardObject> aliveOffBoard;
	private JFrame myFrame;
	private GameBoardObject[][] gameBoard;
	private ArrayList<Level> levels;
	private List<PersistantString> pointStrings;
	private Graphics2D g2;
	private SplashScreen splashScreen;
	private Leaderboard leaderBoard;
	private Font scoreFont;
	private EnemySeeder eSeed;
	private InputMap iMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
	private ActionMap aMap = this.getActionMap();

	private Action myKeyAction;

	private int gameWidth;
	private int gameHeight;
	private int currentLevel;
	private int score;
	private int lives;
	private int scoreLifeIncrement;

	private boolean isPaused = false;
	private boolean splashDraw = false;
	private boolean leaderDraw = false;

	public final int JFRAME_BORDER_WIDTH = 18;
	public final int JFRAME_BORDER_HEIGHT = 45;
	private final int FRAMES_PER_SECOND = 60;
	private final int UPDATE_INTERVAL_MS = 1000 / this.FRAMES_PER_SECOND;
	private final int STATS_BOX_HEIGHT = 20;
	private final int STARTING_LIVES = 5;
	private final int FREE_LIFE_POINTS = 100000;

	private final Color BACKGROUND_COLOR = Color.BLACK;
	private final Color STATS_BOX_COLOR = Color.GREEN;

	public static final int GAME_OBJECT_SIZE = 10;

	/**
	 * 
	 * Builds the "Board" object. This holds a representaton of the game baord,
	 * populates it with level information and processes the objects on the
	 * board as well as drawing them.
	 *
	 * @param frame
	 *            The frame that the board is located in
	 */
	public Board(JFrame frame) {
		this.aliveOffBoard = new ArrayList<>();
		this.pointStrings = Collections.synchronizedList(new ArrayList<PersistantString>());
		this.myFrame = frame;
		this.myKeyAction = new keyAction(this);
		this.eSeed = new EnemySeeder(this);
		this.scoreFont = new Font("Helvetica", Font.BOLD, 20);
		this.splashScreen = new SplashScreen(frame, this);
		this.leaderBoard = new Leaderboard(frame);

		bindKeys();
		loadLevels();
		resetGame(frame);

		Runnable tickTock = new Runnable() {
			@SuppressWarnings("synthetic-access")
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(Board.this.UPDATE_INTERVAL_MS);
						timePassed();
					}
				} catch (InterruptedException exception) {
					// Stop when interrupted
				}

			}
		};

		new Thread(tickTock).start();
	}

	/**
	 * 
	 * Called when the timer fires and updates all non null objects on the board
	 * Also takes care of disposing the objects that remained alive after they
	 * left the board.
	 *
	 */
	public void timePassed() {
		if (!(this.isPaused) && !(this.splashDraw) && !(this.leaderDraw)) {
			checkWin();
			this.eSeed.addEnemies();

			for (int i = 0; i < this.gameHeight; i++) {
				if (i % 2 == 0) {
					for (int j = 0; j < this.gameWidth; j++) {
						if (this.gameBoard[j][i] != null) {
							this.gameBoard[j][i].run();
						}
					}
				} else {
					for (int j = this.gameWidth - 1; j >= 0; j--) {
						if (this.gameBoard[j][i] != null) {
							this.gameBoard[j][i].run();
						}
					}
				}
			}

			GameBoardObject offBoardObject;
			ArrayList<GameBoardObject> waitingObject = new ArrayList<>();
			while (!this.aliveOffBoard.isEmpty()) {
				waitingObject.add(this.aliveOffBoard.remove(0));
			}
			while (!waitingObject.isEmpty()) {
				offBoardObject = waitingObject.remove(0);
				offBoardObject.run();
			}
			int numNotMushrooms = 0;
			for (int i = 0; i < this.gameWidth; i++) {
				for (int j = 0; j < this.gameHeight; j++) {
					if (this.gameBoard[i][j] != null) {
						this.gameBoard[i][j].setHasMoved(false);
						if (!this.gameBoard[i][j].isMushroom()) {
							numNotMushrooms++;
						}
					}
				}
			}
			if (numNotMushrooms < 2) {
				Centipede.centipedeCounter = 0;
				Spider.spiderCounter = 0;
			}
			for (GameBoardObject object : this.aliveOffBoard) {
				object.setHasMoved(false);
			}
		}
		repaint();
	}

	/**
	 * 
	 * Adds an object to the game board. If it is alive and in a valid spot on
	 * the 2D board array, then it is placed there. If something else is there
	 * and a collision happens, both have their getHit methods called and the
	 * "hitter" passes to them. If one of the objects hasn't died by collision
	 * time but is off the board, then it will be put in the AliveOffBoard list.
	 * If there is no collision, it is added normally.
	 *
	 * @param object
	 *            The GameBoardObject object to add
	 */
	public void addGameBoardObject(GameBoardObject object) {
		if (isValidOnBoard(object.getGameX(), object.getGameY())) {
			GameBoardObject otherObject = this.gameBoard[object.getGameX()][object.getGameY()];
			if (object.getIsDead()) {
				// Nothing
			} else if (otherObject == object) {
				// Nothing
			} else if (otherObject != null && isValidOnBoard(object.getGameX(), object.getGameY())) {
				otherObject.getHit(object);
				object.getHit(otherObject);
				if (!object.getIsDead()) {
					this.aliveOffBoard.add(object);
				}
				if (!otherObject.getIsDead()) {
					this.aliveOffBoard.add(otherObject);
				}
			} else {
				this.gameBoard[object.getGameX()][object.getGameY()] = object;
			}
		}
	}

	/**
	 * 
	 * Loads the 2D array game board from a level object and places the player
	 * on it as well as setting the frame size appropriately
	 *
	 * @param levelToPlay
	 */
	private void selectLevel(int levelToPlay) {
		Centipede.centipedeCounter = 0;
		Mushroom.mushroomCount = 0;
		Spider.spiderCounter = 0;

		while (!this.aliveOffBoard.isEmpty()) {
			this.aliveOffBoard.remove(0);
		}

		this.gameBoard = this.levels.get(levelToPlay).makeLevel();
		this.gameWidth = this.gameBoard.length;
		this.gameHeight = this.gameBoard[0].length;

		int c = 1;
		boolean done = false;

		while (!done) {
			if (this.gameBoard[0][this.gameHeight - c] == null) {
				this.gameBoard[0][this.gameHeight - c] = new Player(this.myFrame, this, 0, this.gameHeight - 1);
				done = true;
			} else {
				c--;
			}
		}

		this.myFrame.setSize((this.gameWidth) * (Board.GAME_OBJECT_SIZE) + this.JFRAME_BORDER_WIDTH,
				(this.gameHeight) * (Board.GAME_OBJECT_SIZE) + this.JFRAME_BORDER_HEIGHT + this.STATS_BOX_HEIGHT);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.myFrame.setLocation(dim.width / 2 - this.myFrame.getSize().width / 2,
				dim.height / 2 - this.myFrame.getSize().height / 2);
		this.myFrame.setVisible(true);
		this.myFrame.validate();
		this.myFrame.repaint();
	}

	/**
	 * 
	 * Populates local Level arrayList with as many Level objects as there are
	 * numerical level files.
	 *
	 */
	private void loadLevels() {
		this.levels = new ArrayList<>();
		boolean done = false;
		int i = 0;

		while (!done) {
			if (Level.isValidLevelFile(i)) {
				this.levels.add(new Level(this, i));
				i++;
			} else {
				done = true;
			}
		}
	}

	/**
	 * 
	 * Moves game down a level
	 *
	 */
	public void lowerLevel() {
		if (!(this.currentLevel == 0)) {
			this.currentLevel--;
			selectLevel(this.currentLevel);
		}
	}

	/**
	 * 
	 * Moves game to next level
	 *
	 */
	public void raiseLevel() {
		if (!(this.currentLevel == this.levels.size() - 1)) {
			this.currentLevel++;
			selectLevel(this.currentLevel);

		} else {
			changeLives(0);
		}
	}

	/**
	 * 
	 * Restores living Mushrooms to health upon a player's death but leaves
	 * other values intact.
	 *
	 */
	public void reloadBoard() {
		for (int i = 0; i < this.gameWidth; i++) {
			for (int j = 0; j < this.gameHeight; j++) {
				if (this.gameBoard[i][j] != null) {
					if (this.gameBoard[i][j].isMushroom()) {
						this.score += 50 * (4 - this.gameBoard[i][j].getHealth());
						this.gameBoard[i][j].setHealth(4);
						((Mushroom) this.gameBoard[i][j]).setPoison(false);
					}
				}
			}
		}
		addGameBoardObject(new Player(this.myFrame, this, 0, this.gameHeight - 1));
		this.changeLives(this.getLives() - 1);
		if (this.lives == 1) {
			drawSplash(60);
		} else {
			drawSplash((6 - this.lives) * 60);
		}
	}

	/**
	 * 
	 * Initializes major values for the game. Called when game starts and when
	 * resetting after a loss.
	 *
	 * @param frame
	 */
	public void resetGame(JFrame frame) {

		this.currentLevel = DEFAULT_LEVEL;
		if (this.score != 0) {
			this.leaderBoard.addScore(this.score);
		}
		this.score = 0;
		this.lives = this.STARTING_LIVES;
		selectLevel(this.currentLevel);

		drawSplash(300);
		drawLeader(400);

	}

	/**
	 * 
	 * Adds a value to the score. Also makes this number a persistant string to
	 * be displayed on the field at the location the scoring happened.
	 *
	 * @param pointsToAdd
	 *            Amount of points to add to the score
	 * @param x
	 *            X position to display number on screen
	 * @param y
	 *            Y position to display number on screen
	 */
	public void addScore(int pointsToAdd, int x, int y) {
		this.score += pointsToAdd;
		this.scoreLifeIncrement += pointsToAdd;
		String pointsString = "" + pointsToAdd;
		PersistantString diePointsString = new PersistantString(pointsString, 30, x, y);
		this.pointStrings.add(diePointsString);

		if (this.scoreLifeIncrement >= this.FREE_LIFE_POINTS) {
			this.scoreLifeIncrement = 0;
			this.changeLives(this.lives + 1);
		}
	}

	/**
	 * 
	 * Change the number of lives the player has. If it drops to zero, then the
	 * game is reset.
	 *
	 * @param num
	 *            Number of lives to set player lives to.
	 */
	public void changeLives(int num) {
		this.lives = num;
		if (this.lives == 0) {
			resetGame(this.myFrame);
		}
	}

	/**
	 * 
	 * Helper method to check if all monsters that have "permanence" are dead.
	 * If they are, it raises the level for a win.
	 *
	 */
	private void checkWin() {
		if (Centipede.centipedeCounter == 0 && Spider.spiderCounter == 0) {
			this.raiseLevel();
		}
	}

	/**
	 * returns the game board
	 * 
	 * @return gameBoard
	 */
	public GameBoardObject[][] getGameBoardObject() {
		return this.gameBoard;
	}

	/**
	 * 
	 * Returns the height of the game board
	 *
	 * @return the height of the game board
	 */
	public int getGameHeight() {
		return this.gameHeight;
	}

	/**
	 * 
	 * Returns the width of the game board
	 *
	 * @return the width of the game board
	 */
	public int getGameWidth() {
		return this.gameWidth;
	}

	/**
	 * 
	 * Returns the number of lives that the player has
	 *
	 * @return
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * 
	 * Returns whether a location on the board is null
	 *
	 * @param testDim
	 *            Dimension to test for whether a location on the board is null
	 * @return Whether or not the specified location is null
	 */
	public boolean isEmpty(int testX, int testY) {
		if (this.gameBoard[testX][testY] == null) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Returns whether a location on the board is valid (Inside board width &
	 * height)
	 *
	 * @param gameX
	 *            X location to check
	 * @param gameY
	 *            Y location to check
	 * @return Whether location is inside board
	 */
	public boolean isValidOnBoard(int gameX, int gameY) {
		return (gameX >= 0 && gameY >= 0 && gameX < this.gameWidth && gameY < this.gameHeight);
	}

	/**
	 * 
	 * Removes an item from the game board by setting it's position to null
	 *
	 * @param dieDim
	 *            The dimension at which to remove the item
	 */
	public void remove(int remX, int remY) {
		this.gameBoard[remX][remY] = null;
	}

	/**
	 * 
	 * Gets whether the game is paused
	 *
	 * @return A boolean indicating whether the game is paused
	 */
	public boolean getIsPaused() {
		return this.isPaused;
	}

	/**
	 * 
	 * Pauses and unpauses game
	 *
	 * @param pauseState
	 *            A boolean determining whether to pause or unpause the game
	 */
	public void setIsPaused(boolean pauseState) {
		this.isPaused = pauseState;
	}

	/**
	 * 
	 * Starts the drawing of the splash screen
	 *
	 * @param framesToSplash
	 *            Amount of time for screen to be displayed
	 */
	private void drawSplash(int framesToSplash) {
		this.splashScreen.setFrames(framesToSplash);
		this.splashDraw = true;

	}

	/**
	 * 
	 * Starts the drawing of the leaderboard.
	 *
	 * @param framesToLead
	 *            Amount of time for the leaderboard to be displayed
	 */
	private void drawLeader(int framesToLead) {
		this.leaderBoard.setFrames(framesToLead);
		this.leaderDraw = true;

	}

	/**
	 * 
	 * Draws board in frame. Will also call drawing methods in SplashScreen or
	 * Leaderboard if their booleans are true.
	 *
	 * @param g
	 *            Graphics object to draw board on
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.g2 = (Graphics2D) g;

		// SPLASH DRAW
		if (this.splashDraw /* ||this.isPaused */) {
			this.splashDraw = this.splashScreen.drawScreen(this.g2);
			return;
		}

		// LEADER DRAW
		if (this.leaderDraw) {
			this.leaderDraw = this.leaderBoard.drawScreen(this.g2);
			return;
		}

		// NORMAL DRAW
		if (!this.leaderDraw && !this.splashDraw) {
			// Set size & center Frame
			this.myFrame.setSize((this.gameWidth) * (Board.GAME_OBJECT_SIZE) + this.JFRAME_BORDER_WIDTH,
					(this.gameHeight) * (Board.GAME_OBJECT_SIZE) + this.JFRAME_BORDER_HEIGHT + this.STATS_BOX_HEIGHT);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.myFrame.setLocation(dim.width / 2 - this.myFrame.getSize().width / 2,
					dim.height / 2 - this.myFrame.getSize().height / 2);

			// Background Box
			Rectangle background = new Rectangle(0, 0, this.gameWidth * Board.GAME_OBJECT_SIZE,
					this.gameHeight * Board.GAME_OBJECT_SIZE);
			this.g2.setColor(this.BACKGROUND_COLOR);
			this.g2.fill(background);

			// Score Box
			Rectangle statsBox = new Rectangle(0, this.gameHeight * Board.GAME_OBJECT_SIZE,
					this.gameWidth * Board.GAME_OBJECT_SIZE, this.STATS_BOX_HEIGHT);
			this.g2.setColor(this.STATS_BOX_COLOR);
			this.g2.fill(statsBox);

			// Score Itself
			String scoreString = "Score: " + this.score;
			this.g2.setFont(this.scoreFont);
			this.g2.setColor(Color.RED);
			this.g2.drawString(scoreString, 0, this.gameHeight * Board.GAME_OBJECT_SIZE + this.STATS_BOX_HEIGHT - 3);

			String lifeString = "Lives: " + this.lives;
			this.g2.drawString(lifeString, this.gameWidth * Board.GAME_OBJECT_SIZE - 100,
					this.gameHeight * Board.GAME_OBJECT_SIZE + this.STATS_BOX_HEIGHT - 3);

			// Print Persistant Strings
			this.g2.setColor(Color.RED);
			if (!(this.pointStrings.isEmpty())) {
				for (int i = 0; i < this.pointStrings.size(); i++) {
					PersistantString PString = this.pointStrings.get(i);
					if (PString.stepLife()) {
						this.g2.drawString(PString.getData(), PString.getXpos() * Board.GAME_OBJECT_SIZE,
								PString.getYpos() * Board.GAME_OBJECT_SIZE);
					} else {
						this.pointStrings.remove(i);
					}
				}
			}

			// Draw the GameBoardObjects
			for (int i = 0; i < this.gameWidth; i++) {
				for (int j = 0; j < this.gameHeight; j++) {
					if (this.gameBoard[i][j] != null) {
						this.gameBoard[i][j].drawOn(this.g2);
					}
				}
			}
		}
	}

	/**
	 * 
	 * Key binding action class
	 *
	 */
	class keyAction extends AbstractAction {

		Board myBoard;

		keyAction(Board board) {
			this.myBoard = board;
		}

		/**
		 * Called when a bound key is pressed
		 */
		@Override
		public void actionPerformed(ActionEvent tf)

		{
			if (tf.getActionCommand().equals("u")) {
				this.myBoard.raiseLevel();
			}
			if (tf.getActionCommand().equals("d")) {
				this.myBoard.lowerLevel();
			}
			if (tf.getActionCommand().equals("p")) {
				this.myBoard.setIsPaused(!(this.myBoard.getIsPaused()));
			}

		}

	}

	/**
	 * 
	 * Binds Keys on Board's I/A maps
	 *
	 */
	private void bindKeys() {
		this.iMap.put(KeyStroke.getKeyStroke("U"), "upress");
		this.iMap.put(KeyStroke.getKeyStroke("D"), "dpress");
		this.iMap.put(KeyStroke.getKeyStroke("P"), "ppress");
		this.aMap.put("upress", this.myKeyAction);
		this.aMap.put("dpress", this.myKeyAction);
		this.aMap.put("ppress", this.myKeyAction);
	}

}