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


