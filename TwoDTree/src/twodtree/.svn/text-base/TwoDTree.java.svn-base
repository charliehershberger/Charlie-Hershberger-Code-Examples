package twodtree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * A 2D Tree implementation.
 * 
 * @author Matt Boutell and TODO: You!
 */
public class TwoDTree {
	/*
	 * TODO: Directions: Implement the methods below. See the specifications for
	 * details.
	 */
	private Node root;
	private Point2 nearestFound = null;
	public final Node NULL_NODE = new Node();

	/** For drawing the plane. */
	public static final double DOT_RADIUS = 5;
	private int planePanelWidth;
	private int planePanelHeight;

	// For drawing the tree
	private static final int MARGIN = 30;
	private static final double RADIUS_SCALE_FACTOR = 0.75;
	private static final double FONT_SCALE_FACTOR = 1.5;
	private static final double DIRECTION_TYPE_SCALE_FACTOR = 1.1;
	private int treePanelWidth;
	private int treePanelHeight;
	// The number of pixels horizontally and vertically between nodes.
	private int xStep, yStep;
	private double radius;
	// font to use for labeling nodes
	private Font font;
	private int fontSize;

	/**
	 * Constructs an empty tree.
	 * 
	 */
	public TwoDTree() {
		this(0, 0, 0, 0); // When called within params, it won't be visualized
	}

	/**
	 * Constructs an empty tree.
	 * 
	 */
	public TwoDTree(int planePanelWidth, int planePanelHeight, int treePanelWidth, int treePanelHeight) {
		root = NULL_NODE;
		this.planePanelWidth = planePanelWidth;
		this.planePanelHeight = planePanelHeight;
		this.treePanelWidth = treePanelWidth;
		this.treePanelHeight = treePanelHeight;
	}

	/**
	 * Inserts the given point into the tree
	 * 
	 * @param p
	 *            A point to insert.
	 */
	public void insert(Point2 p, String label) {
		// TODO: write this.
		if (this.root==NULL_NODE){
			this.root = new Node(p.x, p.y, label, Direction.X, null, 1);
			//Node(double x, double y, String label, Direction dir, RectHV bounds, int depth) 
			return;
		}
		this.root.insert(p, label, Direction.X, 1);
	}

	/**
	 * Checks to see if the given query point is in this tree.
	 * 
	 * @param q
	 *            Query point.
	 * @return True if and only if the query point is in this tree.
	 * 
	 */
	public boolean contains(Point2 q) {
		// TODO: write this.
		if (this.root==NULL_NODE){
			return false;
		}
		return this.root.contains(q, Direction.X);
	}

	/**
	 * Finds the closest point in the tree to the query point.
	 * 
	 * @param q
	 *            The query point
	 * @throws IllegalStateException.
	 *             If the tree is empty.
	 * @return The closest point
	 */
	public Point2 nearestNeighbor(Point2 q) throws IllegalStateException {
		// TODO: write this.
		if (this.root == NULL_NODE){
			return null;
		}
		return this.root.nearestNeighbor(q, Direction.X);
	}

	public void drawTree(Graphics2D g) {
		int nodeCountPlusOne = root.setInOrderNumbers(1);
		this.xStep = (this.treePanelWidth - 2 * MARGIN) / nodeCountPlusOne;
		this.yStep = (this.treePanelHeight - 2 * MARGIN) / (height() + 2);
		this.radius = ((xStep < yStep) ? xStep : yStep) * RADIUS_SCALE_FACTOR;
		this.fontSize = (int) (radius * FONT_SCALE_FACTOR * 96 / 72);
		this.font = new Font("Monospaced", Font.BOLD, fontSize);
		root.drawTree(g, -1, -1);
	}

	public void clear() {
		root = NULL_NODE;
		nearestFound = null;
	}

	@Override
	public String toString() {
		if (root == NULL_NODE) {
			return "()";
		}
		StringBuilder sb = new StringBuilder();
		root.buildString(sb);
		return sb.toString();
	}

	public void draw(Graphics2D g2, double minX, double maxX, double minY, double maxY) {
		root.drawInPlane(g2, minX, maxY, minY, maxY);

		if (nearestFound != null) {
			Ellipse2D.Double nodeDot = new Ellipse2D.Double(screenX(nearestFound.x) - DOT_RADIUS,
					screenY(nearestFound.y) - DOT_RADIUS, DOT_RADIUS * 2, DOT_RADIUS * 2);
			g2.setColor(Color.RED);
			g2.fill(nodeDot);
		}
	}

