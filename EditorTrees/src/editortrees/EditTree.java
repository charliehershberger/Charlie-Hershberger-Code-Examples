package editortrees;

import editortrees.Node.Code;
import editortrees.Node.NodeRotCount;
// unless someone has comments we are done
// A height-balanced binary tree with rank that could be the basis for a text editor.

public class EditTree {

	private Node root;
	public final static Node NULL_NODE = new Node();
	private int rotations = 0;
	/**
	 * Construct an empty tree
	 */
	public EditTree() {
		root = NULL_NODE;
	}

	/**
	 * Construct a single-node tree whose element is c
	 * 
	 * @param c
	 */
	public EditTree(char c) {
		this.root = new Node(c);
	}

	/**
	 * Create an EditTree whose toString is s. This can be done in O(N) time,
	 * where N is the length of the tree (repeatedly calling insert() would be
	 * O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		this.root = NULL_NODE;
		this.root = this.root.fillTree(s);
	}
	/**
	 * Make this tree be a copy of e, with all new nodes, but the same shape and
	 * contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		//if (e.root != NULL_NODE) {
		//
		//this.root = buildTree(e.root);
		//this.root.balance=this.root.balance.SAME;
		//}
		Node myTreeNull = this.NULL_NODE;
		Node eTreeNull = e.NULL_NODE;
		
		
		
		this.root = new Node();
		
		Node rootNull = root.NULL_NODE;
		Node erootNull = e.root.NULL_NODE;
		this.root = this.root.makeTree(e.root);
	}
	
	public void setRoot(Node node)
	{
		this.root = node;
	}
	
	public Node buildTree(Node node){
		//set node info for current node
		Node ans = new Node(node.element);
		ans.balance = node.balance;
		ans.rank = node.rank;
		//recurse left
		if (node.left!= NULL_NODE){
			ans.left = buildTree(node.left);
		}
		//recurse right
		if (node.right!= NULL_NODE){
			ans.right = buildTree(node.right);
		}
		return ans;
	}

	/**
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		if (this.root==NULL_NODE || this.root == this.root.NULL_NODE){
			return -1;
		}
		return this.root.height; // replace by a real calculation.
	}

	/**
	 * 
	 * returns the total number of rotations done in this tree since it was
	 * created. A double rotation counts as two.
	 *
	 * @return number of rotations since tree was created.
	 */
	public int totalRotationCount() {
		return this.rotations; // replace by a real calculation.
	}

	/**
	 * return the string produced by an inorder traversal of this tree
	 */
	@Override
	public String toString() {
		if (this.root != NULL_NODE)
			return this.root.toString(); // replace by a real calculation.
		return "";
	}

	/**
	 * This one asks for more info from each node. You can write it like 
	 * the arraylist-based toString() method from the
	 * BST assignment. However, the output isn't just the elements, but the
	 * elements, ranks, and balance codes. Former CSSE230 students recommended
	 * that this method, while making it harder to pass tests initially, saves
	 * them time later since it catches weird errors that occur when you don't
	 * update ranks and balance codes correctly.
	 * For the tree with node b and children a and c, it should return the string:
	 * [b1=, a0=, c0=]
	 * There are many more examples in the unit tests.
	 * 
	 * @return The string of elements, ranks, and balance codes, given in
	 *         a pre-order traversal of the tree.
	 */
	public String toDebugString() {
		if (this.root != NULL_NODE)
			return this.root.toDebugString();
		return "[]";
	}

	
	
	
	/**
	 * 
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		return this.root.get(pos);
	}

	/**
	 * 
	 * @param c
	 *            character to add to the end of this tree.
	 */
	public void add(char c) {
		// Notes:
		// 1. Please document chunks of code as you go. Why are you doing what
		// you are doing? Comments written after the code is finalized tend to
		// be useless, since they just say WHAT the code does, line by line,
		// rather than WHY the code was written like that. Six months from now,
		// it's the reasoning behind doing what you did that will be valuable to
		// you!
		// 2. Unit tests are cumulative, and many things are based on add(), so
		// make sure that you get this one correct.
		if (this.root==NULL_NODE){
			this.root=new Node(c);
			this.root.balance=this.root.balance.SAME;
			return;
		}
		add(c,this.root.size());
	}

	/**
	 * 
	 * @param c
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             id pos is negative or too large for this tree
	 */
	public void add(char c, int pos) throws IndexOutOfBoundsException {
		//deal with errors
		if (this.root==NULL_NODE){
			if (pos<0 || pos>0){
				throw new IndexOutOfBoundsException();
			}
			this.root=new Node(c);
			this.root.balance=this.root.balance.SAME;
		} else {
			if (this.root.size() < pos || pos<0) {
				throw new IndexOutOfBoundsException();
			}
			//now solve the problem
			int num = this.rotations;
			NodeRotCount helper = this.root.add(c, pos);
			//answer in helper, put parts of answer in the right places 
			this.root = helper.node;
			this.rotations = num+helper.numRotations;
		}		
	} 
	
	/**
	 * 
	 * @return the number of nodes in this tree
	 */
	public int size() {
		if (this.root!=NULL_NODE && this.root!=this.root.NULL_NODE)
			return this.root.size();
		return 0;
	}

