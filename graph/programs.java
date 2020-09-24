**************************************************************************************************************************************************************************
                                                                            1. Depth first Search
**************************************************************************************************************************************************************************
// add the element 
// dfs to its children
class Program {
  static class Node {
    String name;
    List<Node> children = new ArrayList<Node>();
    public Node(String name) {
      this.name = name;
    }
    public List<String> depthFirstSearch(List<String> array) {
			array.add(name);
			for(int i=0; i<children.size();i++){
				children.get(i).depthFirstSearch(array);
			}
      return array;
    }

    public Node addChild(String name) {
      Node child = new Node(name);
      children.add(child);
      return this;
    }
  }
}

**************************************************************************************************************************************************************************
                                                                            2. Breadth first Search
**************************************************************************************************************************************************************************

class Program {
  static class Node {
    String name;
    List<Node> children = new ArrayList<Node>();
    public Node(String name) {
      this.name = name;
    }

    public List<String> breadthFirstSearch(List<String> array) {
			Queue<Node> que = new LinkedList<>();
			que.offer(this);
			while(!que.isEmpty()){
				Node node = que.poll();
				array.add(node.name);
				for(Node item : node.children){
					que.offer(item);
				}
			}
      return array;
    }

    public Node addChild(String name) {
      Node child = new Node(name);
      children.add(child);
      return this;
    }
  }
  
**************************************************************************************************************************************************************************
                                                                            3. Number of Islands 
**************************************************************************************************************************************************************************
/* Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water
and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example 1:

Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
Example 2:

Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
*/

class Solution {
    
    public int numIslands(char[][] grid) {
        if(grid==null || grid.length==0)
            return 0;
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        List<Integer> islands = new ArrayList<>();
        for(int i=0; i<grid.length;i++){
            for(int j=0; j<grid[0].length;j++){                
                if(!visited[i][j]){
		    if(grid[i][j]=='1'){
		    	// bfs to collect all neighbours
                      helper(i, j,grid,visited, islands);
		    }
                }
            }
        }
        return islands.size();
    }
    
    private void helper(int i, int j, char[][] board, 
                                       boolean[][] visited, List<Integer> islands){
        
        int count = 0;
        Queue<GraphNode> que = new LinkedList<>();
        que.offer(new Members(i,j));
        while(!que.isEmpty()){                        
            Members mem = que.poll();
	    // if already visited, we just continue
            if(visited[mem.i][mem.j])
                continue;
            visited[mem.i][mem.j] = true;            
            count++;
            List<Members> neighbours = getNeighbours(mem.i, mem.j,board,visited);
            for(Members member : neighbours){
                que.offer(new Members(member.i,member.j));
            }
        }
	// double check to use count greater than 0
        if(count>0){
            islands.add(count);
        }            
    }
    
    private List<Members> getNeighbours(int i, int j, char[][] board, 
                                       boolean[][] visited){
        List<Members> list = new  ArrayList<>();
	//int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};
        int[] first = {0, -1, 0 , 1};
        int[] second = {-1 , 0, 1, 0};
        for(int n=0; n<first.length; n++){            
            int one = first[n] + i;
            int two = second[n] + j;
            if(one >= 0 && two >= 0 && one <= board.length-1 && two <= board[0].length-1 &&
              !visited[one][two] && board[one][two]!='0'){
             list.add(new Members(one,two));   
            }
        }
        return list;
    }
    static class Members{
        int i;
        int j;
        public Members(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}

**************************************************************************************************************************************************************************
                                                                            3. Number of Islands 2 
**************************************************************************************************************************************************************************
You have a map that marks the location of a treasure island. Some of the map area has jagged rocks and dangerous reefs. Other areas are safe to sail in. 
There are other explorers trying to find the treasure. So you must figure out a shortest route to the treasure island.

Assume the map area is a two dimensional grid, represented by a matrix of characters. You must start from the top-left corner of the map and can move one block up,
down, left or right at a time. The treasure island is marked as X in a block of the matrix. X will not be at the top-left corner. 
Any block with dangerous rocks or reefs will be marked as D. You must not enter dangerous blocks. You cannot leave the map area. Other areas O are safe to sail in. 
The top-left corner is always safe. Output the minimum number of steps to get to the treasure.

Example:

Input:
[['O', 'O', 'O', 'O'],
 ['D', 'O', 'D', 'O'],
 ['O', 'O', 'O', 'O'],
 ['X', 'D', 'D', 'O']]

Output: 5
Explanation: Route is (0, 0), (0, 1), (1, 1), (2, 1), (2, 0), (3, 0) The minimum route takes 5 steps.


**************************************************************************************************************************************************************************
                                                                            3. Find Bridges
**************************************************************************************************************************************************************************
Given an underected connected graph with n nodes labeled 1..n. A bridge (cut edge) is defined as an edge which, when removed, 
makes the graph disconnected (or more precisely, increases the number of connected components in the graph). 
Equivalently, an edge is a bridge if and only if it is not contained in any cycle. 
The task is to find all bridges in the given graph.
Output an empty list if there are no bridges.

Input:

n, an int representing the total number of nodes.
edges, a list of pairs of integers representing the nodes connected by an edge.
Example 1:

Input: n = 5, edges = [[1, 2], [1, 3], [3, 4], [1, 4], [4, 5]]
   
