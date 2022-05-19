import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.time.Duration;
import java.time.Instant;

class QuickSelect {
	public static void main(String[] args) {
		//Prüft Anzahl von Argumente
		if (args.length != 2) {
			System.err.println("Required arguments: int k, String algorithm (quickf|quickr|heap)");
			return;
		}

		//Prüft Gültigkeit von erste Argument
		int k = 0;
		try {
			k = Integer.parseInt(args[0]);
			if (k < 1) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			System.err.println("Argument k must be a positive integer.");
			return;
		}

		//Liest Input und bricht ab, falls ungültig
		int[] data = readInput();
		if (data == null) {
			System.err.println("Input must be a non-empty list of integers.");
			return;
		}

		//Prüft gültigkeit von k bzgl. Array
		if (k > data.length) {
			System.err.println("Array has fewer than k elements.");
			return;
		}

		//Anfang Zeitmessung
		long t1 = System.currentTimeMillis();

		//Finde k-te Element des Arrays mit gegebenem Algorithmus oder meldet ggf. einen Fehler
		int out = 0;
		if (args[1].equals("quickf")) {
			out = quickSelectFirst(data, k);
		} else if (args[1].equals("quickr")) {
			out = quickSelectRand(data, k);
		} else if (args[1].equals("heap")) {
			out = HeapSelect.heapSelect(data, k); //Ruft Klasse HeapSelect von Aufgabe 4.1 auf
		} else {
			System.err.println("Argument alg must be one of following: (quickf|quickr|heap).");
			return;
		}

		//Ende Zeitmessung
		long t2 = System.currentTimeMillis();

		//Ausgabe
		System.out.println("The " + k + "-smallest element of the list is " + out + ".");
		System.out.println("Elapsed time: " + (t2 - t1) + " ms");
	}

	public static int partition(int[] data, int l, int p, int r) {
		//Bringe Pivot zu den Anfang des Arrays
		swapElems(data, l, p);
		p = l;

		//Dijkstra-Partitionierung wie normal
		while (p < r) {
			if (data[p+1] < data[p]) {
				swapElems(data, p, p+1); //Falls kleiner als Pivot, tausche Element zur linke Seite
				p++;
			} else {
				swapElems(data, p+1, r); //Falls größer als Pivot, tausche zur rechte Seite
				r--;
			}
		}

		return p-l; //Anzahl von Elemente, die links von Pivot im Teilarray stehen
	}

	//Ruft deterministische quickSelect für ganzes Array auf
	public static int quickSelectFirst(int[] data, int k) {
		return quickSelect(data, 0, data.length-1, k, false);
	}

	//Ruft randomisierte deterministische quickSelect für ganzes Array auf
	public static int quickSelectRand(int[] data, int k) {
		return quickSelect(data, 0, data.length-1, k, true);
	}

	public static int quickSelect(int[] data, int l, int r, int k, boolean doRand) {
		if (l == r) {		//Zu betrachtende Array hat nur ein Element
			return data[l];
		}

		int p = l;		//Pivot mit l initialisieren und ggf. mit zufälligem Wert zw. l und r ersetzen
		if (doRand) {
			Random rand = new Random();
			p = rand.nextInt(r-l+1) + l;
		}

		int m = partition(data, l, p, r); //Dijkstra-Partitionierung mit Pivot-Index p

		if (k <= m) {
			return quickSelect(data, l, l+m-1, k, doRand); //Gesuchtes Element in linkem Teilarray
		} else if (k == m+1) {
			return data[l+m];					//Pivot ist gesuchtes Element
		} else {
			return quickSelect(data, l+m+1, r, k-m-1, doRand); //Gesuchtes Element in rechtem Teilarray
		}
	}

	//Tauscht 2 Elemente eines Arrays
	public static void swapElems(int[] arr, int i1, int i2) {
		int n = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = n;
	}

	//Liest Standard-In und prüft Gültigkeit
	public static int[] readInput() {
		Scanner input = new Scanner(System.in);

		ArrayList<Integer> data = new ArrayList<>();

		while (input.hasNextLine()) {
			try {
				data.add(Integer.parseInt(input.nextLine()));
			} catch (NumberFormatException e) {
				System.err.println("Input contains a non-number.");
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
}