	private int screenX(double x) {
		return (int) (x * planePanelWidth);
	}

	private int screenY(double y) {
		return (int) (y * planePanelHeight);
	}

	private int height() {
		return root.height();
	}

	/**
	 * The direction of each node is given in this enumeration. The root always
	 * splits the plane depending on its point's x-coordinate, so has direction
	 * of Direction.X. This is shown using a vertical line (see node A in the
	 * screenshot in the specification) since splitting based on X splits the
	 * plane using a vertical line. The root's children split the plane
	 * depending on the y-coordinate, so will be of type Direction.Y (the
	 * horizontal lines on E and B in the screenshot).
	 */
	enum Direction {
		X, Y
	}

	public class Node {
		// The two data fields: a label and a point
		public String label;
		public Point2 p;

		// Children
		Node topLeft;
		Node bottomRight;
		
		// The enumeration above.
		public Direction dir;

		// Each node knows the bounds of the rectangle it is part of. Helpful
		// when searching. See the spec for details.
		public RectHV bounds;

		// For tree drawing
		// depth is used by drawTree below to place the nodes correctly when
		// drawing the tree.
		// You need to set it when you insert a node.
		private int depth;
		// inOrderNumber is both calculated and used by code below. You can
		// ignore it.
		private int inOrderNumber;

		// This one is used by the NULL_NODE.
		public Node() {
			// do nothing
		}

		public Node(Point2 p) {
			if (p == null) {
				return;
			}
			this.p = new Point2(p);
			this.topLeft = NULL_NODE;
			this.bottomRight = NULL_NODE;
			this.bounds = null;
		}

		// You will probably use this when writing insert()
		public Node(double x, double y, String label, Direction dir, RectHV bounds, int depth) {
			this.p = new Point2(x, y);
			this.label = label;
			this.dir = dir;
			this.topLeft = NULL_NODE;
			this.bottomRight = NULL_NODE;
			this.bounds = bounds;
			this.depth = depth;
		}
		/**
		 * Inserts the given point into the tree
		 * 
		 * @param p
		 *            A point to insert.
		 */
		public void insert(Point2 p, String label, Direction orient, int depth) {
			// TODO: write this.

			//System.out.println("inserting "+ label + "["+p.x+" "+p.y+"]");
			/*if (this == NULL_NODE){
				this.p = p;
				this.label = label;
				if (orient==Direction.X){
					this.dir = Direction.X; 
				}else{
					this.dir = Direction.Y; 
				}
				this.depth = depth;
				this.bottomRight = NULL_NODE;
				this.topLeft = NULL_NODE;
				this.bounds = null;
				return;
			}*/
			// Node(p.x, p.y, label, orient, null,depth);
			
			// oriented X
			if (orient == Direction.X){
				if(this.p.x>p.x){
					//insert if empty
					if (this.topLeft == NULL_NODE){
						this.topLeft=new Node(p.x, p.y, label, Direction.Y, null,depth);
					}else{
						//recurse left
						this.topLeft.insert(p,label, Direction.Y, depth+1);
					}
				}else{
					//insert if empty
					if (this.bottomRight == NULL_NODE){
						this.bottomRight=new Node(p.x, p.y, label, Direction.Y, null,depth);
						//recurse right
					}else{
						this.bottomRight.insert(p,label, Direction.Y, depth+1);
					}
				}

			// oriented Y
			}else{
				if(this.p.y>p.y){
					//insert if empty
					if (this.topLeft == NULL_NODE){
						this.topLeft=new Node(p.x, p.y, label, Direction.X, null,depth);
					}else{
						//recurse up
						this.topLeft.insert(p,label, Direction.X, depth+1);
					}
				}else{
					//insert if empty
					if (this.bottomRight == NULL_NODE){
						this.bottomRight=new Node(p.x, p.y, label, Direction.X, null,depth);
					}else{
						//recurse down
						this.bottomRight.insert(p,label, Direction.X, depth+1);
					}
				}
			}
		}

