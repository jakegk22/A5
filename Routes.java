import java.util.*;

public class Routes {
    public LinkedList<Edge> edges;
    public double hops;
    public double dist;
    public double cost;

    public Routes(double hops,double cost, double dist,LinkedList<Edge> edges){
        this.edges = edges;
        this.hops = hops;
        this.dist = dist;
        this.cost = cost;
    }

    public Routes(int hops,int cost, int dist,LinkedList<Edge> edges){
        //this.edges = edges;
        this.hops = hops;
        this.dist = dist;
        this.cost = cost;
    }

    @Override
    public String toString() {
        String toString;
        toString = "|Cost: " + cost + " Distance: " + dist + "Number of Hops: " + hops+"|\n";
        return toString;
    }

}
