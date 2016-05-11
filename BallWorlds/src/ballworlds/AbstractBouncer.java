package ballworlds;

import java.awt.geom.Point2D;

import util.Random;

/**
 * A ball that bounces off the walls.
 * 
 * @author Curt Clifton. Created Jan 22, 2011.
 */
public abstract class AbstractBouncer extends Ball {
	// nothing here... yet

	protected double vx;
	protected double vy;
	

	private int velocity = 4;
	/**
	 * Constructs a abstract bouncer in the given world.
	 * 
	 * @param world
	 */
	public AbstractBouncer(BallEnvironment world) {
		super(world);
		
		double x = world.getCenterPoint().getX();
		double y = world.getCenterPoint().getY();
		
		while(this.vx == 0 && this.vy == 0){
			this.vx = Random.randRange(-this.velocity, this.velocity);
			this.vy = Random.randRange(-this.velocity, this.velocity);
		}
		
		setCenterPoint(new Point2D.Double(x,y));

	}
	
	public void checkBounce(BallEnvironment world) {
		if((this.getCenterPoint().getX() <= world.getSize().getWidth()+this.velocity && 
				this.getCenterPoint().getX() >= world.getSize().getWidth()-this.velocity) ||
					(this.getCenterPoint().getX() <= 0+this.velocity && this.getCenterPoint().getX() >= 0-this.velocity)){
			this.vx = -this.vx;
		}
		if((this.getCenterPoint().getY() <= world.getSize().getHeight()+this.velocity && 
				this.getCenterPoint().getY() >= world.getSize().getHeight()-this.velocity) ||
					(this.getCenterPoint().getY() <= 0+this.velocity && this.getCenterPoint().getY() >= 0-this.velocity)){
			this.vy = -this.vy;
		}
	}
}
