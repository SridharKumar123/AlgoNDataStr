Max subset sum no adjacent
take an array of positive int and return max sum of non-adjacent elements in array.
non-adjacent elements mean  - if u choose an element then u cannot choose its neighbours.
create an array of same length of input array, and fill it with the greatest sum untill its index, without including neighbours but itself.
[7 , 10, 12, 7, 9, 14]

[7 , 10, 19, 19, 28, 33]

33 is the max sum.

(For any position, if we take max sum before it, we cannot add the current, as it will be adjacent.)
OR
(We can take the max sum one step before it and add the current value to it.)
We find the max of the above 2.
For any i in array[i],
maxSumOf[i] = Max ( maxSumOf[i-1] , maxSumOf[i-2] + array[i] )
Base cases - 
maxSumOf[0] = array[0]
maxSumOf[1] = array[1]
applying i as 2 in above eq,
maxSumOf[2] = Max ( maxSumOf[1] , maxSumOf[0] + array[2] )

time - O(N)
space - O(N) - we are building an array of length N.

Can we do better ??????

we always look at only last 2 stored values at any time, so we can just hold the last 2 values and reduce space.
we can have pointers like first and second to hold the 2 values.
current = Max(prev, far + array[i])
far -> prev
prev -> current

change pointers accordingly.
time - O(N)
space - O(1)

If we are given -ve values in array, we need to tweak formula, as we never want to add a -ve integer to a sum as it will lower the sum.

import java.util.*;

class Program {
  public static int maxSubsetSumNoAdjacent(int[] array) {
		if(array==null || array.length==0)
			return 0;
		if(array.length==1)
			return array[0];
    int[] max = new int[array.length];
		max[0] = array[0];
		max[1] = Math.max(array[0],array[1]);
		int maxUntill = Math.max(max[0],max[1]);
    for(int i=2; i< array.length; i++){
			max[i] = Math.max(max[i-1], max[i-2]+array[i]);
			maxUntill = Math.max(max[i], maxUntill);
		}
		return maxUntill;
  }
}
---
import java.util.*;

class Program {
  public static int maxSubsetSumNoAdjacent(int[] array) {
		if(array==null || array.length==0)
			return 0;
		if(array.length==1)
			return array[0];    
		int prev = array[0];
		int current = Math.max(array[0],array[1]);
		int maxUntill = Math.max(prev, current);
    for(int i=2; i< array.length; i++){
			int next = Math.max(current, prev+array[i]);
			prev = current;
			current = next;
			maxUntill = Math.max(current, maxUntill);
		}
		return maxUntill;
  }
}

=======================================================================================================================================================================
No of ways to make change:
Given an array of int representing coins, find the diff no of ways we can form the denomination
[1,5]  denomination - 6
2 ways  = 1 * 1 + 1 *5 , 1 * 6

We need to approach this problems using a ways[].
For the size of the denomination, we construct an array starting from 0 ... until denomination.
now for each size, we need to find the combination of no of ways in which it can be formed with all elements.
We start with every element in array and first find the ways in which we can form the denomination and then use this to add it to the next element and so on.
If the size of denomination is less than the coin, then we can ignore it.
if size is >=, then we apply ways[i] = ways[i] + ways[i-coniSize]
Base case - ways[0] = 1. We assume that there is only 1 way to form 0 denomination. i.e not use any coin. 

Now we iterate through each coin and apply the above eq.
The idea here is, every time we loop through, we pick the current value = which is the no of ways in which we were able to form previous denomination + ways[denomination-coin] - no of ways we formed it withoout this denomination.

no of ways[new coin] = ways[exclude new coin] + ways[include new coin]
ways[i] = ways[i] + ways[i-coniSize]

here exclude the coin matches to ways i, because, if we dont consider this coin, the current ways will have the ways to form the denomination excluding this coin
Now include coin, tricky,if we include the coin, then the remaining amount to make the total will be (denomination-new coin) 
lets say denomination is 5, coin is 2.
Now 2 + 3 = 5  => to make 5, if we include 2, then we need to find the no of ways to make 3 using the new coin.
In our ways array, we always start from 0. so by the time we use this logic, we will already have the ways for the logic to find the above.
see vivek video for above explanation.

space - O(N) 
time - O(Nd) - where d is no of denomination. we are iterating through each of denomination.

import java.util.*;

class Program {
  public static int numberOfWaysToMakeChange(int n, int[] denoms) {
   int[] ways = new int[n+1];
		ways[0] = 1;
	 
		 for(int j=0; j<denoms.length;j++){
			 for(int i=1; i<=n;i++){
			 if(denoms[j] <= i){				 
				 ways[i] = Math.max(ways[i], ways[i - denoms[j]] + ways[i]);
			 }
		 }
	 }
		return ways[n];
  }
}

