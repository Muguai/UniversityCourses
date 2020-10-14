


public class SkipList<T extends Comparable<T>> {

	private Node<T> head;
	private Node<T> tail;

	private RandomHeight rand;
	private int height;
	private int nodeAmount;

	SkipList(int MaxLevels) {
		nodeAmount = 0;
		height = 0;
		rand = new RandomHeight(MaxLevels);

		Node<T> negInfinity = new Node<>(null);
		Node<T> posInfinity = new Node<>(null);

		negInfinity.setNext(posInfinity);
		posInfinity.setPrev(negInfinity);

		head = negInfinity;
		tail = posInfinity;
	}

	public boolean isEmpty() {
		return nodeAmount == 0;
	}

	public int size() {
		return nodeAmount;
	}

	public int getHeight() {
		return height;
	}

	public boolean insert(T data) {
		// Checka om datan inlagd är null
		if (data == null) {
			throw new IllegalArgumentException("Kan ej ta in null");
		}

		Node<T> currentPosition = search(data);
		
		// Checka om datan är samma
		if (!currentPosition.toString().equals("null") && currentPosition.getData().compareTo(data) == 0) {
			return false;
		}

		Node<T> Insert = new Node<T>(data);

		Insert.setNext(currentPosition.getNext());
		Insert.setPrev(currentPosition);
		currentPosition.getNext().setPrev(Insert);
		currentPosition.setNext(Insert);
		int levels = rand.Levels();
		if (levels > 0) {
			// Bygg Levlar
			Node<T> previousPosition = currentPosition;
			int initHeight = height;
			int offset;

			while (height <= levels) {
				// Bygg en tom 
				Node<T> tempHead = new Node<T>(null);
				Node<T> tempTail = new Node<T>(null);
				tempHead.setNext(tempTail);
				tempTail.setPrev(tempHead);
				tempHead.setDown(head);
				tempTail.setDown(tail);
				head.setUp(tempHead);
				tail.setUp(tempTail);
				head = tempHead;
				tail = tempTail;
				height++;
			}

			if (levels < height) {
				initHeight = 0;
			}
			if (levels > initHeight) {
				offset = levels - initHeight;
			} else {
				offset = 1;
			}
			for (int counter = 0; counter < offset; counter++) {
				while (previousPosition.getUp() == null) {
					previousPosition = previousPosition.getPrev();
				}
				previousPosition = previousPosition.getUp();

				Node<T> towerNode = new Node<T>(Insert.getData());
				towerNode.setPrev(previousPosition);
				towerNode.setNext(previousPosition.getNext());
				previousPosition.getNext().setPrev(towerNode);
				previousPosition.setNext(towerNode);
				towerNode.setDown(Insert);
				Insert.setUp(towerNode);
				Insert = towerNode;
			}

		}
		nodeAmount++;
		return true;
	}
	
	public boolean contains(T data) {
		if (data.compareTo(search(data).getData()) == 0) {
			return true;
		}
		return false;
	}


	public boolean remove(T data) {
		if (!contains(data))
			return false;
		Node<T> OriginalcurPos = search(data);
		Node<T> currentPosition = search(data);
		Node<T> previousPosition = currentPosition.getPrev();
		while (true) {
			System.out.println("This is the next: " + currentPosition.getNext());
			System.out.println("This is the prev: " + previousPosition);
			if (!(currentPosition.getData() == OriginalcurPos.getData())) {
				//Vi är klara med bortagning
				System.out.println("lets break");
				break;
			}
			previousPosition.setNext(currentPosition.getNext());
			if (!(currentPosition.getNext() == null)) {
			currentPosition.getNext().setPrev(previousPosition);
			}
			System.out.println("this gets deleted" + currentPosition);
			currentPosition.setNext(null);
			currentPosition.setPrev(null);
			currentPosition.setUp(null);
			currentPosition = null;
			
			System.out.println("this gets uped" + previousPosition.getUp());
			System.out.println("this is the height " + height);
			// om heighten bara är 0 är vi redan klara med jobbet
			if(!(height > 0)) {
				break;
			}
			while (previousPosition.getUp() == null) {
				previousPosition = previousPosition.getPrev();
			}
			
			previousPosition = previousPosition.getUp();
			if (previousPosition == head && previousPosition.getNext() == tail) {
				break;
			}
			
			
			currentPosition = previousPosition.getNext();
			currentPosition.setDown(null);
			
			if (currentPosition.getNext() == null && previousPosition.getNext() == null) {
				break;
			}
		}
		nodeAmount--;
		
	    if (!(height == 0) && (head.getDown().getNext() == tail.getDown())) {
				//ta bort tomma levels
				Node<T> tempHead = head;
				Node<T> tempTail = tail;
				head = head.getDown();
				tail = tail.getDown();
				head.setUp(null);
				tail.setUp(null);
				tempHead.setDown(null);
				tempTail.setDown(null);
				tempHead.setNext(null);
				tempTail.setPrev(null);
				tempHead = null;
				tempTail = null;
		}
		
		return true;
	}
	
	private Node<T> search(T data) {
		Node<T> currentPosition = head;
		while (true) {
			while (!(currentPosition.getNext().toString().equals("null")) && currentPosition.getNext().getData().compareTo(data) <= 0) {
				currentPosition = currentPosition.getNext();
			}
			if (currentPosition.getDown() != null) {
				currentPosition = currentPosition.getDown();
			} else {
				break;
			}
		}
		return currentPosition;
	}
	@Override
	public String toString() {
		return printList().toString();
	}

	private StringBuilder printList() {
		StringBuilder sb = new StringBuilder();
		Node<T> currentPosition = head;
		while (currentPosition.getDown() != null) {
			currentPosition = currentPosition.getDown();
		}

		while (currentPosition != null) {
			Node<T> pos = currentPosition;
			sb.append(Column(pos));
			sb.append("\n");
			currentPosition = currentPosition.getNext();
		}
		return sb;
	}

	private String Column(Node<T> pos) {
		String row = " ";
		while (pos != null) {
			row = row + pos.toString() + " ";
			pos = pos.getUp();
		}
		return row;
	}

}