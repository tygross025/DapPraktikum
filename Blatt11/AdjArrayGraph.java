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

                    destination[firstEdge[firstEdge.length-1]] = dst;
                    if (src > dst){
                        for(i = src; i < firstEdge.length; i++){
                            firstEdge[i]++;
                        }
                    } else {
                        firstEdge[firstEdge.length-1]++;
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

        returnStr.append(Arrays.toString(firstEdge) + "\n");
        returnStr.append(Arrays.toString(destination));

        return returnStr.toString();
    }

    public int numberOfNodes(){
        return firstEdge.length-1;
    }

    public int numberOfEdges(){
        return destination.length;
    }

    public int numberOfNeighbors(int node){
        //not sure about this
        int val = 0;
        for (int i = 0; i < destination.length; i++){
            if (destination[i] == node){
                val++;
            }
        }
        return val;
    }

    public int getNeighbor(int node, int k){
        return destination[firstEdge[node] + k];
    }

}
