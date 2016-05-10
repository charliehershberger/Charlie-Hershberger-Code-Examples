package ballworlds;

import java.awt.Color;
import java.awt.geom.Point2D;

public class ExploderHunter extends AbstractBouncer{
	private BallWorld world;
	public ExploderHunter(BallWorld world){
		super(world);
		this.world = world;
	}
	public ExploderHunter(BallEnvironment world){
		super(world);
	}
	public void kill(){
		for (Ball ball : world.getBalls()){
			double xd = Math.pow(ball.getCenterPoint().getX() - this.getCenterPoint().getX(),2);
			double yd = Math.pow(ball.getCenterPoint().getY() - this.getCenterPoint().getY(),2);
			if (Math.sqrt(xd+yd)<(this.getDiameter()/2+ball.getDiameter()/2)){
				ball.die();
			}
		}
	}
	@Override
	public void updatePosition() {
		
		checkBounce(this.world);
			
		double x = this.getCenterPoint().getX() + this.vx;
		double y = this.getCenterPoint().getY() + this.vy;
			
		setCenterPoint(new Point2D.Double(x,y));
		kill();
	}
	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.YELLOW;
	}
	@Override
	public void updateSize() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateColor() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double getDiameter() {
		// TODO Auto-generated method stub
		return 10;
	}
}
