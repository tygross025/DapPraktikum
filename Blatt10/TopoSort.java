package Blatt10;


import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class TopoSort {

    static boolean dfs(List<Node> solution, Node currentNode, Mark[] marked) {
        int nodeID = currentNode.getId();
        switch (marked[nodeID]) {
            case TEMPORARY:
                return false;

            case PERMANENT:
                return true;

            default:
                marked[nodeID] = Mark.TEMPORARY;
                for (Edge e : currentNode.getAdjazenzliste()) {
                    if (!dfs(solution, e.getDst(), marked)){
                        return false;
                    }
                }
                marked[nodeID] = Mark.PERMANENT;
                solution.add(0, currentNode);
                return true;
        }
    }

    static List<Node> topoSort(Graph g) {
        List<Node> solution = new LinkedList<>();
        Mark[] marked = new Mark[g.getN()];
        Arrays.fill(marked, Mark.UNMARKED);

        for (int i = 0; i < g.getN(); i++) {
            if (marked[i] == Mark.UNMARKED && !dfs(solution, g.getNode(i), marked)){
                return null;
            }
        }

        return solution;
    }

    public static void main(String[] args) {
        if (args == null || args.length != 1) {
            System.err.println("Required argument: String filepath");
            return;
        }

        //Graph von Datei einlesen
        Graph g;
        try {
            g = Graph.fromFile(args[0]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        //Berechnen und Ausgabe
        List<Node> solution = topoSort(g);

        if (solution == null) {
            System.out.println("Der Graph ist keine DAG.");
        } else {
            System.out.println("Topologische Sortierung: " + listToString(solution));
        }
    }

    private static String listToString(List<Node> solution) {
        StringBuilder s = new StringBuilder();
        ListIterator<Node> l = solution.listIterator();

        s.append("[");

        boolean first = true;
        while (l.hasNext()) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(l.next().getId());
        }

        s.append("]");

        return s.toString();
    }
}
