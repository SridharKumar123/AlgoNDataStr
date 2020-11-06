Two sum
check if sum of any 2 nos in array, sums up to a given target.

3 5 -4 8 11 1 -1 6
target : 10
we can add 11 and -1.

Approach 1:
2 forloop and iterate every number and see if we can find a sum.
time : O(N^2)
space : O(1)

Approach 2:
Use a hashtable. traverse array and store every element in hashtable which allows to access every element in constant time.
now we traverse through the array and take every value and minus it with target and see if we can find this value in hashtable. 
x + y = 10
y = 10- x, for every value of x, we see if y in in hashtable.
we can do this above logic in a single loop. 
loop in , try the above eq and see if result is there in hashtable, if not, add the looped element into the hashtable.

time: O(N) - we are just iterating once.
space : O(N) - hashtable 

Approach 3:
more optimal approach would be to first sort the array. a good sorting algo like quick/heap/merge will take O( N log N) time.
then we can find the answe in O(N) time.
1) sort the array
2) assign L and R pointers in start and end of array
3) perform array[L] +array[R] = sum. compare sum with target. 
   - If sum is less than target, increment L
   - If sum is greater than target, decrement R
   - continue untill L <R

time : O ( N Log N) sort + O(N) search - O( N log N)
space : O(1)

class Program {
  public static int[] twoNumberSum(int[] array, int targetSum) {
    Set<Integer> num = new HashSet<>();
		for(int i=0; i<array.length;i++){
			int diff = targetSum - array[i];
			if(num.contains(diff)){
				return new int[] {array[i], diff};
			}else{
				num.add(array[i]);
			}
		}
		return new int[0];
  }
}
=======================================================================================================================================================================

Validate Subsequence
given 2 arrays, write a function to see if second array is a subsequence of first.

 5 1 22 25 6 -1 8 10 11
 1 6 -1 10
 
have 2 pointers, 1 for each array
if elements in both pointers are same, we increment the second pointer and first pointer
if elements does not match, we just increment first pointer
we continue this until, second array is fully covered - so its a success scenario
 or untill first array gets over without second being over, then its a not subsequence.
Corner case : if we try to return for true use case, and false outsde, it will not address scenaio where last element matches to form the sequence.
	to address this, even if we return true or break for positive use case, it final line, we need to check if j==length of seq
time : O(N) - we itertae throguh the main araay of size N
space : O(1)

class Program {
  public static boolean isValidSubsequence(List<Integer> array, List<Integer> sequence) {
    int j = 0;
		for(int i=0; i<array.size();i++){
			if(j==sequence.size()){
				break; 
			}
			if(array.get(i).equals(sequence.get(j))){
				j++;
			}
		}
	  // corner case
    return j == sequence.size();
  }
}
=======================================================================================================================================================================

Three Number Sum:
in a list of unsorted array, find the list of triplets from any positions whose sum is equal to target.

12 3 1 2 -6 5 -8 6
target = 0
-8,2,6  -8,3,5  -6,1,5

Approach 1: 
we can have 3 for loops and we can find the sum and find the elements. 
time : O(N^3)

Approach 2:
we can use hashtable approach and reduce time to O(N^2)
 - we need to ensure to remove duplicates
 - duplicates can come by having different order of the 3 numbers.
 
Approach 3:
1) sort the array in ascending order O(N log N) time
2) use current, left and right pointers to find the sum and elements.
  sum = current + left + right
  
3) if sum==target, 
      add triplet to op
	  left++
	  right--   (we do both left++ & right-- because, once we get to target, our current is not changing, so to get to target again, just by moving left or right, we cannot get target again, need to move both)
   if sum < target
      left ++
   else right--
4) once left >= right 
      move current to its right - current++
	  continue above logic again.
	  
-8 -6 1 2 3 5 6 12
 C  L           R

time : O(N^2) - O(Nlog N) sort + O(N^2) search
space : O(N) - this is for the op which we store.
	
import java.util.*;

