package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   Represents a directed graph with vertices and edges. Each vertex in the
    //   graph has directed edges to other vertices with specific weights.
    //
    // Representation invariant:
    //   - vertices is non-null.
    //   - Each Vertex object in vertices has unique vertex names.
    //   - Edge weights are non-negative, and no edge has a weight of zero (unless removed).
    //
    // Safety from rep exposure:
    //   - The vertices list is private and final.
    //   - Methods return unmodifiable views or copies to prevent external modification.
    
    // Constructor
    public ConcreteVerticesGraph() {
        checkRep();
    }

    // Check representation invariant
    private void checkRep() {
        assert vertices != null : "Vertices list should not be null";
        Set<String> names = new HashSet<>();
        for (Vertex vertex : vertices) {
            assert vertex != null : "Vertex should not be null";
            assert !names.contains(vertex.getName()) : "Vertex names should be unique";
            names.add(vertex.getName());
        }
    }
    
    @Override
    public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (v.getName().equals(vertex)) return false;
        }
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        Vertex sourceVertex = findOrCreateVertex(source);
        Vertex targetVertex = findOrCreateVertex(target);
        
        int previousWeight = sourceVertex.setEdge(target, weight);
        
        if (weight == 0) {
            sourceVertex.removeEdge(target);
        }
        
        checkRep();
        return previousWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        Vertex toRemove = null;
        for (Vertex v : vertices) {
            if (v.getName().equals(vertex)) {
                toRemove = v;
                break;
            }
        }
        
        if (toRemove == null) return false;
        
        vertices.remove(toRemove);
        
        for (Vertex v : vertices) {
            v.removeEdge(vertex);
        }
        
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        Set<String> vertexNames = new HashSet<>();
        for (Vertex v : vertices) {
            vertexNames.add(v.getName());
        }
        return Collections.unmodifiableSet(vertexNames);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Vertex v : vertices) {
            int weight = v.getEdgeWeight(target);
            if (weight > 0) {
                sources.put(v.getName(), weight);
            }
        }
        return Collections.unmodifiableMap(sources);
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (v.getName().equals(source)) {
                return Collections.unmodifiableMap(v.getEdges());
            }
        }
        return Collections.emptyMap();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Append vertices in a single line, separated by commas
        sb.append("Vertices: ");
        sb.append(String.join(", ", vertices())); // assuming vertices() returns a Collection or List

        return sb.toString();
    }


    private Vertex findOrCreateVertex(String name) {
        for (Vertex v : vertices) {
            if (v.getName().equals(name)) return v;
        }
        Vertex newVertex = new Vertex(name);
        vertices.add(newVertex);
        return newVertex;
    }
}




class Vertex {
    
    private final String name;
    private final Map<String, Integer> edges = new HashMap<>();
    
    // Abstraction function:
    //   Represents a vertex in a graph, storing directed edges with weights to other vertices.
    //
    // Representation invariant:
    //   - name is non-null.
    //   - edges is non-null.
    //   - Edge weights are positive; edges with zero weight are removed.
    //
    // Safety from rep exposure:
    //   - Fields are private and final or immutable.
    //   - Methods return defensive copies or unmodifiable views.

    // Constructor
    public Vertex(String name) {
        if (name == null) throw new IllegalArgumentException("Vertex name cannot be null");
        this.name = name;
    }
    
    // Check representation invariant
    private void checkRep() {
        assert name != null : "Vertex name should not be null";
        assert edges != null : "Edges map should not be null";
    }

    public String getName() {
        return name;
    }

    public int getEdgeWeight(String target) {
        return edges.getOrDefault(target, 0);
    }
    
    public int setEdge(String target, int weight) {
        if (weight < 0) throw new IllegalArgumentException("Edge weight cannot be negative");
        int previousWeight = edges.getOrDefault(target, 0);
        if (weight == 0) {
            edges.remove(target);
        } else {
            edges.put(target, weight);
        }
        checkRep();
        return previousWeight;
    }
    
    public boolean removeEdge(String target) {
        return edges.remove(target) != null;
    }
    
    public Map<String, Integer> getEdges() {
        return new HashMap<>(edges);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + " -> ");
        for (Map.Entry<String, Integer> edge : edges.entrySet()) {
            result.append(edge.getKey()).append(" (").append(edge.getValue()).append("), ");
        }
        return result.toString();
    }
}

