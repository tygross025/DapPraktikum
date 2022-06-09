package Blatt9;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private int n,m; //Anzahl von Knoten(n) und Kanten(m)
    private Node[] knotenArray;

    public Graph (){
        //node Array init
        knotenArray = new Node[0];
        this.n = 0;
        this.m = 0;
    }

    public int getN() { return n; }
    public int getM() { return m; }

    public boolean contains(int id){
        return id < n && id >= 0;
    }

    public Node getNode(int id){
        if (this.contains(id)){
            return knotenArray[id];
        }
        return null;
    }

    public boolean addEdge(int src, int dst){
        if(!contains(src) || !(contains(dst))){
            //Einer der Knoten sind nicht vorhanden
            return false;
        }

        getNode(src).addEdge(getNode(dst));
        m++;
        return true;
    }

    public void addNode(){
        n++; //Knoten Anzahl incrementieren
        Node newNode = new Node(n-1);
        Node[] newKnotenArray = new Node[n];

        //Kopieren KnotenArray in newKnotenArray
        for(int i = 0; i < n-1; i++){
            newKnotenArray[i] = knotenArray[i];
        }

        //newNode in newKnotenArray einfügen
        newKnotenArray[n-1] = newNode;
        knotenArray = newKnotenArray;
    }

    public static Graph fromFile(String filepath) throws IOException {
        Graph graph = new Graph();

        try {
            File file = new File("./Blatt9/"+ filepath);
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

                    for (i = 0; i < v; i++){
                        //add v nodes in Graph
                        graph.addNode();
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

                    if(!graph.addEdge(src, dst)){ //ruf addedge an
                        System.err.println("Error adding Edge: " + src + " " + dst);
                        throw new IOException();
                    }
                } else if (c != 'c') {
                    throw new IOException("Unknown line marker " + c);
                }
            }

            if (e != graph.getM()) {
                throw new IOException("Graph does not have expected number of edges");
            }

        } catch (Exception e){
            throw new IOException("Error in graph file: " + e.getMessage());
        }
        return graph;
    }

    public String toString(){
        StringBuilder returnStr = new StringBuilder();
        for(int i = 0; i < n; i++){
            LinkedList<Edge> adjazenzliste = knotenArray[i].getAdjazenzliste();

            returnStr.append("Knoten " + i + " verbunden zu:" );

            for (Edge e : adjazenzliste){
                returnStr.append(" " + e.getDst().getId());
            }
            returnStr.append("\n");
        }
        return returnStr.toString();
    }
}
