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

// Given an input array [1,2,3] find the powerset of it - [[], [1], [2], [1, 2], [3], [1, 3], [2, 3], [1, 2, 3]]
// we start with [], then match it with 1 and we get [],[1]. we match this with 2 to get [],[1],[2],[1,2] and so on
// during the second iteration, we add in the same outer list, so we get its size before addinng new elements

List<List<Integer>> op = new ArrayList<>();
op.add(new ArrayList<>());
List<Integer> inner1 = new ArrayList<>();
		inner1.add(1);
		inner1.add(2);
		inner1.add(3);
subsets(inner1, op);

private static void subsets(List<Integer> inputs, List<List<Integer>> op) {

		for(int i=0; i<inputs.size();i++) {
			int size = op.size();
			for(int j=0; j<size;j++) {
				List<Integer> temp = new ArrayList<>(op.get(j));
				temp.add(inputs.get(i));
				op.add(temp);
			}			
		}		
	}

**************************************************************************************************************************************************************************
                                                                            5.  Interleaving Strings
**************************************************************************************************************************************************************************
//Given three strings A, B and C. checks whether C is an interleaving of A and B.
//C is said to be interleaving A and B, if it contains all characters of A and B and order of all characters in individual strings is preserved
//"aaa", "aaf", "aafaaa" - here we can form the third using first 2 without changing order.
// catch here is, if we start in first string and move on, once we reach the 3rd element, we see that it does not match.
// now we move one step back in this and one step forward in second element and check, if it matches we continue, else we move another step back in first 
// this process of back and forth is a costly operation as we might end up trying the same position again to see if it matches.
// first we have bruteforce approach

private static boolean interStrings(String first, String second, String main) {
		
		return interStrings(first, second, main,0,0,0);		
	}
	
private static boolean interStrings(String first, 
			String second, String main, int i, int j, int k) {
		if(i==first.length() && j==second.length())
			return true;
		
		if(k< main.length() && i < first.length() && main.charAt(k)==first.charAt(i)) {
			if(interStrings(first,second,main,i+1,j,k+1)) {
				return true;
			}
		}
		if(k< main.length() && j < second.length() && main.charAt(k)==second.charAt(j)) {
			return interStrings(first, second, main,i,j+1,k+1);
		}
		
		return false;	
	}

// for the same above solution, we apply memoization to make sure we donot travel to the same faulty position again

private static boolean interStringMemoization(String first, String second, String main) {
		
		Boolean [][] cache = new Boolean[first.length()][second.length()];
		return interStrings(first, second, main,0,0,0, cache);		
	}
	
private static boolean interStrings(String first, 
			String second, String main, int i, int j, int k, Boolean[][] cache) {
		if(i==first.length() && j==second.length())
			return true;
		
		if(cache[i][j]!=null)
			return cache[i][j];
		
		if(k< main.length() && i < first.length() && main.charAt(k)==first.charAt(i)) {
			cache[i][j] = interStrings(first,second,main,i+1,j,k+1);
			if(cache[i][j]) {
				return true;
			}
		}
		if(k< main.length() && j < second.length() && main.charAt(k)==second.charAt(j)) {
			cache[i][j] = interStrings(first, second, main,i,j+1,k+1);
			return cache[i][j];
		}
		
		return false;	
	}


