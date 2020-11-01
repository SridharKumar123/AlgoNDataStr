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

