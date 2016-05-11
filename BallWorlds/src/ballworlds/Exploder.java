package ballworlds;

import java.awt.Color;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;

import util.Random;

public class Exploder extends AbstractBouncer{
	
	private BallEnvironment world;
	
	private static int exploders = 1;
	private int initialDiameter = 20;
	private int explodedDiameter = initialDiameter * Random.randRange(2, 10);
	private int diameter = initialDiameter;
	private double explodeRate = 1;
	
	private Color color;
	
	public Exploder (BallEnvironment world) {
		super(world);
		
		this.world = world;

		this.color = Color.CYAN;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void updatePosition() {
		
		checkBounce(this.world);
		
		double x = this.getCenterPoint().getX() + this.vx;
		double y = this.getCenterPoint().getY() + this.vy;
		
		setCenterPoint(new Point2D.Double(x,y));
	}

	@Override
	public void updateSize() {
		
		this.diameter += explodeRate;
		
		if(this.diameter >= explodedDiameter){
			this.explodeRate = 0;
			this.diameter = 0;
			Exploder a = new Exploder(this.world);
			a.setCenterPoint(new Point2D.Double(this.getCenterPoint().getX(),this.getCenterPoint().getY()));
			Exploder b = new Exploder(this.world);
			b.setCenterPoint(new Point2D.Double(this.getCenterPoint().getX(),this.getCenterPoint().getY()));
			this.world.addBall(a);
			this.world.addBall(b);
			this.exploders++;
			this.die();
			if (this.exploders> 50){
				System.out.println("sorry, there were too many balls, so we are shutting down");
				System.exit(1);
			}
		}
	}

	@Override
	public void updateColor() {
		return;
	}

	@Override
	public double getDiameter() {
		return diameter;
	}
}
