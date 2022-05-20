import java.util.*;
import java.math.*;

class RankCorrect {

  
  private static int checkRank(BitVector bv, BitVectorRank rank) {
    int r = 0;
    for (int i = 0; i < bv.size(); i++)
    {
      if (rank.rank(i) != r) return i;
      if (bv.get(i)) ++r;
    }
    if (rank.rank(bv.size()) != r) return bv.size(); 
    return -1;
  }
  
  private static void checkRankPrint(BitVector bv, BitVectorRank rank) {
    System.out.print(((bv.size() <= 100) ? 
                       (BVUtil.BVtoString(bv) + " ") : 
                       ("BV der Laenge " + bv.size() + " ")));
    if (bv.size() != rank.size()) {
      System.err.println("Die rank-Datenstruktur hat die falsche Laenge.");
      System.err.println("Vorgabe: " + bv.size());
      System.err.println("rankDS:  " + rank.size());
      System.exit(-1);
    }
    else {
      int error = checkRank(bv, rank);
      if (error == -1) {
        System.out.println("korrekt");
      }
      else {
        System.err.println("\nFalsches Ergebnis fuer rank(" + error +
                           "): " + rank.rank(error));
        System.exit(-1);
      }
    }
  }
  
  private static void printCountError(int correct, int wrong, int s, int e) {
    System.err.println("\nFalsches Ergebnis fuer count(" + s + ", " + e + "): ");
    System.err.println("Vorgabe: " + correct);
    System.err.println("rankDS:  " + wrong);
    System.exit(-1);
  }  
  
  private static void checkCount(BitVector bv, BitVectorRank rank) {
    System.out.print(((bv.size() <= 100) ? 
                       (BVUtil.BVtoString(bv) + " ") : 
                       ("BV der Laenge " + bv.size() + " ")));
                       
    if (bv.size() != rank.size()) {
      System.err.println("Die rank-Datenstruktur hat die falsche Laenge.");
      System.err.println("Vorgabe: " + bv.size());
      System.err.println("rankDS:  " + rank.size());
      System.exit(-1);
    }
    
    int x1 = bv.size() / 2 - BVUtil.rng.nextInt(bv.size() / 3);
    int x2 = bv.size() / 2 + BVUtil.rng.nextInt(bv.size() / 3);
        
    int c = 0;
    int l = 0;
    int r = 0;
    while (r < x2) {
      if (c != rank.count(l, r)) {
          printCountError(c, rank.count(l, r), l, r);
          return;
      }
      if (r < bv.size() && bv.get(r)) {
        ++c;
      }
      ++r;
    }
    if (r > bv.size()) r = bv.size();
    
    while (l < x2) {
      if (c != rank.count(l, r)) {
          printCountError(c, rank.count(l, r), l, r);
          return;
      }
      if (l < bv.size() && bv.get(l)) {
        --c;
      }
      ++l;
    }
    
    c = 0;
    l = x1;
    r = x1;
    while (r < bv.size() + 1) {
      if (c != rank.count(l, r)) {
          printCountError(c, rank.count(l, r), l, r);
          return;
      }
      if (r < bv.size() && bv.get(r)) {
        ++c;
      }
      ++r;
    }
    r = bv.size();
    
    while (l <= r) {
      if (c != rank.count(l, r)) {
          printCountError(c, rank.count(l, r), l, r);
          return;
      }
      if (l < bv.size() && bv.get(l)) {
        --c;
      }
      ++l;
    }
    
    System.out.println("korrekt");
  }
      
      
  public static void main ( String[] args ) {
    
    BitVector bv;
    BitVectorRank rankDs;
    List<String> input = new ArrayList<String>();
    
    Integer C[] = { 1, 2, 
      3, 
      8, 
      32,
      125,
      1337};
  

    
    System.out.println("Teste rank fuer kleine Eingaben...");
    System.out.println();
    for (Integer c : C) {
      input.clear();
      input.add("0100110111100010101011");
      input.add(BVUtil.uniformBVString(32, 0.5));
      input.add(BVUtil.uniformBVString(33, 0.5));
      input.add(BVUtil.uniformBVString(64, 0.5));
      input.add(BVUtil.uniformBVString(65, 0.5));
      
      input.add(BVUtil.uniformBVString(70, 0));
      input.add(BVUtil.uniformBVString(70, 0.1));
      input.add(BVUtil.uniformBVString(70, 0.5));
      input.add(BVUtil.uniformBVString(70, 0.9));
      input.add(BVUtil.uniformBVString(70, 1));
      
      for (String str : input) {
        bv = BVUtil.BVfromString(str);
        rankDs = new BitVectorRank(BVUtil.BVfromString(str), c);
        checkRankPrint(bv, rankDs);
      }
    }
    System.out.println();
    
    System.out.println("Teste rank fuer große Eingaben...");
    System.out.println();
    for (Integer c : C) {
      input.clear();
      input.add(BVUtil.uniformBVString(1000000, 0));
      input.add(BVUtil.uniformBVString(1000000, 0.1));
      input.add(BVUtil.uniformBVString(1000000, 0.5));
      input.add(BVUtil.uniformBVString(1000000, 0.9));
      input.add(BVUtil.uniformBVString(1000000, 1));
      
      for (String str : input) {
        bv = BVUtil.BVfromString(str);
        rankDs = new BitVectorRank(BVUtil.BVfromString(str), c);
        checkRankPrint(bv, rankDs);
      }
    }
    System.out.println();
    
    System.out.println("Teste count fuer kleine Eingaben...");
    System.out.println();
    for (Integer c : C) {  
      
      
      input.clear();
      input.add("0100110111100010101011");
      input.add(BVUtil.uniformBVString(32, 0.5));
      input.add(BVUtil.uniformBVString(33, 0.5));
      input.add(BVUtil.uniformBVString(64, 0.5));
      input.add(BVUtil.uniformBVString(65, 0.5));
      
      input.add(BVUtil.uniformBVString(70, 0));
      input.add(BVUtil.uniformBVString(70, 0.1));
      input.add(BVUtil.uniformBVString(70, 0.5));
      input.add(BVUtil.uniformBVString(70, 0.9));
      input.add(BVUtil.uniformBVString(70, 1));
      
      for (String str : input) {
        bv = BVUtil.BVfromString(str);
        rankDs = new BitVectorRank(BVUtil.BVfromString(str), c);
        checkCount(bv, rankDs);
      }
    }
    System.out.println();
    
    System.out.println("Teste count fuer große Eingaben...");
    System.out.println();    
    for (Integer c : C) {
      input.clear();
      input.add(BVUtil.uniformBVString(100000, 0));
      input.add(BVUtil.uniformBVString(100000, 0.1));
      input.add(BVUtil.uniformBVString(100000, 0.5));
      input.add(BVUtil.uniformBVString(100000, 0.9));
      input.add(BVUtil.uniformBVString(100000, 1));
      
      for (String str : input) {
        bv = BVUtil.BVfromString(str);
        rankDs = new BitVectorRank(BVUtil.BVfromString(str), c);
        checkCount(bv, rankDs);
      }
    }
  }
}
