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
