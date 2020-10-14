package alda.graph;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	public List<Vertex> nodes = new ArrayList<Vertex>();
	public List<Edge> edges = new ArrayList<Edge>();
	int NodesCount = 0;
	int EdgeCount = 0;
	@Override
	public int getNumberOfNodes() {
		int i = 0;
		for(Vertex T : nodes) {
		i++;
		}
		return i;
	}

	@Override
	public int getNumberOfEdges() {
		List<Vertex> Findduplicates = new ArrayList<Vertex>();
		int i = 0;
		for(Vertex T : nodes) {
			 List<Vertex> Search = T.neighbors;	
			 for(Vertex d : Search) {
		    	 i++;
		     }
		}
		i /= 2;
		return i;
	}

	@Override
	public boolean add(T newNode) {   
		Vertex thisNode = new Vertex();
		thisNode.setData((Comparable) newNode);
		for(Vertex T : nodes) {
			if(thisNode.getData().equals(T.getData())) {
				System.out.print("equals");
				return false;
			}
		}
		nodes.add(thisNode);
		NodesCount++;
		return true;
	}

	@Override
	public boolean connect(T node1, T node2, int cost) {
		if(cost < 1) {
			return false;
		}
        int exist = 0;	   
//		for(Vertex N : nodes) {
//	    	if(N.getData().equals(node1) ||N.getData().equals(node2)) {
//	    		exist++;
//	    	}
//	    }
//		if(exist != 2) {
//			return false;
//		}
        System.out.println("this is the data that should get connected");
		
		System.out.println(node1);
		System.out.println(node2);
		int Index1 = getIndexofData(node1);
		if(Index1 + 1  == nodes.size() ){
			if(nodes.get(Index1).getData() != node1) {
				return false;
			}
		}
		int Index2 = getIndexofData(node2);
		if(Index2 + 1  == nodes.size() ){
			if(nodes.get(Index2).getData() != node2) {
				return false;
			}
		}
		if(isConnected(node1, node2)) {
			T data1 = (T) nodes.get(Index1).getData();
			T data2 = (T) nodes.get(Index2).getData();
			nodes.get(Index1).CostMap.remove(data2, cost);
			nodes.get(Index2).CostMap.remove(data1, cost);
			nodes.get(Index1).CostMap.put(data2, cost);
			nodes.get(Index2).CostMap.put(data1, cost);
			return true;
		}
		List<Vertex> Findduplicates = nodes.get(Index1).neighbors;
		for(Vertex N : Findduplicates) {
			if(N == nodes.get(Index2)) {
				return false;
			}
		}
		
		System.out.println("this is the data that gets connected");
	
		System.out.println(nodes.get(Index1).getData());
		System.out.println(nodes.get(Index2 ).getData());
		nodes.get(Index1).neighbors.add(nodes.get(Index2));
		nodes.get(Index2).neighbors.add(nodes.get(Index1));
		Vertex thisNode2 = nodes.get(Index2);
		Vertex thisNode1 = nodes.get(Index1);
		T data1 = (T) nodes.get(Index1).getData();
		T data2 = (T) nodes.get(Index2).getData();
		nodes.get(Index1).CostMap.put(data2, cost);
		nodes.get(Index2).CostMap.put(data1, cost);
		Edge E = new Edge(thisNode1, thisNode2, cost);
		Edge E2 = new Edge(thisNode2, thisNode1, cost);
		nodes.get(Index1).edges.add(E);
		nodes.get(Index2).edges.add(E2);
		edges.add(E);
	    
		return true;
	}

	@Override
	public boolean isConnected(T node1, T node2) {
		int Index1 = getIndexofData(node1);
//		int i = 0;
//		for (Vertex N : nodes) {
//			i++;
//		}
//		System.out.println("this is the size of the arraylist" + nodes.size());
//		System.out.println("this is maybe the size of the arraylist" + i);
		
		if(Index1 + 1 == nodes.size() ){
			if(nodes.get(Index1).getData() != node1) {
				return false;
			}
		}
	    boolean connected = nodes.get(Index1).isConnected((Comparable) node2);
	    return connected;		
	}
    public int getIndexofData(T node) {
    	int Index1 = -1;
    	for (Vertex N : nodes) {
	    	Index1++;
	    	if(N.getData().equals(node)) {
	    		break;
	    	}
	    }
    	return Index1;
    }
	@Override
	public int getCost(T node1, T node2) {
      int index1 = getIndexofData(node1);
      int index2 = getIndexofData(node2);
      if(isConnected(node1, node2)) {
    	  int cost = (Integer) nodes.get(index1).CostMap.get(nodes.get(index2).getData());
    	  return cost;
      }
	return -1;
	}

	@Override
	public List<T> depthFirstSearch(T start, T end) {
		int index1 = getIndexofData(start);
		int index2 = getIndexofData(end);
		Stack<Vertex> stack=new  Stack<Vertex>();
		nodes.get(index1).visited=true;
		stack.add(nodes.get(index1));
		ArrayList<T> delivery = new ArrayList<T>();
		while (!stack.isEmpty()){
			
			Vertex element=stack.pop();
			
			if (!element.getData().equals(start)) {
				boolean connected = false;
				int con = 0;
				while(!connected) {
			        if(!isConnected(delivery.get(delivery.size() - 1), (T) element.getData())) {
				        delivery.remove(delivery.get(delivery.size() - 1));
			        }else {
			        	connected = true;
			        }
				}
			}
			
			System.out.print(element.getData() + " ");
			if(element.equals(nodes.get(index2))){
				delivery.add((T) element.getData());
				break;	
				
			}
			delivery.add((T) element.getData());
			ArrayList<Vertex> neighbours=(ArrayList<Vertex>) element.neighbors;
//            for(Vertex N : neighbours) {
//                for(Vertex d : neighbours)
//				if(N.neighbors.size() == 1) {
//					
//				}
//			}
		
			for (int i = 0; i < neighbours.size(); i++) {
				Vertex n=neighbours.get(i);
	
				if(n!=null &&!n.visited)
				{
					System.out.println("fuck up 2");
					stack.add(n);
					n.visited=true;
				
                    
				}
				if(n.equals(nodes.get(index2))){
					break;
				}
			}
			
			
		}
		
		// Collections.reverse(delivery);
		return delivery;
	}

	@Override
	public List<T> breadthFirstSearch(T start, T end) {
		int index1 = getIndexofData(start);
		int index2 = getIndexofData(end);
		PriorityQueue<T> queue = new PriorityQueue<T>();
		ArrayList<T> delivery = new ArrayList<T>();
		nodes.get(index1).visited=true;
		queue.add((T) nodes.get(index1).getData());
//		List<List> depthlevels = new ArrayList<List>();
		while (!queue.isEmpty())
		{
 
			
			T element=queue.remove();
			
			if (!element.equals(start)) {
				boolean connected = false;
				int con = 0;
				while(!connected) {
					System.out.print("comparing" + delivery.get(delivery.size() - 1) + " to " + element);
			        
					if(!isConnected(delivery.get(delivery.size() - 1), (T) element)) {
				        delivery.remove(delivery.get(delivery.size() - 1));
			        }else {
			        	connected = true;
			        }
				}
			}
			System.out.print(element+ " ");
			delivery.add((T) element);
			if(element.equals(nodes.get(index2))){
				break;
			}
			int indexNode = getIndexofData(element);
			List<Vertex> neighbours=nodes.get(indexNode).neighbors;
//			List<Vertex> depth = new ArrayList<Vertex>();
			boolean found = false;
			for (int i = 0; i < neighbours.size(); i++) {
				
				Vertex n=neighbours.get(i);
				System.out.println("this is should get added " + n.getData());
				if(n!=null && !n.visited)
				{
				
					
					if(n.equals(nodes.get(index2))){
						delivery.add((T) n.getData());
						n.visited=true;
						found = true;
						break;
					}
					
					System.out.print(delivery);
					queue.add((T) n.getData());
					n.visited=true;
					
				}
				
				
			}
			if (found == true) {
				break;
			}
 
		}
		
		return delivery;
	}

	@Override
	public UndirectedGraph<T> minimumSpanningTree() {
		
		   
		  int cost = 0;

	      final Set<Vertex> unvisited = new HashSet(nodes);
	      UndirectedGraph<T> mst = new MyUndirectedGraph<>();
	      //  unvisited.addAll(graph.getVertices());
	      //  unvisited.remove(start); // O(1)
            nodes.get(0).visited = true;
            Vertex start = nodes.get(0);
	        List<Edge> path = new ArrayList<Edge>();
	        Queue<Edge> edgesAvailable = new PriorityQueue<Edge>();

	        Vertex N = start;
	        while (!unvisited.isEmpty()) {
	            // Add all edges to unvisited vertices
	        	ArrayList<Edge> neighbours = (ArrayList<Edge>) N.edges;
	            for (Edge e : neighbours) {
	                if (unvisited.contains(e.getToVertex())) {
	                	System.out.println(" this gets added ");
	                	System.out.println(e);
	                    edgesAvailable.add(e);
	                }
	            }

	            // Remove the lowest cost edge
	            System.out.println("this is my parade of Edges");
	            if(edgesAvailable.peek() != null) {
	            Edge e = edgesAvailable.remove();
	            mst.connect((T)e.getFromVertex(),(T)e.getToVertex(), e.getCost());
	            cost += e.getCost();
	            path.add(e);
	            N = (Vertex) e.getToVertex();
	            mst.add((T) N.getData());
	            unvisited.remove(N);
	            }
	          
	            
	           // O(1)

	          // O(1)
	        }
	       

	        return mst;
		
	}

}
