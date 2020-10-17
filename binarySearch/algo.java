Binary search:
given an sorted array of int as input, and target integer, find if this is there in array and its position.
given the input is sorted, if we start by finding the middle element, then we can compare if our target is lesser or greater.
Now based on this we can eliminate one half of the search. Now we pick the remaining of the array and update start and end continue search.
we find mid by - (start+edn)/2  -  if the mid value is equal to our int, we return its end and end the logic.
if value is not mid and value is lesser than mid, we make our end ptr one step before the middle ptr.
if value had been grt than middle, then we would have moved start ptr, one step next to mid.
now we have new start/end, then we calculate mid again for this and continue untill we find the number or untill start<=end.

if start > end, then it means we have traversed entire array and we have not found the match
time : O(log N)
space : O(1) - iterative and O(logN) - for recursive due to memory on call stack.
so implement iterative


class Solution {
    public int search(int[] nums, int target) {
        if(nums==null || nums.length==0)
            return -1;
        int start = 0;
        int end = nums.length-1;
        while(start <= end){
            int mid = (start+end)/2;            
            if(nums[mid]==target)
                return mid;
            else{
                if(target < nums[mid]){
                    end = mid-1;
                }else{
                    start = mid+1;
                }
            }
        }
        return -1;        
    }
}

======================================================================================================================================================================

Find 3 largest numbers:
List is not sorted. given a list of integers, without sorting it, find the max 3 largest numbers.
it needs to handle duplicate use case as well. if input is  - [3,4,2,1,3]  - op = [3,3,4]
 - can we just sort array and return last 3 elements ? 
  - the best sorting algo runs in O(n logn) time . we can do better than this.
  
We need to store and keep track of the 3 largest numbers.
we declare an empty list of size 3. 
we start iterating main array, we check if we have any number for the last element in op array. if we dont have anything, we just update it. we pick the first element in main array and update it in last of op array.
Then we move to second element and we compare it to the largest no we have. 3rd element in op array. if its lesser than it, we compare it to second largest number and so on. 
if the num if grt than any element in the op array, we insert it to that position and shift array to left. meaning we remove smallest element. Kind of like max heap operation with fixed k (offer and poll if size is grt then k).
we continue till end of mail array and we have the sorted max 3 elements.

time - O(n) - we traverse entire array
space - O(1) - no new ds for input.

	private static void updateArray(int[] output, int index, int value){
		for(int i=0; i<=index; i++){
			if(i==index){
				output[i] = value;
			}else{
				output[i] = output[i+1];
			}
		}
	}
	
  public static int[] findThreeLargestNumbers(int[] array) {
		int[] output = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		for(int value : array){
			if(output[2] <= value){
				updateArray(output, 2, value);
			} else if (output[1] <= value){
				updateArray(output, 1, value);
			} else if (output[0] <= value){				
				updateArray(output, 0, value);			
			}
		}
    return output;
  }
}

======================================================================================================================================================================

Search in sorted matrix
we pick the top right most element, if its grt thn our value, then we can eliminate that column.
we come one step left and pick value before it, we check if with value,
  - if its less then value, we eliminae the whole row before it
  - if its grt then value, we eliminae the whole col below it
  - we keep moving left utill we find the value
time - O(n+m) - worst case can take time. but works better for other solns.
space - O(1)
	  
public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix==null || matrix.length==0)
            return false;
        int i = 0;
        int j = matrix[0].length-1;
        while(i<matrix.length && j >=0){            
            if(matrix[i][j]==target){
                return true;
            }
            if(matrix[i][j] > target){
                j -= 1;
            }else{
                i +=1;
            }
        }
        return false;
    }
	  
======================================================================================================================================================================
	
shifted binary search:
given sorted array is shiftet by some elements. we need to find the target int.
We can traverse every element and find ours, but it will be O(N).
we know its sorted, so we can use binary search. But its shifted, so we cannot directly start by taking first and last.

take first and last and find middle. same formula (start+end)/2
if middle ==  target , return
if L <= M : (means all nos to left of M are sorted order)
  if Target < M && Target >= L: (target lies in between L and M)
	explore left side of middle.
  else :
    explore right of M (move L to one position left of M - continue)
if L > M : (means this left part of M is not sorted - we have mix of front and back parts - but right part is sorted)	
   if Target > M && Target <=R   (we do exact opposite of above inner if condition)
     explore right of M.
   else
     explore left of M   
time O(log N)
space O(1) - iterative, recurisve O(log N) due to stack calls.

class Solution {
    public int search(int[] nums, int target) {
        // take start and end
        // if left is less than right, it means that left position is sorted
        // else other wise. so use comparison for shortest portion
        
        int first = 0;
        int end = nums.length-1;
        while(first <= end){
            int mid = (first+end)/2;
            if(nums[mid]==target){
                return mid;
            }
            if(nums[first] <= nums[mid]){  // corner case, for 2 elements , first will be mid, so we add <=.
                // left side is sorted
                if(target < nums[mid] && target >=nums[first]){
                    end = mid -1;
                }else{
                    first = mid +1;
                }
                
            }else {
                //right side is sorted
                if(target > nums[mid] && target <= nums[end]){
                    first = mid +1;
                }else{
                    end = mid -1;
                }
            }
        }
        return -1;
    }
}
	  
======================================================================================================================================================================

Find First and Last Position of Element in Sorted Array
Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.

If target is not found in the array, return [-1, -1].

Follow up: Could you write an algorithm with O(log n) runtime complexity?

we have a sorted array and duplicate values are given. we need to find our target and see if its dulicated.
if duplicate exists, return start and end index of duplicates.

we must use Binary search.
take first and last and find middle. same formula (start+end)/2
we continue untill we find our target number as middle.
Once we find it, we need to find left and right extreme if the target.
if( mid value == target , then continue below - else continue Binary search)
if (mid==0) OR (check if front of mid, if it has diff value than target. if value is diff, we found the left extreme),
 - we found left extreme 
 - if not, then we need to perform Binary search in left portion untill we find a mid, where min-1 Not eql to mid, which is the left exrememe.
same applies to right extreme.
if (mid==endOfArray) OR (check if next of mid, if it has diff value than target. if value is diff, we found the left extreme),
 - we found right extreme 
 - if not, then we need to perform Binary search in right portion untill we find a mid, where min+1 Not eql to mid, which is the right exrememe.

time O(log N) - 2 times O(log N)
space O(1) iterative, O(log N) recurisve

public int[] searchRange(int[] nums, int target) {
        if(nums==null || nums.length==0)
            return new int[]{-1,-1};
        int start = 0;
        int end = nums.length-1;
        int f = binarySearch(start,end, nums,target,true);
        int s = binarySearch(start,end, nums,target,false);
        
        return new int[]{f,s};
    }
    
    private int binarySearch(int start, int end, int[] nums, int target, boolean flag){
            if(start > end) 
                {
                    return -1;
                }
            int mid = (start + end)/2;
            if(nums[mid]==target){
                if(flag){
                if(mid==0 || nums[mid-1] != nums[mid]){
                    return mid;                    
                }else{
                    return binarySearch(start, mid -1, nums, target,flag);
                    
                }
                }
                else{
                if(mid==nums.length-1 || nums[mid+1]!=nums[mid]){
                    return mid;
                }else{
                    return binarySearch(mid+1, end, nums, target,flag);
                    
                }
                }
            }
            if(target < nums[mid]){
                return binarySearch(start, mid -1, nums, target,flag);                
            }else{
                return binarySearch(mid+1, end, nums, target,flag);                
            }
            
        }

	  
	  
	  
	  