=======================================================================================================================================================================
Min num of coins for change
find the smallest number of coins needed to form the target. this is similar to above, but we need to find the min no of coins.

[1,5]  denomination - 6
1 * 1 + 1 *5 , 1 * 6 
2 coins using 1 and 5, wheras 6 coins using 1.
so we choose 2.


build array of length of target+1, like before prblm.
for 0 dollar, we dont need any coin. so its base case 0.
now for each coin, we need to find the no of coins needed to reach it. 
we fill the array with Math.max value.
we see if coin value is greater than current denomination, else we skip those values untill we reach value <= denomination.
for doing this, we subtract the value with coin - meaning we are using 1 new coin, and we see if its 0, if 0 its just 1 coin.
if we have reminder, we see the min num for this reminder in array and take the min no of its coins.
we add 1 + min no of the reminder. This gives min no for that value. we take min of this vs current value.
we continue, for every value of n.
we start with each denomination and iterate all n elements. and loop for all denomination.
final result will be the value for nth denomination.


if denomination <= amount:
   nums[amount] = Min( nums[amount], 1+nums[amount-denomination])

time - O(ND) - d is no of denomination. 
space - O(N)
	   
import java.util.*;

class Program {
  public static int minNumberOfCoinsForChange(int n, int[] denoms) {
		if(denoms.length==0){
			return -1;
		}
		Arrays.sort(denoms);
    		int[] min = new int[n+1];
		for(int i=0; i<=n; i++){
			min[i] = n+10;
		}
		min[0] = 0;
		for(int j = 0; j<denoms.length; j++){
		  for(int i=1; i<=n; i++){
		    if(denoms[j] <=i){
		       min[i] = Math.min(min[i], 1 + min[i-denoms[j]]);
		    }
		  }
		}
		if(min[n]==n+10){
		  return -1;
		}else{
		  return min[n];
		}    
  }
}

=======================================================================================================================================================================
Levenshtein distance:
takes in 2 strings and returns min number of edit operations that needs to be performed on first string to obtain other.
Insert/Delete/Update are edit operations.
We need to build a matrix with chars in one string in cols and another in rows.
For every element in it, we find the number of operations for the row header to form the column positions until now.


str1 = "abc"
str2 = "yabd"

   "" y a b d
"" 0  1 2 3 4 
a  1  1 1 2 3
b  2  2 2 1 2
c  3  3 3 2 2
We start with empty strings. how many edits ? 0 edits.
Then we move to next item, y - with empty string, we need one insert to form y. then one more insert to form a-so 1 +1 =2 for a
this continues for first row.
same applies for first column.
Now from second row, we see how many edit for a to become "". - 1
how many edit for a to become y - 1 - substitute. this goes on. and we take the value in the last row,column.
By building this we can arrive at below formula. 
If the char of 2 strings are equal, then we can pick the diagonal left value, meaning the last operations needed for its before char. 
If chars are diff, we pick the min of (up, left, diag left) +1. Meaning, we can take any min route out of previous chars value and make one change to it, as they both are not same.
here we used r-1, c-1 in string, as we added extra "" in the matrix.

if(str1[r-1]==str2[c-1]):
   E[r][c] = E[r-1][c-1]
else:
   E[r][c] = 1 + min ( E[r][c-1], E[r-1][c], E[r-1][c-1])

Time O(MN) - M and N are length of 2 strings.
Space O(MN)
we can reduce space by picking only last 2 rows, as in formula we just depend on only the last 2 rows for finding values
Space O(min(M,N)) - we pick the min string to form the row.

import java.util.*;

class Program {
  public static int levenshteinDistance(String str1, String str2) {
    int[][] matrix = new int[str1.length()+1][str2.length()+1];
		for(int i=0,j=0; j<matrix[0].length;j++){
			matrix[i][j] = j;
		}
		for(int i=0,j=0; i<matrix.length;i++){
			matrix[i][j] = i;
		}
		for(int i=1; i<matrix.length;i++){
		  for(int j=1; j<matrix[0].length; j++){
		    if(str1.charAt(i-1)==str2.charAt(j-1)){
			matrix[i][j] = matrix[i-1][j-1];
		    }else{
			matrix[i][j] = Math.min(matrix[i-1][j], matrix[i-1][j-1]);
			matrix[i][j] = 1 + Math.min(matrix[i][j], matrix[i][j-1]);
		      }
		   }
		}
    return matrix[str1.length()][str2.length()];
  }
}

-------
import java.util.*;

