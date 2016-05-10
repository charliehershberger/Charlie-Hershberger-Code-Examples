import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JComponent;

/**
 * 
 * Defines any object existing on the board.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */
@SuppressWarnings("serial")
public abstract class GameBoardObject extends JComponent {
	private int gameX;
	private int gameY;
	private int health;
	private boolean isDead;
	private Shape image;
	private Board board;
	private boolean hasMoved;

	/**
	 * 
	 * Creates a new GameBoardObject.
	 *
	 * @param board
	 *            The board on which the GameBoardObject is created.
	 */
	public GameBoardObject(int inX, int inY, Board board) {
		this.board = board;
		this.gameX = inX;
		this.gameY = inY;
		this.health = 1;
		this.hasMoved = false;
	}

	/**
	 * 
	 * Tells if this GameBoardObject is a mushroom.
	 *
	 * @return Whether object is a mushroom
	 */
	public boolean isMushroom() {
		return false;
	}

	/**
	 * 
	 * Tells if this GameBoardObject is a player.
	 *
	 * @return Whether object is a player
	 */
	public boolean isMuschroom() {
		return false;
	}

	/**
	 * 
	 * Displays the proper image, allowing for cycling through animations.
	 *
	 */
	public abstract void updateImage();

	/**
	 * 
	 * Changes the position of the given GameBoardObject.
	 *
	 */
	public abstract void updatePosition();

	/**
	 * 
	 * Tells if the GameBoardObject is hitting another GameBoardObject
	 *
	 * @return points value awarded for the death of the given GameBoardObject
	 */
	public abstract int getHit(GameBoardObject hitter);

	/**
	 * 
	 * Sets dead boolean to true when object is killed
	 *
	 */
	public void die() {
		this.isDead = true;
	}

	/**
	 * 
	 * Put here a description of what this method does.
	 *
	 * @return boolean indicating whether object is dead
	 */
	public boolean getIsDead() {
		return this.isDead;
	}

	/**
	 * 
	 * Returns the image to be displayed.
	 *
	 * @return The current image
	 */
	public Shape returnImage() {

		return this.image;
	}

	/**
	 * 
	 * Returns the game board that the GameBoardObject is on
	 *
	 * @return the board that the GameBoardObject is on
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * 
	 * Returns where the GameBoardObject is, in terms of x.
	 *
	 * @return x-value of the GameBoardObject's position
	 */
	public int getGameX() {
		return this.gameX;
	}

	/**
	 * 
	 * Returns where the GameBoardObject is, in terms of y.
	 *
	 * @return y-value of the GameBoardObject's position
	 */
	public int getGameY() {
		return this.gameY;
	}

	/**
	 * 
	 * Allows for the changing of position in the x-direction.
	 *
	 * @param x
	 *            x-value to be set
	 */
	public void setGameX(int x) {
		this.gameX = x;
	}

	/**
	 * 
	 * Allows for the changing of position in the y-direction.
	 *
	 * @param y
	 *            y-value to be set
	 */
	public void setGameY(int y) {
		this.gameY = y;
	}

	/**
	 * 
	 * Does all the things that a character should do per step Called by
	 * Boards's timer method
	 */
	public void run() {
		this.updatePosition();
		this.updateImage();
	}

	public abstract void drawOn(Graphics2D g2);

	/**
	 * 
	 * Decreases object health by one
	 * 
	 */
	public void reduceHealth() {
		this.health--;
		if (this.health == 0)
			die();
	}

	/**
	 * 
	 * Sets whether the object has moved
	 *
	 * @param moved
	 *            Boolean indicating whether the object has moved
	 */
	public void setHasMoved(boolean moved) {
		this.hasMoved = moved;
	}

	/**
	 * 
	 * Returns whether the object has moved.
	 *
	 * @return Whether the object has moved
	 */
	public boolean getHasMoved() {
		return this.hasMoved;
	}

	/**
	 * Returns the health of the object
	 * 
	 * @return health
	 * 
	 */
	public int getHealth() {
		return this.health;
	}

	/**
	 * Sets the health of the object allows for variable health
	 * 
	 * @return health
	 * 
	 */
	public void setHealth(int health) {
		this.health = health;
	}
}