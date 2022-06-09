package Blatt9;

import java.io.IOException;
import java.util.LinkedList;

public class Breitensuche {

    static int breitensuche(Graph g, int s, int t) {

        /*
        Distanzarray und Priority-Queue erzeugen
        Eine Distanz von 2^31-1 zeigt, dass der entsprechende Knoten noch nicht entdeckt ist
         */
        int[] distance = new int[g.getN()];
        LinkedList<Integer> queue = new LinkedList<>();

        //Alle Distanzen außer s auf (2^31)-1 initialisieren und s ins Priority-Queue einfügen
        for (int i = 0; i < distance.length; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[s] = 0;
        queue.add(s);

        while (!queue.isEmpty()){
            //Entferne erstes Element v vom Queue
            int idV = queue.removeFirst();
            LinkedList<Edge> edges = g.getNode(idV).getAdjazenzliste();

            //Finde jeden Knoten u, zu dem v verbunden ist
            for (Edge e : edges) {
                int idU = e.getDst().getId();

                //Falls u noch nicht entdeckt worden ist, bestimme Distanz von u und füge u ins Queue
                if (distance[idU] == Integer.MAX_VALUE) {
                    distance[idU] = distance[idV]+1;
                    queue.add(idU);
                }

                //Falls ein Knoten der Gesuchte ist, liefern wir seine Distanz zurück
                if (idU == t) {
                    return distance[t];
                }
            }
        }

        //Wenn Knoten t nicht entdeckt wird, wird Integer.MAX_VALUE zurückgeliefert
        return distance[t];
    }

    public static void main(String[] args) {
        if (args == null || args.length != 3) {
            System.err.println("Required arguments: String filepath, int s, int t");
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

        //Start- und Zielknoten einlesen
        int s, t;
        try {
            s = Integer.parseInt(args[1]);
            t = Integer.parseInt(args[2]);
        } catch (NumberFormatException e){
            System.err.println("Arguments 2 and 3 must be integer numbers");
            return;
        }

        //Gültigkeit von Eingaben prüfen
        if (!g.contains(s) || !g.contains(t)) {
            System.err.println("Node(s) nonexistent");
            return;
        }

        //Berechnen und Ausgabe
        int length = breitensuche(g, s, t);

        if (length == Integer.MAX_VALUE) {
            System.out.println("Node " + t + " is not reachable from node "
                    + s + " or is (2^31)-1 edges away.");
        } else {
            System.out.println("The shortest path from node " + s +
                    " to node " + t + " has length " + length + ".");
        }
    }
}
