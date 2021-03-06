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
        int n = bitsArray[index/32] << (index % 32); //findet int in bitsArray und shift die Zahl links index % 32 mal, sodass das Bit in index liegt im pos 0
        return n < 0; //Falls pos 0 = 1, dann ist n negative, Falls pos 0 = 0 dann ist n positive.
    }

    public void set(int index, boolean value){
        //wo im int array index liegt
        int arrayIndex = index/32;
        //wo im int index liegt
        int bitIndex = 1 << (31-(index%32)); //bitIndex = 31 - 2^(index % 32)

        if (value) {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] | bitIndex;
        } else {
            bitsArray[arrayIndex] = bitsArray[arrayIndex] & (0xFFFFFFFF - bitIndex);
        }

    }
}