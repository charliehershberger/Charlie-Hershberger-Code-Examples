import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * Constructs and draws a leaderboard which reads and writes to a text file
 * indicating the 10 highest scores in the Centipede game.
 *
 * @author Charlie Hershberger, Riley Shore, Jacob Soehren Modified: 11/12/2015
 */
public class Leaderboard {
	@SuppressWarnings("unused")
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	@SuppressWarnings("unused")
	private ArrayList<String> names = new ArrayList<String>();
	private JFrame frame;
	private String fileName = "./src/leaderboard.txt";

	private int leaderFrameCounter;

	/**
	 * 
	 * Constructs the Leaderboard. Reads the data in a text gfile and loads the
	 * local arrayLists with the data there (Names and scores). The text file
	 * allows leader information to survive beyond the killing of the game.
	 *
	 * @param frame
	 */
	@SuppressWarnings("boxing")
	public Leaderboard(JFrame frame) {
		File leaderboard = new File("./src/leaderboard.txt");
		this.frame = frame;
		try {
			Scanner reader = new Scanner(leaderboard);
			try {
				for (int i = 0; i < 10; i++) {
					if (reader.hasNext()) {
						String tempS = reader.next();
						if (tempS == null) {
							throw new EOFException("number for name");
						}
						this.names.add(i, tempS);
					} else {
						break;
					}
					if (reader.hasNext()) {
						int tempI = reader.nextInt();
						if (tempI < 0) {
							throw new EOFException("impossible score");
						}
						this.scores.add(i, tempI);
					} else {
						throw new EOFException("name but no score");
					}
				}
			} catch (EOFException e) {
				System.out.println(e.getMessage());
			} finally {
				reader.close();
			}
		} catch (Exception e) {
			// Nothing Here
		} finally {
			// Nothing to complete
		}
	}

	/**
	 * 
	 * Called each time a person ends a Centipede game by losing their lives or
	 * playing all of the levels. Checks to see if their score fits on the
	 * board. If it does, prompts for name and adds them. If not, does nothing.
	 *
	 * @param score
	 */
	@SuppressWarnings("boxing")
	public void addScore(int score) {
		int insert = -1;
		for (int i = 0; i < this.scores.size(); i++) {
			if (this.scores.get(i) < score) {
				this.scores.add(i, score);
				this.names.add(i, "---");
				insert = i;
				break;
			}
		}
		if (this.scores.size() != 0) {
			if (this.scores.get(this.scores.size() - 1) > score && this.scores.size() < 10) {
				this.scores.add(score);
				this.names.add(this.scores.size() - 1, "---");
				insert = this.scores.size() - 1;
			}
		} else {
			this.scores.add(score);
			this.names.add("---");
			insert = 0;
		}
		if (this.scores.size() == 11) {
			this.scores.remove(10);
			this.names.remove(10);
		}
		boolean validName = false;
		String name = "";
		if (insert >= 0) {
			while (!validName) {
				name = JOptionPane.showInputDialog("what is your name? (limit to three characters)");
				if (name != null) {
					for (int i = 0; i < name.length(); i++) {
						if (name.charAt(i) == ' ') {
							break;
						}
						if (i == name.length() - 1) {
							validName = true;
						}
					}
					if (name.length() > 3) {
						validName = false;
					}
				}
			}
			this.names.set(insert, name);
			try {
				PrintWriter writer = new PrintWriter(this.fileName, "UTF-8");
				for (int i = 0; i < this.scores.size(); i++) {
					writer.print(this.names.get(i) + " ");
					writer.println(this.scores.get(i));
				}
				writer.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {

				e.printStackTrace();
			} finally {
				// Nothing to finish
			}
		}
	}

	/**
	 * 
	 * Used to draw the board itself on the frame. Called repeatedly by Board's
	 * repaint. Returns false after it has been displayed for a certain number
	 * of frames in order to live for finite time.
	 *
	 * @param g2
	 *            Graphics object to draw itself on
	 * @return Whether it has life left
	 */
	public boolean drawScreen(Graphics2D g2) {
		this.frame.setSize(500, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.frame.setLocation(dim.width / 2 - this.frame.getSize().width / 2,
				dim.height / 2 - this.frame.getSize().height / 2);
		g2.setFont(new Font("Arial", Font.PLAIN, 48));
		g2.setColor(Color.BLACK);
		Rectangle backGround = new Rectangle(0, 0, 500, 700);
		g2.fill(backGround);
		g2.setColor(Color.blue);
		g2.drawString("Leader Board", 0, 50);
		for (int i = 0; i < this.names.size(); i++) {
			g2.drawString("#" + (1 + i), 0, 50 * (2 + i));
			g2.drawString(this.names.get(i), 100, 50 * (2 + i));
			g2.drawString(this.scores.get(i).toString(), 200, 50 * (2 + i));
		}
		this.leaderFrameCounter--;
		return (this.leaderFrameCounter >= 0);
	}

	/**
	 * 
	 * Sets the number of frames that the next Leaderbord should live for
	 *
	 * @param frames
	 *            Number of frames the next Leaderboard should live for
	 */
	public void setFrames(int frames) {
		this.leaderFrameCounter = frames;
	}
}
