package model;

import java.util.List;

public class Path<K> {

    private double distance;

    private List<K> path;

    public Path(List<K> path, double distance) {
        
        this.distance = distance;
        
        this.path = path;
    
    }

    public double getDistance() {
        
        return distance;
    
    }

    public void setDistance(double distance) {
        
        this.distance = distance;
    
    }
  
    public List<K> getPath() {
        return path;
    }

    public void setPath(List<K> path) {
        this.path = path;
    }


}