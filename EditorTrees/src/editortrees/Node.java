package editortrees;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {
	enum Code {
		SAME, LEFT, RIGHT;
		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
				case LEFT:
					return "/";
				case SAME:
					return "=";
				case RIGHT:
					return "\\";
				default:
					throw new IllegalStateException();
			}
		}
	}
	
	// The fields would normally be private, but for the purposes of this class, 
	// we want to be able to test the results of the algorithms in addition to the
	// "publicly visible" effects
	public static final Node NULL_NODE = new Node();
	char element;
	int height;
	Node left, right; // subtrees
	int rank;         // inorder position of this node within its own subtree.
	Code balance;
	// Node parent;  // You may want this field.
	// Feel free to add other fields that you find useful

	// You will probably want to add several other methods

	// For the following methods, you should fill in the details so that they work correctly
	
	public Node(){
		
		this.left=NULL_NODE;
		this.right=NULL_NODE;
		this.balance = this.balance.SAME;
		this.height = -1;
	}
	public Node(char e){
		this.element = e;
		this.left=NULL_NODE;
		this.right=NULL_NODE;
		this.balance = Code.SAME;
		this.height = 0;
	}


	public int size() {
		if (this.right!=NULL_NODE)
			return this.rank+1+this.right.size();
		return this.rank+1;
	}
	
	
	
	public String toString(){
		if (this == NULL_NODE){
			return "";
		}
		return this.left.toString()+this.element+this.right.toString();
	}
	public String toDebugString(){
		if (this == NULL_NODE){
			return "[]";
		}
		if (this.left!=NULL_NODE && this.right!=NULL_NODE)
			return "["+this.element+this.rank+this.balance+", "+this.left.toDebugStringHelper()+", "+this.right.toDebugStringHelper()+"]";
		if (this.left!=NULL_NODE)
			return "["+this.element+this.rank+this.balance+", "+this.left.toDebugStringHelper()+"]";
		if (this.right!=NULL_NODE)
			return "["+this.element+this.rank+this.balance+", "+this.right.toDebugStringHelper()+"]";
		return "["+this.element+this.rank+this.balance+"]";
		
		
	}
	private String toDebugStringHelper() {
		if (this == NULL_NODE){
			return "";
		}
		if (this.left!=NULL_NODE && this.right!=NULL_NODE)
			return ""+this.element+this.rank+this.balance+", "+this.left.toDebugStringHelper()+", "+this.right.toDebugStringHelper();
		if (this.left!=NULL_NODE)
			return ""+this.element+this.rank+this.balance+", "+this.left.toDebugStringHelper();
		if (this.right!=NULL_NODE)
			return ""+this.element+this.rank+this.balance+", "+this.right.toDebugStringHelper();
		return ""+this.element+this.rank+this.balance;
	}
	
	/**
	 * 
	 * TODO Put here a description of what this method does.
	 *
	 * @param pos of the node you want to get the character from
	 * @return the character at pos position in the tree
	 * @throws IndexOutOfBoundsException when the index isn't between all possible indexes
	 */
	public char get(int pos) throws IndexOutOfBoundsException{
		if(this == EditTree.NULL_NODE || this == NULL_NODE)
			throw new IndexOutOfBoundsException();
		
		if (pos < this.rank){
			return this.left.get(pos);
		}
		else if(pos > this.rank){
			return this.right.get(pos-this.rank-1);
		}
		else{
			return this.element;
		}
	}
	/**
	 * 
	 * TODO Put here a description of what this method does.
	 *
	 * @param pos
	 * @param length
	 * @param cnt
	 * @param str
	 * @return
	 */
	public SubEntry getSub(int pos, int length, int cnt, String str)
	{	
		if(this == NULL_NODE || this == EditTree.NULL_NODE || length == cnt)
		{
			return new SubEntry(cnt,str);
		}
		else if(this.rank > pos)
		{
			SubEntry leftResult = this.left.getSub(pos, length, cnt, str);
			if(leftResult.num < length)
			{
				return this.right.getSub(0, length, leftResult.num+1, leftResult.str + this.element);
			}
			return new SubEntry(leftResult.num,leftResult.str);
		}
		else if(this.rank < pos)
		{
			return this.right.getSub(pos-1-this.rank, length, cnt, str);
		}
		else
		{
			return this.right.getSub(0, length, cnt+1, str + this.element);
		}
		
	}	
	class SubEntry
	{
		public int num;
		public String str;
		public SubEntry(int num, String str)
		{
			this.num = num;
			this.str = str;
		}	
	}
	/**
	 * 
	 * TODO adds a character c after position pos.
	 *
	 * @param c the character you are adding
	 * @param pos the position you are adding after
	 * @return
	 */
	public NodeRotCount add(char c, int pos) {		
		int Rots = 0;
		// runniong total of rotations
		if (pos < this.rank) {
			this.rank++;
			NodeRotCount helper = this.left.add(c,pos);
			//recurse left
			Rots += helper.numRotations;
			this.left = helper.node;
		} else if (pos > this.rank) {
			//add right if empty
			if (this.right == NULL_NODE) {
				this.right = new Node(c);
			//recurse right
			} else {
				NodeRotCount helper = this.right.add(c,pos-this.rank-1);
				Rots += helper.numRotations;
				this.right = helper.node;
			}
		} else {
			//add left if empty
			this.rank++;
			Node node = new Node(c);
			NodeRotCount helper = new NodeRotCount(node,Rots);
			Rots += helper.numRotations;
			if (this.left == NULL_NODE) {
				this.left = helper.node;
			} else {
				NodeRotCount help = this.left.add(c,pos);
				this.left = help.node;
				Rots += help.numRotations;
			}
		}
		//check balance and return all data up
		NodeRotCount ans = this.checkBalance();
		return new NodeRotCount(ans.node, ans.numRotations+Rots);
	}
	/**
	 * 
	 * TODO delete the object at a given position.
	 *
	 * @param pos the position we delete the item at
	 * @return the node and rotations in a way it can be parsed
	 */
	public NodeRotCount delete(int pos) {		
		int Rots = 0;
		if (pos < this.rank) {
			if (this.left == NULL_NODE) {
				//error here
			}
			this.rank--;
			NodeRotCount helper = this.left.delete(pos);
			Rots += helper.numRotations;
			this.left = helper.node;
		} else if (pos > this.rank) {
			if (this.right == NULL_NODE) {
				//error here
			} else {
				NodeRotCount helper = this.right.delete(pos-this.rank-1);
				Rots += helper.numRotations;
				this.right = helper.node;
			}
		} else {
			if (this.left == NULL_NODE && this.right == NULL_NODE) {
				return new NodeRotCount(NULL_NODE,0);
			}else if(this.left!=NULL_NODE&&this.right==NULL_NODE){ 
				return this.left.checkBalance();
			}else if(this.left==NULL_NODE&&this.right!=NULL_NODE){
				return this.right.checkBalance();
			}else{
				if (this.right.left == NULL_NODE) {
					this.element = this.right.element;
					NodeRotCount helper = this.right.delete(this.right.rank);
					this.right = helper.node;
					Rots += helper.numRotations;
					NodeRotCount ans = this.checkBalance();
					return new NodeRotCount(ans.node, ans.numRotations+Rots);
				} else {
					Node temp = this.right;
					while (temp.left != NULL_NODE) {
						temp = temp.left;
					}
					this.element = temp.element;
					NodeRotCount helper = this.right.delete(0);
					this.right = helper.node;
					Rots+= helper.numRotations;
					NodeRotCount ans = this.checkBalance();
					return new NodeRotCount(ans.node, ans.numRotations+Rots);
				}
			}
		}
		NodeRotCount ans = this.checkBalance();
		return new NodeRotCount(ans.node, ans.numRotations+Rots);
	}
	public class NodeRotCount{
		Node node;
		int numRotations;
		public NodeRotCount(Node node, int numRotations){
			this.node = node;
			this.numRotations = numRotations;
		}
	}
	/**
	 * 
	 * TODO balances and rotates the tree.
	 *
	 * @return the node and the amount of rotations in a way that it can be parsed
	 */
	public NodeRotCount checkBalance() {
		if (this != NULL_NODE) {
		int lh = this.left.height;
		int rh = this.right.height;
		if (rh-lh >= 2) {
			// There is an imbalance to the right
			int num = 0;
			if (this.right.balance == Code.LEFT) {
				num=+2;
				Node ans = this.rotateDL();
				return new NodeRotCount(ans, num);
			} else {
				num++;
				Node ans = this.rotateSL();
				return new NodeRotCount(ans, num);
			}
		} else if (lh-rh >= 2) {
			// There is an imbalance to the left
			int num=0;
			if (this.left.balance == Code.RIGHT) {
				num += 2;
				Node ans = this.rotateDR();
				return new NodeRotCount(ans, num);
			} else {
				num++;
				Node ans = this.rotateSR();
				return new NodeRotCount(ans, num);
			}
		} else if (rh-lh == 1) {
			this.balance = Code.RIGHT;
			this.height = this.right.height+1;
		} else if (lh-rh == 1) {
			this.balance = Code.LEFT;
			this.height = this.left.height+1;
		} else {
			this.balance = Code.SAME;
			this.height = this.left.height+1;
		}
		if (lh >= 0) {
			rank = this.left.size();
		} else {
			rank = 0;
		}
		return new NodeRotCount(this, 0);
		}
		return new NodeRotCount(this, 0);
	}
	
	public Node rotateSL(){
		Node temp = this.right;
		this.right = temp.left;
		temp.left = this.checkBalance().node;
		return temp.checkBalance().node;
	}
	public Node rotateSR(){
		Node temp = this.left;
		this.left = temp.right;
		temp.right = this.checkBalance().node;
		return temp.checkBalance().node;
	}
	public Node rotateDL() {
		Node temp;
		temp = this.right;
		this.right = temp.left;
		temp.left = this.right.right;
		this.right.right = temp.checkBalance().node;
		this.right = this.right.checkBalance().node;
		return this.rotateSL();
	}
	public Node rotateDR() {
		Node temp;
		temp = this.left;
		this.left = temp.right;
		temp.right = this.left.left;
		this.left.left = temp.checkBalance().node;
		this.left = this.left.checkBalance().node;
		return this.rotateSR();
	}
	
	public Node makeTree(Node target)
	{
		Node myTreeNull = EditTree.NULL_NODE;
		if(target == EditTree.NULL_NODE || target == NULL_NODE)
		{
			return NULL_NODE;
		}
		//set elements to match
		this.element = target.element;
		this.balance = target.balance;
		this.rank = target.rank;
		this.height = target.height;
		
		this.left = new Node();
		this.right = new Node();
		
		//recurse left and right
		
		this.left = this.left.makeTree(target.left);
		this.right = this.right.makeTree(target.right);
		
		return this;
	}
	public class StringInt{
		public String string = "";
		public int pos;
		public int index;
		public StringInt(String string, int pos, int index){
			this.string = string;
			this.pos = pos;
			this.index = index;
		}
	}
	
	public StringBuilder inOrder(StringBuilder s)
	{
		StringBuilder bs = s;
		
		if(this.left!=NULL_NODE)
			bs = this.left.inOrder(s);
		
		bs.append(this.element);
		
		if(this.right!=NULL_NODE)
			return this.right.inOrder(bs);
		
		return bs;
	}
	
	
	
	
	
	
	
	class intBol
	{
		public int curpos;
		public int sIndex;
		public boolean found;
		public intBol(int curpos, int sIndex, boolean found)
		{
			this.curpos = curpos;
			this.sIndex = sIndex;
			this.found = found;
		}
		
	}
	
	
	/**
	 * 
	 * TODO return the node at the given position
	 *
	 * @param pos position of place you get the node from in tree
	 * @return the node you are looking for at the given position
	 */
	public Node getNodeAt(int pos)
	{
		Node result;
		//look right
		if(pos > this.rank)
		{
			if(this.right == NULL_NODE)
				return null;
			this.right = this.right.getNodeAt(pos-1-this.rank);
			result = this.checkBalance().node;
		}
		//look left
		else if(pos < this.rank)
		{
			if(this.left == NULL_NODE)
				return null;
			this.left = this.left.getNodeAt(pos);
			
			result = this.checkBalance().node;
		}	
		// when you find theight position
		else
		{
			this.left = NULL_NODE;
			this.height = this.right.height+1;
			
			result = this.checkBalance().node;
		}
		
		return result;
	}
	/**
	 * 
	 * TODO get postion of this node in the tree
	 *
	 * @return the postion of this node in the tree 
	 */
	public int pos(){
		if (this == NULL_NODE){
			return 0;
		}
		return this.rank+this.right.pos();
	}
	
	public Node fillTree(String s) {
		char ele = s.charAt(s.length()/2);
		Node result = new Node();
		
		this.left = NULL_NODE;
		this.right = NULL_NODE;
		
		if(s.length()>1)//If we have at least 2 chars left, left sub tree will be created
		{
			String ls = s.substring(0,s.length()/2);
			result.rank = ls.length();
			result.left = this.left.fillTree(ls);
		}
		if(s.length()>2)//If we have at least 3 chars left, right sub tree will be created
		{
			String rs = s.substring(s.length()/2 + 1);
			result.right = this.right.fillTree(rs);
		}
		
		//Check & Assign balance 
		if(result.left.height > result.right.height)
		{
			result.height = result.left.height+1;
			this.balance = Code.LEFT;
		}
		else if(result.left.height < result.right.height)
		{	
			result.height = result.right.height+1;
			this.balance = Code.RIGHT;
		}
		else
		{
			result.height = result.right.height+1;
			this.balance = Code.SAME;
		}
		
		
		result.element = ele;
		return result;
	}
	
	public Node findPPr(int h)
	{
		if(this.right.height - h <= 1)
		{
			return this;
		}
		return this.right.findPPr(h);
		
	}
	
	public Node findPPl(int h)
	{
		if(this.left.height - h <= 1)
		{
			return this;
		}
		return this.left.findPPl(h);
		
	}
	public Node reBalance() {
		if(this.right != NULL_NODE && Math.abs(this.right.left.height - this.right.right.height) > 1)
		{
			this.right = this.right.reBalance();
			return this;
		}
		return this.checkBalance().node;
	}
	
	public twoTree split(int pos, int cur)
	{
		if(this == NULL_NODE || this == EditTree.NULL_NODE)
			return new twoTree(new EditTree(), new EditTree());
		
		
		twoTree result;
		EditTree temp = new EditTree(this.element);
		if(cur < pos)
		{		
			result = this.right.split(pos, cur + 1 + this.right.rank);
			
			temp.concatenate(result.main);
			
			
			
			if(this.left!=NULL_NODE)
			{
				EditTree leftTree = new EditTree();
				leftTree.setRoot(this.left);
				leftTree.concatenate(temp);
				
				result.main = leftTree;
			}
			else
			{
				result.main = temp;
			}
			
			return result;
		}
		else if(cur > pos)
		{
			result = this.left.split(pos, cur - (this.rank - this.left.rank));
			
			result.sub.add(this.element);
			
			if(this.right!=NULL_NODE)
			{
				EditTree rightTree = new EditTree();
				rightTree.setRoot(this.right);
				result.sub.concatenate(rightTree);
			}
			
			return result;
		}
		
		EditTree main = new EditTree();
		if(this.left!=NULL_NODE)
		main.setRoot(this.left);
		
		EditTree sub = new EditTree();
		if(this.right!=NULL_NODE)
		sub.setRoot(this.right);
		
		temp.concatenate(sub);
		
		return new twoTree(main,temp);
	}
	
	
}