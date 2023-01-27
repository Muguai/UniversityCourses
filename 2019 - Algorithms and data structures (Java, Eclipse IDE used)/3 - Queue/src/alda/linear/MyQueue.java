//Fredrik Hammar, frha2022
package alda.linear;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyQueue<E> implements Iterable<E>, ALDAQueue<E> {
	private MyList<E> list;
	private int TotalCapacity = 10;
	private int CurrentCapacity;
	private String object;

	public MyQueue(int CAPACITY) {
		if (CAPACITY < 1) {
			throw new IllegalArgumentException(Integer.toString(CAPACITY));
		}
		TotalCapacity = CAPACITY;
		CurrentCapacity = TotalCapacity;
		list = new MyList<E>();
	}

	public void add(E element) {
		if (size() == TotalCapacity) {
			throw new IllegalStateException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (list.ShowHead() == null) {

			CurrentCapacity--;
			list.insertHead(element);
		} else {

			CurrentCapacity--;
			list.insertTail(element);
		}
	}

	public void addAll(Collection<? extends E> c) {
		if (c == null) {
			throw new NullPointerException();
		}
		int a = 0;
		for (E element : c) {
			if (size() == 0 && a == 0) {
				list.insertHead(element);
				CurrentCapacity--;
				a += 1;
			} else {
				list.insertTail(element);
				CurrentCapacity--;
			}

		}
	}

	public E remove() {
		Node<E> tmp = list.ShowHead();
		if (tmp == null) {
			throw new NoSuchElementException();
		} else {
			list.deleteHead();
			CurrentCapacity++;

		}
		return tmp.getData();
	}

	public E peek() {
		if (list.ShowHead() == null) {
			return null;
		} else {
			Node<E> tmp = list.ShowHead();
			return tmp.getData();
		}
	}

	public void clear() {
		for (E e : list) {
			remove();
		}
		CurrentCapacity = TotalCapacity;
	}

	public int size() {
		int Size = TotalCapacity - CurrentCapacity;
		return Size;
	}

	public boolean isEmpty() {
		if (CurrentCapacity == TotalCapacity) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFull() {
		if (size() == TotalCapacity) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Set when creating the queue.
	 */
	public int totalCapacity() {

		return TotalCapacity;

	}

	public int currentCapacity() {

		return CurrentCapacity;
	}

	public int discriminate(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		int AmountMoved = 0;
		MyList<E> tmp = list;

		MyList<E> tmp2 = new MyList<E>();
		MyList<E> tmp3 = new MyList<E>();
		int a = 0;
		for (E D : list) {
			if (tmp.ShowHead().getData() == e || tmp.ShowHead().getData().equals(e)) {
				tmp3.insertHead(tmp.ShowHead().getData());
				tmp.deleteHead();
			} else {
				if (a == 0) {

					tmp2.insertHead(tmp.ShowHead().getData());
					a += 1;
				} else {

					tmp2.insertTail(tmp.ShowHead().getData());
				}
				tmp.deleteHead();
			}
		}
		int tempSize = 0;
		for (E g : tmp2) {
			tempSize++;
		}
		int h = 0;
		for (E f : tmp3) {
			if (tempSize == 0 && h == 0) {
				tmp2.insertHead(tmp3.ShowHead().getData());
				h += 1;
			} else {
				tmp2.insertTail(tmp3.ShowHead().getData());
			}
			AmountMoved++;
		}
		list = tmp2;
		return AmountMoved;
	}

	public String toString() {
		object = list.stringTheList();
		if (size() == 0) {
			object = "[]";
		}
		return object;
	}

	@Override
	public Iterator<E> iterator() {
		return new MyListIterator<E>(list);
	}

	private static class MyListIterator<E> implements java.util.Iterator<E> {
		private MyList<E> list;
		private Node<E> n;
		private Node<E> before;
		boolean NextNode = true;
		private int a;

		public MyListIterator(MyList<E> theList) {
			list = theList;
			n = list.ShowHead();
		}

		public boolean hasNext() {
			if (!NextNode || n == null) {
				return false;
			} else if (n.getNext() == null) {
				NextNode = false;
				return true;
			} else {
				return true;
			}
		}

		@Override
		public E next() {

			a++;
			if (list.TheList() == 0) {
				throw new NoSuchElementException();
			}

			if (a > list.TheList()) {
				throw new NoSuchElementException();
			}

			if (n.getNext() == null) {
				if (list.ShowHead() == null) {
					throw new NoSuchElementException();
				} else {
					before = n;
					n = list.ShowHead();

					return before.getData();
				}
			} else {
				before = n;
				n = n.getNext();
				return before.getData();
			}

		}

	}

}

class MyList<E> implements Iterable<E> {

	private Node<E> head;

	public boolean isEmpty() {
		return (head == null);
	}

	public void insertHead(E data) {
		Node<E> changeNode = new Node<E>();
		changeNode.setData(data);
		changeNode.setNext(head);
		head = changeNode;
	}

	public Node<E> deleteHead() {
		Node<E> temp = head;
		head = head.getNext();
		if (head == null) {
		}
		return temp;
	}

	public Node<E> ShowHead() {
		return head;

	}

	public void insertTail(E data) {
		Node<E> current = head;
		while (current.getNext() != null) {
			current = current.getNext();
		}
		Node<E> changeNode = new Node<E>();
		changeNode.setData(data);
		current.setNext(changeNode);
	}

	public String stringTheList() {
		Node<E> current = head;
		String object = "[";
		while (current != null) {
			if (current.getNext() == null) {
				object += current.getData().toString();
			} else {
				object += current.getData().toString() + ", ";
			}
			current = current.getNext();
		}
		object += "]";
		return object;
	}

	public int TheList() {
		Node<E> current = head;
		int i = 0;
		while (current != null) {
			i++;
			current = current.getNext();
		}
		return i;
	}

	@Override
	public Iterator<E> iterator() {
		return new ThisListIterator<E>(this);
	}

	private static class ThisListIterator<E> implements java.util.Iterator<E> {
		private MyList<E> list;
		private Node<E> n;
		private Node<E> before;
		boolean NextNode = true;
		private int i;

		public ThisListIterator(MyList<E> theList) {
			list = theList;
			n = list.ShowHead();
		}

		public boolean hasNext() {
			if (!NextNode || n == null) {
				System.out.print("exit");
				return false;
			} else if (n.getNext() == null) {

				NextNode = false;
				return true;
			} else {

				return true;
			}
		}

		@Override
		public E next() {
			if (n.getNext() == null) {

				n = list.ShowHead();

			} else {
				before = n;
				n = n.getNext();
			}

			return n.getData();
		}

	}

}