   3 ---4---5
     \ /
      1--- 2
Output: [[1, 2], [4, 5]]
Explanation:
There are 2 bridges:
1. Between node 1 and 2
2. Between node 4 and 5
If we remove these edges, then the graph will be disconnected.
If we remove any of the remaining edges, the graph will remain connected.

Note:
when you do this for amazon OA, 2 test cases will fail. For these to pass, 
you r result should contain edges such that for an edge (a, b) a < b.
i.e. if you have a critical edge (2,1), change it to (1, 2) when adding to resultant list

https://leetcode.com/discuss/interview-question/372581
https://leetcode.com/problems/critical-connections-in-a-network/

Brute force approach:
We can form a grap with data. 
Remove an edge, perform DFS/BFS and try to see if we are able to reach all other nodes, if so, then its not a bridge
if while reving a edge, the connections increase, then its a bridge.
Do this for every edge in the gaph and we can identify all bridges.
This is a very costly operation, as we are perforing the BFS for every edge.

https://www.youtube.com/watch?v=aZXi1unBdJA


Bridge Algo:
  Start at any node and do DFS traversal labelling nodes with increasing id values. Keep track if the above mentioned id and smallest low-link value for each node.
  During DFS bridges will be found when, id of the node where edge is coming is less than the low-link value of the node where edge is going to.
  Note: low-link value of a node is smallest id reachable from that node when doing a DFS(including node itself).
  The id can be considered like a rank for each node.
  
Time Complexity : O(V+E)

	int height = 0;
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		// construct graph using input data
		Graph graph = new Graph(n,connections);		
		List<List<Integer>> result = new ArrayList<>();
		for(int i=0; i<n;i++){
		// this is useful, if the given graph has more than one connected group
		// in our case, this dfs will be called only for 0th element as others will be visited in first call itself and we have only 1 connected element
			if(!graph.map.get(i).isVisited){				
				dfs(i,-1,result,graph);
			}
		}
		return result;
	}

	private void dfs(int i, int parent, List<List<Integer>> result, Graph graph){
		// get each node and make it as visited, update low and id values
		GraphNode currentNode = graph.map.get(i); 
		height = height+1;
		currentNode.travelId = height;
		currentNode.lowVal = height;
		currentNode.isVisited = true;
		for(GraphNode neighbour : currentNode.neighbours){			
			// if parent, do not traverse as we just came from there
			if(neighbour.id == parent){
				continue;
			}
			if(!neighbour.isVisited){
				// if node is not visited, call dfs
				dfs(neighbour.id, currentNode.id, result,graph);
				// after dfs, we know the neighbours lowVal, now backtrack and update current node if neighbour has lesser values(meaning its able to 
				// reach ancestors via diff route) 
				currentNode.lowVal = Math.min(currentNode.lowVal,neighbour.lowVal);
				if(currentNode.travelId < neighbour.lowVal){
					List<Integer> values = new ArrayList<>();
					values.add(Math.min(currentNode.id,neighbour.id));
                    			values.add(Math.max(currentNode.id,neighbour.id));
					result.add(values);   
				}
			}else{
				// if node is already visited, just check if it has a lesser id value, and if so update it with lowVal
				currentNode.lowVal = Math.min(currentNode.lowVal,neighbour.travelId);
			}
		}
	}

	static class Graph{
		List<GraphNode> nodes;
		Map<Integer,GraphNode> map;
		public Graph(int n, List<List<Integer>> connections){
			nodes = new ArrayList<>();
			map = new HashMap<>();
			constructGraph(n,connections);
		}

		public void constructGraph(int n, List<List<Integer>> connections){
			for(int i=0; i<n;i++){
				GraphNode node = new GraphNode(i);
				nodes.add(node);
				map.put(i,node);
			}
			for(List<Integer> values : connections){
				int left = values.get(0);
				int right = values.get(1);
				// make sure to get the value from map and re add it
				map.get(left).neighbours.add(map.get(right));
				map.get(right).neighbours.add(map.get(left));				
			}
		}
	}

