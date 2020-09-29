**************************************************************************************************************************************************************************
                                                                            1. Zombie in a Matrix 
**************************************************************************************************************************************************************************
// Min hours to send file to all available servers - question can be rephrased as this as well
/*Given a 2D grid, each cell is either a zombie 1 or a human 0. Zombies can turn adjacent (up/down/left/right) human beings into zombies every hour.
Find out how many hours does it take to infect all humans?

Example:
Input:
[[0, 1, 1, 0, 1],
 [0, 1, 0, 1, 0],
 [0, 0, 0, 0, 1],
 [0, 1, 0, 0, 0]]

Output: 2

Explanation:
At the end of the 1st hour, the status of the grid:
[[1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [0, 1, 0, 1, 1],
 [1, 1, 1, 0, 1]]

At the end of the 2nd hour, the status of the grid:
[[1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1]]
int minHours(int rows, int columns, List<List<Integer>> grid) {
	// todo
}
*/
//Hint: 
//  if all matrix are person, return - 1
//	if no person in matrix, return 0;
//	if all person are infected but your BFS queue still > 0 , break;

// The time complexity is just O(mn) because each cell is visited no more than 4 times, so the upper bound is O(4mn).

private static int zombieHours(int row, int column, List<List<Integer>> grid) {
		if(grid==null || grid.size()==0)
			return 0;
		int count = 0;
		int height = 0;
		int total = row * column;
		Queue<GraphNode> que = new LinkedList<>();
		for(int i=0; i<row;i++) {
			for(int j=0; j<column;j++) {
				if(grid.get(i).get(j)==1) {
				que.offer(new GraphNode(i, j));
				count++;
				}
			}
		}
		//if all matrix are person, return - 1
		if(count==0)
			return -1;
		//if no person in matrix, return 0;
		if(count==total)
			return 0;
		
		while(!que.isEmpty()) {
			if(count==total)
				return height;
			int size = que.size();
			for(int i=0; i<size;i++) {
				GraphNode node = que.poll();
				List<GraphNode> neighbours = getGraphNodes(node.i, node.j, grid);
				for(GraphNode position : neighbours) {
					grid.get(position.i).set(position.j,1);
					que.offer(position);
					count++;
				}
			}
			height++;
		}		
		return -1;
	}
	
	
	private static List<GraphNode> getGraphNodes(int i, int j, List<List<Integer>> grid){
		List<GraphNode> list = new ArrayList<ZombieLinkedList.GraphNode>();
		int[][] directions = {{0,-1},{-1,0},{0,1},{1,0}};
		for(int[] d : directions) {
			int left = d[0]+i;
			int right = d[1]+j;
			if(left>=0 && right>=0 && left<grid.size() && right<grid.get(0).size() && grid.get(left).get(right)==0) {
				list.add(new GraphNode(left, right));
			}
		}
		return list;
	}
	
	static class GraphNode {
		int i;
		int j;
		public GraphNode(int i, int j) {
			this.i = i;
			this.j = j;
		}
	}


**************************************************************************************************************************************************************************
                                                                            2. Min Cost to Connect All Nodes
**************************************************************************************************************************************************************************
Given an undirected graph with n nodes labeled 1..n. Some of the nodes are already connected. The i-th edge connects nodes edges[i][0] and edges[i][1] together. Your
task is to augment this set of edges with additional edges to connect all the nodes. 
Find the minimum cost to add new edges between the nodes such that all the nodes are accessible from each other.

Input:

n, an int representing the total number of nodes.
edges, a list of integer pair representing the nodes already connected by an edge.
newEdges, a list where each element is a triplet representing the pair of nodes between which an edge can be added and
the cost of addition, respectively (e.g. [1, 2, 5] means to add an edge between node 1 and 2, the cost would be 5).
Example 1:

