**************************************************************************************************************************************************************************
                                                                            1.  Valid Binary Tree
**************************************************************************************************************************************************************************
//Given a binary tree, determine if it is a valid binary search tree (BST).
//
//Assume a BST is defined as follows:
//
//The left subtree of a node contains only nodes with keys less than the node's key.
//The right subtree of a node contains only nodes with keys greater than the node's key.
//Both the left and right subtrees must also be binary search trees.
// 
//
//Example 1:
//
//    2
//   / \
//  1   3
//
//Input: [2,1,3]
//Output: true
//Example 2:
//
//    5
//   / \
//  1   4
//     / \
//    3   6
//
//Input: [5,1,4,null,null,3,6]
//Output: false
//Explanation: The root node's value is 5 but its right child's value is 4.

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
        
    public boolean isValidBST(TreeNode root) {
        if(root==null)
            return true;
        
        return isValid(root,null, null);
        //return isValidUsingInteger(root,Integer.MIN_VALUE, Integer.MAX_VALUE);
        // return isValid_iterative(root);
    }
    
    private boolean isValidUsingInteger(TreeNode node, int min, int max){
        if(node==null)
            return true;       
        if(node.val<=min || node.val>=max)
            return false;
        boolean leftSide = isValid(node.left,min,node.val);
        boolean rightSide = isValid(node.right,node.val,max);
        return leftSide && rightSide;
    }
    
    private boolean isValid(TreeNode node, Integer min, Integer max){
        if(node==null)
            return true;       
        if(min!=null && node.val<=min)
            return false;
        if(max!=null && node.val>=max)
            return false;
        boolean leftSide = isValid(node.left,min,node.val);
        boolean rightSide = isValid(node.right,node.val,max);
        return leftSide && rightSide;
    }
    
    private boolean isValid_iterative(TreeNode root){
        Deque<TreeNode> stack = new ArrayDeque<>();       
      
        TreeNode previousNode = null;
        while(!stack.isEmpty() || root!=null){            
            while(root!=null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(previousNode!=null){
                if(previousNode.val >= root.val)
                    return false;
            }            
            previousNode = root;            
            root = root.right;
        }
        return true;
    }
}

**************************************************************************************************************************************************************************
                                                                            2.  Same Tree
**************************************************************************************************************************************************************************
//Given two binary trees, write a function to check if they are the same or not.
//
//Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
//
//Example 1:
//
//Input:     1         1
//          / \       / \
//         2   3     2   3
//
//        [1,2,3],   [1,2,3]
//
//Output: true
//Example 2:
//
//Input:     1         1
//          /           \
//         2             2
//
//        [1,2],     [1,null,2]
//
//Output: false
//Example 3:
//
//Input:     1         1
//          / \       / \
//         2   1     1   2
//
//        [1,2,1],   [1,1,2]
//
//Output: false

private boolean isSameTreeHelper(TreeNode p, TreeNode q) {
        if(p==null && q==null)
            return true;
        if(p==null || q==null)
            return false;
        if(p.val!=q.val)
            return false;
        
        boolean left = isSameTreeHelper(p.left,q.left);
        boolean right = isSameTreeHelper(p.right,q.right);
        return left && right;
    }
    
 private boolean isSameTreeIterative(TreeNode p, TreeNode q){
        Deque<TreeNode> first = new ArrayDeque<>();
        Deque<TreeNode> second = new ArrayDeque<>();
        while(!first.isEmpty() || !second.isEmpty() || p!=null || q!=null){
            
            while(p!=null || q!=null){
                if(p!=null){
                    first.push(p);
                    p=p.left;
                }
                if(q!=null){
                    second.push(q);                
                    q = q.left;    
                }
            }
            if(first.size()!=second.size())
                return false;
            if(!first.isEmpty())
                p = first.pop();
            if(!second.isEmpty())
                q = second.pop();
            if(p==null && q==null)
                break;
            if(p==null || q==null)
                return false;
            if(p.val!=q.val)
                return false;
            p= p.right;
            q= q.right;
        }
        return true;
    }
