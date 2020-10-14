package alda.hash;

public class LinearProbingHashTable<T> extends ProbingHashTable<T> {

	
	@Override
	protected int findPos(T x) {

		int currentPos = myhash(x);

		    // search for the next available element or for the next matching key
		    while(continueProbing(currentPos, x)) {

		        currentPos = (currentPos + 1) % capacity();
		    }
		  return currentPos;
	}

}