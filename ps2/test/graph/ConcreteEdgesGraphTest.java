package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override 
    public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   The toString method should provide a string representation of the graph's vertices
    //   and edges. This test assumes that the toString implementation gives details about
    //   vertices and their respective edges.
    
    @Test
    public void testToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 3); // Add an edge from A to B with weight 3

        // The expected string representation of the graph
        String expected = "Vertices: [A, B]\nEdges:\nA -> B (3)\n";

        // Assert that the actual string representation matches the expected one
        assertEquals("String representation should match", expected, graph.toString());
    }

    // TODO tests for ConcreteEdgesGraph.toString()
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   The Edge class should represent an edge with a source, target, and weight.
    //   We will test the basic creation and manipulation of edges.
    
    @Test
    public void testEdgeCreation() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 10);
        
        // Verifying the edge exists with the correct weight
        assertEquals("expected edge weight to be 10", (Integer) 10, graph.targets("A").get("B"));
    }

    @Test
    public void testEdgeUpdate() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        // Updating the edge weight
        graph.set("A", "B", 10);
        
        // Verifying the updated edge weight
        assertEquals("expected edge weight to be updated to 10", (Integer) 10, graph.targets("A").get("B"));
    }

    @Test
    public void testEdgeRemoval() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        // Removing the edge by setting it to a null weight or non-existent state
        graph.set("A", "B", 0);
        
        // Verifying the edge no longer exists or has been removed
        assertFalse("expected edge to be removed", graph.targets("A").containsKey("B"));
    }
}