Input: n = 6, edges = [[1, 4], [4, 5], [2, 3]], newEdges = [[1, 2, 5], [1, 3, 10], [1, 6, 2], [5, 6, 5]]
Output: 7
Explanation:
There are 3 connected components [1, 4, 5], [2, 3] and [6].
We can connect these components into a single component by connecting node 1 to node 2 and node 1 to node 6 at a minimum cost of 5 + 2 = 7.

Use Krushals algo: https://www.youtube.com/watch?v=JZBQLXgSGfs
arrange all egdes in ascending order of weight - we can use minheap for this
add edge one by one, 
see if it its not forming same group,  (group - if nodes are connected to each other it forms a group)
if forms same group, do not add
if forms a new group or connects 2 diff groups, add edge
continue above step in loop untill, the no of components become 1
or iterate all elements in heap, but increment count only if new edge is added

We can do this in 2 approaches
1) Graph
create a graph with nodes and add edges
pop element from min heap and perform a check if this element is in the same group (custom logic)
if in same group, ignore
if in diff groups, add it and increment count
continue untill connected components reach 1
the custom logic, involves extra loops which affect performance
better approach woule be below union find approach.
2) Union find
https://www.youtube.com/watch?v=0jNmHPfA_yE
https://www.youtube.com/watch?v=KbFlZYCpONw
use Union find approach
to perform below portion of logic, we can choose union find approach
initially, mark all nodes as root. they connect to themselfves
add edge, one end of edge becomes root and other its child, we can choose any parent
better logic is to maintain a rank for each node and assign node with higher ran as parent
find logic, should get the root node, so traverse to parent until node==parent	

public int getMinCost(int nodes, List<List<Integer>> edges, List<List<Integer>> newEdges) {
		UnionFind uf = new UnionFind(nodes);
		for(List<Integer> edge : edges) {
			uf.union(edge.get(0), edge.get(1));
		}
		PriorityQueue<HeapNode> minHeap = new PriorityQueue<>();
		for(List<Integer> newEdge : newEdges) {
			minHeap.offer(new HeapNode(newEdge.get(0), newEdge.get(1), newEdge.get(2)));
		}
		int cost = 0;
		while(!minHeap.isEmpty()) {
			HeapNode node = minHeap.poll();
			boolean isValid = uf.union(node.left, node.right);
			if(!isValid)
				continue;
			cost += node.weight;
		}
		return cost;
	}
	
	static class HeapNode implements Comparable<HeapNode>{
		
		int left;
		int right;
		int weight;
		public HeapNode(int left, int right, int weight) {
			this.left = left;
			this.right = right;
			this.weight = weight;
		}
		
		public int compareTo(HeapNode h) {
			return Integer.compare(this.weight, h.weight);
		}
	}
	
	
	static class Node{
		int id;
		int rank;
		Node parent;
		public Node(int id, int rank) {
			this.id = id;
			this.rank = rank;
		}
	}
	static class UnionFind{
		Map<Integer,Node> map = new HashMap<>();
		
		public UnionFind(int n) {
			for(int i=1; i<=n; i++) {
				Node node = new Node(i,0);
				node.parent = node;
				map.put(i, node);
			}
		}
		public boolean union(int p, int q) {
			int pParent = find(p);
			int qParent = find(q);
			// parents are same, both are in same group, return
			if(pParent==qParent)
				return false;
			Node pParNode = map.get(pParent);
			Node qParNode = map.get(qParent);
			// diff parents
			// based on rank decide, which needs to be parent
			if(pParNode.rank==qParNode.rank) {
				pParNode.rank +=1;
				qParNode.parent = pParNode;
			}else if(pParNode.rank>qParNode.rank) {
				qParNode.parent = pParNode;
			}else {
				pParNode.parent = qParNode;
			}
			
			return true;
		}
		
		public int find(int id) {
			int parentId = map.get(id).parent.id;
			if(parentId==id)
				return id;
			return find(parentId);
		}
	}

Graph Approach :

