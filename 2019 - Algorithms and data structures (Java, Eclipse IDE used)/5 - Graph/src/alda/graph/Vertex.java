package alda.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Vertex<T extends Comparable<T>> implements Comparable<Vertex<T>> {
	private T data;
	boolean visited = false;
	public List<Vertex> neighbors = new ArrayList<Vertex>();
	public List<Edge> edges = new ArrayList<Edge>();
	Map < T, Integer > CostMap = new TreeMap< T, Integer>();
	public void setData(T newNode){
		data = newNode;
	}
	public T getData() {
		return data;
	}
	public int Neighboursvisited() {
		for(Vertex N: neighbors) {
			if(N.visited = true) {
				
			}
		}
		return 0;
		
	}
	public boolean isConnected(T node2) {
		for(Vertex n : neighbors) {
			if(n.getData().equals(node2)) {
				return true;
			}
		}
		
		return false;
		
	}
	   @Override
       public int compareTo(Vertex<T> v) {
           final int valueComp = this.data.compareTo(v.data);
           if (valueComp != 0)
               return valueComp;

         

           if (this.edges.size() < v.edges.size())
               return -1;
           if (this.edges.size() > v.edges.size())
               return 1;

           final Iterator<Edge> iter1 = this.edges.iterator();
           final Iterator<Edge> iter2 = v.edges.iterator();
           while (iter1.hasNext() && iter2.hasNext()) {
               // Only checking the cost
               final Edge<T> e1 = iter1.next();
               final Edge<T> e2 = iter2.next();
               if (e1.getCost() < e2.getCost())
                   return -1;
               if (e1.getCost() > e2.getCost())
                   return 1;
           }

           return 0;
       }
}
