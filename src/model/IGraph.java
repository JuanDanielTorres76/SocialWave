package model;

import java.util.Map;
import java.util.List;

public interface IGraph<K,T> {

    public void addVertex(K key, T element);

    public void addEdge(K sourceKey, K destinationKey, double weight);

    public void DFS();

    public void BFS(K sourceElement);

    public Color getColor(K key);

    public double getDiscoveryTime(K key);

    public int getFinishTime(K key);

    public K getPredecessor (K key);

    public void deleteVertex(K key);

    public void deleteEdge(K sourceKey, K destinationKey);

    public Vertex<K,T> searchVertex(K key);

    public Double searchEdge(K sourceKey, K destinationKey);

    public Path<K> dijkstra(K eSource, K eDestination);

    public List<Vertex<K,T>> getVertices();

}