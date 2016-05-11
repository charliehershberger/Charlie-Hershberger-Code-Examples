import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

@SuppressWarnings("serial")

/**
 * 
 * Defines the bullets used to inflict damage on other objects on the game
 * board.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified: 11/12/2015
 */
public class Bullet extends GameBoardObject {
	/**
	 * xVelocity is the velocity of the bullet in the x-plane yVelocity is the
	 * velocity of the bullet in the y-plane
	 */
	private int xVelocity;
	private int yVelocity;
	private Color myColor;

	/**
	 * Constructs the standard bullet in the starting x and y position on the
	 * board and with the specified x and y velocity.
	 * 
	 * @param x
	 *            Xposition
	 * @param y
	 *            Yposition
	 * @param xVelocity
	 *            the velocity of the bullet in the x-plane
	 * @param yVelocity
	 *            the velocity of the bullet in the y-plane
	 * @param board
	 *            the board it is painted on
	 */
	public Bullet(int x, int y, int xVelocity, int yVelocity, Board board) {
		super(x + xVelocity, y + yVelocity, board);
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.myColor = Color.WHITE;
	}

	/**
	 * moves the bullet forward the amount specified by x and y Velocity
	 */
	@Override
	public void updatePosition() {
		this.getBoard().remove(this.getGameX(), this.getGameY());
		if ((getGameX() + this.xVelocity < this.getBoard().getGameWidth()) && (getGameX() + this.xVelocity >= 0)) {
			setGameX(getGameX() + this.xVelocity);
		} else {
			die();
		}
		if (getGameY() + this.yVelocity >= 0) {
			setGameY(getGameY() + this.yVelocity);
		} else {
			die();
		}
		if (!getIsDead()) {
			this.getBoard().addGameBoardObject(this);
		}
	}

	/**
	 * Can update the image of the bullet if a custom one is used
	 */
	@Override
	public void updateImage() {
		return;
	}

	/**
	 * reduces the health of any object it hits, this kills the object if it has
	 * no health
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		hitter.reduceHealth();
		reduceHealth();
		return 0;
	}

	/**
	 * draws bullet on the board
	 **/
	@Override
	public void drawOn(Graphics2D g2) {
		if (!getIsDead()) {
			int objectSize = Board.GAME_OBJECT_SIZE;

			Rectangle me = new Rectangle(this.getGameX() * objectSize, this.getGameY() * objectSize, objectSize,
					objectSize);
			g2.setColor(this.myColor);
			g2.fill(me);
		}
	}
}