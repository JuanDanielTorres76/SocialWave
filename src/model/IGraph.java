package model;

public interface IGraph<E> {

    public void addVertex(E element);

    public void addEdge(E source, E destination, double weight);

    public void DFS();

    public void BFS(E sourceElement);

    public Color getColor(E element);

    public double getDiscoveryTime(E element);

    public int getFinishTime(E element);

    public E getPredecessor(E element);

    public void deleteVertex(E element);

    public void deleteEdge(E source, E destination);

    public Vertex<E> searchVertex(E element);

    public Double searchEdge(E source, E destination);

    public Path<E> dijkstra(E eSource, E eDestination);

}