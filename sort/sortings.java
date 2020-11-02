Bubble sort:
sort elements in array in ascending order
iterate through the array multiple times, and each time we iterate, we will perform swaps to place numbers in right order.
 - first time we iterate, at any point, we check if the current number and number next to it are in right order.
 - is current number smaller than or equal to next one, 
    - if smaller, then we move to next number
	- if not smaller, we swap the numbers and move on to next number.
 - once we reach the end of the array, we check if we performed any swaps ? 
   - if yes, we have to redo the loop 
   - if no swap was done, we are done with sorting.
- after the first iter, the largets number will end up being the last. As even if it was in front, we would have automatically moved it to end.
- so after every iter, the last element would be right list ordered. meaning, after 1st , we will know the last is the max and rightly arranged. after 2nd loop, we know that the last but 1 is in right place. 
- so after very iter, we can avoid looping throguh the last numbers which are arranged rightly. This is a small optimization.

 Note: swap occurs in place. no new space is needed.
 
 8 5 2 9 5 6 3
after 1st iter =>  5 2 8 5 6 3 9 
after 2nd iter => 2 5 5 6 3 8 9
				  2 5 5 3 6 8 9
				  2 5 3 5 6 8 9
				  2 3 5 5 6 8 9
				  2 5 3 5 6 8 9 - no swap. so sorting is completed.

time: O(N^2) - we are looping through the array until it gets sorted. worst case it will be 2 for loops. Best case will be O(N) if the array is already sorted.
space : O(1)

class Program {
  public static int[] bubbleSort(int[] array) {
  	boolean isModified = false;
	int totalLength = array.length -1;
	while(!isModified){
	     isModified = true;
	     for(int i=0; i<totalLength;i++){
	        if(array[i] > array[i+1]){
		isModified = false;
		swap(i,i+1,array);
	     }
	}
	totalLength--;
	}
    return array;
  }
private static void swap(int i, int j , int[] array){
   int temp = array[i];
   array[i] = array[j];
   array[j] = temp;
  }
}

======================================================================================================================================================================
	
Insertion sort:

 8 5 2 9 5 6 3
 
 1) we have the sorted portion in the front of the list and compare the rest of the list to this and change the sorted set
 2) we make sure, that at any point in time, the front sorted portion is always sorted.
 3) lets say we start with 8. now 8 is the front portion, we compare 5 with 8 and swap as 5 is less than 8.
 4) now we have 58 as front portion, we compare 2 with 8 and swap, as 2 is smaller, then we compare 2 with 5 and swap
 5) it becomes 258 and we ensured that this portion is sorted.
we loop through array once, and at each position we loop back untill the front portion is sorted.

 time - O(N^2) - if array was already sorted, it would have been O(N) which is best case scenario
 space : O(1)
	 
import java.util.*;

class Program {
  public static int[] insertionSort(int[] array) {
    
		for(int i=1; i<array.length;i++){
			int j = i;
			while(j>=1 && array[j-1] > array[j]){
				swap(j-1,j,array);
				j--;
			}
		}
    return array;
  }
	
	private static void swap(int i, int j, int[] array){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

======================================================================================================================================================================

Selection Sort:
 
 8 5 2 9 5 6 3

 we divide it into 2 seperate lists within this list. one sublist represents unsorted numbers and other sublist represents sorted numbers.
 in begening, entire list represents unsorted numbers.
 1) we iterate list a bunch of times, and each time we find the smallest number in the list. 
 2) once we have the smallest number, we swap it to front and append it to the sorted set which is in front.
 3) we continue until unsorted sublist becomes empty list.
 
 time - O(N^2) - if array was already sorted, it would have been O(N) which is best case scenario
 space : O(1)
	 
import java.util.*;

