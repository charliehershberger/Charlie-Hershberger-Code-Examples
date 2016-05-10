package ballworlds;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Bouncer extends AbstractBouncer{
	
	private BallEnvironment world;
	

	private int diameter = 20;
	private Color color;
	
	public Bouncer (BallEnvironment world) {
		super(world);
		
		this.world = world;

		this.color = Color.YELLOW;
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
		return;

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
