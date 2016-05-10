import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/**
 * 
 * The main antagonist of the game "Centipede." Has standard centipede motion.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */

@SuppressWarnings("serial")
public class Centipede extends GameBoardObject {
	private int direction;
	private int timeDivision;
	private static int left = 2;
	private static int right = 0;
	private static int down = 3;
	private boolean goingDown;
	private boolean poisoned = false;

	public static int centipedeCounter;

	private final int MAX_TIME_DIVISION = 5;
	private final int CENTIPEDE_KILL_SCORE = 1000;

	/**
	 * 
	 * Constructs a centipede part at the specified x and y coordinates and
	 * gives it a direction.
	 *
	 * @param direction
	 *            The centipede int direction for the centipede piece to move
	 * @param x
	 *            The game board x to place the centipede piece at
	 * @param y
	 *            The game board y to place the centipede piece at
	 * @param board
	 *            The board that the centipede is on
	 */
	public Centipede(int direction, int x, int y, Board board) {
		super(x, y, board);
		this.direction = direction;
		this.goingDown = true;
		Centipede.centipedeCounter++;
	}

	/**
	 * Tells the centipede if it is poisoned or not, if it is it will move
	 * straight down
	 */
	public void poison() {
		this.poisoned = true;
	}

	/**
	 * Updates the image, can be used for animation
	 */
	@Override
	public void updateImage() {
		return;
	}

	/**
	 * Facilitates the dropping of mushrooms where the centipede piece gets shot
	 * by overriding the GameBoardObject die() method. Also decrements the
	 * centipede piece counter in Board.
	 */
	@Override
	public void die() {
		Centipede.centipedeCounter--;
		super.die();
		this.getBoard().addScore(this.CENTIPEDE_KILL_SCORE, this.getGameX(), this.getGameY());
		getBoard().remove(getGameX(), getGameY());
		getBoard().addGameBoardObject(new Mushroom(getGameX(), getGameY(), getBoard()));
	}

	/**
	 * Moves the centipede, called when GameBoardObject's run() is called by
	 * timer
	 * 
	 */
	@Override
	public void updatePosition() {
		if (!getHasMoved()) {
			if (getBoard().getGameBoardObject()[getGameX()][getGameY()] == this) {
				getBoard().remove(getGameX(), getGameY());
			}
			if (this.timeDivision == 0) {
				this.timeDivision = this.MAX_TIME_DIVISION;
				if (this.direction == left) {
					if (this.getGameX() - 1 >= 0 && this.getGameX() - 1 < this.getBoard().getGameWidth()
							&& this.getGameY() >= 0 && this.getGameY() < this.getBoard().getGameHeight()) {
						if (!(this.getBoard().isEmpty(this.getGameX() - 1, this.getGameY()))) {
							if (this.getBoard().getGameBoardObject()[this.getGameX() - 1][this.getGameY()]
									.isMushroom()) {
								if (((Mushroom) getBoard().getGameBoardObject()[this.getGameX() - 1][this.getGameY()])
										.getIsPoisoned()) {
									poison();
									this.direction = down;
								} else {
									this.direction = right;
									if (this.goingDown)
										this.setGameY(this.getGameY() + 1);
									else
										setGameY(getGameY() - 1);
								}
							} else {
								this.setGameX(this.getGameX() - 1);
							}
						} else {
							this.setGameX(this.getGameX() - 1);
						}
					} else {
						this.direction = right;
						if (this.goingDown) {
							this.setGameY(this.getGameY() + 1);
						} else {
							setGameY(getGameY() - 1);
						}
					}
				} else if (this.direction == right) {
					if (this.getGameX() + 1 >= 0 && this.getGameX() + 1 < this.getBoard().getGameWidth()
							&& this.getGameY() >= 0 && this.getGameY() < this.getBoard().getGameHeight()) {
						if (!(this.getBoard().isEmpty(this.getGameX() + 1, this.getGameY()))) {
							if (this.getBoard().getGameBoardObject()[this.getGameX() + 1][this.getGameY()]
									.isMushroom()) {
								if (((Mushroom) getBoard().getGameBoardObject()[this.getGameX() + 1][this.getGameY()])
										.getIsPoisoned()) {
									poison();
									this.direction = down;
								} else {
									this.direction = left;
									if (this.goingDown) {
										this.setGameY(this.getGameY() + 1);
									} else {
										this.setGameY(this.getGameY() - 1);
									}
								}
							} else {
								this.setGameX(this.getGameX() + 1);
							}
						} else {
							this.setGameX(this.getGameX() + 1);
						}
					} else {
						this.direction = left;
						if (this.goingDown) {
							this.setGameY(this.getGameY() + 1);
						} else {
							setGameY(getGameY() - 1);
						}
					}
				} else if (this.direction == down) {
					if (this.poisoned) {
						this.setGameY(this.getGameY() + 1);
						if (getGameY() == this.getBoard().getGameHeight() - 1) {
							this.poisoned = false;
						}
					} else {
						if (this.getGameY() % 2 != 0) {
							this.direction = right;
							setGameX(getGameX() + 1);
						} else {
							this.direction = left;
							setGameX(getGameX() - 1);
						}
					}
				}
			} else {
				this.timeDivision--;
			}
			if (getGameY() == getBoard().getGameHeight() - 1)
				this.goingDown = false;
			if (getGameY() == 2 * getBoard().getGameHeight() / 3)
				this.goingDown = true;
			setHasMoved(true);
		}
		getBoard().addGameBoardObject(this);
	}

	/**
	 * what happens when the centipede is hit
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		return 0;
	}

	/**
	 * Draws the centipede on the board
	 */
	@Override
	public void drawOn(Graphics2D g2) {
		int objectSize = Board.GAME_OBJECT_SIZE;
		Ellipse2D circle = new Ellipse2D.Double(getGameX() * objectSize, getGameY() * objectSize, objectSize,
				objectSize);
		g2.setColor(Color.CYAN);
		g2.fill(circle);
	}
}