class Program {
  public static int[] selectionSort(int[] array) {
    
	for(int i=0; i<array.length;i++){
		int smallest = array[i];
		int index = i;
		for(int j=i; j<array.length;j++){
			if(array[j] <= smallest){
			   smallest =	array[j];
				 index = j;
			}				
		}
		swap(i,index,array);
	}
    return array;
  }
	private static void swap(int i, int j, int[] array){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

======================================================================================================================================================================
	
Quick Sort:

we pick one value to be our pivot. This can be picked random or like first number in array.
we iterate through rest of the array, we sort every other number just with respect to the pivot.
 - every number smaller than pivot, will be moved to left of pivot. 
 - every number greater than pivot, will be moved to right of pivot. 
at the end of the iteration, pivot will be in the final sorted position in the array.
now we can pick the list left of pivot, and perform the above of picking a pivot and sorting.
the same applies to right of pivot. 
we do this untill entore array is sorted
we start with pivot as first number, we have a Left and Right pointers.
  	
 8 5 2 9 5 6 3
 P L         R
 we continue until r<l, 
 if num[l] > pivot && num[r] < pivot -> then we swap the 2 numbers. 
 if num[l] < pivot, l++  (as we know its in sorted portion)
 if num[r] > pivot, r--  (as we know its in sorted portion)
 8 5 2 9 5 6 3
 P   L       R
 
 8 5 2 9 5 6 3
 P     L     R
 we need to swap,
 8 5 2 3 5 6 9
 P     L     R
 
 8 5 2 3 5 6 9
 P         R
           L
 8 5 2 3 5 6 9
 P         R L
 we stop as R<L. Once R<L, we swap pivot with R.
 
 6 5 2 3 5 8 9
 P         R L
 8 here is perfectly sorted and in final sorted position. all numbers to left of 8 are smaller than it, and vice versa.
we continue the same on the lists to the left and right of pivot.
we pick the smaller sub array first. 
9 is only number to right. so its already sorted.
we pick numbers to left of 8 and start. 
 6 5 2 3 5 8 9
 P L     R 
 
time: 
worst case -O(N^2)-  after every iteration, if our array is split as 0 element on one sub array and rest all in other, for every loop we do, then we do O(N^2). meaning for every element, we do O(N)
best case -  O(N log N)   - what if pivot divides array exactly into half every time we do sort.
Average case - O( N log N) - mathematical proof is there.

space : O(log N) - we are applying recursion. frames on call stack will use space. 
   - so only we mentioned to apply sort first on smaller sub array. 
   - by applying on smaller of two, we know at most we make O(log N) calls at once. 
   - we call it on one sub array, then it will split into 2, then we call it on smaler of it,.. this continues. 
   - if we had called on big, then on small, we might end up with O(N) on call stack.

	   
import java.util.*;

class Program {
  public static int[] quickSort(int[] array) {
    sort(array, 0,array.length-1);
    return array;
  }
	
	private static void sort(int[] array, int start, int end){
		// if we have only one element, then we dont need to sort
		if(start>=end)
			return;
		int pivot = start;
		int left = start + 1;
		int right = end;
		while(right >= left){
			if(array[left] > array[pivot] && array[right] < array[pivot]){
				   swap(array,left,right);
			}
			if(array[left]<=array[pivot]){
				left++;
			}
			if(array[right] >= array[pivot]){
				right--;
			}
		}
		// we swap once left is larger than right.
		swap(array,pivot,right);
		// below 2 arrays are split using the right as middle
		// start -- right -- end
		// start -- right -1  ||  right+1  -- end
		// we first sort on smaller sub array to maintain O(log N) space
		boolean leftSubArraySmaller = (right - 1 - start) < (end - right + 1);
		if(leftSubArraySmaller){
			sort(array,start,right-1);
			sort(array,right+1,end);
		}else {			
			sort(array,right+1,end);
			sort(array,start,right-1);
		}
		
	}
	private static void swap(int[] array, int left, int right){
		int temp = array[left];
		array[left] = array[right];
		array[right] = temp;			
	}
}

======================================================================================================================================================================

Heap sort:
 divide array into 2 sub array
 1 array will be sorted always, other will be not sorted. we wont create 2 subarray
 in same array, we have 2 portions. sorted portion and unsorted portion.
 we need to move sorted portion to back, by removing from first portion
 
 1) implement max heap for the unsorted array. 
 2) start iterating from back,
    - pick an element and swap it with root 
    -  last element is biggest now and in right order.
 3) call siftdown for the first element. 
 4) continue untill the max heap becomes of size 1.
 5) finally, the array will be sorted.( as we are removing max element each time and appending from backwards)
 time - O(N logN) - we build max heap - O(N) - we do siftdown for N elements - nlogn = O(N) + O(Nlogn) = O(nlogn)
 space - O(1)
	 
min heap and mah heap

build:
// we call siftdown for all parents starting from the last parent
firstparentindex = (array.length-1)/2    - floor in java is default
  for( i= firstparentindex; i>=0 ; i--)
	siftdown(firstparentindex, len-1,  array)
return array 

sift up(index, list)
get parent index - i-1/2 floor
while list(parentindex)!=null && list(parentindex) > list(index)
		swap(parentindex, index)
		index = parentindex
		parentindex = index-1/2 floor