class Program {
  public static int levenshteinDistance(String str1, String str2) {
		String small = str1.length() < str2.length() ? str1 : str2;
		String big = str1.length() >= str2.length() ? str1 : str2;
		int[] even = new int[small.length() +1 ];
		int[] odd = new int[small.length() +1 ];
		for(int i=0; i<small.length()+1;i++){
			even[i] = i;
		}
		int[] currentEdits = odd;
		int[] prevEdits = even;
		for(int i=1; i<big.length()+1;i++){			
				if(i%2 == 1){
					currentEdits = odd;
					prevEdits = even;
					
				}else{
					currentEdits = even;
					prevEdits = odd;
				}
			currentEdits[0] = i;
			for(int j=1; j<small.length()+1;j++){
				if(small.charAt(j-1)==big.charAt(i-1)){
					currentEdits[j] = prevEdits[j-1]; 
				}else{
					currentEdits[j] = Math.min(currentEdits[j-1], prevEdits[j-1]);
					currentEdits[j] = 1 + Math.min(currentEdits[j], prevEdits[j]);
				}
			}
		}
		if(big.length()%2==0){
			return 	even[small.length()];
		}else{
			return odd[small.length()];
		}
    
  }
}

	
=======================================================================================================================================================================	
Max sum increasing sub sequence

A sub sequence need not be adjacent in array, but in same order. 
10, 70, 20 ,30, 50, 11, 30
Op - 110 - logic - 10,20,30,50


logic - 
for every index, we start from begening of array and see if we have a number lesser than the current num.
if so, we add the max sum of that num to our current num and see if its greater than current max sum, if grt then update. else continue to next index untill our value.

The question also asks the values which are used for adding. To get that, we can track them in diff DS, but we can do it effecientluy in a new array
This sequence arrray elements will point to the previous index which was used to add to this index to make the max sum. if no prev was added it will be null.
initially, we start by cloning the max sum from input array. this allows us to handle negative use cases.
input 	  =>  [8, 12, 2, 3, 15, 5, 7] 
max sum   =>  [8, 20, 2, 5, 35, 10, 17] 
sequence  =>  [Nul, 0, Non, 2, 1, 3, 5] 
For any index, if we pick the element in sequence it will point to the previous element of that sequence, we can go untill we reach null.

currentNum = array[i]
otherNum = array[j] 0<= j < i

if otherNum < currentNum && if sum[j] + currentNum >= sum[i]
  update sum[i] = sum[j] + currentNum
  sequence[i] = j
time : O(N^2) - we do kind of a 2 for loops
space: O(N)
	
import java.util.*;

class Program {
  public static List<List<Integer>> maxSumIncreasingSubsequence(int[] array) {
    
		int[] max = array.clone();  // this is key, we must not start with 0 initialized values // this help us to hanle negative use cases
		int[] seq = new int[array.length];
		Arrays.fill(seq, Integer.MIN_VALUE);
		int currentMaxIndex = 0;		
		for(int i=0; i< array.length; i++){
				for(int j=0; j<i;j++){
					if(array[j] < array[i] && max[j] + array[i] >= max[i]){
						max[i] = max[j] + array[i];						
						seq[i] = j;
					}					
				}
			if(max[i]>=max[currentMaxIndex]){
				currentMaxIndex = i;
			}
		}
		return buildSeque(currentMaxIndex, array, seq, max);    
  }
	
	private static List<List<Integer>> buildSeque(int index, int[] array, int[] seq, int[] max){
		List<List<Integer>> op = new ArrayList<>();
		List<Integer> sum = new ArrayList<>();
		sum.add(max[index]);
		op.add(sum);
		List<Integer> indices = new ArrayList<>();
		while(index!=Integer.MIN_VALUE){
			indices.add(array[index]);
			index = seq[index];
		}
		Collections.reverse(indices);
		op.add(indices);
		return op;
	}
}

=======================================================================================================================================================================	
Longest Common Subsequence

take 2 strings and get the longest common subsequence.
subsequence need not be adjacent, it needs to be in same order.

if we have 2 string ABC and AC, to find LCS, we first check from the end of the 2 strings and start removing chars
C==C, so C is part of LCS.
Now we take this C and prepend it to the LCS of previous LCS. Here this is our first LCS, so ignore.
Next remaining are AB and A. now we must remove last letter from first and second and see which gives longest LCA and pick it.
1)remove A from second string. But then there wont be any LCS for this portion as second string became empty.
2)remove B from first string, A==A. prepend A to LCS, AC. so we get LCS as AC. we found this by removing char from either strings and see which gives max LCS.
So at ant point, we try removing last char from the 2 strings and pick the bigger LCS of two.

   ""  x   k   y    k   z  p   w
"" ""  ""  ""  ""  ""  ""  ""  ""
z  ""  ""  ""  ""  ""  z   z   z
x  ""  x   x   x    x  x   x   x
v  ""  x   x   x    x  x   x   x
v  ""  x   x   x    x  x   x   x
y  ""  x   x   xy   xy xy  xy  xy
z  ""  x   x   xy   xy xyz xyz xyz
w  ""  x   x   xy   xy xyz xyz xyzw

