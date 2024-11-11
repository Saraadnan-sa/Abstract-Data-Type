package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override 
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   The toString method should provide a string representation of the graph's vertices.
    //   We assume this method prints a list of all vertices in the graph.
    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // The expected output based on the assumed formatting of toString()
        String expected = "Vertices: A, B";
        
        // Assert that the string representation contains the expected vertices format
        assertTrue("expected toString to contain the vertices details", graph.toString().contains(expected));
    }

    // TODO tests for ConcreteVerticesGraph.toString()
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   The Vertex class should represent vertices and handle operations such as adding,
    //   removing, and checking existence.
    
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected true when adding a new vertex", graph.add("A"));
        assertTrue("expected graph to contain the added vertex", graph.vertices().contains("A"));
    }
    
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected false when adding a duplicate vertex", graph.add("A"));
    }

    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected true when removing an existing vertex", graph.remove("A"));
        assertFalse("expected graph to not contain the removed vertex", graph.vertices().contains("A"));
    }

    @Test
    public void testRemoveNonExistentVertex() {
        Graph<String> graph = emptyInstance();
        assertFalse("expected false when removing a non-existent vertex", graph.remove("A"));
    }
    
    @Test
    public void testAddVertexWithEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        
        // Check if the vertex "A" is present and it has edges
        assertTrue("expected vertex A to be present with an edge to B", graph.vertices().contains("A"));
        assertEquals("expected edge from A to B with weight 5", (Integer) 5, graph.targets("A").get("B"));
    }
}
