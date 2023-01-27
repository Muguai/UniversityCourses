package alda.hash;

import java.util.Arrays;


public abstract class ProbingHashTable<AnyType> {

	public ProbingHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}


	public ProbingHashTable(int size) {
		if (size < MINIMUM_TABLE_SIZE)
			size = MINIMUM_TABLE_SIZE;
		allocateArray(size);
		doClear();
	}
	public int GetArrayLenght() {
		return array.length;
	}

	
	public boolean insert(AnyType x) {
		// Insert x as active
		int currentPos = findPos(x);
		if (isActive(currentPos))
			return false;

		if (array[currentPos] == null)
			++occupied;
		array[currentPos] = new HashEntry<>(x, true);
		theSize++;

		if (occupied > array.length / 2)
			rehash();

		return true;
	}


	private void rehash() {
		HashEntry<AnyType>[] oldArray = array;

		// Create a new double-sized, empty table
		allocateArray(2 * oldArray.length);
		occupied = 0;
		theSize = 0;

		// Copy table over
		for (HashEntry<AnyType> entry : oldArray)
			if (entry != null && entry.isActive)
				insert(entry.element);
	}

	protected boolean continueProbing(int currentPos, AnyType x) {
		return array[currentPos] != null && !array[currentPos].element.equals(x);
	}


	protected abstract int findPos(AnyType x);


	public boolean remove(AnyType x) {
		int currentPos = findPos(x);
		if (isActive(currentPos)) {
			array[currentPos].isActive = false;
			theSize--;
			return true;
		} else
			return false;
	}

	
	public int size() {
		return theSize;
	}

	public int capacity() {
		return array.length;
	}

	public boolean contains(AnyType x) {
		int currentPos = findPos(x);

//		System.out.println("this is a current pos");
//		System.out.println(currentPos);
		return isActive(currentPos);
	}

	protected boolean isActive(int currentPos) {
		return array[currentPos] != null && array[currentPos].isActive;
	}
	protected HashEntry<AnyType>[] getArray() {
		return  array;
	}
	
	public void makeEmpty() {
		doClear();
	}

	private void doClear() {
		occupied = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	protected int myhash(AnyType x) {
		int hashVal = x.hashCode();

		hashVal %= array.length;
		if (hashVal < 0)
			hashVal += array.length;

		return hashVal;
	}
	

	@Override
	public String toString() {
		return Arrays.toString(array);
	}

	private static class HashEntry<AnyType> {
		public AnyType element; // the element
		public boolean isActive; // false if marked deleted

		public HashEntry(AnyType e) {
			this(e, true);
		}

		public HashEntry(AnyType e, boolean i) {
			element = e;
			isActive = i;
		}

		@Override
		public String toString() {
			return isActive ? element.toString() : "inactive";
		}
	}

	private static final int DEFAULT_TABLE_SIZE = 101;
	private static final int MINIMUM_TABLE_SIZE = 5;

	private HashEntry<AnyType>[] array; // The array of elements
	private int occupied; // The number of occupied cells
	private int theSize; // Current size

	private void allocateArray(int arraySize) {
		array = new HashEntry[nextPrime(arraySize)];
	}
	
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	protected static final boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}

}