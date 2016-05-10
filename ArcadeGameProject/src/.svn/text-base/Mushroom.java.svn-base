import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * 
 * Represents the Mushroom object
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */
@SuppressWarnings("serial")
public class Mushroom extends GameBoardObject {

	private Color myColor;
	private boolean isPoisoned = false;

	public static int mushroomCount;

	private final int MUSHROOM_KILL_POINTS = 300;

	/**
	 * Constructs the basic Mushroom object
	 * 
	 * @param x
	 *            position of the mushroom
	 * @param y
	 *            position of the mushroom
	 * @param board
	 *            that the mushroom is drawn on
	 */
	public Mushroom(int x, int y, Board board) {
		super(x, y, board);
		this.setHealth(4);
		this.myColor = Color.RED;
		Mushroom.mushroomCount++;
	}

	/**
	 * Changes image of Mushroom when enabled
	 */
	@Override
	public void updateImage() {
		return;
	}

	/**
	 * 
	 * Returns whether the mushroom is poisoned
	 *
	 * @return Boolean on whether the mushroom is poisoned
	 */
	public boolean getIsPoisoned() {
		return this.isPoisoned;
	}

	/**
	 * Ensures that mushroom stays on board when not destroyed
	 */
	@Override
	public void updatePosition() {
		if (!getIsDead())
			getBoard().addGameBoardObject(this);
		return;
	}

	/**
	 * 
	 * Poisons mushroom, called when a scorpion hits the mushroom
	 *
	 */
	public void setPoison(boolean poison) {
		this.isPoisoned = poison;
	}

	/**
	 * Returns true to indicate that this is a Mushroom object
	 */
	@Override
	public boolean isMushroom() {
		return true;
	}

	/**
	 * Reduces the health of the mushroom
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		return 0;
	}

	/**
	 * Kills mushroom by removing it from the board that it is on
	 */
	@Override
	public void die() {
		Mushroom.mushroomCount--;
		super.die();
		this.getBoard().addScore(this.MUSHROOM_KILL_POINTS, this.getGameX(), this.getGameY());
		this.getBoard().remove(this.getGameX(), this.getGameY());
	}

	/**
	 * Draws the mushroom
	 */
	@Override
	public void drawOn(Graphics2D g2) {

		int objectSize = Board.GAME_OBJECT_SIZE;

		Rectangle me = new Rectangle(this.getGameX() * objectSize, this.getGameY() * objectSize, objectSize,
				(objectSize * this.getHealth()) / 4);

		g2.setColor(this.myColor);
		if (this.isPoisoned) {
			g2.draw(me);
		} else {
			g2.fill(me);
		}
	}
}