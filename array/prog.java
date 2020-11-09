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

=======================================================================================================================================================================

Monotonic Array

-1 -5 -10 -1100 -1100 -1101 -1102 -9001
1 1 1 2
check whether an array is monotonic.
monotonic - elements from left to right, must be entirely non-increasing or entirely non-decreasin.

we have a direction var,  this can be increasing/decreasin/NO-directin - can be an enum
No-direction - occurs if the 2 numbers are same.
only if 2 numbers are diff we can come up with increasing/decreasin direction.

we iterate throguh the array and every time we compute direction by comparing element and its next element.
if we get No-direction, we try to check if the next 2 num, gives us a directin.
once we get a valid direction, then in the upcoming direction, we check if it is same as old or breaks the old direction.

time O(N)
space O(1)

OR

Lets first check if array is non-decreasing
Now check if array is non-increasing
The above 2 sets can be done using 2 for loops easily. or can be done using same for loop with 2 diff checks in it
this will be lot easier than above approach as its just 2 booleans to track

time O(N)
space O(1)
	
import java.util.*;

class Program {
  public static boolean isMonotonic(int[] array) {
		if(array.length==1)
			return true;
    boolean nonincreasing = true;
		boolean nondecreasing = true;
		for(int i=0;i <array.length-1;i++){
			if(array[i]==array[i+1]){
				continue;
			}
			if(array[i] < array[i+1]){
				nondecreasing = false;
			}
			if(array[i] > array[i+1]){
				nonincreasing = false;
			}
		}
    return nonincreasing || nondecreasing;
  }
}

=======================================================================================================================================================================

spiral traverse

taken a 2d array and return a 1d array with elements in spiral order. 

[
[1,  2,  3,  4], 
[12, 13, 14, 5],
[11, 16, 15, 6],
[10, 9,  8,  7]
]

[1, 2, 3, 4, 5 ,6, 7, 8 ,9, 10, 11, 12 ,13, 14, 15 , 16 ] 

   SC          EC
SR[1,  2,  3,  4], 
  [12, 13, 14, 5],
  [11, 16, 15, 6],
ER[10, 9,  8,  7]

We consider each outer portion as a rectangle and we need to first traverse the outer rectangle first before coming to inner.
We first find the SR Staring Row, ER Ending Row, SC- Staring Column, EC -Ending column
we need to move along the rectangle and add the items.
once over, go to inner rectangle and perform the same.
we stop when ER > SR and EC > SC

this can be implemented both iteratively and recrusively

time O(N)
space O(N) - saving solution in another array

import java.util.*;

class Program {
  public static List<Integer> spiralTraverse(int[][] array) {
    int startRow = 0;
		int endRow = array.length-1;
		int startCol = 0;
		int endCol = array[0].length-1;
		List<Integer> op = new ArrayList<>();			
		 while(startRow <= endRow && startCol <= endCol){
			 for(int col=startCol; col<=endCol; col++){
				 op.add(array[startRow][col]);
			 }
			 for(int row=startRow +1; row<=endRow; row++){
				 op.add(array[row][endCol]);
			 }
			 for(int col=endCol-1; col>=startCol; col--){
				 if(startRow==endRow) break;
				 op.add(array[endRow][col]);
			 }
			 for(int row=endRow-1; row>startRow; row--){
				 if(startCol==endCol) break;
				 op.add(array[row][startCol]);
			 }
			 startRow++;
			 endRow--;
			 startCol++;
			 endCol--;
		 }
    return op; 
  }
}

=======================================================================================================================================================================

Longest peak

peak is 3 consecutive int, such that they are in increasing order and it dips after the peak
1 4 10 2 - forms a peak. all 4 elements are part of the peak. it slowly increases to 10 and reduces to 2.
4 0 10 and 1 2 2 0 does not form.


1 2 3 3 4 0 10 6 5 -1 -3 2 3 

here we try to first find all the peaks and then find the length of the longest peak.
iterate through the array, compare every value with its adjacent values and find peaks.we store the peak index or we calculate length of peak in same step and hold max always.
if we start with 123, 2 is greater than 1 but not greater than 3. so it does not form a peak.
now from the peak we go in left and right directions untill elements are strictly decreasin.
take the count.

3 3 4 0 - forms a peak.
0 10 6 5 -1 -3 forms a peak
the longest peak is of length 6.

time : O(N) - we iterated through array first, then we just had to go to left and right of peaks.
space : O(1) - if we dont store the peak and do it in place. 
	
import java.util.*;

class Program {
  public static int longestPeak(int[] array) {
		int longest = 0; 
    for(int i=1; i<array.length-1;i++){
			if(array[i] > array[i-1] && array[i] > array[i+1]){
				int left = i;
				int right = i;
				while(left-1>=0 && array[left] > array[left-1]){
					left--;
				}
				while(right+1 <= array.length-1 && array[right] > array[right+1]){
					right++;
				}
				int count = right - left + 1;
				if(count > longest){
					longest = count;
				}
			}
		}
    return longest;
  }
}

