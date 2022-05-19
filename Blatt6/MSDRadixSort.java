import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class MSDRadixSort {
	public static void main(String[] args){

		//Liest Input und bricht ab, falls ungültig
		int[] data = readInput();
		if (data == null || data.length == 0) {
			System.err.println("Input must be a non-empty list of positive integers.");
			return;
		}

		//Eingabe kopieren und absteigend sortieren (für Assertion)
		int[] dataCopy = Arrays.copyOf(data, data.length);
		Arrays.sort(dataCopy);
		for (int i = 0; i < dataCopy.length/2; i++) {
			swapElems(dataCopy, i, dataCopy.length-i-1);
		}

		MSDRadixSort sort = new MSDRadixSort();

		//Zeitmessung & Sortieren
		long t1 = System.currentTimeMillis();
		sort.msdRadix(data);
		long t2 = System.currentTimeMillis();

		assert Arrays.equals(data, dataCopy);

		//Ausgabe
		if (args.length == 0 || !args[0].equals("-q")) {
			System.out.println(Arrays.toString(data));
		}
		System.out.println("Runtime: " + (t2-t1) + "ms");
	}

	//Initiale Aufruf
	public void msdRadix(int[] data) {
		msdRadix(data, 0, data.length-1, 3);
	}

	public void msdRadix(int[] data, int l, int r, int b) {
		if (b < 0 || l >= r) return;

		//Naives Algorithmus
		if (r-l+1 <= 32) {
			insertionSort(data, l, r);
		}

		//Sortiere Arrayinterval [l,r] nach dem b-niederwertigsten Byte
		int[] indexArray = sortByByte(data, l, r, b);

		//Rekursive Aufrufe für Subintervalle nach dem (b-1)-niederwertigsten Bytre
		for (int i = 0; i < indexArray.length-1; i++) {
			msdRadix(data, indexArray[i+1]+1, indexArray[i], b-1);
		}
	}

    public static int[] sortByByte(int[] input, int l, int r, int b){

		//Frequenzarray erzeugen und Hilfsarray für sortierte Werte anlegen
        int[] countArray = count(input, l, r, b);
        int[] sortArray = new int[r-l+1];

		//Frequenzarray aufsummieren wie bei CountingSort
        for (int i = countArray.length-2; i >= 0; i--) {
			countArray[i] += countArray[i+1];
        }

		//Zu sortierende Elemente in entsprechende Intervalle von Hilfsarray stabil eintragen
        for (int i = r; i >= l; i--){
            int key = bLowestByte(input[i], b);
			countArray[key]--;
            sortArray[countArray[key]] = input[i];
        }

		//Sortiertes Array zurück in input-Arrayintervall übertragen
        for (int i = 0; i < sortArray.length; i++) {
			input[i+l] = sortArray[i];
        }

		// Erzeuge Array, damit Elemente in input[l,r] mit b-Byte i im Subintervall [C[i+1]+1, C[i]] liegen
		int[] C = new int[257];
		C[0] = r;
		for (int i = 0; i < countArray.length; i++) {
			C[i+1] = countArray[i]+l-1;
		}
        return C;
    }

	//Frequenzarray von A[l,r] nach b-te Byte erzeugen, Schlüssel liegen in der Menge {0,...,255}
    public static int[] count(int[] A, int l, int r, int b){
        int[] c = new int[256];

        for(int i = l; i <= r; i++){
            int cIndex = bLowestByte(A[i], b);
            c[cIndex]++;
        }

        return c;
    }

    public static int bLowestByte(int n , int b) {
		return (n >> (8 * (b))) & 0xFF;
    }

    public static void insertionSort(int[] data, int l, int r) {
		for (int i = l; i <= r; i++) {
			int n = data[i];
			int j = i-1;

			/*
			while (j >= l && data[j] < n) {
				swapElems(data, j, j+1);
				j--;
			}
			*/

			while (j >= l && data[j] < n) {
				data[j+1] = data[j];
				j--;
			}
			data[j+1] = n;
		}
    }

    public static void swapElems(int[] arr, int i1, int i2) {
		int n = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = n;
	}

	public static int[] readInput() {
		Scanner input = new Scanner(System.in);

		ArrayList<Integer> data = new ArrayList<>();

		while (input.hasNextLine()) {
			try {
				int d = Integer.parseInt(input.nextLine());
				if (d < 1) {
					throw new NumberFormatException();
				}
				data.add(d);
			} catch (NumberFormatException e) {
				System.err.println("Input array is invalid");
				return null;
			}
		}

		int[] out = new int[data.size()];

		int i = 0;
		for (int n : data) {
			out[i] = n;
			i++;
		}

		return out;
	}

	/*
    public static int[] countingSort(int[] data){
        int min = getMin(data);
        int max = getMax(data);

        int[] countArray = count(data, min,max);
        int[] ergebnissArray = new int[data.length];


        for (int i = countArray.length-2; i >= 0; i--){
            countArray[i]  += countArray[i+1];
        }

        for (int i = data.length-1; i >= 0; i--){
            int schluessel = data[i]-min;
            ergebnissArray[countArray[schluessel]-1] = data[i];
            countArray[schluessel]--;
        }

        for (int i = 0; i < data.length; i++){
            data[i] = ergebnissArray[i];
        }

        countArray = count(data,min,max);
        return countArray;
    }
    */
}
