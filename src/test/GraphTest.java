package test;

import java.util.*;

import org.junit.Assert;

import org.junit.Before;

import org.junit.Test;

import model.*;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

public class GraphTest {

    private IGraph<Integer, String> graph;

    private Controler c1;

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

    public void setUp1(){

        c1 = new Controler();

    }

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

    @Test
    public void testDijkstraShortestPath() {

        Path<Integer> path = graph.dijkstra(1, 8);

        //Verifica la rutas msa corta entre 1 y 8

        Assert.assertEquals("[1, 2, 5, 8]", path.getPath().toString());

        Assert.assertEquals(3.0, path.getDistance(), 0.001);

    }

    @Test
    public void testDijkstraSourceEqualsDestination() {

        Path<Integer> path = graph.dijkstra(1, 1);

        //Verifica el camino en caso de que el origen y el destino sean los mismos

        Assert.assertEquals("[1]", path.getPath().toString());

        Assert.assertEquals(0.0, path.getDistance(), 0.001);

    }

    @Test
    public void testDijkstraUnreachableDestination() {

        //Prueba el camino en caso de que el camino no exista

        Path<Integer> path = graph.dijkstra(1, 9);

        Assert.assertNull(path);
    }

    @Test
    public void testDijkstraSourceNotInGraph() {

        //Prueba el caso en el que el origen no exista

        Path<Integer> path = graph.dijkstra(9, 8);

        Assert.assertNull(path);
    }

    @Test
    public void testDijkstraDestinationNotInGraph() {

        //Prueba el caso en el que el destino no exista.

        Path<Integer> path = graph.dijkstra(1, 9);

        Assert.assertNull(path);
    }

    // CommonFriends

    @Test

    public void testFindCommonFriends1(){

        setUp1();

        String  user1 = "Juan";

        String user2 = "Mia";

        //Juan y Mia no tienen amigos en comun.

        List<User> commonFriends =  c1.findCommonFriends(user1, user2);

        assertTrue(commonFriends.isEmpty());

    }

    @Test

    public  void testFindCommonFriends2(){

        setUp1();

        String  user1 = "Sofia";

        String user2 = "Mateo";

        List<User> commonFriends =  c1.findCommonFriends(user1, user2);

        assertTrue(!commonFriends.isEmpty());

    }

    @Test

    public void testCommonFriends3(){

        setUp1();

        String  user1 = "Sofia";

        String user2 = "Mateo";

        List<User> commonFriends =  c1.findCommonFriends(user1, user2);

    }

    // RecommendFriends

    @Test
    public void testRecommendFriends() {
        Controler controler = new Controler();

        
        Map<String, User> recommendedFriends = controler.recommendFriends("Paula");
        
        assertEquals(16, recommendedFriends.size());
        
        assertTrue(recommendedFriends.containsKey("Sofia"));
        
        assertTrue(recommendedFriends.containsKey("Juan"));
        
        assertTrue(recommendedFriends.containsKey("Lucas"));
    
    }

    @Test
    public void testRecommendFriends2() {
        
        Controler controler = new Controler();
    
        Map<String, User> recommendedFriends = controler.recommendFriends("Maria");
    
        assertEquals(16, recommendedFriends.size());
    
        assertTrue(recommendedFriends.containsKey("Sofia"));
    
        assertTrue(recommendedFriends.containsKey("Mateo"));
    
        assertTrue(recommendedFriends.containsKey("Isabella"));

    }

    @Test
    public void testRecommendFriends3() {

        Controler controler = new Controler();
    
        Map<String, User> recommendedFriends = controler.recommendFriends("Lucas");
    
        assertTrue(recommendedFriends.containsKey("Sofia"));
    
        assertTrue(recommendedFriends.containsKey("Juan"));
    
        assertTrue(recommendedFriends.containsKey("Mateo"));

    }

    // AnalyzeSocialInfluence

    @Test
    public void testAnalyzeSocialInfluence() {
        Controler controler = new Controler();
    
        String userName = "Paula";
        
        String expectedOutput = "\nLa influencia social de Paula es de 411.0\n" +
                                "Paula cuenta con una gran influencia en la red social. (influencia mayor a la media)\n" +
                                "Datos especificos:\n" +
                                "Calculo de cercania con los usuarios: 119.0\n" +
                                "Calculo de intermediacion con los usuarios: 292.0\n";
    
        String result = controler.analyzeSocialInfluence(userName);
    
        assertEquals(expectedOutput, result);

    }

    @Test
    public void testAnalyzeSocialInfluence2() {
        Controler controler = new Controler();
    
        String userName = "Maria";
        
        String expectedOutput = "La influencia social de Maria es de 244.0\n" +
                                "Maria no cuenta con una gran influencia en la red social. (influencia menor a la media)\n" +
                                "Datos especificos:\n" +
                                "Calculo de cercania con los usuarios: 116.0\n" +
                                "Calculo de intermediacion con los usuarios: 128.0\n";
    
        String result = controler.analyzeSocialInfluence(userName);
    
        assertEquals(expectedOutput, result);

    }

    @Test
    public void testAnalyzeSocialInfluence3() {
        Controler controler = new Controler();
    
        String userName = "Carlos";
        
        String expectedOutput = "La influencia social de Carlos es de 240.0\n" +
                                "Carlos no cuenta con una gran influencia en la red social. (influencia menor a la media)\n" +
                                "Datos especificos:\n" +
                                "Calculo de cercania con los usuarios: 155.0\n" +
                                "Calculo de intermediacion con los usuarios: 85.0\n";
    
        String result = controler.analyzeSocialInfluence(userName);
    
        assertEquals(expectedOutput, result);

    }

}