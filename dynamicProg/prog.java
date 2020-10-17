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