		/**
		 * Checks to see if the given query point is in this tree.
		 * 
		 * @param q
		 *            Query point.
		 * @return True if and only if the query point is in this tree.
		 * 
		 */
		public boolean contains(Point2 q, Direction orient) {
			// TODO: write this.
			// return true if you reach the node
			if (this.p.equals(q)){
				return true;
			}
			// if X oriented
			if (orient == Direction.X){
				if(this.p.x>q.x){
					//look left
					//if you reach the end
					if (this.topLeft == NULL_NODE){
						return false;
					}else{
						//recurse left
						return this.topLeft.contains(q,Direction.Y);
					}
				}else{
					//look right
					//if you reach the end
					if (this.bottomRight == NULL_NODE){
						return false;
					}else{
						//recurse right
						return this.bottomRight.contains(q,Direction.Y);
					}
				}
			// if Y oriented
			}else{
				if(this.p.y>q.y){
					//look up
					//if you reach the end
					if (this.topLeft == NULL_NODE){
						return false;
					}else{
						//recurse up
						return this.topLeft.contains(q,Direction.X);
					}
				}else{
					//look down
					//if you reach the end
					if (this.bottomRight == NULL_NODE){
						return false;
					}else{
						//recurse down
						return this.bottomRight.contains(q, Direction.X);
					}
				}
			}
		}

		/**
		 * Finds the closest point in the tree to the query point.
		 * 
		 * @param q
		 *            The query point
		 * @throws IllegalStateException.
		 *             If the tree is empty.
		 * @return The closest point
		 */
		public Point2 nearestNeighbor(Point2 q, Direction orient) throws IllegalStateException {
			// TODO: write this.
			//the proper path point
			Point2 close = this.p;
			//the other side
			Point2 other = this.p;
			//if you are on an x
			if (orient == Direction.X){
				// go left
				if(this.p.x>q.x){
					// continue left if you can
					if (this.topLeft != NULL_NODE){
						close = this.topLeft.nearestNeighbor(q,Direction.Y);
					// else this is the closest node on this path
					}else{
						close = p;
					}
					//if it is possible the other side has a better node check it
					if (close.distanceTo(q)>Math.abs(q.x-this.p.x)){
						//go to other side
						if (this.bottomRight != NULL_NODE){
							other = this.bottomRight.nearestNeighbor(q, Direction.Y);
						}
					}
				//go right
				}else{
					//continue right
					if (this.bottomRight != NULL_NODE){
						close = this.bottomRight.nearestNeighbor(q,Direction.Y);
					//else this is the closest node in this path
					}else{
						close = p;
					}
					//if it is possible the other side has a better node check it
					if (close.distanceTo(q)>Math.abs(q.x-this.p.x)){
						//go to other side
						if (this.topLeft != NULL_NODE){
							other = this.topLeft.nearestNeighbor(q, Direction.Y);
						}
					}
				}
				//if other side is closer then switch them
				if (other.distanceTo(q)<close.distanceTo(q)){
					close = other;
				}
			//if you are a y
			}else{
				//go up
				if(this.p.y>q.y){
					//continue up if you can
					if (this.topLeft != NULL_NODE){
						close = this.topLeft.nearestNeighbor(q,Direction.X);
					//else this is the closest node in this path
					}else{
						close = p;
					}
					//if it is possible the other side has a better node check it
					if (close.distanceTo(q)>Math.abs(q.y-this.p.y)){
						if (this.bottomRight != NULL_NODE){
							//go to other side
							other = this.bottomRight.nearestNeighbor(q, Direction.X);
						}
					}
				//go down
				}else{
					//continue down if you can
					if (this.bottomRight != NULL_NODE){
						close = this.bottomRight.nearestNeighbor(q, Direction.X);
					//else this is the closest node in this path	
					}else{
						close = p;
					}
					//if it is possible the other side has a better node check it
					if (close.distanceTo(q)>Math.abs(q.y-this.p.y)){
						if (this.topLeft != NULL_NODE){
							//go to other side
							other = this.topLeft.nearestNeighbor(q, Direction.X);
						}
					}
				}
				//if other side is closer then switch them
				if (other.distanceTo(q)<close.distanceTo(q)){
					close = other;
				}
			}
			//check if this node is more efficent
			if (close.distanceTo(q)>this.p.distanceTo(q)){
				close = p;
			}
			//return part
			return close;
		}
		private void buildString(StringBuilder sb) {
			if (this == NULL_NODE) {
				return;
			}
			if (topLeft != NULL_NODE) {
				sb.append("(");
				topLeft.buildString(sb);
				sb.append(")");
			}
			sb.append(String.format("%s(%4.2f,%4.2f)", label, p.x, p.y));
			if (bottomRight != NULL_NODE) {
				sb.append("(");
				bottomRight.buildString(sb);
				sb.append(")");
			}
		}

