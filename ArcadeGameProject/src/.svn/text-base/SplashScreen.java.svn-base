import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

/**
 * 
 * Draws the splash screen and "interruptor" screen for the Centipede game.
 * Resizes the frame and uses board parameters to draw taunts on the screen for
 * a specified number of frames.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified 11/12/2015
 * 
 */
public class SplashScreen {

	private JFrame theFrame;
	private Board theBoard;

	private int splashFrameCounter;

	private String[] taunt = { "", "LOL", "Give up, Mate", "You Died ;)", "It Rises" };

	/**
	 * 
	 * Constructs a splashScreen object and
	 *
	 * @param frame
	 * @param board
	 */
	public SplashScreen(JFrame frame, Board board) {
		this.theFrame = frame;
		this.theBoard = board;
	}

	/**
	 * Draws the splash screen and updates the frame counter. Called every frame
	 * (Usually in paintComponent)
	 */
	public boolean drawScreen(Graphics2D g2) {
		this.theFrame.setSize(500, 500);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.theFrame.setLocation(dim.width / 2 - this.theFrame.getSize().width / 2,
				dim.height / 2 - this.theFrame.getSize().height / 2);

		g2.setColor(Color.BLUE);
		Rectangle screen = new Rectangle(0, 0, 500, 500);
		g2.fill(screen);

		Ellipse2D.Double circle = new Ellipse2D.Double(35, 30, 400, 400);
		g2.setColor(Color.YELLOW);
		g2.fill(circle);

		String dispString = "CENTIPEDE!";
		Font splashFont = new Font("Helvetica", Font.BOLD, 48);
		g2.setFont(splashFont);
		g2.setColor(Color.RED);
		g2.drawString(dispString, 90, 250);
		if (this.theBoard.getLives() == 1) {
			g2.translate(130, 100);
			g2.rotate(this.splashFrameCounter);
			g2.drawString("6", -20, 20);
			g2.rotate(-this.splashFrameCounter);
			g2.translate(100, 0);
			g2.rotate(this.splashFrameCounter + 10);
			g2.drawString("6", -20, 20);
			g2.rotate(-this.splashFrameCounter - 10);
			g2.translate(100, 0);
			g2.rotate(this.splashFrameCounter + 20);
			g2.drawString("6", -20, 20);
			g2.rotate(-this.splashFrameCounter - 20);
			g2.translate(-355, -100);
		}
		if (!this.theBoard.getIsPaused()) {
			g2.drawString(this.taunt[5 - this.theBoard.getLives()],
					(int) (90 + Math.random() * Math.pow(6 - this.theBoard.getLives(), 3)),
					(int) (100 + Math.random() * Math.pow(6 - this.theBoard.getLives(), 3)));
		}

		this.splashFrameCounter--;
		return (this.splashFrameCounter >= 0);

	}

	/**
	 * 
	 * Sets the number of frames that the next splash screen should live for
	 *
	 * @param frames
	 *            Number of frames the next splash screen should live for
	 */
	public void setFrames(int frames) {
		this.splashFrameCounter = frames;
	}
}
