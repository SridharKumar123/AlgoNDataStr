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