public int getMinCost(int nodes, List<List<Integer>> edges, List<List<Integer>> newEdges) {
		Graph graph = new Graph(nodes, edges);
		PriorityQueue<HeapNode> heap = new PriorityQueue<HeapNode>();
		List<HeapNode> finalResult = new ArrayList<MinCostToConnectNodes.HeapNode>();
		int count = 0;
		for(int i=0; i<newEdges.size();i++) {
			heap.offer(new HeapNode(newEdges.get(i).get(0),
					newEdges.get(i).get(1), newEdges.get(i).get(2)));
		}
		while(true) {
			List<List<Integer>> connections  = graph.getConnectedComponents(nodes);
			if(connections.size()==1) {
				break;
			}
			HeapNode heapNode = heap.poll();
			boolean checkIfValid = isValid(heapNode, connections);
			if(checkIfValid) {
				GraphNode left = graph.map.get(heapNode.left);
				GraphNode right = graph.map.get(heapNode.right);
				left.connections.add(right);
				right.connections.add(left);
				count += heapNode.weight;
				finalResult.add(heapNode);
				System.out.println(heapNode.left + " " + heapNode.right + " " + heapNode.weight);
			}			
		}
		
		return count;
	} 
	
	private boolean isValid(HeapNode heapNode, List<List<Integer>> connections) {
		for(int i=0; i<connections.size();i++) {
			boolean left = false;
			boolean right = false;
			List<Integer> innerList = connections.get(i);
			for(int j=0; j<innerList.size();j++) {
				if(innerList.get(j) == heapNode.left) {
					left = true;
				}
				if(innerList.get(j) == heapNode.right) {
					right = true;
				}
			}
			if(left==true && right==true) {
				return false;
			}
		}
		return true;
	}

	static class HeapNode implements Comparable<HeapNode>{
		int left;
		int right;
		int weight;
		public HeapNode(int left, int right, int weight) {
			this.left = left;
			this.right = right;
			this.weight = weight;
		}		
		@Override
		public int compareTo(HeapNode h) {
			return Integer.compare(this.weight, h.weight);
		}
	}
	static class Graph{
		Map<Integer,GraphNode> map = new HashMap<Integer, GraphNode>();

		public Graph(int nodes, List<List<Integer>> edges){
			for(int i=1;i<=nodes;i++) {
				map.put(i, new GraphNode(i));
			}
			for(List<Integer> edge : edges) {
				int left = edge.get(0);
				int right = edge.get(1);
				map.get(left).connections.add(map.get(right));
				map.get(right).connections.add(map.get(left));
			}
		}

		public List<List<Integer>> getConnectedComponents(int nodes){
			List<List<Integer>> components = new ArrayList<List<Integer>>();
			Map<Integer,Boolean> visited = new HashMap<>();
			for(int i=1;i<=nodes;i++) {
				visited.put(i, Boolean.FALSE);
			}
			for(int i=1; i<=nodes;i++) {
				if(!visited.get(i)) {
					List<Integer> conection = new ArrayList<Integer>();
					Queue<GraphNode> que = new LinkedList<>();
					que.offer(map.get(i));
					visited.put(i, Boolean.TRUE);
					conection.add(i);
					while(!que.isEmpty()) {
						GraphNode node = que.poll();
						for(GraphNode item : node.connections) {
							if(!visited.get(item.id)) {
								que.offer(map.get(item.id));
								conection.add(item.id);
								visited.put(item.id, Boolean.TRUE);
							}
						}
					}
					components.add(conection);
				}
			}
			return components;
		}
	}

	static class GraphNode{
		int id; 
		List<GraphNode> connections = new ArrayList<>();
		public GraphNode(int id) {
			this.id= id;
		}
	}

**************************************************************************************************************************************************************************
                                                                            3. Min Cost to Repair Edges
**************************************************************************************************************************************************************************

