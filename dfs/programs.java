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
