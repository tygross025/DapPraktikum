package Blatt9;

public class GraphApplication {
    public static void main(String[] args){
        Graph graph = Graph.fromFile(args[0]);
        System.out.println(graph.toString());
    }
}
