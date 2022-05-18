

public class RadixSort {
    public static void main(String[] args){
        int[] arr = {1,5,7,324,6,32};
    }

    public static void SortByByte(int[] input, int l, int r, int b){
        int min = getMin(data, l, r);
        int max = getMax(data, l, r);

        int[] countArray = count(data, min,max);
        int[] ergebnissArray = new int[data.length];
    }

    //berechnet min in Interval l bis r
    public static int getMin(int[] data, int l, int r){
        int min = data[l];
        for(int i = l; l < r; i++){
            if (data[i]<min){
                //n ist kleiner als min
                min = data[i];
            }
        }
        return min;
    }
    //berechnet max in Interval l bis r
    public static int getMax(int[] data, int l, int r){
        int max = data[l];
        for(int i = l; l < r; i++){
            if (data[i]>max){
                //n ist groeßer als max
                max = data[i];
            }
        }
        return max;
    }

    public static int[] count(int[] A, int min, int max, int l, int r){
        int[] c = new int[max-min+1];

        for(int i = l; i < r; i++){
            int cIndex = A[i] - min;
            c[cIndex]++;
        }

        return c;
    }

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
}