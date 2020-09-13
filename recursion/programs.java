**************************************************************************************************************************************************************************
                                                                            1.  Fibanocci Series
**************************************************************************************************************************************************************************
//1) Fibanocci Series
//Calculate nth fibanocci number. 
//fib series is 0 1 1 2 3 5 8 13 21 34
//here first number is 0, so we start with it and find the nth element

//Recursive approach - we use Memoization to improve the time complexity
//O(n) Time and O(n) space
private static int calFibRecursiveMemoization(int n, Map<Integer,Integer> cache) {
		if(n==2)
			return 1;
		if(n==1)
			return 0;
		
		if(cache.containsKey(n))
			return cache.get(n);
		else {
			int fibN = calFibRecursiveMemoization(n-1,cache) + calFibRecursiveMemoization(n-2, cache);
			cache.put(n, fibN);
			return fibN;
		}
	}
 
//Iterative approach - 
//O(n) time but O(1) space - we just need the last 2 fib to calculate the current fib
private static int calculateFibIterative(int n) {
		if(n==2)
			return 1;
		if(n==1)
			return 0;
		int[] lastTwoFib = {0,1};
		for(int i=3; i<=n; i++) {
			int currentFib = lastTwoFib[0] + lastTwoFib[1];
			lastTwoFib[0] = lastTwoFib[1];
			lastTwoFib[1] = currentFib;
		}
		return lastTwoFib[1];
	}

**************************************************************************************************************************************************************************
                                                                            2.  Product Sum
**************************************************************************************************************************************************************************

// Takes a special array and returns its product sum
// [1, 5,[7,-1], 3, [6, [-13, 8], 4]
// 1+ 5 + 2(7-1) +3 + 2(6 + 3(-13+8) + 4)
// for every inner array, we increment the multiplier value and multiply this value with its sum
// multiplier must be updated by one for each internal step and not ++. This ensures that once it returns to the previous stack call, it retains its old value

private static int productSum(List<Object> array, int multiplier) {
		if(array==null || array.size()==0)
			return 0;
		int sum = 0;		
		for(Object obj : array) {
			if(obj instanceof ArrayList<?>) {				
			sum +=productSum((List<Object>)obj, multiplier+1);
			}else {
				sum+= (int) obj;
			}
		}
		sum = sum * multiplier;
		return sum;
	}

**************************************************************************************************************************************************************************
                                                                            3.  Permutation
**************************************************************************************************************************************************************************

// take an array of integers and return array of permutations 
//Upper Max : //T O(n^2 * n!)  S O(n * n!)
//T O(n * n!)  S O(n * n!)
private void permWithoutSwap(List<Integer> input, List<Integer> permutation, List<List<Integer>> output) {
		if(input.size()==0) {
			output.add(new ArrayList<>(permutation));
		}else {
			for(int i=0; i<input.size();i++) {
				List<Integer> removedHalf = new ArrayList<>(input);
				removedHalf.remove(i);
				List<Integer> newPerm = new ArrayList<>(permutation);
				newPerm.add(input.get(i));
				permWithoutSwap(removedHalf, newPerm, output);
			}
		}
	}

// the below is permutation using swap
//T O(n * n!)  S O(n * n!)
private void perm(int index, List<Integer> input, List<List<Integer>> output) {
		if(index==input.size()-1)
			output.add(new ArrayList<>(input));
		else {
			for(int j=index; j<input.size(); j++) {
				swap(index,j,input);
				perm(index+1,input,output);
				swap(index,j,input);				
			}
		}		
	} 
	
	private void swap(int i, int j, List<Integer> input) {
		int temp = input.get(i);
		input.set(i,input.get(j));
		input.set(j, temp);
	}
**************************************************************************************************************************************************************************
                                                                            4.  Subsets
**************************************************************************************************************************************************************************


**************************************************************************************************************************************************************************
                                                                            5.  Interleaving Strings
**************************************************************************************************************************************************************************


