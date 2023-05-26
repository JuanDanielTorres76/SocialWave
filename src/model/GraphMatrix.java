package model;

import java.util.*;

public class GraphMatrix<K,T> implements IGraph<K,T> {

    private List<List<Double>> adjMatrix;

    private Map<Vertex<K,T>, Integer> mapIndex;

    private Map<K, Vertex<K,T>> vertices;

    private boolean isDirected;

    public GraphMatrix(boolean isDirected) {

        adjMatrix = new ArrayList<>();

        mapIndex = new HashMap<>();
        
        vertices = new HashMap<>();
        
        this.isDirected = isDirected;
    
    }

    @Override
    public void addVertex(K key, T element) {
        
        if (!vertices.containsKey(key)) {

            Vertex<K,T> vertex = new Vertex<K,T>(element, key);

            vertices.put(key, vertex);
            
            mapIndex.put(vertex, mapIndex.size());

            //Se inicializa la lista
            
            for (List<Double> row : adjMatrix) {
                
                row.add(0.0);
            
            }

            // Nueva fila creada por el vertice añadido
            
            List<Double> newRow = new ArrayList<>();
            
            for (int i = 0; i < adjMatrix.size() + 1; i++) {
                
                newRow.add(0.0);
            
            }
            
            adjMatrix.add(newRow);
        
        }
    
    }

    @Override
    public void addEdge(K keySource, K keyDestination, double weight) {

        Vertex<K,T> sourceVertex = vertices.get(keySource);

        Vertex<K,T> destinationVertex = vertices.get(keyDestination);

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

        for (Vertex<K,T> vertex : vertices.values()) {

            vertex.setColor(Color.WHITE);

            vertex.setPredecessor(null);

        }

        int time = 0;

        for (Vertex<K,T> a : vertices.values()) {

            if (a.getColor() == Color.WHITE) {

                time = DFSTraversal(a, time);

            }

        }

    }

    private int DFSTraversal(Vertex<K,T> a, int time) {

        time++;

        a.setDiscoveryTime(time);

        a.setColor(Color.GRAY);

        int aIndex = mapIndex.get(a);

        for (int i = 0; i < adjMatrix.size(); i++) {

            if (adjMatrix.get(aIndex).get(i) != 0) {

                Vertex<K,T> b = getVertex(i);

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
    public void BFS(K sourceElement) {

        Vertex<K,T> source = vertices.get(sourceElement);

        List<Vertex<K,T>> vertexList = new ArrayList<>(vertices.values());
        
        for (Vertex<K,T> v : vertexList) {
            
            v.setColor(Color.WHITE);
            
            v.setDiscoveryTime(-1);
            
            v.setPredecessor(null);
        
        }
        
        source.setColor(Color.GRAY);
        
        source.setDiscoveryTime(0);
        
        source.setPredecessor(null);
        
        Queue<Vertex<K,T>> queue = new LinkedList<>();
        
        queue.offer(source);
        
        while (!queue.isEmpty()) {
            
            Vertex<K,T> a = queue.poll();
            
            int aIndex = mapIndex.get(a);
            
            for (int i = 0; i < adjMatrix.size(); i++) {
                
                if (adjMatrix.get(aIndex).get(i) != 0) {
                    
                    Vertex<K,T> b = getVertex(i);
                    
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

    @Override
    public Path<K,T> dijkstra(K eSource, K eDestination) {

        Map<Vertex<K,T>, Double> distances = new HashMap<>();

        for (Vertex<K,T> vertex : vertices.values()) {

            distances.put(vertex, Double.POSITIVE_INFINITY);

        }

        PriorityQueue<Path<K,T>> queue = new PriorityQueue<>(Comparator.comparingDouble(Path::getDistance));

        Vertex<K,T> sourceVertex = vertices.get(eSource);

        distances.put(sourceVertex, 0.0);

        queue.add(new Path<>(sourceVertex, 0.0, null));

        while (!queue.isEmpty()) {

            Path<K,T> currentPath = queue.poll();

            Vertex<K,T> currentVertex = currentPath.getVertex();

            if (currentPath.isVisited()) {

                currentPath.setVisited(true);

            }

            if (currentVertex.getElement().equals(eDestination)) return currentPath;

            int currentIndex = mapIndex.get(currentVertex);

            for (int i = 0; i < adjMatrix.size(); i++) {

                if (adjMatrix.get(currentIndex).get(i) != null) {
                    
                    Vertex<K,T> neighbor = getVertexByIndex(i);
                    
                    double edgeWeight = adjMatrix.get(currentIndex).get(i);
                    
                    double newDistance = distances.get(currentVertex) + edgeWeight;
                    
                    if (newDistance < distances.get(neighbor)) {
                        
                        distances.put(neighbor, newDistance);
                        
                        queue.add(new Path<>(neighbor, newDistance, currentVertex));
                    
                    }
                
                }
            
            }
        
        }
        
        return null;
    
    }

    private Vertex<K,T> getVertexByIndex(int index) {

        for (Map.Entry<Vertex<K,T>, Integer> entry : mapIndex.entrySet()) {

            
            if (entry.getValue() == index) return entry.getKey();
        
        }
        
        return null;
    
    }    


    private Vertex<K,T> getVertex(int index) {

        for (Map.Entry<Vertex<K,T>, Integer> entry : mapIndex.entrySet()) {

            if (entry.getValue() == index) {

                return entry.getKey();

            }

        }

        return null;

    }

    @Override
    public void deleteVertex(K key) {

        if (vertices.containsKey(key)) {

            Vertex<K,T> vertex = vertices.get(key);

            int index = mapIndex.get(vertex);

            adjMatrix.remove(index);
            
            for (List<Double> row : adjMatrix) {
                
                row.remove(index);
            
            }
            
            mapIndex.remove(vertex);
            
            vertices.remove(key);
        
        }
    
    }

    @Override
    public void deleteEdge(K keySource, K keyDestination) {

        Vertex<K,T> sourceVertex = vertices.get(keySource);
        
        Vertex<K,T> destinationVertex = vertices.get(keyDestination);
        
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
    public Vertex<K,T> searchVertex(K key) {

        return vertices.get(key);

    }

    @Override
    public Double searchEdge(K source, K destination) {

        Vertex<K,T> sourceVertex = vertices.get(source);

        Vertex<K,T> destinationVertex = vertices.get(destination);

        if (sourceVertex != null && destinationVertex != null) {

            int sourceIndex = mapIndex.get(sourceVertex);
            
            int destinationIndex = mapIndex.get(destinationVertex);
            
            return adjMatrix.get(sourceIndex).get(destinationIndex);
        
        }
        
        return null;
    
    }

    @Override
    public Color getColor(K element) {

        Vertex<K,T> vertex = vertices.get(element);

        if (vertex != null) {

            return vertex.getColor();

        }

        return null;

    }

    @Override
    public double getDiscoveryTime(K key) {

        Vertex<K,T> vertex = vertices.get(key);
        
        if (vertex != null) {

            return vertex.getDiscoveryTime();

        }

        return 0;

    }

    @Override
    public int getFinishTime(K key) {

        Vertex<K,T> vertex = vertices.get(key);

        if (vertex != null) {

            return vertex.getFinishTime();

        }

        return 0;
    }

    @Override
    public K getPredecessor(K key) {

        Vertex<K,T> vertex = vertices.get(key);

        if (vertex != null) {

            Vertex<K,T> predecessor = vertex.getPredecessor();

            if (predecessor != null) {

                return predecessor.getKey();

            }

        }

        return null;

    }

}