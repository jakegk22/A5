import java.util.*;


/**
 *  The <tt>EdgeWeightedGraph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  in the graph, iterate over all of the neighbors incident to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */


public class Graph {

    private final int V;
    private int E;
    private LinkedList<Edge>[] adj;
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private static final int INFINITY = Integer.MAX_VALUE;
    public ArrayList<Routes> finRoutes;
   /**
     * Create an empty edge-weighted graph with V vertices.
     */
    public Graph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;


        adj = (LinkedList<Edge>[])new LinkedList[V];
        for (int v = 0; v < V; v++) adj[v] = new LinkedList<Edge>();
    }

   /**
     * Create a random edge-weighted graph with V vertices and E edges.
     * The expected running time is proportional to V + E.
     */
    public Graph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            double weight = Math.round(100 * Math.random()) / 100.0;
            Edge e = new Edge(v, w, weight,0);
            //addEdge(e);
        }
    }

   /**
     * Create a weighted graph from input stream.
     */

    /*
    public Graph(Scanner in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Double cost = in.readDouble();
            Edge e = new Edge(v, w, weight,cost);
            addEdge(e);
        }
    }
    /*

   /**
     * Return the number of vertices in this graph.
     */

     
    public int V() {
        return V;
    }

   /**
     * Return the number of edges in this graph.
     */
    public int E() {
        return E;
    }


   /**
     * Add the edge e to this graph.
     */

     
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }


   /**
     * Return the edges incident to vertex v as an Iterable.
     * To iterate over the edges incident to vertex v, use foreach notation:
     * <tt>for (Edge e : graph.adj(v))</tt>.
     */

     
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }
    

   /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges, use foreach notation:
     * <tt>for (Edge e : graph.edges())</tt>.
     */

     /*
    public Iterable<Edge> edges() {
        Edge list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }
    */
    
    public void bfs(int start, int end, double maxCost, double maxHops,LinkedList<Edge>[] routes){
        marked = new boolean[this.V];
        distTo = new int[this.E];
        edgeTo = new int[this.V];
  
        Queue<Integer> q = new LinkedList<Integer>();
        for (int i = 0; i < this.V; i++){
          distTo[i] = INFINITY;
          marked[i] = false;
        }
        distTo[start] = 0;
        marked[start] = true;
        q.add(start);
  
        while (!q.isEmpty()) {
          int v = q.remove();
          for (Edge e : adj(v)) {
            if (!marked[e.other(v)]) {
              edgeTo[e.other(v)] = v;
              distTo[e.other(v)] = distTo[v] + 1;
              marked[e.other(v)] = true;
              q.add(e.other(v));
              if(v==end){
                  //if()
                  
              }
            }
          }
        }
    }

    public void bfs(int startV, int endV, Double maxCost, int maxHops) {
    }

    public void DepthFirstPaths(int startV,int end, double maxCost, double maxHops,LinkedList<Edge>[] routes,LinkedList<Edge> edges) {
        //this.s = s;
        marked=new boolean[V];
        finRoutes = new ArrayList<Routes>();
        dfs(startV, end, maxCost, maxHops, routes, 0, 0, 0,0,edges);
        //dfs(G, startV);
    }

    // depth first search from v
    public void dfs(int startV,int end, double maxCost, double maxHops,LinkedList<Edge>[] routes,double cost,double distance,double hops,int totRoutes,LinkedList<Edge>edges) {
        marked[startV] = true;
        if(startV == end){
            Routes newRo = new Routes(hops,cost,distance, edges);
            finRoutes.add(newRo);
            return;       
        }

        if(hops<maxHops){
            for (Edge w : adj(startV)) {
                //System.out.println(w.toString());
                //if(startV==w.other(w.either()))
                int e = w.other(w.either());
                int v =w.either();
                int next;

                if(startV == e)
                    next=v;
                else next =e;
                if(!marked[next]) {
                    double nc = cost + w.cost();
                    if(nc<=maxCost){
                        hops++;
                        //routes[totRoutes].add(w);
                        edges.add(w);
                        //distance+=w.distance();
                        double nd = distance+w.distance();
                        //dfs(next,end,maxCost,maxHops,routes,cost,distance,hops,totRoutes,edges);
                        dfs(next,end,maxCost,maxHops,routes,nc,nd,hops,totRoutes,edges);
                        
                        marked[next]=false;
                        hops--;
                        //if(routes[totRoutes].size()>0)
                        //    routes[totRoutes].remove(routes[totRoutes].size()-1);
                        if(edges.size()>0)
                            edges.remove(edges.size()-1);
                    }
                    //edgeTo[w.either()] = startV;
                    //dfs( startV);
                    
                }
            }
        }
        
    }

    // is there a path between s and v?
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /* // return a path between s to v; null if no such path
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }*/

    /**
         * Return a string representation of this graph.
         */
        /*
        public String toString() {
            String NEWLINE = System.getProperty("line.separator");
            StringBuilder s = new StringBuilder();
            s.append(V + " " + E + NEWLINE);
            for (int v = 0; v < V; v++) {
                s.append(v + ": ");
                for (Edge e : adj[v]) {
                    s.append(e + "  ");
                }
                s.append(NEWLINE);
            }
            return s.toString();
        }
        */


    /**
         * Test client.
         */
        
    /*
        public static void main(String[] args) {
            EdgeWeightedGraph G;

            if (args.length == 0) {
                // read graph from stdin
                G = new EdgeWeightedGraph(new In());
            }
            else {
                // random graph with V vertices and E edges, parallel edges allowed
                int V = Integer.parseInt(args[0]);
                int E = Integer.parseInt(args[1]);
                G = new EdgeWeightedGraph(V, E);
            }

            StdOut.println(G);
        }
    */

}