There's an undirected connected graph with n nodes labeled 1..n. But some of the edges has been broken disconnecting the graph. 
Find the minimum cost to repair the edges so that all the nodes are once again accessible from each other.

Input:

n, an int representing the total number of nodes.
edges, a list of integer pair representing the nodes connected by an edge.
edgesToRepair, a list where each element is a triplet representing the pair of nodes between which an edge is currently broken and
the cost of repearing that edge, respectively (e.g. [1, 2, 12] means to repear an edge between nodes 1 and 2, the cost would be 12).
Example 1:

Input: n = 5, edges = [[1, 2], [2, 3], [3, 4], [4, 5], [1, 5]], edgesToRepair = [[1, 2, 12], [3, 4, 30], [1, 5, 8]]
Output: 20
Explanation:
There are 3 connected components due to broken edges: [1], [2, 3] and [4, 5].
We can connect these components into a single component by repearing the edges between nodes 1 and 2, and nodes 1 and 5 at a minimum cost 12 + 8 = 20.
Example 2:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [3, 5], [1, 6], [2, 4]], edgesToRepair = [[1, 6, 410], [2, 4, 800]]
Output: 410
Example 3:

Input: n = 6, edges = [[1, 2], [2, 3], [4, 5], [5, 6], [1, 5], [2, 4], [3, 4]], edgesToRepair = [[1, 5, 110], [2, 4, 84], [3, 4, 79]]
Output: 79

Same as above problem with few changes,
Skip the pairs in “edges” which appear in “edgesToRepair”(May use hash to speed up), connect all other edges in "edges" by using union-find.
Than apply Kruskal's algorithm on "edgesToRepair", If some of them have already been connected before(have the same parents in Union-find),
we would skip these edges.


	public int getMinCost(int nodes, List<List<Integer>> edges, List<List<Integer>> newEdges) {
		UnionFind uf = new UnionFind(nodes);
		// Logic to cache broken edge details and remove them from adding 
		Map<Integer,Set<Integer>> validate = new HashMap<>();
		for(List<Integer> newEdge : newEdges) {
			int left = newEdge.get(0);
			int right = newEdge.get(1);
			if(validate.containsKey(left)) {
				validate.get(left).add(right);
			}else {
				Set<Integer> set = new HashSet<>();
				set.add(right);
				validate.put(left, set);
			}
			if(validate.containsKey(right)) {
				validate.get(right).add(left);
			}else {
				Set<Integer> set = new HashSet<>();
				set.add(left);
				validate.put(right, set);
			}
		}
		for(List<Integer> edge : edges) {
			if((validate.containsKey(edge.get(0)) && validate.get(edge.get(0)).contains(edge.get(1))) || 
					(validate.containsKey(edge.get(1)) && validate.get(edge.get(1)).contains(edge.get(0)))) {
				continue;
			}
			uf.union(edge.get(0), edge.get(1));
		}
		PriorityQueue<HeapNode> minHeap = new PriorityQueue<>();
		for(List<Integer> newEdge : newEdges) {
			minHeap.offer(new HeapNode(newEdge.get(0), newEdge.get(1), newEdge.get(2)));
		}
		int cost = 0;
		while(!minHeap.isEmpty()) {
			HeapNode node = minHeap.poll();
			boolean isValid = uf.union(node.left, node.right);
			if(!isValid)
				continue;
			cost += node.weight;
		}
		return cost;
	}

Union find classes and Heap node are same as above problem

**************************************************************************************************************************************************************************
                                                                            4. Subtree with Maximum Average
**************************************************************************************************************************************************************************
Given an N-ary tree, find the subtree with the maximum average. Return the root of the subtree.
A subtree of a tree is the node which have at least 1 child plus all its descendants. The average value of a subtree is the sum of its values, divided by the number of nodes.

Example 1:

Input:
        20
       /   \
     12     18
  /  |  \   / \
11   2   3 15  8

