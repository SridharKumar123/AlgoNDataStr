Ceasar cipher encryptor

given a string and an int k , return a new string by shifting every letter by k positions in alphabet order
xyz - k=2

integer can be very huge, in that case we need to use modulo
unicode value - it takes in char and return its unicode value
lets say the range of unicode for a--z is 97 -- 122.
now if we just take unicode + k, it will not work for use case like z. 
as z value is 122. ==> 122+2 = 124 - this is not unicode for b. we have gone out of range.

lets say nLc as new Letter count,
1) we ensure that key is a value within 26. if key is large number our approach will result in val grt than 122
2) if nLc is less than 122, then we know we can directly find the char
3) if greater than 122, we need to take the modulo and add it to 96. so its like we start from beginning and find the char

key = key % 26
nLc = int(letter) + key
if(nLc <=122)
  return (char) nLc
else 
  return (char) (96 + nLc%122)  

time : O(N)
space : O(N) - op store  


 import java.util.*;

class Program {
  public static String caesarCypherEncryptor(String str, int key) {
    key = key % 26;
		//97   122
		StringBuilder builder = new StringBuilder();
		for(int i=0; i<str.length();i++){
			char current = str.charAt(i);
			int val = (int) current;
			int newCount = val + key;
			if(newCount <= 122){
				char charVal = (char)newCount;
				builder.append(String.valueOf(charVal));
			}else{
				int reminder = newCount - 122;
				int newVal = 96 + reminder;
				char charVal = (char)newVal;
				builder.append(String.valueOf(charVal));
			}
		}
		return builder.toString();
  }
}

=======================================================================================================================================================================

Longest Palindromic substring

given a string, return its longest palindromic substring

approach:
we can find all the substring using a double for loop - O(N^2)
 and within each loop, for each substring we call isPalindrome method which will turn to be O(N^3)

 approach:
 in a palindrome,
 - for odd number of chars, if we start from middle char, the left and right of it needs to be same....
 - for even, middle will be empty string and left and right chars needs to be same ...
 iterate through the string.
 at each point assume that it is the middle of the palindrome and check if its a palindrome.
 for palindrome check we hae 2 usecases,
  1) we assume this char as middle of palindrome
  2) we assume space between this as the middle of palindrome
 
time : O(N^2) - we iterate through array and at every point O(N), we keep checking for palindrome for each char. O(N^2)
space : O(N) - for storing the op. worst case the whole string can be a palindrome

import java.util.*;

class Program {
  public static String longestPalindromicSubstring(String str) {
    String longest = String.valueOf(str.charAt(0));
		for(int i=1; i<str.length();i++){			
				String oddCheck = checkPalindrome(i-1,i,str);
				if(oddCheck.length() > longest.length()){
					longest = oddCheck;
				}
				String evenCheck = checkPalindrome(i-1,i+1,str);
				if(evenCheck.length() > longest.length()){
					longest = evenCheck;
				}
			}
    return longest;
  }
	private static String checkPalindrome(int i, int j, String str){
		int left = i;
		int right = j;		
		while(left>=0 && right<str.length()){
			if(str.charAt(left) == str.charAt(right)){
				left--;
				right++;
			}else{
				break;
			}			
		}
		return str.substring(left+1, right);
	}
}

=======================================================================================================================================================================

Group Anagrams

take array of strings and group anagrams together.

anagrams are strings which have same chars but can be in diffeent order 
hi  == ih

["yo", "act", "flop", "tac", "foo", "cat", "oy", "olfp"]
yo - oy
flop - olfp
tac - cat - act
foo

approach
sort all the strings in ascending order. 
now we can know that any strings which are equal are anagrams.
add in hashtable based on its logic


iterate throguh the words, 
sort the letters in word
check if this is already in hashtable 
 - if we dont have in hashtable, add word to hashtable and add the real word within an array as value in hashtable
 - if we already have word in hashtable, append it to its anagrams group inside the value
 
time: O(w n logn) - 
      - we iterate through words - O(w)
	  - for each word we sort letters O(n log n) - where n is the length of the longest word
space : O(wn) - hashtable stores group of anagrams and words
		- w is no of words
		- where n is the length of the longest word	

import java.util.*;

class Program {
  public static List<List<String>> groupAnagrams(List<String> words) {
    Map<String, List<String>> map = new HashMap<>();
		List<List<String>> op = new ArrayList<>();
		for(String word : words){
			   char[] charArray = word.toCharArray();
			   Arrays.sort(charArray);
			   String sortedWord = new String(charArray);				 
			   if(map.containsKey(sortedWord)){
					 map.get(sortedWord).add(word);
				 }else{
					 List<String> input = new ArrayList<>();
					 input.add(word);
					 map.put(sortedWord, input);
				 }
		}
		for(Map.Entry<String,List<String>> current : map.entrySet()){
			op.add(current.getValue());
		}
    return op;
  }
}

=======================================================================================================================================================================

traverse the string 
for each letter, we store the last seen index of each letter in hashtable.
   - whenever we get to a letter, 
   - if letter is not in hashtable, we store the correspoding index of the letter, 
   - if letter was already there, we need to recompute the startIndex, we use the last stored index of that char to do this. 
        - if letter was there, means we hit a duplicate and we need to stop with the substring and recompute new start index
		- we need to pick the last seen position of this char and take its next char. so that we avoid the duplicate
   
   if char in hashtable
	   startIndex = max(startIndex, lastseen[char] + 1)
	   update the longestSubstring 
            
   input - clementisacap
   last seen
   c 0
   l 1
   e 2
   m 3
   
  
time : O(N) - N is length of string. we are just iterating the string and updating index
space : O(min (N , A)) - N is length of string . A is the length of unique alphabet that is represented in string

import java.util.*;

class Program {
  public static String longestSubstringWithoutDuplication(String str) {
    // create map and stire values of index of each char
		// maintain start index
		// if char is already present, then change start to index +1
		// longest is longest of start to current
		Map<Character, Integer> map = new HashMap<>();
		int startIndex = 0;
		String longestString = "";
		for(int i=0; i<str.length();i++){
			char c = str.charAt(i);
			if(map.containsKey(c)){				
				if(map.get(c) >= startIndex){
				startIndex = map.get(c) +1;
				}				
			}
			// to ensure that we count untill i, we do i+1
			String currentLongest = str.substring(startIndex,i+1);				
				if(currentLongest.length() > longestString.length()){
					longestString = currentLongest;
				}
			map.put(c,i);
		}
    return longestString;
  }
}
