import java.util.*;
import java.math.*;
import java.util.concurrent.TimeUnit;

class BVUtil {
  public static Random rng = new Random();
  
  public static String uniformBVString(int n, double fillratio) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < n; i++)
    {
      result.append((rng.nextDouble() <= fillratio) ? '1' : '0');
    }
    return result.toString();
  }
  
  public static BitVector BVfromString(String s) {
    BitVector bv = new BitVector(s.length());
    for (int i = 0; i < s.length() / 2; i++)
    {
      bv.set(i, true);
    }
    for (int i = 0; i < s.length(); i++)
    {
      bv.set(i, (s.charAt(i) != '0'));
    }
    return bv;
  }
  
  public static String BVtoString(BitVector bv) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < bv.size(); i++)
    {
      result.append(bv.get(i) ? '1' : '0');
    }
    return result.toString();
  }
  
  public static long used_bits() {
    var rt = Runtime.getRuntime();
    rt.gc();
    return 8L * rt.totalMemory() - 8L * rt.freeMemory();
  }
  
  public static int parseLength(String[] args) {
    try { 
      int len = Integer.parseInt(args[0]); 
      if (len > 0) return len;
    }
    catch (Exception e) {}
    System.err.println("Es muss genau eine positive Ganzzahl übergeben werden.");
    System.exit(-1); 
    return 0;
  }
}
