/**
 * 
 * Weapon that fires three bullets forward.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified: 11/12/2015
 */
public class BeamBlaster implements Weapon {
	/**
	 * Method to add 3 bullets in a row to the game board.
	 */
	@Override
	public void fire(Board board, int x, int y) {
		board.addGameBoardObject(new Bullet(x, y, 0, -1, board));
		board.addGameBoardObject(new Bullet(x, y - 1, 0, -1, board));
		board.addGameBoardObject(new Bullet(x, y - 2, 0, -1, board));
	}

}
