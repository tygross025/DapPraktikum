public class BitVectorRank {
  
  private BitVector bv;
    
  public BitVectorRank(BitVector bitvec, int c) {
    bv = bitvec;
  }
  
  public int size() {
    return bv.size();
  }
  
  public int rank(int index) {
    int r = 0;
    for (int i = 0; i < index; i++)
    {
      if (bv.get(i)) ++r;
    }
    return r;    
  }
  
  public int count(int start, int end) {
    int r = 0;
    for (int i = start; i < end; i++)
    {
      if (bv.get(i)) ++r;
    }
    return r;    
  }
}
