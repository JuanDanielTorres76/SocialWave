package model;

public class Pair<K, L> {

    private K firstKey;

    private L secondKey;

    public Pair(K firstKey, L secondKey){

        this.firstKey = firstKey;

        this.secondKey = secondKey;

    }

    public K getFirstKey() {
        return firstKey;
    }

    public void setFirstKey(K firstKey) {
        this.firstKey = firstKey;
    }

    public L getSecondKey() {
        return secondKey;
    }

    public void setSecondKey(L secondKey) {
        this.secondKey = secondKey;
    }
    
}