1) In the start we pick empty char. so anything with empty will result in empty
2  We travese through fields, if there is a match in row/col, then we pick iys left diagonal in previous row and append new char to it. Meaning, we pick the last LCS withoout this char and append new char to form new LCS.
3) If there is no match, then as mentioned earlier, we need to remove 1 char from both strings, find LCS for them, pick the LCS which has longest length.
4) Meaning, check right and top of char, pick LCS with bigger length.
We arive at the formula using this.

time : O(NM * min(N,M)) 
   - O(NM) - for forming the matrix. 
     min(N,M) - for every LCS, when we concat its another time involved.
space: O(NM * min(N,M))

1) We can improve space complexity by having only the last 2 rows of the matrix. We just need current and previous rows.
2) Much better way would be to not store LCS in matrix, rather store pointers to backtrack to form LCS.

class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        String[][] matrix = new String[text1.length()+1][text2.length()+1];
        for(int i=0,j=0; j<matrix[0].length;j++){
            matrix[i][j] = "";
        }
        for(int i=0,j=0; i<matrix.length;i++){
            matrix[i][j] = "";
        }
        for(int i=1; i<matrix.length;i++){
            for(int j=1; j<matrix[0].length;j++){
                if(text1.charAt(i-1)==text2.charAt(j-1)){
                    String diag = matrix[i-1][j-1];
                    String chars = String.valueOf(text1.charAt(i-1));
                    matrix[i][j] = diag + chars;
                }else{
                    String before = matrix[i-1][j];
                    String up = matrix[i][j-1];
                    matrix[i][j] = before.length() > up.length() ? matrix[i-1][j] : matrix[i][j-1];
                }
            }
        }
        return matrix[text1.length()][text2.length()].length();
    }
}

	
=======================================================================================================================================================================	

Min number of jumps

given array of int, the int at each position is the max no of nums can be made.
find the min no of jumps to reach last position.

if (arrays[j]+j >= i)
   jumps[i] = mins(jumps[i], jumps[j]+1)
   
time O(N^2) - we iterate throguh each index. and for each index, we iterate through all index before it.
space O(N)

For every index, we store the maxReach and we maintain a steps and jump variable and tweak it.
See code for more understanding.
At each point we know what is the maxReach from the point. its basically the element at that index + index.
 maxReach = arrays[i] + i
steps - when at index 0, we know that we can take no of steps=arrays[0], untill we have to take a step.
while iterating array, we update maxReach and steps.
3, 4, 2, 1, 2, 3, 7, 1, 1, 3
   maxReach = arrays[0]   - initially it will be the max steps available at start index
   steps = arrays[0] - we know we have these much steps to take untill we run out of steps.
   jump = 0
  for 1 <= i < len
    maxReach = max( maxReach , arrays[i] + i) 
	  // maxReach will always be max position which can be reached using any of before index elements
	steps -=1
    if(steps ==0)   // when we run out of steps, we have to make a jump.
	  jumps +=1
	  steps = maxReach - i // we know that we can still go to maxReach index without making jumps. so we change positions.
	  return jumps+1 - by time we reach final index, we still need one more jump.
	    
class Solution {
    public int jump(int[] nums) {
        System.out.println(nums.length);
        int[] jumps = new int[nums.length];
        Arrays.fill(jumps,Integer.MAX_VALUE);
        jumps[0] = 0;
        for(int i=0; i<nums.length;i++){
            for(int j=0; j<i;j++){
                if( j + nums[j] >=i){
                    jumps[i] = Math.min(jumps[i] , 1 + jumps[j]);
                }
            }
        }
        return jumps[nums.length-1];        
    }
}

--
	
class Solution {
    public int jump(int[] nums) {
        if(nums.length==1)
            return 0;
        int jumps = 0;
        int maxReach = nums[0];
        int steps = nums[0];
        for(int i=1; i<nums.length-1;i++){
            maxReach = Math.max(maxReach, i+ nums[i]);
            steps -=1;
            if(steps==0){
                jumps +=1;
                steps = maxReach - i;
            }
        }
        
        return jumps+1;        
    }
}
	
=======================================================================================================================================================================	
water trapped
in a container where pillars are present, we need to find the water stored between the pillars.

For each index we can calculate the water stored above it.
for any index, we need to find the tallest pillar to its left and tallest pillar to its right. the water will be trapped in between the 2 pillars. 
leftPillar , rightPillar are the tallest in left and right.
Now we find the smaller of these 2. Any water can be stored until this smaller level.
We see the height of any pillar at the current position. if there is a pillar, we minus it with smaller value.

we build leftMax and rightMax arrays. At any position, leftMax will hold the tallest pillar to left of this position.

