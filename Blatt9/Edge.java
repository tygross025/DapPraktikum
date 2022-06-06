package Blatt9;

public class Edge {
    private Node src, dst;
    public Edge(Node n1, Node n2){
        this.src = n1;
        this.dst = n2;
    }

    public Node getSrc(){
        return src;
    }
    public Node getDst(){
        return dst;
    }
}
