import java.util.*;
import java.math.*;
import java.util.Collections;
import java.util.Arrays;

class RankMem {
      
  public static void main ( String[] args ) {   

    System.out.println("Teste Speicherverbrauch...");
    int input_size = BVUtil.parseLength(args);
    
    Integer C[] = { 1, 2, 
      3, 4, 5, 
      7, 8, 9, 
      15, 16, 17, 
      31, 32, 33,
      63, 64, 65,
      127, 128, 129,
      1337};
      
    Arrays.sort(C, Collections.reverseOrder());
      
    for (Integer c : C) {
      System.out.println("Parameter c=" + c + "...");
      
      // Generiere zufälligen BV
      String bvstring = BVUtil.uniformBVString(input_size, input_size / 10);
      BitVector bv = BVUtil.BVfromString(bvstring);
      
      // Messe aktuellen Speicher
      long bits_before = BVUtil.used_bits();
      
      BitVectorRank rankDs = new BitVectorRank(bv, c);
      
      // Berechne Speicher (ACHTUNG ungenau!)
      long bits_used = BVUtil.used_bits() - bits_before;
      
      System.out.println("Bits Erlaubt: " + ((long) (32L * input_size / c)));
      System.out.println("Bits Belegt:  " + bits_used);
      
      // grosszügige 100% Toleranz
      if (bits_used > ((long) (2 * 32L * input_size / c))) {
        System.err.println("Zu viel Speicher belegt!");
        return;
      }
      if (bits_used > ((long) (32L * input_size / c))) {
        System.out.println("Im Toleranzbereich.");
      }     
      System.out.println();  
      
      
      // Teste Korrektheit
      int result = 0;      
      for (int i = 0; i < input_size; i++)
      {
        if (rankDs.rank(i) != result) {
          System.err.println("Falsches Ergebnis für rank(" + i + "):");
          System.err.println("Erwartet: " + result);
          System.err.println("Ergebnis: " + rankDs.rank(i));
          return;
        }
        
        if (bvstring.charAt(i) == '1') {
          ++result;
        }
      }   
    }
  }
}

