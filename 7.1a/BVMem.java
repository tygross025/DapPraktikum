import java.util.*;
import java.math.*;

class BVMem {
      
  public static void main ( String[] args ) {   

    System.out.println("Teste Speicherverbrauch...");
    int input_size = BVUtil.parseLength(args);
    
    // Generiere zufällige Anfragen
    int queries = input_size / 10;
    int positions [] = new int [queries];
    boolean values [] = new boolean [queries];
    for (int i = 0; i < queries; i++)
    {
      positions[i] = BVUtil.rng.nextInt(input_size);
      values[i] = BVUtil.rng.nextDouble() > 0.5;
    }
    Arrays.sort(positions);
    for (int i = 1; i < queries; i++)
    {
      if (positions[i - 1] == positions[i]) {
        values[i] = values[i - 1];
      }
    }
    
    // Messe aktuellen Speicher
    long bits_before = BVUtil.used_bits();
    
    // Erstelle BV
    BitVector bv = new BitVector(input_size);

    // Setze generierte Positionen    
    for (int i = 0; i < queries / 2; i++)
    {
      bv.set(positions[i], true);
    }      
    for (int i = 0; i < queries; i++)
    {
      bv.set(positions[i], values[i]);
    }
    
    // Berechne Speicher (ACHTUNG ungenau!)
    // grosszügige 5% Toleranz
    long bits_used = BVUtil.used_bits() - bits_before;
    
    System.out.println("Bits Erlaubt: " + ((long) (1.2 * input_size)));
    System.out.println("Bits Belegt:  " + bits_used);
    
    if (bits_used > (1.2 * input_size)) {
      System.err.println("Zu viel Speicher belegt!");
      System.exit(0);
    }
    
    // Teste Belegung
    int error;
    for (error = 0; error < queries; error++)
    {
      if (bv.get(positions[error]) != values[error]) {
        error = -positions[error] - 1;
        break;
      }
    }
    
    // Wenn bisher kein Fehler: Teste Lücken (sollten 0 sein)
    if (error >= 0) {
      for (error = 1; error < queries; error++)
      {
        int distance = positions[error] - positions[error - 1];
        if (distance > 1) {
          int pos = positions[error - 1] + BVUtil.rng.nextInt(distance - 1) + 1;
          if (bv.get(pos)) {
            error = -pos - 1;
            break;
          }
        }
      }
    }
    
    if (error < 0) {
      error = - error - 1;
      System.err.println("Speicherlimit eingehalten, " + 
                         "aber BV enthaelt falschen Wert an Position " + 
                         error + ": ");
      System.err.println("Vorgabe:   " + (bv.get(error) ? '0' : '1'));
      System.err.println("Bitvektor: " + (bv.get(error) ? '1' : '0'));
    }
    else {
      System.out.println("Speicherlimit eingehalten und BV korrekt.");
    }      
  }
}
