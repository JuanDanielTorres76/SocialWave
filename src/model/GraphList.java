package model;

import java.util.*;

public class GraphList<K,T> implements IGraph<K,T>{

    private Map<K, Vertex<K,T>> vertices;

    private Map<Vertex<K,T>, Map<Vertex<K,T>, Double>> edges;

    private boolean isDirected;

    public GraphList(boolean isDirected){

        vertices = new HashMap<>();

        edges = new HashMap<>();

        this.isDirected = isDirected;
        
    }

    @Override
    public void addVertex(K key, T element) {

        if(!vertices.containsKey(key)){

            vertices.put(key, new Vertex<>(element, key));

        }

    }

    @Override
    public void addEdge(K source, K destination, double weight) {

        Vertex<K,T> verticeSource = vertices.get(source);

        Vertex<K,T> verticeDestination = vertices.get(destination);

        if(verticeSource != null && verticeDestination != null){

            verticeSource.addAdjacent(verticeDestination);

            Map<Vertex<K,T>, Double> firts = edges.get(verticeSource) != null ? edges.get(verticeSource) : new HashMap<>();

            firts.put(verticeDestination, weight);

            edges.put(verticeSource, firts);

            if(!isDirected){
                verticeDestination.addAdjacent(verticeSource);

                Map<Vertex<K,T>, Double> second = edges.get(verticeDestination) != null ? edges.get(verticeDestination) : new HashMap<>();

                second.put(verticeSource, weight);

                edges.put(verticeDestination, second);

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

        for(Vertex<K,T> a : vertices.values()){

            if(a.getColor() == Color.WHITE){

                time = DFSTraversal(a, time);

            }
        }

    }

    private int DFSTraversal(Vertex<K,T> a, int time){

        time++;

        a.setDiscoveryTime(time);

        a.setColor(Color.GRAY);

        for(Vertex<K,T> b: a.getGraphList()){

            if(b.getColor() == Color.WHITE){

                b.setPredecessor(a);

                DFSTraversal(b, time);

            }

        }

        a.setColor(Color.BLACK);

        time++;

        a.setFinishTime(time);

        return time;

    }

    @Override
    public void BFS(K sourceKey) {

        Vertex<K,T> source = vertices.get(sourceKey);
        
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
        
            for (Vertex<K,T> v : a.getGraphList()) {

                if (v.getColor() == Color.WHITE) {

                    v.setColor(Color.GRAY);

                    v.setDiscoveryTime(a.getDiscoveryTime() + 1);

                    v.setPredecessor(a);

                    queue.offer(v);

                }

            }
        
            a.setColor(Color.BLACK);

        }

    }

    @Override
    public void deleteVertex(K key){

        if(vertices.containsKey(key)){

            edges.put(vertices.get(key), null);

            vertices.put(key, null);

        }

    }

    @Override
    public Map<Pair<K, K>, Path<K>> floydWarshall(){

        int n = vertices.size();
        double [][] distance = new double[n][n];
        Vertex<K,T> [][] prev = new Vertex[n][n];
        List<Vertex<K,T>> vertexList = new ArrayList<>(vertices.values());
        Map<Vertex<K,T>, Integer> mapIndex = new HashMap<>();

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                distance[i][j] = Double.MAX_VALUE;
            }
        }

        for(int i = 0; i < n; i++){
            mapIndex.put(vertexList.get(i), i);
            distance[i][i] = 0;
        }

        for(Vertex<K,T> v: vertexList){
            for(Vertex<K,T> u: v.getGraphList()){
                distance[mapIndex.get(v)][mapIndex.get(u)] = edges.get(v).get(u);
                prev[mapIndex.get(v)][mapIndex.get(u)] = v;
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
                listPath.add(vertexList.get(j).getKey());

                Vertex<K,T> current = prev[i][j];

                while(current != null && current != vertexList.get(i)){
                    listPath.add(current.getKey());
                    current = prev[i][mapIndex.get(current)];
                }
                listPath.add(vertexList.get(i).getKey());
                Collections.reverse(listPath);

                paths.put(new Pair<>(vertexList.get(i).getKey(), vertexList.get(j).getKey()),
                        new Path<>(listPath, distance[i][j]));

            }
        }

        return paths;

    }

    @Override

    public PathDijkstra<K,T> dijkstra(K eSource, K eDestination) {
        
        Map<Vertex<K,T>, Double> distances = new HashMap<>();
        
        for (Vertex<K,T> vertex : vertices.values()) {
            
            distances.put(vertex, Double.POSITIVE_INFINITY);
        
        }
        
        PriorityQueue<PathDijkstra<K,T>> queue = new PriorityQueue<>(Comparator.comparingDouble(PathDijkstra::getDistance));
        
        Vertex<K,T> sourceVertex = vertices.get(eSource);
        
        distances.put(sourceVertex, 0.0);
        
        queue.add(new PathDijkstra(sourceVertex, 0, null));
        
        while (!queue.isEmpty()) {
            
            PathDijkstra<K,T> currentPath = queue.poll();
            
            Vertex<K,T> currentVertex = currentPath.getVertex();
    
            if (!currentPath.isVisited()) {
                
                currentPath.setVisited(true);

            }
    
            if (currentVertex.getElement().equals(eDestination)) {
                
                return currentPath;
                
            }

            for (Map.Entry<Vertex<K,T>, Double> entry : edges.get(currentVertex).entrySet()) {

                Vertex<K,T> neighbor = entry.getKey();
                
                double edgeWeight = entry.getValue();
                
                double newDistance = distances.get(currentVertex) + edgeWeight;
                
                if (newDistance < distances.get(neighbor)) {
                    
                    distances.put(neighbor, newDistance);
                    
                    queue.add(new PathDijkstra<>(neighbor, newDistance, currentVertex));
                
                }
            
            }
        
        }
    
        return null;
    
    } 


    @Override
    public Vertex<K,T> searchVertex(K key){

        return vertices.get(key);

    }

    @Override
    public Double searchEdge(K sourceKey, K destinationKey){

        Map<Vertex<K,T>, Double> aux = edges.get(vertices.get(sourceKey));

        if(aux != null){

            return aux.get(vertices.get(destinationKey));

        }

        return null;
    }

    @Override
    public void deleteEdge(K source, K destination){

        Vertex<K,T> vSource = vertices.get(source);

        Vertex<K,T> vDestination = vertices.get(destination);

        if(vSource != null && vDestination != null){

            edges.get(vSource).remove(vDestination);

            if(!isDirected){
                
                edges.get(vDestination).remove(vSource);
            }

        }
        
    }


    @Override
    public Color getColor(K key) {

        Vertex<K,T> vertex = vertices.get(key);

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
    public K getPredecessor(K element) {

        Vertex<K,T> vertex = vertices.get(element);

        if (vertex != null) {

            Vertex<K,T> predecessor = vertex.getPredecessor();

            if (predecessor != null) {

                return predecessor.getKey();

            }

        }

        return null;

    }

}