=======================================================================================================================================================================

Four number sum

find set of all 4 numbers in the array which when added forms the target.

7 6 4 -1 1 2
target sum - 16

having 4 for loops to solve this would not be a better solution as it will have O(N^4) time complexity

A quadraplet (4 numbers) can be expressed as a pair of sum of numbers.

a b c d - 4 numbers 

a + b = k 
c + d = l
can be expressed as k,l 

we store in a hashtable, the sum and the list of pair of numbers, whose sum is equal to the sum.
2 diffeent numbers can form the same sum. 1,2  4,-1  both form 3 as sum.
we need to form the hashtable while iterating the array in a single shot and check for values. 
 - else we might end up in wrong count. [7,6, 1,2]  [7,1  6,2]  - these 2 sets are same but with diffeent combo. to avoid this.

7 6 4 -1 1 2 
we start at 7, we iterate through all numbers after 7
  - 7+6 = 13, 16-13 = 3. we check if 3 is in hashtable. its not. move on
  - 7+4 = 11, 16-11 = 5. we check if 5 is in hashtable. its not. move on
  ... we continue for all after 7. and as we did not add any entry in hashtable untill now, all above will not match
- we come to second number 6, we perform same operation for all numbers after 6. nothing will generate op.
  - now we iterate through all numbers before 6, in our case only 7.  
    - 7+16 = 13, we add this to hashtable.  13 is key, 7,6 is value
- we come to third number 4, we iterate throguh rest.
   - 4 + -1 = 3, 16-3=13, we have 13 in hashtable.
   - we pick all the pairs in hashtable value for 13. note- 13 can have multiple combination of pairs  1,12   2,10 etc
   - for each pair in value, we add the current pair - 4,-1 to form quadraplets.
  - now we iterate through all numbers before 4, and find sum and add them to the hashtable. this time we dont compare it to target, we just find sum and add to hashtable
Note: we always add entries in hashtable only with numbers before it. this is to avoid duplicate quadraplets.      
  - we continue till end.
  
time : O(N^2) - we are doing one for loop - and for every item, we again iterate through every element in list
     worst case - O(N^3) - if for same sum, many pair of values are added to hashtable, we need to iterate through elements in values of hashtable to form the quadraplet.
space : O(N^2) - hashtable can have diffeent combination of N

import java.util.*;

class Program {
  public static List<Integer[]> fourNumberSum(int[] array, int targetSum) {
		List<Integer[]> op = new ArrayList<>();
    Map<Integer,List<Integer[]>> map = new HashMap<>();
		for(int i=0; i<array.length;i++){
			for(int j=i+1; j<array.length;j++){	
					int sum = array[i] + array[j];
					if(map.containsKey(targetSum - sum)){
						List<Integer[]> data = map.get(targetSum - sum);
						for(Integer[] arr : data){
							Integer[] opArray = new Integer[4];	
							opArray[0] = arr[0];
							opArray[1] = arr[1];
							opArray[2] = array[i];
							opArray[3] = array[j];
							op.add(opArray);
						}
					}			
			}
				for(int l=0; l<i;l++){
					int sum = array[i] + array[l];
					if(map.containsKey(sum)){
						List<Integer[]> data = map.get(sum);
						data.add(new Integer[]{array[i] , array[l]});
					}else{
						List<Integer[]> list = new ArrayList<>();
						Integer[] a = new Integer[]{array[i] , array[l]};
						list.add(a);
						map.put(sum,list);	
					}
				}
			}		
    return op;
  }
}

=======================================================================================================================================================================

Subarray Sort
given an array, return the indeices of start and end of the smallest sub array within the main array, that needs to be sorted in place, so that the entire main array gets sorted in ascending order.

[1, 2, 4, 7, 10, 11, 7, 12, 6, 7, 16, 18, 19]

here 7,10,11,7,6,7 - needs to be sorted
so op - 3,9

this could start even from the first index. 
tricky part is, even if one number is not in right position, but if its real position is in first, then whole array needs to be sorted.
so final position of sorted index depends on the position of the misplaced element.
we need to find all the unsorted numbers and 
  - within this we need to find the smallest and largest one.
  - find their final positions in sorted order.
  - the final positions determine the start and end of the items to sort

1) go over every element and find the unsorted ones by comparing them to their previous and next elements
2) find the smallest and largest numbers within this.
3) we need to find the final sorted position for the smallest and largest numbers
4) these 2 are the start and end indeices for the set of indeices which needs to be sorted
5) for smallest number position, start from first index and compare elements and find the index of this element
6) for the largest number position, start from end and compare elements and find the index of this element
7) so we need to sort all elements between the start and end indeices which we identified.

time : O(N) 
      - we find all unsorted elements in one pass O(N)
	  - we find position of smallest and largest number - O(N)
space : O(1)
	
import java.util.*;

