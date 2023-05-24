package model;

import java.util.*;

public class GraphMatrix<E> implements IGraph<E> {

    private List<List<Double>> adjMatrix;

    private Map<Vertex<E>, Integer> mapIndex;

    private Map<E, Vertex<E>> vertices;

    private boolean isDirected;

    public GraphMatrix(boolean isDirected) {

        adjMatrix = new ArrayList<>();

        mapIndex = new HashMap<>();
        
        vertices = new HashMap<>();
        
        this.isDirected = isDirected;
    
    }

    @Override
    public void addVertex(E element) {
        
        if (!vertices.containsKey(element)) {

            Vertex<E> vertex = new Vertex<>(element);

            vertices.put(element, vertex);
            
            mapIndex.put(vertex, mapIndex.size());
            
            for (List<Double> row : adjMatrix) {
                
                row.add(0.0);
            
            }
            
            List<Double> newRow = new ArrayList<>();
            
            for (int i = 0; i < adjMatrix.size() + 1; i++) {
                
                newRow.add(0.0);
            
            }
            
            adjMatrix.add(newRow);
        
        }
    
    }

    @Override
    public void addEdge(E source, E destination, double weight) {

        Vertex<E> sourceVertex = vertices.get(source);

        Vertex<E> destinationVertex = vertices.get(destination);

        if (sourceVertex != null && destinationVertex != null) {

            int sourceIndex = mapIndex.get(sourceVertex);

            int destinationIndex = mapIndex.get(destinationVertex);

            adjMatrix.get(sourceIndex).set(destinationIndex, weight);

            if (!isDirected) {

                adjMatrix.get(destinationIndex).set(sourceIndex, weight);

            }

        }

    }

    @Override
    public void DFS() {

        for (Vertex<E> vertex : vertices.values()) {

            vertex.setColor(Color.WHITE);

            vertex.setPredecessor(null);

        }

        int time = 0;

        for (Vertex<E> a : vertices.values()) {

            if (a.getColor() == Color.WHITE) {

                time = DFSTraversal(a, time);

            }

        }

    }

    private int DFSTraversal(Vertex<E> a, int time) {

        time++;

        a.setDiscoveryTime(time);

        a.setColor(Color.GRAY);

        int aIndex = mapIndex.get(a);

        for (int i = 0; i < adjMatrix.size(); i++) {

            if (adjMatrix.get(aIndex).get(i) != 0) {

                Vertex<E> b = getVertex(i);

                if (b.getColor() == Color.WHITE) {

                    b.setPredecessor(a);

                    time = DFSTraversal(b, time);

                }

            }

        }

        a.setColor(Color.BLACK);

        time++;
        
        a.setFinishTime(time);
        
        return time;
    
    }

    @Override
    public void BFS(E sourceElement) {

        Vertex<E> source = vertices.get(sourceElement);

        List<Vertex<E>> vertexList = new ArrayList<>(vertices.values());
        
        for (Vertex<E> v : vertexList) {
            
            v.setColor(Color.WHITE);
            
            v.setDiscoveryTime(-1);
            
            v.setPredecessor(null);
        
        }
        
        source.setColor(Color.GRAY);
        
        source.setDiscoveryTime(0);
        
        source.setPredecessor(null);
        
        Queue<Vertex<E>> queue = new LinkedList<>();
        
        queue.offer(source);
        
        while (!queue.isEmpty()) {
            
            Vertex<E> a = queue.poll();
            
            int aIndex = mapIndex.get(a);
            
            for (int i = 0; i < adjMatrix.size(); i++) {
                
                if (adjMatrix.get(aIndex).get(i) != 0) {
                    
                    Vertex<E> b = getVertex(i);
                    
                    if (b.getColor() == Color.WHITE) {
                        
                        b.setColor(Color.GRAY);
                        
                        b.setDiscoveryTime(a.getDiscoveryTime() + 1);
                        
                        b.setPredecessor(a);
                        
                        queue.offer(b);
                    
                    }
                
                }
            
            }
            
            a.setColor(Color.BLACK);
        
        }
    
    }

    private Vertex<E> getVertex(int index) {

        for (Map.Entry<Vertex<E>, Integer> entry : mapIndex.entrySet()) {

            if (entry.getValue() == index) {

                return entry.getKey();

            }

        }

        return null;

    }

    @Override
    public void deleteVertex(E element) {

        if (vertices.containsKey(element)) {

            Vertex<E> vertex = vertices.get(element);

            int index = mapIndex.get(vertex);

            adjMatrix.remove(index);
            
            for (List<Double> row : adjMatrix) {
                
                row.remove(index);
            
            }
            
            mapIndex.remove(vertex);
            
            vertices.remove(element);
        
        }
    
    }

    @Override
    public void deleteEdge(E source, E destination) {

        Vertex<E> sourceVertex = vertices.get(source);
        
        Vertex<E> destinationVertex = vertices.get(destination);
        
        if (sourceVertex != null && destinationVertex != null) {
            
            int sourceIndex = mapIndex.get(sourceVertex);
            
            int destinationIndex = mapIndex.get(destinationVertex);
            
            adjMatrix.get(sourceIndex).set(destinationIndex, 0.0);
            
            if (!isDirected) {
                
                adjMatrix.get(destinationIndex).set(sourceIndex, 0.0);
            
            }
        
        }
    
    }

    @Override
    public Vertex<E> searchVertex(E element) {

        return vertices.get(element);

    }

    @Override
    public Double searchEdge(E source, E destination) {

        Vertex<E> sourceVertex = vertices.get(source);

        Vertex<E> destinationVertex = vertices.get(destination);

        if (sourceVertex != null && destinationVertex != null) {

            int sourceIndex = mapIndex.get(sourceVertex);
            
            int destinationIndex = mapIndex.get(destinationVertex);
            
            return adjMatrix.get(sourceIndex).get(destinationIndex);
        
        }
        
        return null;
    
    }

    @Override
    public Color getColor(E element) {

        Vertex<E> vertex = vertices.get(element);

        if (vertex != null) {

            return vertex.getColor();

        }

        return null;

    }

    @Override
    public double getDiscoveryTime(E element) {

        Vertex<E> vertex = vertices.get(element);
        
        if (vertex != null) {

            return vertex.getDiscoveryTime();

        }

        return 0;

    }

    @Override
    public int getFinishTime(E element) {

        Vertex<E> vertex = vertices.get(element);

        if (vertex != null) {

            return vertex.getFinishTime();

        }

        return 0;
    }

    @Override
    public E getPredecessor(E element) {

        Vertex<E> vertex = vertices.get(element);

        if (vertex != null) {

            Vertex<E> predecessor = vertex.getPredecessor();

            if (predecessor != null) {

                return predecessor.getElement();

            }

        }

        return null;

    }

}