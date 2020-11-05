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
    return j == sequence.size();
  }
}

