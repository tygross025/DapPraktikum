import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

class EnumSubset {
	public static void main(String[] args) {
		int[] data = readInput();
		if (data == null || data.length == 0) {
			System.out.println("Input must contain only integers separated by line breaks.");
			return;
		}

		int k = readArgs(args);
		if (k == -1) {
			return;
		}

		System.out.println("Before removing duplicates: " + Arrays.toString(data));

		int n = removeDuplicates(data);

		System.out.print("After removing duplicates: [" + data[0]);
		for (int i = 1; i < n; i++) {
			System.out.print(", " + data[i]);
		}
		System.out.println("]");

		if (k > n) {
			System.out.println("Error: k is too large");
			return;
		}

		int total = allSubsets(data, n, k);
		System.out.println("There are " + total + " subsets.");
	}

	public static int allSubsets(int[] set, int n, int k) {
		int[] subset = new int[k];
		return countSubsets(set, 0, n, k, subset);
	}

	public static int countSubsets(int[] set, int l, int r, int k, int[] subset) {
		if (k > r-l) {
			return 0;
		} else if (k == 0) {
			System.out.println(Arrays.toString(subset));
			return 1;
		} else {
			subset[subset.length - k] = set[l];
			int a = countSubsets(set, l+1, r, k-1, subset);
			int b = countSubsets(set, l+1, r, k, subset);
			return a + b;
		}
	}

	public static int removeDuplicates(int[] data) {
		Arrays.sort(data);

		int i = 0;
		for (int n : data) {
			if (n != data[i]) {
				i++;
				data[i] = n;
			}
		}

		return i+1;
	}


	public static int readArgs(String[] args) {
		if (args.length != 1) {
			System.out.println("Exactly one argument is required.");
			return -1;
		}

		try {
			int arg = Integer.parseInt(args[0]);

			if (arg > 0) {
				return arg;
			} else {
				throw new NumberFormatException();
			}

		} catch (NumberFormatException e) {
			System.out.println("The argument must be a positive integer.");
		}

		return -1;
	}

	public static int[] readInput() {
		Scanner input = new Scanner(System.in);

		ArrayList<Integer> data = new ArrayList<>();

		while (input.hasNextLine()) {
			try {
				data.add(Integer.parseInt(input.nextLine()));
			} catch (NumberFormatException e) {
				System.err.println("Input list contains a non-number.");
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
