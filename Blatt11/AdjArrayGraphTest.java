package Blatt11;

import Blatt9.Graph;

import java.io.IOException;

public class AdjArrayGraphTest {
    public static void main(String[] args){
        AdjArrayGraph graph;
        try {
            graph = AdjArrayGraph.fromFile(args[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Has HamiltonCycle: " + graph.hasHamiltonCycle());
        System.out.println(graph.toString());
    }
}
