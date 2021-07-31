
public class Edge implements Comparable<Edge> { 

    private final int v;
    private final int w;
    private final double distance;
    private final double cost;

   /**
     * Create an edge between v and w with given weight.
     */
    public Edge(int v, int w, double distance,double cost) {
        this.v = v;
        this.w = w;
        this.distance = distance;
        this.cost = cost;
    }

   /**
     * Return the distance of this edge.
     */
    public double distance() {
        return distance;
    }

   /**
     * Return the cost of this edge.
     */

    public double cost(){
        return cost;
    } 



   /**
     * Return either endpoint of this edge.
     */
    public int either() {
        return v;
    }

   /**
     * Return the endpoint of this edge that is different from the given vertex
     * (unless a self-loop).
     */
    public int other(int vertex) {
        if      (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Illegal endpoint");
    }

   /**
     * Compare edges by weight.
     */
    public int compareTo(Edge that) {
        if      (this.distance() < that.distance()) return -1;
        else if (this.distance() > that.distance()) return +1;
        else                                    return  0;
    }

   /**
     * Return a string representation of this edge.
     */
    public String toString() {
        return String.format("%d-%d %.2f %.2f", v, w, distance, cost);
    }


   /**
     * Test client.
     */
    public static void main(String[] args) {
        //Edge e = new Edge(12, 23, 3.14);
        //StdOut.println(e);
    }
}