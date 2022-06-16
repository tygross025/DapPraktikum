package Blatt10;

import java.util.LinkedList;

public class Node {
    private LinkedList<Edge> adjazenzliste;
    private int id;
    public Node(int id){
        this.id = id;
        adjazenzliste = new LinkedList<>();
    }

    public void addEdge(Node dst){
        Edge e = new Edge(this, dst);
        adjazenzliste.add(e);
    }

    public boolean equals(Object other){
        if (other instanceof Node){
            return ((Node) other).getId() == id;
        }
        return false;
    }


    public int getId(){
        return id;
    }
    public LinkedList<Edge> getAdjazenzliste(){
        return adjazenzliste;
    }
}
