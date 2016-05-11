/**
 * 
 * Defines the SideBlaster weapon which fires 2 bullets at 45 degrees from
 * center line of player in opposite direction
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified: 11/12/2015
 */
public class SideBlaster implements Weapon {
	/**
	 * creates and adds a bullet that goes forward and right creates and adds a
	 * bullet that goes forward and left
	 */
	@Override
	public void fire(Board board, int x, int y) {
		if (x < board.getGameWidth() - 1) {
			board.addGameBoardObject(new Bullet(x, y, 1, -1, board));
		}
		if (x > 0) {
			board.addGameBoardObject(new Bullet(x, y, -1, -1, board));
		}
	}

}
