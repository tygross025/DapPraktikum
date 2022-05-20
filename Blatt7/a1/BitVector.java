package Blatt7.a1;


class BitVector {
    private int size = 0;
    private int[] bitsArray;

    public BitVector(int n){
        size = n;
        bitsArray = new int[(n/32)+1];
    }

    public int size(){
        return size;
    }

    public boolean get(int index){
        if (index >= size || index <0){
            //error
        }
        return bitsArray[index/32] << (index) <0;
    }

    public void set(int index, boolean value){
        if (index >= size || index <0){
            //error
        }
        //wo im int array index liegt
        int arrayIndex = index/32;
        //wo im int index liegt
        int bitIndex = 1 << (31-(index%32)); //bitIndex = 2^(index % 32)

        if (value) {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] | bitIndex;
        } else {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] & (0xFFFFFFFF - bitIndex);
        }

    }
}