**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************
    
/*Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3
 
But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3
 
Note:
Bonus points if you could solve it both recursively and iteratively.*/

private boolean isSymmetricHelper(TreeNode p, TreeNode q) {
        if(p==null && q==null)
            return true;
        if(p==null || q==null)
            return false;
        if(p.val!=q.val)
            return false;
        
        boolean left = isSymmetricHelper(p.left,q.right);
        boolean right = isSymmetricHelper(p.right,q.left);
        return left && right;
    }

private boolean isSymmetricIterative(TreeNode p, TreeNode q){
        Deque<TreeNode> first = new ArrayDeque<>();
        Deque<TreeNode> second = new ArrayDeque<>();
        while(!first.isEmpty() || !second.isEmpty() || p!=null || q!=null){
            
            while(p!=null || q!=null){
                if(p!=null){
                    first.push(p);
                    p=p.left;
                }
                if(q!=null){
                    second.push(q);                
                    q = q.right;    
                }
            }
            if(first.size()!=second.size())
                return false;
            if(!first.isEmpty())
                p = first.pop();
            if(!second.isEmpty())
                q = second.pop();
            if(p==null && q==null)
                break;
            if(p==null || q==null)
                return false;
            if(p.val!=q.val)
                return false;
            p= p.right;
            q= q.left;
        }
        return true;
    }

**************************************************************************************************************************************************************************
                                                                            4.  MAXIMUM DEPTH OF BINARY TREE
**************************************************************************************************************************************************************************
/*Given a binary tree, find its maximum depth.
The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
Note: A leaf is a node with no children.
Example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its depth = 3.*/

public int maxDepth(TreeNode root) {
        return height(root);
    }
    
    private int height(TreeNode root){
        if(root==null)
            return 0;
        int left = height(root.left);
        int right = height(root.right);
        int height = Math.max(left,right)+1;
        return height;
    }
 public int maxDepth(TreeNode root) {
        return iterativeHeight(root);
    }
    
 private int iterativeHeight(TreeNode root){
        if(root==null)
            return 0;
        int count = 0;
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int size = que.size();
            for(int i=0; i<size;i++){
                TreeNode node = que.poll();
                if(node.left!=null)
                    que.offer(node.left);
                if(node.right!=null)
                    que.offer(node.right);                
            }
            count++;
        }
        return count;
    }
**************************************************************************************************************************************************************************
                                                                            5.  construct-binary-tree-from-preorder-and-inorder-traversal
**************************************************************************************************************************************************************************
/*Given preorder and inorder traversal of a tree, construct the binary tree.
Note:
You may assume that duplicates do not exist in the tree.
For example, given
preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]
Return the following binary tree:
    3
   / \
  9  20
    /  \
   15   7*/
/**
 * Definition for a binary tree node.
 * function TreeNode(val) {
 *     this.val = val;
 *     this.left = this.right = null;
 * }
 */
 
//The basic idea is here:
//Say we have 2 arrays, PRE and IN.
//Preorder traversing implies that PRE[0] is the root node.
//Then we can find this PRE[0] in IN, say it's IN[5].
//Now we know that IN[5] is root, so we know that IN[0] - IN[4] is on the left side, IN[6] to the end is on the right side.
//Recursively doing this on subarrays, we can build a tree out of it

  Map<Integer,Integer> cache = new HashMap<>();
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder==null || inorder==null || inorder.length==0 ||preorder.length==0)
            return null;
        for(int i=0; i<inorder.length;i++){
            cache.put(inorder[i],i);
        }
        return buildTreeHelper(0,0,inorder.length-1,preorder,inorder);
    }
    
    private TreeNode buildTreeHelper(int preStart, int inStart, int inEnd,
                                     int[] preorder, int[] inorder){
        
        if(preStart>preorder.length || inStart > inEnd)
            return null;
       
        TreeNode root = new TreeNode(preorder[preStart]);
        int indexInInorder = cache.get(root.val);
        int leftRemaining = indexInInorder - inStart;
        root.left = buildTreeHelper(preStart+1,inStart,indexInInorder-1,preorder,inorder);
        root.right = buildTreeHelper(preStart+leftRemaining+1,indexInInorder+1,inEnd, preorder,inorder);
        return root;
    }
