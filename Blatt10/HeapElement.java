package Blatt10;

import java.util.Comparator;

public class HeapElement implements Comparable<HeapElement> {

	private int distance;
	private Node node;

    public HeapElement() {}

	public HeapElement(int distance, Node node) {
		this.distance = distance;
		this.node = node;
	}

	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Node getNode() {
		return this.node;
	}

	public String toString() {
		return "(" + this.distance + ", " + this.node.toString() + ")";
	}

    @Override public int compareTo(HeapElement element)
    {
 
        if (this.distance < element.distance)
            return -1;
 
        if (this.distance > element.distance)
            return 1;
 
        return 0;
    }
}
