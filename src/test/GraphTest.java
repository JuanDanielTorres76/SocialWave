package test;

import java.util.*;

import org.junit.Assert;

import org.junit.Before;

import org.junit.Test;

import model.*;

public class GraphTest {

    private IGraph<Integer, String> graph;

    @Before
    public void setUp() {

        graph = new GraphList<>(true);

        graph.addVertex(1, "One");

        graph.addVertex(2, "Two");

        graph.addVertex(3, "Three");

        graph.addVertex(4, "Four");

        graph.addVertex(5, "Five");

        graph.addVertex(6, "Six");

        graph.addVertex(7, "Seven");

        graph.addVertex(8, "Eigth");

        graph.addEdge(1, 2, 1);

        graph.addEdge(1, 3, 1);

        graph.addEdge(2, 4, 1);

        graph.addEdge(2, 5, 1);

        graph.addEdge(3, 6, 1);

        graph.addEdge(3, 7, 1);

        graph.addEdge(4, 8, 1);

        graph.addEdge(5, 8, 1);

        graph.addEdge(6, 8, 1);

        graph.addEdge(7, 8, 1);

    }

    // Add

    @Test
    public void testAddVertex() {
        
        graph.addVertex(9, "Nine");
        
        Assert.assertNotNull(graph.searchVertex(9));
    
    }

    @Test
    public void testAddDuplicateVertex() {
        
        graph.addVertex(1, "One");
     
        Assert.assertEquals("One", graph.searchVertex(1).getElement());
    
    }

    @Test
    public void testAddEdge() {
        
        graph.addEdge(1, 4, 2.5);
       
        Assert.assertEquals(2.5, graph.searchEdge(1, 4), 0.001);
    }
    

    // Deelete

    @Test
    public void testRemoveVertex() {
        
        graph.deleteVertex(8);
        
        Assert.assertNull(graph.searchVertex(8));
        
        Assert.assertNull(graph.searchEdge(4, 8));
        
        Assert.assertNull(graph.searchEdge(5, 8));
        
        Assert.assertNull(graph.searchEdge(6, 8));
        
        Assert.assertNull(graph.searchEdge(7, 8));
    
    }

    @Test
    public void testRemoveNonexistentVertex() {

        // Vertice inexistente
        graph.deleteVertex(9);

        Assert.assertNotNull(graph.searchVertex(1));
        
        Assert.assertNotNull(graph.searchVertex(2));
        
        Assert.assertNotNull(graph.searchVertex(3));
    
    }
    

    // Search

    @Test
    public void testSearchVertex() {
        
        Vertex<Integer, String> vertex = graph.searchVertex(3);
        
        Assert.assertNotNull(vertex);
        
        Assert.assertEquals("Three", vertex.getElement());
    
    }

    @Test
    public void testSearchNonexistentEdge() {

        // Arista inexistente
        Double weight = graph.searchEdge(4, 6);
        
        Assert.assertNull(weight);

    }

    @Test
    public void testSearchNonexistentVertex() {

        // Vertice inexistente
        Vertex<Integer, String> vertex = graph.searchVertex(9);
        
        Assert.assertNull(vertex);
    
    }

    // Dijkstra



    // Prim



    // BFS

    @Test
    public void testBFSColor() {

        graph.BFS(1);

        Assert.assertEquals(Color.BLACK, graph.getColor(1));

        Assert.assertEquals(Color.BLACK, graph.getColor(2));

        Assert.assertEquals(Color.BLACK, graph.getColor(3));

        Assert.assertEquals(Color.BLACK, graph.getColor(4));

        Assert.assertEquals(Color.BLACK, graph.getColor(5));

        Assert.assertEquals(Color.BLACK, graph.getColor(6));

        Assert.assertEquals(Color.BLACK, graph.getColor(7));

        Assert.assertEquals(Color.BLACK, graph.getColor(8));

    }

    @Test
    public void testBFSPath() {

        graph.BFS(1);

        Assert.assertEquals(1, (int)graph.getPredecessor(2));

        Assert.assertEquals(1, (int)graph.getPredecessor(3));

        Assert.assertEquals(2, (int)graph.getPredecessor(4));

        Assert.assertEquals(2, (int)graph.getPredecessor(5));

        Assert.assertEquals(3, (int)graph.getPredecessor(6));

        Assert.assertEquals(3, (int)graph.getPredecessor(7));

        Assert.assertEquals(4, (int)graph.getPredecessor(8));

    }

    @Test
    public void testBFSDisconnected() {

        graph.addVertex(9, "Nine");

        graph.BFS(1);

        Assert.assertEquals(Color.WHITE, graph.getColor(9));

        Assert.assertEquals(-1, (int)graph.getDiscoveryTime(9));

        Assert.assertNull(graph.getPredecessor(9));

    }

    // DFS

    @Test
    public void testDFSBackEdge() {

        graph.addEdge(4, 2, 1);

        graph.DFS();

        int discoveryTime2 = (int)graph.getDiscoveryTime(2);

        int discoveryTime4 = (int)graph.getDiscoveryTime(4);

        int finishTime2 = (int)graph.getFinishTime(2);

        int finishTime4 = (int)graph.getFinishTime(4);

        Assert.assertEquals(discoveryTime2 < discoveryTime4 && discoveryTime4 < finishTime4 && finishTime4 < finishTime2, false);

    }

    @Test
    public void testDFSPath() {
        
        graph.DFS();

        Assert.assertEquals(1, (int)graph.getPredecessor(2));

        Assert.assertEquals(1, (int)graph.getPredecessor(3));

        Assert.assertEquals(2, (int)graph.getPredecessor(4));

        Assert.assertEquals(2, (int)graph.getPredecessor(5));

        Assert.assertEquals(3, (int)graph.getPredecessor(6));

        Assert.assertEquals(3, (int)graph.getPredecessor(7));

        Assert.assertEquals(4, (int)graph.getPredecessor(8));

    }

    @Test
    public void testDFSOrder() {

        List<Integer> order = new ArrayList<>();

        graph.DFS();

        for (int i = 1; i <= 8; i++) {

            order.add((int)graph.getFinishTime(i));

        }

        List<Integer> expectedOrder = new ArrayList<>(order);

        Collections.sort(expectedOrder);

        Assert.assertEquals(expectedOrder, order);
        
    }

}