**************************************************************************************************************************************************************************
                                                                            6.  construct-binary-tree-from-inorder-and-postorder-traversal
**************************************************************************************************************************************************************************
/*Given inorder and postorder traversal of a tree, construct the binary tree.
Note:
You may assume that duplicates do not exist in the tree.
For example, given
inorder = [9,3,15,20,7]
postorder = [9,15,7,20,3]
Return the following binary tree:
    3
   / \
  9  20
    /  \
   15   7*/

public TreeNode buildTree(int[] inorder, int[] postorder) {
        if(inorder==null || postorder==null || inorder.length==0 || postorder.length==0)
            return null;
        Map<Integer,Integer> cache = new HashMap<>();
        for(int i=0; i<inorder.length;i++){
            cache.put(inorder[i],i);
        }
        return buildTreeHelper(postorder.length-1,0,inorder.length-1,inorder,postorder,cache);
    }
    
    private TreeNode buildTreeHelper(int postStart, int inStart,
                int inEnd, int[] inorder, int[] postorder, Map<Integer,Integer> cache){
        
        if(postStart < 0 || inStart>inEnd)
            return null;
        TreeNode root = new TreeNode(postorder[postStart]);
        int indexInInorder = cache.get(root.val);
        int rightRemaining = inEnd - indexInInorder;
        root.left = buildTreeHelper(postStart-rightRemaining-1,
                            inStart, indexInInorder-1,inorder,postorder,cache);
        root.right = buildTreeHelper(postStart-1,indexInInorder+1,
                             inEnd,inorder,postorder,cache);
        return root;
    }

**************************************************************************************************************************************************************************
                                                                            7.  Convert Sorted Array to Binary Search Tree
**************************************************************************************************************************************************************************
/*Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
Example:
Given the sorted array: [-10,-3,0,5,9],
One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
      0
     / \
   -3   9
   /   /
 -10  5*/

int mid = low + (high-low)/2; // avoids integer overflow

 public TreeNode sortedArrayToBST(int[] nums) {
        if(nums==null || nums.length==0)
            return null;
        return sortedArrayToBSTHelper(nums, 0,nums.length-1);
    }
    
    private TreeNode sortedArrayToBSTHelper(int[] nums, int start, int end){
        if(start>end )
            return null;
        int mid = (start + end)/2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBSTHelper(nums, start, mid-1);
        root.right = sortedArrayToBSTHelper(nums, mid+1,end);
        return root;
    }

**************************************************************************************************************************************************************************
                                                                            8.  Convert Sorted List to Binary Search Tree
