package test;

import java.util.*;

import org.junit.Assert;

import org.junit.Before;

import org.junit.Test;

import model.*;

public class GraphTest {

    private IGraph<Integer> graph;

    @Before
    public void setUp() {

        graph = new GraphList<>(true);

        graph.addVertex(1);

        graph.addVertex(2);

        graph.addVertex(3);

        graph.addVertex(4);

        graph.addVertex(5);

        graph.addVertex(6);

        graph.addVertex(7);

        graph.addVertex(8);

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

        graph.addVertex(9);

        graph.BFS(1);

        Assert.assertEquals(Color.WHITE, graph.getColor(9));

        Assert.assertEquals(-1, (int)graph.getDiscoveryTime(9));

        Assert.assertNull(graph.getPredecessor(9));

    }

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