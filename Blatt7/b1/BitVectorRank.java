package Blatt7.b1;

import Blatt7.b1.BitVector;

class BitVectorRank {
    private BitVector bitVector;
    private int[] rankArray;
    private int c;

    public BitVectorRank(BitVector b, int c){
        this.bitVector = b;
        this.c = c;
        rankArray = new int[(b.size()/c)+1];

        int rank = 0;
        int rankIndex = 0;
        for (int i = 0; i<b.size(); i++){
            if (bitVector.get(i)){
                rank++;
            }
            if ((i+1) % c == 0){
                //speichert jeder Cte rank in rankArray
                rankArray[rankIndex+1] = rank;
                rankIndex++;
            }
        }
    }

    public int size(){
        return bitVector.size();
    }

    public int rank(int index){
        int rank = rankArray[(index/c)]; //findet rank am nähsten pos in rankArray (Rank von 0 bis index/c)

        //Addiert rank von index/c bis index
        for (int i = 0; i < index%c; i++){
            //Falls index i true ist incrementieren rank, sonst nichts.
            if (bitVector.get(index-1-i)){
                rank++;
            }
        }

        return rank;
    }

    public int count (int start, int end){
        return rank(end) - rank(start); //count = rank von end - rank von start
    }
}