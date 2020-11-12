Stack

Min Max stack construction
 Construct a stack with basic push, pop, peek and also a getMin and getMax in constant time.
 We can use list to construct push, pop and peek. But for getMin and getMax we could use a minHeap and maxHeap. But it will be O( N log N) operation as we will add and remove as well. 
 So better approach woule be to have another list of the same size of the stack, and at each point of this list, it will have the current min and max of the current position of stack element.
 so when a new element is added, we compute new min and max based on this new and previous elements of list and create new entry, and when an element is removed, we remove the entry in this list.
 
import java.util.*;

class Program {
  // Feel free to add new properties and methods to the class.
	static class Pair{
		private int min;
		private int max;
		public Pair(int min, int max){
			this.min = min;
			this.max = max;
		}
		public int getMin(){
			return this.min;
		}
		public int getMax(){
			return this.max;
		}
	}
  static class MinMaxStack {
		List<Integer> list = new ArrayList<>();
		Queue<Integer> min = new PriorityQueue<>();		
		Queue<Integer> max = new PriorityQueue<>((a,b) -> Integer.compare(b,a));
		List<Pair> minMax = new ArrayList<>();
    public int peek() {      
      return list.get(list.size()-1);
    }

    public int pop() {
			int val = list.remove((int)list.size()-1);
			//min.remove(val);
			//max.remove(val);
			minMax.remove((int)minMax.size()-1);
			return val;
    }

    public void push(Integer number) {
    	list.add(number);
			//min.offer(number);
			//max.offer(number);
			if(minMax.size()==0){
				Pair p = new Pair(number, number);
				minMax.add(p);
			}else{
				Pair current = minMax.get(minMax.size()-1);
				int min = current.min < number ? current.min : number;
				int max = current.max > number ? current.max : number;
				Pair newPair = new Pair(min,max);
				minMax.add(newPair);
			}
    }

    public int getMin() {
			if(minMax.size()==0){
				return 0;
			}
			Pair current = minMax.get(minMax.size()-1);
      return current.min;
    }

    public int getMax() {
      //return max.peek();
			if(minMax.size()==0){
				return 0;
			}
			Pair current = minMax.get(minMax.size()-1);
      return current.max;
    }
  }
}
