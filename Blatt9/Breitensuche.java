package Blatt9;

import java.util.LinkedList;

public class Breitensuche {
    public static void main(String[] args){
        Graph graph = Graph.fromFile(args[0]);

        int n1 = Integer.parseInt(args[1]);
        int n2 = Integer.parseInt(args[2]);

        int breitSuche = breitensuche(graph, n1, n2);
        if (breitSuche == Integer.MAX_VALUE){
            System.out.println("Es existiert kein Pfad.");
            return;
        }
        System.out.println("Kürzsten Pfades: " + breitSuche);
    }


    public static int breitensuche(Graph graph, int s, int t){
        int[] distance = new int[graph.getN()];
        for(int i =0; i< graph.getN(); i++){
            if (i == s){
                distance[i] = 0;
            } else {
                distance[i] = Integer.MAX_VALUE;
            }
        }

        LinkedList<Node> queue = new LinkedList<Node>(); //queue inisitalieren
        queue.add(graph.getNode(s));

        while (!queue.isEmpty()){
            Node n = queue.getFirst();//lesen erste Element in queue

            LinkedList<Edge> kanten = n.getAdjazenzliste();//Liste von Kanten verbindet mit n
            for (Edge k: kanten){
                if (distance[k.getDst().getId()] == Integer.MAX_VALUE){ //Falls noch nicht besucht
                    distance[k.getDst().getId()] = distance[k.getSrc().getId()]+1; //speichern distance = kanten src distance + 1

                    //füge verbindeten Knoten in queue
                    queue.add(k.getDst());
                }
            }
            queue.removeFirst();
        }
        return distance[t];
    }
}
