package model;

import java.util.ArrayList;

import java.util.List;

public class Vertex<E> {

    private E element;

    private Vertex<E> predecessor;

    private List<Vertex<E>> graphList;

    private Color color;

    private double discoveryTime;

    private int finishTime;

    public Vertex(E element){

        this.element = element;

        this.discoveryTime = 0;

        this.finishTime = 0;

        graphList = new ArrayList<>();

    }

    public void addAdjacent(Vertex<E> adj){

        graphList.add(adj);

    }

    public Vertex<E> getPredecessor() {

        return predecessor;

    }

    public void setPredecessor(Vertex<E> predecessor) {

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

    public List<Vertex<E>> getGraphList() {

        return graphList;

    }

    public void setGraphList(List<Vertex<E>> graphList) {

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

    public E getElement() {

        return element;

    }

}