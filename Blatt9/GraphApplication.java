package Blatt9;

import java.io.IOException;

public class GraphApplication {
    public static void main(String[] args){
        Graph graph;
        try {
            graph = Graph.fromFile(args[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println(graph.toString());
    }
}
