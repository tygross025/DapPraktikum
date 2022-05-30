package Blatt8;

import java.util.ArrayList;
import java.util.LinkedList;

class SimpleHT{
    private ArrayList<LinkedList<Pair>> hashTabelle;
    private int m;

    SimpleHT(int m){
        this.m = m;
        hashTabelle = new ArrayList<LinkedList<Pair>>(m);
        for(int i = 0; i < m; i++){
            //hashTabelle mit null init
            hashTabelle.add(null);
        }
    }

    private int addressOfList(Integer key){
        return Math.floorMod(key, m);
    }

    public void insert(Integer key, Integer value){
        Pair newPair = new Pair(key, value);

        int arrayIndex = addressOfList(key);
        LinkedList<Pair> list = hashTabelle.get(arrayIndex);
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
            list = new LinkedList<Pair>();
            list.add(newPair);
            hashTabelle.set(arrayIndex, list);
        }
    }

    public Integer get(Integer key){
        int arrayIndex = addressOfList(key);
        LinkedList<Pair> list = hashTabelle.get(arrayIndex);
        if (list != null){
            for (Pair p: list) {
                if (p.getKey().equals(key)){
                    //Element gefunden
                    return p.getValue();
                }
            }
        }
        //Element existiert nicht
        return null;
    }

    public boolean remove(Integer key){
        int arrayIndex = addressOfList(key);
        boolean returnVal = false;
        LinkedList<Pair> list = hashTabelle.get(arrayIndex);
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


    private class Pair{
        private Integer schluessel, wert;
        Pair(int s, int w){
            this.schluessel = s;
            this.wert = w;
        }

        public boolean equals(Pair p){
            return (p.getKey().equals(schluessel) && p.getValue().equals(wert));
        }

        public Integer getKey(){
            return schluessel;
        }
        public Integer getValue(){
            return wert;
        }
    }
}