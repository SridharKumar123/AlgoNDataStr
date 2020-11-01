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
