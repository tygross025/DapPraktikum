package Blatt9;

import java.util.LinkedList;

public class Breitensuche {

    static int breitensuche(Graph g, int s, int t) {

        int[] distance = new int[g.getN()];
        LinkedList<Integer> queue = new LinkedList<>();

        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[s] = 0;
        queue.add(s);

        while (!queue.isEmpty()){
            int idV = queue.removeFirst();
            LinkedList<Edge> neighbors = g.getNode(idV).getAdjazenzliste();

            for (Edge e : neighbors) {
                int idU = e.getDst().getId();

                if (distance[idU] == Integer.MAX_VALUE) {
                    distance[idU] = distance[idV]+1;
                    queue.add(idU);
                }
                if (idU == t) {
                    return distance[t];
                }
            }
        }

        return distance[t];
    }

    public static void main(String[] args) {
        if (args == null || args.length < 3) {
            System.err.println("Too few arguments");
            return;
        }

        int s, t;
        try {
            s = Integer.parseInt(args[1]);
            t = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            System.err.println("Arguments 2 and 3 must be positive integer numbers");
            return;
        }

        Graph g = Graph.fromFile(args[0]);

        if (!g.contains(s) || !g.contains(t)) {
            System.err.println("Node(s) nonexistent");
            return;
        }

        int length = breitensuche(g, s, t);

        if (length == Integer.MAX_VALUE) {
            System.out.println("Node " + t + " is not reachable from node "
                    + s + " (or is (2^31)-1 edges away).");
        } else {
            System.out.println("The shortest path from node " + s +
                    " to node " + t + " has length " + length + ".");
        }
    }
}
