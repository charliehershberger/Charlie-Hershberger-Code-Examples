package ballworlds;

import java.awt.Color;
import java.awt.geom.Point2D;

import util.Random;

public class Pulsar extends Ball {

	private float intensity;
	private boolean intensityIncreasing;
	private double intensityStep;
	private int diameter = 30;
	
	public Pulsar(BallEnvironment world){
		super(world);
		double x = Random.randRange(0, world.getSize().width);
		double y = Random.randRange(0, world.getSize().height);
		setCenterPoint(new Point2D.Double(x,y));
		this.intensity = 0.0f;
		this.intensityStep = 0.01f;
		this.intensityIncreasing = true;
	}
	
	@Override
	public Color getColor() {
		return new Color(this.intensity,this.intensity,this.intensity);
	}

	@Override
	public void updatePosition() {
		return;
	}

	@Override
	public void updateSize() {
		return;
	}

	@Override
	public void updateColor() {
		if (this.intensityIncreasing){
			this.intensity+=this.intensityStep;
			if (this.intensity > 1.0f){
				this.intensityIncreasing = false;
				this.intensity = 1.0f;
			}
		}else{
			this.intensity-=this.intensityStep;
			if (this.intensity < 0.0f){
				this.intensityIncreasing = true;
				this.intensity=0.0f;
			}
		}
	}

	@Override
	public double getDiameter() {
		return diameter;
	}

}
