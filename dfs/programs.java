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
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************




**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************




**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************




**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************




**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************




**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************



**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************


**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************



**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************



**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************



**************************************************************************************************************************************************************************
                                                                            3.  Symmetric Tree
**************************************************************************************************************************************************************************
