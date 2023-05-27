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
    public Path<K> dijkstra(K eSource, K eDestination) {
        
        Vertex<K,T> source = vertices.get(eSource);
        
        Vertex<K,T> destination = vertices.get(eDestination);
        
        if (source == null || destination == null) {
            
            return null;
        
        }
        
        Map<Vertex<K,T>, Double> distances = new HashMap<>();
        
        Map<Vertex<K,T>, Vertex<K,T>> predecessors = new HashMap<>();
        
        PriorityQueue<Vertex<K,T>> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        
        for (Vertex<K,T> vertex : vertices.values()) {
            
            distances.put(vertex, Double.MAX_VALUE);
            
            predecessors.put(vertex, null);
        
        }
        
        distances.put(source, 0.0);
        
        queue.offer(source);
        
        while (!queue.isEmpty()) {
            
            Vertex<K,T> current = queue.poll();
            
            if (current.equals(destination)) {
                
                break;
            
            }
            
            for (Vertex<K,T> adjacent : getAdjacentVertices(current)) {
                
                double weight = searchEdge(current.getKey(), adjacent.getKey());
                
                double newDistance = distances.get(current) + weight;
                
                if (newDistance < distances.get(adjacent)) {
                    
                    distances.put(adjacent, newDistance);
                    
                    predecessors.put(adjacent, current);
                    
                    queue.offer(adjacent);
                
                }
            
            }
        
        }
        
        List<K> path = new ArrayList<>();
        
        Vertex<K,T> current = destination;
        
        while (current != null) {
            
            path.add(0, current.getKey());
            
            current = predecessors.get(current);
        
        }
        
        return new Path<>(path, distances.get(destination));
    
    }
    
    private List<Vertex<K,T>> getAdjacentVertices(Vertex<K,T> vertex) {
        
        List<Vertex<K,T>> adjacentVertices = new ArrayList<>();
        
        int index = mapIndex.get(vertex);
        
        for (int i = 0; i < adjMatrix.size(); i++) {
            
            if (adjMatrix.get(index).get(i) != 0) {
                
                adjacentVertices.add(getVertex(i));
            
            }
        
        }
        
        return adjacentVertices;
    
    }
    
    @Override
    public Map<Pair<K, K>, Path<K>> floydWarshall() {

        int n = vertices.size();
        double [][] distance = new double[n][n];
        Vertex<K,T> [][] prev = new Vertex[n][n];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                distance[i][j] = Double.MAX_VALUE;
            }
            distance[i][i] = 0;
        }

        Map<Integer, Vertex<K,T>> aux = new HashMap<>();
        mapIndex.forEach((vertex, i) ->  aux.put(i, vertex));

        for (int i = 0; i < adjMatrix.size(); i++){
            for(int j = 0; j < adjMatrix.size(); j++){
                if(adjMatrix.get(i).get(j) != Double.MAX_VALUE){
                    distance[i][j] = adjMatrix.get(i).get(j);
                    prev[i][j] = aux.get(i);
                }
            }
        }


        for (int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++) {
                    if(distance[i][j] > distance[i][k] + distance[k][j]){
                        distance[i][j] = distance[i][k] + distance[k][j];
                        prev[i][j] = prev[k][j];
                    }
                }
            }
        }

        Map<Pair<K, K>, Path<K>> paths = new HashMap<>();

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){

                if(i == j) continue;

                List<K> listPath = new ArrayList<>();
                listPath.add(aux.get(j).getKey());

                Vertex<K,T> current = prev[i][j];
                while(current != null && current != aux.get(i)){
                    listPath.add(current.getKey());
                    current = prev[i][mapIndex.get(current)];
                }
                listPath.add(aux.get(i).getKey());
                Collections.reverse(listPath);

                paths.put(new Pair<>(aux.get(i).getKey(), aux.get(j).getKey()),
                        new Path<>(listPath, distance[i][j]));

            }
        }

        return paths;

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