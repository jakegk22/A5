import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
public class Airline{

    static private Scanner sc;   
    private String[] cities;
    public Graph G;
    public LinkedList<Edge>[] routes;
    public LinkedList<Edge> edges;
    public static void main(String[] args) throws FileNotFoundException {
        Airline air = new Airline();
        sc = new Scanner(System.in);
        String fileName = args[0];
        air.readGraph(fileName);
        int choice;
        //menu();
        while(true){
            try {
                choice = air.menu();
                switch(choice){
                    case 1:
                        air.showRoutes();
                        break;
                    case 2: 
                        choice=0;
                        air.routeFinder();
                        break;
                    case 3:
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invailid Option!\n");
                }
            } catch (Exception e) {
                //TODO: handle exception
                System.out.println("Invalid Option!\n");
            }
         
        }
    }







    public int menu(){
        System.out.println("*********************************\n");
        System.out.println("Please choose from the options below:\n");
        System.out.println("1.) List cities served and direct routes.");
        System.out.println("2.) Find a route .");
        System.out.println("3.) Quit program.");
        System.out.println("*********************************");

        int option = Integer.parseInt(sc.nextLine());
        return option;
    }

    private void readGraph(String fileName) throws FileNotFoundException{
        Scanner fs = new Scanner(new FileInputStream(fileName));
        int size = Integer.parseInt(fs.nextLine());
        cities = new String[size];
        G = new Graph(size);

        for(int i=0;i<size;i++){
            cities[i] = fs.nextLine();
        }

        while(fs.hasNext()){
            int v = fs.nextInt();
            int e = fs.nextInt();
            double dist = fs.nextDouble();
            double cost = fs.nextDouble();
            //G.addEdge(v--,e--,dist,cost);
            G.addEdge(new Edge(v,e,dist,cost));
            //G.addEdge(new Edge(e--,v--,dist,cost));
        }
        fs.close();

    }

    private void showRoutes() {
        System.out.println("\tCities Served\n");

        for(int i=0;i<G.V();i++){
            System.out.println(cities[i]);
        }
        System.out.println("");
        System.out.println("\tAll Available Routes\n");
        System.out.println("\nFrom\t\tTo\t\tCost\t\tDistance\n");
        for(int i=0;i<G.V();i++){
            //System.out.printf("%s : ",cities[i]);
            //System.out.print("");
            for(Edge e: G.adj(i)){
                //System.out.printf("%s ->\t",cities[i]);
                //System.out.printf("%s ->\t%s\t$%.2f\t\t%.2f miles\n",cities[i],cities[e.other(i)],e.cost(),e.distance());
                System.out.printf("%s -> %s , $%.2f , %.2f miles\n",cities[i],cities[e.other(i)],e.cost(),e.distance());
                //System.out.printf("%s -> ");
            }
            System.out.println();
        }
    }

    private void routeFinder() {
        String start="";
        String end="";
        int startV=0;
        int endV = 0;
        double maxCost=0;
        double maxHops=0;
        boolean flag = true;

        while(flag){
            System.out.println("Please enter you're starting City");
            start = sc.nextLine();
            startV = checkCity(start);
            //System.out.println(start);
            if(startV == -1) flag = false;
            else break;
            if(!flag) System.out.println("Please enter valid city");
            flag=true;

        }
        
        while(flag){
            System.out.println("Please enter you're end Destination");
            end = sc.nextLine();
            endV = checkCity(end);
            //System.out.println(end);
            if(endV ==-1)flag=false;
            else break;
            if(!flag) System.out.println("Please enter valid city");
            flag=true;
        }
       
        //System.out.println(end);
        while(flag){
            System.out.println("Maximum Cost? (>0)");
            maxCost = sc.nextDouble();
            if(maxCost<=0) flag =false;
            else break;
            if(!flag) System.out.println("Please enter a number greater than 0");
            flag = true;
        }
        
        while(flag){
            System.out.println("Maximum amount of hops? (>0)");
            maxHops = sc.nextDouble();
            if(maxHops<=0) flag =false;
            else break;
            if(!flag) System.out.println("Please enter a number greater than 0");
            flag = true;
        }
       
        //routes = (LinkedList<Edge>[]) new LinkedList[G.V()];
        edges = new LinkedList<Edge>();
        G.DepthFirstPaths(startV,endV,maxCost,maxHops,routes,edges);
        
        if(G.finRoutes.size()<=0)
            System.out.println("No routes found for " + start + " to " + end + " that meet your criteria");
        else{
            System.out.println( G.finRoutes.size() +" Route(s) found that are less than $" + maxCost+ " and less than " + maxHops + " hops\n");
            System.out.println("\nHow would you like to view these paths?");
            System.out.println("1) Ordered by hops (fewest to most)");
            System.out.println("2) Ordered by cost (least expensive to most expensive)");
            System.out.println("3) Ordered by distance (shortest overall to longest overall)");
            System.out.println("");
            try {
                int option = sc.nextInt();
                switch(option){
                    case 1:
                        System.out.println("Displying flights by hops ");
                        minHops comp1 = new minHops();
                        Collections.sort(G.finRoutes,comp1);
                        System.out.println(G.finRoutes.toString());
                        break;
                    case 2:
                        System.out.println("Displying flights by cost");
                        minCost comp2 = new minCost();
                        Collections.sort(G.finRoutes,comp2);
                        System.out.println(G.finRoutes.toString());
                        break;
                    case 3:
                        System.out.println("Displying flights by distance");
                        minDistance comp = new minDistance();
                        Collections.sort(G.finRoutes,comp);
                        System.out.println(G.finRoutes.toString());
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                     
                }
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        //System.out.println(G.finRoutes.toString());    
        


    }

    private int checkCity(String city){
        for(int i=0;i<G.V();i++){
            if(cities[i].toLowerCase().equals(city.toLowerCase())){
                //System.out.println(i);
                return i;
            }
        }
        return -1;
    }

    private class minDistance implements Comparator<Routes>{
        public int compare(Routes first, Routes other){
            int comp = (int) (first.dist - other.dist);
            return comp;
        }
    }

    private class minCost implements Comparator<Routes>{
        public int compare(Routes first, Routes other){
            int comp = (int) (first.cost - other.cost);
            return comp;
        }
    }

    private class minHops implements Comparator<Routes>{
        public int compare(Routes first, Routes other){
            int comp = (int) (first.hops - other.hops);
            return comp;
        }
    }

}