package alda.hash;

public class DoubleHashingProbingHashTable<T> extends ProbingHashTable<T> {

	
	@Override
	protected int findPos(T x) {
		   int hash1 = myhash( x );
	        int hash2 = hash2( x );    
		while (continueProbing(hash1, x)) {
		 hash1 += hash2(x);
         hash1 %= capacity();
		}
		return hash1;
	}
	protected int hash2(T i2) {
		 int hashVal = i2.hashCode( );
		 int primeSize = smallerPrimeThanCapacity();
	        hashVal %= capacity();
	        if (hashVal < 0)
	            hashVal += capacity();
	        return primeSize - hashVal % primeSize;
	}
	protected int smallerPrimeThanCapacity() {
		int n = capacity() - 2;
		while (!isPrime(n)) {
			n -= 2;
		}
		return n;
	}

}