Output: 18
Explanation:
There are 3 nodes which have children in this tree:
12 => (11 + 2 + 3 + 12) / 4 = 7
18 => (18 + 15 + 8) / 3 = 13.67
20 => (12 + 11 + 2 + 3 + 18 + 15 + 8 + 20) / 8 = 11.125

18 has the maximum average so output 18.
	
https://leetcode.com/problems/maximum-average-subtree

	double max = Integer.MIN_VALUE;
	static int maxNode = Integer.MIN_VALUE;
private double[] dfs(BinaryTreeNode node) {
		if(node.childs==null || node.childs.size()==0)
			return new double[] {node.value,1}; 
		
		double sum = node.value;
		int count = 1;
		for(BinaryTreeNode treeNode : node.childs) {
			double[] childData = dfs(treeNode);
			sum += childData[0];
			count += childData[1];
		}		
		if(count >1 && (sum/count)> max) {
			max = (sum/count);
			maxNode = node.value;
		}
		return new double[] {sum,count};
	}

	static class BinaryTreeNode {
		int value;
		List<BinaryTreeNode> childs;
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public List<BinaryTreeNode> getChilds() {
			return childs;
		}
		public void setChilds(List<BinaryTreeNode> childs) {
			this.childs = childs;
		}
		public BinaryTreeNode(int val) {
			this.value = val;
		}
		
	}

**************************************************************************************************************************************************************************
                                                                            5. Distance Between Nodes in BST
**************************************************************************************************************************************************************************
Given a list of unique integers nums, construct a BST from it (you need to insert nodes one-by-one with the given order to get the BST) and find the distance between two nodes node1 and node2. Distance is the number of edges between two nodes. If any of the given nodes does not appear in the BST, return -1.

Example 1:

Input: nums = [2, 1, 3], node1 = 1, node2 = 3
Output: 2
Explanation:
     2
   /   \
  1     3
https://leetcode.com/problems/insert-into-a-binary-search-tree/
https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
https://www.techiedelight.com/distance-between-given-pairs-of-nodes-binary-tree/
Build BST
Ensure Both nodes are present, else return -1
Find LCA
Find Distance from LCA to given points

	static TreeNode lowestCommonAncestor;
	public int distance(int[] treeArray, int i, int j) {
		// create BST using array
		// ensure both nodes are present
		// find Lowest common ancestor of 2 nodes
		// find distance btw nodes to lcm and sum them
		TreeNode root = null;
		for(int len=0; len<treeArray.length;len++) {
			root = constructBST(root, treeArray[len]);
		}
		// if any node is not in BST, we return -1
		if(isNodePresent(root, i) && isNodePresent(root, j)) {
			findLowestCommonAncestor(root, i, j);
		}else {
			return -1;
		}
		
		int totalDistance = findDistance(lowestCommonAncestor, i, 0) 
				+ findDistance(lowestCommonAncestor, j, 0);
		
		return totalDistance;
	}

	public int findDistance(TreeNode lcm, int i, int level) {
		//base case
		if(lcm==null)
			return Integer.MIN_VALUE;
		//return level if node is found
		if(lcm.value==i)
			return level;
		// search node in left subtree
		int left = findDistance(lcm.leftSide, i, level+1);
		// if node is found in left subtree, return
		if(left!=Integer.MIN_VALUE) {
			return left;
		}
		// else continue the search in right subtree
		return findDistance(lcm.rightSide, i, level+1);
	}

	// we go over dfs to each node. for leaf, we return 0
	// this will be popped above
	// if at any point, we see one of the nodes, we increment the total by 1
	// once we see the other node we increment by 1
	// at ever node, total is  - node's val && nodes left sub tree && rit sub tree
	// if total is 2, meaning, we found both nodes. set it as LowestCommonAncestor . return
	public int findLowestCommonAncestor(TreeNode root, int i, int j) {
		if(root==null)
			return 0;

		int left = findLowestCommonAncestor(root.leftSide, i, j);
		int right = findLowestCommonAncestor(root.rightSide, i, j);
		int total = 0;
		if(root.value == i || root.value==j) {
			total +=1;
		}		
		total = total + left + right;
		if(total==2) {			
			lowestCommonAncestor = root;
			return Integer.MAX_VALUE;
		}
		return total;
	}

	public TreeNode constructBST(TreeNode root, int i) {
		if(root==null) {
			root = new TreeNode(i);
			return root;
		}
		if(root.value < i) {			
			if(root.rightSide ==null) {
				root.rightSide = new TreeNode(i);
			}else {
				constructBST(root.rightSide, i);
			}
		}else if (root.value > i) {
			if(root.leftSide==null) {
				root.leftSide = new TreeNode(i);
			}else {
				constructBST(root.leftSide, i);
			}
		}
		return root;
	}

	public boolean isNodePresent(TreeNode root, int val) {
		if(root==null)
			return false;
		// if node is found, return true
		if(root.value==val) {
			return true;
		}
		// return true if node is found in the left subtree or right subtree
		boolean left = isNodePresent(root.leftSide, val);
		boolean right = isNodePresent(root.rightSide, val);
		return left || right;
	}

