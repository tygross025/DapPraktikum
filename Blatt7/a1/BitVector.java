package Blatt7.a1;

import java.util.Arrays;

class BitVector {
    private int bitsLength = 0;
    private int[] bitsArray;

    public static void main(String[] args){
        BitVector b = new BitVector(4);
        b.set(0, true);
        b.print();
    }
    private void print(){
        System.out.println(Arrays.toString(bitsArray));
    }

    public BitVector(int n){
        bitsLength = n;
        bitsArray = new int[(n/32)+1];
    }

    private int bitOfInt(int n, int b){
        return (n >> b) & 0x01;
    }

    public int size(){
        return bitsLength;
    }

    public boolean get(int index){
        if (index >= bitsLength || index <0){
            //error
        }

        return bitOfInt(index/32, index % 32) == 1;
    }

    public void set(int index, boolean value){
        if (index >= bitsLength || index <0){
            //error
        }
        //wo im int array index liegt
        int arrayIndex = index/32;
        //wo im int index liegt
        int bitIndex = 1 << (index%32); //bitIndex = 2^(index % 32)

        if (value) {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] | bitIndex;
        } else {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] & (0xFFFFFFFF - bitIndex);
        }

    }
}