package model;

public class Path<E> {

    private Vertex<E> vertex;

    private double distance;

    private boolean visited;

    private Vertex<E> predecessor;

    public Path(Vertex<E> vertex, double distance, Vertex<E> predecessor) {

        this.vertex = vertex;
        
        this.distance = distance;
        
        this.visited = false;
    
    }

    public Vertex<E> getVertex() {
        
        return vertex;
    
    }

    public double getDistance() {
        
        return distance;
    
    }

    public void setDistance(double distance) {
        
        this.distance = distance;
    
    }

    public boolean isVisited() {
        
        return visited;
    
    }

    public void setVisited(boolean visited) {
        
        this.visited = visited;
    
    }

    public void setPredecessor(Vertex<E> predecessor) {
        this.predecessor = predecessor;
    }
    
    public Vertex<E> getPredecessor() {

        return predecessor;
    
    }

}