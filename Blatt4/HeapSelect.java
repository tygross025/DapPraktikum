import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class HeapSelect {

    public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Exactly one argument is required");
			return;
		}

        //Ganzzahl k als Argument
        int k;
        try {
            k = Integer.parseInt(args[0]);
        } catch (NumberFormatException e){
            System.err.println("Argument must be an integer number");
            return;
        }

        //Eingabe mit java Scanner lesen
        Scanner input = new Scanner(System.in);
        ArrayList<Integer> arrList = new ArrayList<Integer>();
        while (input.hasNextLine()) {
            try {
                //integer lesen und in ein ArrayList einfuegen
                int nextInt = Integer.parseInt(input.nextLine());
                arrList.add(nextInt);
            } catch (NumberFormatException e) {
                System.err.println("Input list contains a non-number.");
                return;
            }
        }

        //arraylist in int[] umwandeln
        int[] arr = new int[arrList.size()];
        for (int i = 0; i < arr.length; i++){
            int n = arrList.get(i);
            arr[i] = n;
        }

        if(k>arr.length){
            System.out.println("There is no " + k + " Element in given array.");
            return;
        }
        if(k<=0){
            System.out.println("K must be a postive number");
            return;
        }

        //Ausgabe
        System.out.println("Das " + k + "-kleinste Element ist " + heapSelect(arr, k));
    }


    public static void minHeapify(int[] data, int i, int n) {
        int linksKindIndex = (2*i)+1;
        int rechtsKindIndex = (2*i)+2;
        int kleinerKind = 0;
        int kleinerKindIndex = 0;
        if (linksKindIndex >= n){ //Falls linksKind nicht existiert
            return;
        }
        kleinerKind = data[linksKindIndex];
        kleinerKindIndex = linksKindIndex;

        //Falls rechtsKind existiert
        if (rechtsKindIndex < n && data[rechtsKindIndex] < kleinerKind) {
            kleinerKind = data[rechtsKindIndex]; //kleinerkind = rechts kind
            kleinerKindIndex = rechtsKindIndex;
        }


        if (data[i] <= kleinerKind){
            return; //Parent ist schon das Minimum
        }

        swap(data, i, kleinerKindIndex);

        minHeapify(data, kleinerKindIndex, n);
    }


    public static void buildMinHeap(int[] data){
        int i = data.length/2-1; //erste nicht leaf knoten

        //minheapify alle Knoten
        while(i >= 0){
            minHeapify(data, i, data.length);
            i--;
        }
    }


    public static int extractMin(int[]data, int n){
        int min = data[0];


        swap(data, 0, n-1); //tauschen min Element (0) und element n-1.
        minHeapify(data, 0, n-1); //minHeapify verbliebenden Heap-Elemente

        return min;
    }


    public static int heapSelect(int[] data, int k){
        buildMinHeap(data);

        int i = 0;
        for (i = 0; i < k; i++){ //loop bis k kleinste Element
            extractMin(data, data.length-i);
        }

        return data[data.length-i];
    }


    public static void swap(int[] arr, int a, int b){
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
}