	static class GraphNode{
		int id;
		int travelId;
		int lowVal;
		boolean isVisited = false;
		List<GraphNode> neighbours = new ArrayList<>();
		
		public GraphNode(int id){
			this.id= id;
		}
	}
  
**************************************************************************************************************************************************************************
                                                                            3. Find Articulation points
**************************************************************************************************************************************************************************
  
You are given an undirected connected graph. An articulation point (or cut vertex) is defined as a vertex which, when removed along with associated edges,
makes the graph disconnected (or more precisely, increases the number of connected components in the graph). 
The task is to find all articulation points in the given graph.

Input:
The input to the function/method consists of three arguments:

numNodes, an integer representing the number of nodes in the graph.
numEdges, an integer representing the number of edges in the graph.
edges, the list of pair of integers - A, B representing an edge between the nodes A and B.
Output:
Return a list of integers representing the critical nodes.

Example:
	       4
	      /
	     3
	    / \
	   1   2
	   \  / \
	     0   5
	        / 
               6 
Input: numNodes = 7, numEdges = 7, edges = [[0, 1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3, 4]]
Output: [2, 3, 5]

https://www.youtube.com/watch?v=aZXi1unBdJA

Articulation Algorithm:
1) With bridge : On a connected component with 3 or more vertices, if there is a edge(u,v) which is a bridge, either u or v is an articulation point.
2) Without bridge: cycle indicates that there is an articulation point. in previous algo, if (id(e.from) == lowlink(e.to)) - this condition is for cycle.
     - the above condition of cycle fails only for STARTING NODE, if it has just 0 or 1 outgoing edge. 
       so if starting node has more than 1 outgoing edge, it is also articulation point.


	int outgoingEdge = 0;
	int height;
	public List<Integer> criticalNodes(List<List<Integer>> edges, int numLinks, int numRouters) {
		List<Integer> result = new ArrayList<>();
		Graph graph = new Graph(numRouters, edges);
		for(int i=0; i<numRouters; i++) {
			GraphNode node = graph.map.get(i);
			if(!node.isVisited) {
				// initialize outgoinedge for every starting node of each connection. here it will be invoked just once for our use case
				outgoingEdge = 0;
				dfs(i,-1,graph,result);
				// ideally, if outgoingEdge was greater then one, we must add to result, if outgoingEdge was less than 1 it must not be added (removed)
				// (remove -  even if it was wrongly added as part of cycle)
				if(outgoingEdge>1) {
					result.add(node.id);
				}else {
					// call remove method using object to invoked removal of object, else it considers the input as index(primitive int)
					result.remove((Integer)node.id);
				}
			}
		}
		
		return result;	
	}
	
	private void dfs(int id, int parent, Graph graph, List<Integer> result) {
		if(id==parent) {
			outgoingEdge++;
		}
		height = height + 1;
		GraphNode currentNode = graph.map.get(id);		
		currentNode.isVisited = true;
		currentNode.travelId = height;
		currentNode.lowId= height;
		
		for(GraphNode neigh : currentNode.neighbours) {
			if(neigh.id == parent) {
				continue;
			}
			if(!neigh.isVisited) {
				dfs(neigh.id, currentNode.id, graph, result);
				currentNode.lowId = Math.min(currentNode.lowId, neigh.lowId);
				// condition of cycle
				if(currentNode.travelId == neigh.lowId) {
					result.add(currentNode.id);
				}
				// normal condition where id lesser than lowid   
				// above and this could have been combined as <=, just splitted for more understanding
				if(currentNode.travelId < neigh.lowId) {
					result.add(currentNode.id);
				}
			}else {
				currentNode.lowId = Math.min(currentNode.lowId, neigh.travelId);
			}
		}
	}
	
	
	static class Graph{
		List<GraphNode> nodes;
		Map<Integer,GraphNode> map; 
		public Graph(int numRoutes, List<List<Integer>>  edges) {
			nodes = new ArrayList<>();
			map = new HashMap<>();
			constructGraph(numRoutes, edges);
		}
		
		public void constructGraph(int numRoutes, List<List<Integer>> edges) {
			for(int i=0; i<numRoutes; i++) {
				GraphNode node = new GraphNode(i);
				nodes.add(node);
				map.put(i,node);
			}
			for(List<Integer> edge : edges) {
				int left = edge.get(0);
				int right = edge.get(1);
				map.get(left).neighbours.add(map.get(right));
				map.get(right).neighbours.add(map.get(left));
			}
		}
	}
	
	static class GraphNode {
		int id;
		int travelId;
		int lowId;
		boolean isVisited;
		List<GraphNode> neighbours = new ArrayList<CriticalResourcesLinkedList.GraphNode>();
		public GraphNode(int id) {
			this.id = id;
		}
	}




