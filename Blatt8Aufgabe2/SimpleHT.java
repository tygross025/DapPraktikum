package Blatt8Aufgabe2;

import java.util.ArrayList;
import java.util.LinkedList;

public class SimpleHT<K, V> {
    private ArrayList<LinkedList<Pair<K,V>>> hashTabelle;
    private int m;
    private SimpleHashFunction<K> hash;

    SimpleHT(int m, SimpleHashFunction<K> h){
        this.m = m;
        this.hash = h;
        hashTabelle = new ArrayList<LinkedList<Pair<K,V>>>(m);
        for(int i = 0; i < m; i++){
            //hashTabelle mit null init
            hashTabelle.add(null);
        }
    }

    SimpleHT(int m){
        this(m, (key -> key.hashCode()));
    }


    private int addressOfList(K key){
        return Math.floorMod(hash.getHash(key), m);
        // return Math.floorMod(key.hashCode(), m);
    }

    public void insert(K key, V value){
        Pair<K,V> newPair = new Pair<K,V>(key, value);

        int arrayIndex = addressOfList(key);
        LinkedList<Pair<K,V>> list = hashTabelle.get(arrayIndex);
        //Prüft ob es schon ein list in index arrayIndex gibt
        if (list != null){
            //list existiert, jetzt prüft ob Element in List schon existiert
            for(int i = 0; i < list.size(); i++){
                if (list.get(i).getKey().equals(key)){
                    //Element mit Key existiert schon
                    list.set(i, newPair);
                    return;
                }
            }
            list.add(newPair); //newPair in list einfügen
        } else {
            //neue linkedList initialzieren
            list = new LinkedList<Pair<K,V>>();
            list.add(newPair);
            hashTabelle.set(arrayIndex, list);
        }
    }

    public V get(K key){
        int arrayIndex = addressOfList(key);
        LinkedList<Pair<K,V>> list = hashTabelle.get(arrayIndex);
        if (list != null){
            for (Pair<K,V> p: list) {
                if (p.getKey().equals(key)){
                    //Element gefunden
                    return p.getValue();
                }
            }
        }
        //Element existiert nicht
        return null;
    }

    public boolean remove(K key){
        int arrayIndex = addressOfList(key);
        boolean returnVal = false;
        LinkedList<Pair<K,V>> list = hashTabelle.get(arrayIndex);
        if (list != null){
            //array index hat ein LinkedList
            for(int i = 0; i < list.size(); i++){
                if (list.get(i).getKey().equals(key)){
                    //Element mit key gefunden
                    list.remove(i);
                    returnVal = true;
                }
            }

            if (list.size() == 0){
                //Falls das LinkedList leer ist, überschreib es mit null
                hashTabelle.set(arrayIndex, null);
            }
        }
        return returnVal;
    }


    private class Pair<K, V>{
        private K schluessel;
        private V wert;
        Pair(K s, V w){
            this.schluessel = s;
            this.wert = w;
        }

        public boolean equals(Pair<K,V> p){
            return (p.getKey().equals(schluessel) && p.getValue().equals(wert));
        }

        public K getKey(){
            return schluessel;
        }
        public V getValue(){
            return wert;
        }
    }


}