**************************************************************************************************************************************************************************
                                                                            6. Subtree of Another Tree
**************************************************************************************************************************************************************************
Given two non-empty binary trees s and t, check whether tree t has exactly the same structure and node values with a subtree of s. 
A subtree of s is a tree consists of a node in s and all of this node's descendants. 
The tree s could also be considered as a subtree of itself.
Example 1:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
Given tree t:
   4 
  / \
 1   2
Return true, because t has the same structure and node values with a subtree of s.
 

Example 2:
Given tree s:

     3
    / \
   4   5
  / \
 1   2
    /
   0
Given tree t:
   4
  / \
 1   2
Return false.
	
Time complexity : O(m*n). In worst case(skewed tree) traverse function takes O(m*n) time.

Space complexity : O(n). The depth of the recursion tree can go upto nn. nn refers to the number of nodes in ss.
	
we make use a function traverse(s,t) which traverses over the given tree ss and treats every node as the root of the subtree currently being considered.
https://leetcode.com/problems/subtree-of-another-tree/
    
    private boolean checkIfSame(TreeNode s, TreeNode t){
        if(s==null && t==null)
            return true;
        if(s==null || t==null)
            return false;
        if(s.val!=t.val){           
            return false;
        }
        
        boolean left = checkIfSame(s.left,t.left);
        boolean right = checkIfSame(s.right,t.right);
        return left && right;
    }
    
    private boolean traverse(TreeNode s, TreeNode t){
        return s!=null && (checkIfSame(s,t) || traverse(s.left,t) || traverse(s.right,t));
    }
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s==null || t==null)
            return false;
        return traverse(s,t);        
    }


**************************************************************************************************************************************************************************
                                                                            7. Subtree with Maximum Average
**************************************************************************************************************************************************************************

Given a list of item associations, write an algorithm that outputs the largest item association group. If two groups have ssame length, select group which
appears first based on lexicographic order.
https://leetcode.com/discuss/interview-question/782606/

// union find - form all groups
// create a map- key will be parent - iterate veery element, takes its parent and add in the obj as list
// create a max heap of keys of map and lexo first string of list 
// Create a custom class to add in heap, implement comparable to sort based on the count and if same, use lexo string order
  
