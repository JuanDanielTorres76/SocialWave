package model;

public class PathDijkstra<K,T> {

    private Vertex<K,T> vertex;

    private double distance;

    private boolean visited;

    private Vertex<K,T> predecessor;

    public PathDijkstra(Vertex<K,T> vertex, double distance, Vertex<K,T> predecessor) {

        this.vertex = vertex;
        
        this.distance = distance;
        
        this.visited = false;
    
    }

    public Vertex<K,T> getVertex() {
        
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

    public void setPredecessor(Vertex<K,T> predecessor) {
        this.predecessor = predecessor;
    }
    
    public Vertex<K,T> getPredecessor() {

        return predecessor;
    
    }

    
}
