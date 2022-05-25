package Blatt7.c1;

class BitVectorSelect {

    private BitVectorRank bitVectorRank;

    public BitVectorSelect(BitVectorRank bvr){
        this.bitVectorRank = bvr;
    }

    public int size(){
        return bitVectorRank.size();
    }

    public int select(int k){
        if (k < 1 || k > bitVectorRank.rank(size())){
            //Es gibt kein K-te Element für k <1 und K > max rank
            return -1;
        }

        int i = k; //Kte Element liegt mindestens K-bits vorne im Array
        int rank = bitVectorRank.rank(i);
        while (rank != k){
            i += k-rank; //Bit liegt mindestens k-rank bits vorna im Array

            rank = bitVectorRank.rank(i);
        }

        return i-1; //Rank(i) Zahlt i nicht, also das kte Element liegt im i-1 pos
    }

    public int select(int k, int start, int end){
        if (k <1|k > bitVectorRank.count(start,end)){
            //Es gibt kein K-te Element
            return -1;
        }

        int i = start + k; //Kte Element liegt mindestens K-bits vorne im Array
        int count = bitVectorRank.count(start, i);
        while (count != k){
            i += k-count; //Bit liegt mindestens k-rank bits vorna im Array

            count = bitVectorRank.count(start,i);
        }

        return i-1; //Rank(i) Zahlt i nicht, also das kte Element liegt im i-1 pos
    }
}