package model;

import java.util.ArrayList;

import java.util.List;

public class Vertex<K,T> {

    private T element;

    private K key;

    private Vertex<K,T> predecessor;

    private List<Vertex<K,T>> graphList;

    private Color color;

    private double discoveryTime;

    private int finishTime;

    public Vertex(T element, K key){

        this.key = key;

        this.element = element;

        this.discoveryTime = 0;

        this.finishTime = 0;

        graphList = new ArrayList<>();

    }

    public void addAdjacent(Vertex<K,T> adj){

        graphList.add(adj);

    }

    public Vertex<K,T> getPredecessor() {

        return predecessor;

    }

    public void setPredecessor(Vertex<K,T> predecessor) {

        this.predecessor = predecessor;

    }

    public Color getColor(){

        return color;

    }

    public void setColor(Color color){

        this.color = color;

    }

    public double getDiscoveryTime() {

        return discoveryTime;

    }

    public List<Vertex<K,T>> getGraphList() {

        return graphList;

    }

    public void setGraphList(List<Vertex<K,T>> graphList) {

        this.graphList = getGraphList();

    }

    public void setDiscoveryTime(double discoveryTime) {

        this.discoveryTime = discoveryTime;

    }

    public int getFinishTime() {

        return finishTime;

    }

    public void setFinishTime(int finishTime) {

        this.finishTime = finishTime;

    }

    public T getElement() {

        return element;

    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

}