
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;

class SimpleHTTestA1 {

    static Random rng = new Random();
    
    interface Generator<T> {
        public T random();
    }    
    
    private static int randomTests = 100;
    private static int capacity = 133750;
    private static double removeChance = .1;
    private static double reInsertChance = .1;
    
    private static void testHT(Generator<Integer> genKey, Generator<Integer> genVal) {
        HashMap<Integer, Integer> javaHM = new HashMap<Integer, Integer>();
        SimpleHT simpleHT = new SimpleHT(capacity);
        ArrayList<Integer> keys = new ArrayList<Integer>();
        ArrayList<Integer> removedKeys = new ArrayList<Integer>();
        
        // Erst werden reichlich zufällige Paare eingefügt.
        for (int i = 0; i < 1.5 * capacity; ++i)
        {
            Integer a = genKey.random();
            Integer b = genVal.random();
            javaHM.put(a, b);
            simpleHT.insert(a, b);
            keys.add(a);
        }
        
        // Dann werden einige Paare neu zugewiesen        
        for (Integer key : keys)
        {
            if (rng.nextDouble() < reInsertChance) {
                Integer b = genVal.random();
                javaHM.put(key, b);
                simpleHT.insert(key, b);
            }
        }
        
        // Jetzt werden noch einige Paare gelöscht
        for (Integer key : keys)
        {
            if (rng.nextDouble() < removeChance) {
                removedKeys.add(key);
                javaHM.remove(key);
                simpleHT.remove(key);
            }
        }
        
        // Hier testen wir, ob alle gewollten Paare noch da sind
        for (var pair : javaHM.entrySet()) {
            if (!pair.getValue().equals(simpleHT.get(pair.getKey()))) {
                System.err.println("Table should contain pair (" + 
                    pair.getKey() + ", " + pair.getValue() + ")!");
                System.err.println("Instead, it contains (" + 
                    pair.getKey() + ", " + simpleHT.get(pair.getKey()) + ")!");
                System.exit(-1);
            }
        }
        
        // Hier testen wir, ob alle entfernten Paare tatsächlich weg sind
        for (Integer key : removedKeys) {
            if (simpleHT.get(key) != null) {
                System.err.println("Table should not contain pair (" + 
                    key + ", " + simpleHT.get(key) + "), which has been removed!");
                System.exit(-1);
            }
        }
        
        // Hier testen wir, ob nicht hinzugefügte Paare tatsächlich nicht da sind
        for (int i = 0; i < randomTests; i++)
        {
            Integer key = rng.nextInt();
            // Force Failure:
            // simpleHT.insert(key, genVal.random());
            if (!keys.contains(key)) {
                if (simpleHT.get(key) != null) {
                    System.err.println("Table should not contain pair (" + 
                        key + ", " + simpleHT.get(key) + "), which has never been inserted!");
                    System.exit(-1);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Aufgabe 1:
        testHT(() -> rng.nextInt(), () -> rng.nextInt());
    }
}