input - [0,8,0,0,5,0,0,10,0,0,1,1,0,3]
we go from left to right and calculate this,
leftMax - [0,0,8,8,8,9,8,8,10,10,10,10,10,10]
we go from right to left and calculate this,
rightMax - [10,10,10,10,10,10,10,3,3,3,3,3,3,0]
water - [0,0,8,8,3,8,8,0,3,3,2,2,3,0] - we sum up the values to get result. 48

minHeight = min(leftMax,rightMax)
if(height < minHeight){
	w = minHeight - height;
}else 
   w = 0

time O(N)
space O(N)

class Solution {
    public int trap(int[] height) {
     // we move from left to right and find the largest to its left
     // we move from right to left anf find the largest to right
     // max = minof(left,right) - value
       int[] leftMax = new int[height.length];
       int[] rightMax = new int[height.length];
       int currentMax = 0;
       for(int i=0; i<height.length; i++){
         leftMax[i] = Math.max(currentMax,height[i]);
         currentMax = leftMax[i];
       }
      currentMax = 0;
      for(int i=height.length-1; i>=0; i--){
          rightMax[i] = Math.max(currentMax,height[i]);
          currentMax = rightMax[i];
      }
      int[] total = new int[height.length];
      for(int i=0; i<height.length;i++){
          total[i] = Math.min(leftMax[i],rightMax[i]) - height[i];
      }
    int sum = 0;
        for(int i=0; i<height.length;i++){
          sum = sum + total[i]; 
      }
     return sum;
        
    }
}

	
=======================================================================================================================================================================	

Knapsack
given array of arrays, each sub holds 2 items, 1) items value 2) items weight
we have a max capacity of knapsack.
fit items into knapsack, maximing value but within capacity. we only have 1 of each item.

[1,2][4,3][5,6][6,7]
10

return max value you achieved and the items used
10 - [4,3][6,7]

create 2d array of the capacity values from 0 - capacity in rows
the combination subarray in column

now we need to fill the matrix fiels with the max values sum which can fir that capacity.
1) base case is 0. 0 value as we dont have any item.
2) for each item, can we fit all the items until now for the given capacity.
	- if we cannot, we retain the previous rows value(value of items until previous item)
	- if we can fit, we need to put the corresponds value in the cell.
	  - but we need some calculation here,
	    - lets say we have 2 items in our logic, which can fit for the capacity. which one do we choose or do we choose both ?
		- if collective of both weights exceeds capacity, we know we cannot put both.
		- take the current weight in picture, go to previous row, find cell value of ->( current capacity - weight)- old val
		- add current value to this old val - we get a sum.
		- compare this sum with the - max value we would have if we did not add the new value. 
		 - if we did not add this, it just the value above the current cell.
		- we compare these 2 values and pick max of it.
		- this is similar to include + exclude in coin change problem.
		
      0  1  2  3  4  5  6  7  8  9  10
[] 	  0  0  0  0  0  0  0  0  0  0  0 
[1,2] 0  0  1  1  1  1  1  1  1  1  1
[4,3] 0  0  1  4  4  5  5  5  5  5  5
[5,6] 0  0  1  4  4  5  5  5  6  9  9
[6,7] 0  0  1  4  4  5  5  6  6  9  10

we get 10 as the answer.
we need to backtrack to get the items.
  we take final cell and compare it with previous cell, if they both are same, means we did not pick the current item.
  if they are diffeent, then we picked current item. (if we dont pick an item, the previous cells value will be same)
  Now go to above row, and minus the previous capacity w, value and pick the cell.(meaning we exclude this item)
  if we had not picked the item, we will just go to above cell.
  apply same logic. 
we can form the items using this logic.
we start at 10, 10!=9, so we add 6,7 
Now go to current capacity - w, 10-7 , we go to 3rd cell in before row.
compare its value with previous cell, 4==4, so we did not add this item. go one row ahead in same cell
1!=4, we added this item. 4,3
go up, 3 cells before, capacity becomes 0, we can stop. 
  
To form 4, we take max(1, 0+4) = 4

Let v be value of item and w be weight of item, we form the values[][] matrix
if w <= j
  values[i][j] = max (values[i-1][j] , values[i-1][j-w] + v )
else 
  values[i][j] = values[i-1][j]
  
time - O(Nc) - N is number of items, c is capacity
space - O(Nc)
	
import java.util.*;

