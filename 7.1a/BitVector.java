

class BitVector {
    private int bitsLength = 0;
    private int[] bitsArray;

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
        int i = index/32;
        int b = 2^(index%32);
        if (value) {
            bitsArray[i] = bitsArray[i] | b;
        } else {
            bitsArray[i] = bitsArray[i] & (0xFFFFFFFF - b);
        }

    }
}