class Program {
  public static List<Integer[]> threeNumberSum(int[] array, int targetSum) {
    if(array.length<=2){
			return new ArrayList<Integer[]>();
		}
		Arrays.sort(array);
		List<Integer[]> op = new ArrayList<Integer[]>();
		int current = 0;
		int left = current+1;
		int right = array.length-1;
		while(current <= array.length-2){			
			int sum = array[current] + array[left] + array[right];
			if(sum==targetSum){
					op.add(new Integer[]{array[current] , array[left] , array[right]});
				left++;
				right--;
			}else if(sum < targetSum){
				left++;
			}else{
				right--;
			}
			if(left>=right){
				current++;
				left = current+1;
				right = array.length-1;
			}
		}
		
    return op;
  }
}

=======================================================================================================================================================================

Smallest difference

take 2 non-empty arrays of int, find pair of numbers whose absolute difference is closest to 0.
absolute difference between -5 and  5 is 10. and between -5 and -4 is 1.
-1 5 10 20 28 3 
26 134 135 15 17
1) sort both the arrays O(N log N)
2) have 2 pointers both at start of 2 arrays.
3) if absolute difference is 0 (if both numbers are same), return the 2 numbers. we have reached solution.
4) else,
   - find the difference and store the minDiffUntillNow
   - increment the index of array which has the smaller of the two numbers. (both arrays are sorted, so we incremenet smaller value to get close to the other larger value in next array)
5) continue untill we get diff as 0 or till one of the array is iterated fully

time - O( N logN + M log M) - N and M are length of 2 arrays. We are sorting both. so we get this..
      the search will be just O(N+M) - so this is ignored compared to above

space - O(1)

class Program {
  public static int[] smallestDifference(int[] arrayOne, int[] arrayTwo) {
    Arrays.sort(arrayOne);
		Arrays.sort(arrayTwo);
		int i=0;
		int j=0;
		int minDiffUntillNow = Integer.MAX_VALUE;
		int[] min = new int[2];
		while(i<arrayOne.length && j<arrayTwo.length){
			int currentVal = Math.abs(arrayOne[i] - arrayTwo[j]);
			if(currentVal < minDiffUntillNow){
				minDiffUntillNow = currentVal;
				min[0] = arrayOne[i];
				min[1] = arrayTwo[j];
			}
			if( currentVal==0){
				break; //return new int[]{arrayOne[i] , arrayTwo[j]};
			}else if(arrayOne[i] < arrayTwo[j]){
				i++;
			}else{
				j++;
			}
		}
    return min;
  }
}

	
=======================================================================================================================================================================
	
Move Element to End

given a array of int, move all instances of a given int to end of array in place. we dont care about order of other elements.

2 1 2 2 2 3 4 2 
int - 2
1 3 4 2 2 2 2 2

2 pointer approach.
1 pointer at start and 1 ptr at end of array.
if, the second pointer points to our target element, decrement second pointer. (we need to point to the element which we would like to swap - we dont want to swap target from back)
if, first pointer is not pointing to target, increment it.
if first points to ->target and second not point to target,
   swap
if second pointer passes the first, we stop.

2 1 2 2 2 3 4 2 
i			  j

time : O(N) - we are just iterating once. every element is visited only once.
space : O(1)

import java.util.*;

class Program {
  public static List<Integer> moveElementToEnd(List<Integer> array, int toMove) {
    int fromLast = array.size()-1;
		int i = 0;
		while(i < fromLast){
			// to handle case where all elements are same 9,9,9,9
			while(fromLast>0 && array.get(fromLast)==toMove){
				fromLast--;
			}
			// to handle case where oter while loop is valid but fromLast gets dec befote
			if(i < fromLast && array.get(i)==toMove){
				swap(i,fromLast,array);
			}
			i++;
		}
    return array;
  }
	
	private static void swap(int i,int j, List<Integer> array){
		int temp = array.get(i);
		array.set(i,array.get(j));
		array.set(j,temp);
	}
}