//public class LargestItemAssociation {

	public static void main (String args[]) {
		
		List<PairString> itemAssociation = new ArrayList<>();
		PairString s1 = new PairString("Item1","Item2");
		PairString s2 = new PairString("Item9","Item4");
		PairString s3 = new PairString("Item4","Item5");
		PairString s4 = new PairString("Item6","Item7");
		PairString s5 = new PairString("Item7","Item8");
			itemAssociation.add(s1); itemAssociation.add(s4); itemAssociation.add(s5); itemAssociation.add(s2); itemAssociation.add(s3);
		LargestItemAssociation assoc = new LargestItemAssociation();
		List<String> largestResult = assoc.largestItemAssociation(itemAssociation);
		for(String n : largestResult) {
			System.out.println(n);
		}
	}

	public List<String> largestItemAssociation(List<PairString> itemAssociation) {
		// create UF, using the String as key
		UF uf = new UF(itemAssociation);
		// Using TreeSet to sort the strings based on lexographic order. If using list, we need to manually call Collections.Sort to sort
		// But Tree set does it by defaut during insertion
		Map<String,TreeSet<String>> cache = new HashMap<String, TreeSet<String>>();
		for(String name : uf.names) {
			Node node = uf.find(name);
			if(!cache.containsKey(node.name)) {
				TreeSet<String> list = new TreeSet<>();
				list.add(name);
				cache.put(node.name, list);
			}else {
				cache.get(node.name).add(name);
			}
		}
		// to find the max count and lexo order at same time, implement the compare method
		PriorityQueue<Counter> maxHeap = new PriorityQueue<>();
		for(Map.Entry<String, TreeSet<String>> entry : cache.entrySet()) {
			maxHeap.offer(new Counter(entry.getValue().size(),entry.getKey(),entry.getValue().first()));
		}		

		Counter topCounter = maxHeap.poll();
		return new ArrayList<String>(cache.get(topCounter.name));

	}	

	static class Counter implements Comparable<Counter>{
		String name;
		int count;
		String firstName;
		public Counter(int count, String name) {
			this.count = count;
			this.name = name;
		}
		public Counter(int count, String name, String firstName) {
			this.count = count;
			this.name = name;
			this.firstName = firstName;
		}
		// if count is diff, we just sort using count
		// if count is same, we consider the string lexo order
		public int compareTo(Counter c) {
			int nameCompare =  Integer.compare(c.count,this.count);
			if(nameCompare!=0)
				return nameCompare;
			if(firstName==null || c.firstName==null)
				return nameCompare;
			return this.firstName.compareTo(c.firstName);
		}
	}

	static class PairString {
		String first;
		String second;

		public PairString(String first, String second) {
			this.first = first;
			this.second = second;
		}
	}

	static class UF {
		Map<String,Node> map = new HashMap<>();
		Set<String> names = new HashSet<>();

		public UF(List<PairString> itemAssociation) {
			
			// Key is string for map
			
			for(PairString keyPair : itemAssociation) {

				if(!names.contains(keyPair.first)) {
					Node node = new Node(keyPair.first);
					node.parent = node;
					map.put(keyPair.first,node);
					names.add(keyPair.first);

				}
				if(!names.contains(keyPair.second)) {
					Node node = new Node(keyPair.second);
					node.parent = node;
					map.put(keyPair.second,node);
					names.add(keyPair.second);

				}
			}

			for(PairString keyPair : itemAssociation) {
				union(keyPair.first, keyPair.second);
			}

		}

		public Node find(String p) {
			Node node = map.get(p);
			if(node.name.equals(node.parent.name)) {
				return node;
			}
			return find(node.parent.name);
		}

		public boolean union(String p, String q) {
			Node pParent = find(p);
			Node qParent = find(q);
			if(pParent.name.equals(qParent.name)) {
				return false;
			}			
			if(pParent.rank==qParent.rank) {
				pParent.rank++;
				qParent.parent = pParent;
			}else if(pParent.rank>qParent.rank) {
				qParent.parent = pParent;
			}else if(qParent.rank>pParent.rank) {
				pParent.parent = qParent;
			}
			
			return true;
		}
	}

	static class Node{

		int rank;
		String name;
		Node parent;

		public Node(String name) {			
			this.name = name;
		}
	}
	