	/**
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		//deal with errors
		if (this.root==NULL_NODE || pos < 0 || pos >= this.size()) {
			throw new IndexOutOfBoundsException();
		}
		int num = this.rotations;
		char ans = this.get(pos);
		//answer in helper put in the right places
		NodeRotCount helper = this.root.delete(pos);
		this.root = helper.node;
		this.rotations = num+helper.numRotations;

		return ans;
	}

	/**
	 * This method operates in O(length*log N), where N is the size of this
	 * tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		int size = this.size();
		int end = pos + length -1;
		if(pos < 0 || pos >= size || end >= size || pos > end)
			throw new IndexOutOfBoundsException();
		return this.root.getSub(pos, length, 0, "").str;
	}

	/**
	 * This method is provided for you, and should not need to be changed. If
	 * split() and concatenate() are O(log N) operations as required, delete
	 * should also be O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length)
			throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete"
							: "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		if(this == other)
			throw new IllegalArgumentException();
		if(this.root == NULL_NODE || this.root == Node.NULL_NODE)
		{
			this.root = other.root;
			this.rotations = other.rotations;
			other.root = NULL_NODE;
		}
		if(other.root == NULL_NODE || this.root == Node.NULL_NODE)
		{
			return;
		}
		if(other.size()==1)
		{
			this.add(other.delete(0));
			return;
		}
		
		EditTree T = this;
		EditTree U = other;
		
		Node q;
		q = new Node(U.delete(0));
		
		this.root = paste(T,q,U);
		other.root = NULL_NODE;
		
		
	}
	
	private Node paste(EditTree T, Node q, EditTree U)
	{
		Node pp;
		
		Node iroot = new Node();
		iroot.right = T.root;
		
		pp = iroot.findPPr(U.root.height);
		if(pp != iroot)
		{
			q.left = pp.right;
			pp.right = q;
			q.right = U.root;
	
			adjustValue(q);
			q.rank = q.left.size();
		
			adjustValue(pp);
		}
		else
		{
			q.left = T.root;
			T.root = q;
			q.right = U.root;
			
			adjustValue(q);
			q.rank = q.left.size();
		
			adjustValue(pp);
		}

		T.root = T.root.reBalance();
		
		return T.root;
	}
	
	public static void adjustValue(Node q)
	{
		if(q.left.height > q.right.height)
		{
			q.height = q.left.height+1;
			q.balance = Code.LEFT;
		}
		else if(q.left.height < q.right.height)
		{
			q.height = q.right.height+1;
			q.balance = Code.RIGHT;
		}
		else
		{
			q.height = q.left.height+1;
			q.balance = Code.SAME;
		}
	}

	/**
	 * This operation must be done in time proportional to the height of this
	 * tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		if(this.root == null)
			throw new IndexOutOfBoundsException();
		
		
		twoTree result = this.root.split(pos,this.root.rank);
		
		this.root = result.main.root;
		
		return result.sub;
	}
	public class doubleTree {
		EditTree newTree;
		EditTree oldTree;
		public doubleTree (EditTree n, EditTree o) {
			this.newTree = n;
			this.oldTree = o;
		}
	}
	
	
	
	
//	private EditTree deleteright(EditTree newTree) {
//		int pos = this.root.rank;
//		Node temp = this.root;
//		while(this.root.getNodeAt(pos)!=temp.left) {
//			newTree.add(this.delete(pos));
//		}
//		return newTree;
//	}
//	private EditTree splitFrostsWay(EditTree newTree,int pos) {
//		if (pos<this.root.rank) {
//			//To the left
//			newTree = deleteright(newTree);
//		} else if (pos>this.root.rank) {
//			//To the right
//		} else {
//			//Right on
//			newTree = deleteright(newTree);
//		}
//	}
	
	
	
	
	
	
	
	
//	private doubleTree recursedown(Node node,EditTree newTree,EditTree oldTree,int pos) {
//		doubleTree ans;
//		if (pos<node.rank) {
//			//We need the stuff on the right to be a part of newTree
//			EditTree temp = new EditTree();
//			Node temproot = node;
//			temp.root = temproot.right;
//			temp.add(node.element);
//			newTree.concatenate(temp);
//			ans = recursedown(node.left,newTree,oldTree,pos);
//		} else if (pos>node.rank) {
//			EditTree temp = new EditTree();
//			Node temproot = node;
//			temp.root = temproot.left;
//			temp.add(node.element);
//			oldTree.concatenate(temp);
//			ans = recursedown(node.right,newTree,oldTree,pos-node.rank-1);
//		} else {
//			EditTree temp = new EditTree();
//			Node temproot = node.left;
//			temp.root = temproot;
//			oldTree.concatenate(temp);
//			temp = new EditTree();
//			temproot = node.right;
//			temp.root = temproot;
//			temp.add(node.element);
//			newTree.concatenate(temp);
//			ans = new doubleTree(newTree,oldTree);
//		}
//		return ans;
//	}
	

	/**
	 * Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		return this.find(s, 0);
	}

	/**
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		if (s.equals(""))
			return 0;
		if(this.root == NULL_NODE)
			return -1;
		// convert to string inorder
		StringBuilder bs = this.root.inOrder(new StringBuilder(""));
		String result = bs.toString();
		
		int temp = -1;
		int sIndex = 0;
		//search string for word
		for(int i = pos; i < result.length(); i++)
		{
			if(s.charAt(sIndex) == result.charAt(i))
			{
				if(sIndex == 0)
					temp = i;
				if(sIndex == s.length()-1)
					return temp;
				sIndex++;
			}
			else
				sIndex = 0;
		}
		
		return -1;
		
		
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		if (this.root!=NULL_NODE){
			
		}
		return this.root;
	}
	
	public static void main(String args[])
	{
		String s = "test";
		
		EditTree myTree = new EditTree(s);
	}
}
