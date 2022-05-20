import java.util.*;
import java.math.*;


class SelectCorrect {
  
    private static void checkSelect(BitVector bv, BitVectorSelect select) {
    ArrayList<Integer> ones = new ArrayList<Integer>();
    ones.add(-1);
    for (int i = 0; i < bv.size(); i++)
    {
      if (bv.get(i)) ones.add(i);
    }
    for (int i = 0; i < ones.size(); ++i) {
      if (select.select(i) != ones.get(i)) {
        System.err.println("Falsches Ergebnis fuer select(" + i + "):");
        System.err.println("Vorgabe:  " + ones.get(i));
        System.err.println("selectDS: " + select.select(i));
        System.exit(-1);
      }
    }
  }
 
  
  private static void checkSelect(BitVector bv, BitVectorSelect select, int s, int e) {
    ArrayList<Integer> ones = new ArrayList<Integer>();
    ones.add(-1);
    for (int i = s; i < e; i++)
    {
      if (bv.get(i)) ones.add(i);
    }
    for (int i = 0; i < ones.size(); ++i) {
      if (select.select(i, s, e) != ones.get(i)) {
        System.err.println("Falsches Ergebnis fuer select(" + i + ", " + s + ", " + e + "):");
        System.err.println("Vorgabe:  " + ones.get(i));
        System.err.println("selectDS: " + select.select(i, s, e));
        System.exit(-1);
      }
    }
  }
  
  private static void checkIntervalSelect(BitVector bv, BitVectorSelect select, int iter) {
    System.out.print(((bv.size() <= 100) ? 
                       (BVUtil.BVtoString(bv) + " ") : 
                       ("BV der Laenge " + bv.size() + " ")));
    if (bv.size() != select.size()) {
      System.err.println("\nDie select-Datenstruktur hat die falsche Laenge.");
      System.err.println("Vorgabe:  " + bv.size());
      System.err.println("selectDS: " + select.size());
      System.exit(-1);
    }
    
    checkSelect(bv, select);
    if (iter > 0) checkSelect(bv, select, 0, bv.size());
    
    for (int i = 0; i < iter; i++)
    {
      int x1 = BVUtil.rng.nextInt(bv.size() + 1);
      int x2 = BVUtil.rng.nextInt(bv.size() + 1);
      
      checkSelect(bv, select, 0, x1);
      checkSelect(bv, select, x2, bv.size());
      checkSelect(bv, select, Math.min(x1, x2), Math.max(x1, x2));
    }
    
    System.out.println("korrekt");
  }
      
      
  public static void main ( String[] args ) {
    

    BitVector bv;
    BitVectorRank rankDs;
    BitVectorSelect selectDs;
    List<String> input = new ArrayList<String>();
    
    
    System.out.println("Teste select(k) fuer kleine Eingaben...");
    System.out.println();
    input.clear();
    input.add("0100110111100010101011");
    input.add(BVUtil.uniformBVString(32, 0.5));
    input.add(BVUtil.uniformBVString(33, 0.5));
    input.add(BVUtil.uniformBVString(64, 0.5));
    input.add(BVUtil.uniformBVString(65, 0.5));
    input.add(BVUtil.uniformBVString(100, 0));
    input.add(BVUtil.uniformBVString(100, 0.1));
    input.add(BVUtil.uniformBVString(100, 0.5));
    input.add(BVUtil.uniformBVString(100, 0.9));
    input.add(BVUtil.uniformBVString(100, 1));
    for (String str : input) {
      bv = BVUtil.BVfromString(str);
      rankDs = new BitVectorRank(bv, 1);
      selectDs = new BitVectorSelect(rankDs);
      checkIntervalSelect(BVUtil.BVfromString(str), selectDs, 0);
    }
    System.out.println();
    
    
    System.out.println("Teste select(k) fuer große Eingaben...");
    System.out.println();
    input.clear();
    input.add(BVUtil.uniformBVString(100000, 0));
    input.add(BVUtil.uniformBVString(100000, 0.1));
    input.add(BVUtil.uniformBVString(100000, 0.5));
    input.add(BVUtil.uniformBVString(100000, 0.9));
    input.add(BVUtil.uniformBVString(100000, 1));
    for (String str : input) {
      bv = BVUtil.BVfromString(str);
      rankDs = new BitVectorRank(bv, 1);
      selectDs = new BitVectorSelect(rankDs);
      checkIntervalSelect(BVUtil.BVfromString(str), selectDs, 0);
    }
    System.out.println();
    
    System.out.println("Teste select(k, s, e) fuer kleine Eingaben...");
    System.out.println();    
    input.clear();
    input.add("0100110111100010101011");
    input.add(BVUtil.uniformBVString(32, 0.5));
    input.add(BVUtil.uniformBVString(33, 0.5));
    input.add(BVUtil.uniformBVString(64, 0.5));
    input.add(BVUtil.uniformBVString(65, 0.5));
    input.add(BVUtil.uniformBVString(100, 0));
    input.add(BVUtil.uniformBVString(100, 0.1));
    input.add(BVUtil.uniformBVString(100, 0.5));
    input.add(BVUtil.uniformBVString(100, 0.9));
    input.add(BVUtil.uniformBVString(100, 1));
    System.out.println("Beispiel vom Blatt:");
    for (String str : input) {
      bv = BVUtil.BVfromString(str);
      rankDs = new BitVectorRank(bv, 1);
      selectDs = new BitVectorSelect(rankDs);
      checkIntervalSelect(BVUtil.BVfromString(str), selectDs, 20);
    }
    System.out.println();
  
    System.out.println("Teste select(k, s, e) fuer große Eingaben...");
    System.out.println();
    input.clear();
    input.add(BVUtil.uniformBVString(100000, 0));
    input.add(BVUtil.uniformBVString(100000, 0.1));
    input.add(BVUtil.uniformBVString(100000, 0.5));
    input.add(BVUtil.uniformBVString(100000, 0.9));
    input.add(BVUtil.uniformBVString(100000, 1));
    
    for (String str : input) {
      bv = BVUtil.BVfromString(str);
      rankDs = new BitVectorRank(bv, 1);
      selectDs = new BitVectorSelect(rankDs);
      checkIntervalSelect(BVUtil.BVfromString(str), selectDs, 20);
    }
    System.out.println();
  }
}
