import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * Defines the scorpion. Scorpions poison Mushrooms that they touch and those
 * cause the centipede to fall to the bottom of the player board when they're
 * hit by it.
 * 
 * @author Sets whether player will move right when updatePosition is called
 *         Modified 11/12/2015
 */
@SuppressWarnings("serial")
public class Scorpion extends GameBoardObject {

	// private static image[] = {};
	private int MAX_image;
	private int CUR_image;
	private Color myColor = Color.GREEN;
	private boolean leavingBoard = false;

	private final int SCORPION_KILL_POINTS = 800;

	/**
	 * Constructs a Scorpion at the specified X and Y coordinates
	 * 
	 * @param x
	 *            position of the Scorpion
	 * @param y
	 *            position of the Scorpion
	 * @param board
	 *            Board that the scorpion is on
	 */
	public Scorpion(int x, int y, Board board) {
		super(x, y, board);
	}

	/**
	 * Cycles through images for scorpion if they exist
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
	 * Moves the scorpion
	 */
	@Override
	public void updatePosition() {
		if (getBoard().getGameBoardObject()[getGameX()][getGameY()] == this) {
			getBoard().remove(getGameX(), getGameY());
		}
		if (getGameY() % 2 != 0) {
			if (getGameX() < getBoard().getGameWidth() - 1) {
				setGameX(getGameX() + 1);
			} else {
				this.leavingBoard = true;
				die();
			}
		} else {
			if (getGameX() > 0) {
				setGameX(getGameX() - 1);
			} else {
				this.leavingBoard = true;
				die();
			}
		}
		getBoard().addGameBoardObject(this);
	}

	/**
	 * Takes damage when Scorpion is hit and poisons mushrooms when it hits them
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		if (hitter.isMushroom()) {
			((Mushroom) hitter).setPoison(true);
		}
		return 0;
	}

	/**
	 * Overrides the die method in GameBoardObject to assign points for killing
	 * the scorpion
	 */
	@Override
	public void die() {
		super.die();
		if (!this.leavingBoard) {
			this.getBoard().addScore(this.SCORPION_KILL_POINTS, getGameX(), getGameY());
		}
		getBoard().remove(getGameX(), getGameY());
	}

	/**
	 * Draws the scorpion
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		int objectSize = Board.GAME_OBJECT_SIZE;

		Rectangle me = new Rectangle(this.getGameX() * objectSize, this.getGameY() * objectSize, objectSize,
				objectSize);
		g2.setColor(this.myColor);
		g2.fill(me);

	}

}
