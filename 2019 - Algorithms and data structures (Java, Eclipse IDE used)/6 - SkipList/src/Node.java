

class Node<T extends Comparable<? super T>> {
    private Node<T> up = null;
    private Node<T> down = null;
    private Node<T> next = null;
    private Node<T> prev = null;
    private T data;

    Node(T data){ 
    	this.data = data;
    }

    public void setUp(Node<T> up){
    	this.up = up;
    }
    public void setDown(Node<T> down){
    	this.down = down;
    }
    public void setNext(Node<T> next)
    {
    	this.next = next;
    }
    public void setPrev(Node<T> prev){
    	this.prev = prev;
    }
   

    public T getData(){
    	return data;
    }        
    public Node<T> getUp(){
    	return up;
    }
    public Node<T> getDown(){
    	return down;
    }
    public Node<T> getNext(){
    	return next;
    }
    public Node<T> getPrev(){
    	return prev;
    }
   

    @Override
    public String toString(){ 
    	return (data == null ? "null" : data.toString()); 
    }
}