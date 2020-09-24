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


https://leetcode.com/discuss/interview-question/372581
https://leetcode.com/problems/critical-connections-in-a-network/

https://www.youtube.com/watch?v=aZXi1unBdJA

Bridge Algo:
  Start at any node and do DFS traversal labelling nodes with increasing id values. Keep track if the above mentioned id and smallest low-link value for each node.
  During DFS bridges will be found when, id of the node where edge is coming is less than the low-link value of the node where edge is going to.
  Note: low-link value of a node is smallest id reachable from that node when doing a DFS(including node itself).
  

  
  