class Program {
  public static List<List<Integer>> knapsackProblem(int[][] items, int capacity) {
    int[][] matrix = new int[items.length+1][capacity+1];
		
		for(int i=1; i<matrix.length; i++){
			for(int j=1; j< matrix[0].length; j++){
				int weight = items[i-1][1];
				if(weight > j){
					matrix[i][j] = matrix[i-1][j];
				}else {
					matrix[i][j] = Math.max(matrix[i-1][j], items[i-1][0] + matrix[i-1][j-weight]);
				}				
			}
		}
		List<Integer> indices = new ArrayList<>();
		int row = items.length;
		int col = capacity;
		//indices.add(row);
		while(matrix[row][col]!=0){			
			if(matrix[row][col]==matrix[row-1][col]){
				row -=1;
			}else{
				indices.add(row-1);				
				col = col - items[row-1][1];
				row -=1;
			}
		}
    List<Integer> totalValue = Arrays.asList(matrix[items.length][capacity]);
    //List<Integer> finalItems = Arrays.asList(1, 2);
    var result = new ArrayList<List<Integer>>();
    result.add(totalValue);
    result.add(indices);
    return result;
  }
}

=======================================================================================================================================================================	
Disk stacking:
we have list of disks, where  disk - [1,1,1] - first int - width, 2nd int - depth, 3rd - height
we need to keep one on top of another and get max height. case is, for any disk, all disks below it must have strictly lesser, width, depth and height.

to make this problem easier, we can sort the disks by any dimension. We pick height.
we sort disks based on height.
Nowe we create array of same length as tower. 
For each index, we assume that this disk is at the bottom of tower and we find the max height we can reach by keeping this disk as the bottom.

for every index, the min height will be the height of the disk at that index. we can just put tht disk and we get the height.
we initialize the array with corresponding heights of disks at that position.

we need to update this array and see if we can get to greater heights.
Now for any disk in cunter position, we know all disks after it will have bigger height than this as we sorted based on height. so we can add only disks before it.

so for the first disk the max height will be its height.
for second, we look all disks to left of it, we check if its width, depth and height are smaller than current.  (height still could be same duplicate)
  if smaller, 
    - we add the max height of other index to max height of current index. if this sum is greater than current max, we update.
  if not smaller, we ignore.

array - [2,2,1][2,1,2][3,2,3][2,3,4][4,4,5][2,2,8]

maxHeight - initialized - [1,2,3,4,5,8]

[1,2,3,4,5,8]
         6
		 7
		 10
		 9 - 9 is small than 10 , so 10 stays.

  currentDisk = array[i] for 0<= i <length
  otherDisk = array[j]  for 0<= j < i
  
  if ( otherDisk.width < currentDisk.width && otherDisk.depth < currentDisk.depth && otherDisk.height < currentDisk.height)
		maxHeight[i] = Max( maxHeight[i] , maxHeight[j] + currentDisk.height)

Using this we can form the maxHeight array, we need tp pick the max value in this array.

we can have sequence array to track the disks which are added.
in every position i of the sequence array, we have index of the previous disk which was added on top of it.
time - O(N^2)
space - O(N)

import java.util.*;

class Program {
  public static List<Integer[]> diskStacking(List<Integer[]> disks) {
    int[] max = new int[disks.size()];
		int[] seq = new int[disks.size()];
		int maxIndex = 0;
		
		Arrays.fill(seq,Integer.MIN_VALUE);
		Collections.sort(disks,(a,b)->a[2].compareTo(b[2]));
		for(int i=0; i<max.length;i++){			
			max[i] = disks.get(i)[2];
		}
		for(int i=1; i<disks.size(); i++){
			Integer[] current = disks.get(i);
			for(int j=0; j<i;j++){
				Integer[] mutated = disks.get(j);
				if(current[0] > mutated[0]
					 && current[1] > mutated[1] && current[2] > mutated[2]){
						 if(current[2] + max[j] >= max[i]){  // this extra check is need to fix the seq array. else its breaking
							 // math.max will provide right result for max[] but seq[] is not maintained
					   max[i] = current[2] + max[j];
					   seq[i] = j;
						 }
				}
			}
			if(max[i]>max[maxIndex]){
				maxIndex = i;
			}
			
		}
		System.out.println(maxIndex + "---    ");
    return buildSequ(disks,seq,maxIndex);
  }
	private static List<Integer[]> buildSequ(List<Integer[]> disks, int[] seq, int max){
		List<Integer[]> op = new ArrayList<Integer[]>();
		
		while(max!=Integer.MIN_VALUE){
			System.out.println(max);
			op.add(disks.get(max));
			max = seq[max];
		}
		Collections.reverse(op);
		return op;
	}
}

=======================================================================================================================================================================	
	
Max Profit with k Transactions
given array of positive int, representing prices of stocks in different days, and max number of transactions allowed to make using them. One transaction refers to buying a stock on a day and selling on another later day. find max profit which can be made.

we can hold only one stock at a time. 

prices[]  - [5 , 11, 3, 50 , 60 , 90]
k = 2

We represent this as a 2D array, profit[]

   5  11  3  50  60  90
