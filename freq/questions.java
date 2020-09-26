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



