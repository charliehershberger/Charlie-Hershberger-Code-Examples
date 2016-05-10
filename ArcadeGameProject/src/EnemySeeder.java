import java.util.Random;

/**
 * 
 * Seeds enemies to the board based on the current number of things on the
 * board. Method called every time the game steps in order to add enemies when
 * they're needed. Checks object counters on the board to determine when to put
 * the enemies onto the board.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/15
 */
public class EnemySeeder {

	private Board theBoard;
	private int spiderGracePeriod;
	private int scorpionGracePeriod;
	private Random rand;

	private final int maxSpiderGracePeriod = 500;
	private final int maxScorpionGracePeriod = 200;

	/**
	 * 
	 * Constructs a new EnemySeeder with a reference to the board that it's on.
	 * Seeds a random generator with unix time.
	 *
	 * @param board
	 */
	public EnemySeeder(Board board) {
		this.theBoard = board;
		this.rand = new Random(System.currentTimeMillis());
		this.spiderGracePeriod = this.maxSpiderGracePeriod;
	}

	/**
	 * 
	 * Adds enemies if necessary to the board that the EnemySeeder is on. Called
	 * each frame. Adds fleas if there are less than five Mushrooms on the
	 * board. Adds spiders after a wait period after they're all destroyed. Adds
	 * a scorpion after a wait period adfter they're all gone.
	 *
	 */
	public void addEnemies() {

		if (Mushroom.mushroomCount <= 5) {
			addFleas();
		}

		if (Spider.spiderCounter == 0) {
			if (this.spiderGracePeriod == 0) {
				addSpider();
				this.spiderGracePeriod = this.maxSpiderGracePeriod;
			} else {
				this.spiderGracePeriod--;
			}
		}

		if (this.scorpionGracePeriod == 0) {
			addScorpion();
			this.scorpionGracePeriod = this.maxScorpionGracePeriod;
		} else {
			this.scorpionGracePeriod--;
		}

	}

	/**
	 * 
	 * Adds fleas to the top of the board in random locations that are
	 * unoccupied.
	 *
	 */
	private void addFleas() {
		this.rand.setSeed(System.currentTimeMillis());
		int fleaX = (int) ((this.theBoard.getGameWidth() - 1) * this.rand.nextDouble());
		int fleaY = 0;
		boolean done = false;

		while ((!done) && (fleaX < this.theBoard.getGameWidth())) {
			if (this.theBoard.isEmpty(fleaX, fleaY)) {
				this.theBoard.addGameBoardObject(new Flea(fleaX, fleaY, this.theBoard));
				done = true;
			} else {
				fleaX++;

			}
		}

	}

	/**
	 * 
	 * Adds a Spider at the upper left corner of player region or the nearest
	 * unoccupied space below it.
	 *
	 */
	private void addSpider() {
		int spiderX = this.theBoard.getGameWidth() - 1;
		int spiderY = this.rand.nextInt(this.theBoard.getGameHeight() / 3) + (2 * this.theBoard.getGameHeight() / 3);
		boolean done = false;

		while ((!done) && (spiderY < this.theBoard.getGameHeight())) {
			if (this.theBoard.isEmpty(spiderX, spiderY)) {
				this.theBoard.addGameBoardObject(new Spider(spiderX, spiderY, this.theBoard));
				done = true;
			} else {
				spiderY++;

			}
		}
	}

	/**
	 * 
	 * Adds a scorpion to either side of the board randomly in an unoccupied
	 * space.
	 *
	 */
	private void addScorpion() {
		int scorpionX = 0;
		int scorpionY = 0;
		boolean done = false;

		scorpionY = (int) ((2 * this.theBoard.getGameHeight() / 3) * this.rand.nextDouble());
		if (scorpionY % 2 == 0)
			scorpionX = this.theBoard.getGameWidth() - 1;
		else
			scorpionX = 0;
		while ((!done) && (scorpionY < this.theBoard.getGameHeight())) {
			if (this.theBoard.isEmpty(scorpionX, scorpionY)) {
				this.theBoard.addGameBoardObject(new Scorpion(scorpionX, scorpionY, this.theBoard));
				done = true;
			} else {
				scorpionY++;
			}
		}

	}

}