0  0   0  0   0   0   0
1  0   6  6  47  57  87
2  0   6  6  53  63  93

for any day, we can either sell a stock or not. 
if we dont sell, then the max profit will be the max profit on previous day. we can get this using profit[]
now if we sell it, we need to take the ( price of current day from prices []  + Max ( - price of [x] + profit[t-1][x]) ) where 0 <= x < d

profit[t][d]  = Max ( profit[t][d-1] (no transaction on day) ,  prices[d] + Max(-prices[x] + profit[t-1][x]) (transaction made ) )) , 0<=x<d

 90 + 
 x=0: -5+0 = -5
 x=1: -11+6=-5
 x=2: -3+6=3
 x=3: -50+47 = -3
 x=4: -60+57=-3
 
 Here 3 is the max, so 90+3 = 93.
 
 time : O(N^2 * k) - for forming the 2d matrix it takes NK. but for each cell, we perform another O(n^2) operation to calculate max of each of the value. 
 space : O(nk)
 
 Now can we do better in time complexity ?
 try to see if we are doing any calculation multiple times.
 We calculated untill x=4 for 93. what would have we done for its previous 63 ? 
 we would have done untill x=3 to get it. so we had been doing this same calculation multiple times.
 x=0: -5+0 = -5
 x=1: -11+6=-5
 x=2: -3+6=3
 x=3: -50+47 = -3
 Max would have been 3. so we took 60+3 = 63.
 we could have stored the greates max untill every cell. so by the time we had calculated for 63, greatest max would have been 3.
 Now we just calculate for x=4. when we get to 93, we get max as -> -3, we then take max of (3,-3) here which is 3. 
 we can reduce the no of operations. we have changed he O(N) into a O(1) operation.
 
 time O(NK)
 space O(NK)
 
 for(int i=1; i<=k;i++){
			int maxThisFar = Integer.MIN_VALUE;
			for(int j=1; j<prices.length;j++){				
					maxThisFar = Math.max(maxThisFar,  matrix[i-1][j-1] - prices[j-1]);
					matrix[i][j] = Math.max(matrix[i][j-1] , maxThisFar + prices[j]);				
			}			
		}
		
 can we reduce space complexity ? yes, we could have just used the last 2 rows instead of holding the whole matrix.
 
 time O(NK)
 space O(N) - 2 rows of N length
 
 
 import java.util.*;

class Program {
  public static int maxProfitWithKTransactions(int[] prices, int k) {
		if(prices.length==0){
			return 0;
		}
    int[][] matrix = new int[k+1][prices.length];
		
		for(int i=1; i<=k;i++){
			int maxThisFar = Integer.MIN_VALUE;
			for(int j=1; j<prices.length;j++){				
					maxThisFar = Math.max(maxThisFar,  matrix[i-1][j-1] - prices[j-1]);
					matrix[i][j] = Math.max(matrix[i][j-1] , maxThisFar + prices[j]);				
			}			
		}
		return matrix[k][prices.length-1];
  }
}

=======================================================================================================================================================================	

Palindrome partiotioning with Min cuts
Given non-empty string write a function that returns min no of cuts needed to perform on string, such that each remaining string is a palindrome.
noonabbad

noon abba d
2 cuts

create a 2d matrix, for each index in both row and column. 
1) now for every index, we do a palindrom check based on the row and column numbers. 
  - meaning, for index 0 - just n - its a palindrome
  - for index 1 in row 1, we start from 0 to 1, no - not a palindrome
  - for index 2 in row 1, we start from 0 to 2,noo - not a palindrome
  - for index 3 in row 1, we start from 0 to 3,noon - it is a palindrome
if its palindrome ,it strue, we put T. else we put F for false.
The diagonal will always be True. as for them, we start with that letter and end with that letter.single letter is palindrome. 
noonabbad
for second row, we start at index 1 of j and we do as if its the start of the string.
palindrome[]
   0 1 2 3 4 5 6 7 8
0  T F F T F F F F F
1    T T F F F F F F
2      T F F F F F F
3        T F F F F F
4          T F F T F   
5            T T F F
6              T F F
7                T F
8                  T

for 0<=i < length(string)
  for i<=j < length(string)
     palindrome[i][j] = isPalindrome(string[i:j+1])

now we form an array of same length of string, where each index holds the min number of cuts required, starting at index 0 until its index.
like - fist index will have min no of splits for string "n". The second index will be for string "no" ...
we initia with max value

cuts[]
[ max max max max max max max max max] 
we start at first string and see if its a palindrome.
[ 0 max max max max max max max max] 
is "no" a palindrome ? we can get this info from palindrome[]. 
 if its not a palindrome, the cuts will be cuts of earlier char + 1,
