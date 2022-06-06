package Blatt9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Graph {
    private int n,m; //Anzahl von Knoten(n) und Kanten(m)
    public Node[] knotenArray;

    public Graph (int n){
        //node Array init
        knotenArray = new Node[n];
        this.n = n;
        for(int i = 0; i < n; i++){
            //neu Node mit id i in index i
            knotenArray[i] = new Node(i);
        }
    }


    public boolean contains(int id){
        return id < n;
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

    public static Graph fromFile(String filepath){
        Graph graph = null;
        try {

            File file = new File("./Blatt9/"+ filepath);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()){
                String data = input.nextLine();
                char c =data.charAt(0);
                if (c == 'c'){
                    //line ist Kommentar. Mach nichts
                } else if (c == 'p'){

                    //Parse |V|
                    int i = 7; //anfang erste Zahl in "P edge |V| |E|";
                    StringBuilder intStr = new StringBuilder();
                    while(data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    int v = Integer.parseInt(intStr.toString());

                    //Parse |E|
                    i++;//anfang zweite Zahl E in "P edge |V| |E|";
                    intStr = new StringBuilder();
                    while(i < data.length() && data.charAt(i) != ' '){
                        intStr.append(data.charAt(i));
                        i++;
                    }
                    int e = Integer.parseInt(intStr.toString());

                    graph = new Graph(v); //Graph init
                } else if(c =='e'){
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
                        System.out.println("Error adding Edge: " + src + " " + dst);
                    }
                }
            }
        } catch (IOException e){
            System.out.println("Error in Graph file");
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
