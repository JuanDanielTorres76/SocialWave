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

    public void setUp1(){

        graph.addVertex(1, "One");
        graph.addVertex(2, "Two");
        graph.addVertex(3, "Three");
        graph.addVertex(4, "Four");
        graph.addVertex(5, "Five");

        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 2, 5);
        graph.addEdge(2, 4, 1);
        graph.addEdge(3, 2, 3);
        graph.addEdge(3, 5, 2);
        graph.addEdge(3, 4, 9);
        graph.addEdge(5, 4, 6);
        graph.addEdge(5, 1, 2);

    }


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
    public void testFloydWarshall1(){

        double distanceExpected = 9.0;
        Integer [] pathExpected = new Integer[]{1, 3, 2, 4};
        Path<Integer> path;

        Pair<Integer, Integer> route = new Pair<>(1, 4);

        graph = new GraphList<>(true);

        setUp1();

        path =  graph.floydWarshall().get(route);

        verifyPath(path, pathExpected, distanceExpected);

        //--------------------------------------------

        graph = new GraphMatrix<>(true);

        setUp1();

        path =  graph.floydWarshall().get(route);

        verifyPath(path, pathExpected, distanceExpected);

    }

    public boolean verifyPath(Path<Integer> path, Integer[] pathExpected, double distanceExpected){

        if(path.getDistance() != distanceExpected) return false;

        for(int i = 0; i < pathExpected.length; i++)
            if(!path.getPath().get(i).equals(pathExpected[i]))
                return false;

        return true;
    }



}