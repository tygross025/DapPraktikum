public class BitVector {
    
  int size;
  boolean data [];
  
  public BitVector(int n) {
    size = n;
    data = new boolean [n];          
  }
  
  public int size() {
    return size;
  }
  
  public boolean get(int index) {
    return data[index];
  }
  
  public void set(int index, boolean value) {
    data[index] = value;
  }
}
