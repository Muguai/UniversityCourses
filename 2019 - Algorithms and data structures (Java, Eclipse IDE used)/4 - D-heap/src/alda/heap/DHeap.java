// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
package alda.heap;

import java.util.Arrays;

public class DHeap<AnyType extends Comparable<? super AnyType>>
{
	private int currentSize = 0;
	private AnyType[] array;
	private static final int DEFAULTCAPACITY = 10;
	private int d;

	public DHeap() {
	    this(2, DEFAULTCAPACITY);
	}

	public DHeap(int children) {
		
	    this(children, DEFAULTCAPACITY);
	}

	@SuppressWarnings("unchecked")
	public DHeap(int children, int capacity) {
        if(children < 2) {
			throw new IllegalArgumentException();
		}
	    currentSize = 0;
	    d = children;
	    array = (AnyType[]) new Comparable[capacity + 1];
	}


	/**
	 * Inserts an element into the heap, placing it correctly according
	 * to heap properties.
	 * 
	 * @param element the element to insert.
	 * @throws IllegalArgumentException if the element to insert is null.
	 */
	public void insert(AnyType x) {
	    if (x == null)
	        throw new IllegalArgumentException("cannot insert null");

	    if (size() == array.length - 1)
	    	enlargeArray();

	    int hole = ++currentSize;
	    for( ; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)) {
	        array[hole] = array[parentIndex(hole)];
	    }
	    array[hole] = x;
	}

	/**
	 * Deletes the smallest element in the heap.
	 * 
	 * @return the smallest element in the heap.
	 * @throws IllegalStateException if the heap is empty.
	 */
	public AnyType deleteMin() {
	    if (isEmpty())
	        throw new IllegalStateException("Error: Empty heap");

	    AnyType minItem = findMin();
	    array[1] = array[currentSize--];
	    percolateDown(1);

	    return minItem;
	}

	/**
	 * Checks if the heap is empty or not.
	 * 
	 * @return true if the heap is empty, otherwise false.
	 */
	public AnyType findMin() {
	    if (isEmpty())
	        throw new IllegalStateException("Error: Empty heap");

	    return array[1];
	}

	private void percolateDown(int hole) {
	    int child = firstChildIndex(hole);
	    int tempChild = firstChildIndex(hole);
	    AnyType tempElement = array[hole];

	    for( ; firstChildIndex(hole) <= size(); hole = child) {
	        child = firstChildIndex(hole);
	        tempChild = firstChildIndex(hole);

	        for(int i = 0; i < d && tempChild != size(); i++, tempChild++){
	            if(array[tempChild + 1].compareTo(array[child]) < 0){
	                child = tempChild + 1;
	            }
	        }

	        if (array[child].compareTo(tempElement) < 0)
	            array[hole] = array[child];
	        else
	            break;
	    }
	    array[hole] = tempElement;
	}

	@SuppressWarnings("unchecked")
	private void enlargeArray() {
	    AnyType [] old = array;
	    array = (AnyType [])new Comparable[old.length * 2];
	    for (int i = 0; i < old.length; i++)
	        array[i] = old[i];
	}

	public boolean isEmpty() {
	    return size() == 0;
	}

	public void makeEmpty() {       
	    currentSize = 0;
	}

	public int size() {
	    return currentSize;
	}

	/**
	 * Finds the index of the first child for a given parent's index.
	 * This method is normally private, but is used to test the
	 * correctness of the heap.
	 * 
	 * @param parent the index of the parent.
	 * @return an integer with the index of the parent's first child.
	 */
	public int firstChildIndex(int parent) {
	    return d * (parent - 1) + 2;
	}

	/**
	 * Finds the index of a parent for a given child's index.
	 * This method is normally private, but is used to test
	 * the correctness of the heap.
	 * 
	 * @param child the index of the child.
	 * @return an integer with the child's parent's index.
	 */
	public int parentIndex(int child) {
	    return (child - 2)/d + 1;
	}

	public AnyType get(int index){ 
	    return array[index]; 
	    }
	

     // Test program
 public static void main( String [ ] args )
 {
     int numItems = 10000;
     DHeap<Integer> h = new DHeap<>( );
     int i = 37;

     for( i = 37; i != 0; i = ( i + 37 ) % numItems )
         h.insert( i );
     for( i = 1; i < numItems; i++ )
         if( h.deleteMin( ) != i )
             System.out.println( "Oops! " + i );
 }



}