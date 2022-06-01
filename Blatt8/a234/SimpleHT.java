
import java.util.ArrayList;
import java.util.LinkedList;

interface SimpleHashFunction<K> {
	int getHash(K key);
}

class SimpleHT<K, V>{
    private ArrayList<LinkedList<Pair>> hashTabelle;
    private int m;
    private SimpleHashFunction<K> hashFunktion;

    SimpleHT(int m){
   		this(m, (k) -> k.hashCode());
    }

    SimpleHT(int m, SimpleHashFunction<K> h){
		this.m = m;
		hashTabelle = new ArrayList<LinkedList<Pair>>(m);
		for(int i = 0; i < m; i++){
            //hashTabelle mit null init
            hashTabelle.add(null);
        }
		hashFunktion = h;
    }

    private int addressOfList(K key){
        return Math.floorMod(hashFunktion.getHash(key), m);
    }

    public void insert(K key, V value){
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

    public V get(K key){
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

    public boolean remove(K key){
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
        private K schluessel;
        private V wert;

        Pair(K s, V w){
            this.schluessel = s;
            this.wert = w;
        }

        public boolean equals(Pair p){
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
