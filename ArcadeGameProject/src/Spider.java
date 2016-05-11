import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * Defines the spider, which zigzags across the player area.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modify: 11/12/2015
 */
@SuppressWarnings("serial")
public class Spider extends GameBoardObject {
	// private static image[] = {};
	private int MAX_image;
	private int CUR_image;
	private Color myColor = Color.PINK;
	private int xVelocity;
	private int yVelocity;
	private int timeDivision;

	public static int spiderCounter;

	private final int MAX_TIME_DIVISION = 3;
	private final int SPIDER_KILL_POINTS = 700;

	/**
	 * Constructs the standard Spider object
	 * 
	 * @param x
	 *            position of the spider
	 * @param y
	 *            position of the spider
	 * @param board
	 *            the board the spider is on
	 */
	public Spider(int x, int y, Board board) {
		super(x, y, board);
		Spider.spiderCounter++;

		if (getGameX() == 0)
			this.xVelocity = 1;
		else
			this.xVelocity = -1;
		if (getGameY() == getBoard().getGameHeight() - 1)
			this.yVelocity = -1;
		else
			this.yVelocity = 1;
	}

	/**
	 * this runs the animation of the spider by progressing the animation 1
	 * frame per run call
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
	 * Moves Spider
	 **/
	@Override
	public void updatePosition() {
		if (!getHasMoved() && this.timeDivision == 0) {
			this.timeDivision = this.MAX_TIME_DIVISION;
			if (getBoard().getGameBoardObject()[getGameX()][getGameY()] == this) {
				getBoard().remove(getGameX(), getGameY());
			}
			setGameY(getGameY() + this.yVelocity);
			setGameX(getGameX() + this.xVelocity);
			if (getGameY() <= (2 * getBoard().getGameHeight() / 3)) {
				this.yVelocity = 1;
			}
			if (getGameY() == getBoard().getGameHeight() - 1) {
				this.yVelocity = -1;
			}
			if (getGameX() == 0) {
				this.xVelocity = 1;
			}
			if (getGameX() == getBoard().getGameWidth() - 1) {
				this.xVelocity = -1;
			}
			setHasMoved(true);
		} else if (getHasMoved()) {
			// Nothing
		} else {
			this.timeDivision--;
		}
		getBoard().addGameBoardObject(this);
	}

	/**
	 * takes damage when hit by the bullet and kills mushrooms
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		if (hitter != null) {
			if (hitter.isMushroom())
				hitter.die();
		}
		return 0;
	}

	/**
	 * Overrides the die() method in GameBoardObject to assign points for
	 * killing a Spider
	 */
	@Override
	public void die() {
		Spider.spiderCounter--;
		this.getBoard().addScore(this.SPIDER_KILL_POINTS, this.getGameX(), this.getGameY());
		super.die();
		getBoard().remove(getGameX(), getGameY());
	}

	/**
	 * Draws the spider on the frame
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
