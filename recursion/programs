1) Fibanocci Series
Calculate nth fibanocci number. 
fib series is 0 1 1 2 3 5 8 13 21 34
here first number is 0, so we start with it and find the nth element

Recursive approach - we use Memoization to improve the time complexity
O(n) Time and O(n) space
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
 
Iterative approach - 
O(n) time but O(1) space - we just need the last 2 fib to calculate the current fib
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