[ 0 1 max max max max max max max]  
now from now on, for each char, we see from 0th to ith position if its palindrome and get a value,
 then we start from 1st position to ith and see if its a palindrome and get a value. 
 now we compare it with earlier value and take min of it.
1) Lets take noo - we know noo is not a palindrome. so default value will be 1(prev value)+1 = 2.
2) can we form a palindrome somewhere in between this ?
3) if we take no | o - it will not form palindrome , but if we remove n like n | oo - we can reduce number of cuts via oo.
4) so it comes down to 1 cut. min(1,2) - 1.
[ 0 1 2 max max max max max max]  - we get 2 by start from 0
[ 0 1 1 max max max max max max]  - we get 1 by n | oo - so we choose min of these , which is 1.



time  : O(N^3)
  1) palindrome[] - we do a double for loop O(N^2), but for each computation, we do a string slice which is O(N) - O(N^3)
  2) cuts[] - we do O(n^2) in forming the min value for each index
space : O(N^2) - palindrome[] size

can we do better ?
in palindrome[] , every time we call the isPalindrome - which makes it N^3.
 in each entry, we can decide id next chars forms a palindrome based on previous values.
 for every string, if first and last are same, then we need to see if letters in middle form a palindrome.
 if we already have this info of letters in middle in matrix, we can use it. this is the approach.
	 

import java.util.*;

class Program {
  public static int palindromePartitioningMinCuts(String str) {
    boolean[][] matrix = new boolean[str.length()][str.length()];
		for(int i=0; i<matrix.length;i++){
			for(int j=i; j<matrix[0].length;j++){
				matrix[i][j] = isPalindrome(str,i,j);
			}
		}
		int[] mincuts = new int[str.length()];
	  // remember to initialize this with max
		Arrays.fill(mincuts,Integer.MAX_VALUE);
		for(int i=0; i<mincuts.length; i++){
			if(matrix[0][i]){
				mincuts[i]=0;
			}else{
				mincuts[i]= mincuts[i-1] + 1 ;
				for(int j=1; j<i; j++){
					// we start from j untill i, see if its palindrome
					if(matrix[j][i] && mincuts[j-1]+1 < mincuts[i]){
						mincuts[i]= mincuts[j-1] + 1 ;
					}	
				}				
			}
		}
		return mincuts[str.length()-1];
  }
	private static boolean isPalindrome(String str, int i, int j){
		while(i<j){	
		if(str.charAt(i)!=str.charAt(j)){
				return false;
			}else{
				i+=1;
				j-=1;
			}
		}
		return true;
	}
}

=======================================================================================================================================================================	
	
Longest Increasing Subsequence
return the longest increasing subsequence in array.
5, 7, -24, 12, 10, 2, 3, 12, 5, 6, 35


we use 2 arrays.

length[] - each index i, we store the length of the longest increasing subsequence that ends until with element in input[] at this index.
  - initialize the array to all 1
  - look all the numbers that come before the index i, if the number is less than element at i,
     - if smaller than i'th element, we can add it to count
	    - take the length[j] and add 1 to it, and see if its greater then length[i], if so update
		- update the sequence[] as well
	 - if smaller, ignore 
  - keep incrementing the count, we will finally store the count at the length'index
sequence[] - 
    - initialize to all None
	- at each index, we store the index of the input that comes before this index.

length -   [ 1 ,  2, 1,    3, 3, 2, 3, 4, 4, 5, 6 ]
sequence - [None, 0, None, 1, 1, 2, 5, 6, 6, 8 ,9 ]

  for 0<= i < length
    for 0<= j < i
	  if(input[j] < input[i])
	     length[i] = max( length[i] , length[i] + length[j])
		 sequence[i] = j
		 
   we can backtrack in sequence[] to find the input in the input[]
   
time : O(N^2) 
space : O(N) 

we can do better . time can become O (N log N)
we can apply binary search which id log N operation.
	
class Solution {
    public int lengthOfLIS(int[] nums) {
        if(nums.length==0)
            return 0;
        int[] maxArray = new int[nums.length];
        int max = 0;
        Arrays.fill(maxArray,1);
        int[] sequence = new int[nums.length];
        Arrays.fill(sequence,Integer.MIN_VALUE);
        for(int i=0; i<maxArray.length;i++){
            int currentElement = nums[i];
            for(int j=0; j<i;j++){
                int presentElement = nums[j];
                if(presentElement < currentElement && maxArray[j] + 1 > maxArray[i]){
                    maxArray[i] = maxArray[j] +1;
                    sequence[i] = j;
                }
            }
            if(maxArray[i] > maxArray[max]){
                max = i;
            }
        }
        int count = 0;
        while(max!=Integer.MIN_VALUE){
            count++;
            max = sequence[max];
        }
        return count;
    }
}	
