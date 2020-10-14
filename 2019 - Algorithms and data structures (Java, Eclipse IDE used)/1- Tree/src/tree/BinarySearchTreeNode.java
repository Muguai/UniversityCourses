package tree;

import java.util.NoSuchElementException;


public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
			
		 int compareResult = data.compareTo( this.getdata() );
			
			if( compareResult < 0 ) {
				if(this.left != null) {

					 left.add(data);
					 return true;
				 }

				 System.out.print(data);
				 System.out.println("adding left");
			 this.left = new BinarySearchTreeNode<T>(data);
			 return true;
			}
			 else if( compareResult > 0 ) {
				 if(this.right != null) {
					 right.add(data);

					 return true;
				 }

				 System.out.print(data);

				 System.out.println("adding right");
				 this.right = new BinarySearchTreeNode<T>(data);
			 return true;
			 }
			else {
			 ; // Duplicate; do nothing
			}
			 return false;
	}

	private T findMin() {
		if(this == null) {
			return null;
		}
		if(left == null) {
			return data;
		}else {
			return left.findMin();
		}
	}
	
	private T getdata() {
		return data;
	}
	private BinarySearchTreeNode<T> getLeft() {
		return left;
	}

	public BinarySearchTreeNode<T> remove(T data) {
		if( this == null ) {
			 return this; // Item not found; do nothing
		}
			 int compareResult = data.compareTo( this.getdata() );
			
			 if( compareResult < 0 )
			 this.left = this.left.remove( data);
			 else if( compareResult > 0 )
			 this.right = this.right.remove(data);
			 else if( this.left != null && this.right != null ) // Two children
			 {
			 setData(findMin());
			 this.right = this.right.remove( this.getdata());
			 }
			 else {
				 BinarySearchTreeNode<T> tmp = new BinarySearchTreeNode<T>(getdata());
				 tmp = this;
			     tmp = ( this.left != null ) ? this.left : this.right;
			     this.setData(tmp.getdata());
			     this.left = tmp.left;
			     this.right = tmp.right;
			 }
		return this;
		
		
	}
   

	public void setData(T d) {
    	data = d;
    }
	public boolean contains(T data) {
		if( this.getdata() == null )
			return false;
			
			 int compareResult = data.compareTo( this.data );
			
			 if( compareResult < 0 )
			 return contains( left.getdata());
			 else if( compareResult > 0 )
			 return contains( right.getdata());
			 else
			 return true; // Match
	}

	public int size() {
		int size = 1;
		if(left != null) {
			size += left.size();
	    } 
		if(right != null) {
			size += right.size();
	    }
		return size;
	}

	public int depth() {
		return -1;
	}

	public String toString() {
		return "";
	}
}