**************************************************************************************************************************************************************************
/*Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of every node never differ by more than 1.
Example:
Given the sorted linked list: [-10,-3,0,5,9],
One possible answer is: [0,-3,9,-10,null,5], which represents the following height balanced BST:
      0
     / \
   -3   9
   /   /
 -10  5*/
 
 /*
Since we are given a linked list and not an array, we don't really have access to the elements of the list using indexes.
We want to know the middle element of the linked list.
We can use the two pointer approach for finding out the middle element of a linked list. Essentially, we have two pointers called slow_ptr and fast_ptr.
The slow_ptr moves one node at a time whereas the fast_ptr moves two nodes at a time. By the time the fast_ptr reaches the end of the linked list, 
the slow_ptr would have reached the middle element of the linked list. For an even sized list, any of the two middle elements can act as the root of the BST.
Once we have the middle element of the linked list, we disconnect the portion of the list to the left of the middle element.
The way we do this is by keeping a prev_ptr as well which points to one node before the slow_ptr i.e. prev_ptr.next = slow_ptr. 
For disconnecting the left portion we simply do prev_ptr.next = None
We only need to pass the head of the linked list to the function that converts it to a height balances BST.
So, we recurse on the left half of the linked list by passing the original head of the list and on the right half by passing slow_ptr.next as the head.

Time Complexity: O(NlogN)
Space Complexity: O(logN)
*/
 public TreeNode sortedListToBST(ListNode head) {        
        
        return sortedListToBstHelper(head);
    }
    
    
    
    private TreeNode sortedListToBstHelper(ListNode head){
        if(head==null)
            return null;
        int i=1;
        ListNode fastPtr = head;
        ListNode slowPtr = head;
        ListNode prevPrt = null;        
        while(fastPtr!=null){
            fastPtr = fastPtr.next;
            
            if(i==0 || i%2 ==0){                
                prevPrt = slowPtr;
                slowPtr = slowPtr.next;                
            }
            i++;
        }
        // here we break the array by setting the next of pre to null      
        if(prevPrt!=null)
            prevPrt.next = null;
        // this will be the mid element of the list
        TreeNode root = new TreeNode(slowPtr.val);
        if(head==slowPtr)
            return root;
        // we get the right half by picking the right of slow
        ListNode rightPrt = slowPtr.next;
        
        root.left = sortedListToBstHelper(head);
        root.right = sortedListToBstHelper(rightPrt);
        return root;        
    }
/*
Approach 2: 
Convert the given linked list into an array. Let's call the beginning and the end of the array as left and right
the remaining can be solved using above problem. Here time complexity is reduced, but space complexity increases as we are using a new array.
Time Complexity: O(N)
Space Complexity: O(N)
*/
**************************************************************************************************************************************************************************
                                                                            9.  Balanced Binary Tree
**************************************************************************************************************************************************************************
/*Given a binary tree, determine if it is height-balanced.
For this problem, a height-balanced binary tree is defined as:
a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 
Example 1:
Given the following tree [3,9,20,null,null,15,7]:
    3
   / \
  9  20
    /  \
   15   7
Return true.
Example 2:
Given the following tree [1,2,2,3,3,null,null,4,4]:
       1
      / \
     2   2
    / \
   3   3
  / \
 4   4
Return false.*/

boolean isBalanced = true;
    public boolean isBalanced(TreeNode root) {
        isBalancedHelper(root);
        return isBalanced;
    }
    
    private int isBalancedHelper(TreeNode root){
        if(root==null)
            return 0;
        int leftMax = isBalancedHelper(root.left);
        int rightMax = isBalancedHelper(root.right);
        if(isBalanced){
            if(Math.abs(leftMax-rightMax) > 1)
                isBalanced = false;
        }
        return Math.max(leftMax,rightMax)+1;
    }


**************************************************************************************************************************************************************************
                                                                            10.  Minimum Depth of Binary Tree
**************************************************************************************************************************************************************************
/*Given a binary tree, find its minimum depth.
The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
Note: A leaf is a node with no children.
Example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its minimum depth = 2.*/

public int minDepth(TreeNode root) {
        if(root==null)
            return 0;
        //return height(root);
        return heightBFS(root);
    }
    
    private static int heightBFS(TreeNode root){
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        int length = 0;
        while(!que.isEmpty()){
            int size = que.size();            
            for(int i=0; i<size; i++){
                TreeNode node = que.poll();
                if(node.left==null && node.right==null){
                    que.clear();
                    break;
                }
                if(node.left!=null)
                    que.offer(node.left);
                if(node.right!=null)
                    que.offer(node.right);
            }
            length++;
        }
        return length;
    }
    
    private static int height(TreeNode root){
        if(root==null)
            return 0;
        int left = height(root.left);
        int right = height(root.right);
        if((left!=0 && right!=0) || (left==0 && right==0))
            return Math.min(left,right)+1;
        else if(left!=0)
            return left+1;
        else 
            return right+1;
    }


