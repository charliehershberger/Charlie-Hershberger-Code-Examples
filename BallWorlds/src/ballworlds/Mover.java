package ballworlds;

import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Mover extends Ball{
	private Color color = new Color(0,250,0);
	private int diameter = 30;
	private int vx;
	private int vy;
	
	public Mover(BallEnvironment world){
		super(world);
		double x = Random.randRange(0, world.getSize().width);
		double y = Random.randRange(0, world.getSize().height);
		setCenterPoint(new Point2D.Double(x,y));
		while(this.vx == 0 && this.vy == 0){
			this.vy = (int)((Math.random()*6)-3);
			this.vx = (int)((Math.random()*6)-3);
		}
	}
	
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void updatePosition() {
		super.setCenterPoint(new Point2D.Double(super.getCenterPoint().getX()+vx, super.getCenterPoint().getY()+vy));
		return;
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
