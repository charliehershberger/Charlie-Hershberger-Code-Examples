import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

/**
 * 
 * Represents the "Flea" enemy which drops vertically and adds mushrooms to the
 * board.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */

@SuppressWarnings("serial")
public class Flea extends GameBoardObject {
	// private static image[] = {};
	private int MAX_image;
	private int CUR_image;
	private Color myColor = Color.YELLOW;
	private Random generator;
	private boolean leavingBoard = false;

	private final double MUSHROOM_DROP_RATE = .07;
	private final int FLEA_KILL_POINTS = 900;

	/**
	 * Constructs the standard Flea
	 * 
	 * @param x
	 *            position of the flea
	 * @param y
	 *            position of the flea
	 * @param board
	 *            that you are drawing on
	 */
	public Flea(int x, int y, Board board) {
		super(x, y, board);
		this.generator = new Random();
	}

	/**
	 * Animates the flea by moving the animation forward one frame every run
	 * call
	 * 
	 */
	@Override
	public void updateImage() {
		if (this.CUR_image == this.MAX_image) {
			this.CUR_image = 0;
		} else {
			this.CUR_image++;
		}
	}

	/**
	 * Updates the position of the flea
	 */
	@Override
	public void updatePosition() {
		if (!getHasMoved()) {
			if (getGameY() < getBoard().getGameHeight() - 1) {
				setGameY(getGameY() + 1);
				if (getBoard().getGameBoardObject()[getGameX()][getGameY() - 1] == this) {
					getBoard().remove(getGameX(), getGameY() - 1);
				}
				if (this.generator.nextDouble() < this.MUSHROOM_DROP_RATE)

					if (getBoard().isEmpty(getGameX(), getGameY())) {
						getBoard().addGameBoardObject(new Mushroom(getGameX(), getGameY() - 1, getBoard()));
					}

			} else {
				this.leavingBoard = true;
				die();
			}
			setHasMoved(true);
		}
		getBoard().addGameBoardObject(this);
	}

	/**
	 * Called when the object is hit by a bullet
	 */
	@Override
	public int getHit(GameBoardObject hitter) {

		return 0;
	}

	/**
	 * Kills Flea by removing it from game board
	 */
	@Override
	public void die() {
		super.die();
		if (!this.leavingBoard) {
			this.getBoard().addScore(this.FLEA_KILL_POINTS, this.getGameX(), this.getGameY());
		}
		getBoard().remove(getGameX(), getGameY());
	}

	/**
	 * Draws flea on the game board
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		int objectSize = Board.GAME_OBJECT_SIZE;

		Rectangle me = new Rectangle(getGameX() * objectSize, getGameY() * objectSize, objectSize, objectSize);
		g2.setColor(this.myColor);
		g2.fill(me);

	}

}