// pick the 2 child and get the one we need to swap.
sift down(index, endindex, list)
child1 = index*2 +1
while child1 index <= endindex 
	child2 = index*2 +2 <=endindex ? index*2 +2 : -1
	minchild = 0;
	if(child1val < child2val) 
	minchild = child1val
	else
	minchild = child2val
	if(list(index) > list(minchild)
		swap
		index = minchild
		child1 = minchild * 2+1
	else
	 return; 	
 
		
delete
swap root with last element
removed elem = pop from heap
sift down(0,end index, list)
return removed elem
 
insert
list.add - add in last 
sift up(new index, list)


parent i 
child 2i+1, 2i+2

child i 
parent i-1/2 floor


import java.util.*;

class Program {
  public static int[] heapSort(int[] array) {
  	// build max heap
	// swap and call sift down on each num, starting from back
	buildAMaxHeap(array);
	for(int i=array.length-1; i>0; i--){
		swap(0,i,array);
		siftDown(0,i-1,array);
	}
    return array;
  }
	
	private static void buildAMaxHeap(int[] array){
		int lastParentIndex = (array.length-2)/2;
		for(int i=lastParentIndex; i>=0; i--){
			siftDown(i, array.length-1,array);
		}
	}
	
	private static void siftDown(int startIndex, int endIndex, int[] array){
		int childOneIndex = startIndex * 2 +1;
		while(childOneIndex <= endIndex){
				int childTwoIndex = (startIndex * 2 + 2) <=endIndex ? (startIndex * 2 + 2) : -1;
				int indexToSwap;
			  if(childTwoIndex!=-1 && array[childTwoIndex] > array[childOneIndex]){
					indexToSwap = childTwoIndex;
				}else{
					indexToSwap = childOneIndex;
				}
			if(array[startIndex] < array[indexToSwap]){
				swap(startIndex,indexToSwap,array);
				startIndex = indexToSwap;
				childOneIndex = startIndex*2 +1;
			}else{
				return;
			}
		}		
	}
	
	private static void swap(int i, int j, int[] array){
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}

======================================================================================================================================================================
	   
Merge Sort:
Uses divide and conquer algorithm

        8 5 2 9 5 6 3
indes - 0 1 2 3 4 5 6

  mid = (0+6)/2 = 3
  
  [ 8 5 2 9]   [5 6 3]   - 2 sub arrays
  [ 8 5 ] [ 2 9]
  [ 8 ] [ 5 ] [ 2 ] [ 9 ]- base case where sub arrays of length 1. compare the numbers and sort them into single array
  [5,8] [ 2,9] - we know that both arrays are sorted. so we can compare the values and sort them easily
  [2,5,8,9] - we could have had 2 ptrs for 5 and 2 and compare their values and sort.
  -- we must have done same for other sub array and we need to merge them finally.
  
 - divides the array into 2 and conquers the two halves. 
 - continously divides subarrays and conquers them.
 1) we find the mid (start+end)/2 = mid
 2) we split the array based on the mid and form 2 subarrays
 3) we continue untill they become of size 1. we then start sorting them going forward. 
 4) sorting becomes easy now as both the arrays are sorted.

time :  O ( N log N) - we have logN number of splits for subarrays
  1) O(N) - we split the input array into 2 sub array copies
  2) O(N/2) = O(N) - we split the above sub array into 2 sub array copies
  ......
  n-1) O(N) - we merge the 2 sub arrays together.
  n) O(N) - we merge the 2 sub arrays together.
space : O( N log N) - we are always creating new sub arrays. 

import java.util.*;

class Program {
  public static int[] mergeSort(int[] array) {
		if(array.length <=1)
			return array;
		
		int mid = array.length/2;
		int[] left = Arrays.copyOfRange(array,0,mid);
		int[] right = Arrays.copyOfRange(array,mid,array.length);
		return sort(mergeSort(left),mergeSort(right));
  }
	
	private static int[] sort(int[] left, int[] right){
		int[] result = new int[left.length + right.length];
		int i=0;
		int j=0;
		int k=0;
		while(i < left.length && j<right.length){
			if(left[i] <= right[j]){
				result[k] = left[i];
				k++;
				i++;
			}else{
				result[k] = right[j];
				k++;
				j++;
			}
		}
		while(i < left.length){
			result[k] = left[i];
			k++;
			i++;
		}
		while(j < right.length){
			result[k] = right[j];
			k++;
			j++;
		}
		return result;
	}
}
