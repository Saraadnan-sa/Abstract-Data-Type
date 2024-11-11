package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a directed graph where `vertices` is the set of nodes,
    //   and `edges` is a list of directed edges with weights between nodes.
    //
    // Representation invariant:
    //   - vertices and edges are non-null.
    //   - All edges connect vertices within the `vertices` set.
    //   - No duplicate edges with the same source and target in the edges list.
    //   - All weights in edges are positive.
    //
    // Safety from rep exposure:
    //   - The vertices set and edges list are private and final.
    //   - Methods return defensive copies or unmodifiable views.
    
    // Constructor
    public ConcreteEdgesGraph() {
        checkRep();
    }

    // Check representation invariant
    private void checkRep() {
        assert vertices != null : "Vertices set should not be null";
        assert edges != null : "Edges list should not be null";
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "Edge source should be in vertices set";
            assert vertices.contains(edge.getTarget()) : "Edge target should be in vertices set";
            assert edge.getWeight() > 0 : "Edge weight should be positive";
        }
    }
    
    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }
    
    @Override
    public int set(String source, String target, int weight) {
        if (!vertices.contains(source)) add(source);
        if (!vertices.contains(target)) add(target);
        
        int previousWeight = 0;
        
        // Find existing edge, if present, and remove it
        Edge toRemove = null;
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                previousWeight = edge.getWeight();
                toRemove = edge;
                break;
            }
        }
        
        if (toRemove != null) edges.remove(toRemove);
        
        // If weight is not zero, add or update the edge
        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        
        checkRep();
        return previousWeight;
    }
    
    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) return false;
        
        vertices.remove(vertex);
        
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        
        checkRep();
        return true;
    }
    
    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(sources);
    }
    
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return Collections.unmodifiableMap(targets);
    }


    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        // Append vertices
        sb.append("Vertices: ").append(vertices()).append("\n");
        
        // Append edges
        sb.append("Edges:\n");
        for (String source : vertices()) {
            for (Map.Entry<String, Integer> entry : targets(source).entrySet()) {
                sb.append(source).append(" -> ").append(entry.getKey())
                  .append(" (").append(entry.getValue()).append(")\n");
            }
        }

        return sb.toString();
    }

}




class Edge {
    
    private final String source;
    private final String target;
    private final int weight;
    
    // Abstraction function:
    //   Represents a directed, weighted edge from `source` to `target` with a weight of `weight`.
    //
    // Representation invariant:
    //   - source and target are non-null.
    //   - weight is positive.
    //
    // Safety from rep exposure:
    //   - Fields are private, final, and immutable (String and int).
    
    // Constructor
    public Edge(String source, String target, int weight) {
        if (source == null || target == null || weight <= 0) {
            throw new IllegalArgumentException("Invalid source, target, or weight");
        }
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }
    
    // Check representation invariant
    private void checkRep() {
        assert source != null : "Source should not be null";
        assert target != null : "Target should not be null";
        assert weight > 0 : "Weight should be positive";
    }
    
    public String getSource() {
        return source;
    }
    
    public String getTarget() {
        return target;
    }
    
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
