/**
 * 
 * Creates a PersistantString, which only "persists" for a fixed time. Gets
 * drawn to a specific location and is destroyed by its creator a certain number
 * of frames after it is drawn
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modify: 11/12/2015
 *         Created Nov 11, 2015.
 */
public class PersistantString {

	private String data;
	private int framesLeft;
	private int xPos;
	private int yPos;

	/**
	 * 
	 * Creates a PersistantString
	 *
	 * @param inData
	 *            String to display
	 * @param inFrames
	 *            How many frames it should be displayed for
	 * @param x
	 *            X position of String
	 * @param y
	 *            Y position of String
	 */
	public PersistantString(String inData, int inFrames, int x, int y) {
		this.data = inData;
		this.framesLeft = inFrames;
		this.xPos = x;
		this.yPos = y;
	}

	/**
	 * 
	 * Called every time master frames cycle, usually in paintComponent. Returns
	 * whether the PersistantString has been alive for the specified life or
	 * decrements life.
	 *
	 * @return Whether string should continue being displayed
	 */
	public boolean stepLife() {
		if (this.framesLeft == 0) {
			return false;
		}
		this.framesLeft--;
		return true;
	}

	/**
	 * 
	 * Returns X position of the PerisitantString
	 *
	 * @return
	 */
	public int getXpos() {
		return this.xPos;
	}

	/**
	 * 
	 * Returns Y position of the PersistantString
	 *
	 * @return
	 */
	public int getYpos() {
		return this.yPos;
	}

	/**
	 * 
	 * Returns the String to display
	 *
	 * @return String to display
	 */
	public String getData() {
		return this.data;
	}

}