		private void drawInPlane(Graphics2D g2, double minX, double maxX, double minY, double maxY) {
			if (this == NULL_NODE) {
				return;
			}

			// Dot
			Ellipse2D.Double nodeDot = new Ellipse2D.Double(screenX(p.x) - DOT_RADIUS, screenY(p.y) - DOT_RADIUS,
					DOT_RADIUS * 2, DOT_RADIUS * 2);
			g2.fill(nodeDot);

			// Label
			int xOffset = this.dir == Direction.X ? 10 : 0;
			int yOffset = this.dir == Direction.X ? 0 : 20;
			g2.drawString(label, (int) screenX(p.x) + xOffset, (int) screenY(p.y) + yOffset);

			if (dir == Direction.X) {
				// Draw vertical line from (x, minY) to (x, maxY)
				Line2D.Double line = new Line2D.Double(new Point2D.Double(screenX(p.x), screenY(minY)),
						new Point2D.Double(screenX(p.x), screenY(maxY)));
				g2.draw(line);
				topLeft.drawInPlane(g2, minX, p.x, minY, maxY);
				bottomRight.drawInPlane(g2, p.x, maxX, minY, maxY);
			} else {
				// VERTICAL separation, so draw horizontal line
				Line2D.Double line = new Line2D.Double(new Point2D.Double(screenX(minX), screenY(p.y)),
						new Point2D.Double(screenX(maxX), screenY(p.y)));
				g2.draw(line);
				topLeft.drawInPlane(g2, minX, maxX, minY, p.y);
				bottomRight.drawInPlane(g2, minX, maxX, p.y, maxY);
			}
		}

		private void drawTree(Graphics2D g, double parentX, double parentY) {
			if (this == NULL_NODE) {
				return;
			}

			double centerX = this.inOrderNumber * xStep + MARGIN;
			double centerY = (this.depth + 1) * yStep + MARGIN;

			if (parentX > 0 && parentY > 0) {
				// Draws line
				g.setColor(Color.BLACK);
				double deltaX = parentX - centerX;
				double deltaY = parentY - centerY;
				double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
				double xOffset = deltaX * radius / distance;
				double yOffset = deltaY * radius / distance;
				Point2D.Double selfEdge = new Point2D.Double(centerX + xOffset, centerY + yOffset);
				Point2D.Double parentEdge = new Point2D.Double(parentX - xOffset, parentY - yOffset);
				g.draw(new Line2D.Double(selfEdge, parentEdge));
			}

			// Draws the circle
			Ellipse2D.Double circ = new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
			g.setColor(Color.WHITE);
			g.fill(circ);
			g.setColor(Color.BLACK);
			g.draw(circ);

			// Labels the circle
			g.setFont(font);
			// coefficients on radius determined experimentally
			g.drawString(label.toString(), (int) (centerX - 0.5 * radius), (int) (centerY + 0.6 * radius));

			// Direction
			Point2D.Double first = new Point2D.Double(centerX, centerY);
			Point2D.Double second = new Point2D.Double(centerX, centerY);
			if (dir == Direction.X) {
				first.y -= radius * DIRECTION_TYPE_SCALE_FACTOR;
				second.y += radius * DIRECTION_TYPE_SCALE_FACTOR;
			} else {
				first.x -= radius * DIRECTION_TYPE_SCALE_FACTOR;
				second.x += radius * DIRECTION_TYPE_SCALE_FACTOR;
			}
			g.setStroke(new BasicStroke(2));
			g.draw(new Line2D.Double(first, second));

			// Draw children
			topLeft.drawTree(g, centerX, centerY);
			bottomRight.drawTree(g, centerX, centerY);
		}

		// The next two are helpers for the drawTree.
		private int height() {
			if (this == NULL_NODE) {
				return -1;
			}
			return Math.max(topLeft.height(), bottomRight.height()) + 1;
		}

		private int setInOrderNumbers(int nextNumber) {
			if (this == NULL_NODE) {
				return nextNumber;
			}
			nextNumber = topLeft.setInOrderNumbers(nextNumber);
			this.inOrderNumber = nextNumber++;
			return bottomRight.setInOrderNumbers(nextNumber);
		}
	}
}