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



