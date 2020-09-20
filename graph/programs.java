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