**************************************************************************************************************************************************************************
                                                                           11.  Path Sum
**************************************************************************************************************************************************************************
/*Given a binary tree and a sum, determine if the tree has a root-to-leaf path such that adding up all the values along the path equals the given sum.
Note: A leaf is a node with no children.
Example:
Given the below binary tree and sum = 22,
      5
     / \
    4   8
   /   / \
  11  13  4
 /  \      \
7    2      1
return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.*/

// The basic idea is to subtract the value of current node from sum until it reaches a leaf node and the subtraction equals 0, then we know that we got a hit.
//Otherwise the subtraction at the end could not be 0.

private boolean hasPathHelper(TreeNode root, int sum){        
        if(root==null)
            return false;
            // we calculate to see if the leaf matches the remaining
        if(root.left == null && root.right==null && root.val-sum ==0)
            return true;
        boolean left = hasPathHelper(root.left, sum-root.val);
        boolean right = hasPathHelper(root.right, sum-root.val);
        return left || right;
    } 
        
// why two stack ? path stack keep record of visited nodes in a DFS way, sub stack keep record of sum till current node. 
//Only check ==sum when current node doesn't have any child, otherwise keep adding its child node. 
//The visited nodes are no longer in the stack, so it won't be double checked for the same node
    private boolean hasPathSumHelpIterative(TreeNode root, int sum){
        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> values = new ArrayDeque<>();
        stack.push(root);
        values.push(sum);
        while(!stack.isEmpty()){
            // modified from inorder stack traversal
            int val = values.pop();
            TreeNode currentNode = stack.pop();
            if(currentNode.left==null && currentNode.right==null && val==currentNode.val)
                return true;
            if(currentNode.left!=null){
                stack.push(currentNode.left);
                values.push(val-currentNode.val);
            }
            if(currentNode.right!=null){
                stack.push(currentNode.right);
                values.push(val-currentNode.val);
            }
        }
        return false;
    }

**************************************************************************************************************************************************************************
                                                                            12.  Path Sum II
**************************************************************************************************************************************************************************
/*Given a binary tree and a sum, find all root-to-leaf paths where each path's sum equals the given sum.
Note: A leaf is a node with no children.
Example:
Given the below binary tree and sum = 22,
      5
     / \
    4   8
   /   / \
  11  13  4
 /  \    / \
7    2  5   1
Return:
[
   [5,4,11,2],
   [5,8,4,5]
]*/

public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> output = new ArrayList<>();
        if(root==null)
            return output;
        pathSumHelper(root,sum,new ArrayList<>(),output);
        return output;
    }
    
    private void pathSumHelper(TreeNode root, int sum,
                List<Integer> currentList, List<List<Integer>> output ){
        if(root==null)
            return ;
        
        List<Integer> tempList = new ArrayList<>(currentList);
        tempList.add(root.val);
        if(root.left==null && root.right==null && root.val-sum==0){
            output.add(tempList);          
        }
        pathSumHelper(root.left,sum-root.val,tempList,output);
        pathSumHelper(root.right,sum-root.val,tempList,output);
    }

**************************************************************************************************************************************************************************
                                                                            13.  Flatten Binary Tree to Linked List
**************************************************************************************************************************************************************************
/*Given a binary tree, flatten it to a linked list in-place.
For example, given the following tree:
    1
   / \
  2   5
 / \   \
3   4   6
The flattened tree should look like:
1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6*/
    
// if lest is not null, 
// take right out
// move left to right
// move right to last of left

  private void flattenHelper(TreeNode root){
        if(root==null)
            return;
        if(root.left!=null){
            TreeNode right = root.right;
            root.right = root.left;
            root.left = null;
            TreeNode temp = root;
            while(temp.right!=null){
                temp = temp.right;
            }
            temp.right = right;
        }
        root = root.right;
        flattenHelper(root);
    }

**************************************************************************************************************************************************************************
                                                                            14.  Populating Next Right Pointers in Each Node
