package Blatt11;

import Blatt9.Edge;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class AdjArrayGraph {
    private int[] firstEdge;
    private int[] destination;

    private AdjArrayGraph(int[] firstEdge, int[] destination){
        this.firstEdge = firstEdge;
        this.destination = destination;
    }

    public static AdjArrayGraph fromFile(String path) throws IOException {
        int[] firstEdge = new int[0];
        int[] destination = new int[0];

        try {
            File file = new File("./Blatt11/"+ path);
            Scanner input = new Scanner(file);
            boolean init = false;

            int v = 0, e = 0;

            while (input.hasNextLine()){
                String data = input.nextLine();

                if (data.length() == 0) {
                    continue;
                }

                char c = data.charAt(0);
                if (c == 'p') {

                    if (init) {
                        throw new IOException("More than one problem line");
                    } else {
                        init = true;
                    }

                    //Parse |V|
                    int i = 7; //anfang erste Zahl in "P edge |V| |E|";
                    StringBuilder intStr = new StringBuilder();
                    while(data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    v = Integer.parseInt(intStr.toString());

                    //Parse |E|
                    i++;//anfang zweite Zahl E in "P edge |V| |E|";
                    intStr = new StringBuilder();
                    while(i < data.length() && data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    e = Integer.parseInt(intStr.toString());

                    firstEdge = new int[v+1];
                    destination = new int[e];
                    for(int n: destination){
                        n = -1;
                    }
                } else if (c =='e'){

                    if (!init){
                        throw new IOException("No problem line before first edge line");
                    }

                    //Parse erste Zahl
                    int i = 2; //anfang erste Zahl ist in pos 2
                    StringBuilder intStr = new StringBuilder();
                    while(data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    int src = Integer.parseInt(intStr.toString());

                    //Parse zweite Zahl
                    i++;//anfang zweite Zahl ist nach leerzeichen
                    intStr = new StringBuilder();
                    while(i < data.length() && data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    int dst = Integer.parseInt(intStr.toString());



                    //src und dst speichern
                    destination[firstEdge[src+1]] = dst;
                    for(i = src+1; i < firstEdge.length; i++){
                        firstEdge[i]++;
                    }

                } else if (c != 'c') {
                    throw new IOException("Unknown line marker " + c);
                }
            }

        } catch (Exception e){
            throw new IOException("Error in graph file: " + e.getMessage());
        }



        return new AdjArrayGraph(firstEdge,destination);
    }




    public String toString(){
        StringBuilder returnStr = new StringBuilder();

        for (int i = 0; i < numberOfNodes(); i++){ //Für alle Knoten
            for(int j = 0; j < numberOfNeighbors(i); j++){ //Für alle Nachbarn von knoten i
                if (true){//Knoten kann nicht mit sich selbst verbindet sein
                    returnStr.append("Knoten " + i + " verbindet zu " + getNeighbor(i,j) + "\n");
                }
            }
        }
        returnStr.append("Has HamiltonCycle: " + hasHamiltonCycle() + "\n");
        returnStr.append("firstEdge: " + Arrays.toString(firstEdge) + "\n");
        returnStr.append("destination: " + Arrays.toString(destination));

        return returnStr.toString();
    }

    public int numberOfNodes(){
        return firstEdge.length-1;
    }

    public int numberOfEdges(){
        return destination.length;
    }

    public int numberOfNeighbors(int node){
        return firstEdge[node+1] - firstEdge[node];
    }

    public int getNeighbor(int node, int k){
        return destination[firstEdge[node] + k];
    }

    public boolean hasHamiltonCycle(){
        for(int i = 0; i < numberOfNodes(); i++){
            if (hamiltonCycle(new int[0], i)){
                return true;
            }
        }
        return false;
    }

    private boolean hamiltonCycle(int[] besucht, int next){
        if(next==-1){
            //Suche ist fertig

            if (besucht.length != numberOfNodes()){
                return false;
            }

            for (int i = 0; i < numberOfNeighbors(besucht[0]); i++){
                //Für alle Knoten verbindet mit der letzten Knoten
                if(getNeighbor(besucht[0], i) == besucht[besucht.length-1]){
                    //Falls die Knoten mit der ersten Nachbar ist
                    return true;
                }
            }
            return false;
        } else{
            //Besuch nächste in queue
            int[] newBesucht = new int[besucht.length+1];
            for(int i = 1; i < newBesucht.length; i++){
                newBesucht[i] = besucht[i-1];
            }
            newBesucht[0] = next;

            if (numberOfNeighbors(next) == 0){

            }

            for (int i = 0; i < numberOfNeighbors(next); i++){
                //Für alle Nachbarn i

                int nachbar = getNeighbor(newBesucht[0], i);

                boolean schonBesucht = false;
                for (int n:newBesucht) {
                    //Prüf ob Knoten next schon besucht ist
                    if (n == nachbar){
                        schonBesucht = true;
                    }
                }
                if (!schonBesucht && hamiltonCycle(newBesucht, nachbar)){
                    return true;
                }
            }
            //Hier ist nur erreicht falls next keine Nachbarn hat oder sie alle besucht sind.
            //Prüf ob Pfad ein hamiltonCycle ist
            return hamiltonCycle(newBesucht, -1);

        }
    }



}