class Program {
  public static int[] subarraySort(int[] array) {
    int smallest  = Integer.MAX_VALUE;
		int largest = Integer.MIN_VALUE;
		for(int i=0; i<array.length;i++){
			if(i>0 && i<array.length-1 
				 && (array[i] < array[i-1] || array[i] > array[i+1])){
				if(array[i] < smallest){
					smallest = array[i];
				}
				if(array[i] > largest){
					largest = array[i];
				}
			} else if(i==0 && array[i] > array[i+1]){
				if(array[i] < smallest){
					smallest = array[i];
				}
				if(array[i] > largest){
					largest = array[i];
				}
			}else if(i==array.length-1 && array[i] < array[i-1]){
				if(array[i] < smallest){
					smallest = array[i];
				}
				if(array[i] > largest){
					largest = array[i];
				}
			}
		}
		int startIndex = -1;
		for(int i=0; i<array.length;i++){
			if(array[i] > smallest){
				startIndex = i;
				break;
			}
		}
		int endIndex = -1;
		for(int i=array.length-1; i>=0;i--){
			if(array[i] < largest){
				endIndex = i;
				break;
			}
		}
    return new int[] {startIndex, endIndex};
  }
}

=======================================================================================================================================================================

Largest Range
take array of int, and return the smallest and largest numbers in range of int.
A range of int, is set of numbers that come after each other in set of real int.
2,3,4,5,6 - this forms a range.
3,2,4,6,5 - this forms a range.
The numbers need not be sorted or adjacent in input array.

1) we could sort the array
2) check if previous number is 1 less then current number, then we continue till for all
3) we can find the range using this.

time: O(N logN) - due to sort

A better optimal solution
[1,11,3,0,15,5,2,4,10,7,12,6]
1) store all numbers in a hashtable and let the value be false - O(N)
2) start from first number in array, here its 1. Mark its value as true(visited)
  - minus 1 from it - 0 . and see 0 is in hashtable, it is. so it is the start element for now. mark value of 0 as true.
  - minus 1 from 0. its -1. check if -1 is in hashtable, its not. so our start element is 0. 
  - add 1 to 1. its 2. check if 2 is in hashtable, if so update hashtable and end index.
  - continue untill we find end element that is not in hashtable.
  - store the newly formed array
3) start with next element, 11. check if it is not visited in hashtable. meaning, its value is false
   - if not visited, continue the above logic and stire its range
4) we start with 3, 3 is having true in hashtable, meaning it was already visited. so we skip it
... continue with others.

time : O(N) - we iterated and add in hastable. 
            - next time while we iterate, we just check if its not already visited and then only visit. 
space : O(N) - hashtable   

import java.util.*;

class Program {
  public static int[] largestRange(int[] array) {
		
		int smallest = 0;
		int largest = 0;
		int longestLength = 0;
    Map<Integer,Boolean> map = new HashMap<>();
		for(int i=0; i<array.length;i++){
			map.put(array[i], false);
		}
		for(int i=0; i<array.length;i++){
			int cur = array[i];
			if(!map.get(cur)){
				map.put(cur,true);				
				int currentLength = 1;
				int left = cur - 1;
				int right = cur + 1;
				while(map.containsKey(left)){					
					map.put(left,true);
					currentLength++;
					left--;					
				}				
				while(map.containsKey(right)){					
					map.put(right,true);
					currentLength++;
					right++;					
				}
				if(currentLength > longestLength){
					longestLength = currentLength;
					smallest = left +1;
					largest = right -1;
				}
			}
		}
    return new int[] {smallest, largest};
  }
}

=======================================================================================================================================================================

zig zag:
1  3  4  10
2  5  9  11
6  8  12 15
7 13  14 16

output should be - 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16

if we need todo a diagonal traversal its simple, we just need to move one row and column down or one row and column up.
problem arises when we need to go down, up, left or right.
if we closely look at traversal, when we are in first column and we are going down, we go one row down.
if we are in last row and we are going down direction, we go one step right.
same applies for top row and last column but tracking upward direction.

we need to track direction
1) start by going down. direction is down.
2) as we did go down in first column, we change direction and move diagonaly up,
3) check if we are in first row or last column,
 - if so, move right or down based if we are in first row or final column, change direction
 - if not so, then keep moving diagonaly up
4) same logic in first column and last row.
5) continue the same to traverse untill final element of array

time: O(N)
space: O(N) - output array but we did not use any extra space for the algorithm

import java.util.*;

class Program {
  public static List<Integer> zigzagTraverse(List<List<Integer>> array) {
		int row=0;
		int col=0;
		List<Integer> op = new ArrayList<>();
		int height = array.size()-1;
		int width = array.get(0).size()-1;
		boolean goingDown = true;
    while(row>=0 && col>=0 && row<array.size() && col<array.get(0).size()){
			op.add(array.get(row).get(col));
			if(goingDown){
				if(col==0 || row == height){
					goingDown = false;
					if(row == height){
						col++;
					}else{
						row++;
					}
				}else{
					row++;
					col--;
				}
			}else{
				if(row==0 || col==width){
					goingDown = true;
					if(col==width){
						row++;
					}else{
						col++;
					}
				}else{
					row--;
					col++;
				}
			}
		}
    return op; 
  }
}