**************************************************************************************************************************************************************************
/*You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.
 
Follow up:
You may only use constant extra space.
Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 
Example 1:
Input: root = [1,2,3,4,5,6,7]
Output: [1,#,2,3,#,4,5,6,7,#]
Explanation: Given the above perfect binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B. The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 
Constraints:
The number of nodes in the given tree is less than 4096.
-1000 <= node.val <= 1000  */

 // BFS should easily do this
public Node connect(Node root) {
        if(root==null)
            return null;
        Queue<Node> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int size = que.size();            
            for(int i=0; i< size; i++){
                Node node = que.poll();               
                if(i== size-1){
                    node.next = null;
                }else{
                    node.next = que.peek();
                }
                if(node.left!=null)
                    que.offer(node.left);
                if(node.right!=null)
                    que.offer(node.right);
            }
        }
        return root;
    }

**************************************************************************************************************************************************************************
                                                                            15.  Populating Next Right Pointers in Each Node - II
**************************************************************************************************************************************************************************
/*Given a binary tree
struct Node {
  int val;
  Node *left;
  Node *right;
  Node *next;
}
Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
Initially, all next pointers are set to NULL.
 
Follow up:
You may only use constant extra space.
Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
 
Example 1:
Input: root = [1,2,3,4,5,null,7]
Output: [1,#,2,3,#,4,5,7,#]
Explanation: Given the above binary tree (Figure A), your function should populate each next pointer to point to its next right node, just like in Figure B.
The serialized output is in level order as connected by the next pointers, with '#' signifying the end of each level.
 
Constraints:
The number of nodes in the given tree is less than 6000.
-100 <= node.val <= 100*/       
public Node connect(Node root) {
        if(root==null)
            return null;
        Queue<Node> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int size = que.size();            
            for(int i=0; i< size; i++){
                Node node = que.poll();               
                if(i== size-1){
                    node.next = null;
                }else{
                    node.next = que.peek();
                }
                if(node.left!=null)
                    que.offer(node.left);
                if(node.right!=null)
                    que.offer(node.right);
            }
        }
        return root; 
    }

**************************************************************************************************************************************************************************
                                                                            16.  Sum Root to Leaf Numbers
**************************************************************************************************************************************************************************
/*Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
An example is the root-to-leaf path 1->2->3 which represents the number 123.
Find the total sum of all root-to-leaf numbers.
Note: A leaf is a node with no children.
Example:
Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.
Example 2:
Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.*/

public int sumNumbers(TreeNode root) {
        if(root==null)
            return 0;
        List<String> output = new ArrayList<>();
        sumNumbersHelper(root, new StringBuilder(), output);
        int sum = 0;
        for(int i=0; i<output.size(); i++){
            if(output.get(i).length()>0){
            Integer val = Integer.valueOf(output.get(i));
            sum += val;
            }
        }
        return sum;
    }
    
    private void sumNumbersHelper(TreeNode root,
                        StringBuilder builder, List<String> output){
        if(root==null)
            return ;
        StringBuilder tempBuilder = new StringBuilder(builder);
        tempBuilder.append(root.val);
        if(root.left==null && root.right==null){            
            output.add(tempBuilder.toString());           
        }
        sumNumbersHelper(root.left,tempBuilder,output);
        sumNumbersHelper(root.right,tempBuilder,output);
        
    }

Binary Tree Right Side View
/*Given a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.

Example:

Input: [1,2,3,null,5,null,4]
Output: [1, 3, 4]
Explanation:

   1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
*/

public List<Integer> rightSideView(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        if(root==null)
            return output;
        
        Queue<TreeNode> que = new LinkedList<>();
        que.offer(root);
        while(!que.isEmpty()){
            int size = que.size();
            for(int i=0; i<size; i++){
                if(i==size-1)
                    output.add(que.peek().val);
                TreeNode node = que.poll();
                if(node.left!=null)
                    que.offer(node.left);
                if(node.right!=null)
                    que.offer(node.right);
            }
        }
        return output;
    }
