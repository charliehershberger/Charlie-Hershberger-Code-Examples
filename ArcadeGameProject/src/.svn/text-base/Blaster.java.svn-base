/**
 * 
 * Most basic weapon, fires bullets in a straight line up from the player.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified: 11/12/2015
 */
public class Blaster implements Weapon {
	/**
	 * Method to fire a single bullet on the game board.
	 */
	@Override
	public void fire(Board board, int x, int y) {
		board.addGameBoardObject(new Bullet(x, y, 0, -1, board));
	}

}
