package model;

import java.util.*;

public class GraphList<E> implements IGraph<E>{

    private Map<E, Vertex<E>> vertices;

    private Map<Vertex<E>, Map<Vertex<E>, Double>> edges;

    private boolean isDirected;

    public GraphList(boolean isDirected){

        vertices = new HashMap<>();

        edges = new HashMap<>();

        this.isDirected = isDirected;
        
    }

    @Override
    public void addVertex(E element) {

        if(!vertices.containsKey(element)){

            vertices.put(element, new Vertex<>(element));

        }

    }

    @Override
    public void addEdge(E source, E destination, double weight) {

        Vertex<E> verticeSource = vertices.get(source);

        Vertex<E> verticeDestination = vertices.get(destination);

        // Verifica si los vertices de origen y destino existen en el grafo.

        if(verticeSource != null && verticeDestination != null){

            verticeSource.addAdjacent(verticeDestination);

            // Obtiene el mapa de vertices adyacentes y pesos del vértice de origen.

            Map<Vertex<E>, Double> firts = edges.get(verticeSource) != null ? edges.get(verticeSource) : new HashMap<>();

            // Agrega una entrada al mapa con el vértice de destino como clave y el peso como valor

            firts.put(verticeDestination, weight);

            edges.put(verticeSource, firts);

            // Verifica si el grafo no es dirigido.

            if(!isDirected){
                verticeDestination.addAdjacent(verticeSource);

                Map<Vertex<E>, Double> second = edges.get(verticeDestination) != null ? edges.get(verticeDestination) : new HashMap<>();

                second.put(verticeSource, weight);

                edges.put(verticeDestination, second);

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

        for(Vertex<E> a : vertices.values()){

            if(a.getColor() == Color.WHITE){

                time = DFSTraversal(a, time);

            }
        }

    }

    private int DFSTraversal(Vertex<E> a, int time){

        time++;

        a.setDiscoveryTime(time);

        a.setColor(Color.GRAY);

        for(Vertex<E> b: a.getGraphList()){

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
        
            for (Vertex<E> v : a.getGraphList()) {

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
    public Path<E> dijkstra(E eSource, E eDestination){
        
    }

    @Override
    public void deleteVertex(E element){

        if(vertices.containsKey(element)){

            edges.put(vertices.get(element), null);

            vertices.put(element, null);

        }

    }

    @Override
    public Vertex<E> searchVertex(E element){

        return vertices.get(element);

    }

    @Override
    public Double searchEdge(E source, E destination){

        Map<Vertex<E>, Double> aux = edges.get(vertices.get(source));

        if(aux != null){

            return aux.get(vertices.get(destination));

        }

        return null;
    }

    @Override
    public void deleteEdge(E source, E destination){

        Vertex<E> vSource = vertices.get(source);

        Vertex<E> vDestination = vertices.get(destination);

        if(vSource != null && vDestination != null){

            edges.get(vSource).remove(vDestination);

            if(!isDirected){
                
                edges.get(vDestination).remove(vSource);
            }

        }
        
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