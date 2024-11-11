package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Set;

import org.junit.Test;

public abstract class GraphInstanceTest {
    
    // Provide empty instance of Graph for each test
    public abstract Graph<String> emptyInstance();
    
    // Testing strategy:
    // - testInitialVerticesEmpty(): test that a new graph has no vertices
    // - testAddVertex(): add vertices and check if they exist in the graph
    // - testAddDuplicateVertex(): add a vertex that already exists and check no duplication
    // - testSetEdge(): add edges and check if they exist with correct weights
    // - testRemoveVertex(): remove a vertex and verify it no longer exists
    // - testSourcesAndTargets(): check sources and targets for directed edges

    // Test that a new graph has no vertices
    @Test
    public void testInitialVerticesEmpty() {
        Graph<String> graph = emptyInstance();
        assertEquals("expected empty graph to have no vertices", Collections.emptySet(), graph.vertices());
    }

    // Add vertices and check if they exist in the graph
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("expected true when adding a new vertex", graph.add("A"));
        assertTrue("expected graph to contain the added vertex", graph.vertices().contains("A"));
    }
    
    // Add a vertex that already exists and check for no duplication
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse("expected false when adding a duplicate vertex", graph.add("A"));
    }

    // Add edges and check if they exist with correct weights
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals("expected no previous edge", 0, graph.set("A", "B", 5));
        assertEquals("expected new edge with weight 5", (Integer) 5, graph.targets("A").get("B"));
    }

    // Remove a vertex and verify it no longer exists
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue("expected true when removing an existing vertex", graph.remove("A"));
        assertFalse("expected graph to not contain the removed vertex", graph.vertices().contains("A"));
    }

    // Check sources and targets for directed edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 3);
        graph.set("C", "B", 2);
        
        // Check sources for vertex B
        assertEquals("expected source A with weight 3 for vertex B", (Integer) 3, graph.sources("B").get("A"));
        assertEquals("expected source C with weight 2 for vertex B", (Integer) 2, graph.sources("B").get("C"));
        
        // Check targets for vertex A
        assertEquals("expected target B with weight 3 from vertex A", (Integer) 3, graph.targets("A").get("B"));
        assertTrue("expected no other targets from vertex A", graph.targets("A").size() == 1);
    }
}

