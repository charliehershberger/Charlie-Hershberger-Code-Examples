import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 * 
 * Controls the player, a small blue box that can move and shoot and die.
 * 
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 */
@SuppressWarnings("serial")
public class Player extends GameBoardObject {
	private JFrame theFrame;
	private Blaster blaster = new Blaster();
	private BeamBlaster beam = new BeamBlaster();
	private SideBlaster side = new SideBlaster();
	private ArrayList<Weapon> weapons;
	private int updatesSinceFired;

	private ActionMap aMap = this.getActionMap();
	private InputMap iMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

	private Action myUpKeyAction;
	private Action myDownKeyAction;
	private Action myLeftKeyAction;
	private Action myRightKeyAction;
	private Action mySpaceBarAction;
	private Action myOneAction;
	private Action myTwoAction;
	private Action myThreeAction;

	private int selectedWeapon;
	private static boolean right;
	private static boolean left;
	private static boolean up;
	private static boolean down;

	private Color myColor;

	/**
	 * 
	 * Constructs a standard Player at the specified location on the specified
	 * board and binds the keyboard keys that control it
	 *
	 * @param inX
	 *            The game x coordinate at which to construct the player
	 * @param inY
	 *            The game Y coordinate at whcih to construct the player
	 * @param board
	 *            The board on which the player is constructed
	 */
	@SuppressWarnings("unused")
	public Player(JFrame frame, Board board, int inX, int inY) {
		super(inX, inY, board);
		this.theFrame = frame;
		this.weapons = new ArrayList<Weapon>();
		this.updatesSinceFired = 0;
		this.weapons.add(this.blaster);
		this.weapons.add(this.beam);
		this.weapons.add(this.side);
		this.selectedWeapon = 0;
		this.myColor = Color.BLUE;
		this.theFrame.add(this);

		this.myUpKeyAction = new upKeyAction(this);
		this.myDownKeyAction = new downKeyAction(this);
		this.myLeftKeyAction = new leftKeyAction(this);
		this.myRightKeyAction = new rightKeyAction(this);
		this.mySpaceBarAction = new spaceBarAction(this);
		this.myOneAction = new oneAction(this);
		this.myTwoAction = new twoAction(this);
		this.myThreeAction = new threeAction(this);

		this.iMap.put(KeyStroke.getKeyStroke("UP"), "uppress");
		this.iMap.put(KeyStroke.getKeyStroke("DOWN"), "downpress");
		this.iMap.put(KeyStroke.getKeyStroke("LEFT"), "leftpress");
		this.iMap.put(KeyStroke.getKeyStroke("RIGHT"), "rightpress");
		this.iMap.put(KeyStroke.getKeyStroke("SPACE"), "spacepress");
		this.iMap.put(KeyStroke.getKeyStroke("1"), "onepress");
		this.iMap.put(KeyStroke.getKeyStroke("2"), "twopress");
		this.iMap.put(KeyStroke.getKeyStroke("3"), "threepress");

		this.aMap.put("uppress", this.myUpKeyAction);
		this.aMap.put("downpress", this.myDownKeyAction);
		this.aMap.put("leftpress", this.myLeftKeyAction);
		this.aMap.put("rightpress", this.myRightKeyAction);
		this.aMap.put("spacepress", this.mySpaceBarAction);
		this.aMap.put("onepress", this.myOneAction);
		this.aMap.put("twopress", this.myTwoAction);
		this.aMap.put("threepress", this.myThreeAction);
	}

	/**
	 * 
	 * Sets whether player will move right when updatePosition is called
	 *
	 * @param set
	 */
	public void setRight(boolean set) {
		right = set;
	}

	/**
	 * 
	 * Sets whether player will move left when updatePosition is called
	 *
	 * @param set
	 *            Whether to move left
	 */
	public void setLeft(boolean set) {
		left = set;
	}

	/**
	 * 
	 * Sets whether player will move up when updatePosition is called
	 *
	 * @param set
	 *            Whether to move up
	 */
	public void setUp(boolean set) {
		up = set;
	}

	/**
	 * 
	 * Sets whether player will move down when updatePosition is called
	 *
	 * @param set
	 *            Whether to move down
	 */
	public void setDown(boolean set) {
		down = set;
	}

	/**
	 * 
	 * Returns the weapon that is selected for the player
	 *
	 * @return The weapon that the player is currently using
	 */
	public Weapon getWeapon() {
		return this.weapons.get(this.selectedWeapon);
	}

	/**
	 * Updates the image of the player if enabled
	 */
	@Override
	public void updateImage() {
		return;

	}

