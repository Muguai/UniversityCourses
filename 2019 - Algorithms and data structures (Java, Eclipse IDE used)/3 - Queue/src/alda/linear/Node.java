//Fredrik Hammar, frha2022
package alda.linear;

public class Node<E> {
	private E data;
	private Node<E> next;
	public E getData(){
		 if(data == null) {
	        	return null;
		}else {
				return data;
		}
	}
	public void setData(E data2){
		data = data2;
	}
	public Node<E> getNext(){
        if(next == null) {
        	return null;
		}else {
			return next;
		}
		
	}
	public void setNext(Node<E> next2){
		next = next2;
	}
	
}
