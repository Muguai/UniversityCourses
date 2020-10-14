package alda.graph;

public class Edge<T extends Comparable<T>> implements Comparable<Edge<T>>{

	  private final Vertex to, from;
      private int cost;
	  public Edge(Vertex to, Vertex from, int cost) {
	    this.to = to;
	    this.from = from;
	    this.cost = cost;
	  } 
	  public int getCost() {
          return cost;
      }
	  public Vertex getFromVertex() {
          return from;
      }

      public Vertex getToVertex() {
          return to;
      }
	@Override
	public int compareTo(Edge<T> e) {
        if (this.cost < e.cost)
            return -1;
        if (this.cost > e.cost)
            return 1;

        final int from = this.from.compareTo(e.from);
        if (from != 0)
            return from;

        final int to = this.to.compareTo(e.to);
        if (to != 0)
            return to;

        return 0;
    }
	
	} 