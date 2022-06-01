package Blatt8Aufgabe2;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

class SimpleHTTest234 {

    static Random rng = new Random();
    
    interface Generator<T> {
        public T random();
    }
    
    private static int randomTests = 100;
    private static int capacity = 1337;
    private static double removeChance = .1;
    private static double reInsertChance = .1;
    
    private static <K, V> void testHT(Generator<K> genKey, Generator<V> genVal, SimpleHT<K, V> simpleHT) {
        HashMap<K, V> javaHM = new HashMap<K, V>();
        ArrayList<K> keys = new ArrayList<K>();
        ArrayList<K> removedKeys = new ArrayList<K>();
        
        // Erst werden reichlich zufällige Paare eingefügt.
        for (int i = 0; i < 1.5 * capacity; ++i)
        {
            K a = genKey.random();
            V b = genVal.random();
            javaHM.put(a, b);
            simpleHT.insert(a, b);
            keys.add(a);
        }
        
        // Dann werden einige Paare neu zugewiesen        
        for (K key : keys)
        {
            if (rng.nextDouble() < reInsertChance) {
                V b = genVal.random();
                javaHM.put(key, b);
                simpleHT.insert(key, b);
            }
        }
        
        // Jetzt werden noch einige Paare gelöscht
        for (K key : keys)
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
        for (K key : removedKeys) {
            if (simpleHT.get(key) != null) {
                System.err.println("Table should not contain pair (" + 
                    key + ", " + simpleHT.get(key) + "), which has been removed!");
                System.exit(-1);
            }
        }
        
        
        // Hier testen wir, ob nicht hinzugefügte Paare tatsächlich nicht da sind
        for (int i = 0; i < randomTests; i++)
        {
            K key = genKey.random();
            // Force Failure:
            // simpleHT.insert(key, genVal.random());
            if (!keys.contains(key) || removedKeys.contains(key)) {
                if (simpleHT.get(key) != null) {
                    System.err.println("Table should not contain pair (" + 
                        key + ", " + simpleHT.get(key) + "), which has never been inserted!");
                    System.exit(-1);
                }
            }
        }
    }

    public static void main(String[] args) {
        

        // **********************************************************************
        // **********************************************************************
        // Aufgabe 2:
        // Generator für alphanumerische Strings der Länge 42
        
        System.out.println("Teste Aufgabe 2...");
        
        Generator<Integer> intGen = () -> rng.nextInt();
        Generator<Float> floatGen = () -> rng.nextFloat();
        Generator<Double> doubleGen = () -> rng.nextDouble();
        Generator<String> stringGen = () -> 
            rng.ints(48, 123).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(42)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();     
        
        testHT(intGen, intGen, new SimpleHT<Integer, Integer>(capacity));
        testHT(doubleGen, intGen, new SimpleHT<Double, Integer>(capacity));
        testHT(stringGen, floatGen, new SimpleHT<String, Float>(capacity));
        
        

        // **********************************************************************
        // **********************************************************************
        // Aufgabe 3:
        
        System.out.println("Teste Aufgabe 3...");
        
        // Hashfunktion 1: Gibt 1 zurueck, wenn Key 0 ist, und sonst 0.
        class WorstHashEver<K> implements SimpleHashFunction<K> {
            public int getHash(K key) {
                return (key.equals(0)) ? 1 : 0;
            }
        }
        WorstHashEver<Double> whe = new WorstHashEver<Double>();
        
        // Hashfunktion 2: Wandelt String in Integer um, oder gibt 0 zurueck.
        class WorstStringHashEver implements SimpleHashFunction<String> {
            public int getHash(String key) {
                try { return Integer.parseInt(key); }
                catch (Exception e) { return 0; }
            }
        }
        WorstStringHashEver wshe = new WorstStringHashEver();
        
        // Verwende die neuen Klassen
        testHT(doubleGen, intGen, new SimpleHT<Double, Integer>(capacity, whe));
        testHT(stringGen, floatGen, new SimpleHT<String, Float>(capacity, wshe));
            
        // Alternativ: Gleiches Ergebnis, aber mit Lambda-Ausdrücken
        testHT(doubleGen, intGen, new SimpleHT<Double, Integer>(capacity, 
            (key) -> (key.equals(0)) ? 1 : 0));
            
        testHT(stringGen, floatGen, new SimpleHT<String, Float>(capacity, 
            (key) -> {
                try { return Integer.parseInt(key); }
                catch (Exception e) { return 0; }
            }));           

        

        // **********************************************************************
        // **********************************************************************
        // Aufgabe 4:
        
        System.out.println("Teste Aufgabe 4...");
        
        // Hier ergänzen: Initialisierung von Primzahl und Zufallsvariablen
        // 
        // code
        long p = ((2L << 31)-1);
        //zufällige a und b wählen
        long a = (long)Math.floor(Math.random()*(p-1));
        long b = (long)Math.floor(Math.random()*(p-1));
        // 
        // 

        testHT(intGen, intGen, new SimpleHT<Integer, Integer>(capacity,
            // HIER ERGÄNZEN: LAMBDA AUSDRUCK
                (key-> (int) Math.floorMod(a*key + b, p))
            ));
            
        testHT(stringGen, floatGen, new SimpleHT<String, Float>(capacity,
            // HIER ERGÄNZEN: LAMBDA AUSDRUCK
                (key -> {
                    int hash = 0;
                    for (int i = 0; i < key.length(); i++){
                        hash += Math.pow(a, i)*(key.charAt(i));
                    }
                    return (int) Math.floorMod(hash, p);
                })
            ));
    }
}
