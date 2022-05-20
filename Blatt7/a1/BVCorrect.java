package Blatt7.a1;

import java.util.*;


class BVCorrect {      
      
  public static void main ( String[] args ) {
      
    BitVector bv;
    List<String> input = new ArrayList<String>();
    
    System.out.println("Teste Funktionalitaet fuer kleine Eingaben...");
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
      String check = BVUtil.BVtoString(BVUtil.BVfromString(str));
      System.out.println("Vorgabe:   " + str);
      System.out.println("Bitvektor: " + check);
      System.out.println((check.equals(str)) ? "Korrekt!" : "Fehler!");
      System.out.println();
    }
    
    // ohne initialisierung
    bv = new BitVector(100);
    System.out.println("Vorgabe:   " + BVUtil.uniformBVString(100, 0));
    System.out.println("BitVector: " + BVUtil.BVtoString(bv));
    System.out.println();        

    System.out.println("Teste Funktionalitaet fuer große Eingaben...");
    System.out.println();
    input.clear();
    input.add(BVUtil.uniformBVString(1000000, 0));
    input.add(BVUtil.uniformBVString(1000000, 0.1));
    input.add(BVUtil.uniformBVString(1000000, 0.5));
    input.add(BVUtil.uniformBVString(1000000, 0.9));
    input.add(BVUtil.uniformBVString(1000000, 1));
    
    for (String str : input) {
      String bvresult = BVUtil.BVtoString(BVUtil.BVfromString(str));
      if (bvresult.length() != str.length()) {
        System.out.println("Der Bitvektor hat die falsche Laenge.");
        System.out.println("Vorgabe:   " + str.length());
        System.out.println("Bitvektor: " + bvresult.length());
      }
      
      int i;
      for (i = 0; i < str.length(); i++)
      {
        if (str.charAt(i) != bvresult.charAt(i)) break;
      }
      
      if (i < str.length()) {
        System.err.println("Fehler an Position " + i + ":");
        System.err.println("Vorgabe:   " + str.charAt(i));
        System.err.println("Bitvektor: " + bvresult.charAt(i));
      }
      else {
        System.out.println("Bitvektor korrekt!");
      }
    }
  }
}