	/**
	 * Updates the position of the player when player's run() is called
	 */
	@Override
	public void updatePosition() {
		this.getBoard().remove(getGameX(), getGameY());

		if (this.getGameY() != this.getBoard().getGameHeight() - 1) {
			if (down) {
				if (this.getBoard().isEmpty(this.getGameX(), this.getGameY() + 1)) {
					this.setGameY(this.getGameY() + 1);
				} else if (this.getBoard().getGameBoardObject()[this.getGameX()][this.getGameY() + 1].isMushroom()) {
					// Don't move.
				} else {
					this.setGameY(this.getGameY() + 1);
				}
			}
		}
		if (this.getGameY() > 2 * getBoard().getGameHeight() / 3) {
			if (up) {
				if (this.getBoard().isEmpty(this.getGameX(), this.getGameY() - 1)) {
					// if (this.getGameY() -
					// 1>this.getBoard().getGameHeight()*0.7){
					this.setGameY(this.getGameY() - 1);
					// }
				} else if (this.getBoard().getGameBoardObject()[this.getGameX()][this.getGameY() - 1].isMushroom()) {
					// Don't move.
				} else {
					this.setGameY(this.getGameY() - 1);
				}
			}
		}
		if (this.getGameX() != this.getBoard().getGameWidth() - 1) {
			if (right) {
				if (this.getBoard().isEmpty(this.getGameX() + 1, this.getGameY())) {
					this.setGameX(this.getGameX() + 1);
				} else if (this.getBoard().getGameBoardObject()[this.getGameX() + 1][this.getGameY()].isMushroom()) {
					// Don't move.
				} else {
					this.setGameX(this.getGameX() + 1);
				}
			}
		}
		if (this.getGameX() >= 1) {
			if (left) {
				if (this.getBoard().isEmpty(this.getGameX() - 1, this.getGameY())) {
					this.setGameX(this.getGameX() - 1);
				} else if (this.getBoard().getGameBoardObject()[this.getGameX() - 1][this.getGameY()].isMushroom()) {
					// Don't move.
				} else {
					this.setGameX(this.getGameX() - 1);
				}
			}
		}

		this.getBoard().addGameBoardObject(this);
	}

	/**
	 * Handles collisions with other things on the board
	 */
	@Override
	public int getHit(GameBoardObject hitter) {
		reduceHealth();
		hitter.reduceHealth();
		return 0;
	}

	/**
	 * Overriden run() to handle setting position booleans to false after
	 * movement
	 */
	@Override
	public void run() {
		super.run();
		up = false;
		down = false;
		left = false;
		right = false;
		this.updatesSinceFired++;
	}

	/**
	 * Overrides GameBoardObject's die() method. This allows player to reset
	 * itself to the lower left square (Or nearest unoccupied square) of the
	 * board that it is on.
	 */
	@Override
	public void die() {
		super.die();

		getBoard().reloadBoard();
	}

	/**
	 * 
	 * Makes the player's selected weapon fire itself. Also slows down firing of
	 * the weapon to a selected amount.
	 *
	 */
	public void fireWeapon() {
		boolean reloaded = false;
		switch (this.selectedWeapon) {
		case 0: // Blaster
			if (this.updatesSinceFired > 30)
				reloaded = true;
			break;
		case 1: // Beam
			if (this.updatesSinceFired > 50)
				reloaded = true;
			break;
		case 2: // Twin
			if (this.updatesSinceFired > 35)
				reloaded = true;
			break;
		default:
			reloaded = true;
			break;
		}
		if (reloaded) {
			getWeapon().fire(this.getBoard(), this.getGameX(), this.getGameY());
			this.updatesSinceFired = 0;
		}
	}

	/**
	 * 
	 * Changes the weapon that the player is currently using
	 *
	 * @param weaponSelection
	 *            The number of the weapon to select
	 */
	public void changeWeapon(int weaponSelection) {
		this.selectedWeapon = weaponSelection - 1;
	}

	/**
	 * Draws the player on the board
	 */
	@Override
	public void drawOn(Graphics2D g2) {

		int objectSize = Board.GAME_OBJECT_SIZE;

		Rectangle me = new Rectangle(this.getGameX() * objectSize, this.getGameY() * objectSize, objectSize,
				objectSize);
		g2.setColor(this.myColor);
		g2.fill(me);

	}

	/**
	 * 
	 * The following classes bind keys and actions to the input and action maps
	 * of the player JComponent to register keyboard presses
	 */
	class upKeyAction extends AbstractAction {

		Player myPlayer;

		upKeyAction(Player player) {
			this.myPlayer = player;
		}

		@SuppressWarnings({ "static-access", "synthetic-access" })
		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.setUp(true);
			this.myPlayer.up = true;
		}
	}

	class downKeyAction extends AbstractAction {

		Player myPlayer;

		downKeyAction(Player player) {
			this.myPlayer = player;
		}

		@SuppressWarnings({ "static-access", "synthetic-access" })
		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.down = true;
			this.myPlayer.setDown(true);
		}
	}

	class leftKeyAction extends AbstractAction {

		Player myPlayer;

		leftKeyAction(Player player) {
			this.myPlayer = player;
		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.setLeft(true);
		}
	}

	class rightKeyAction extends AbstractAction {

		Player myPlayer;

		rightKeyAction(Player player) {
			this.myPlayer = player;
		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.setRight(true);
		}
	}

	class spaceBarAction extends AbstractAction {

		Player myPlayer;

		public spaceBarAction(Player player) {
			this.myPlayer = player;

		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.fireWeapon();
		}
	}

	class oneAction extends AbstractAction {

		Player myPlayer;

		public oneAction(Player player) {
			this.myPlayer = player;

		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.changeWeapon(1);
		}
	}

	class twoAction extends AbstractAction {

		Player myPlayer;

		public twoAction(Player player) {
			this.myPlayer = player;

		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.changeWeapon(2);
		}
	}

	class threeAction extends AbstractAction {

		Player myPlayer;

		public threeAction(Player player) {
			this.myPlayer = player;

		}

		@Override
		public void actionPerformed(ActionEvent tf) {
			this.myPlayer.changeWeapon(3);
		}
	}
}
