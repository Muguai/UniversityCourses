/*
* Mustafa Bay
* Maximilian Törnqvist
* Fredrik Hammar
*/

/*
* A datastructure that contains different search algorithms.
* A tank holds one object of this class to simplify testing between different solutions to search problems
*
* @author Mustafa Bay
*/
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Search {
    // Default constructor
    public Search() {
    }
    
    public Nod<PVector, String> dfs(GeneralProblem problem) {
      Stack<Nod> frontier = new Stack<Nod>();
        HashSet<Nod> explored = new HashSet<Nod>();
        
        Nod<PVector, String> node = new Nod(problem.getInitialState());
        if (problem.testGoal(node.getState())) {
            return node;
        }
        frontier.add(new Nod(problem.getInitialState()));

        while(!frontier.isEmpty()) {
          Nod parent = frontier.pop();
           if (problem.testGoal(parent.getState())) {
                 System.out.println("solution found");
                 return parent;
             }
           explored.add(parent);
           for (Object o : problem.getActions(parent.getState())) {
                String a = (String) o;
                Nod<PVector, String> child = parent.childNode(problem, o);

              if(!explored.contains(child)) {

               frontier.add(child);
              }
            
           }

        }
      return node;
    }
    
    /**
    * An implementation of Breadth-First Search
    * Starts traversing from the initial state formulated in the problem formulation
    * and "explores all of neighbors nodes at the present depth prior to moving on to
    * the nodes at the next depth level" (Wikipedia)
    *
    * Returns the Nod correspoding to the goal state of the problem.
    *
    * @param problem the problem formulation of the agent that is calling this method
    * @return Nod corresponding to the goal state of the problem
    */
    public Nod<PVector, String> bfs(GeneralProblem<PVector, String> problem) {
      println("started");
        Queue<Nod> frontier = new LinkedList<Nod>();
        HashSet<Nod> explored = new HashSet<Nod>();
        Nod<PVector, String> node = new Nod(problem.getInitialState());
        if (problem.testGoal(node.getState())) {
            return node;
        }
        println(node.getState());
        frontier.add(node);
        
        while(!frontier.isEmpty()) {
            node = frontier.poll();
            explored.add(node);
            for (Object o : problem.getActions(node.getState())) {
                String a = (String) o;
                Nod<PVector, String> child = node.childNode(problem, a);
                if (!frontier.contains(child) || !explored.contains(child)) {
                    println(child.getState());
                    if (problem.testGoal(child.getState())) {
                        println("solution found");
                        return child;
                    }
                    frontier.add(child);
                }
            }
        }
        return node;
    }
 
    /**
    * An implementation of A* Search
    * Starts traversing from the initial state formulated in the problem formulation
    * and "aims to find a path to the given goal node having the smallest cost.
    * It does this by maintaining a tree of paths originating at the start node and
    * extending those paths one edge at a time until its termination criterion is satisfied." (Wikipedia)
    *
    * f(n) = pathcost + manhattanDistance()
    *
    * Returns the Nod correspoding to the goal state of the problem.
    *
    * @param problem the problem formulation of the agent that is calling this method
    * @return Nod corresponding to the goal state of the problem
    */
    public Nod<PVector, String> aStarSearch(GeneralProblem<PVector, String> problem, PVector goal) {
        Nod<PVector, String> node = new Nod(problem.getInitialState());
        float childDist = 1;

        HashMap<Nod<PVector, String>, Nod<PVector, String>> parentMap = new HashMap<Nod<PVector, String>, Nod<PVector, String>>();
        HashMap<Nod<PVector, String>, Float> distances = new HashMap<Nod<PVector, String>, Float>();
        HashSet<Nod<PVector, String>> explored = new HashSet<Nod<PVector, String> >();
        PriorityQueue<Nod> frontier = initQueue();

        distances.put(node, node.getPathCost());
        frontier.add(node);

        while(!frontier.isEmpty()) {
            node = frontier.poll();

            if (!explored.contains(node)) {
                explored.add(node);
                if (problem.testGoal(node.getState())) {
                  println(node);
                  return node;
                }
                HashSet<Nod<PVector, String>> children = node.expand(problem);
                for (Nod<PVector, String> n : children) {
                    if (!explored.contains(n) && !frontier.contains(n))
                        frontier.add(n);
                    else if (frontier.contains(n)) {
                        float manDist = this.manhattanDistance(n.getState(), problem.getGoal());
                        float totalDist = n.getPathCost() + manDist;
                        if (totalDist < (node.getPathCost()+this.manhattanDistance(node.getState(),  problem.getGoal()))) {
                            frontier.remove(node);
                            frontier.add(n);
                        }
                    }
                }
            }
        }
        return node;
    }
    
    /*
    * Helper method to initialize PriorityQueue with path cost as the comparing field.
    */
    private PriorityQueue<Nod> initQueue() {
        return new PriorityQueue<>(10, new Comparator<Nod>() {
            public int compare(Nod x, Nod y) {
                if (x.getPathCost() < y.getPathCost()) {
                return -1;
                }     
                if (x.getPathCost() > y.getPathCost()){
                    return 1;
                }
                return 0;
            };
        });
    }
    
    /* 
    * Heuristic function that returns the manhattan distance between two vectors
    *
    * @param x the first vector
    * @param y the second vector
    * @return  the manhattan distance between x and y
    */
    private float manhattanDistance(PVector x, PVector y) {
        float distance = Math.abs(x.x - y.x)
                + Math.abs(x.y - y.y);
        return distance;
    }
    
    /*
    * Learning Real-Time A* Search
    * Builds a map of the environment with problem.getActions(state) and problem.getResult()
    * then chooses the "apparently best" move according to its current cost estimates
    * (Russel & Norvig, 2016)
    *
    * Returns the "apparently best" action given its current state
    *
    * @param state the current state of the agent calling this method
    * @param problem the problem formulation of the agent that is calling this method
    * @return String the "apparently best" action from state
    */
    
    public String LRTA(PVector state, GeneralProblem<PVector, String> problem) {
        float minCost = 1000000000;
        Nod<PVector, String> initialNod = new Nod(state);
        String a = null;

        if (problem.testGoal(initialNod.getState()))
            return "stop"; // stop
        for (String b : problem.getActions(state)) {
            if (LRTACost(state, b, problem.getResult(state, b), problem) < minCost) {
                minCost = LRTACost(state, b, problem.getResult(state, b), problem);
                a = b;
            }
        }
        return a;
    }
    
    /*
    * Helper function to calculate the estimated cost to reach the goal through a neighbor.
    * "The estimated cost to reach the goal through a neighbor state is the cost to get to state plus the estimated
    * cost to get to a goal from there. c(s, a, state) + H(state) (Russel & Norvig, 2016)
    *
    * @param s current state
    * @param a an action which gets the agent from s to state
    * @param state the neighboring state
    * @param problem the problem formulation of the agent that is calling this method
    * @return r the estimated cost to reach the goal through state from s
    */ 
    public float LRTACost(PVector s, String a, PVector state, GeneralProblem<PVector, String> problem){
            float r = manhattanDistance(s, state) + manhattanDistance(state, problem.getGoal());
            return r;
        }
    
    // not used anymore

    class ParentWrapper<S, A> {
        private final S state;
        private final A action;
        private final int hash;

        private ParentWrapper(S state, A action) {
            this.state = state;
            this.action = action;
            this.hash = Objects.hash(this.state, this.action);
        }
        
        public ParentWrapper<S, A> of(S state, A action) {
            return new ParentWrapper(state, action);
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o instanceof ParentWrapper) {
                ParentWrapper other = (ParentWrapper) o;
                return Objects.equals(this.state, other.state) && 
                        Objects.equals(this.action, other.action);
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return hash;
        }

        public S getState() {
            return state;
        }

        public A getAction() {
            return action;
        }
    }
    
    /*
    * Learning Real-Time A* Search
    * Builds a map of the environment with problem.getActions(state) and problem.getResult()
    * then chooses the "apparently best" move according to its current cost estimates
    * (Russel & Norvig, 2016)
    *
    * Returns the "apparently best" action given its current state
    *
    * @param state the current state of the agent calling this method
    * @param problem the problem formulation of the agent that is calling this method
    * @return String the "apparently best" action from state
    */
    
    public PVector LRTAp(PVector state, GeneralProblem<PVector, PVector> problem, HashSet<PVector> visited) {
        float minCost = 1000000000;
        Nod<PVector, String> initialNod = new Nod(state);
        PVector a = null;

        if (problem.testGoal(initialNod.getState()))
            return null; // stop
        for (PVector b : problem.getActions(state)) {
            if (LRTACostp(state, b, problem.getResult(state, b), problem) < minCost && !visited.contains(b)) {
                minCost = LRTACostp(state, b, problem.getResult(state, b), problem);
                a = b;
            }
        }
        return a;
    }
    
    /*
    * Helper function to calculate the estimated cost to reach the goal through a neighbor.
    * "The estimated cost to reach the goal through a neighbor state is the cost to get to state plus the estimated
    * cost to get to a goal from there. c(s, a, state) + H(state) (Russel & Norvig, 2016)
    *
    * @param s current state
    * @param a an action which gets the agent from s to state
    * @param state the neighboring state
    * @param problem the problem formulation of the agent that is calling this method
    * @return r the estimated cost to reach the goal through state from s
    *
    * Since the movement is gridbased and the cost between each gridspace is the same
    * there is no need to to calculate the cost from s to state
    */ 
    public float LRTACostp(PVector s, PVector a, PVector state, GeneralProblem<PVector, PVector> problem){
            float r = manhattanDistance(state, problem.getGoal());
            return r;
        }
}
